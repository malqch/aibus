package com.wntime.advert.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.advert.entity.LogAdvertiseShowEntity;

import java.util.Map;

/**
 * @desc 广告播放日志
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
public interface LogAdvertiseShowService extends IService<LogAdvertiseShowEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

