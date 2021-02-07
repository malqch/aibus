package com.wntime.advert.dao;

import com.wntime.advert.entity.OrderAdvertiseDeliveryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.advert.vo.AdvertiseCardVo;
import com.wntime.advert.vo.AdvertiseInfoVo;
import com.wntime.advert.vo.AdvertiseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @desc 广告投放单
 * 
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Mapper
public interface OrderAdvertiseDeliveryDao extends BaseMapper<OrderAdvertiseDeliveryEntity> {

    List<AdvertiseCardVo> list(@Param("advertiseNo") String advertiseNo, @Param("statusList") List<Integer> statusList, @Param("userId") Long userId,@Param("tabGroup")String tabGroup);

    AdvertiseInfoVo getVoById(Long advertiseId);

    String getBaseDeviceName();
}
