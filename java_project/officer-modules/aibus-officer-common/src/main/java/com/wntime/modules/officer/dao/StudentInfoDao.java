package com.wntime.modules.officer.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wntime.modules.officer.dto.StudentInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface StudentInfoDao extends BaseMapper<StudentInfoDto> {

    IPage<StudentInfoDto> query(IPage page, Map<String, Object> params, @Param(Constants.WRAPPER) Wrapper<StudentInfoDto> wrapper);

    StudentInfoDto query(@Param(Constants.WRAPPER) Wrapper<StudentInfoDto> wrapper);

}
