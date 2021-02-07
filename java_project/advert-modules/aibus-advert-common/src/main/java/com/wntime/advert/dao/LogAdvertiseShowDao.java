package com.wntime.advert.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.advert.entity.LogAdvertiseShowEntity;
import com.wntime.advert.vo.CompanyLinePeopleCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @desc 广告播放日志
 * 
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Mapper
public interface LogAdvertiseShowDao extends BaseMapper<LogAdvertiseShowEntity> {
    List<LogAdvertiseShowEntity> queryPageList(Map<String, Object> params);

    LogAdvertiseShowEntity queryBusLatelyAdvertiseLog(@Param("busId")long busId, @Param("companyLineId")long companyLineId,@Param("advertiseDeliveryId")long advertiseDeliveryId);

    Long queryBusLatelyAdvertiseLogId(@Param("busId")long busId, @Param("companyLineId")long companyLineId);

    void increaseLogPeopleCount(@Param("advertiseShowId")Long advertiseShowId);

    List<CompanyLinePeopleCountVo> queryPeopleCountGroupByCompanyLine(@Param("factoryId")long factoryId,@Param("size")int size);
}
