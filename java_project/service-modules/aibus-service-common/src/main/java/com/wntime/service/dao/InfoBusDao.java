package com.wntime.service.dao;

import com.wntime.service.common.entity.InfoBusEntity;
import com.wntime.service.common.vo.StatisticsResultVo;
import com.wntime.service.common.vo.BusInfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.service.common.vo.BusStatusVO;
import com.wntime.service.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @desc 公交车表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:28:17
 */
@Mapper
public interface InfoBusDao extends BaseMapper<InfoBusEntity> {

    List<InfoBusEntity> queryPageList(Map<String, Object> params);

    /**
     * 查询区域/公司权限：所有车辆
     * @param areaId
     * @return
     */
    List<InfoBusEntity> getAllBusByAreaId(@Param(value = "areaId") Long areaId,
                                          @Param(value = "companyIdList") List<Long> companyIdList);
    /**
     * 查询区域和子区域/公司权限：所有车辆
     * @param areaIdList
     * @param companyIdList
     * @return
     */
    List<InfoBusEntity> getAllBusByAreaIdListAndCompanyId(@Param(value = "areaIdList") List<Long> areaIdList,
                                          @Param(value = "companyIdList") List<Long> companyIdList);

    List<InfoBusEntity> getAllBusByQueryAreaId(Map<String, Object> params);

    /**
     * 统计区域下固定事件数量（可燃气体、重点人员、交通违章、特种卡违规）
     * @param busIdList
     * @return
     */
    List<StatisticsResultVo> getBusEventStatByAreaId(@Param(value = "busIdList") List<Long> busIdList);

    List<String> getVinListByFaultTypeIdAndCompanyId(Long faultTypeId, Long companyId);

    List<BusInfoVo> queryBusInfoByVinOrPlateCode(@Param("factoryId") long factoryId,@Param("keyWord")String keyWord, @Param("size")int size);

    List<String> getVinListByBusStatusAndCompanyId(Long busStatus, Long companyId);

    List<String> getVinListByCompanyId(Long companyId);

    List<String> getPlateCodeListByCompanyId(Long companyId);

    List<InfoBusEntity> getVinNoDeliveryList();

    List<AfterSalesBusInfoVO> getBusListByStatusAndCompanyIdAndVin(Map<String, Object> params);

    List<AfterSalesMaintainBusInfoVO> getMaintainBusListAndCompanyIdAndVin(Map<String, Object> params);

    List<BusStatusVO> getBusStatusList();

    InfoBusEntity getDetailInfoById(Long busId);

    BusStatusVO getBusStatusByCode(String busStatusCode);

    int changeBusStatus(Long busId, Long busStatusId);

    BusStatusVO getBusStatusById(Long busStatusId);

    List<InfoBusEntity> getBusListByCompanyId(Long companyId);

    BusVo queryBusByVin(@Param("vinCode")String vinCode);

    int queryBusLoadCount(@Param("busId")long busId);

    long getBusCountByCompanyId(Long companyId);

    List<Map<String, Object>> countByRunStatusAndCompanyId(Long companyId);

    List<Map<String, Object>> countByBusStatusAndCompanyId(Long companyId);

    int updateCarStatus(@Param("busId")long busId,@Param("carStatus")int carStatus);

    List<BusStatusCountVo> queryCompanyBusStatus(@Param("companyId")long companyId);

    BusStatusCodeVo queryBusStatusAndCompanyId(@Param("busId")long busId);

    List<Long> queryBusIdsByBusTypeId(@Param("busTypeId")long busTypeId);

    boolean isBusPlanned(Long busId);

    BusVo queryBusInfo(@Param("busId")long busId);
}
