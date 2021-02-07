package com.wntime.fault.service;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.validator.ValidatorUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.fault.entity.InfoFaultLevelEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 故障级别表
 *
 * @date 2020-08-25 13:48:11
 */
public interface InfoFaultLevelService extends IService<InfoFaultLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<Map<String, Object>> countBusCompanyIdGroupByFaultLevel(Long companyId);

    void deleteBatch (List<Long> ids, Long userId);
}

