package com.wntime.fault.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import com.wntime.fault.entity.LogFaultAttachEntity;
import com.wntime.fault.service.LogFaultAttachService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wntime.fault.entity.LogFaultDetailEntity;
import com.wntime.fault.service.LogFaultDetailService;



/**
 * @desc 故障日志表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@RestController
@RequestMapping("fault/logfaultdetail")
@Api(value = "故障日志管理", tags = {"故障日志管理"})
public class LogFaultDetailController {
    @Autowired
    private LogFaultDetailService logFaultDetailService;

    @Autowired
    private LogFaultAttachService logFaultAttachService;

    /**
     * 列表分页
     */
    @RequestMapping("/page")
//    @RequiresPermissions("fault:infofaulttype:list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "collectFault", value = "采集故障", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "vinCode", value = "公交车VIN", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "faultTypeId", value = "类型ID", required = false, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "faultTargetId", value = "标签ID", required = false, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "faultLevelId", value = "级别ID", required = false, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "string")
    })
    @SysLog("分页查询@desc 故障日志表")
    @ApiOperation(value = "分页查询", httpMethod = "GET", notes = "分页查询")
    public R page(@RequestParam Map<String, Object> params) {
        PageUtils page = logFaultDetailService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fault:logfaultdetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = logFaultDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{faultDetailId}")
    @SysLog("查询详情+附件@desc 故障日志表")
//    @RequiresPermissions("fault:logfaultdetail:info")
    @ApiOperation(value = "查询详细信息+附件", httpMethod = "GET", notes = "查询详细信息+附件")
    public R info(@PathVariable("faultDetailId") Long faultDetailId){
        LogFaultDetailEntity logFaultDetail = logFaultDetailService.getDetailById(faultDetailId);
        List<LogFaultAttachEntity> list = logFaultAttachService.queryListByEventId(faultDetailId);
        return R.ok().put("data", logFaultDetail).put("list",list);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fault:logfaultdetail:save")
    public R save(@RequestBody LogFaultDetailEntity logFaultDetail){
        logFaultDetailService.save(logFaultDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fault:logfaultdetail:update")
    public R update(@RequestBody LogFaultDetailEntity logFaultDetail){
        ValidatorUtils.validateEntity(logFaultDetail);
        logFaultDetailService.updateById(logFaultDetail);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fault:logfaultdetail:delete")
    public R delete(@RequestBody Long[] faultDetailIds){
        logFaultDetailService.removeByIds(Arrays.asList(faultDetailIds));

        return R.ok();
    }

}
