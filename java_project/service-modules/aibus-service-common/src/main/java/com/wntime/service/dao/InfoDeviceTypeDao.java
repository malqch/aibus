package com.wntime.service.dao;

import com.wntime.service.entity.InfoDeviceTypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * @desc 设备类型
 * 
 * @author psp
 * @date 2020-08-29 16:19:14
 */
@Mapper
public interface InfoDeviceTypeDao extends BaseMapper<InfoDeviceTypeEntity> {

    List<InfoDeviceTypeEntity> queryPageList(Map<String, Object> params);
	
}
