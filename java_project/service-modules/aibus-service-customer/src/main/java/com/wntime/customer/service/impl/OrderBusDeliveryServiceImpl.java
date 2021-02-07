package com.wntime.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.customer.dao.InfoBusCompanyDao;
import com.wntime.customer.dao.OrderBusCompanyDao;
import com.wntime.customer.dao.OrderBusDeliveryDao;
import com.wntime.customer.entity.OrderBusCompanyDetailEntity;
import com.wntime.customer.entity.OrderBusCompanyEntity;
import com.wntime.customer.entity.OrderBusDeliveryEntity;
import com.wntime.customer.entity.OrderCompanyDeliveryEntity;
import com.wntime.service.common.entity.InfoBusEntity;
import com.wntime.service.common.form.OrderBusDeliveryBatchForm;
import com.wntime.customer.service.OrderBusCompanyDetailService;
import com.wntime.customer.service.OrderBusCompanyService;
import com.wntime.customer.service.OrderBusDeliveryService;
import com.wntime.customer.service.OrderCompanyDeliveryService;
import com.wntime.customer.vo.OrderBusDeliveryVo;
import com.wntime.service.common.service.BusBatteryInfoService;
import com.wntime.service.common.service.BusInfoService;
import com.wntime.service.common.service.BusMotorInfoService;
import com.wntime.service.common.vo.BusBaseInfoVo;
import net.bytebuddy.asm.Advice;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


@Service("orderBusDeliveryService")
public class OrderBusDeliveryServiceImpl extends ServiceImpl<OrderBusDeliveryDao, OrderBusDeliveryEntity> implements OrderBusDeliveryService {

    @Autowired
    private OrderCompanyDeliveryService orderCompanyDeliveryService;

    @Autowired
    private OrderBusCompanyDetailService orderBusCompanyDetailService;

    @Autowired
    private OrderBusCompanyService orderBusCompanyService;

    @Resource
    private InfoBusCompanyDao infoBusCompanyDao;

    @Resource
    private OrderBusCompanyDao orderBusCompanyDao;
    @Autowired
    private BusInfoService busInfoService;

    @Autowired
    private BusBatteryInfoService busBatteryInfoService;

