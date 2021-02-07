package com.wntime.fault.service;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.validator.ValidatorUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.fault.entity.InfoFaultSuggestionEntity;

import java.util.Map;

/**
 * @desc 故障方案表
 *
 * @date 2020-08-25 13:48:11
 */
public interface InfoFaultSuggestionService extends IService<InfoFaultSuggestionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

