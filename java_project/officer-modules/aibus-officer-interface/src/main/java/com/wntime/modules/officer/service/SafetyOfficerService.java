package com.wntime.modules.officer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.modules.officer.dto.SafetyOfficerInfoDto;
import com.wntime.modules.officer.entity.SafetyOfficerEntity;
import com.wntime.modules.officer.from.SafetyOfficerFrom;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @desc 校车安全员
 *
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
public interface SafetyOfficerService extends IService<SafetyOfficerEntity> {

    PageUtils queryPage(Map<String, Object> params);

    SafetyOfficerInfoDto queryOne(Long id);

    List<SafetyOfficerEntity> getPrimarySafetyOfficerList();

    /***
     * @Author Buxl
     * @Description 根据证件号查询安全员信息
     * @Date 15:02 2021/1/23
     * @Param [idNo]
     * @return com.wntime.modules.officer.entity.SafetyOfficerEntity
     **/
    SafetyOfficerEntity getSafetyOfficerByIdNo(String idNo);

    @Transactional(rollbackFor = Exception.class)
    void saveSafetyOfficer(SafetyOfficerFrom from);
}

