package com.wntime.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.validator.Assert;
import com.wntime.customer.dao.OrderBusCompanyDao;
import com.wntime.customer.dao.OrderCompanyDeliveryDao;
import com.wntime.customer.entity.OrderBusCompanyDetailEntity;
import com.wntime.customer.entity.OrderBusCompanyEntity;
import com.wntime.customer.entity.OrderBusDeliveryEntity;
import com.wntime.customer.entity.OrderCompanyDeliveryEntity;
import com.wntime.customer.service.OrderBusCompanyDetailService;
import com.wntime.customer.service.OrderBusCompanyService;
import com.wntime.customer.service.OrderBusDeliveryService;
import com.wntime.customer.service.OrderCompanyDeliveryService;
import com.wntime.datasource.annotation.DataSource;
import com.wntime.service.common.service.BusOrderService;
import com.wntime.service.common.vo.BusFactorySalesVo;
import com.wntime.service.common.vo.FactorySaleVo;
import com.wntime.service.common.vo.OrderStatisticsVo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @desc 订单交付表
 * @date 2020-09-07 11:39:33
 */
@Service("orderCompanyDeliveryService")
public class OrderCompanyDeliveryServiceImpl extends ServiceImpl<OrderCompanyDeliveryDao, OrderCompanyDeliveryEntity> implements OrderCompanyDeliveryService , BusOrderService {

    @Autowired
    private OrderBusDeliveryService orderBusDeliveryService;
    @Autowired
    private OrderBusCompanyDetailService orderBusCompanyDetailService;
    @Autowired
    private OrderBusCompanyService orderBusCompanyService;
    @Resource
    private OrderBusCompanyDao orderBusCompanyDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Long,Long> saveImportOrderCompanyDeliveryInfo(Map<Long,Integer> map, long orderId, Map<Long,Timestamp> mapDate,long userId ) {
        // 车型ID+交付ID
        Map<Long,Long> resultMap = new HashedMap();
        for(Map.Entry<Long, Integer> mapObj : map.entrySet()){
            OrderCompanyDeliveryEntity OrderCompanyDeliveryEntitySave = new OrderCompanyDeliveryEntity();
            OrderCompanyDeliveryEntitySave.setOrderDeliveryNum(mapObj.getValue());
            OrderCompanyDeliveryEntitySave.setBusTypeId(mapObj.getKey());
            OrderCompanyDeliveryEntitySave.setOrderId(orderId);
            OrderCompanyDeliveryEntitySave.setOrderDeliveryDate(mapDate.get(mapObj.getKey()));
            OrderCompanyDeliveryEntitySave.setIsCompleted(0);
            this.save(OrderCompanyDeliveryEntitySave,userId);
            resultMap.put(mapObj.getKey(),OrderCompanyDeliveryEntitySave.getCompanyDeliveryId());
        }
        return resultMap;
    }

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
        Page<OrderCompanyDeliveryEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    public List<OrderCompanyDeliveryEntity> getAllList(Long orderId,Integer isCompleted) {
        return this.baseMapper.getAllList(orderId,isCompleted);
    }

    @Override
    public List<OrderCompanyDeliveryEntity> getOrderCompanyDeliveryByOrderId(Long orderId) {
        return this.baseMapper.getOrderCompanyDeliveryByOrderId(orderId);
    }

    @Override
    public OrderCompanyDeliveryEntity getDetailById(Long id) {
        return this.getById(id);
    }

    private void checkNum(OrderCompanyDeliveryEntity orderCompanyDeliveryEntity){
        OrderBusCompanyDetailEntity orderDetailEntity = orderBusCompanyDetailService.getOne(new QueryWrapper<OrderBusCompanyDetailEntity>()
                .eq("is_deleted", 0)
                .eq("order_id", orderCompanyDeliveryEntity.getOrderId())
                .eq("bus_type_id", orderCompanyDeliveryEntity.getBusTypeId())
        );
        List<OrderCompanyDeliveryEntity> list = this.list(new QueryWrapper<OrderCompanyDeliveryEntity>()
                .eq("is_deleted", 0)
                .eq("order_id", orderCompanyDeliveryEntity.getOrderId())
                .eq("bus_type_id", orderCompanyDeliveryEntity.getBusTypeId())
        );
        Assert.isNull(orderDetailEntity,"所属订单详情缺失！");
        //修改时校验要减掉自己原来的,最多只会有一个
        int oldNum = list.stream()
                .filter(entity -> entity.getCompanyDeliveryId().equals(orderCompanyDeliveryEntity.getCompanyDeliveryId()))
                .mapToInt(OrderCompanyDeliveryEntity::getOrderDeliveryNum).sum();
        Integer orderDetailNum = orderDetailEntity.getOrderDetailNum();
        int sum = list.stream().mapToInt(OrderCompanyDeliveryEntity::getOrderDeliveryNum).sum();
        if(orderDetailNum - (sum - oldNum) < orderCompanyDeliveryEntity.getOrderDeliveryNum()){
            throw new RRException("交付订单数量超出订单数量！");
        }
    }
    @Override
    public void save(OrderCompanyDeliveryEntity orderCompanyDeliveryEntity, Long userId) {
        checkNum(orderCompanyDeliveryEntity);

        OrderBusCompanyEntity order = orderBusCompanyService.getById(orderCompanyDeliveryEntity.getOrderId());
        if(orderCompanyDeliveryEntity.getOrderDeliveryDate().before(order.getOrderDate())){
            throw new RRException("交付时间应晚于订单签订时间");
        }

        orderCompanyDeliveryEntity.setCompanyDeliveryId(null);
        orderCompanyDeliveryEntity.setIsDeleted(0);
        orderCompanyDeliveryEntity.setCreatedDate(DateUtils.getTimestamp());

        this.save(orderCompanyDeliveryEntity);
    }

