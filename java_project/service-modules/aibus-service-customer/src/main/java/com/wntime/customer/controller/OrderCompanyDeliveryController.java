package com.wntime.customer.controller;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.customer.entity.OrderCompanyDeliveryEntity;
import com.wntime.customer.service.OrderCompanyDeliveryService;
import com.wntime.service.common.vo.DeleteBatchVo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 订单交付表
 *
 * @author psp
 * @date 2020-09-07 11:39:33
 */
@Api(value = "订单交付表", tags = {"订单交付表"})
@RestController
@RequestMapping("customer/ordercompanydelivery")
public class OrderCompanyDeliveryController extends AbstractController {
    @Autowired
    private OrderCompanyDeliveryService orderCompanyDeliveryService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "订单交付表分页列表", httpMethod = "GET", notes = "订单交付表管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("订单交付表分页列表,参数:" + JSON.toJSONString(params));
        PageUtils page = orderCompanyDeliveryService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 查询订单交付
     *
     * @param orderId     订单ID
     * @param isCompleted 订单交付状态,为空查询所有订单交付,1已完成,0未完成
     * @return
     */
    @ApiOperation(value = "根据订单ID查询所有订单交付", httpMethod = "GET", notes = "根据订单ID查询所有订单交付")
    @RequestMapping("/allList")
    public R getAllList(Long orderId, Integer isCompleted) {
        List<OrderCompanyDeliveryEntity> list = orderCompanyDeliveryService.getAllList(orderId, isCompleted);
        return R.ok().put("selection", list);
    }

    /**
     * 列表
     */
    @GetMapping("/getOrderCompanyDeliveryByOrderId/{orderId}")
    @ApiOperation(value = "根据订单ID获取所有订单交付", httpMethod = "GET", notes = "根据订单ID获取所有订单交付")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单ID", required = true, dataType = "String")
    })
    public R getOrderCompanyDeliveryByOrderId(@PathVariable("orderId") Long orderId) {
        logger.info("根据订单ID获取所有订单交付,参数:" + orderId);
        List<OrderCompanyDeliveryEntity> list = orderCompanyDeliveryService.getOrderCompanyDeliveryByOrderId(orderId);
        return R.ok().put("list", list);
    }

    @ApiOperation(value = "获取单条订单交付表", httpMethod = "GET", notes = "获取单条订单交付表")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条订单交付表,参数ID:" + id);
        OrderCompanyDeliveryEntity orderCompanyDeliveryEntity;
        try {
            orderCompanyDeliveryEntity = orderCompanyDeliveryService.getDetailById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条订单交付表失败");
        }
        return R.ok().put("data", orderCompanyDeliveryEntity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存订单交付表")
    @ApiOperation(value = "添加订单交付表", httpMethod = "POST", notes = "订单交付表管理页面中添加订单交付表")
    public R save(@RequestBody OrderCompanyDeliveryEntity orderCompanyDeliveryEntity) {
        logger.info("保存订单交付表,参数:" + JSON.toJSONString(orderCompanyDeliveryEntity));
        orderCompanyDeliveryEntity.setIsCompleted(0);
        ValidatorUtils.validateEntity(orderCompanyDeliveryEntity);
        orderCompanyDeliveryService.save(orderCompanyDeliveryEntity, getUserId());
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改订单交付表")
    @ApiOperation(value = "修改订单交付表", httpMethod = "POST", notes = "订单交付表管理页面中修改订单交付表")
    public R update(@RequestBody OrderCompanyDeliveryEntity orderCompanyDeliveryEntity) {
        logger.info("修改订单交付表,参数:" + JSON.toJSONString(orderCompanyDeliveryEntity));
        ValidatorUtils.validateEntity(orderCompanyDeliveryEntity);
        orderCompanyDeliveryService.updateById(orderCompanyDeliveryEntity, getUserId());
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除订单交付表", httpMethod = "POST", notes = "订单交付表管理页面中删除订单交付表")
    @SysLog("单条删除订单交付表")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除订单交付表,id:" + id);
        orderCompanyDeliveryService.delById(id, getUserId());
        return R.ok();
    }

    @ApiOperation(value = "批量删除订单交付表", httpMethod = "POST", notes = "批量删除订单交付表")
    @SysLog("批量删除订单交付表")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除订单交付表,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            orderCompanyDeliveryService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!" + e.getMessage());
        }

        return R.ok("批量删除成功!");
    }


}
