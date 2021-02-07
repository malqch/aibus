package com.wntime.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.customer.entity.PlanBusServiceEntity;
import com.wntime.customer.vo.PlanBusServiceVo;
import com.wntime.customer.vo.PlanBusVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @desc 营运计划表
 *
 * @date 2020-08-25 14:00:24
 */
public interface PlanBusServiceService extends IService<PlanBusServiceEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void savePlanBusService(PlanBusServiceEntity planBusServiceEntity);

    void updatePlanBusService(PlanBusServiceEntity planBusServiceEntity);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);

    List<PlanBusServiceVo> listDetail(Long companyLineId);

    List<PlanBusServiceVo> listPlanInTimeRangeByBus(Long companyLineId, String direction,Long busId,Date beginDate,Date endDate);

    /**
     * 判断公交车在时间段内有无计划
     * 如果公交车已有计划(因为可能有多个计划，所以查存在的计划，而不是查不存在的)
     * 1. beginDate在 已有计划的开始时间和结束时间之间
     * 2. beginDate在 已有计划的开始时间之前，endDate在已有计划的开始时间之后
     */
    boolean isBusPlannedInTimeRange(Long busId, Date beginDate, Date endDate);

    boolean isBusPlannedInTimeRangeExceptPlanId(Long planServiceId,Long busId,Date beginDate,Date endDate);

    List<PlanBusVo> listBusNotPlannedInTimeRange(Long schoolId, String direction,Long companyId,Long companyLineId,Date beginDate, Date endDate);

    PlanBusServiceEntity getDetailById(Long id);
}

