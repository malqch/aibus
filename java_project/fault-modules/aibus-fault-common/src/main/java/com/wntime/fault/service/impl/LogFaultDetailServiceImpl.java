package com.wntime.fault.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.datasource.annotation.DataSource;
import com.wntime.fault.dao.*;
import com.wntime.fault.entity.LogFaultDetailEntity;
import com.wntime.fault.form.FaultReportForm;
import com.wntime.fault.region.BusFaultInfo;
import com.wntime.fault.region.BusFaultLevelStatistic;
import com.wntime.fault.region.FaultInfo;
import com.wntime.fault.repo.BusFaultInfoRepository;
import com.wntime.fault.repo.BusFaultLevelStatisticRepository;
import com.wntime.fault.service.FaultInfoService;
import com.wntime.fault.service.LogFaultDetailService;
import com.wntime.fault.vo.*;
import com.wntime.modules.gemfire.key.BusDateKey;
import org.apache.commons.lang.StringUtils;
import org.apache.geode.cache.CacheTransactionManager;
import org.apache.geode.cache.GemFireCache;
import org.springframework.data.gemfire.repository.Wrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


@Service("logFaultDetailService")
public class LogFaultDetailServiceImpl extends ServiceImpl<LogFaultDetailDao, LogFaultDetailEntity> implements LogFaultDetailService, FaultInfoService {

    @Resource
    private GemFireCache gemFireCache;
    @Resource
    private InfoCollectFaultDao infoCollectFaultDao;
    @Resource
    private InfoFaultTargetDao infoFaultTargetDao;
    @Resource
    private InfoFaultTypeDao infoFaultTypeDao;
    @Resource
    private InfoFaultExtendDao infoFaultExtendDao;
    @Resource
    private InfoFaultLevelDao infoFaultLevelDao;
    @Resource
    private BusFaultInfoRepository busFaultInfoRepository;
    @Resource
    private InfoFaultSuggestionDao infoFaultSuggestionDao;
    @Resource
    private BusFaultLevelStatisticRepository busFaultLevelStatisticRepository;

    @Override
    public LogFaultDetailEntity getDetailById(Long id) {
        return getBaseMapper().getDetailById(id);
    }

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
        Page<LogFaultDetailEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        if (params.get("faultTypeId") != null && StringUtils.isNotEmpty(String.valueOf(params.get("faultTypeId")))) {
            params.put("faultTypeId", Long.parseLong(String.valueOf(params.get("faultTypeId"))));
        }

        if (params.get("faultTargetId") != null && StringUtils.isNotEmpty(String.valueOf(params.get("faultTargetId")))) {
            params.put("faultTargetId", Long.parseLong(String.valueOf(params.get("faultTargetId"))));
        }

        if (params.get("faultLevelId") != null && StringUtils.isNotEmpty(String.valueOf(params.get("faultLevelId")))) {
            params.put("faultLevelId", Long.parseLong(String.valueOf(params.get("faultLevelId"))));
        }

        if (params.get("startTime") != null && StringUtils.isNotEmpty(String.valueOf(params.get("startTime")))) {
            params.put("startTime", DateUtils.stringToDate(String.valueOf(params.get("startTime")), DateUtils.DATE_TIME_PATTERN));
        } else {
            params.put("startTime", null);
        }

