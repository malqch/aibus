package com.wntime.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.*;
import com.wntime.common.validator.Assert;
import com.wntime.customer.dao.InfoBusCompanyDao;
import com.wntime.customer.dao.InfoCompanyLineDao;
import com.wntime.customer.dao.InfoLineStationDao;
import com.wntime.customer.dao.PlanBusServiceDao;
import com.wntime.customer.entity.InfoBusStationEntity;
import com.wntime.customer.entity.InfoCompanyLineEntity;
import com.wntime.customer.entity.InfoLineStationEntity;
import com.wntime.customer.entity.PlanBusServiceEntity;
import com.wntime.customer.region.BusDriveStationInfo;
import com.wntime.customer.region.LineBus;
import com.wntime.customer.repo.BusDriveStationInfoRepository;
import com.wntime.customer.repo.LineBusRepository;
import com.wntime.customer.service.InfoBusStationService;
import com.wntime.customer.service.InfoCompanyLineService;
import com.wntime.customer.service.InfoLineStationService;
import com.wntime.customer.vo.AdvertlineVo;
import com.wntime.customer.vo.ValidCompanyVO;
import com.wntime.datasource.annotation.DataSource;
import com.wntime.service.common.service.BusInfoService;
import com.wntime.service.common.service.CompanyLineService;
import com.wntime.service.common.service.ConfigParamService;
import com.wntime.service.common.vo.*;
import com.wntime.util.DistanceUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.wntime.common.utils.ShiroUtils.getUserId;


@Service("infoCompanyLineService")
public class InfoCompanyLineServiceImpl extends ServiceImpl<InfoCompanyLineDao, InfoCompanyLineEntity> implements InfoCompanyLineService, CompanyLineService {

