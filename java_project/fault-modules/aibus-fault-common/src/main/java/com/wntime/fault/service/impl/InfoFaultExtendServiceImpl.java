package com.wntime.fault.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wntime.common.utils.DateUtils;
import com.wntime.fault.entity.InfoFaultTargetEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.fault.dao.InfoFaultExtendDao;
import com.wntime.fault.entity.InfoFaultExtendEntity;
import com.wntime.fault.service.InfoFaultExtendService;
import org.springframework.transaction.annotation.Transactional;


@Service("infoFaultExtendService")
public class InfoFaultExtendServiceImpl extends ServiceImpl<InfoFaultExtendDao, InfoFaultExtendEntity> implements InfoFaultExtendService {

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
        Page<InfoFaultExtendEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        return new PageUtils(page.setRecords(getBaseMapper().queryPageList(params)));
    }

    @Override
    public List<InfoFaultExtendEntity> queryList(Map<String, Object> params) {
        return getBaseMapper().queryPageList(params);
    }

    @Override
    public void delById(Long id, Long userId) {
        InfoFaultExtendEntity entity = new InfoFaultExtendEntity();
        entity.setFaultExtendId(id);
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
