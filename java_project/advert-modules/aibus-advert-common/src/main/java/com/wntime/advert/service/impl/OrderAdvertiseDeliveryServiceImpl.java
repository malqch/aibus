package com.wntime.advert.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wntime.advert.constant.AdvertiseConstant;
import com.wntime.advert.entity.*;
import com.wntime.advert.service.*;
import com.wntime.advert.util.AdvertiseNoUtil;
import com.wntime.advert.util.FileHelper;
import com.wntime.advert.util.VideoUtil;
import com.wntime.advert.vo.*;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.validator.Assert;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.entity.AdminUser;
import com.wntime.service.common.service.BusCompanyService;
import com.wntime.service.common.service.CompanyLineService;
import com.wntime.service.common.service.ConfigParamInfoService;
import com.wntime.service.common.vo.ConfigParamVo;
import com.wntime.service.entity.InfoConfigParamEntity;
import com.wntime.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.advert.dao.OrderAdvertiseDeliveryDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;


@Service("orderAdvertiseDeliveryService")
public class OrderAdvertiseDeliveryServiceImpl extends ServiceImpl<OrderAdvertiseDeliveryDao, OrderAdvertiseDeliveryEntity> implements OrderAdvertiseDeliveryService {

    @Autowired
    private OrderDeliveryAreaService orderDeliveryAreaService;

    @Autowired
    private ConfigParamInfoService configParamInfoService;

    @Autowired
    private InfoAdvertiseTargetService advertiseTargetService;

    @Autowired
    private OrderDeliveryTargetService orderDeliveryTargetService;

    @Autowired
    private InfoAdvertisePositionService advertisePositionService;

    @Autowired
    private OrderAdvertiseAttachService orderAdvertiseAttachService;

    @Autowired
    private InfoAdvertiseTargetService infoAdvertiseTargetService;

    @Autowired
    private LogCheckItemService logCheckItemService;

    @Autowired
    private CompanyLineService companyLineService;

    @Autowired
    private BusCompanyService busCompanyService;