    @Resource
    private LineBusRepository lineBusRepository;
    @Resource
    private InfoBusCompanyDao infoBusCompanyDao;
    @Autowired
    private InfoBusStationService infoBusStationService;
    @Autowired
    private InfoLineStationService infoLineStationService;
    @Autowired
    private ConfigParamService configParamService;
    @Resource
    private InfoLineStationDao infoLineStationDao;
    @Resource
    private BusInfoService busInfoService;
    @Resource
    private BusDriveStationInfoRepository busDriveStationInfoRepository;
    @Resource
    private PlanBusServiceDao planBusServiceDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InfoCompanyLineEntity> page = this.page(
                new Query<InfoCompanyLineEntity>().getPage(params),
                new QueryWrapper<InfoCompanyLineEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 关联删除检查
     * @param id
     */
    void deletePermitCheck(Long id){
        // 公交线路营运计划检查
        QueryWrapper<PlanBusServiceEntity> QueryWrapper = new QueryWrapper<PlanBusServiceEntity>()
                .eq("is_deleted", 0)
                .eq("company_line_id",id);

        List<PlanBusServiceEntity> getList = planBusServiceDao.selectList(QueryWrapper);
        if(getList != null && getList.size() > 0){
            throw new RRException("该公交线路存在关联[车辆运营]配置，不可删除。");
        }
    }

    @Override
    public void delById(Long id, Long userId) {
        deletePermitCheck(id);
        InfoCompanyLineEntity infoCompanyLineEntity = new InfoCompanyLineEntity();
        infoCompanyLineEntity.setCompanyLineId(id);
        infoCompanyLineEntity.setIsDeleted(1);
        infoCompanyLineEntity.setModifiedBy(userId);
        infoCompanyLineEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoCompanyLineEntity);
        //删除公交线路和车站关系
        this.baseMapper.deleteLineStation(getById(id).getCompanyLineId());
//        List<LineBus> busList  = lineBusRepository.queryLineBusesByCompanyLineId(infoCompanyLineEntity.getCompanyLineId());
//        if (busList != null && !busList.isEmpty()) {
//            for (LineBus lineBus : busList) {
//                lineBus.setCompanyLineCode("");
//                lineBus.setCompanyLineName("");
//                lineBus.setCompanyLineId(null);
//                lineBusRepository.save(lineBus);
//            }
//        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

    @Override
    public InfoCompanyLineEntity getDetailById(Long id) {
        InfoCompanyLineEntity infoCompanyLineEntity = this.baseMapper.getDetailById(id);

        List<InfoLineStationEntity> lineStationList = infoLineStationService.list(new QueryWrapper<InfoLineStationEntity>()
                .eq("company_line_id", infoCompanyLineEntity.getCompanyLineId())
                .eq("is_deleted", 0)
                .eq("is_enabled", 1));
        if (lineStationList != null && lineStationList.size() > 0) {
            Long[] ids = new Long[lineStationList.size()];
            for (int i = 0; i < lineStationList.size(); i++) {
                ids[i] = lineStationList.get(i).getBusStationId();
            }
            infoCompanyLineEntity.setBusStationIds(ids);
        }
        return infoCompanyLineEntity;
    }

    @Override
    public InfoCompanyLineEntity getDetailInfoById(Long id) {
        return this.baseMapper.getDetailInfoById(id);
    }

    @Override
    public List<InfoCompanyLineEntity> getCompanyLineByCompanyId(Long companyId) {
        return this.baseMapper.getCompanyLineByCompanyId(companyId);
    }

    @Override
    public PageUtils getCompanyLinePageByCompanyId(Map<String, Object> params) {

        int currPage = 1;
        int pageSize = 20;
        if (params.get("page") != null) {
            currPage = Integer.parseInt((String) params.get("page"));
        }
        if (params.get("page") != null) {
            pageSize = Integer.parseInt((String) params.get("limit"));
        }
        Page<InfoCompanyLineEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        if(params.get("companyId") != null && StringUtils.isNotEmpty(String.valueOf(params.get("companyId")))){
            params.put("companyId",Long.parseLong(String.valueOf(params.get("companyId")) ));
        }else{
            params.put("companyId",null);
        }
        return new PageUtils(page.setRecords(getBaseMapper().queryPageListByCompanyId(params)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCompanyLine(InfoCompanyLineEntity infoCompanyLineEntity) {
        // 关联检查
        if(infoCompanyLineEntity.getIsEnabled() == 0){
            deletePermitCheck( infoCompanyLineEntity.getCompanyLineId());
        }
        infoCompanyLineEntity.setModifiedBy(getUserId());
        infoCompanyLineEntity.setModifiedDate(DateUtils.getTimestamp());
        updateById(infoCompanyLineEntity);
        //删除公交线路和车站关系
        this.baseMapper.deleteLineStation(infoCompanyLineEntity.getCompanyLineId());

        // 获取车站类型
        Map<String,Long> configParamMap = new HashedMap();
        Long configId = 0L;
        Optional<ConfigParamVo> optionalConfigParamVo = configParamService.queryConfigByGroupCode("station_type","start");
        if(optionalConfigParamVo.isPresent()){
            configId = optionalConfigParamVo.get().getConfigParamId();
        }

        configParamMap.put("start",configId);
        optionalConfigParamVo = configParamService.queryConfigByGroupCode("station_type","middle");
        if(optionalConfigParamVo.isPresent()){
            configId = optionalConfigParamVo.get().getConfigParamId();
        }
        configParamMap.put("middle",configId);

        optionalConfigParamVo = configParamService.queryConfigByGroupCode("station_type","end");
        if(optionalConfigParamVo.isPresent()){
            configId = optionalConfigParamVo.get().getConfigParamId();
        }
        configParamMap.put("end",configId);
        //保存公交线路和车站关系
        for (int i = 0; i < infoCompanyLineEntity.getBusStationIds().length; i++) {
            InfoLineStationEntity tmp = new InfoLineStationEntity();
            tmp.setCompanyLineId(infoCompanyLineEntity.getCompanyLineId());
            tmp.setBusStationId(infoCompanyLineEntity.getBusStationIds()[i]);
            Assert.isNull(tmp.getBusStationId(),"公交车站id不能为空");
            if(i == 0){
                tmp.setStationType(configParamMap.get("start"));
            }else if(i == infoCompanyLineEntity.getBusStationIds().length -1){
                tmp.setStationType(configParamMap.get("end"));
            }else{
                tmp.setStationType(configParamMap.get("middle"));
            }
            tmp.setStationOrder(i + 1);
            tmp.setIsDeleted(0);
            tmp.setIsEnabled(1);
            tmp.setCreatedBy(getUserId());
            tmp.setCreatedDate(DateUtils.getTimestamp());
            infoLineStationService.save(tmp);
        }
//        try {
//            updateLineBus(infoCompanyLineEntity);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCompanyLineBatch(InfoCompanyLineEntity infoCompanyLineEntity) {
        // 保存车站
        List<Long> busStationIds = new ArrayList<>();
        if(infoCompanyLineEntity.getBusStationList() != null && infoCompanyLineEntity.getBusStationList().size() > 0){

            // 获取车站GPS计算偏差值
            double busStationDeviation = 50;
            Optional<ConfigParamVo> optionalConfigParamVo = configParamService.queryConfigByGroupCode("stationDeviationGroup",
                    "stationDeviation");
            if(optionalConfigParamVo.isPresent()){
                busStationDeviation = optionalConfigParamVo.get().getParamValue();
            }

            for(InfoBusStationEntity infoBusStationEntity :infoCompanyLineEntity.getBusStationList() ){
                QueryWrapper<InfoBusStationEntity> QueryWrapper = new QueryWrapper<InfoBusStationEntity>()
                        .eq("is_deleted", 0)
                        .eq("area_id",infoBusStationEntity.getAreaId())
                        .eq("bus_station_name",infoBusStationEntity.getBusStationName())
                        .eq("bus_station_code",infoBusStationEntity.getBusStationCode());

                // 经纬度比较相差
                Long busStationId= 0L;
                List<InfoBusStationEntity> getList = infoBusStationService.list(QueryWrapper);
                if(getList != null){
                    for(InfoBusStationEntity entity : getList){
                        // 计算GPS距离
                        double distance = DistanceUtil.getShortestDistance(entity.getBusStationLongitude(),entity.getBusStationLatitude(),
                                infoBusStationEntity.getBusStationLongitude(), infoBusStationEntity.getBusStationLatitude());
                        if(distance <= busStationDeviation){
                            busStationId = entity.getBusStationId();
                            break;
                        }
                    }
                }
                if(busStationId == 0L){
                    infoBusStationEntity.setBusStationId(null);
                    infoBusStationEntity.setIsDeleted(0);
                    infoBusStationEntity.setIsEnabled(1);
                    infoBusStationEntity.setCreatedDate(DateUtils.getTimestamp());
                    infoBusStationEntity.setCreatedBy(getUserId());
                    infoBusStationEntity.setBusStationDeviation(new Double(busStationDeviation).intValue());
                    infoBusStationService.save(infoBusStationEntity);
                    busStationIds.add(infoBusStationEntity.getBusStationId());
                }else{
                    busStationIds.add(busStationId);
                }
            }
        }

        // 设置车站ID
        infoCompanyLineEntity.setBusStationIds(busStationIds.toArray(new Long[busStationIds.size()]));
        // 保存线路
        saveCompanyLine(infoCompanyLineEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCompanyLine(InfoCompanyLineEntity infoCompanyLineEntity) {
        infoCompanyLineEntity.setCompanyLineId(null);
        infoCompanyLineEntity.setIsDeleted(0);
        infoCompanyLineEntity.setCreatedBy(getUserId());
        infoCompanyLineEntity.setCreatedDate(DateUtils.getTimestamp());
        save(infoCompanyLineEntity);
        // 获取车站类型
        Map<String,Long> configParamMap = new HashedMap();
        Long configId = 0L;
        Optional<ConfigParamVo> optionalConfigParamVo = configParamService.queryConfigByGroupCode("station_type","start");
        if(optionalConfigParamVo.isPresent()){
            configId = optionalConfigParamVo.get().getConfigParamId();
        }

        configParamMap.put("start",configId);
        optionalConfigParamVo = configParamService.queryConfigByGroupCode("station_type","middle");
        if(optionalConfigParamVo.isPresent()){
            configId = optionalConfigParamVo.get().getConfigParamId();
        }
        configParamMap.put("middle",configId);

        optionalConfigParamVo = configParamService.queryConfigByGroupCode("station_type","end");
        if(optionalConfigParamVo.isPresent()){
            configId = optionalConfigParamVo.get().getConfigParamId();
        }
        configParamMap.put("end",configId);

        //保存公交线路和车站关系
        for (int i = 0; i < infoCompanyLineEntity.getBusStationIds().length; i++) {
            InfoLineStationEntity tmp = new InfoLineStationEntity();
            tmp.setCompanyLineId(infoCompanyLineEntity.getCompanyLineId());
            tmp.setBusStationId(infoCompanyLineEntity.getBusStationIds()[i]);
            Assert.isNull(tmp.getBusStationId(),"公交车站id不能为空");
            if(i == 0){
                tmp.setStationType(configParamMap.get("start"));
            }else if(i == infoCompanyLineEntity.getBusStationIds().length -1){
                tmp.setStationType(configParamMap.get("end"));
            }else{
                tmp.setStationType(configParamMap.get("middle"));
            }

            tmp.setStationOrder(i + 1);
            tmp.setIsDeleted(0);
            tmp.setIsEnabled(1);
            tmp.setCreatedBy(getUserId());
            tmp.setCreatedDate(DateUtils.getTimestamp());
            infoLineStationService.save(tmp);
        }
//        try {
//            updateLineBus(infoCompanyLineEntity);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public List<AdvertlineVo> getCompanyLineCodeByCompanyIdList(List<Long> companyIdList) {
        List<InfoCompanyLineEntity> lineList = getBaseMapper().getCompanyLineCodeByCompanyIdList(companyIdList);

        List<AdvertlineVo> list = new ArrayList<>();
        Map<String, List<InfoCompanyLineEntity>> map = lineList.stream().collect(Collectors.groupingBy(InfoCompanyLineEntity::getCompanyLineCode));
        for (String companyLineCode : map.keySet()) {
            AdvertlineVo advertlineVo = new AdvertlineVo();
            List<Long> lineIdList = map.get(companyLineCode).stream().map(InfoCompanyLineEntity::getCompanyLineId).collect(Collectors.toList());
            advertlineVo.setCompanyLineCode(companyLineCode);
            advertlineVo.setLineIdList(lineIdList);
            list.add(advertlineVo);
        }
        return list;
    }

    private void updateLineBus(InfoCompanyLineEntity infoCompanyLineEntity) {
        List<LineBus> lineBuses = lineBusRepository.queryLineBusesByCompanyLineId(infoCompanyLineEntity.getCompanyLineId());
        if (Constant.Deleted.UNDELETED.getValue() == infoCompanyLineEntity.getIsDeleted() &&
                Constant.Enabled.ENABLE.getValue() == infoCompanyLineEntity.getIsEnabled()) {
            Date firstTime, lastTime, now = new Date();
            if (now.before(infoCompanyLineEntity.getSummerEndTime()) && now.after(infoCompanyLineEntity.getSummerStartTime())) {
                firstTime = new Date(infoCompanyLineEntity.getSummerFirstTime().getTime());
                lastTime = new Date(infoCompanyLineEntity.getSummerLastTime().getTime());
            } else {
                firstTime = new Date(infoCompanyLineEntity.getWinterFirstTime().getTime());
                lastTime = new Date(infoCompanyLineEntity.getWinterLastTime().getTime());
            }
            ValidCompanyVO company = infoBusCompanyDao.queryCompanyAreaName(infoCompanyLineEntity.getCompanyId());

            for (LineBus lineBus : lineBuses) {
                lineBus.setCompanyName(company.getCompanyName());
                lineBus.setCompanyLineCode(infoCompanyLineEntity.getCompanyLineCode());
                lineBus.setCompanyLineName(infoCompanyLineEntity.getCompanyLineName());
                lineBus.setCompanyLineId(infoCompanyLineEntity.getCompanyLineId());

                lineBus.setAreaId(company.getAreaId());
                lineBus.setAreaName(company.getAreaName());

                lineBus.setFirstTime(firstTime);
                lineBus.setLastTime(lastTime);
                lineBusRepository.save(lineBus);
            }
        } else if (Constant.Deleted.DELETED.getValue() == infoCompanyLineEntity.getIsDeleted() ||
                Constant.Enabled.DISABLE.getValue() == infoCompanyLineEntity.getIsEnabled()) {
            for (LineBus lineBus : lineBuses) {
                lineBus.setCompanyLineCode("");
                lineBus.setCompanyLineName("");
                lineBus.setCompanyLineId(null);
                lineBusRepository.save(lineBus);
            }
        }
    }

    @Override
    @DataSource("read")
    public BusLineVo queryBusLineInfo(long busId) {
        return getBaseMapper().queryBusLineInfo(busId);
    }

    @Override
    @DataSource("read")
    public List<BusLineVo> queryCompanyBusLineInfo(long busCompanyId) {
        return getBaseMapper().queryCompanyBusLineInfo(busCompanyId);
    }

    @Override
    public BusLineVo queryBusLine(long busId) {
        Optional<LineBus> optional = lineBusRepository.findById(busId);
        if(optional.isPresent()){
            LineBus lineBus = optional.get();
            BusLineVo vo = new BusLineVo();
            vo.setCompanyLineName(lineBus.getCompanyLineName());
            vo.setCompanyLineCode(lineBus.getCompanyLineCode());
            vo.setCompanyName(lineBus.getCompanyName());
            vo.setAreaName(lineBus.getAreaName());
            vo.setServiceTime(DateUtils.format(lineBus.getFirstTime(), "HH:mm") + " - " + DateUtils.format(lineBus.getLastTime(), "HH:mm"));
            return vo;
        }else {
            return queryBusLineInfo(busId);
        }
    }

    @Override
    public List<BusOperationVo> queryCompanyLines(long companyId, long companyLineId,String keyWord) {
        List<Long> busIds = planBusServiceDao.queryBusIdByCompanyLineId(companyLineId);

       List<BusOperationVo> result=new ArrayList<>();
        if (busIds != null && !busIds.isEmpty()) {
            for (long busId : busIds) {
                Optional<BusDriveStationInfo> busVo = busDriveStationInfoRepository.findById(busId);
                busVo.ifPresent(driveVo -> {
                     if(driveVo.getCompanyLineId()==companyLineId){
                         BusBaseInfoVo busInfo = busInfoService.queryBusInfo(busId);
                             boolean isNull = StringUtils.isEmpty(keyWord);
                             boolean isMatch = StringUtils.isNotEmpty(keyWord) && (busInfo.getVinCode().matches(".*" + keyWord + ".*") || busInfo.getPlateCode().matches(".*" + keyWord + ".*"));
                             if (isNull || isMatch) {
                                 BusOperationVo operation = new BusOperationVo();
                                 operation.setBusId(busId);
                                 operation.setVinCode(busInfo.getVinCode());
                                 operation.setPlateCode(busInfo.getPlateCode());
                                 operation.setBusStatus(busInfo.getStatus());
                                 operation.setStationId(driveVo.getStationId());
                                 operation.setStationStatus(driveVo.getStationStatus());
                                 result.add(operation);
                             }
                     }
                 });
            }
        }
        return result;
    }

    @Override
    public List<BusLineVo> queryCompanyLineList(long companyId) {
        return getBaseMapper().queryCompanyBusLineInfo(companyId);
    }

    @Override
    @DataSource("read")
    public List<LineStationVo> queryCompanyLines() {
        return infoLineStationDao.queryList();
    }

    @Override
    @DataSource("read")
    public List<BusLinePlanServiceVo> queryBusLinePlanServices(Long busId) {
        return planBusServiceDao.queryList(busId);
    }

    @Override
    public Collection<CompanyLineOverviewVo> queryCompanyLineOverview(long companyId) {
        List<CompanyLineStationVo> list = getBaseMapper().queryCompanyLineOverview(companyId);
        Map<Long,CompanyLineOverviewVo> temp=new HashMap<>();
        if(list!=null && !list.isEmpty()){
            for(CompanyLineStationVo vo:list){
                CompanyLineOverviewVo overviewVo = temp.get(vo.getCompanyLineId());
                if(overviewVo==null){
                    overviewVo=new CompanyLineOverviewVo();
                    overviewVo.setCompanyId(vo.getCompanyId());
                    overviewVo.setCompanyLineId(vo.getCompanyLineId());
                    overviewVo.setCompanyLineCode(vo.getCompanyLineName());
                    overviewVo.setCompanyLineName(vo.getCompanyLineCode());
                }
                if(vo.getStationOrder()==1){
                    overviewVo.setFromBusStationId(vo.getBusStationId());
                    overviewVo.setFromBusStationName(vo.getBusStationName());
                    overviewVo.setFromBusStationLatitude(vo.getBusStationLatitude());
                    overviewVo.setFromBusStationLongitude(vo.getBusStationLongitude());
                }else {
                    overviewVo.setToBusStationId(vo.getBusStationId());
                    overviewVo.setToBusStationName(vo.getBusStationName());
                    overviewVo.setToBusStationLatitude(vo.getBusStationLatitude());
                    overviewVo.setToBusStationLongitude(vo.getBusStationLongitude());
                }
               temp.put(vo.getCompanyLineId(),overviewVo);
            }
        }
        return temp.values();
    }

    @Override
    public List<BusLineStationVo> queryCompanyLineStations(long companyId, String companyLineName) {
        List<BusLineStationVo> result = new ArrayList<>();
        List<BusLineVo> lineNames = getBaseMapper().queryCompanyLineByName(companyId, companyLineName);
        if (lineNames != null && !lineNames.isEmpty()) {
            for (BusLineVo lineVo : lineNames) {
                BusLineStationVo vo = new BusLineStationVo();
                vo.setCompanyId(companyId);
                vo.setCompanyLineId(lineVo.getCompanyLineId());
                vo.setCompanyLineName(lineVo.getCompanyLineCode());
                vo.setCompanyLineCode(lineVo.getCompanyLineName());
                List<BusStationVo> list = getBaseMapper().queryCompanyLineStations(lineVo.getCompanyLineId());
                vo.setStations(list);
                result.add(vo);
            }
        }
        return result;
    }

    @Override
    public List<CompanyLineNameVo> queryCompanyLineNames(long companyId) {
        return getBaseMapper().queryCompanyLineNames(companyId);
    }

    @Override
    @DataSource("read")
    public Long queryCompanyLineBusId(long companyId) {
        return getBaseMapper().queryCompanyLineBusId(companyId);
    }

    @Override
    public List<Long> queryCompanyLineByCodeAndCompany(String companyLineCode,Long companyId) {
        return list(new QueryWrapper<InfoCompanyLineEntity>()
                .eq("is_deleted",0)
                .eq("is_enabled",1)
                .eq("company_line_code",companyLineCode)
                .eq("company_id",companyId)
            ).stream().map(InfoCompanyLineEntity::getCompanyLineId).collect(Collectors.toList());
    }

    @Override
    @DataSource("read")
    public void checkCompanyLine(long companyLineId) {
        Integer count = getBaseMapper().selectCount(new QueryWrapper<InfoCompanyLineEntity>().eq("company_line_id", companyLineId).eq("is_deleted", 0).eq("is_enabled", 1));
        if (count == null || count == 0) {
            throw new RRException("公交线路不存在");
        }
    }

    @Override
    @DataSource("read")
    public int queryCompanyLineBusCount(long companyLineId) {
        List<Long> list = planBusServiceDao.queryBusIdByCompanyLineId(companyLineId);
        if (list != null) {
            return list.size();
        }
        return 0;
    }
}
