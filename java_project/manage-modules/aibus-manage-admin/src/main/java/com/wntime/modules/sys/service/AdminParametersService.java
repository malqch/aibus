package com.wntime.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.entity.AdminParametersEntity;

import java.util.Map;

/**
 * 系统参数表
 *
 * @author buxl
 * @email
 * @date 2019-09-27 14:50:20
 */
public interface AdminParametersService extends IService<AdminParametersEntity> {

    /**
     * 查询需要缓存的系统参数，并将其放入缓存中
     * @return
     */
    Map<String, Map<String,AdminParametersEntity>> queryCacheParameters();

}

