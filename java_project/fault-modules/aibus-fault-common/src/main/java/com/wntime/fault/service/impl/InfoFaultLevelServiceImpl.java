package com.wntime.fault.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wntime.fault.entity.InfoFaultTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.fault.dao.InfoFaultLevelDao;
import com.wntime.fault.entity.InfoFaultLevelEntity;
import com.wntime.fault.service.InfoFaultLevelService;


@Service("infoFaultLevelService")
public class InfoFaultLevelServiceImpl extends ServiceImpl<InfoFaultLevelDao, InfoFaultLevelEntity> implements InfoFaultLevelService {


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
        Page<InfoFaultLevelEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        return new PageUtils(page.setRecords(getBaseMapper().queryPageList(params)));
    }

    @Override
    public List<Map<String, Object>> countBusCompanyIdGroupByFaultLevel(Long companyId) {
        return getBaseMapper().countBusGroupByFaultLevel(companyId);
    }

    @Override
    public void deleteBatch(List<Long> ids, Long userId) {
        getBaseMapper().updateBatch(ids, userId);
    }

}
