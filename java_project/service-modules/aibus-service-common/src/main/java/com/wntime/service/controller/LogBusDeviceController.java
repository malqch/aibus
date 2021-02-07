package com.wntime.service.controller;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.service.common.vo.DeleteBatchVo;
import com.wntime.service.entity.LogBusDeviceEntity;
import com.wntime.service.service.LogBusDeviceService;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.Map;

/**
 * @desc 设备日志表
 *
 * @author psp
 * @date 2020-09-02 13:49:03
 */
@Api(value = "@desc 设备日志表", tags = {"@desc 设备日志表"})
@RestController
@RequestMapping("service/logbusdevice")
public class LogBusDeviceController extends AbstractController {
    @Autowired
    private LogBusDeviceService logBusDeviceService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "@desc 设备日志表分页列表", httpMethod = "GET", notes = "@desc 设备日志表管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("@desc 设备日志表分页列表,参数:" + JSON.toJSONString(params));
        PageUtils page = logBusDeviceService.queryPage(params);
        return R.ok().put("page", page);
    }

    @ApiOperation(value = "获取单条@desc 设备日志表", httpMethod = "GET", notes = "获取单条@desc 设备日志表")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条@desc 设备日志表,参数ID:" + id);
        LogBusDeviceEntity logBusDeviceEntity;
        try {
            logBusDeviceEntity = logBusDeviceService.getDetailById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条@desc 设备日志表失败");
        }
        return R.ok().put("data", logBusDeviceEntity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存@desc 设备日志表")
    @ApiOperation(value = "添加@desc 设备日志表", httpMethod = "POST", notes = "@desc 设备日志表管理页面中添加@desc 设备日志表")
    public R save(@RequestBody LogBusDeviceEntity logBusDeviceEntity) {
        logger.info("保存@desc 设备日志表,参数:" + JSON.toJSONString(logBusDeviceEntity));
        ValidatorUtils.validateEntity(logBusDeviceEntity);
        try {
            logBusDeviceService.save(logBusDeviceEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存@desc 设备日志表失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改@desc 设备日志表")
    @ApiOperation(value = "修改@desc 设备日志表", httpMethod = "POST", notes = "@desc 设备日志表管理页面中修改@desc 设备日志表")
    public R update(@RequestBody LogBusDeviceEntity logBusDeviceEntity) {
        logger.info("修改@desc 设备日志表,参数:" + JSON.toJSONString(logBusDeviceEntity));
        ValidatorUtils.validateEntity(logBusDeviceEntity);
        try {
            logBusDeviceService.updateById(logBusDeviceEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改@desc 设备日志表失败");
        }
        return R.ok();
    }
}
