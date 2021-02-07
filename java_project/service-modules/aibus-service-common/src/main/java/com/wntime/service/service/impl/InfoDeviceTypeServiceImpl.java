package com.wntime.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.R;
import com.wntime.service.dao.InfoBatteryTypeDao;
import com.wntime.service.dao.InfoMotorTypeDao;
import com.wntime.service.entity.InfoBatteryTypeEntity;
import com.wntime.service.entity.InfoMotorTypeEntity;
import com.wntime.service.service.InfoBatteryTypeService;
import com.wntime.service.service.InfoMotorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.service.dao.InfoDeviceTypeDao;
import com.wntime.service.entity.InfoDeviceTypeEntity;
import com.wntime.service.service.InfoDeviceTypeService;

import javax.annotation.Resource;

/**
 * @desc 设备类型
 * @date 2020-08-29 16:19:14
 */
@Service("infoDeviceTypeService")
public class InfoDeviceTypeServiceImpl extends ServiceImpl<InfoDeviceTypeDao, InfoDeviceTypeEntity> implements InfoDeviceTypeService {
    @Resource
    private InfoBatteryTypeDao infoBatteryTypeDao;

    @Resource
    private InfoMotorTypeDao infoMotorTypeDao;

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
        Page<InfoDeviceTypeEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    public InfoDeviceTypeEntity getDetailById(Long id) {
        return this.getById(id);
    }

    @Override
    public List<InfoDeviceTypeEntity> getAll() {
        return this.list(new QueryWrapper<InfoDeviceTypeEntity>().eq("is_deleted", 0).eq("is_enabled", 1));
    }

    @Override
    public void save(InfoDeviceTypeEntity infoDeviceTypeEntity, Long userId) {
        infoDeviceTypeEntity.setDeviceTypeId(null);
        infoDeviceTypeEntity.setIsDeleted(0);
        infoDeviceTypeEntity.setCreatedDate(DateUtils.getTimestamp());
        infoDeviceTypeEntity.setCreatedBy(userId);
        this.save(infoDeviceTypeEntity);
    }

    @Override
    public void updateById(InfoDeviceTypeEntity infoDeviceTypeEntity, Long userId) {
        // 关联检查
        if(infoDeviceTypeEntity.getIsEnabled() == 0){
            deletePermitCheck(infoDeviceTypeEntity.getDeviceTypeId());
        }

        infoDeviceTypeEntity.setModifiedBy(userId);
        infoDeviceTypeEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoDeviceTypeEntity);
    }

    /**
     * 关联删除检查
     * @param id
     */
    void deletePermitCheck(Long id){
        // 电池类型检查
        QueryWrapper<InfoBatteryTypeEntity> QueryWrapper = new QueryWrapper<InfoBatteryTypeEntity>()
                .eq("is_deleted", 0)
                .eq("device_type_id",id);

        List<InfoBatteryTypeEntity> getList = infoBatteryTypeDao.selectList(QueryWrapper);
        if(getList != null && getList.size() > 0){
            throw new RRException("该设备类型存在关联[电池类型]配置，删除或禁用失败。");
        }

        // 电机类型检查
        QueryWrapper<InfoMotorTypeEntity> QueryMotorWrapper = new QueryWrapper<InfoMotorTypeEntity>()
                .eq("is_deleted", 0)
                .eq("device_type_id",id);

        List<InfoMotorTypeEntity> getMotorList = infoMotorTypeDao.selectList(QueryMotorWrapper);
        if(getMotorList != null && getMotorList.size() > 0){
            throw new RRException("该设备类型存在关联[电机类型]配置，删除或禁用失败。");
        }
    }

    @Override
    public void delById(Long id, Long userId) {
        // 关联检查
        deletePermitCheck(id);
        InfoDeviceTypeEntity infoDeviceTypeEntity = new InfoDeviceTypeEntity();
        infoDeviceTypeEntity.setDeviceTypeId(id);
        infoDeviceTypeEntity.setIsDeleted(1);
        infoDeviceTypeEntity.setModifiedBy(userId);
        infoDeviceTypeEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoDeviceTypeEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

}
