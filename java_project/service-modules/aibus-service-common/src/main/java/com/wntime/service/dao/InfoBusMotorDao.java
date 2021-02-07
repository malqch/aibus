package com.wntime.service.dao;

import com.wntime.service.entity.InfoBusMotorEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author psp
 * @desc 电机表
 * @date 2020-09-01 10:05:27
 */
@Mapper
public interface InfoBusMotorDao extends BaseMapper<InfoBusMotorEntity> {

    List<InfoBusMotorEntity> queryPageList(Map<String, Object> params);

}