    @Override
    public void updateById(OrderCompanyDeliveryEntity orderCompanyDeliveryEntity, Long userId) {

        List<OrderBusDeliveryEntity> list = orderBusDeliveryService.list(new QueryWrapper<OrderBusDeliveryEntity>()
                .eq("company_delivery_id", orderCompanyDeliveryEntity.getCompanyDeliveryId())
                .eq("is_deleted",0)
        );
        if(!list.isEmpty()){
            throw new RRException("订单交付下还有已交付车辆，不能修改！");
        }
        checkNum(orderCompanyDeliveryEntity);
        OrderBusCompanyEntity order = orderBusCompanyService.getById(orderCompanyDeliveryEntity.getOrderId());
        if(orderCompanyDeliveryEntity.getOrderDeliveryDate().before(order.getOrderDate())){
            throw new RRException("交付时间应晚于订单签订时间");
        }
        orderCompanyDeliveryEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(orderCompanyDeliveryEntity);
    }

    @Override
    public void delById(Long id, Long userId) {
        List<OrderBusDeliveryEntity> list = orderBusDeliveryService.list(new QueryWrapper<OrderBusDeliveryEntity>()
                .eq("company_delivery_id", id)
                .eq("is_deleted",0)
        );
        if(!list.isEmpty()){
            throw new RRException("订单交付下还有已交付车辆，不能删除！");
        }
        OrderCompanyDeliveryEntity orderCompanyDeliveryEntity = new OrderCompanyDeliveryEntity();
        orderCompanyDeliveryEntity.setCompanyDeliveryId(id);
        orderCompanyDeliveryEntity.setIsDeleted(1);
        orderCompanyDeliveryEntity.setModifiedDate(DateUtils.getTimestamp());

        this.updateById(orderCompanyDeliveryEntity);
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
    public List<OrderStatisticsVo> queryFactoryOrderStatistic(long factoryId) {
        int deliverySum = getBaseMapper().queryCompleteDeliveryBusSum(factoryId);
        int orderSum = getBaseMapper().queryOrderBusSum(factoryId);
        int max= orderSum>deliverySum?orderSum:deliverySum;
        OrderStatisticsVo deliveryVo=new OrderStatisticsVo();
        deliveryVo.setMax(max);
        deliveryVo.setName("车辆订单交付数");
        deliveryVo.setNumber(deliverySum);
        deliveryVo.setOrderType(1);

        OrderStatisticsVo orderVo=new OrderStatisticsVo();
        orderVo.setMax(max);
        orderVo.setName("车辆订单总数");
        orderVo.setNumber(orderSum);
        orderVo.setOrderType(0);
        return Arrays.asList(orderVo,deliveryVo);
    }

    @Override
    public int queryFactoryOrderSum(long factoryId) {
        return getBaseMapper().queryCompleteDeliveryBusSum(factoryId);
    }

    @Override
    public List<FactorySaleVo> queryFactorySalesVolume(long factoryId) {
        List<FactorySaleVo> sales=new ArrayList<>();
        List<BusFactorySalesVo> list = orderBusCompanyDao.queryFactorySalesVolume(factoryId);
        if(list!=null && !list.isEmpty()){
            for (BusFactorySalesVo vo:list){
                FactorySaleVo saleVo=new FactorySaleVo();
                saleVo.setName(vo.getCompanyName());
                saleVo.setValue(new Double[]{vo.getCompanyLongitude(),vo.getCompanyLatitude()});
                saleVo.setNumber(vo.getOrderSum());
                sales.add(saleVo);
            }
        }
        return sales;
    }
}
