package com.wntime.customer.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.service.common.entity.LogBusServiceEntity;

import java.util.Map;

/**
 * @desc 营运日志表
 *
 * @date 2020-08-25 14:00:25
 */
public interface LogBusServiceService extends IService<LogBusServiceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