        if (params.get("endTime") != null && StringUtils.isNotEmpty(String.valueOf(params.get("endTime")))) {
            params.put("endTime", DateUtils.stringToDate(String.valueOf(params.get("endTime")), DateUtils.DATE_TIME_PATTERN));
        } else {
            params.put("endTime", null);
        }
        return new PageUtils(page.setRecords(getBaseMapper().queryPageList(params)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFaultDetail(LogFaultDetailEntity logFaultDetailEntity) {
        save(logFaultDetailEntity);
    }

    @Override
    public void saveBusFault(FaultReportForm faultReportForm) {

        Long busId = faultReportForm.getBusId();
        BusDateKey key = new BusDateKey(busId);
        Optional<BusFaultLevelStatistic> optional = busFaultLevelStatisticRepository.findById(key);
        BusFaultLevelStatistic busFault = optional.orElse(new BusFaultLevelStatistic(key));
        List<FaultInfo> infos = busFault.getFaultLevelInfos();
        FaultInfo info = new FaultInfo();
        info.setFaultLevelId(faultReportForm.getEventLevelId());
        int index = infos.indexOf(info);
        if (index != -1) {
            FaultInfo old = infos.get(index);
            old.incrementCount();
        } else {
            info.setFaultLevelCode(faultReportForm.getFaultLevelCode());
            info.setFaultLevelName(faultReportForm.getFaultLevelName());
            info.setCount(1);
            infos.add(info);
        }
        Optional<BusFaultInfo> optional1 = busFaultInfoRepository.findById(key);
        BusFaultInfo busFaultInfo = optional1.orElse(new BusFaultInfo(key));
        Set<FaultInfoItem> faults = busFaultInfo.getFaults();
        FaultInfoItem item = new FaultInfoItem();
        item.setType(faultReportForm.getFaultTypeName());
        String suggestion = infoFaultSuggestionDao.querySuggestionByTargetId(faultReportForm.getEventTargetId());
        item.setDesc(suggestion);
        item.setLevel(faultReportForm.getFaultLevelName());
        item.setFaultDate(new Date(faultReportForm.getEventDate()));
        faults.add(item);
        CacheTransactionManager txManager = gemFireCache.getCacheTransactionManager();
        try {
            txManager.begin();
            busFaultLevelStatisticRepository.save(new Wrapper<>(busFault, busFault.getKey()));
            busFaultInfoRepository.save(new Wrapper<>(busFaultInfo, busFaultInfo.getKey()));
            txManager.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (txManager.exists()) {
                txManager.rollback();
            }
        }
    }


    @Override
    public Collection<FaultInfoVo> queryBusFaultLevelStatistics(long busId) {
        Map<String, FaultInfoVo> temp = new LinkedHashMap<>();
        temp.put("1", new FaultInfoVo("1", "一级故障"));
        temp.put("2", new FaultInfoVo("2", "二级故障"));
        temp.put("3", new FaultInfoVo("3", "三级故障"));
        Optional<BusFaultLevelStatistic> optional = busFaultLevelStatisticRepository.findById(new BusDateKey(busId));
        AtomicInteger max = new AtomicInteger(0);
        if (optional.isPresent()) {
            optional.get().getFaultLevelInfos().stream().sorted(Comparator.comparing(FaultInfo::getCount).reversed()).forEach(faultInfo -> {
                max.compareAndSet(0, faultInfo.getCount());
                FaultInfoVo vo = new FaultInfoVo();
                vo.setFaultLevelCode(faultInfo.getFaultLevelCode());
                vo.setName(faultInfo.getFaultLevelName());
                vo.setNumber(faultInfo.getCount());
                vo.setMax(max.get());
                temp.put(vo.getFaultLevelCode(), vo);
            });
        } else {
            List<FaultCountVo> list = getBaseMapper().queryFaultCoutGroupCode(busId);
            if (list != null && !list.isEmpty()) {
                for (FaultCountVo vo : list) {
                    FaultInfoVo faultVo = temp.get(vo.getCode());
                    if(faultVo!=null){
                        faultVo.setNumber(vo.getCount());
                        max.compareAndSet(0, vo.getCount());
                        faultVo.setMax(max.get());
                    }
                }
            }
        }
        return temp.values();
    }

    @Override
    @DataSource("read")
    public List<InfoCollectFaultVo> queryCollectFault() {
        return infoCollectFaultDao.queryList();
    }

    @Override
    @DataSource("read")
    public List<InfoFaultExtendVo> queryFaultExtend() {
        return infoFaultExtendDao.queryList();
    }

    @Override
    @DataSource("read")
    public List<InfoFaultLevelVo> queryFaultLevel() {
        return infoFaultLevelDao.queryList();
    }

    @Override
    @DataSource("read")
    public List<InfoFaultTargetVo> queryFaultTarget() {
        return infoFaultTargetDao.queryList();
    }

    @Override
    @DataSource("read")
    public List<InfoFaultTypeVo> queryFaultType() {
        return infoFaultTypeDao.queryList();
    }

    @Override
    public List<BusFaultInfoVo> queryBusFaultInfos(long busId) {
        Optional<BusFaultInfo> optional = busFaultInfoRepository.findById(new BusDateKey(busId));
        List<BusFaultInfoVo> result = new ArrayList<>();
        BusFaultInfoVo vo = new BusFaultInfoVo();
        vo.setId(1L);
        vo.setName("总线告警");
        if(optional.isPresent()){
            vo.setInfo(optional.get().getFaults());
        } else {
            List<FaultInfoItem> list = getBaseMapper().queryBusFaultList(busId);
            vo.setInfo(new HashSet<>(list));
        }
        result.add(vo);
        return result;
    }
}