    @Autowired
    private FileHelper fileHelper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderAdvertiseDeliveryEntity> page = this.page(
                new Query<OrderAdvertiseDeliveryEntity>().getPage(params),
                new QueryWrapper<OrderAdvertiseDeliveryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<AdvertiseCardVo> list(String advertiseNo, String group, String code, AdminUser user) {

        Optional<ConfigParamVo> configParamVo = configParamInfoService.queryConfigByGroupCode(group, code);
        if (!configParamVo.isPresent()) {
            throw new RRException("tab页无对应的信息");
        }
        List<Integer> statusList = Arrays.asList(configParamVo.get().getParamChar().split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());

        //每个投放用户只能看自己投的广告
        Long userId = user.getUserId();
        //审核者和admin可以看所有的广告
        if (group.equals("advertiseAuditTab") || userId == 1) {
            userId = null;
        }
        List<AdvertiseCardVo> list = getBaseMapper().list(advertiseNo, statusList, userId,group);
        for (AdvertiseCardVo advertiseVo : list) {
            int checkStatus = advertiseVo.getCheckStatus();
            if (checkStatus == AdvertiseConstant.FAIL) {
                advertiseVo.setCheckItemList(logCheckItemService.listByAdvertise(advertiseVo.getAdvertiseDeliveryId()));
            }
            //投放中与投放完有计时
            else if (checkStatus == AdvertiseConstant.DELIVERY || checkStatus == AdvertiseConstant.OFFLINE) {
                Timestamp begin = advertiseVo.getDeliveryBegin();
                Timestamp end = advertiseVo.getDeliveryEnd();
                int totalTime = (int) ((end.getTime() - begin.getTime()) / 1000);
                Date now = new Date();
                int useTime = 0;
                if(now.after(end)){
                    useTime = totalTime;
                }else{
                    if(advertiseVo.getCheckStatus() == AdvertiseConstant.OFFLINE) {
                        useTime = totalTime;
                    }else{
                        useTime = (int) ((new Date().getTime() - begin.getTime()) / 1000);
                        if (useTime < 0) useTime = 0;
                    }
                }
                advertiseVo.setTotalTime(totalTime);
                advertiseVo.setUseTime(useTime);
                advertiseVo.setRemainingTime(formatSecond(totalTime  - useTime));
            }
            if(advertiseVo.getIsInterrupt() == AdvertiseConstant.IS_INTERRUPT && StringUtils.isNotEmpty(advertiseVo.getInterruptNotice())){
                advertiseVo.setDeviceName(getBaseMapper().getBaseDeviceName());
            }
        }
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveVo(AdvertiseVo advertiseVo, MultipartFile[] rectangleFiles, MultipartFile[] squareFiles, Long userId) {


        advertiseVo.setAdvertiseNo(AdvertiseNoUtil.generateNo(userId));
        advertiseVo.setAdvertiseDeliveryId(IdWorker.getId());
        Long advertiseId = advertiseVo.getAdvertiseDeliveryId();
        advertiseVo.setCreatedBy(userId);
        advertiseVo.setCreatedDate(DateUtils.getTimestamp());

        checkAndSaveAdvertiseBaseInfo(advertiseVo,userId);

        save(advertiseVo);

        //保存附件
        //校验文件列表
        checkAndSaveAttachList(advertiseVo, rectangleFiles, squareFiles, userId, advertiseId);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateVo(AdvertiseVo advertiseVo, MultipartFile[] rectangleFiles, MultipartFile[] squareFiles, Long userId){

        Long advertiseId = advertiseVo.getAdvertiseDeliveryId();
        //清除审核记录
        advertiseVo.setCheckSuggest("");
        logCheckItemService.deleteBatchByAdvertiseId(advertiseId);

        advertiseVo.setModifiedBy(userId);
        advertiseVo.setModifiedDate(DateUtils.getTimestamp());

        //清除基础信息
        orderDeliveryTargetService.deleteBatchByAdvertiseId(advertiseId);
        orderDeliveryAreaService.deleteBatchByAdvertiseId(advertiseId);
        orderAdvertiseAttachService.deleteBatchByAdvertiseId(advertiseId);

        checkAndSaveAdvertiseBaseInfo(advertiseVo,userId);

        getBaseMapper().updateById(advertiseVo);

        //保存附件
        //校验文件列表
        checkAndUpdateAttachList(advertiseVo, rectangleFiles, squareFiles, userId, advertiseId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveInterruptVo(AdvertiseVo advertiseVo, MultipartFile[] rectangleFiles, MultipartFile[] squareFiles, Long userId) {

        advertiseVo.setAdvertiseNo(AdvertiseNoUtil.generateNo(userId));
        advertiseVo.setAdvertiseDeliveryId(IdWorker.getId());
        Long advertiseId = advertiseVo.getAdvertiseDeliveryId();
        advertiseVo.setCreatedBy(userId);
        advertiseVo.setCreatedDate(DateUtils.getTimestamp());

        checkInterrupt(advertiseVo, rectangleFiles, squareFiles, userId);
        save(advertiseVo);

        String interruptNotice = advertiseVo.getInterruptNotice();
        if(StringUtils.isEmpty(interruptNotice)) {

            //保存附件
            //校验文件列表
            checkAndSaveAttachList(advertiseVo, rectangleFiles, squareFiles, userId, advertiseId);
        }
    }

    private void checkAndSaveAttachList(AdvertiseVo advertiseVo, MultipartFile[] rectangleFiles, MultipartFile[] squareFiles, Long userId, Long advertiseId) {
        if(ArrayUtils.isEmpty(rectangleFiles) && ArrayUtils.isEmpty(squareFiles)){
            throw new RRException("缺少广告素材！");
        }else if(ArrayUtils.isEmpty(rectangleFiles)){
            rectangleFiles = new MultipartFile[]{};
        }else if(ArrayUtils.isEmpty(squareFiles)){
            squareFiles = new MultipartFile[]{};
        }
        AdvertisePositionVo rectangleScreen = advertisePositionService.getOneByCode("rectangle");
        AdvertisePositionVo squareScreen = advertisePositionService.getOneByCode("square");
        checkFileList(rectangleFiles, rectangleScreen);
        checkFileList(squareFiles, squareScreen);
        String tmpRelativeDir = fileHelper.getRelativeDir(userId, "tmp_" + advertiseVo.getAdvertiseNo());

        try {
            saveAttachList(advertiseVo, rectangleFiles, squareFiles, userId, advertiseId, rectangleScreen, squareScreen, tmpRelativeDir);

            boolean result = fileHelper.getDiskFile(tmpRelativeDir).renameTo(new File(fileHelper.getAbsoluteFilePath(fileHelper.getRelativeDir(userId, advertiseVo.getAdvertiseNo()))));
            if (!result) {
                throw new RRException("目录重命名失败！");
            }
        } catch (Exception e) {
            //异常回滚时清空文件夹
            fileHelper.deleteDir(tmpRelativeDir);
            fileHelper.deleteDir(fileHelper.getRelativeDir(userId, advertiseVo.getAdvertiseNo()));
            throw new RRException(e.getMessage());
        }
    }

    private void saveAttachList(AdvertiseVo advertiseVo, MultipartFile[] rectangleFiles, MultipartFile[] squareFiles, Long userId, Long advertiseId, AdvertisePositionVo rectangleScreen, AdvertisePositionVo squareScreen, String tmpRelativeDir) {
        for (MultipartFile rectangleFile : rectangleFiles) {
            OrderAdvertiseAttachEntity advertiseAttachEntity = new OrderAdvertiseAttachEntity();
            //校验并保存文件
            String newFileName = saveAndCheckFile(rectangleFile, tmpRelativeDir, rectangleScreen, advertiseAttachEntity);
            String attachLink = fileHelper.getRelativeDir(userId, advertiseVo.getAdvertiseNo()) + "/" + newFileName;

            advertiseAttachEntity.setAdvertiseDeliveryId(advertiseId);
            advertiseAttachEntity.setAdvertisePositionId(rectangleScreen.getAdvertisePositionId());
            int fileType = getFileType(rectangleFile, rectangleScreen);
            if (fileType != 0) {
                throw new RRException("长条屏文件只能为图片!");
            }
            advertiseAttachEntity.setAttachType(fileType);
            advertiseAttachEntity.setAttachLink(attachLink);
            advertiseAttachEntity.setCreatedDate(DateUtils.getTimestamp());
            orderAdvertiseAttachService.save(advertiseAttachEntity);
        }

        for (MultipartFile squareFile : squareFiles) {
            OrderAdvertiseAttachEntity advertiseAttachEntity = new OrderAdvertiseAttachEntity();
            //校验并保存文件
            String newFileName = saveAndCheckFile(squareFile, tmpRelativeDir, squareScreen, advertiseAttachEntity);
            String attachLink = fileHelper.getRelativeDir(userId, advertiseVo.getAdvertiseNo()) + "/" + newFileName;

            advertiseAttachEntity.setAdvertiseDeliveryId(advertiseId);
            advertiseAttachEntity.setAdvertisePositionId(squareScreen.getAdvertisePositionId());
            advertiseAttachEntity.setAttachType(getFileType(squareFile, squareScreen));
            advertiseAttachEntity.setAttachLink(attachLink);
            advertiseAttachEntity.setCreatedDate(DateUtils.getTimestamp());
            orderAdvertiseAttachService.save(advertiseAttachEntity);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateInterruptVo(AdvertiseVo advertiseVo, MultipartFile[] rectangleFiles, MultipartFile[] squareFiles, Long userId) {
        Long advertiseId = advertiseVo.getAdvertiseDeliveryId();
        //清除审核记录
        advertiseVo.setCheckSuggest("");
        logCheckItemService.deleteBatchByAdvertiseId(advertiseId);

        advertiseVo.setModifiedBy(userId);
        advertiseVo.setModifiedDate(DateUtils.getTimestamp());

        //清除基础信息
        orderDeliveryTargetService.deleteBatchByAdvertiseId(advertiseId);
        orderDeliveryAreaService.deleteBatchByAdvertiseId(advertiseId);
        orderAdvertiseAttachService.deleteBatchByAdvertiseId(advertiseId);

        checkInterrupt(advertiseVo, rectangleFiles, squareFiles, userId);
        getBaseMapper().updateById(advertiseVo);

        String interruptNotice = advertiseVo.getInterruptNotice();
        if(StringUtils.isEmpty(interruptNotice)) {

            //保存附件
            //校验文件列表
            checkAndUpdateAttachList(advertiseVo, rectangleFiles, squareFiles, userId, advertiseId);
        } else{
            fileHelper.deleteDir(fileHelper.getRelativeDir(userId, advertiseVo.getAdvertiseNo()));
        }
    }

    private void checkInterrupt(AdvertiseVo advertiseVo, MultipartFile[] rectangleFiles, MultipartFile[] squareFiles, Long userId) {
        String interruptNotice = advertiseVo.getInterruptNotice();
        if(advertiseVo.getIsInterrupt() != 1){
            throw new RRException("该广告不是插播广告!");
        }
        if(StringUtils.isEmpty(interruptNotice) && ArrayUtils.isEmpty(rectangleFiles) && ArrayUtils.isEmpty(squareFiles)){
            throw new RRException("插播通知和广告素材必须有一个！");
        }
        if(StringUtils.isNotEmpty(interruptNotice) && (ArrayUtils.isNotEmpty(rectangleFiles) || ArrayUtils.isNotEmpty(squareFiles))){
            throw new RRException("插播通知和广告素材只能有一个！");
        }
        checkAndSaveAdvertiseBaseInfo(advertiseVo,userId);
    }

    private void checkAndUpdateAttachList(AdvertiseVo advertiseVo, MultipartFile[] rectangleFiles, MultipartFile[] squareFiles, Long userId, Long advertiseId) {
        if(ArrayUtils.isEmpty(rectangleFiles) && ArrayUtils.isEmpty(squareFiles)){
            throw new RRException("缺少广告素材！");
        }else if(ArrayUtils.isEmpty(rectangleFiles)){
            rectangleFiles = new MultipartFile[]{};
        }else if(ArrayUtils.isEmpty(squareFiles)){
            squareFiles = new MultipartFile[]{};
        }
        AdvertisePositionVo rectangleScreen = advertisePositionService.getOneByCode("rectangle");
        AdvertisePositionVo squareScreen = advertisePositionService.getOneByCode("square");
        checkFileList(rectangleFiles, rectangleScreen);
        checkFileList(squareFiles, squareScreen);
        String tmpRelativeDir = fileHelper.getRelativeDir(userId, "tmp_" + advertiseVo.getAdvertiseNo());

        try {
            saveAttachList(advertiseVo, rectangleFiles, squareFiles, userId, advertiseId, rectangleScreen, squareScreen, tmpRelativeDir);
            //删除原文件夹，重命名临时文件夹
            boolean deleteResult = fileHelper.deleteDir(fileHelper.getRelativeDir(userId, advertiseVo.getAdvertiseNo()));
            if (!deleteResult) {
                throw new RRException("目录删除失败！");
            }
            boolean renameResult = fileHelper.getDiskFile(tmpRelativeDir).renameTo(new File(fileHelper.getAbsoluteFilePath(fileHelper.getRelativeDir(userId, advertiseVo.getAdvertiseNo()))));
            if (!renameResult) {
                throw new RRException("目录重命名失败！");
            }
        } catch (Exception e) {
            //异常回滚时清空文件夹
            fileHelper.deleteDir(tmpRelativeDir);
            throw new RRException(e.getMessage());
        }
    }

    @Override
    public AdvertiseInfoVo getInterruptVo(Long advertiseId) {
        AdvertiseInfoVo advertiseInfoVo = getBaseMapper().getVoById(advertiseId);
        Assert.isNull(advertiseInfoVo,"没有对应的广告");

        List<OrderAdvertiseAttachEntity> orderAdvertiseAttachList = orderAdvertiseAttachService.listByAdvertiseId(advertiseId);
        Map<Long, List<OrderAdvertiseAttachEntity>> positionAttachList = orderAdvertiseAttachList.stream().collect(Collectors.groupingBy(attach -> attach.getAdvertisePositionId()));
        Map<String,AdvertiseAttachVo> attachList = new LinkedHashMap<>();
        List<String> positionDescList = new ArrayList<>();

        for (Map.Entry<Long, List<OrderAdvertiseAttachEntity>> entry : positionAttachList.entrySet()) {

            InfoAdvertisePositionEntity positionEntity = advertisePositionService.getById(entry.getKey());
            positionDescList.add(positionEntity.getPositionDesc());

            AdvertiseAttachVo advertiseAttachVo = new AdvertiseAttachVo();
            advertiseAttachVo.setAdvertisePositionId(entry.getKey());
            List<OrderAdvertiseAttachEntity> list = entry.getValue();
            List<String> fileList = new ArrayList<>();
            List<String> fileUrlList = new ArrayList<>();
            for (OrderAdvertiseAttachEntity orderAdvertiseAttachEntity : list) {
                advertiseAttachVo.setAdvertiseType(orderAdvertiseAttachEntity.getAttachType());
                String attachLink = orderAdvertiseAttachEntity.getAttachLink();
                fileList.add(attachLink);
                fileUrlList.add(fileHelper.getUrl(attachLink));
            }
            advertiseAttachVo.setFileList(fileList);
            advertiseAttachVo.setFileUrlList(fileUrlList);
            attachList.put(positionEntity.getPositionCode() + "Screen",advertiseAttachVo);
        }
        advertiseInfoVo.setAttachList(attachList);
        advertiseInfoVo.setPositionDescList(positionDescList);

        return advertiseInfoVo;
    }

    private void checkAndSaveAdvertiseBaseInfo(AdvertiseVo advertiseVo, Long userId) {

        Long advertiseId = advertiseVo.getAdvertiseDeliveryId();
        ValidatorUtils.validateEntity(advertiseVo);

        //校验投放时间，投放时间至少是7天后
        Date limitDate = Date.from(LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Timestamp deliveryBegin = advertiseVo.getDeliveryBegin();
        Timestamp deliveryEnd = advertiseVo.getDeliveryEnd();
        if(deliveryBegin.before(limitDate)){
           //todo 测试完后恢复逻辑 throw new RRException("开始投放时间要在七天后！");
        }
        if(deliveryEnd.before(deliveryBegin)){
            throw new RRException("结束投放时间要在开始投放时间之后！");
        }
        Integer isInterrupt = advertiseVo.getIsInterrupt();

        //插播者不需要校验和保存 投放受众人群类型、投放受众人群性别、投放地区
        if(isInterrupt == AdvertiseConstant.IS_INTERRUPT){
            return;
        }

        List<Long> peopleGenderList = advertiseVo.getPeopleGenderList();
        List<Long> peopleAgeList = advertiseVo.getPeopleAgeList();
        // 投放标签可以为空
        //Assert.isEmpty(peopleGenderList, "投放受众人群类型不能为空！");
        //Assert.isEmpty(peopleAgeList, "投放受众人群性别不能为空！");
        if(peopleGenderList == null){
            peopleGenderList = new ArrayList<>();
        }
        if(peopleAgeList == null){
            peopleAgeList = new ArrayList<>();
        }

        Map<Long, InfoAdvertiseTargetEntity> ageTagMap = advertiseTargetService.listByGroup("age")
                .stream().collect(Collectors.toMap(InfoAdvertiseTargetEntity::getAdvertiseTargetId, a -> a));
        Map<Long, InfoAdvertiseTargetEntity> genderTagMap = advertiseTargetService.listByGroup("gender")
                .stream().collect(Collectors.toMap(InfoAdvertiseTargetEntity::getAdvertiseTargetId, a -> a));
        for (Long tagId : peopleAgeList) {
            if (!ageTagMap.containsKey(tagId)) {
                throw new RRException("投放受众人群类型不合法");
            }
        }
        for (Long tagId : peopleGenderList) {
            if (!genderTagMap.containsKey(tagId)) {
                throw new RRException("投放受众人群性别不合法");
            }
        }

        //保存广告的tag
        if(!peopleGenderList.isEmpty()) {
            orderDeliveryTargetService.saveBatch(trans2Target(peopleGenderList, advertiseId));
        }
        if(!peopleAgeList.isEmpty()) {
            orderDeliveryTargetService.saveBatch(trans2Target(peopleAgeList, advertiseId));
        }
        //保存投放地区
        InfoConfigParamEntity advertiseDeliveryType = configParamInfoService.getDetailById(advertiseVo.getAdvertiseDeliveryType());
        List<OrderDeliveryAreaEntity> deliveryAreaList = new ArrayList<>();

        List<Long> userCompanyIdList = busCompanyService.getUserCompanyIdList(userId);
        if(userCompanyIdList.size() != 1){
            throw new RRException("该用户不是广告投放者！");
        }
        //获取用户名下的线路code为xx路的id列表
        Long companyId = userCompanyIdList.get(0);
        switch (advertiseDeliveryType.getParamCode()){
            case AdvertiseConstant.LINE:{
                List<String> deliveryLineCodeList = advertiseVo.getDeliveryLineList();
                deliveryAreaList = deliveryLineCodeList.stream()
                        .flatMap(deliveryLineCode ->
                                companyLineService.queryCompanyLineByCodeAndCompany(deliveryLineCode, companyId).stream())
                        .map(lineId -> {
                            OrderDeliveryAreaEntity deliveryArea = new OrderDeliveryAreaEntity();
                            deliveryArea.setCompanyLineId(lineId);
                            return deliveryArea;
                        }).collect(Collectors.toList());

                break;
            }
            case AdvertiseConstant.STATION:{
                deliveryAreaList = advertiseVo.getDeliveryStationList();
                break;
            }
            default:{
                throw new RRException("广告类型不合法！");
            }
        }
        Assert.isEmpty(deliveryAreaList, "投放区域不能为空！");

        for (OrderDeliveryAreaEntity deliveryAreaEntity : deliveryAreaList) {
            deliveryAreaEntity.setCreatedDate(DateUtils.getTimestamp());
            deliveryAreaEntity.setAdvertiseDeliveryId(advertiseId);
        }
        orderDeliveryAreaService.saveBatch(deliveryAreaList);
    }

    private void checkFileList(MultipartFile[] files, AdvertisePositionVo advertisePositionVo) {

        if(ArrayUtils.isEmpty(files)) return;
        if (files.length > advertisePositionVo.getFileNum()) {
            throw new RRException("文件数量不能超过" + advertisePositionVo.getFileNum() + "个！");
        }
        //校验文件类型
        List<String> fileTypeList = Arrays.stream(files)
                .map(MultipartFile::getOriginalFilename)
                .map(fileName -> fileName.substring(fileName.lastIndexOf(".") + 1))
                .distinct()
                .collect(Collectors.toList());

        if(fileTypeList.size() != 1){
            throw new RRException("单个屏幕的广告素材类型必须保持一致！");
        }
        String fileType = fileTypeList.get(0);
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            //是图片
            if (fileType.equals(advertisePositionVo.getPictureFileType())) {
                //判断图片大小 单位kb
                if (file.getSize() > advertisePositionVo.getPictureFileSize() * 1024) {
                    throw new RRException(fileName + "文件不能超过"+advertisePositionVo.getPictureFileSizeStr()+"！");
                }
            }//是视频
            else if (fileType.equals(advertisePositionVo.getVideoFileType())) {
                //判断视频大小 单位mb
                if (file.getSize() > advertisePositionVo.getVideoFileSize() * 1024 * 1024) {
                    throw new RRException(fileName + "文件不能超过"+advertisePositionVo.getVideoFileSizeStr()+"！");
                }
            } else {
                throw new RRException("文件格式不符！图片类型为" + advertisePositionVo.getPictureFileType()+"，视频格式为"+ advertisePositionVo.getVideoFileType());
            }
        }

    }

    private int getFileType(MultipartFile file,AdvertisePositionVo advertisePositionVo){
        String fileName = file.getOriginalFilename();
        //判断文件类型
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);

        //是图片
        if (fileType.equals(advertisePositionVo.getPictureFileType())) {
           return 0;
        }//是视频
        else if (fileType.equals(advertisePositionVo.getVideoFileType())) {
           return 1;
        } else {
            throw new RRException("文件格式不符！");
        }
    }

    private String saveAndCheckFile(MultipartFile file, String relativeDir, AdvertisePositionVo advertisePositionVo, OrderAdvertiseAttachEntity advertiseAttachEntity) {
        //存储文件
        String fileName = fileHelper.saveFile(file, relativeDir);
        if(fileName == null){
            throw new RRException("文件上传失败！");
        }
        //判断文件类型
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        //是图片
        if (fileType.equals(advertisePositionVo.getPictureFileType())) {
            checkPicture(file,fileHelper.getAbsoluteFilePath(relativeDir + "/" + fileName),advertisePositionVo);
            advertiseAttachEntity.setShowTimes(advertisePositionVo.getPictureFileTime().doubleValue());
        }//是视频
        else if (fileType.equals(advertisePositionVo.getVideoFileType())) {
            double duration = checkVideoAndGetDuration(file, fileHelper.getAbsoluteFilePath(relativeDir + "/" + fileName), advertisePositionVo);

            advertiseAttachEntity.setShowTimes(Math.ceil(duration));
        } else {
            throw new RRException("文件格式不符！");
        }
        return fileName;
    }

    private void checkPicture(MultipartFile file,String filePath, AdvertisePositionVo advertisePositionVo) {

        File pictureFile = new File(filePath);
        String fileName = file.getOriginalFilename();
        BufferedImage image = null;
        try {
            image = ImageIO.read(pictureFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RRException("图片读取失败！");
        }

        //如果image=null 表示上传的不是图片格式
        if (image == null) {
            throw new RRException(fileName + "格式不正确");
        }
        //判断图片大小 单位kb
        if (file.getSize() > advertisePositionVo.getPictureFileSize() * 1024) {
            throw new RRException(fileName + "文件不能超过"+advertisePositionVo.getPictureFileSizeStr()+"！");
        }
        //判断图片的分辨率是否合适
        double imagePixelRatio = (double) image.getHeight() / (double) image.getWidth();
        double positionPixelRatio = advertisePositionVo.getPixelHeight() / advertisePositionVo.getPixelWidth();
        if (imagePixelRatio > positionPixelRatio * 1.05 || imagePixelRatio < positionPixelRatio * 0.95) {
            throw new RRException(fileName + "分辨率应和"+advertisePositionVo.getPixelWidth().intValue() +"x"+advertisePositionVo.getPixelHeight().intValue()+"比例相同");
        }
    }
    private double checkVideoAndGetDuration(MultipartFile file,String filePath, AdvertisePositionVo advertisePositionVo) {

        String fileName = file.getOriginalFilename();
        Map<String, Object> videoInfo = VideoUtil.getVideoInfo(filePath);
        Assert.isEmpty(videoInfo,"视频解析失败！");
        int width = (int) videoInfo.get("width");
        int height = (int) videoInfo.get("height");
        //判断视频的分辨率是否合适
        double videoPixelRatio = (double) height / (double) width;
        double positionPixelRatio = advertisePositionVo.getPixelHeight() / advertisePositionVo.getPixelWidth();
        if (videoPixelRatio > positionPixelRatio * 1.05 || videoPixelRatio < positionPixelRatio * 0.95) {
            throw new RRException(fileName + "分辨率应和"+advertisePositionVo.getPixelWidth().intValue() +"x"+advertisePositionVo.getPixelHeight().intValue()+"比例相同");
        }
        double duration = Double.parseDouble((String) videoInfo.get("duration"));
        if(duration > advertisePositionVo.getVideoFileTime()){
            throw new RRException(fileName + "时长超过限制，应小于"+ advertisePositionVo.getVideoFileTimeStr());
        }
        return duration;
    }

    @Override
    public AdvertiseInfoVo getVo(Long advertiseId) {

        AdvertiseInfoVo advertiseInfoVo = getBaseMapper().getVoById(advertiseId);
        Assert.isNull(advertiseInfoVo,"没有对应的广告");

        switch (advertiseInfoVo.getAdvertiseDeliveryTypeCode()) {
            case AdvertiseConstant.LINE: {
                advertiseInfoVo.setOutDeliveryLineList(orderDeliveryAreaService.listLineCodeByAdvertise(advertiseId).stream().map(DeliveryLineVo::getCompanyLineCode).collect(Collectors.toList()));
                advertiseInfoVo.setDeliveryStationList(new ArrayList<>());
                break;
            }
            case AdvertiseConstant.STATION: {
                advertiseInfoVo.setOutDeliveryLineList(new ArrayList<>());
                advertiseInfoVo.setDeliveryStationList(orderDeliveryAreaService.listByAdvertiseId(advertiseId));
                break;
            }
            default: {
                throw new RRException("广告类型不合法！");
            }
        }

        advertiseInfoVo.setPeopleAgeList(
                orderDeliveryTargetService
                        .listByAdvertiseIdAndGroup(advertiseId,"age")
                        .stream()
                        .map(OrderDeliveryTargetEntity::getAdvertiseTargetId)
                        .collect(Collectors.toList()));
        advertiseInfoVo.setPeopleGenderList(orderDeliveryTargetService
                .listByAdvertiseIdAndGroup(advertiseId,"gender")
                .stream()
                .map(OrderDeliveryTargetEntity::getAdvertiseTargetId)
                .collect(Collectors.toList()));


        List<OrderAdvertiseAttachEntity> orderAdvertiseAttachList = orderAdvertiseAttachService.listByAdvertiseId(advertiseId);
        Map<Long, List<OrderAdvertiseAttachEntity>> positionAttachList = orderAdvertiseAttachList.stream().collect(Collectors.groupingBy(attach -> attach.getAdvertisePositionId()));
        Map<String,AdvertiseAttachVo> attachList = new LinkedHashMap<>();
        List<String> positionDescList = new ArrayList<>();

        for (Map.Entry<Long, List<OrderAdvertiseAttachEntity>> entry : positionAttachList.entrySet()) {

            InfoAdvertisePositionEntity positionEntity = advertisePositionService.getById(entry.getKey());
            positionDescList.add(positionEntity.getPositionDesc());

            AdvertiseAttachVo advertiseAttachVo = new AdvertiseAttachVo();
            advertiseAttachVo.setAdvertisePositionId(entry.getKey());
            List<OrderAdvertiseAttachEntity> list = entry.getValue();
            List<String> fileList = new ArrayList<>();
            List<String> fileUrlList = new ArrayList<>();
            for (OrderAdvertiseAttachEntity orderAdvertiseAttachEntity : list) {
                advertiseAttachVo.setAdvertiseType(orderAdvertiseAttachEntity.getAttachType());
                String attachLink = orderAdvertiseAttachEntity.getAttachLink();
                fileList.add(attachLink);
                fileUrlList.add(fileHelper.getUrl(attachLink));
            }
            advertiseAttachVo.setFileList(fileList);
            advertiseAttachVo.setFileUrlList(fileUrlList);
            attachList.put(positionEntity.getPositionCode() + "Screen",advertiseAttachVo);
        }
        advertiseInfoVo.setAttachList(attachList);
        advertiseInfoVo.setPositionDescList(positionDescList);

        return advertiseInfoVo;
    }

    @Override
    public AdvertiseDetailVo getDetailVo(Long advertiseId) {

        AdvertiseInfoVo advertiseInfoVo = getBaseMapper().getVoById(advertiseId);
        Assert.isNull(advertiseInfoVo,"没有对应的广告");

        AdvertiseDetailVo advertiseDetailVo = new AdvertiseDetailVo();
        advertiseDetailVo.setAdvertiseId(advertiseId);
        advertiseDetailVo.setCheckStatus(advertiseInfoVo.getCheckStatus());
        advertiseDetailVo.setIsInterrupt(advertiseInfoVo.getIsInterrupt());
        advertiseDetailVo.setInterruptNotice(advertiseInfoVo.getInterruptNotice());

        String format = "yyyy-MM-dd HH:mm:ss";
        advertiseDetailVo.setDeliveryDate(DateUtil.date2String(advertiseInfoVo.getDeliveryBegin(), format) + " ~ " + DateUtil.date2String(advertiseInfoVo.getDeliveryEnd(), format));
        if(advertiseDetailVo.getIsInterrupt() == AdvertiseConstant.NOT_INTERRUPT) {

            advertiseDetailVo.setAdvertiseDeliveryTypeName(advertiseInfoVo.getAdvertiseDeliveryTypeName());
            switch (advertiseInfoVo.getAdvertiseDeliveryTypeCode()) {
                case AdvertiseConstant.LINE: {
                    List<DeliveryLineVo> deliveryLineVoList = orderDeliveryAreaService.listLineCodeByAdvertise(advertiseId);
                    advertiseDetailVo.setDeliveryLine(list2Str(deliveryLineVoList, DeliveryLineVo::getCompanyLineCode));
                    advertiseDetailVo.setDeliveryStation("");
                    break;
                }
                case AdvertiseConstant.STATION: {
                    advertiseDetailVo.setDeliveryLine("");
                    List<String> deliveryStationList = orderDeliveryAreaService.listStationByAdvertiseId(advertiseId);
                    advertiseDetailVo.setDeliveryStation(list2Str(deliveryStationList, a -> a));
                    break;
                }
                default: {
                    throw new RRException("广告类型不合法！");
                }
            }
            List<InfoAdvertiseTargetEntity> ageTagList = infoAdvertiseTargetService.listByAdvertiseIdAndGroup(advertiseId, "age");
            advertiseDetailVo.setDeliveryPeopleAge(list2Str(ageTagList, InfoAdvertiseTargetEntity::getAdvertiseTargetName));
            List<InfoAdvertiseTargetEntity> genderTagList = infoAdvertiseTargetService.listByAdvertiseIdAndGroup(advertiseId, "gender");
            advertiseDetailVo.setDeliveryPeopleGender(list2Str(genderTagList, InfoAdvertiseTargetEntity::getAdvertiseTargetName));
        }

        List<OrderAdvertiseAttachEntity> orderAdvertiseAttachList = orderAdvertiseAttachService.listByAdvertiseId(advertiseId);
        List<InfoAdvertisePositionEntity> positionList = advertisePositionService.list();
        Map<Long, List<OrderAdvertiseAttachEntity>> positionAttachMap = orderAdvertiseAttachList.stream().collect(Collectors.groupingBy(attach -> attach.getAdvertisePositionId()));
        Map<String,AdvertiseAttachVo> attachList = new LinkedHashMap<>();
        StringBuilder positionDesc = new StringBuilder();
        int i = 1;
        for (InfoAdvertisePositionEntity positionEntity : positionList) {

            AdvertiseAttachVo advertiseAttachVo = new AdvertiseAttachVo();
            advertiseAttachVo.setAdvertisePositionId(positionEntity.getAdvertisePositionId());
            List<OrderAdvertiseAttachEntity> list = positionAttachMap.get(positionEntity.getAdvertisePositionId());
            List<String> fileList = new ArrayList<>();
            List<String> fileUrlList = new ArrayList<>();
            if(list != null) {
                positionDesc.append( i +". " +positionEntity.getPositionDesc());
                positionDesc.append(";  ");
                i++;
                for (OrderAdvertiseAttachEntity orderAdvertiseAttachEntity : list) {
                    advertiseAttachVo.setAdvertiseType(orderAdvertiseAttachEntity.getAttachType());
                    String attachLink = orderAdvertiseAttachEntity.getAttachLink();
                    fileList.add(attachLink);
                    fileUrlList.add(fileHelper.getUrl(attachLink));
                }
            }
            //如果是插播通知，所有屏都需要播放
            else if(StringUtils.isNotEmpty(advertiseDetailVo.getInterruptNotice())){
                positionDesc.append( i +". " +positionEntity.getPositionDesc());
                positionDesc.append(";  ");
                i++;
            }
            advertiseAttachVo.setFileList(fileList);
            advertiseAttachVo.setFileUrlList(fileUrlList);
            attachList.put(positionEntity.getPositionCode() + "Screen",advertiseAttachVo);
        }
        advertiseDetailVo.setAttachList(attachList);
        advertiseDetailVo.setPositionDesc(positionDesc.toString());

        return advertiseDetailVo;
    }

    @Override
    public void audit(AuditVo auditVo,Long userId) {
        Long advertiseId = auditVo.getAdvertiseId();
        OrderAdvertiseDeliveryEntity advertiseDeliveryEntity = getById(advertiseId);
        Assert.isNull(advertiseDeliveryEntity,"广告不存在!");
        int auditResult = auditVo.getAuditResult();
        if(auditResult == 1){
            advertiseDeliveryEntity.setCheckStatus(AdvertiseConstant.UNCONFIRMED);
        }else{
            advertiseDeliveryEntity.setCheckStatus(AdvertiseConstant.FAIL);
            advertiseDeliveryEntity.setCheckSuggest(auditVo.getCheckSuggest());

            List<Long> checkItemList = auditVo.getCheckItemList();
            Assert.isNull(checkItemList,"审核不通过时,违规选项不能为空!");
            Assert.isEmpty(checkItemList,"审核不通过时,违规选项不能为空!");
            List<LogCheckItemEntity> logCheckItemEntityList = checkItemList.stream().map(checkItem -> {
                LogCheckItemEntity logCheckItemEntity = new LogCheckItemEntity();
                logCheckItemEntity.setAdvertiseDeliveryId(advertiseId);
                logCheckItemEntity.setCheckItem(checkItem);
                logCheckItemEntity.setCreatedDate(DateUtils.getTimestamp());
                return logCheckItemEntity;
            }).collect(Collectors.toList());
            logCheckItemService.saveBatch(logCheckItemEntityList);
        }
        advertiseDeliveryEntity.setModifiedBy(userId);
        advertiseDeliveryEntity.setModifiedDate(DateUtils.getTimestamp());

        updateById(advertiseDeliveryEntity);
    }

    @Transactional
    @Override
    public void delete(Long advertiseId,Long userId) {
        OrderAdvertiseDeliveryEntity advertiseDeliveryEntity = getById(advertiseId);
        getBaseMapper().deleteById(advertiseId);
        orderDeliveryTargetService.deleteBatchByAdvertiseId(advertiseId);
        orderDeliveryAreaService.deleteBatchByAdvertiseId(advertiseId);
        orderAdvertiseAttachService.deleteBatchByAdvertiseId(advertiseId);
        //清空广告文件目录
        fileHelper.deleteDir(fileHelper.getRelativeDir(userId,advertiseDeliveryEntity.getAdvertiseNo()));
    }

    private List<OrderDeliveryTargetEntity> trans2Target(List<Long> idList, Long advertiseId) {
        return idList
                .stream()
                .map(id -> {
                    OrderDeliveryTargetEntity orderDeliveryTargetEntity = new OrderDeliveryTargetEntity();
                    orderDeliveryTargetEntity.setAdvertiseDeliveryId(advertiseId);
                    orderDeliveryTargetEntity.setAdvertiseTargetId(id);
                    orderDeliveryTargetEntity.setCreatedDate(DateUtils.getTimestamp());
                    return orderDeliveryTargetEntity;
                })
                .collect(Collectors.toList());
    }

    private static <T> String list2Str(List<T> list, Function<T,String> getStrFunc){
        return list2Str(list,getStrFunc,",");
    }
    private static <T> String list2Str(List<T> list, Function<T,String> getStrFunc,String seperator){
        StringBuilder sb = new StringBuilder();
        for (T t : list) {
            if(t == null) continue;
            String str = getStrFunc.apply(t);
            sb.append(str);
            sb.append(seperator);
        }
        if(sb.length() >0) {
            return sb.substring(0, sb.length() - 1);
        }
        return "";
    }

    /**
     *
     * @param second 要转换的秒数
     * @return
     */
    public static String formatSecond(int second) {

        if(second == 0) return "无";

        StringBuilder sb = new StringBuilder();
        long days = second / (60 * 60 * 24);
        long hours = (second % (60 * 60 * 24)) / (60 * 60);
        long minutes = (second % (60 * 60)) / (60);
        long seconds = second % 60;
        if(days != 0){
            sb.append(days);
            sb.append("天");
        }
        if(hours != 0){
            sb.append(hours);
            sb.append("小时");
        }
        if(minutes != 0){
            sb.append(minutes);
            sb.append("分钟");
        }
        if(seconds != 0){
            sb.append(seconds);
            sb.append("秒");
        }
        return sb.toString();
    }
}