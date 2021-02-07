package com.wntime.modules.officer.service.impl;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wntime.modules.officer.dao.StudentLineSeatDao;
import com.wntime.modules.officer.entity.StudentLineSeatEntity;
import com.wntime.modules.officer.service.StudentLineSeatService;


@Service("studentLineSeatService")
public class StudentLineSeatServiceImpl extends ServiceImpl<StudentLineSeatDao, StudentLineSeatEntity> implements StudentLineSeatService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StudentLineSeatEntity> page = this.page(
                new Query<StudentLineSeatEntity>().getPage(params),
                new QueryWrapper<StudentLineSeatEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void copyLineSeatID() {
        this.baseMapper.copyLineSeatId();
    }

}