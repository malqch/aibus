package com.wntime.fault.dao;

import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.fault.entity.InfoFaultTypeEntity;
import com.wntime.fault.vo.FaultCompanyBusStatVo;
import com.wntime.fault.vo.FaultTypeStatVo;
import com.wntime.fault.vo.InfoFaultTypeVo;
import com.wntime.service.common.vo.TypeCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @desc 故障类型表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@Mapper
public interface InfoFaultTypeDao extends BaseMapper<InfoFaultTypeEntity> {

    void updateBatch(List<Long> ids, Long userId);

    List<InfoFaultTypeEntity> queryPageList(Map<String, Object> params);

    List<Map<String, Object>> countBusGroupByFaultType(Long companyId);

    List<FaultTypeStatVo> getCountFaultTypeByBusIds(@Param(value = "busMile") Long busMile ,
                                                    @Param(value = "searchDate") Date searchDate,
                                                    @Param(value = "busIdList") List<Long> busIdList);

    List<TypeCountVo> getCountFaultByBusType(@Param(value = "busTypeId") Long busTypeId ,
                                             @Param(value = "searchDate") Date searchDate,
                                             @Param(value = "busIdList") List<Long> busIdList);

    List<FaultCompanyBusStatVo> getFaultStatByCompanyBusType(
                                                             @Param(value = "searchDate") Date searchDate,
                                                             @Param(value = "companyIdList") List<Long> companyIdList);

    List<InfoFaultTypeVo> queryList();
}
