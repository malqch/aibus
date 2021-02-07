package com.wntime.modules.officer.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.modules.officer.dao.GuardianDao;
import com.wntime.modules.officer.entity.GuardianEntity;
import com.wntime.modules.officer.service.GuardianService;


@Service("guardianService")
public class GuardianServiceImpl extends ServiceImpl<GuardianDao, GuardianEntity> implements GuardianService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GuardianEntity> page = this.page(
                new Query<GuardianEntity>().getPage(params),
                new QueryWrapper<GuardianEntity>()
        );

        return new PageUtils(page);
    }

}