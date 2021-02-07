package com.wntime.modules.officer.dao;

import com.wntime.modules.officer.entity.ClassesEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @desc 班级信息
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@Mapper
public interface ClassesDao extends BaseMapper<ClassesEntity> {

    List<ClassesEntity> queryPage(Map<String, Object> params);

}
