package com.wntime.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.service.common.entity.LogBusDriveEntity;
import com.wntime.service.region.BusRealtimeMonitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @desc 行驶日志表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 16:36:18
 */
@Mapper
public interface LogBusDriveDao extends BaseMapper<LogBusDriveEntity> {

    List<LogBusDriveEntity> queryPageList(Map<String, Object> params);

    Double queryFactoryBusTotalMileSum(@Param("factoryId")long factoryId);

    Optional<BusRealtimeMonitor> queryLogBusDerive(@Param("busId")long busId, @Param("localDate") LocalDate localDate);
}
