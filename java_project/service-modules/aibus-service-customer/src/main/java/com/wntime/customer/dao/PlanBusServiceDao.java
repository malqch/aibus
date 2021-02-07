package com.wntime.customer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.customer.entity.PlanBusServiceEntity;
import com.wntime.customer.vo.PlanBusServiceVo;
import com.wntime.customer.vo.PlanBusVo;
import com.wntime.service.common.vo.BusLinePlanServiceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @desc 营运计划表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:00:24
 */
@Mapper
public interface PlanBusServiceDao extends BaseMapper<PlanBusServiceEntity> {

    List<PlanBusServiceVo> listDetail(Long companyLineId);

    List<PlanBusServiceVo> listPlanInTimeRangeByBus(Long companyLineId, String direction,Long busId,Date beginDate,Date endDate);

    boolean isBusPlannedInTimeRange(Long busId,Date beginDate,Date endDate);

    boolean isBusPlannedInTimeRangeExceptPlanId(Long planServiceId,Long busId,Date beginDate,Date endDate);

    List<PlanBusVo> listBusNotPlannedInTimeRange(Long schoolId, String direction,Long companyId,Long companyLineId,Date beginDate,Date endDate);

    PlanBusServiceEntity getDetailById(Long id);

    List<Long> queryBusIdByCompanyLineId(@Param("companyLineId")long companyLineId);

    List<BusLinePlanServiceVo> queryList(@Param("busId") Long busId);
}
