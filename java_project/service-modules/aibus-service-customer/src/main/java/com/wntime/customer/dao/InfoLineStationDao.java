package com.wntime.customer.dao;

import com.wntime.customer.entity.InfoCompanyLineEntity;
import com.wntime.customer.entity.InfoLineStationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.customer.vo.InfoLineStationVo;
import com.wntime.service.common.vo.LineStationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @desc 线路车站表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:23:25
 */
@Mapper
public interface InfoLineStationDao extends BaseMapper<InfoLineStationEntity> {


    List<Long> queryBusIdByLineStation(@Param("busStationId")long busStationId);

    List<LineStationVo> queryList();

    List<InfoLineStationVo> listByLineId(Long companyLineId);

    List<InfoLineStationVo> listByCompanyIdList(@Param("companyIdList") List<Long> companyIdList);
}
