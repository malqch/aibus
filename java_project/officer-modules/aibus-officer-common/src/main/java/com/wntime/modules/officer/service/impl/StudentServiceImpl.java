package com.wntime.modules.officer.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.CompressTools;
import com.wntime.common.MultFileUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.customer.entity.InfoBusStationEntity;
import com.wntime.customer.entity.InfoCompanyLineEntity;
import com.wntime.customer.service.InfoBusStationService;
import com.wntime.customer.service.InfoCompanyLineService;
import com.wntime.customer.service.InfoLineStationService;
import com.wntime.customer.service.PlanBusServiceService;
import com.wntime.customer.vo.InfoLineStationVo;
import com.wntime.entity.AdminUser;
import com.wntime.modules.officer.dao.StudentDao;
import com.wntime.modules.officer.dao.StudentInfoDao;
import com.wntime.modules.officer.dto.StudentInfo;
import com.wntime.modules.officer.dto.StudentInfoDto;
import com.wntime.modules.officer.entity.GuardianEntity;
import com.wntime.modules.officer.entity.PeopleBasicFactsEntity;
import com.wntime.modules.officer.entity.StudentEntity;
import com.wntime.modules.officer.entity.StudentLineSeatEntity;
import com.wntime.modules.officer.service.GuardianService;
import com.wntime.modules.officer.service.PeopleBasicFactsService;
import com.wntime.modules.officer.service.StudentLineSeatService;
import com.wntime.modules.officer.service.StudentService;
import com.wntime.modules.officer.vo.LineStationVO;
import com.wntime.modules.sys.entity.AdminRole;
import com.wntime.modules.sys.form.AdminUserFrom;
import com.wntime.modules.sys.service.AdminRoleService;
import com.wntime.modules.sys.service.AdminUserService;
import com.wntime.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static com.wntime.common.utils.ShiroUtils.getUserId;


