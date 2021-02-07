package com.wntime.maintain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.maintain.entity.LogBusMaintenanceEntity;
import com.wntime.maintain.vo.MaintainBusVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @desc 维保日志表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:00:24
 */
@Mapper
public interface LogBusMaintenanceDao extends BaseMapper<LogBusMaintenanceEntity> {

    List<MaintainBusVO> getMaintainBusDetailByStatusAndCompanyIdAndTime(Long status, Long companyId, Date startTime, Date endTime);

    int queryBusMaintenanceCount(@Param("factoryId")long factoryId);
}
