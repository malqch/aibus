package com.wntime.advert.dao;

import com.wntime.advert.entity.OrderDeliveryAreaEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.advert.vo.AdvertiseDeliveryDetailVo;
import com.wntime.advert.vo.DeliveryLineVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @desc 投放范围表
 * 
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Mapper
public interface OrderDeliveryAreaDao extends BaseMapper<OrderDeliveryAreaEntity> {

    List<AdvertiseDeliveryDetailVo> queryDeliveryAreaDetailByCompanyLineId(@Param("companyLineId")long companyLineId);

    List<DeliveryLineVo> listLineCodeByAdvertise(Long advertiseId);

    List<String> listStationByAdvertiseId(Long advertiseId);
}
