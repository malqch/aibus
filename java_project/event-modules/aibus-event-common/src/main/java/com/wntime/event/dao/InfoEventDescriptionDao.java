package com.wntime.event.dao;

import com.wntime.event.entity.InfoEventDescriptionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author psp
 * @date 2020-09-01 15:22:13
 */
@Mapper
public interface InfoEventDescriptionDao extends BaseMapper<InfoEventDescriptionEntity> {

    List<InfoEventDescriptionEntity> queryPageList(Map<String, Object> params);

    String queryDescriptionByTargetId(@Param("eventTargetId")long eventTargetId);
}
