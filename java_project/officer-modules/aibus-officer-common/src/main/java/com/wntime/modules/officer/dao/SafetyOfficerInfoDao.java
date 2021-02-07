package com.wntime.modules.officer.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wntime.modules.officer.dto.SafetyOfficerInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SafetyOfficerInfoDao extends BaseMapper<SafetyOfficerInfoDto> {

    IPage<SafetyOfficerInfoDto> query(IPage page,  @Param(Constants.WRAPPER) Wrapper<SafetyOfficerInfoDto> wrapper);

    SafetyOfficerInfoDto query(@Param(Constants.WRAPPER) Wrapper<SafetyOfficerInfoDto> wrapper);

}
