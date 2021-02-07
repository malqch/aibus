package com.wntime.service.dao;

import com.wntime.service.common.vo.BusInfoBatteryTypeVo;
import com.wntime.service.entity.InfoBatteryTypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.service.vo.BatteryTypeInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @desc 电池类型表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:28:17
 */
@Mapper
public interface InfoBatteryTypeDao extends BaseMapper<InfoBatteryTypeEntity> {

    List<InfoBatteryTypeEntity> queryPageList(Map<String, Object> params);

    BusInfoBatteryTypeVo queryByCode(String code);

    Optional<BatteryTypeInfoVo> queryBusBatteryTypeInfo(@Param("busId")long busId);
}
