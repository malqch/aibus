package com.wntime.event.dao;

import com.wntime.event.entity.InfoEventLevelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.event.vo.InfoEventLevelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @desc 事件级别表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:34:24
 */
@Mapper
public interface InfoEventLevelDao extends BaseMapper<InfoEventLevelEntity> {

    List<InfoEventLevelVo> queryList();

    List<InfoEventLevelEntity> listWithUser(Map<String, Object> params);
}
