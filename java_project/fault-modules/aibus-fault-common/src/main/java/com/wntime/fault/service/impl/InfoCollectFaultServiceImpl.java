package com.wntime.fault.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wntime.common.utils.DateUtils;
import com.wntime.fault.entity.InfoFaultTargetEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.fault.dao.InfoCollectFaultDao;
import com.wntime.fault.entity.InfoCollectFaultEntity;
import com.wntime.fault.service.InfoCollectFaultService;
import org.springframework.transaction.annotation.Transactional;


@Service("infoCollectFaultService")
public class InfoCollectFaultServiceImpl extends ServiceImpl<InfoCollectFaultDao, InfoCollectFaultEntity> implements InfoCollectFaultService {

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
        Page<InfoCollectFaultEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        if(params.get("faultTypeId") != null && StringUtils.isNotEmpty(String.valueOf(params.get("faultTypeId")))){
            params.put("faultTypeId", Long.parseLong(String.valueOf(params.get("faultTypeId"))));
        }

        if(params.get("faultTargetId") != null&& StringUtils.isNotEmpty(String.valueOf(params.get("faultTargetId")))){
            params.put("faultTargetId", Long.parseLong(String.valueOf(params.get("faultTargetId"))));
        }

        if(params.get("faultLevelId") != null&& StringUtils.isNotEmpty(String.valueOf(params.get("faultLevelId")))){
            params.put("faultLevelId", Long.parseLong(String.valueOf(params.get("faultLevelId"))) );
        }

        return new PageUtils(page.setRecords(getBaseMapper().queryPageList(params)));
    }

    @Override
    public InfoCollectFaultEntity getDetailById(Long id) {
        return getBaseMapper().getDetailById(id);
    }

    @Override
    public void delById(Long id, Long userId) {
        InfoCollectFaultEntity entity = new InfoCollectFaultEntity();
        entity.setCollectFaultId(id);
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
