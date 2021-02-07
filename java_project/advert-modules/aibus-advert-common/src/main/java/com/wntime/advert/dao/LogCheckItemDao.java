package com.wntime.advert.dao;

import com.wntime.advert.entity.LogCheckItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.advert.vo.ItemVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @desc 审核违规表
 * 
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Mapper
public interface LogCheckItemDao extends BaseMapper<LogCheckItemEntity> {

    List<ItemVo> listByAdvertise(Long advertiseId);
}
