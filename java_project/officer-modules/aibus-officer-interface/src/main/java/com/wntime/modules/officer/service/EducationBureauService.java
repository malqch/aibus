package com.wntime.modules.officer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.modules.officer.entity.EducationBureauEntity;

import java.util.Map;

/**
 * @desc 教育局
 *
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
public interface EducationBureauService extends IService<EducationBureauEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

