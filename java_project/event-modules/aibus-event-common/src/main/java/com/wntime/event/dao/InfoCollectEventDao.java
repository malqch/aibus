package com.wntime.event.dao;

import com.wntime.event.entity.InfoCollectEventEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.event.entity.InfoEventTypeEntity;
import com.wntime.event.vo.InfoCollectEventDetailVo;
import com.wntime.event.vo.InfoCollectEventVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @desc 事件采集表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:34:24
 */
@Mapper
public interface InfoCollectEventDao extends BaseMapper<InfoCollectEventEntity> {

    List<InfoCollectEventVo> queryList();

    List<InfoEventTypeEntity> listDetail(Map<String, Object> params);

    InfoCollectEventDetailVo getDetailById(Long collectEventId);
}
