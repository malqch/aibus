package com.wntime.event.dao;

import com.wntime.event.entity.InfoEventTargetEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.event.vo.InfoEventTargetVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @desc 事件标签表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:34:24
 */
@Mapper
public interface InfoEventTargetDao extends BaseMapper<InfoEventTargetEntity> {

    List<InfoEventTargetEntity> getTrafficEventTypeList();

    List<InfoEventTargetVo> queryList();

    List<InfoEventTargetEntity> listWithUser(Map<String, Object> params);
}
