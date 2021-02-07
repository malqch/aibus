package com.wntime.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.customer.dao.InfoBusStationDao;
import com.wntime.customer.dao.InfoLineStationDao;
import com.wntime.customer.entity.InfoBusStationEntity;
import com.wntime.customer.entity.InfoLineStationEntity;
import com.wntime.customer.service.InfoBusStationService;
import com.wntime.customer.service.InfoLineStationService;
import com.wntime.customer.vo.BusStationDetailVO;
import com.wntime.customer.vo.InfoCompanyInitVo;
import com.wntime.datasource.annotation.DataSource;
import com.wntime.modules.gemfire.vo.LineStationInfoVo;
import com.wntime.service.common.service.BusStationService;
import com.wntime.service.common.vo.BusStationVo;
import com.wntime.service.common.vo.InfoBusStationVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("infoBusStationService")
public class InfoBusStationServiceImpl extends ServiceImpl<InfoBusStationDao, InfoBusStationEntity> implements InfoBusStationService, BusStationService {
    @Autowired
    private InfoLineStationService infoLineStationService;
    @Resource
    private InfoLineStationDao infoLineStationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        int currPage = 1;
        int pageSize = 20;
        if (params.get("page") != null) {
            currPage = Integer.parseInt((String) params.get("page"));
        }
        if (params.get("page") != null) {
            pageSize = Integer.parseInt((String) params.get("limit"));
        }
        Page<InfoBusStationEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        if(params.get("areaId") != null && StringUtils.isNotEmpty(String.valueOf(params.get("areaId")))){
            params.put("areaId",Long.parseLong(String.valueOf(params.get("areaId")) ));
        }else{
            params.put("areaId",null);
        }

        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    public InfoBusStationEntity getDetailById(Long id) {
        return getBaseMapper().getDetailInfoById(id);
    }

    @Override
    public void save(InfoBusStationEntity infoBusStationEntity, Long userId) {
        infoBusStationEntity.setBusStationId(null);
        infoBusStationEntity.setIsDeleted(0);
        infoBusStationEntity.setCreatedDate(DateUtils.getTimestamp());
        infoBusStationEntity.setCreatedBy(userId);
        this.save(infoBusStationEntity);
    }

    @Override
    public void updateById(InfoBusStationEntity infoBusStationEntity, Long userId) {
        // 关联检查
        if(infoBusStationEntity.getIsEnabled() == 0){
            deletePermitCheck( infoBusStationEntity.getBusStationId());
        }
        infoBusStationEntity.setModifiedBy(userId);
        infoBusStationEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoBusStationEntity);
    }

    /**
     * 关联删除检查
     * @param id
     */
    void deletePermitCheck(Long id){
        // 公交线路检查
        QueryWrapper<InfoLineStationEntity> QueryWrapper = new QueryWrapper<InfoLineStationEntity>()
                .eq("is_deleted", 0)
                .eq("bus_station_id",id);

        List<InfoLineStationEntity> getList = infoLineStationDao.selectList(QueryWrapper);
        if(getList != null && getList.size() > 0){
            throw new RRException("该车站存在关联[公交线路车站]配置，删除失败。");
        }
    }

    @Override
    public void delById(Long id, Long userId) {
        // 关联检查
        deletePermitCheck(id);
        InfoBusStationEntity infoBusStationEntity = new InfoBusStationEntity();
        infoBusStationEntity.setBusStationId(id);
        infoBusStationEntity.setIsDeleted(1);
        infoBusStationEntity.setModifiedBy(userId);
        infoBusStationEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoBusStationEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

    @Override
    public InfoCompanyInitVo getInitInfoByBusStation(String eventTargetCode,List<Long> companyIdList) {

        return baseMapper.getInitInfoByBusStation(eventTargetCode,companyIdList);
    }

    @Override
    public List<InfoBusStationEntity> getStationListByCompanyId(Long companyId) {

        return getBaseMapper().getStationListByCompanyId(companyId);
    }

    @Override
    public List<InfoBusStationEntity> getStationListByCompanyLineId(Long companyLineId) {

        return getBaseMapper().getStationListByCompanyLineId(companyLineId);
    }

    @Override
    public List<InfoBusStationEntity> getStationAllByCompanyArea(Long companyId) {
        return getBaseMapper().getStationAllByCompanyArea(companyId);
    }

    @Override
    public BusStationDetailVO getBusStationDetailById(Long busStationId) {

        return getBaseMapper().getBusStationDetailById(busStationId);
    }

    @Override
    public Long getBusStationIdByName(Long areaId, String stationName) {
        QueryWrapper<InfoBusStationEntity> QueryWrapperType = new QueryWrapper<InfoBusStationEntity>()
                .eq("is_deleted", 0)
                .eq("is_enabled", 1)
                .eq("area_id", areaId)
                .eq("bus_station_name  ",stationName);
        InfoBusStationEntity infoBusStationEntity = this.getOne(QueryWrapperType);
        if(infoBusStationEntity == null){
            return 0L;
        }
        return infoBusStationEntity.getBusStationId();
    }

    @Override
    public List<BusStationVo> getStationsByCompanyAndLine(Map<String, Object> params) {
        Long companyId = Long.parseLong(String.valueOf(params.get("companyId")));
        Long companyLineId = 0L;
        if(params.get("companyLineId") != null){
            companyLineId = Long.parseLong(String.valueOf(params.get("companyLineId") ));
        }
        String busStationName = String.valueOf(params.get("busStationName") == null ?"":params.get("busStationName"));
        return getBaseMapper().getStationsByCompanyAndLine(companyId,companyLineId,busStationName);
    }

    @Override
    @DataSource("read")
    public LineStationInfoVo queryBusStationInfo(long busStationId,Long companyLineId) {
        return getBaseMapper().queryBusStationByBusCompanyId(companyLineId,busStationId);
    }

    @Override
    @DataSource("read")
    public List<InfoBusStationVo> queryBusStations() {
        return getBaseMapper().queryList();
    }
}
