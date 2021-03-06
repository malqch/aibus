package com.wntime.event.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.event.entity.InfoEventExtendEntity;
import com.wntime.event.vo.InfoEventExtendDetailVo;

import java.util.List;
import java.util.Map;

/**
 * @desc 事件拓展表
 *
 * @date 2020-08-25 13:34:24
 */
public interface InfoEventExtendService extends IService<InfoEventExtendEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);

    List<InfoEventExtendDetailVo> listDetail(Long collectEventId);
}

