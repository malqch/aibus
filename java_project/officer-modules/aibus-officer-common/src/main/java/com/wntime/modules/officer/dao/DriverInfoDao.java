package com.wntime.modules.officer.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wntime.modules.officer.dto.DriverInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DriverInfoDao extends BaseMapper<DriverInfoDto> {

    IPage<DriverInfoDto> query(IPage page,  @Param(Constants.WRAPPER) Wrapper<DriverInfoDto> wrapper);

    DriverInfoDto query(@Param(Constants.WRAPPER) Wrapper<DriverInfoDto> wrapper);

}
