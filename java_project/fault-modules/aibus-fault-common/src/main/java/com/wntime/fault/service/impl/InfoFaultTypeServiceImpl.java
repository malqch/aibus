package com.wntime.fault.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wntime.common.utils.DateUtils;
import com.wntime.fault.vo.FaultCompanyBusStatVo;
import com.wntime.fault.vo.FaultTypeStatVo;
import com.wntime.service.common.vo.TypeCountVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.fault.dao.InfoFaultTypeDao;
import com.wntime.fault.entity.InfoFaultTypeEntity;
import com.wntime.fault.service.InfoFaultTypeService;


@Service("infoFaultTypeService")
public class InfoFaultTypeServiceImpl extends ServiceImpl<InfoFaultTypeDao, InfoFaultTypeEntity> implements InfoFaultTypeService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        int currPage = 1;
        int pageSize = 20;
        if (params.get("page") != null) {
            currPage = Integer.parseInt((String) params.get("page"));
        }
        if (params.get("page") != null) {
            pageSize = Integer.parseInt((String) params.get("limit"));
        }
        Page<InfoFaultTypeEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        return new PageUtils(page.setRecords(getBaseMapper().queryPageList(params)));
    }

    @Override
    public List<FaultTypeStatVo> getCountFaultTypeByBusIds(Map<String, Object> params,List<Long> busIdList) {
        Long miles = Long.parseLong(String.valueOf(params.get("busMile")));
        Date searchDate =DateUtils.stringToDate(String.valueOf(params.get("searchDate")),DateUtils.DATE_TIME_PATTERN);
        return getBaseMapper().getCountFaultTypeByBusIds(miles,searchDate, busIdList);
    }

    @Override
    public List<TypeCountVo> getCountFaultByBusType(Map<String, Object> params, List<Long> busIdList) {
        Long busTypeId = Long.parseLong(String.valueOf(params.get("busTypeId")));
        Date searchDate =DateUtils.stringToDate(String.valueOf(params.get("searchDate")),DateUtils.DATE_TIME_PATTERN);
        return getBaseMapper().getCountFaultByBusType(busTypeId,searchDate, busIdList);
    }

    @Override
    public List<FaultCompanyBusStatVo> getFaultStatByCompanyBusType(Map<String, Object> params) {
        Date searchDate =DateUtils.stringToDate(String.valueOf(params.get("searchDate")),DateUtils.DATE_TIME_PATTERN);
        List<Long> companyIdList = (List<Long>)params.get("companyIdList");
        return getBaseMapper().getFaultStatByCompanyBusType(searchDate, companyIdList);
    }

    @Override
    public List<Map<String, Object>> countBusByCompanyIdGroupByFaultType(Long companyId) {
        return getBaseMapper().countBusGroupByFaultType(companyId);
    }

    @Override
    public void deleteBatch(List<Long> ids, Long userId) {
        getBaseMapper().updateBatch(ids, userId);
    }
}
