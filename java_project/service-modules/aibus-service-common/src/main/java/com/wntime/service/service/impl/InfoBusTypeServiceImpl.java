package com.wntime.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.datasource.annotation.DataSource;
import com.wntime.modules.gemfire.key.BusDateKey;
import com.wntime.service.common.entity.InfoBusEntity;
import com.wntime.service.common.region.BusRealtimeMonitorStatistics;
import com.wntime.service.common.repo.BusRealtimeMonitorStatisticsRepository;
import com.wntime.service.common.service.BusTypeInfoService;
import com.wntime.service.common.vo.BusInfoTypeVo;
import com.wntime.service.dao.InfoBusDao;
import com.wntime.service.dao.InfoBusTypeDao;
import com.wntime.service.entity.InfoBusTypeEntity;
import com.wntime.service.region.BusInfo;
import com.wntime.service.repo.BusInfoRepository;
import com.wntime.service.service.InfoBusTypeService;
import com.wntime.service.vo.InfoDeviceTypeVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("infoBusTypeService")
public class InfoBusTypeServiceImpl extends ServiceImpl<InfoBusTypeDao, InfoBusTypeEntity>
        implements InfoBusTypeService, BusTypeInfoService {

    @Resource
    private BusInfoRepository busInfoRepository;
    @Resource
    private InfoBusDao infoBusDao;
    @Resource
    private BusRealtimeMonitorStatisticsRepository busRealtimeMonitorStatisticsRepository;
    @Override
    public BusInfoTypeVo queryByCode(String code) {
        return this.baseMapper.queryByCode(code);
    }

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
        Page<InfoBusTypeEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    public InfoBusTypeEntity getDetailById(Long id) {
        return this.getById(id);
    }

    @Override
    public void save(InfoBusTypeEntity infoBusTypeEntity, Long userId) {
        infoBusTypeEntity.setBusTypeId(null);
        infoBusTypeEntity.setIsDeleted(0);
        infoBusTypeEntity.setCreatedDate(DateUtils.getTimestamp());
        infoBusTypeEntity.setCreatedBy(userId);
        this.save(infoBusTypeEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(InfoBusTypeEntity infoBusTypeEntity, Long userId) {
        // 关联检查
        if(infoBusTypeEntity.getIsEnabled() == 0){
            deletePermitCheck( infoBusTypeEntity.getBusTypeId());
        }

        infoBusTypeEntity.setModifiedBy(userId);
        infoBusTypeEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoBusTypeEntity);
        List<Long> busIds = infoBusDao.queryBusIdsByBusTypeId(infoBusTypeEntity.getBusTypeId());

        Iterable<BusInfo> busInfos = busInfoRepository.findAllById(busIds);
        LocalDate now=LocalDate.now();

        if (busInfos != null) {
            busInfos.forEach(busInfo -> {
                busInfo.setPeopleLoadCount(infoBusTypeEntity.getPeopleNumber());
                Optional<BusRealtimeMonitorStatistics> optional = busRealtimeMonitorStatisticsRepository.findById(new BusDateKey(busInfo.getBusId(), now));
                optional.ifPresent(busRealtimeMonitorStatistics -> {
                    busRealtimeMonitorStatistics.setPeopleLoadCount(infoBusTypeEntity.getPeopleNumber());
                    busRealtimeMonitorStatisticsRepository.save(busRealtimeMonitorStatistics);
                });
            });
            busInfoRepository.saveAll(busInfos);

        }

    }

    /**
     * 关联删除检查
     * @param id
     */
    void deletePermitCheck(Long id){
        // 车辆检查
        QueryWrapper<InfoBusEntity> QueryWrapper = new QueryWrapper<InfoBusEntity>()
                .eq("is_deleted", 0)
                .eq("bus_type_id",id);

        List<InfoBusEntity> getList = infoBusDao.selectList(QueryWrapper);
        if(getList != null && getList.size() > 0){
            throw new RRException("该车辆类型存在关联[车辆信息]配置，删除失败。");
        }
    }
    @Override
    public void delById(Long id, Long userId) {
        // 关联检查
        deletePermitCheck(id);

        InfoBusTypeEntity infoBusTypeEntity = new InfoBusTypeEntity();
        infoBusTypeEntity.setBusTypeId(id);
        infoBusTypeEntity.setIsDeleted(1);
        infoBusTypeEntity.setModifiedBy(userId);
        infoBusTypeEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoBusTypeEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

    @Override
    public List<InfoBusTypeEntity> getAllList() {
        return this.baseMapper.selectList(new QueryWrapper<InfoBusTypeEntity>()
                .eq("is_enabled",1).eq("is_deleted",0));
    }

    @Override
    @DataSource("read")
    public List<InfoDeviceTypeVo> queryDeviceType() {
        return getBaseMapper().queryList();
    }

}
