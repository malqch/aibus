package com.wntime.service.service;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.validator.ValidatorUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.service.entity.InfoBusTypeEntity;
import com.wntime.service.vo.InfoDeviceTypeVo;

import java.util.List;
import java.util.Map;

/**
 * @desc 公交车型
 * @date 2020-08-25 14:28:17
 */
public interface InfoBusTypeService extends IService<InfoBusTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    InfoBusTypeEntity getDetailById(Long id);

    void save(InfoBusTypeEntity infoBusTypeEntity, Long userId);

    void updateById(InfoBusTypeEntity infoBusTypeEntity, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);

    List<InfoBusTypeEntity> getAllList();

    List<InfoDeviceTypeVo> queryDeviceType();
}