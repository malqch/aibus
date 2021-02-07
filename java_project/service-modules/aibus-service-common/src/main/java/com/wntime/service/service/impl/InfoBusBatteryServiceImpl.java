package com.wntime.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.exception.RRException;
import com.wntime.service.common.entity.InfoBusEntity;
import com.wntime.service.common.form.OrderBusDeliveryBatchForm;
import com.wntime.service.common.service.BusBatteryInfoService;
import com.wntime.service.entity.InfoBusMotorEntity;
import com.wntime.service.entity.InfoConfigParamEntity;
import com.wntime.service.vo.BusVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.service.dao.InfoBusBatteryDao;
import com.wntime.service.entity.InfoBusBatteryEntity;
import com.wntime.service.service.InfoBusBatteryService;

@Service("infoBusBatteryService")
public class InfoBusBatteryServiceImpl extends ServiceImpl<InfoBusBatteryDao, InfoBusBatteryEntity>
        implements InfoBusBatteryService, BusBatteryInfoService {

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
        Page<InfoBusBatteryEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveImportBusBatteryInfo(OrderBusDeliveryBatchForm orderBusDeliveryBatchForm) {
        if(orderBusDeliveryBatchForm.getBusBatteryCodes() != null){
            for(String busBatteryCode :orderBusDeliveryBatchForm.getBusBatteryCodes()){
                InfoBusBatteryEntity infoBusBatteryEntity = this.getOne(new QueryWrapper<InfoBusBatteryEntity>()
                        .eq("is_deleted",0)
                        .eq("is_enabled",1)
                        .eq("bus_battery_code",busBatteryCode));
                if(infoBusBatteryEntity != null){
                    throw  new RRException("该[VIN码:"+orderBusDeliveryBatchForm.getVinCode()
                            +",电池编号:"+busBatteryCode+"]车辆电池信息已存在");
                }
                InfoBusBatteryEntity infoBusBatteryEntitySave = new InfoBusBatteryEntity();
                infoBusBatteryEntitySave.setBusId(orderBusDeliveryBatchForm.getBusId());
                infoBusBatteryEntitySave.setBatteryTypeId(orderBusDeliveryBatchForm.getBatteryTypeId());
                infoBusBatteryEntitySave.setBusBatteryCode(busBatteryCode);
                infoBusBatteryEntitySave.setIsEnabled(1);
                save(infoBusBatteryEntitySave,orderBusDeliveryBatchForm.getUserId());
            }
        }
    }

    @Override
    public List<InfoBusBatteryEntity> queryListByBusId(Map<String, Object> params) {
        if(params.get("busId") != null){
            params.put("busId",Long.parseLong(String.valueOf(params.get("busId"))));
        }
        return this.baseMapper.queryPageList(params);
    }

    @Override
    public InfoBusBatteryEntity getDetailById(Long id) {
        return this.getById(id);
    }

    @Override
    public void save(InfoBusBatteryEntity infoBusBatteryEntity, Long userId) {
        infoBusBatteryEntity.setBusBatteryId(null);
        infoBusBatteryEntity.setIsDeleted(0);
        infoBusBatteryEntity.setCreatedDate(DateUtils.getTimestamp());
        infoBusBatteryEntity.setCreatedBy(userId);
        this.save(infoBusBatteryEntity);
    }

    @Override
    public void updateById(InfoBusBatteryEntity infoBusBatteryEntity, Long userId) {
        infoBusBatteryEntity.setModifiedBy(userId);
        infoBusBatteryEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoBusBatteryEntity);
    }

    @Override
    public void delById(Long id, Long userId) {
        InfoBusBatteryEntity infoBusBatteryEntity = new InfoBusBatteryEntity();
        infoBusBatteryEntity.setBusBatteryId(id);
        infoBusBatteryEntity.setIsDeleted(1);
        infoBusBatteryEntity.setModifiedBy(userId);
        infoBusBatteryEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoBusBatteryEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

}
