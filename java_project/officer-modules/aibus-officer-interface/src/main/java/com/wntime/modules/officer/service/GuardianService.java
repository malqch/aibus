package com.wntime.modules.officer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.modules.officer.entity.GuardianEntity;

import java.util.Map;

/**
 * @desc 监护人
 *
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
public interface GuardianService extends IService<GuardianEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

