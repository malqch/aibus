package com.wntime.advert.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.advert.entity.OrderAdvertiseAttachEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 广告附件表
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
public interface OrderAdvertiseAttachService extends IService<OrderAdvertiseAttachEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void deleteBatchByAdvertiseId(Long advertiseId);

    List<OrderAdvertiseAttachEntity> listByAdvertiseId(Long advertiseId);
}

