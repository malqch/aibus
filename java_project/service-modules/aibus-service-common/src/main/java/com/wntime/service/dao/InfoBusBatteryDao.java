package com.wntime.service.dao;

import com.wntime.service.entity.InfoBusBatteryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @desc 电池表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:28:17
 */
@Mapper
public interface InfoBusBatteryDao extends BaseMapper<InfoBusBatteryEntity> {

    Double queryBusBatteryEnergy(@Param("busId")long busId);

    List<InfoBusBatteryEntity> queryPageList(Map<String, Object> params);
}
