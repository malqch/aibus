package com.wntime.event.dao;

import com.wntime.event.entity.InfoEventTypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.event.vo.InfoEventTypeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @desc 事件类型表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:34:24
 */
@Mapper
public interface InfoEventTypeDao extends BaseMapper<InfoEventTypeEntity> {

    List<InfoEventTypeVo> queryList();

    List<InfoEventTypeEntity> listWithUser(Map<String, Object> params);
}
