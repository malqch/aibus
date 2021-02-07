package com.wntime.customer.controller;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.customer.entity.OrderBusCompanyEntity;
import com.wntime.customer.service.OrderBusCompanyService;
import com.wntime.customer.vo.OrderBusCompanyVo;
import com.wntime.entity.AdminUser;
import com.wntime.service.common.service.BusCompanyService;
import com.wntime.service.common.util.UniqueCheckHelper;
import com.wntime.service.common.vo.DeleteBatchVo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author psp
 * 公交订单表
 * @date 2020-09-04 10:03:34
 */
@Api(value = "公交订单表", tags = {"公交订单表"})
@RestController
@RequestMapping("customer/orderbuscompany")
public class OrderBusCompanyController extends AbstractController {
    @Autowired
    private OrderBusCompanyService orderBusCompanyService;
    @Autowired
    private BusCompanyService busCompanyService;

    @Value("${server.webUrl}")
    private String webUrl;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "公交订单表分页列表", httpMethod = "GET", notes = "公交订单表管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("公交订单表分页列表,参数:" + JSON.toJSONString(params));
        // 登录用户权限下公司列表
        params.put("companyIdList", busCompanyService.getUserCompanyIdList(getUserId()));
        PageUtils page = orderBusCompanyService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * @param companyId   公交公司id,为空查询所有公交公司订单
     * @param isCompleted 订单状态,为空查询所有订单,1已完成,0未完成
     * @return
     */
    @GetMapping("/getByOrderStatus")
    @ApiOperation(value = "根据公交公司和订单状态查询订单列表", httpMethod = "GET", notes = "根据公交公司和订单状态查询订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公交公司ID", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "isCompleted", value = "订单状态", dataType = "int")
    })
    public R getByOrderStatus(Long companyId, Integer isCompleted) {
        logger.info("根据订单状态查询订单列表,公交公司ID:{},状态{}", companyId, isCompleted);
        // 登录用户权限下公司列表
        List<Long> companyIds = busCompanyService.getUserCompanyIdList(getUserId());
        List<OrderBusCompanyVo> list = orderBusCompanyService.getByOrderStatus(companyIds, companyId, isCompleted);
        return R.ok().put("selection", list);
    }

    @ApiOperation(value = "获取单条公交订单表", httpMethod = "GET", notes = "获取单条公交订单表")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条公交订单表,参数ID:" + id);
        OrderBusCompanyVo detail;
        try {
            detail = orderBusCompanyService.getDetailById(id);
            detail.setFactorySnapshot(webUrl + "/" + detail.getFactorySnapshot());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条公交订单表失败");
        }
        return R.ok().put("data", detail);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存公交订单表")
    @ApiOperation(value = "添加公交订单表", httpMethod = "POST", notes = "公交订单表管理页面中添加公交订单表")
    public R save(@RequestBody OrderBusCompanyEntity orderBusCompanyEntity) {
        logger.info("保存公交订单表,参数:" + JSON.toJSONString(orderBusCompanyEntity));
        orderBusCompanyEntity.setIsCompleted(0);
        ValidatorUtils.validateEntity(orderBusCompanyEntity);
        UniqueCheckHelper.assertIsUnique(orderBusCompanyService, "order_code", orderBusCompanyEntity.getOrderCode(), "订单编号已存在！");

        orderBusCompanyService.save(orderBusCompanyEntity, getUserId());
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改公交订单表")
    @ApiOperation(value = "修改公交订单表", httpMethod = "POST", notes = "公交订单表管理页面中修改公交订单表")
    public R update(@RequestBody OrderBusCompanyEntity orderBusCompanyEntity) {
        logger.info("修改公交订单表,参数:" + JSON.toJSONString(orderBusCompanyEntity));
        ValidatorUtils.validateEntity(orderBusCompanyEntity);
        UniqueCheckHelper.assertIsUnique(orderBusCompanyService, "order_code", orderBusCompanyEntity.getOrderCode(), "order_id", orderBusCompanyEntity.getOrderId(), "订单编号已存在！");

        orderBusCompanyService.updateById(orderBusCompanyEntity, getUserId());
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除公交订单表", httpMethod = "POST", notes = "公交订单表管理页面中删除公交订单表")
    @SysLog("单条删除公交订单表")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除公交订单表,id:" + id);
        orderBusCompanyService.delById(id, getUserId());
        return R.ok();
    }

    @ApiOperation(value = "批量删除公交订单表", httpMethod = "POST", notes = "批量删除公交订单表")
    @SysLog("批量删除公交订单表")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除公交订单表,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            orderBusCompanyService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!" + e.getMessage());
        }

        return R.ok("批量删除成功!");
    }


}
