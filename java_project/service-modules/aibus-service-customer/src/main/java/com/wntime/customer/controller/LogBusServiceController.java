package com.wntime.customer.controller;

import java.util.Arrays;
import java.util.Map;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wntime.service.common.entity.LogBusServiceEntity;
import com.wntime.customer.service.LogBusServiceService;



/**
 * @desc 营运日志表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:00:25
 */
@RestController
@RequestMapping("customer/logbusservice")
@Api(value = "营运日志管理", tags = {"营运日志管理"})
public class LogBusServiceController {
    @Autowired
    private LogBusServiceService logBusServiceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("customer:logbusservice:list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "vinCode", value = "公交车VIN", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "plateCode", value = "车牌号", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "companyLine", value = "公交线路", required = false, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "busStation", value = "公交车站", required = false, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "string")
    })
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = logBusServiceService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{busServiceId}")
    @RequiresPermissions("customer:logbusservice:info")
    public R info(@PathVariable("busServiceId") Long busServiceId){
        LogBusServiceEntity logBusService = logBusServiceService.getById(busServiceId);

        return R.ok().put("logBusService", logBusService);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("customer:logbusservice:save")
    public R save(@RequestBody LogBusServiceEntity logBusService){
        logBusServiceService.save(logBusService);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("customer:logbusservice:update")
    public R update(@RequestBody LogBusServiceEntity logBusService){
        ValidatorUtils.validateEntity(logBusService);
        logBusServiceService.updateById(logBusService);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("customer:logbusservice:delete")
    public R delete(@RequestBody Long[] busServiceIds){
        logBusServiceService.removeByIds(Arrays.asList(busServiceIds));

        return R.ok();
    }

}
