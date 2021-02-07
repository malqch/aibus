package com.wntime.customer.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.customer.entity.InfoLineStationEntity;
import com.wntime.customer.vo.CompanyLineWithStationVo;
import com.wntime.customer.vo.InfoLineStationVo;
import com.wntime.customer.vo.LogisticCompanyLineVo;

import java.util.List;
import java.util.Map;

/**
 * @desc 线路车站表
 *
 * @date 2020-08-25 14:23:25
 */
public interface InfoLineStationService extends IService<InfoLineStationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CompanyLineWithStationVo> listByLineCode(String companyLineCode);

    List<LogisticCompanyLineVo> listByCompanyIdList(List<Long> userCompanyIdList);

    List<InfoLineStationVo> listByLineId(Long companyLineId);
}

