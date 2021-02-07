package com.wntime.customer.dao;

import com.wntime.customer.entity.OrderBusCompanyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.service.common.vo.BusFactorySalesVo;
import com.wntime.customer.vo.OrderBusCompanyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author psp
 * @desc 公交订单表
 * @date 2020-09-04 10:03:34
 */
@Mapper
public interface OrderBusCompanyDao extends BaseMapper<OrderBusCompanyEntity> {

    List<OrderBusCompanyVo> queryPageList(Map<String, Object> params);

    List<OrderBusCompanyVo> getByOrderStatus(List<Long> companyIdList,Long companyId, Integer isCompleted);

    OrderBusCompanyVo getDetailById(Long orderId);


    List<BusFactorySalesVo> queryFactorySalesVolume(@Param("factoryId")long factoryId);
}
