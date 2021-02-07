package com.wntime.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.exception.RRException;
import com.wntime.service.common.form.OrderBusDeliveryBatchForm;
import com.wntime.service.common.service.BusMotorInfoService;
import com.wntime.service.entity.InfoBusBatteryEntity;
import com.wntime.service.entity.InfoBusDeviceEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.service.dao.InfoBusMotorDao;
import com.wntime.service.entity.InfoBusMotorEntity;
import com.wntime.service.service.InfoBusMotorService;

/**
 * @desc 电机表
 * @date 2020-09-01 10:05:27
 */
@Service("infoBusMotorService")
public class InfoBusMotorServiceImpl extends ServiceImpl<InfoBusMotorDao, InfoBusMotorEntity>
        implements InfoBusMotorService, BusMotorInfoService {

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
        Page<InfoBusMotorEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    public void saveImportBusMotorInfo(OrderBusDeliveryBatchForm orderBusDeliveryBatchForm) {
        InfoBusMotorEntity InfoBusMotorEntity = this.getOne(new QueryWrapper<InfoBusMotorEntity>()
                .eq("is_deleted",0)
                .eq("is_enabled",1)
                .eq("bus_motor_code",orderBusDeliveryBatchForm.getBusMotorCode()));
        if(InfoBusMotorEntity != null){
            throw  new RRException("该[VIN码:"+orderBusDeliveryBatchForm.getVinCode()
                    +",电机编号:"+orderBusDeliveryBatchForm.getBusMotorCode()+"]车辆电机信息已存在");
        }
        InfoBusMotorEntity InfoBusMotorEntitySave = new InfoBusMotorEntity();
        InfoBusMotorEntitySave.setBusId(orderBusDeliveryBatchForm.getBusId());
        InfoBusMotorEntitySave.setMotorTypeId(orderBusDeliveryBatchForm.getMotorTypeId());
        InfoBusMotorEntitySave.setBusMotorCode(orderBusDeliveryBatchForm.getBusMotorCode());
        InfoBusMotorEntitySave.setIsEnabled(1);
        InfoBusMotorEntitySave.setIsDeleted(0);
        InfoBusMotorEntitySave.setCreatedDate(DateUtils.getTimestamp());
        InfoBusMotorEntitySave.setCreatedBy(orderBusDeliveryBatchForm.getUserId());
        this.save(InfoBusMotorEntitySave);
    }

    @Override
    public List<InfoBusMotorEntity> queryListByBusId(Map<String, Object> params) {
        if(params.get("busId") != null){
            params.put("busId",Long.parseLong(String.valueOf(params.get("busId"))));
        }
        return this.baseMapper.queryPageList(params);
    }

    @Override
    public InfoBusMotorEntity getDetailById(Long id) {
        return this.getById(id);
    }

    @Override
    public void save(InfoBusMotorEntity infoBusMotorEntity, Long userId) {
        infoBusMotorEntity.setBusMotorId(null);
        infoBusMotorEntity.setIsDeleted(0);
        infoBusMotorEntity.setCreatedDate(DateUtils.getTimestamp());
        infoBusMotorEntity.setCreatedBy(userId);
        this.save(infoBusMotorEntity);
    }

    @Override
    public void updateById(InfoBusMotorEntity infoBusMotorEntity, Long userId) {
        infoBusMotorEntity.setModifiedBy(userId);
        infoBusMotorEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoBusMotorEntity);
    }

    @Override
    public void delById(Long id, Long userId) {
        InfoBusMotorEntity infoBusMotorEntity = new InfoBusMotorEntity();
        infoBusMotorEntity.setBusMotorId(id);
        infoBusMotorEntity.setIsDeleted(1);
        infoBusMotorEntity.setModifiedBy(userId);
        infoBusMotorEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoBusMotorEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

}
