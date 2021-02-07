package com.wntime.advert.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.advert.entity.OrderDeliveryTargetEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 投放标签表
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
public interface OrderDeliveryTargetService extends IService<OrderDeliveryTargetEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void deleteBatchByAdvertiseId(Long advertiseId);

    List<OrderDeliveryTargetEntity> listByAdvertiseId(Long advertiseId);

    List<OrderDeliveryTargetEntity> listByAdvertiseIdAndGroup(Long advertiseId, String group);
}

