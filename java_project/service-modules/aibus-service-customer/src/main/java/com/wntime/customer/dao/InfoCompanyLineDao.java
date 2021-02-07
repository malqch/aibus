package com.wntime.customer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.customer.entity.InfoCompanyLineEntity;
import com.wntime.modules.gemfire.vo.LineStationInfoVo;
import com.wntime.service.common.vo.BusLineVo;
import com.wntime.service.common.vo.BusStationVo;
import com.wntime.service.common.vo.CompanyLineNameVo;
import com.wntime.service.common.vo.CompanyLineStationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @desc 公交线路表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:04:05
 */
@Mapper
public interface InfoCompanyLineDao extends BaseMapper<InfoCompanyLineEntity> {

    InfoCompanyLineEntity getDetailById(Long id);

    InfoCompanyLineEntity getDetailInfoById(Long id);

    void deleteLineStation(Long companyLineId);

    BusLineVo queryBusLineInfo(@Param("busId")long busId);

    BusLineVo queryBusLineInfoByCompanyLineId(@Param("companyLineId")long companyLineId);

    List<InfoCompanyLineEntity> queryPageListByCompanyId(Map<String, Object> params);

    List<InfoCompanyLineEntity> getCompanyLineByCompanyId(Long companyId);

    List<BusLineVo> queryCompanyBusLineInfo(@Param("busCompanyId")long busCompanyId);

    List<LineStationInfoVo> queryBusCompanyLineStations(@Param("busId")long busId);

    List<BusStationVo> queryCompanyLineStations(@Param("companyLineId")long companyLineId);

    List<InfoCompanyLineEntity> getCompanyLineCodeByCompanyIdList(@Param("companyIdList")List<Long> companyIdList);

    List<CompanyLineStationVo> queryCompanyLineOverview(@Param("busCompanyId")long busCompanyId);

    List<CompanyLineNameVo> queryCompanyLineNames(@Param("busCompanyId")long busCompanyId);

    List<BusLineVo> queryCompanyLineByName(@Param("busCompanyId")long busCompanyId,@Param("companyLineName")String companyLineName);

    /**
     * 查询公交公司的一辆公交车id
     * @param companyId
     * @return
     */
    Long queryCompanyLineBusId(@Param("companyId")long companyId);
}
