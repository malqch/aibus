package com.wntime.service.controller;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.service.common.vo.DeleteBatchVo;
import com.wntime.service.entity.InfoConfigParamEntity;
import com.wntime.service.service.InfoConfigParamService;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author psp
 * @desc 配置参数表
 * @date 2020-08-25 14:28:17
 */
@Api(value = "配置参数接口", tags = {"配置参数管理"})
@RestController
@RequestMapping("service/infoConfigParam")
public class InfoConfigParamController extends AbstractController {
    @Autowired
    private InfoConfigParamService infoConfigParamService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "配置参数分页列表", httpMethod = "GET", notes = "配置参数管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "paramName", value = "配置参数", dataType = "String")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("配置参数分页列表,参数:" + JSON.toJSONString(params));
        PageUtils page = infoConfigParamService.queryPage(params);
        return R.ok().put("page", page);
    }

    @ApiOperation(value = "通过paramGroup获取数据", httpMethod = "GET", notes = "通过paramGroup获取数据")
    @GetMapping("/getListByParamGroup")
    public R getListByParamGroup(String paramGroup) {
        logger.info("通过paramGroup获取数据,paramGroup:"+paramGroup);
        List<InfoConfigParamEntity> result = infoConfigParamService.getListByParamGroup(paramGroup);
        return R.ok().put("selection", result);
    }

    @ApiOperation(value = "获取单条配置参数", httpMethod = "GET", notes = "获取单条配置参数")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条配置参数,参数ID:" + id);
        InfoConfigParamEntity infoConfigParamEntity;
        try {
            infoConfigParamEntity = infoConfigParamService.getDetailById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条配置参数失败");
        }
        return R.ok().put("data", infoConfigParamEntity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存配置参数")
    @ApiOperation(value = "添加配置参数", httpMethod = "POST", notes = "配置参数管理页面中添加配置参数")
    public R save(@RequestBody InfoConfigParamEntity infoConfigParam) {
        logger.info("保存配置参数,参数:" + JSON.toJSONString(infoConfigParam));
        ValidatorUtils.validateEntity(infoConfigParam);
        try {
            infoConfigParamService.save(infoConfigParam, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存配置参数失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改配置参数")
    @ApiOperation(value = "修改配置参数", httpMethod = "POST", notes = "配置参数管理页面中修改配置参数")
    public R update(@RequestBody InfoConfigParamEntity infoConfigParam) {
        logger.info("修改配置参数,参数:" + JSON.toJSONString(infoConfigParam));
        ValidatorUtils.validateEntity(infoConfigParam);
        try {
            infoConfigParamService.updateById(infoConfigParam, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改配置参数失败");
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除配置参数", httpMethod = "POST", notes = "配置参数管理页面中删除配置参数")
    @SysLog("单条删除配置参数")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除配置参数,id:" + id);
        try {
            infoConfigParamService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改配置参数失败");
        }
        return R.ok();
    }

    @ApiOperation(value = "批量删除配置参数", httpMethod = "POST", notes = "批量删除配置参数")
    @SysLog("批量删除配置参数")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除配置参数,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            infoConfigParamService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!");
        }

        return R.ok("批量删除成功!");
    }

    /**
     * 查询里程范围配置
     */
    @ApiOperation(value = "查询里程范围配置", notes = "查询里程范围配置", httpMethod = "GET")
    @GetMapping("/config/busMiles")
    public R getFaultBusMileConfig() {
        List<Double> list = new ArrayList<>();
        List<InfoConfigParamEntity> configList = infoConfigParamService.getConfigBusMilesList();
        if(configList != null && configList.size() > 0){
            list = configList.stream().map(InfoConfigParamEntity::getParamValue).collect(Collectors.toList());
        }
        return R.ok().put("milesData",list);
    }
}
