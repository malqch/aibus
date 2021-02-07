package com.wntime.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.customer.dao.InfoCompanyLineDao;
import com.wntime.customer.dao.PlanBusServiceDao;
import com.wntime.customer.entity.PlanBusServiceEntity;
import com.wntime.customer.region.LineBus;
import com.wntime.customer.repo.LineBusRepository;
import com.wntime.customer.service.PlanBusServiceService;
import com.wntime.customer.vo.PlanBusServiceVo;
import com.wntime.customer.vo.PlanBusVo;
import com.wntime.service.common.service.BusInfoService;
import com.wntime.service.common.vo.BusLineVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("planBusServiceService")
public class PlanBusServiceServiceImpl extends ServiceImpl<PlanBusServiceDao, PlanBusServiceEntity> implements PlanBusServiceService {

    @Resource
    private InfoCompanyLineDao infoCompanyLineDao;
    @Resource
    private LineBusRepository lineBusRepository;
    @Resource
    private BusInfoService busInfoService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PlanBusServiceEntity> page = this.page(
                new Query<PlanBusServiceEntity>().getPage(params),
                new QueryWrapper<PlanBusServiceEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePlanBusService(PlanBusServiceEntity planBusServiceEntity) {
        save(planBusServiceEntity);
//        saveLineBus(planBusServiceEntity);
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePlanBusService(PlanBusServiceEntity planBusServiceEntity) {
        updateById(planBusServiceEntity);
//        saveLineBus(planBusServiceEntity);
    }

    private void saveLineBus(PlanBusServiceEntity planBusServiceEntity) {
        if(planBusServiceEntity.getIsEnabled()== Constant.Enabled.DISABLE.getValue()){
            lineBusRepository.deleteById(planBusServiceEntity.getBusId());
        }else {
            BusLineVo info = infoCompanyLineDao.queryBusLineInfoByCompanyLineId(planBusServiceEntity.getCompanyLineId());
            if(info!=null){
                LineBus lineBus=new LineBus();
                lineBus.setBusId(planBusServiceEntity.getBusId());
                lineBus.setCompanyId(info.getCompanyId());
                lineBus.setCompanyName(info.getCompanyName());
                lineBus.setCompanyLineId(planBusServiceEntity.getCompanyLineId());
                lineBus.setCompanyLineName(info.getCompanyLineName());
                lineBus.setCompanyLineCode(info.getCompanyLineCode());
                lineBus.setAreaName(info.getAreaName());
                lineBus.setAreaId(info.getAreaId());
                String serviceTime = info.getServiceTime();
                String[] timeSplit = serviceTime.split("#");
                lineBus.setFirstTime(DateUtils.stringToDate(timeSplit[0],DateUtils.DATE_TIME_PATTERN));
                lineBus.setLastTime(DateUtils.stringToDate(timeSplit[1],DateUtils.DATE_TIME_PATTERN));
                lineBusRepository.save(lineBus);
                busInfoService.updateBusCompany(planBusServiceEntity.getBusId(),info.getCompanyId());
            }
        }

    }

    @Override
    public PlanBusServiceEntity getDetailById(Long id) {
        return getBaseMapper().getDetailById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delById(Long id, Long userId) {
        PlanBusServiceEntity entity=getById(id);
        entity.setIsDeleted(1);
        entity.setModifiedBy(userId);
        entity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(entity);
//        lineBusRepository.deleteById(entity.getBusId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

    @Override
    public List<PlanBusServiceVo> listDetail(Long companyLineId) {
        return getBaseMapper().listDetail(companyLineId);
    }

    @Override
    public List<PlanBusServiceVo> listPlanInTimeRangeByBus(Long companyLineId,String direction, Long busId, Date beginDate, Date endDate) {
        return getBaseMapper().listPlanInTimeRangeByBus(companyLineId,direction,busId, beginDate, endDate);
    }

    @Override
    public boolean isBusPlannedInTimeRange(Long busId, Date beginDate, Date endDate) {
        return getBaseMapper().isBusPlannedInTimeRange(busId,beginDate,endDate);
    }

    @Override
    public boolean isBusPlannedInTimeRangeExceptPlanId(Long planServiceId, Long busId, Date beginDate, Date endDate) {
        return getBaseMapper().isBusPlannedInTimeRangeExceptPlanId(planServiceId, busId, beginDate, endDate);
    }

    @Override
    public List<PlanBusVo> listBusNotPlannedInTimeRange(Long schoolId, String direction,Long companyId,Long companyLineId,Date beginDate, Date endDate) {
        return getBaseMapper().listBusNotPlannedInTimeRange(schoolId, direction,companyId,companyLineId,beginDate,endDate);
    }
}
