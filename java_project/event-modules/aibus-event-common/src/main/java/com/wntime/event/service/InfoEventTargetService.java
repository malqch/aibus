package com.wntime.event.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.event.entity.InfoEventTargetEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 事件标签表
 *
 * @date 2020-08-25 13:34:24
 */
public interface InfoEventTargetService extends IService<InfoEventTargetEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<InfoEventTargetEntity> getTrafficEventTypeList();

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

