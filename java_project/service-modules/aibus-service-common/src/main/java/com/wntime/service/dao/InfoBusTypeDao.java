package com.wntime.service.dao;

import com.wntime.service.common.vo.BusInfoMotorTypeVo;
import com.wntime.service.common.vo.BusInfoTypeVo;
import com.wntime.service.entity.InfoBusTypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.service.vo.InfoDeviceTypeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import java.util.List;

/**
 * @author Mark
 * @desc 公交车型
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:28:17
 */
@Mapper
public interface InfoBusTypeDao extends BaseMapper<InfoBusTypeEntity> {
    List<InfoBusTypeEntity> queryPageList(Map<String, Object> params);

    List<InfoDeviceTypeVo> queryList();

    BusInfoTypeVo queryByCode(String code);
}
