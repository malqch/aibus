package com.wntime.service.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.service.dao.InfoUpdateListDao;
import com.wntime.service.common.entity.InfoUpdateListEntity;
import com.wntime.service.common.service.InfoUpdateListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("infoUpdateListService")
public class InfoUpdateListServiceImpl extends ServiceImpl<InfoUpdateListDao, InfoUpdateListEntity> implements InfoUpdateListService {


    @Override
    public PageUtils queryPageList(Map<String, Object> params) {

        Integer currPage = 1;
        Integer pageSize = 10000;
        if (params.get("page") != null) {
            currPage = Integer.parseInt((String) params.get("page"));
        }
        if (params.get("limit") != null) {
            pageSize = Integer.parseInt((String) params.get("limit"));

        }
        Page<Object> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    public List<Object> getDetailById(Long id) {
        return this.baseMapper.getDetailById(id);
    }

    @Override
    public List<Object> queryUpdateType(Map<String, Object> params) {
        return this.baseMapper.queryUpdateType(params);
    }

    @Override
    public boolean updateData(String updateType, Long userId, Timestamp modifiedDate) {
        return this.baseMapper.updateData(updateType, userId, modifiedDate) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(InfoUpdateListEntity infoUpdateList) {
        return this.updateById(infoUpdateList);
    }

    @Override
    public boolean delById(Long id) {
        return this.baseMapper.delById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteBatch(String[] ids) {
        for (String id : ids) {
            this.baseMapper.delById(Long.parseLong(id));
        }
        return true;
    }

    @Override
    public boolean isEnableInsert(String name) {
        if (this.baseMapper.isEnableInsert(name) > 0) return false;
        return true;
    }

    @Override
    public boolean isEnableUpdate(long id, String name) {
        if (this.baseMapper.isEnableUpdate(id, name) > 0) return false;
        return true;
    }

    @Override
    public void updatePublishStatus(String updateType) {
        this.baseMapper.updatePublishStatus(updateType);
    }

    @Override
    public boolean isTableUpdate(String tableName, Date date) {
        return getBaseMapper().isTableUpdate(tableName,date);
    }

    @Override
    public boolean isTableUpdate1(String tableName, Date date) {
        return getBaseMapper().isTableUpdate1(tableName,date);
    }

}
