package com.wntime.customer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.service.common.entity.LogBusServiceEntity;
import com.wntime.service.common.vo.BusPassengerAgeVo;
import com.wntime.service.common.vo.BusPassengerVo;
import com.wntime.service.common.vo.CompanyLinePassengerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @desc 营运日志表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:00:25
 */
@Mapper
public interface LogBusServiceDao extends BaseMapper<LogBusServiceEntity> {


    int queryBusServiceLogByBusIdAndLineStation(@Param("busId")long busId,@Param("companyLineId")long companyLineId,@Param("stationId")long stationId);

    LogBusServiceEntity queryByLogByBusIdAndCompanyLineId(@Param("busId")long busId, @Param("companyLineId")long companyLineId);

    int disableLogBusService(@Param("busServiceId")long busServiceId);

    List<CompanyLinePassengerVo> queryCompanyBusServiceLog(@Param("companyId")long companyId);

    List<LogBusServiceEntity> queryPageList(Map<String, Object> params);

    void disableLastestBusServiceLog(@Param("busId")long busId,@Param("companyLineId")long companyLineId,@Param("stationId")long stationId,@Param("nextStationId")Long nextStationId);

    BusPassengerVo queryBusPassengerCount(@Param("busId")long busId,@Param("date") LocalDate date);

    BusPassengerAgeVo queryBusPassengerAge(@Param("busId")long busId, @Param("date") LocalDate date);

    /**
     * 查询今天最新一条营运日志中的乘客数量
     * @param busId
     * @param companyLineId
     * @return
     */
    Integer queryBusKeepRide(@Param("busId")long busId,@Param("companyLineId")long companyLineId);
}
