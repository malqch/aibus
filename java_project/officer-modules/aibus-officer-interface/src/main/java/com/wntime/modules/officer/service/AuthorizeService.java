package com.wntime.modules.officer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.modules.officer.entity.AuthorizeEntity;

import java.util.Map;

/**
 * @desc 授权人
 *
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
public interface AuthorizeService extends IService<AuthorizeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

