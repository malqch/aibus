package com.wntime.service.dao;

import com.wntime.service.entity.InfoBusDeviceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.service.region.BusDeviceKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @desc AI设备表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:28:17
 */
@Mapper
public interface InfoBusDeviceDao extends BaseMapper<InfoBusDeviceEntity> {

    List<InfoBusDeviceEntity> queryPageList(Map<String, Object> params);

    void insertBatch(List<InfoBusDeviceEntity> list);

    void updateBatch(List<InfoBusDeviceEntity> list);

    void deleteBach(List<InfoBusDeviceEntity> list);

    Long queryDeviceBusId(@Param("busDeviceId")long busDeviceId);

    List<BusDeviceKey> queryOverTimeOnlineEC(@Param("expireDate") Date expireDate);

    void updateDeviceStatusOffline(List<BusDeviceKey> list);
}
