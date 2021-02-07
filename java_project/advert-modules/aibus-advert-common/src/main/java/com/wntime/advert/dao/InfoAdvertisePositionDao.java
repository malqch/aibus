package com.wntime.advert.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.advert.entity.InfoAdvertisePositionEntity;
import com.wntime.advert.entity.InfoAdvertiseTargetEntity;
import com.wntime.advert.vo.AdvertisePositionVo;
import com.wntime.advert.vo.InfoAdvertisePositionVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Mapper
public interface InfoAdvertisePositionDao extends BaseMapper<InfoAdvertisePositionEntity> {

    List<AdvertisePositionVo> listAll();

    AdvertisePositionVo getOne(Long id);

    List<InfoAdvertisePositionVo> queryList();


    List<InfoAdvertisePositionEntity> listWithUser(Map<String, Object> params);

    AdvertisePositionVo getOneByCode(String code);
}
