package com.wntime.advert.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.advert.vo.ItemVo;
import com.wntime.common.utils.PageUtils;
import com.wntime.advert.entity.LogCheckItemEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 审核违规表
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
public interface LogCheckItemService extends IService<LogCheckItemEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ItemVo> listByAdvertise(Long advertiseId);

    void deleteBatchByAdvertiseId(Long advertiseId);

    List<LogCheckItemEntity> listByAdvertiseId(Long advertiseId);
}

