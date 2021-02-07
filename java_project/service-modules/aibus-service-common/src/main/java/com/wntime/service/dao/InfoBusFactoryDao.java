package com.wntime.service.dao;

import com.wntime.service.common.vo.AreaVo;
import com.wntime.service.entity.InfoBusFactoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author psp
 * @desc 公交车厂表
 * @date 2020-08-25 14:28:17
 */
@Mapper
public interface InfoBusFactoryDao extends BaseMapper<InfoBusFactoryEntity> {
    List<InfoBusFactoryEntity> queryPageList(Map<String, Object> params);

    AreaVo queryFactoryAreaInfo(@Param("factoryId")long factoryId);
}
