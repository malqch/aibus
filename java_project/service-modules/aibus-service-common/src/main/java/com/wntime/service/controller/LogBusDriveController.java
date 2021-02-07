package com.wntime.service.controller;

import java.util.Arrays;
import java.util.Map;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import com.wntime.service.common.entity.LogBusDriveEntity;
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

import com.wntime.service.service.LogBusDriveService;



/**
 * @desc 行驶日志表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 16:36:18
 */
@RestController
@RequestMapping("service/logbusdrive")
@Api(value = "行驶日志管理", tags = {"行驶日志管理"})
public class LogBusDriveController {
    @Autowired
    private LogBusDriveService logBusDriveService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("service:logbusdrive:list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "vinCode", value = "公交车VIN", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "busTotalMileMin", value = "总里程小", required = false, dataType = "double"),
            @ApiImplicitParam(paramType = "query", name = "busTotalMileMax", value = "总里程大", required = false, dataType = "double"),
            @ApiImplicitParam(paramType = "query", name = "busStatusId", value = "车辆状态", required = false, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "motorStatusId", value = "电机状态", required = false, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "batteryStatusId", value = "电池状态", required = false, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "string")
    })
    @SysLog("分页查询@desc 行驶日志表")
    @ApiOperation(value = "分页查询", httpMethod = "GET", notes = "分页查询")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = logBusDriveService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{busDriveId}")
    @RequiresPermissions("service:logbusdrive:info")
    public R info(@PathVariable("busDriveId") Long busDriveId){
        LogBusDriveEntity logBusDrive = logBusDriveService.getById(busDriveId);

        return R.ok().put("logBusDrive", logBusDrive);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("service:logbusdrive:save")
    public R save(@RequestBody LogBusDriveEntity logBusDrive){
        logBusDriveService.save(logBusDrive);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("service:logbusdrive:update")
    public R update(@RequestBody LogBusDriveEntity logBusDrive){
        ValidatorUtils.validateEntity(logBusDrive);
        logBusDriveService.updateById(logBusDrive);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("service:logbusdrive:delete")
    public R delete(@RequestBody Long[] busDriveIds){
        logBusDriveService.removeByIds(Arrays.asList(busDriveIds));

        return R.ok();
    }

}
