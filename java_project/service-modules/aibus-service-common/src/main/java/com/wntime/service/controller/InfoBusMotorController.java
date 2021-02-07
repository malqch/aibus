package com.wntime.service.controller;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.service.common.util.UniqueCheckHelper;
import com.wntime.service.common.vo.DeleteBatchVo;
import com.wntime.service.entity.InfoBusDeviceEntity;
import com.wntime.service.entity.InfoBusMotorEntity;
import com.wntime.service.service.InfoBusMotorService;

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
 * @author psp
 * 电机表
 * @date 2020-09-01 10:05:27
 */
@Api(value = "电机表", tags = {"电机表"})
@RestController
@RequestMapping("service/infobusmotor")
public class InfoBusMotorController extends AbstractController {
    @Autowired
    private InfoBusMotorService infoBusMotorService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "电机表分页列表", httpMethod = "GET", notes = "电机表管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("电机表分页列表,参数:" + JSON.toJSONString(params));
        PageUtils page = infoBusMotorService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 指定车辆列表
     */
    @GetMapping("/listByBusId")
    @ApiOperation(value = "电机表分页列表指定车辆", httpMethod = "GET", notes = "电机表管理指定车辆")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busId", value = "车辆ID", required = true, dataType = "long")
    })
    public R listByBusId(@RequestParam Map<String, Object> params) {
        logger.info("电机表指定车辆列表,参数:" + JSON.toJSONString(params));
        List<InfoBusMotorEntity> list = infoBusMotorService.queryListByBusId(params);
        return R.ok().put("list", list);
    }

    @ApiOperation(value = "获取单条电机表", httpMethod = "GET", notes = "获取单条电机表")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条电机表,参数ID:" + id);
        InfoBusMotorEntity infoBusMotorEntity;
        try {
            infoBusMotorEntity = infoBusMotorService.getDetailById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条电机表失败");
        }
        return R.ok().put("data", infoBusMotorEntity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存电机表")
    @ApiOperation(value = "添加电机表", httpMethod = "POST", notes = "电机表管理页面中添加电机表")
    public R save(@RequestBody InfoBusMotorEntity infoBusMotorEntity) {
        logger.info("保存电机表,参数:" + JSON.toJSONString(infoBusMotorEntity));
        ValidatorUtils.validateEntity(infoBusMotorEntity);
        UniqueCheckHelper.assertIsUnique(infoBusMotorService, "bus_motor_code", infoBusMotorEntity.getBusMotorCode(), "电机编号已存在！");

        try {
            infoBusMotorService.save(infoBusMotorEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存电机表失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改电机表")
    @ApiOperation(value = "修改电机表", httpMethod = "POST", notes = "电机表管理页面中修改电机表")
    public R update(@RequestBody InfoBusMotorEntity infoBusMotorEntity) {
        logger.info("修改电机表,参数:" + JSON.toJSONString(infoBusMotorEntity));
        ValidatorUtils.validateEntity(infoBusMotorEntity);
        UniqueCheckHelper.assertIsUnique(infoBusMotorService, "bus_motor_code", infoBusMotorEntity.getBusMotorCode(), "bus_motor_id", infoBusMotorEntity.getBusMotorId(), "电机编号已存在！");

        try {
            infoBusMotorService.updateById(infoBusMotorEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改电机表失败");
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除电机表", httpMethod = "POST", notes = "电机表管理页面中删除电机表")
    @SysLog("单条删除电机表")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除电机表,id:" + id);
        try {
            infoBusMotorService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改电机表失败");
        }
        return R.ok();
    }

    @ApiOperation(value = "批量删除电机表", httpMethod = "POST", notes = "批量删除电机表")
    @SysLog("批量删除电机表")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除电机表,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            infoBusMotorService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!");
        }

        return R.ok("批量删除成功!");
    }

}
