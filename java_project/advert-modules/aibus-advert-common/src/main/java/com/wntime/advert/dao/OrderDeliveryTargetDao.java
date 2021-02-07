package com.wntime.advert.dao;

import com.wntime.advert.entity.OrderDeliveryTargetEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @desc 投放标签表
 * 
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Mapper
public interface OrderDeliveryTargetDao extends BaseMapper<OrderDeliveryTargetEntity> {

    void deleteBatchByAdvertiseId(Long advertiseId);

    List<OrderDeliveryTargetEntity> listByAdvertiseIdAndGroup(Long advertiseId, String group);
}
