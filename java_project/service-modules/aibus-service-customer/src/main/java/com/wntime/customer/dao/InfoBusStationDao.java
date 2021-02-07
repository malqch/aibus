package com.wntime.customer.dao;

import com.wntime.customer.entity.InfoBusStationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.customer.vo.BusStationDetailVO;
import com.wntime.customer.vo.InfoCompanyInitVo;
import com.wntime.modules.gemfire.vo.LineStationInfoVo;
import com.wntime.service.common.vo.BusStationVo;
import com.wntime.service.common.vo.InfoBusStationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @desc 公交车站表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:00:25
 */
@Mapper
public interface InfoBusStationDao extends BaseMapper<InfoBusStationEntity> {

    List<InfoBusStationEntity> queryPageList(Map<String, Object> params);

    InfoBusStationEntity getDetailInfoById(Long busStationId);

    List<InfoBusStationEntity> getStationListByCompanyId(Long companyId);

    List<InfoBusStationEntity> getStationListByCompanyLineId(Long companyLineId);

    List<InfoBusStationEntity> getStationAllByCompanyArea(Long companyId);

    InfoCompanyInitVo getInitInfoByBusStation(@Param(value = "eventTargetCode") String eventTargetCode,
                                              @Param(value = "companyIdList") List<Long> companyIdList);

    BusStationDetailVO getBusStationDetailById(Long busStationId);

    List<BusStationVo> getStationsByCompanyAndLine(Long companyId,Long companyLineId,String busStationName);

    LineStationInfoVo queryBusStationByBusCompanyId(@Param("companyLineId")Long companyLineId,@Param("busStationId")long busStationId);

    List<InfoBusStationVo> queryList();
}