@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentDao, StudentEntity> implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${wntime.officer.upload.filePath}")
    private String filePath;
    @Value("${wntime.officer.upload.fileUrl}")
    private String fileUrl;

    @Value("${wntime.officer.upload.imagePath}")
    private String imagePath;
    @Value("${wntime.officer.upload.imageUrl}")
    private String imageUrl;


    @Autowired
    PeopleBasicFactsService factsService;

    @Autowired
    InfoBusStationService busStationService;

    @Autowired
    InfoCompanyLineService companyLineService;

    @Autowired
    StudentLineSeatService studentLineSeatService;

    @Autowired
    GuardianService guardianService;

    @Autowired
    AdminUserService adminUserService;

    @Autowired
    AdminRoleService adminRoleService;

    @Autowired
    StudentInfoDao studentInfoDao;


    @Autowired
    PlanBusServiceService serviceService;

    @Autowired
    InfoLineStationService lineStationService;

    @Override
    public Set<LineStationVO> getLineStation(Long companyLineId) {
        List<InfoLineStationVo> stationVos = lineStationService.listByLineId(companyLineId);
        TreeSet<LineStationVO> set = new TreeSet<>();
        for (InfoLineStationVo vo : stationVos) {
            LineStationVO stationVO = new LineStationVO();
            stationVO.setBusStationName(vo.getBusStationName());
            stationVO.setCompanyLineId(vo.getCompanyLineId());
            stationVO.setBusStationId(vo.getBusStationId());
            stationVO.setStationOrder(vo.getStationOrder());
            set.add(stationVO);
        }
        return set;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Long id) {
        StudentInfoDto studentInfoDto = getStudentInfoById(id);
        Long basicId = studentInfoDto.getBasicId();
        factsService.removeById(basicId);
        Long guardianId = studentInfoDto.getGuardianId();
        GuardianEntity guardianEntity = guardianService.getById(guardianId);
        AdminUser adminUser = adminUserService.getById(guardianEntity.getLoginUserId());
        adminUserService.removeById(adminUser.getUserId());
        guardianService.removeById(guardianId);
        this.baseMapper.deleteById(id);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        for (Serializable serializable : idList) {
            removeById(serializable);
        }
        return true;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

//        QueryWrapper<StudentInfo> queryWrapper = new QueryWrapper<>();
        QueryWrapper<StudentInfoDto> wrapper = new QueryWrapper<>();
        if (params.containsKey("studentName")) {
            wrapper.like("ipbf.full_name", params.get("studentName"));
        }
        if(params.containsKey("companyLineName")){
            wrapper.and(queryWrapper ->{
                queryWrapper.like("sls.up_company_line_name",params.get("companyLineName"))
                        .or()
                        .like("sls.off_bus_station_name",params.get("companyLineName"));
            });
        }
        wrapper.eq("ins.is_deleted", "0");
        IPage<StudentInfoDto> page = studentInfoDao.query(
                new Query<StudentEntity>().getPage(params),
                params, wrapper);
//        if(page != null && page.getRecords() != null){
//            page.getRecords().forEach(studentInfoDto -> {
//
//            });
//        }
        return new PageUtils(page);
    }


    @Override
    public HashMap<String, Object> importInfo(MultipartFile attach, Long schoolId, Long classId) throws Exception {
        int succ = 0;
        int fail = 0;
        ArrayList<StudentInfo> fails = new ArrayList<>();

        String path = File.separatorChar
                + "excel" + File.separatorChar
                + DateUtil.date2String(new Date(), DateUtil.yyyyMMddHHmmss)
                + File.separatorChar;

        LinkedList<StudentInfo> listStu = new LinkedList<>();
        HashSet<String> lineNames = new HashSet<>();
        HashSet<String> stationName = new HashSet<>();
        File file = MultFileUtils.save(attach, filePath + path);
        ExcelReader reader = ExcelUtil.getReader(file);
        List<Map<String, Object>> excel = reader.readAll();
        if(excel != null && excel.size() > 0){
            for (Map<String, Object> map : excel) {
                StudentInfo info =
                        new StudentInfo(map);
                listStu.add(info);
                lineNames.add(info.getCompanyLineName().trim());
                stationName.add(info.getUpBusStationName().trim());
                stationName.add(info.getOffBusStationName().trim());
            }
            // 获得站点id
            QueryWrapper<InfoBusStationEntity> busStationEntityQueryWrapper = new QueryWrapper<>();
            busStationEntityQueryWrapper.in("bus_station_name", stationName);
            busStationEntityQueryWrapper.in("school_id", schoolId);
            List<InfoBusStationEntity> busStationEntities = busStationService.list(busStationEntityQueryWrapper);
            Map<String, Long> statMap = busStationEntities.stream()
                    .collect(Collectors.toMap(InfoBusStationEntity::getBusStationName,
                            InfoBusStationEntity::getBusStationId));


            //获得线路id
            QueryWrapper<InfoCompanyLineEntity> companyLineEntityQueryWrapper = new QueryWrapper<>();
            companyLineEntityQueryWrapper.in("company_line_name", lineNames);
            companyLineEntityQueryWrapper.in("school_id", schoolId);
            List<InfoCompanyLineEntity> companyLineEntities = companyLineService.list(companyLineEntityQueryWrapper);
            Map<String, List<InfoCompanyLineEntity>> stringListMap =
                    companyLineEntities.stream().collect(Collectors.groupingBy(InfoCompanyLineEntity::getDirection));
            Map<String, Map<String, Long>> compLine = new HashMap<>();
            stringListMap.forEach((s, infoCs) -> {
                if (!compLine.containsKey(s)) {
                    compLine.put(s, new HashMap<>());
                }
                Map<String, Long> longMap = infoCs.stream()
                        .collect(Collectors.toMap(InfoCompanyLineEntity::getCompanyLineName,
                                InfoCompanyLineEntity::getCompanyLineId));
                compLine.get(s).putAll(longMap);
            });


            for (int i = 0; i < listStu.size(); i++) {
                StudentInfo info = listStu.get(i);
                try {
                    ValidatorUtils.validateEntity(info);
                    saveOneStudent(schoolId, classId, statMap, compLine, info);
                    succ += 1;
                } catch (Exception e) {
                    fail += 1;
                    fails.add(info);
                    logger.error("当前文件行:" + i + "出错!", e);
                }
            }
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("success", succ);
        map.put("fail", fail);
        map.put("failList", fails);
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOneStudent(Long schoolId, Long classId, Map<String, Long> statMap, Map<String, Map<String, Long>> compLine, StudentInfo info) {
        PeopleBasicFactsEntity guardianBasic = new PeopleBasicFactsEntity();
        guardianBasic.setIdNo(info.getGuardianIdNo());
        guardianBasic.setFullName(info.getGuardianName());
        guardianBasic.setCategory("监护人");

        guardianBasic = factsService.saveOrUpdateForIM(guardianBasic);

        GuardianEntity guardianEntity = new GuardianEntity();
        guardianEntity.setBasicId(guardianBasic.getId());
        guardianEntity.setSeqNumber(1);
        guardianEntity.setMobileNumber(info.getMobileNumber());
        guardianEntity.setRelationStudent(info.getRelationStudent());

        AdminUser adminUser = adminUserService.queryByLoginName(guardianEntity.getMobileNumber());
        if (null == adminUser) {
            AdminRole adminRole = adminRoleService.getOne(new QueryWrapper<AdminRole>().eq("role_code","pa"));
            AdminUserFrom guardian = new AdminUserFrom();
            guardian.setUserName(guardianBasic.getFullName());
            guardian.setLoginName(guardianEntity.getMobileNumber());
            guardian.setPassword(guardianEntity.getMobileNumber());
            if(adminRole != null){
                guardian.setRoleIdList(Arrays.asList(new Long[]{adminRole.getRoleId()}));
            }
            guardian.setMobile(guardianEntity.getMobileNumber());
            adminUserService.addUser(guardian, getUserId());
        }

        guardianEntity.setLoginUserId(adminUser.getUserId());

        UpdateWrapper<GuardianEntity> guardianEntityUpdateWrapper = new UpdateWrapper<>();
        guardianEntityUpdateWrapper.eq("basic_id", guardianEntity.getBasicId());
        QueryWrapper<GuardianEntity> guardianEntityQueryWrapper = new QueryWrapper<>();
        guardianEntityQueryWrapper.eq("basic_id", guardianEntity.getBasicId());
        GuardianEntity one = guardianService.getOne(guardianEntityQueryWrapper);
        if (null != one) {
            guardianEntity.setModifyDt(new Date());
            guardianEntity.setModifyUserId(getUserId());
        } else {
            guardianEntity.setCreateDt(new Date());
            guardianEntity.setCreateUserId(getUserId());
        }
        guardianService.saveOrUpdate(guardianEntity, guardianEntityUpdateWrapper);
        guardianEntity = guardianService.getOne(new QueryWrapper<>(guardianEntity));


        //学生基本信息存储
        PeopleBasicFactsEntity studentBasic = new PeopleBasicFactsEntity();
        studentBasic.setFullName(info.getFullName());
        studentBasic.setIdNo(info.getIdNo());
        studentBasic.setSex(info.getSex().trim().equals("男") ? 0 + "" : 1 + "");
        studentBasic.setAge(info.getAge());
        studentBasic.setCategory("学生");
        studentBasic = factsService.saveOrUpdateForIM(studentBasic);
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setSchoolId(schoolId);
        studentEntity.setGuardianId(guardianEntity.getId());
        studentEntity.setClassesId(classId);
        studentEntity.setBasicId(studentBasic.getId());


        UpdateWrapper<StudentEntity> studentEntityUpdateWrapper = new UpdateWrapper<>();
        studentEntityUpdateWrapper.eq("basic_id", studentEntity.getBasicId());
        QueryWrapper<StudentEntity> studentEntityQueryWrapper = new QueryWrapper<>();
        studentEntityQueryWrapper.eq("basic_id", studentEntity.getBasicId());
        StudentEntity selectOne = this.baseMapper.selectOne(studentEntityQueryWrapper);
        if (null != selectOne) {
            studentEntity.setModifyUserId(getUserId());
            studentEntity.setModifyDt(new Date());
        } else {
            studentEntity.setCreateUserId(getUserId());
            studentEntity.setCreateDt(new Date());
        }
        saveOrUpdate(studentEntity, studentEntityUpdateWrapper);
        studentEntity = getOne(new QueryWrapper<>(studentEntity));

        //重写学生id
        guardianEntity.setStudentId(studentEntity.getId());
        guardianService.updateById(guardianEntity);

        StudentLineSeatEntity lineSeatEntity = new StudentLineSeatEntity();
        lineSeatEntity.setStudentId(studentEntity.getId());
        lineSeatEntity.setSeatNo(Long.parseLong(info.getSeatNo().toString()));

        lineSeatEntity.setUpCompanyLineId(compLine.get("1").get(info.getCompanyLineName()));
        lineSeatEntity.setOffCompanyLineId(compLine.get("2").get(info.getCompanyLineName()));
        lineSeatEntity.setUpStationId(statMap.get(info.getUpBusStationName()));
        lineSeatEntity.setOffStationId(statMap.get(info.getOffBusStationName()));

        lineSeatEntity.setDefaultUpStationId(statMap.get(info.getUpBusStationName()));
        lineSeatEntity.setDefaultOffStationId(statMap.get(info.getOffBusStationName()));

        UpdateWrapper<StudentLineSeatEntity> studentLineSeatEntityUpdateWrapper = new UpdateWrapper<>();
        studentLineSeatEntityUpdateWrapper.eq("student_id", lineSeatEntity.getStudentId());
        QueryWrapper<StudentLineSeatEntity> studentLineSeatEntityQueryWrapper = new QueryWrapper<>();
        studentLineSeatEntityQueryWrapper.eq("student_id", lineSeatEntity.getStudentId());
        StudentLineSeatEntity seatServiceOne = studentLineSeatService.getOne(studentLineSeatEntityQueryWrapper);
        if (null != seatServiceOne) {
            lineSeatEntity.setModifyDt(new Date());
            lineSeatEntity.setModifyUserId(getUserId());
        } else {
            lineSeatEntity.setCreateDt(new Date());
            lineSeatEntity.setCreateUserId(getUserId());
        }

        studentLineSeatService.saveOrUpdate(lineSeatEntity, studentLineSeatEntityUpdateWrapper);
    }

    @Override
    public HashMap<String, Object> importCompress(MultipartFile attach, String type) {
        HashMap<String, Object> map = new HashMap<>();
        String destDirPath = File.separatorChar
                + "photo" + File.separatorChar
                + "take" + File.separatorChar
                + DateUtil.date2String(new Date(), DateUtil.yyyyMMddHHmmss)
                + File.separatorChar;
        File outFile = new File(filePath + destDirPath);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        int success = 0;
        int fail = 0;
        ArrayList<String> failList = new ArrayList<>();
        try {
            File file = MultFileUtils.save(attach, filePath + destDirPath);
            String extension = FilenameUtils.getExtension(file.getName());
            if (extension.equals("rar")) {
                CompressTools.unrar(file, filePath + destDirPath);
            } else if (type.equals("zip")) {
                CompressTools.unzip(file, filePath + destDirPath);
            } else {
                return null;
            }
            //解压完成,读取路径和文件名
            Collection<File> files = FileUtils.listFiles(outFile, new String[]{"jpg", "png"}, true);

            for (File file1 : files) {
                String baseName = FilenameUtils.getBaseName(file1.getAbsolutePath());
                int length = baseName.length();
                String idNo = baseName.substring(length - 6, length);
                String name = baseName.substring(0, length - 6);
                PeopleBasicFactsEntity factsEntity = factsService.findByIdAndName(idNo, name);
                if (factsEntity != null) {

                    String absolutePath = file1.getAbsolutePath();
                    factsEntity.setTakePhoto(absolutePath.substring(absolutePath.indexOf(destDirPath), absolutePath.length()));
                    factsEntity.setModifyUserId(getUserId());
                    factsEntity.setModifyDt(new Date());
                    factsService.updateById(factsEntity);
                    success += 1;
                } else {
                    failList.add(baseName);
                    fail += 1;
                }
            }
            map.put("success", success);
            map.put("fail", fail);
            map.put("failList", failList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    @Override
    public StudentInfoDto getStudentInfoById(Long id) {
        QueryWrapper<StudentInfoDto> wrapper = new QueryWrapper<>();
        wrapper.eq("ins.id", id);
        return studentInfoDao.query(wrapper);
    }

    @Override
    public void updateStudentInfo(StudentInfoDto student) {
        Long seatNo = student.getSeatNo();
        Long upStationId = student.getUpStationId();
        Long offStationId = student.getOffStationId();
        StudentLineSeatEntity studentLineSeatEntity = new StudentLineSeatEntity();
        studentLineSeatEntity.setStudentId(student.getId());
        studentLineSeatEntity.setIsDeleted("0");
        studentLineSeatEntity.setUpCompanyLineId(student.getUpCompanyLineId());


        StudentLineSeatEntity one = studentLineSeatService.getOne(new QueryWrapper<>(studentLineSeatEntity));
        one.setSeatNo(seatNo);
        one.setDefaultUpStationId(upStationId);
        one.setDefaultOffStationId(offStationId);
        studentLineSeatService.updateById(one);
    }


}