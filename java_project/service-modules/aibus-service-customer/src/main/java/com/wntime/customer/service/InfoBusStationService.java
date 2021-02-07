package com.wntime.customer.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.customer.entity.InfoBusStationEntity;
import com.wntime.customer.vo.BusStationDetailVO;
import com.wntime.customer.vo.InfoCompanyInitVo;

import java.util.List;
import java.util.Map;

/**
 * @desc 公交车站表
 * @date 2020-08-25 14:00:25
 */
public interface InfoBusStationService extends IService<InfoBusStationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    InfoBusStationEntity getDetailById(Long id);

    void save(InfoBusStationEntity infoBusStationEntity, Long userId);

    void updateById(InfoBusStationEntity infoBusStationEntity, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);

    InfoCompanyInitVo getInitInfoByBusStation(String eventTargetCode, List<Long> companyIdList);

    List<InfoBusStationEntity> getStationListByCompanyId(Long companyId);

    List<InfoBusStationEntity> getStationListByCompanyLineId(Long companyLineId);

    List<InfoBusStationEntity> getStationAllByCompanyArea(Long companyId);

    BusStationDetailVO getBusStationDetailById(Long busStationId);
}

