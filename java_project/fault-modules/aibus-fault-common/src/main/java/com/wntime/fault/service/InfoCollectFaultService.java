package com.wntime.fault.service;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.validator.ValidatorUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.fault.entity.InfoCollectFaultEntity;

import java.util.Map;

/**
 * @desc 故障采集表
 *
 * @date 2020-08-25 13:48:11
 */
public interface InfoCollectFaultService extends IService<InfoCollectFaultEntity> {

    PageUtils queryPage(Map<String, Object> params);

    InfoCollectFaultEntity getDetailById(Long id);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

