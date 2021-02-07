package com.wntime.advert.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.advert.entity.InfoAdvertiseTargetEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 广告标签表
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
public interface InfoAdvertiseTargetService extends IService<InfoAdvertiseTargetEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<InfoAdvertiseTargetEntity> listByGroup(String group);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);

     List<InfoAdvertiseTargetEntity>listByAdvertiseIdAndGroup(Long advertiseId, String group);
}

