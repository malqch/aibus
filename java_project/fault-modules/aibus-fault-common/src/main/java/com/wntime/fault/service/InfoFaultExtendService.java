package com.wntime.fault.service;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.validator.ValidatorUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.fault.entity.InfoFaultExtendEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 故障扩展表
 *
 * @date 2020-08-25 13:48:11
 */
public interface InfoFaultExtendService extends IService<InfoFaultExtendEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<InfoFaultExtendEntity> queryList(Map<String, Object> params);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

