package com.wntime.event.dao;

import com.wntime.event.entity.LogEventAttachEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.event.vo.LogEventAttachVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @desc 事件附件表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:34:24
 */
@Mapper
public interface LogEventAttachDao extends BaseMapper<LogEventAttachEntity> {

    List<LogEventAttachVo> listAttachByDetailId(Long eventDetailId);
}
