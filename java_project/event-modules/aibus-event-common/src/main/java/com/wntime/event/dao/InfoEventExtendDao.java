package com.wntime.event.dao;

import com.wntime.event.entity.InfoEventExtendEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.event.vo.InfoEventExtendDetailVo;
import com.wntime.event.vo.InfoEventExtendVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @desc 事件拓展表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:34:24
 */
@Mapper
public interface InfoEventExtendDao extends BaseMapper<InfoEventExtendEntity> {

    List<InfoEventExtendVo> queryList();

    List<InfoEventExtendDetailVo> listDetail(Long collectEventId);
}
