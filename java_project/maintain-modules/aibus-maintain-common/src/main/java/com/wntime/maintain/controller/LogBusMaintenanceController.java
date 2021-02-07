package com.wntime.maintain.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import com.wntime.maintain.vo.MaintainBusVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.wntime.maintain.entity.LogBusMaintenanceEntity;
import com.wntime.maintain.service.LogBusMaintenanceService;


/**
 * @author Mark
 * @desc 维保日志表
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:00:24
 */
@Api(value = "维保日志管理", tags = {"维保日志管理"})
@RestController
@RequestMapping("maintenance")
public class LogBusMaintenanceController {
    @Autowired
    private LogBusMaintenanceService logBusMaintenanceService;

    /**
     * 获取维修车辆信息列表
     */
    @ApiOperation(value = "获取维修车辆信息列表", httpMethod = "GET", notes = "status的值，0代表维修中，1代表维修完")
    @RequestMapping("/status/{status}/buses/")
    public R getBuses(@PathVariable Long status, @RequestParam Long companyId,
                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date startTime,
                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime) {

        List<MaintainBusVO> list = logBusMaintenanceService.getMaintainBusDetailByStatusAndCompanyIdAndTime(status, companyId, startTime, endTime);
        return R.ok().put("list", list);
    }

    @ApiOperation(value = "车辆维修", httpMethod = "PUT", notes = "修改车辆状态,记录维修日志")
    @PutMapping("/bus/{busId}/status/{busStatusId}")
    public R repairBus(@PathVariable Long busId, @PathVariable Long busStatusId,
                       @RequestBody LogBusMaintenanceEntity logBusMaintenance) {
        ValidatorUtils.validateEntity(logBusMaintenance);
        int result = logBusMaintenanceService.repairBus(busId,busStatusId,logBusMaintenance);
        if (result == 1) {
            return R.ok();
        } else {
            return R.error("车辆维修失败");
        }
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("customer:logbusmaintenance:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = logBusMaintenanceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{maintenanceId}")
    @RequiresPermissions("customer:logbusmaintenance:info")
    public R info(@PathVariable("maintenanceId") Long maintenanceId) {
        LogBusMaintenanceEntity logBusMaintenance = logBusMaintenanceService.getById(maintenanceId);

        return R.ok().put("logBusMaintenance", logBusMaintenance);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("customer:logbusmaintenance:save")
    public R save(@RequestBody LogBusMaintenanceEntity logBusMaintenance) {
        logBusMaintenanceService.save(logBusMaintenance);

        return R.ok();
    }

    /**
     * 新增或修改维修日志
     */
    @ApiOperation(value = "新增或修改维修日志", httpMethod = "PUT", notes = "")
    @PutMapping("/update")
    public R update(@RequestBody LogBusMaintenanceEntity logBusMaintenance) {
        ValidatorUtils.validateEntity(logBusMaintenance);
        if(logBusMaintenance.getMaintenanceId() == null){
            logBusMaintenanceService.save(logBusMaintenance);
        }else {
            logBusMaintenanceService.updateById(logBusMaintenance);
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("customer:logbusmaintenance:delete")
    public R delete(@RequestBody Long[] maintenanceIds) {
        logBusMaintenanceService.removeByIds(Arrays.asList(maintenanceIds));

        return R.ok();
    }

}
