package com.wntime.customer.controller;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.customer.entity.OrderBusCompanyDetailEntity;
import com.wntime.customer.service.OrderBusCompanyDetailService;
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
 * 订单详情表
 *
 * @author psp
 * @date 2020-09-04 15:19:14
 */
@Api(value = "订单详情表", tags = {"订单详情表"})
@RestController
@RequestMapping("customer/orderbuscompanydetail")
public class OrderBusCompanyDetailController extends AbstractController {
    @Autowired
    private OrderBusCompanyDetailService orderBusCompanyDetailService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "订单详情表分页列表", httpMethod = "GET", notes = "订单详情表管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("订单详情表分页列表,参数:" + JSON.toJSONString(params));
        PageUtils page = orderBusCompanyDetailService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @GetMapping("/getOrderBusCompanyDetailByOrderId/{orderId}")
    @ApiOperation(value = "根据订单ID获取所有订单", httpMethod = "GET", notes = "根据订单ID获取所有订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单ID", required = true, dataType = "String")
    })
    public R getOrderBusCompanyDetailByOrderId(@PathVariable("orderId") Long orderId) {
        logger.info("根据订单ID获取所有订单,参数:" + orderId);
        List<OrderBusCompanyDetailEntity> list = orderBusCompanyDetailService.getOrderBusCompanyDetailByOrderId(orderId);
        return R.ok().put("list", list);
    }

    @ApiOperation(value = "获取单条订单详情表", httpMethod = "GET", notes = "获取单条订单详情表")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条订单详情表,参数ID:" + id);
        OrderBusCompanyDetailEntity orderBusCompanyDetailEntity;
        orderBusCompanyDetailEntity = orderBusCompanyDetailService.getDetailById(id);
        return R.ok().put("data", orderBusCompanyDetailEntity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存订单详情表")
    @ApiOperation(value = "添加订单详情表", httpMethod = "POST", notes = "订单详情表管理页面中添加订单详情表")
    public R save(@RequestBody OrderBusCompanyDetailEntity orderBusCompanyDetailEntity) {
        logger.info("保存订单详情表,参数:" + JSON.toJSONString(orderBusCompanyDetailEntity));
        ValidatorUtils.validateEntity(orderBusCompanyDetailEntity);
        orderBusCompanyDetailService.save(orderBusCompanyDetailEntity, getUserId());
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改订单详情表")
    @ApiOperation(value = "修改订单详情表", httpMethod = "POST", notes = "订单详情表管理页面中修改订单详情表")
    public R update(@RequestBody OrderBusCompanyDetailEntity orderBusCompanyDetailEntity) {
        logger.info("修改订单详情表,参数:" + JSON.toJSONString(orderBusCompanyDetailEntity));
        ValidatorUtils.validateEntity(orderBusCompanyDetailEntity);
        orderBusCompanyDetailService.updateById(orderBusCompanyDetailEntity, getUserId());
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除订单详情表", httpMethod = "POST", notes = "订单详情表管理页面中删除订单详情表")
    @SysLog("单条删除订单详情表")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除订单详情表,id:" + id);
        orderBusCompanyDetailService.delById(id, getUserId());
        return R.ok();
    }

    @ApiOperation(value = "批量删除订单详情表", httpMethod = "POST", notes = "批量删除订单详情表")
    @SysLog("批量删除订单详情表")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除订单详情表,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            orderBusCompanyDetailService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!" + e.getMessage());
        }

        return R.ok("批量删除成功!");
    }


}
