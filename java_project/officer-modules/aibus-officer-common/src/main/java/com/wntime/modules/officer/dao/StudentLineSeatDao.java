package com.wntime.modules.officer.dao;

import com.wntime.modules.officer.entity.StudentLineSeatEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @desc 学生线路与坐位
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-20 20:24:11
 */
@Mapper
public interface StudentLineSeatDao extends BaseMapper<StudentLineSeatEntity> {

    void copyLineSeatId();
}
