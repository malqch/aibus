package com.wntime.fault.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.fault.entity.InfoFaultTypeEntity;
import com.wntime.fault.vo.FaultCompanyBusStatVo;
import com.wntime.fault.vo.FaultTypeStatVo;
import com.wntime.service.common.vo.TypeCountVo;

import java.util.List;
import java.util.Map;

/**
 * @desc 故障类型表
 *
 * @date 2020-08-25 13:48:11
 */
public interface InfoFaultTypeService extends IService<InfoFaultTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 故障统计:根据车辆列表统计故障类型数量
     * @param busIdList
     * @return
     */
    List<FaultTypeStatVo> getCountFaultTypeByBusIds(Map<String, Object> params,List<Long> busIdList);

    /**
     * 故障统计:根据车辆统计故障类型数量
     * @param busIdList
     * @return
     */
    List<TypeCountVo> getCountFaultByBusType(Map<String, Object> params, List<Long> busIdList);

    /**
     * 分客户、分车型故障数量统计
     * @param params
     * @return
     */
    List<FaultCompanyBusStatVo> getFaultStatByCompanyBusType(Map<String, Object> params);

    List<Map<String, Object>> countBusByCompanyIdGroupByFaultType(Long companyId);

    void deleteBatch (List<Long> ids, Long userId);
}

