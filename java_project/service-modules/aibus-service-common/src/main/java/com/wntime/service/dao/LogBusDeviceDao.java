package com.wntime.service.dao;

import com.wntime.service.entity.LogBusDeviceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * @desc 设备日志表
 * 
 * @author psp
 * @date 2020-09-02 13:49:03
 */
@Mapper
public interface LogBusDeviceDao extends BaseMapper<LogBusDeviceEntity> {

    List<LogBusDeviceEntity> queryPageList(Map<String, Object> params);
	
}
