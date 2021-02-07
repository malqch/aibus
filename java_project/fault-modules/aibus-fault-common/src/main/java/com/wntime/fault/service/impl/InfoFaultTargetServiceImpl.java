package com.wntime.fault.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wntime.common.utils.DateUtils;
import com.wntime.fault.entity.InfoFaultTypeEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.fault.dao.InfoFaultTargetDao;
import com.wntime.fault.entity.InfoFaultTargetEntity;
import com.wntime.fault.service.InfoFaultTargetService;
import org.springframework.transaction.annotation.Transactional;


@Service("infoFaultTargetService")
public class InfoFaultTargetServiceImpl extends ServiceImpl<InfoFaultTargetDao, InfoFaultTargetEntity> implements InfoFaultTargetService {

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
        Page<InfoFaultTargetEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        return new PageUtils(page.setRecords(getBaseMapper().queryPageList(params)));
    }

    @Override
    public void delById(Long id, Long userId) {
        InfoFaultTargetEntity entity = new InfoFaultTargetEntity();
        entity.setFaultTargetId(id);
        entity.setIsDeleted(1);
        entity.setModifiedBy(userId);
        entity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

}
