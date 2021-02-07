package com.wntime.service.controller;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.service.common.util.UniqueCheckHelper;
import com.wntime.service.common.vo.DeleteBatchVo;
import com.wntime.service.entity.InfoBusBatteryEntity;
import com.wntime.service.entity.InfoBusMotorEntity;
import com.wntime.service.service.InfoBusBatteryService;

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
 * @desc 电池表
 *
 * @author psp
 * @date 2020-09-01 14:16:19
 */
@Api(value = "@desc 电池表", tags = {"@desc 电池表"})
@RestController
@RequestMapping("service/infobusbattery")
public class InfoBusBatteryController extends AbstractController {
    @Autowired
    private InfoBusBatteryService infoBusBatteryService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "@desc 电池表分页列表", httpMethod = "GET", notes = "@desc 电池表管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("@desc 电池表分页列表,参数:" + JSON.toJSONString(params));
        PageUtils page = infoBusBatteryService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 指定车辆列表
     */
    @GetMapping("/listByBusId")
    @ApiOperation(value = "电池表分页列表指定车辆", httpMethod = "GET", notes = "电池表管理指定车辆")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busId", value = "车辆ID", required = true, dataType = "long")
    })
    public R listByBusId(@RequestParam Map<String, Object> params) {
        logger.info("电池表指定车辆列表,参数:" + JSON.toJSONString(params));
        List<InfoBusBatteryEntity> list = infoBusBatteryService.queryListByBusId(params);
        return R.ok().put("list", list);
    }

    @ApiOperation(value = "获取单条@desc 电池表", httpMethod = "GET", notes = "获取单条@desc 电池表")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条@desc 电池表,参数ID:" + id);
        InfoBusBatteryEntity infoBusBatteryEntity;
        try {
            infoBusBatteryEntity = infoBusBatteryService.getDetailById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条@desc 电池表失败");
        }
        return R.ok().put("data", infoBusBatteryEntity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存@desc 电池表")
    @ApiOperation(value = "添加@desc 电池表", httpMethod = "POST", notes = "@desc 电池表管理页面中添加@desc 电池表")
    public R save(@RequestBody InfoBusBatteryEntity infoBusBatteryEntity) {
        logger.info("保存@desc 电池表,参数:" + JSON.toJSONString(infoBusBatteryEntity));
        ValidatorUtils.validateEntity(infoBusBatteryEntity);
        UniqueCheckHelper.assertIsUnique(infoBusBatteryService, "bus_battery_code", infoBusBatteryEntity.getBusBatteryCode(), "电池编号已存在！");
        try {
            infoBusBatteryService.save(infoBusBatteryEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存@desc 电池表失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改@desc 电池表")
    @ApiOperation(value = "修改@desc 电池表", httpMethod = "POST", notes = "@desc 电池表管理页面中修改@desc 电池表")
    public R update(@RequestBody InfoBusBatteryEntity infoBusBatteryEntity) {
        logger.info("修改@desc 电池表,参数:" + JSON.toJSONString(infoBusBatteryEntity));
        ValidatorUtils.validateEntity(infoBusBatteryEntity);
        UniqueCheckHelper.assertIsUnique(infoBusBatteryService, "bus_battery_code", infoBusBatteryEntity.getBusBatteryCode(), "bus_battery_id",infoBusBatteryEntity.getBusBatteryId(),"电池编号已存在！");
        try {
            infoBusBatteryService.updateById(infoBusBatteryEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改@desc 电池表失败");
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除@desc 电池表", httpMethod = "POST", notes = "@desc 电池表管理页面中删除@desc 电池表")
    @SysLog("单条删除@desc 电池表")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除@desc 电池表,id:" + id);
        try {
            infoBusBatteryService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改@desc 电池表失败");
        }
        return R.ok();
    }

    @ApiOperation(value = "批量删除@desc 电池表", httpMethod = "POST", notes = "批量删除@desc 电池表")
    @SysLog("批量删除@desc 电池表")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除@desc 电池表,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            infoBusBatteryService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!");
        }

        return R.ok("批量删除成功!");
    }


}
