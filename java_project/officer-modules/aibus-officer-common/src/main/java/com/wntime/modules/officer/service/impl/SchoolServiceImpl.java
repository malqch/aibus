package com.wntime.modules.officer.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.modules.officer.dao.SchoolDao;
import com.wntime.modules.officer.entity.SchoolEntity;
import com.wntime.modules.officer.service.SchoolService;


@Service("schoolService")
public class SchoolServiceImpl extends ServiceImpl<SchoolDao, SchoolEntity> implements SchoolService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SchoolEntity> page = this.page(
                new Query<SchoolEntity>().getPage(params),
                new QueryWrapper<SchoolEntity>()
        );

        return new PageUtils(page);
    }

}