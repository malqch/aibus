package com.wntime.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.datasource.annotation.DataSource;
import com.wntime.service.common.service.BusFactoryService;
import com.wntime.service.common.service.CompanyLineService;
import com.wntime.service.common.vo.AreaVo;
import com.wntime.service.dao.InfoBusFactoryDao;
import com.wntime.service.entity.InfoBusFactoryEntity;
import com.wntime.service.repo.BusInfoRepository;
import com.wntime.service.repo.BusStatusSummaryRepository;
import com.wntime.service.service.InfoBusFactoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("infoBusFactoryService")
public class InfoBusFactoryServiceImpl extends ServiceImpl<InfoBusFactoryDao, InfoBusFactoryEntity> implements InfoBusFactoryService, BusFactoryService {

    @Resource
    private CompanyLineService companyLineService;
    @Resource
    private BusInfoRepository busInfoRepository;
    @Resource
    private BusStatusSummaryRepository busStatusSummaryRepository;

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
        Page<InfoBusFactoryEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    public List<InfoBusFactoryEntity> getAll() {
        return this.list(new QueryWrapper<InfoBusFactoryEntity>()
                .eq("is_deleted", 0)
                .eq("is_enabled", 1));
    }

    @Override
    public InfoBusFactoryEntity getDetailById(Long id) {
        return this.getById(id);
    }

    @Override
    public void save(InfoBusFactoryEntity infoBusFactory, Long userId) {
        infoBusFactory.setFactoryId(null);
        infoBusFactory.setIsDeleted(0);
        infoBusFactory.setCreatedDate(DateUtils.getTimestamp());
        infoBusFactory.setCreatedBy(userId);
        this.save(infoBusFactory);
    }

    @Override
    public void updateById(InfoBusFactoryEntity infoBusFactory, Long userId) {
        infoBusFactory.setModifiedBy(userId);
        infoBusFactory.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoBusFactory);
    }

    @Override
    public void delById(Long id, Long userId) {
        InfoBusFactoryEntity configParamEntity = new InfoBusFactoryEntity();
        configParamEntity.setFactoryId(id);
        configParamEntity.setIsDeleted(1);
        configParamEntity.setModifiedBy(userId);
        configParamEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(configParamEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }


    @Override
    @DataSource("read")
    public AreaVo queryFactoryAreaInfo(long factoryId) {
        return getBaseMapper().queryFactoryAreaInfo(factoryId);
    }
}
