package com.wntime.modules.officer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.modules.officer.entity.SchoolEntity;

import java.util.Map;

/**
 * @desc 学校信息
 *
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
public interface SchoolService extends IService<SchoolEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