    @Autowired
    private BusMotorInfoService busMotorInfoService;

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
        Page<OrderBusDeliveryVo> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    public OrderBusDeliveryVo getDetailById(Long id) {
        return this.baseMapper.getDetailById(id);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveImportBatch(List<OrderBusDeliveryBatchForm> orderBusDeliveryBatchForms,long userId) {
        // 车型+数量
        Map<Long,Integer> map = new HashedMap();

        // 车型+交付日期
        Map<Long, Timestamp> mapDate = new HashedMap();

        // 循环保存
        for(OrderBusDeliveryBatchForm deliveryBatchForm : orderBusDeliveryBatchForms){
            // 车型+交付数量
            if(map.containsKey(deliveryBatchForm.getBusTypeId())){
                map.put(deliveryBatchForm.getBusTypeId(),map.get(deliveryBatchForm.getBusTypeId())+1);
            }else{
                map.put(deliveryBatchForm.getBusTypeId(),1);
            }

            // 车型+交付日期
            if(!mapDate.containsKey(deliveryBatchForm.getBusTypeId())){
                mapDate.put(deliveryBatchForm.getBusTypeId(),deliveryBatchForm.getOrderDeliveryDate());
            }
            // 1.车辆信息表
            InfoBusEntity infoBusEntity = busInfoService.saveImportBusInfo(deliveryBatchForm);
            if(infoBusEntity !=null && infoBusEntity.getBusId()!= 0L){
                deliveryBatchForm.setBusId(infoBusEntity.getBusId());
                // 2.车辆电机表
                busMotorInfoService.saveImportBusMotorInfo(deliveryBatchForm);
                // 3.车辆电池表
                busBatteryInfoService.saveImportBusBatteryInfo(deliveryBatchForm);
            }else{
                throw  new RRException("保存车辆[VIN码:"+deliveryBatchForm.getVinCode()+"]失败");
            }
        }

        // 5.订单交付表》计算交付量
        Map<Long,Long> resultMap = orderCompanyDeliveryService.saveImportOrderCompanyDeliveryInfo(map
                ,orderBusDeliveryBatchForms.get(0).getOrderId(),mapDate,userId);

        // 4.车辆交付
        if(resultMap == null){
            throw  new RRException("保存订单交付数据失败");
        }else{
            // 循环保存车辆交付
            for(OrderBusDeliveryBatchForm deliveryBatchForm : orderBusDeliveryBatchForms){
                OrderBusDeliveryEntity orderBusDeliveryEntitySave = new OrderBusDeliveryEntity();
                orderBusDeliveryEntitySave.setBusId(deliveryBatchForm.getBusId());
                orderBusDeliveryEntitySave.setCompanyDeliveryId(resultMap.get(deliveryBatchForm.getBusTypeId()));
                orderBusDeliveryEntitySave.setOrderDeliveryDate(deliveryBatchForm.getOrderDeliveryDate());
                orderBusDeliveryEntitySave.setOrderDeliveryDesc(deliveryBatchForm.getOrderDeliveryDesc());
                orderBusDeliveryEntitySave.setOrderOutDate(deliveryBatchForm.getOrderOutDate());
                orderBusDeliveryEntitySave.setOrderOutDesc(deliveryBatchForm.getOrderOutDesc());
                orderBusDeliveryEntitySave.setPlateCode(deliveryBatchForm.getPlateCode());
                orderBusDeliveryEntitySave.setBusCode(deliveryBatchForm.getBusCode());
                orderBusDeliveryEntitySave.setOrderId(deliveryBatchForm.getOrderId());
                save(orderBusDeliveryEntitySave,userId);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(OrderBusDeliveryEntity orderBusDeliveryEntity, Long userId) {
        Integer count = getBaseMapper().selectCount(new QueryWrapper<OrderBusDeliveryEntity>()
                .eq("is_deleted", 0)
                .eq("bus_id", orderBusDeliveryEntity.getBusId()));
        if(count == 1){
            throw new RRException("该车辆已交付！");
        }
        orderBusDeliveryEntity.setBusDeliveryId(null);
        orderBusDeliveryEntity.setIsDeleted(0);
        orderBusDeliveryEntity.setCreatedDate(DateUtils.getTimestamp());

        OrderCompanyDeliveryEntity orderCompanyDeliveryEntity = orderCompanyDeliveryService.getDetailById(orderBusDeliveryEntity.getCompanyDeliveryId());
        if(orderCompanyDeliveryEntity.getIsCompleted() == 1){
            throw new RRException("该交付订单已交付完成，不需要再交付车辆");
        }
        this.save(orderBusDeliveryEntity);

        //订单交付完成状态设置1
        int companyDeliveryCount = this.count(new QueryWrapper<OrderBusDeliveryEntity>()
                .eq("company_delivery_id", orderBusDeliveryEntity.getCompanyDeliveryId())
                .eq("is_deleted", 0)
        );
        OrderCompanyDeliveryEntity companyDeliveryEntity = orderCompanyDeliveryService.getById(orderBusDeliveryEntity.getCompanyDeliveryId());

        if (companyDeliveryEntity.getOrderDeliveryNum() == companyDeliveryCount) {
            companyDeliveryEntity.setIsCompleted(1);
            orderCompanyDeliveryService.updateById(companyDeliveryEntity);
            //联动调整订单状态
            List<OrderCompanyDeliveryEntity> deliveryList = orderCompanyDeliveryService.list(new QueryWrapper<OrderCompanyDeliveryEntity>()
                    .eq("order_id", orderBusDeliveryEntity.getOrderId())
                    .eq("is_completed", 1)
                    .eq("is_deleted", 0));
            List<OrderBusCompanyDetailEntity> detailList = orderBusCompanyDetailService.list(new QueryWrapper<OrderBusCompanyDetailEntity>()
                    .eq("order_id", orderBusDeliveryEntity.getOrderId())
                    .eq("is_deleted", 0)
            );
            if (deliveryList.stream().mapToInt(OrderCompanyDeliveryEntity::getOrderDeliveryNum).sum() == detailList.stream().mapToInt(OrderBusCompanyDetailEntity::getOrderDetailNum).sum()){
                //订单完成状态设置1
                OrderBusCompanyEntity order = new OrderBusCompanyEntity();
                order.setOrderId(orderBusDeliveryEntity.getOrderId());
                order.setIsCompleted(1);
                orderBusCompanyService.updateById(order);
            }
        }
        //保存公交车基本信息缓存
        OrderBusCompanyEntity orderBusCompany = orderBusCompanyDao.selectById(orderBusDeliveryEntity.getOrderId());
        BusBaseInfoVo busBaseInfoVo = new BusBaseInfoVo();
        busBaseInfoVo.setPlateCode(orderBusDeliveryEntity.getPlateCode());
        busBaseInfoVo.setBusId(orderBusDeliveryEntity.getBusId());
        busBaseInfoVo.setFactoryId(orderBusCompany.getFactoryId());
        busBaseInfoVo.setCompanyId(orderBusCompany.getCompanyId());
        busInfoService.saveBusInfo(busBaseInfoVo);
    }

    @Override
    public void updateById(OrderBusDeliveryEntity orderBusDeliveryEntity, Long userId) {
        Integer count = getBaseMapper().selectCount(new QueryWrapper<OrderBusDeliveryEntity>()
                .eq("is_deleted", 0)
                .eq("bus_id", orderBusDeliveryEntity.getBusId())
                .ne("bus_delivery_id",orderBusDeliveryEntity.getBusDeliveryId())
        );
        if(count == 1){
            throw new RRException("该车辆已交付！");
        }
        orderBusDeliveryEntity.setModifiedDate(DateUtils.getTimestamp());

        OrderBusDeliveryEntity oldBusDeliveryEntity = this.getById(orderBusDeliveryEntity.getBusDeliveryId());
        if(!orderBusDeliveryEntity.getCompanyDeliveryId().equals(oldBusDeliveryEntity.getCompanyDeliveryId())){
            throw new RRException("车辆交付不能修改所属交付订单！");
        }

        this.updateById(orderBusDeliveryEntity);
        //保存公交车基本信息缓存
        OrderBusCompanyEntity orderBusCompany = orderBusCompanyDao.selectById(orderBusDeliveryEntity.getOrderId());
        BusBaseInfoVo busBaseInfoVo = new BusBaseInfoVo();
        busBaseInfoVo.setPlateCode(orderBusDeliveryEntity.getPlateCode());
        busBaseInfoVo.setBusId(orderBusDeliveryEntity.getBusId());
        busBaseInfoVo.setFactoryId(orderBusCompany.getFactoryId());
        busBaseInfoVo.setCompanyId(orderBusCompany.getCompanyId());

        busInfoService.saveBusInfo(busBaseInfoVo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delById(Long id, Long userId) {
        OrderBusDeliveryEntity orderBusDeliveryEntity = getById(id);
        orderBusDeliveryEntity.setIsDeleted(1);
        orderBusDeliveryEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(orderBusDeliveryEntity);


        OrderCompanyDeliveryEntity companyDeliveryEntity = orderCompanyDeliveryService.getDetailById(orderBusDeliveryEntity.getCompanyDeliveryId());
        companyDeliveryEntity.setIsCompleted(0);
        orderCompanyDeliveryService.updateById(companyDeliveryEntity);

        OrderBusCompanyEntity order = orderBusCompanyDao.selectById(companyDeliveryEntity.getOrderId());
        if (order != null) {
            order.setIsCompleted(0);
            orderBusCompanyService.updateById(order);

            busInfoService.decrementBusStatusSummaryCount(order.getCompanyId(), orderBusDeliveryEntity.getBusId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

}
