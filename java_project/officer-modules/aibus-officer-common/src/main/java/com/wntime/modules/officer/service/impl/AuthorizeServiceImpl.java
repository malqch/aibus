package com.wntime.modules.officer.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.modules.officer.dao.AuthorizeDao;
import com.wntime.modules.officer.entity.AuthorizeEntity;
import com.wntime.modules.officer.service.AuthorizeService;


@Service("authorizeService")
public class AuthorizeServiceImpl extends ServiceImpl<AuthorizeDao, AuthorizeEntity> implements AuthorizeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AuthorizeEntity> page = this.page(
                new Query<AuthorizeEntity>().getPage(params),
                new QueryWrapper<AuthorizeEntity>()
        );

        return new PageUtils(page);
    }

}