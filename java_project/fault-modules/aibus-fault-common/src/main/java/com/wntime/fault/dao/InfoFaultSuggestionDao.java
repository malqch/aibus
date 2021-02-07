package com.wntime.fault.dao;

import com.wntime.fault.entity.InfoFaultSuggestionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @desc 故障方案表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@Mapper
public interface InfoFaultSuggestionDao extends BaseMapper<InfoFaultSuggestionEntity> {

    String querySuggestionByTargetId(@Param("targetId")long targetId);
}
