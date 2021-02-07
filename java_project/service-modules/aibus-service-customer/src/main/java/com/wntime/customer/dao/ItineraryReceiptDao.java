package com.wntime.customer.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.customer.entity.ItineraryReceiptEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author buxl
 * @email 
 * @date 2021-01-21 16:26:42
 */
@Mapper
public interface ItineraryReceiptDao extends BaseMapper<ItineraryReceiptEntity> {

    /**
     * @Author Buxl
     * @Description 获取行程计划，此时行程表中还没有数据，如果查到计划了，则生成行程
     * @Date 21:45 2021/1/21
     * @Param []
     * @return com.wntime.customer.entity.ItineraryReceiptEntity
     **/
    List<ItineraryReceiptEntity> getItineraryPlan(String itineraryDate, String direction, Long busId);

    /***
     * @Author Buxl
     * @Description 根据车辆id查询未开始的行程
     * @Date 23:33 2021/1/22
     * @Param [busId]
     * @return java.util.List<com.wntime.customer.entity.ItineraryReceiptEntity>
     **/
    List<ItineraryReceiptEntity> getItineraryByCon(Map params);

    /**
     * @Author Buxl
     * @Description 根据行程id查询行程的车站列表
     * @Date 8:30 2021/1/27
     * @Param [itineraryId]
     * @return java.util.List<com.wntime.customer.entity.ItineraryReceiptEntity>
     **/
    List<ItineraryReceiptEntity> getStationByItinerary(Long itineraryId);

    /**
     * 查询行程下一停靠站的GPS
     * @param itineraryId
     * @return
     */
    ItineraryReceiptEntity getNextStationGPS(Long itineraryId);

    /**
     * @Author Buxl
     * @Description 统计行程回单的数据
     * @Date 10:00 2021/1/29
     * @Param [itineraryId]
     * @return com.wntime.customer.entity.ItineraryReceiptEntity
     **/
    List<ItineraryReceiptEntity> statReceiptData(Long itineraryId);
}
