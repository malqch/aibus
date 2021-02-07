package com.wntime.service.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.service.entity.InfoMotorTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 电机类型表
 * @date 2020-08-31 11:07:44
 */
public interface InfoMotorTypeService extends IService<InfoMotorTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    InfoMotorTypeEntity getDetailById(Long id);

    List<InfoMotorTypeEntity> getAll();

    void save(InfoMotorTypeEntity infoMotorTypeEntity, Long userId);

    void updateById(InfoMotorTypeEntity infoMotorTypeEntity, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

