package com.wntime.event.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.event.entity.LogEventAttachEntity;
import com.wntime.event.vo.LogEventAttachVo;

import java.util.List;
import java.util.Map;

/**
 * @desc 事件附件表
 *
 * @date 2020-08-25 13:34:24
 */
public interface LogEventAttachService extends IService<LogEventAttachEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<LogEventAttachVo> listAttachByDetailId(Long eventDetailId);
}

