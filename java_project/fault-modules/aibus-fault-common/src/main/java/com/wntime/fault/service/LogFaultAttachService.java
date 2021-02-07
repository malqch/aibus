package com.wntime.fault.service;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.validator.ValidatorUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.fault.entity.LogFaultAttachEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 故障附件表
 *
 * @date 2020-08-25 13:48:11
 */
public interface LogFaultAttachService extends IService<LogFaultAttachEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<LogFaultAttachEntity> queryListByEventId(Long faultDetailId);

    void saveFaultAttach(long busId,Map<String, Object> tags,Long faultDetailId,long faultDate);
}

