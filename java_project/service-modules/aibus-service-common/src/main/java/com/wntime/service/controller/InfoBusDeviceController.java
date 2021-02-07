package com.wntime.service.controller;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.service.common.util.UniqueCheckHelper;
import com.wntime.service.common.vo.DeleteBatchVo;
import com.wntime.service.entity.InfoBusDeviceEntity;
import com.wntime.service.service.InfoBusDeviceService;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author psp
 * @desc AI设备表
 * @date 2020-08-25 14:28:17
 */
@RestController
@RequestMapping("service/infobusdevice")
public class InfoBusDeviceController extends AbstractController {

    @Autowired
    private InfoBusDeviceService infoBusDeviceService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "@desc AI设备表分页列表", httpMethod = "GET", notes = "@desc AI设备表管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("@desc AI设备表分页列表,参数:" + JSON.toJSONString(params));
        PageUtils page = infoBusDeviceService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 指定车辆列表
     */
    @GetMapping("/listByBusId")
    @ApiOperation(value = "@desc AI设备表列表指定车辆", httpMethod = "GET", notes = "@desc AI设备表指定车辆")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busId", value = "车辆ID", required = true, dataType = "long")
    })
    public R listByBusId(@RequestParam Map<String, Object> params) {
        logger.info("@desc AI设备表指定车辆,参数:" + JSON.toJSONString(params));
        List<InfoBusDeviceEntity> list = infoBusDeviceService.queryListByBusId(params);
        return R.ok().put("list", list);
    }

    @ApiOperation(value = "获取单条@desc AI设备表", httpMethod = "GET", notes = "获取单条@desc AI设备表")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条@desc AI设备表,参数ID:" + id);
        InfoBusDeviceEntity infoBusDeviceEntity;
        try {
            infoBusDeviceEntity = infoBusDeviceService.getDetailById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条@desc AI设备表失败");
        }
        return R.ok().put("data", infoBusDeviceEntity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存@desc AI设备表")
    @ApiOperation(value = "添加@desc AI设备表", httpMethod = "POST", notes = "@desc AI设备表管理页面中添加@desc AI设备表")
    public R save(@RequestBody InfoBusDeviceEntity infoBusDeviceEntity) {
        logger.info("保存@desc AI设备表,参数:" + JSON.toJSONString(infoBusDeviceEntity));
        ValidatorUtils.validateEntity(infoBusDeviceEntity);
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("bus_id",infoBusDeviceEntity.getBusId());
        paramMap.put("device_code",infoBusDeviceEntity.getDeviceCode());
        UniqueCheckHelper.assertIsUnique(infoBusDeviceService, paramMap, "同车辆中设备编号已存在！");

        try {
            infoBusDeviceService.save(infoBusDeviceEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存@desc AI设备表失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改@desc AI设备表")
    @ApiOperation(value = "修改@desc AI设备表", httpMethod = "POST", notes = "@desc AI设备表管理页面中修改@desc AI设备表")
    public R update(@RequestBody InfoBusDeviceEntity infoBusDeviceEntity) {
        logger.info("修改@desc AI设备表,参数:" + JSON.toJSONString(infoBusDeviceEntity));
        ValidatorUtils.validateEntity(infoBusDeviceEntity);
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("bus_id",infoBusDeviceEntity.getBusId());
        paramMap.put("device_code",infoBusDeviceEntity.getDeviceCode());
        UniqueCheckHelper.assertIsUnique(infoBusDeviceService, paramMap, "bus_device_id",infoBusDeviceEntity.getBusDeviceId(),"同车辆中设备编号已存在！");

        try {
            infoBusDeviceService.updateById(infoBusDeviceEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改@desc AI设备表失败");
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除@desc AI设备表", httpMethod = "POST", notes = "@desc AI设备表管理页面中删除@desc AI设备表")
    @SysLog("单条删除@desc AI设备表")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除@desc AI设备表,id:" + id);
        try {
            infoBusDeviceService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改@desc AI设备表失败");
        }
        return R.ok();
    }

    @ApiOperation(value = "批量删除@desc AI设备表", httpMethod = "POST", notes = "批量删除@desc AI设备表")
    @SysLog("批量删除@desc AI设备表")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除@desc AI设备表,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");
//校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");


        try {
            infoBusDeviceService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!");
        }

        return R.ok("批量删除成功!");
    }

}
