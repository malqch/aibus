package com.wntime.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.customer.entity.ItineraryReceiptEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author buxl
 * @email 
 * @date 2021-01-21 16:26:42
 */
public interface ItineraryReceiptService extends IService<ItineraryReceiptEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /***
     * @Author Buxl
     * @Description 根据车辆id查询未开始的行程
     * @Date 23:33 2021/1/22
     * @Param [busId]
     * @return java.util.List<com.wntime.customer.entity.ItineraryReceiptEntity>
     **/
    List<ItineraryReceiptEntity> getItineraryByCon(Map<String,Object> params);

    /**
     * @Author Buxl
     * @Description 根据营运计划生成未出行的行程
     * @Date 17:00 2021/1/21
     * @Param [itineraryDate, direction, busId]
     * @return void
     **/
    void produceItinerary();

    /**
     * @Author Buxl
     * @Description 根据营运计划生成未出行的行程
     * @Date 17:00 2021/1/21
     * @Param [itineraryDate, direction, busId]
     * @return void
     **/
    void produceItinerary(String itineraryDate,String direction,Long busId);

    /**
     * @Author Buxl
     * @Description 根据行程id查询行程的车站列表
     * @Date 8:31 2021/1/27
     * @Param [itineraryId]
     * @return java.util.List<com.wntime.customer.entity.ItineraryReceiptEntity>
     **/
    List<ItineraryReceiptEntity> getStationByItinerary(Long itineraryId);

    /**
     * @Author Buxl
     * @Description 查询行程下一停靠站的GPS
     * @Date 10:05 2021/1/28
     * @Param [itineraryId]
     * @return com.wntime.customer.entity.ItineraryReceiptEntity
     **/
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

