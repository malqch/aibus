package com.wntime.advert.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.advert.vo.DeliveryLineVo;
import com.wntime.common.utils.PageUtils;
import com.wntime.advert.entity.OrderDeliveryAreaEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 投放范围表
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
public interface OrderDeliveryAreaService extends IService<OrderDeliveryAreaEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void deleteBatchByAdvertiseId(Long advertiseId);

    List<OrderDeliveryAreaEntity> listByAdvertiseId(Long advertiseId);

    List<String> listStationByAdvertiseId(Long advertiseId);

    List<DeliveryLineVo> listLineCodeByAdvertise(Long advertiseId);
}

