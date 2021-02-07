package com.wntime.customer.dao;

import com.wntime.customer.vo.*;
import com.wntime.customer.entity.InfoBusCompanyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.service.common.vo.BusBaseInfoVo;
import com.wntime.service.common.vo.BusCompanyBaseInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @desc 公交公司表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:04:05
 */
@Mapper
public interface InfoBusCompanyDao extends BaseMapper<InfoBusCompanyEntity> {

    List<InfoBusCompanyVo> queryPageList(Map<String, Object> params);

    InfoBusCompanyVo getDetailById(Long id);

    InfoBusCompanyEntityWithAreaVO selectByIdWithArea(Serializable id);

    /**
     * 查询地区车辆交付数量
     * @return
     */
    int getBusDeliveryCountByAreaId(@Param(value = "companyIdList") List<Long> companyIdList);

    /**
     * 查询地区当前客户数量
     * @param areaId
     * @return
     */
    int getCompanyCountByAreaId(@Param(value = "areaId") Long areaId,
                                @Param(value = "companyIdList") List<Long> companyIdList);

    String getAreaNameShowByAreaId(@Param(value = "areaIdList") List<Long> areaIdList);

    /**
     * 查询客户列表（包括统计订单量、交付量）
     * @param areaIdList
     * @param companyIdList
     * @return
     */
    List<InfoBusCompanyVo> getCompanyStatListByAreaId(@Param(value = "areaIdList") List<Long> areaIdList,
                                                  @Param(value = "companyIdList") List<Long> companyIdList);

    /**
     * 查询客户列表
     * @param areaId
     * @param companyIdList
     * @return
     */
    List<InfoBusCompanyVo> getCompanyListByAreaId(@Param(value = "areaId") Long areaId,
                                                      @Param(value = "companyIdList") List<Long> companyIdList);

    List<InfoBusCompanyVo> getCompanyListByAreaIdList(@Param(value = "areaIdList") List<Long> areaIdList,
                                                  @Param(value = "companyIdList") List<Long> companyIdList);

    List<InfoBusCompanyStatVo> getCompanyBaseInfoListByAreaId(@Param(value = "areaIdList") List<Long> areaIdList,
                                                              @Param(value = "companyIdList") List<Long> companyIdList);

    /**
     * 按地区查询公司分车型订单总量、交付量
     * @param companyId
     * @return
     */
    List<InfoCompanyBusTypeStatVo> getDeliveryStatByBusTypeAndAreaId(@Param(value = "companyId") Long companyId);

    /**
     * 按地区查询公司分车型订单总量、出保量
     * @param companyId
     * @return
     */
    List<InfoCompanyBusTypeStatVo> getOutDateStatByBusTypeAndAreaId(@Param(value = "companyId") Long companyId);

    List<BusCompanyBaseInfoVo> queryChildrenCompanyList(@Param(value = "companyId") Long companyId);

    List<Long> getUserCompanyIdList(@Param(value = "userId")Long userId);

    ValidCompanyVO getValidCompanyListByUserId(Long userId);

    ValidCompanyVO queryCompanyAreaName(@Param("companyId")long companyId);

    int queryCompanyBusCount(@Param("companyId")long companyId);

    BusBaseInfoVo queryBusOrderBaseInfo(@Param("busId")long busId);
}
