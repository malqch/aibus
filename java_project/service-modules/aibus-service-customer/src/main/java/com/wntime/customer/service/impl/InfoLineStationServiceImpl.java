package com.wntime.customer.service.impl;

import com.wntime.common.exception.RRException;
import com.wntime.common.validator.Assert;
import com.wntime.customer.entity.InfoCompanyLineEntity;
import com.wntime.customer.service.InfoCompanyLineService;
import com.wntime.customer.vo.CompanyLineWithStationVo;
import com.wntime.customer.vo.InfoLineStationVo;
import com.wntime.customer.vo.LogisticCompanyLineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.customer.dao.InfoLineStationDao;
import com.wntime.customer.entity.InfoLineStationEntity;
import com.wntime.customer.service.InfoLineStationService;


@Service("infoLineStationService")
public class InfoLineStationServiceImpl extends ServiceImpl<InfoLineStationDao, InfoLineStationEntity> implements InfoLineStationService {

    @Autowired
    private InfoCompanyLineService infoCompanyLineService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InfoLineStationEntity> page = this.page(
                new Query<InfoLineStationEntity>().getPage(params),
                new QueryWrapper<InfoLineStationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CompanyLineWithStationVo> listByLineCode(String companyLineCode) {
        List<CompanyLineWithStationVo> list = new ArrayList<>();
        List<InfoCompanyLineEntity> lineList = infoCompanyLineService.list(
                new QueryWrapper<InfoCompanyLineEntity>()
                        .eq("is_deleted", 0)
                        .eq("is_enabled", 1)
                        .eq("company_line_code", companyLineCode)
        );
        Assert.isEmpty(lineList, "没有对应的线路！");

        if (lineList.size() > 2) {
            throw new RRException("线路数量不合法！");
        }
        for (InfoCompanyLineEntity line : lineList) {
            CompanyLineWithStationVo companyLineWithStationVo = new CompanyLineWithStationVo();
            companyLineWithStationVo.setCompanyLineId(line.getCompanyLineId());
            companyLineWithStationVo.setCompanyLineName(line.getCompanyLineName());
            companyLineWithStationVo.setCompanyLineCode(line.getCompanyLineCode());
            companyLineWithStationVo.setStationList(listByLineId(line.getCompanyLineId()));

            list.add(companyLineWithStationVo);
        }
        return list;
    }

    @Override
    public List<LogisticCompanyLineVo> listByCompanyIdList(List<Long> userCompanyIdList) {

        List<InfoLineStationVo> lineStationList = getBaseMapper().listByCompanyIdList(userCompanyIdList);

        List<LogisticCompanyLineVo> lineList = new ArrayList<>();

        Map<String, List<InfoLineStationVo>> listMap = lineStationList.stream().collect(Collectors.groupingBy(InfoLineStationVo::getCompanyLineCode));
        for (Map.Entry<String, List<InfoLineStationVo>> entry : listMap.entrySet()) {
            LogisticCompanyLineVo logisticCompanyLineVo = new LogisticCompanyLineVo();
            String companyLineCode = entry.getKey();
            List<InfoLineStationVo> lineStationListByCode = entry.getValue();
            Map<Long, List<InfoLineStationVo>> lineStationListMapById = lineStationListByCode.stream().collect(Collectors.groupingBy(InfoLineStationVo::getCompanyLineId));

            List<List<InfoLineStationVo>> list = new ArrayList<>(lineStationListMapById.values());

            logisticCompanyLineVo.setCompanyLineCode(companyLineCode);
            logisticCompanyLineVo.setList(list);
            lineList.add(logisticCompanyLineVo);
        }
        return lineList;
    }

    @Override
    public List<InfoLineStationVo> listByLineId(Long companyLineId) {
        return getBaseMapper().listByLineId(companyLineId);
    }


}
