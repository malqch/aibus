package com.wntime.customer.dao;

import com.wntime.customer.entity.OrderBusDeliveryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.customer.vo.OrderBusDeliveryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @desc 车辆交付表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:00:24
 */
@Mapper
public interface OrderBusDeliveryDao extends BaseMapper<OrderBusDeliveryEntity> {

    List<OrderBusDeliveryVo> queryPageList(Map<String, Object> params);

    OrderBusDeliveryVo getDetailById(Long id);

}
