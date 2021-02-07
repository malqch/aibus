package com.wntime.modules.officer.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.modules.officer.dao.EducationBureauDao;
import com.wntime.modules.officer.entity.EducationBureauEntity;
import com.wntime.modules.officer.service.EducationBureauService;


@Service("educationBureauService")
public class EducationBureauServiceImpl extends ServiceImpl<EducationBureauDao, EducationBureauEntity> implements EducationBureauService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<EducationBureauEntity> page = this.page(
                new Query<EducationBureauEntity>().getPage(params),
                new QueryWrapper<EducationBureauEntity>()
        );

        return new PageUtils(page);
    }

}