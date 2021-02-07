package com.wntime.event.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.event.entity.InfoCollectEventEntity;
import com.wntime.event.vo.InfoCollectEventDetailVo;

import java.util.Map;

/**
 * @desc 事件采集表
 *
 * @date 2020-08-25 13:34:24
 */
public interface InfoCollectEventService extends IService<InfoCollectEventEntity> {

    PageUtils queryPage(Map<String, Object> params);

    InfoCollectEventDetailVo getDetailById(Long collectEventId);

    InfoCollectEventDetailVo getDetailWithExtendById(Long collectEventId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

