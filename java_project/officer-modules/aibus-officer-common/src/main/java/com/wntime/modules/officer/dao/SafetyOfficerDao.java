package com.wntime.modules.officer.dao;

import com.wntime.modules.officer.entity.SafetyOfficerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @desc 校车安全员
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@Mapper
public interface SafetyOfficerDao extends BaseMapper<SafetyOfficerEntity> {

    List<SafetyOfficerEntity> getPrimarySafetyOfficerList();

    /***
     * @Author Buxl
     * @Description 根据证件号查询安全员信息
     * @Date 15:02 2021/1/23
     * @Param [idNo]
     * @return com.wntime.modules.officer.entity.SafetyOfficerEntity
     **/
    SafetyOfficerEntity getSafetyOfficerByIdNo(String idNo);

}
