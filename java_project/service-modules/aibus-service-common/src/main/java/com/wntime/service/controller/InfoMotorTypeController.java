package com.wntime.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.service.common.vo.DeleteBatchVo;
import com.wntime.service.entity.InfoBatteryTypeEntity;
import com.wntime.service.entity.InfoDeviceTypeEntity;
import com.wntime.service.entity.InfoMotorTypeEntity;
import com.wntime.service.service.InfoMotorTypeService;

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
 * @desc 电机类型表
 *
 * @author psp
 * @date 2020-08-31 11:07:44
 */
@Api(value = "@desc 电机类型表", tags = {"@desc 电机类型表"})
@RestController
@RequestMapping("service/infomotortype")
public class InfoMotorTypeController extends AbstractController {
    @Autowired
    private InfoMotorTypeService infoMotorTypeService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "@desc 电机类型表分页列表", httpMethod = "GET", notes = "@desc 电机类型表管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("@desc 电机类型表分页列表,参数:" + JSON.toJSONString(params));
        PageUtils page = infoMotorTypeService.queryPage(params);
        return R.ok().put("page", page);
    }

    @ApiOperation(value = "获取单条@desc 电机类型表", httpMethod = "GET", notes = "获取单条@desc 电机类型表")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条@desc 电机类型表,参数ID:" + id);
        InfoMotorTypeEntity infoMotorTypeEntity;
        try {
            infoMotorTypeEntity = infoMotorTypeService.getDetailById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条@desc 电机类型表失败");
        }
        return R.ok().put("data", infoMotorTypeEntity);
    }

    @ApiOperation(value = "获取全部电机类型", httpMethod = "GET", notes = "获取全部电机类型")
    @GetMapping("/getComboBox")
    public R getComboBox() {
        logger.info("开始查询电机类型");
        List<InfoMotorTypeEntity> result = infoMotorTypeService.getAll();
        return R.ok().put("selection", result);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存@desc 电机类型表")
    @ApiOperation(value = "添加@desc 电机类型表", httpMethod = "POST", notes = "@desc 电机类型表管理页面中添加@desc 电机类型表")
    public R save(@RequestBody InfoMotorTypeEntity infoMotorTypeEntity) {
        logger.info("保存@desc 电机类型表,参数:" + JSON.toJSONString(infoMotorTypeEntity));
        ValidatorUtils.validateEntity(infoMotorTypeEntity);
        try {
            QueryWrapper<InfoMotorTypeEntity> QueryWrapper = new QueryWrapper<InfoMotorTypeEntity>()
                    .eq("is_deleted", 0)
                    .eq("device_type_id",infoMotorTypeEntity.getDeviceTypeId())
                    .eq("motor_type_name",infoMotorTypeEntity.getMotorTypeName());

            InfoMotorTypeEntity get = infoMotorTypeService.getOne(QueryWrapper);
            if(get != null){
                return R.error("电机类型[电机型号:"+infoMotorTypeEntity.getMotorTypeName()+"]数据已存在，保存失败。");
            }
            infoMotorTypeService.save(infoMotorTypeEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存@desc 电机类型表失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改@desc 电机类型表")
    @ApiOperation(value = "修改@desc 电机类型表", httpMethod = "POST", notes = "@desc 电机类型表管理页面中修改@desc 电机类型表")
    public R update(@RequestBody InfoMotorTypeEntity infoMotorTypeEntity) {
        logger.info("修改@desc 电机类型表,参数:" + JSON.toJSONString(infoMotorTypeEntity));
        ValidatorUtils.validateEntity(infoMotorTypeEntity);
        try {
            QueryWrapper<InfoMotorTypeEntity> QueryWrapper = new QueryWrapper<InfoMotorTypeEntity>()
                    .eq("is_deleted", 0)
                    .ne("motor_type_id",infoMotorTypeEntity.getMotorTypeId())
                    .eq("device_type_id",infoMotorTypeEntity.getDeviceTypeId())
                    .eq("motor_type_name",infoMotorTypeEntity.getMotorTypeName());

            InfoMotorTypeEntity get = infoMotorTypeService.getOne(QueryWrapper);
            if(get != null){
                return R.error("电机类型[电机型号:"+infoMotorTypeEntity.getMotorTypeName()+"]数据已存在，保存失败。");
            }
            infoMotorTypeService.updateById(infoMotorTypeEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除@desc 电机类型表", httpMethod = "POST", notes = "@desc 电机类型表管理页面中删除@desc 电机类型表")
    @SysLog("单条删除@desc 电机类型表")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除@desc 电机类型表,id:" + id);
        try {
            infoMotorTypeService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    @ApiOperation(value = "批量删除@desc 电机类型表", httpMethod = "POST", notes = "批量删除@desc 电机类型表")
    @SysLog("批量删除@desc 电机类型表")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除@desc 电机类型表,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            infoMotorTypeService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }

        return R.ok("批量删除成功!");
    }


}
