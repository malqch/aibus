package com.wntime.modules.officer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.PositionUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.common.utils.ShiroUtils;
import com.wntime.entity.AdminUser;
import com.wntime.modules.officer.dao.ClassesDao;
import com.wntime.modules.officer.entity.ClassesEntity;
import com.wntime.modules.officer.service.ClassesService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;


@Service("classesService")
public class ClassesServiceImpl extends ServiceImpl<ClassesDao, ClassesEntity> implements ClassesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        AdminUser user = ShiroUtils.getUserEntity();
        params.put("schoolIdList",PositionUtils.getSchoolIds(user));

        IPage<ClassesEntity> page = new Query<ClassesEntity>().getPage(params);
        params.put("page", page);
        page.setRecords(this.baseMapper.queryPage(params));

        return new PageUtils(page);
    }


}