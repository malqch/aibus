package com.wntime.event.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.event.entity.InfoEventLevelEntity;

import java.util.Map;

/**
 * @desc 事件级别表
 *
 * @date 2020-08-25 13:34:24
 */
public interface InfoEventLevelService extends IService<InfoEventLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

