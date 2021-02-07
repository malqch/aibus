package com.wntime.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.CacheUtils;
import com.wntime.common.utils.CollectionUtils;
import com.wntime.entity.AdminParametersEntity;
import com.wntime.modules.sys.dao.AdminParametersDao;
import com.wntime.modules.sys.service.AdminParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("adminParametersService")
public class AdminParametersServiceImpl extends ServiceImpl<AdminParametersDao, AdminParametersEntity> implements AdminParametersService {

    @Autowired
    private AdminParametersDao AdminParametersDao;

    /**
     * 查询需要缓存的系统参数，并将其放入缓存中
     * @return
     */
    @Override
    public Map<String, Map<String,AdminParametersEntity>> queryCacheParameters() {
        List<AdminParametersEntity> adminParameters = AdminParametersDao.queryCacheParameters();
        CacheUtils.adminParametersMap.clear();
        CacheUtils.adminParametersMap = CollectionUtils.turnListToMap2(adminParameters,"paramType","paramCode");
        return CacheUtils.adminParametersMap;
    }

}
