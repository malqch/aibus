package com.wntime.advert.dao;

import com.wntime.advert.entity.InfoAdvertiseTargetEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.advert.vo.InfoAdvertiseTargetVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @desc 广告标签表
 * 
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Mapper
public interface InfoAdvertiseTargetDao extends BaseMapper<InfoAdvertiseTargetEntity> {

    List<InfoAdvertiseTargetEntity> listByGroup(String group);

    List<InfoAdvertiseTargetVo> queryList();

    List<InfoAdvertiseTargetEntity> listWithUser(Map<String, Object> params);

    List<InfoAdvertiseTargetEntity> listByAdvertiseIdAndGroup(Long advertiseId, String group);
}
