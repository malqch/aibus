package com.wntime.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.customer.entity.InfoBusCompanyEntity;
import com.wntime.customer.vo.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @desc 公交公司表
 * @date 2020-08-25 14:04:05
 */
public interface InfoBusCompanyService extends IService<InfoBusCompanyEntity> {

    PageUtils queryPage(Map<String, Object> params);

    InfoBusCompanyVo getDetailById(Long id);

    void save(InfoBusCompanyEntity infoBusCompanyEntity, Long userId);

    void updateById(InfoBusCompanyEntity infoBusCompanyEntity, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);

    InfoBusCompanyEntityWithAreaVO getByIdWithArea(Serializable id);

    List<StatisticsResultVo> getCompanyStatistics(Map<String, Object> params);

    List<InfoBusCompanyVo> getCompanyStatListByAreaId(Map<String, Object> params);

    List<InfoBusCompanyVo> getCompanyListByAreaId(Map<String, Object> params);

    List<InfoBusCompanyEntity> getCompanyListAll(Map<String, Object> params);

    List<InfoBusCompanyStatVo> getCompanyBusTypeDeliveryListByAreaId(Map<String, Object> params);

    List<InfoBusCompanyStatVo> getCompanyBusTypeOutDateListByAreaId(Map<String, Object> params);

    List<InfoCompanyBusTypeStatVo> getCompanyBusTypeDeliveryListByCompanyId(Map<String, Object> params);

    List<InfoCompanyBusTypeStatVo> getCompanyBusTypeOutDateListByCompanyId(Map<String, Object> params);

    ValidCompanyVO getValidCompanyListByUserId(Long userId);
}

