package com.wntime.service.dao;

import com.wntime.service.common.vo.BusInfoBatteryTypeVo;
import com.wntime.service.common.vo.BusInfoMotorTypeVo;
import com.wntime.service.entity.InfoMotorTypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * @desc 电机类型表
 * 
 * @author psp
 * @date 2020-08-31 11:07:44
 */
@Mapper
public interface InfoMotorTypeDao extends BaseMapper<InfoMotorTypeEntity> {

    List<InfoMotorTypeEntity> queryPageList(Map<String, Object> params);

    BusInfoMotorTypeVo queryByCode(String code);
	
}
