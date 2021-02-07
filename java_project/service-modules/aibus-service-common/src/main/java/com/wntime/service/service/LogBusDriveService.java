package com.wntime.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.service.common.entity.LogBusDriveEntity;

import java.util.Map;

/**
 * @desc 行驶日志表
 *
 * @date 2020-08-25 16:36:18
 */
public interface LogBusDriveService extends IService<LogBusDriveEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

