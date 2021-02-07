package com.wntime.modules.monitor.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.modules.monitor.entity.LogWeatherWarn;
import com.wntime.modules.monitor.vo.AlarmVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 79448
 * @date 2020/11/5 15:14
 */
@Mapper
public interface LogWeatherWarnMapper extends BaseMapper<LogWeatherWarn> {

    List<AlarmVo> queryLatelyAlarm(@Param("areaId")long areaId);
}
