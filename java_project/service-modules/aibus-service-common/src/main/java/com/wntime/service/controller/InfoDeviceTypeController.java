package com.wntime.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.service.common.vo.DeleteBatchVo;
import com.wntime.service.entity.InfoBusFactoryEntity;
import com.wntime.service.entity.InfoDeviceTypeEntity;
import com.wntime.service.service.InfoDeviceTypeService;

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
 * @desc 设备类型
 * @date 2020-08-29 16:19:14
 */
@Api(value = "设备类型", tags = {"设备类型"})
@RestController
@RequestMapping("service/infodevicetype")
public class InfoDeviceTypeController extends AbstractController {
    @Autowired
    private InfoDeviceTypeService infoDeviceTypeService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "设备类型分页列表", httpMethod = "GET", notes = "设备类型管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("设备类型分页列表,参数:" + JSON.toJSONString(params));
        PageUtils page = infoDeviceTypeService.queryPage(params);
        return R.ok().put("page", page);
    }

    @ApiOperation(value = "获取单条设备类型", httpMethod = "GET", notes = "获取单条设备类型")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条设备类型,参数ID:" + id);
        InfoDeviceTypeEntity infoDeviceTypeEntity;
        try {
            infoDeviceTypeEntity = infoDeviceTypeService.getDetailById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条设备类型失败");
        }
        return R.ok().put("data", infoDeviceTypeEntity);
    }

    @ApiOperation(value = "获取全部设备类型", httpMethod = "GET", notes = "获取全部设备类型")
    @GetMapping("/getComboBox")
    public R getComboBox() {
        logger.info("开始查询设备类型");
        List<InfoDeviceTypeEntity> result = infoDeviceTypeService.getAll();
        return R.ok().put("selection", result);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存设备类型")
    @ApiOperation(value = "添加设备类型", httpMethod = "POST", notes = "设备类型管理页面中添加设备类型")
    public R save(@RequestBody InfoDeviceTypeEntity infoDeviceTypeEntity) {
        logger.info("保存设备类型,参数:" + JSON.toJSONString(infoDeviceTypeEntity));
        ValidatorUtils.validateEntity(infoDeviceTypeEntity);
        try {
            QueryWrapper<InfoDeviceTypeEntity> QueryWrapper = new QueryWrapper<InfoDeviceTypeEntity>()
                    .eq("is_deleted", 0)
                    .eq("device_type_code",infoDeviceTypeEntity.getDeviceTypeCode());

            InfoDeviceTypeEntity get = infoDeviceTypeService.getOne(QueryWrapper);
            if(get != null){
                return R.error("设备类型[类型编码:"+infoDeviceTypeEntity.getDeviceTypeCode()+"]数据已存在，保存失败。");
            }

            infoDeviceTypeService.save(infoDeviceTypeEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存设备类型失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改设备类型")
    @ApiOperation(value = "修改设备类型", httpMethod = "POST", notes = "设备类型管理页面中修改设备类型")
    public R update(@RequestBody InfoDeviceTypeEntity infoDeviceTypeEntity) {
        logger.info("修改设备类型,参数:" + JSON.toJSONString(infoDeviceTypeEntity));
        ValidatorUtils.validateEntity(infoDeviceTypeEntity);
        try {
            QueryWrapper<InfoDeviceTypeEntity> QueryWrapper = new QueryWrapper<InfoDeviceTypeEntity>()
                    .eq("is_deleted", 0)
                    .ne("device_type_id",infoDeviceTypeEntity.getDeviceTypeId())
                    .eq("device_type_code",infoDeviceTypeEntity.getDeviceTypeCode());

            InfoDeviceTypeEntity get = infoDeviceTypeService.getOne(QueryWrapper);
            if(get != null){
                return R.error("设备类型[类型编码:"+infoDeviceTypeEntity.getDeviceTypeCode()+"]数据已存在，保存失败。");
            }
            infoDeviceTypeService.updateById(infoDeviceTypeEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除设备类型", httpMethod = "POST", notes = "设备类型管理页面中删除设备类型")
    @SysLog("单条删除设备类型")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除设备类型,id:" + id);
        try {
            infoDeviceTypeService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    @ApiOperation(value = "批量删除设备类型", httpMethod = "POST", notes = "批量删除设备类型")
    @SysLog("批量删除设备类型")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除设备类型,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            infoDeviceTypeService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }

        return R.ok("批量删除成功!");
    }


}
