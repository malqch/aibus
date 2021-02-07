package com.wntime.event.controller;

import com.wntime.common.utils.R;
import com.wntime.common.validator.Assert;
import com.wntime.event.config.EventFileConfig;
import com.wntime.event.serive.LogEventDetailStatService;
import com.wntime.event.service.LogEventDetailService;
import com.wntime.service.common.service.AreaInfoService;
import com.wntime.service.common.service.BusStationService;
import com.wntime.service.common.util.LineChartUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author pengsl
 * 2020/8/28 10:21
 */
@Api(value = "公共服务开放接口",tags = {"公共服务开放接口"})
@RequestMapping("/event/public")
@RestController
public class EventPublicSearchApiController {
    @Autowired
    private AreaInfoService areaInfoService;
    @Autowired
    private BusStationService busStationService;
    @Autowired
    private LogEventDetailStatService logEventDetailStatService;

    @ApiOperation(value = "环境信息数据查询接口",notes = "环境信息数据查询接口",httpMethod = "GET")
    @GetMapping("/env/{type}/searchData")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path",name="type",value="环境数据类型（temperature,humidity,pm25,pm10）",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="areaName",value="地区名称",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="busStationName",value="车站名称",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="startTime",value="查询开始时间（与结束时间最大选择范围7天）",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="endTime",value="查询结束时间（与开始时间最大选择范围7天内）",dataType = "string")
    })
    public R getPublicEnvSearchData(@PathVariable String type,
                                 @RequestParam String areaName,
                                 @RequestParam String busStationName,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date startTime,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime){
        // 参数校验
        if(StringUtils.isBlank(type)){
            return R.error("参数[type]环境信息类型不能为空!");
        }
        if(StringUtils.isBlank(areaName)){
            return R.error("参数[areaName]所属地区名称不能为空!");
        }
        if(StringUtils.isBlank(busStationName)){
            return R.error("参数[busStationName]公交站点名称不能为空!");
        }
        R checkCode = checkTime(startTime,endTime,7);
        if(checkCode.getCode() != R.ok().getCode()){
            return checkCode;
        }
        // 验证地区+站点
        Long areaId = areaInfoService.getInfoAreaIdByName(areaName);
        if(areaId == 0L){
            return R.error("根据参数[areaName]所属地区名称未查询到对应的地区信息!");
        }
        Long busStationId = busStationService.getBusStationIdByName(areaId,busStationName);
        if(busStationId == 0L){
            return R.error("根据参数[areaName]所属地区名称、[busStationName]公交站点名称未查询到对应的站点信息!");
        }
        if("temperature,humidity,pm25,pm10".indexOf(type) < 0){
            return R.error("参数[type]环境信息类型指定值[temperature、humidity、pm25、pm10]!");
        }
        Map<String, Object> params = new HashedMap();
        params.put("type",type);
        params.put("areaId",areaId);
        params.put("busStationId",busStationId);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        return R.ok().put("list",logEventDetailStatService.getEnvSearchData(params));
    }

    @ApiOperation(value = "交通违章信息数据查询接口",notes = "交通违章信息数据查询接口",httpMethod = "GET")
    @GetMapping("/traffic/searchData")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="areaName",value="地区名称",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="startTime",value="查询开始时间（与结束时间最大选择范围90天）",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="endTime",value="查询结束时间（与开始时间最大选择范围90天内）",dataType = "string")
    })
    public R getPublicTrafficSearchData(
            @RequestParam String areaName,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime){
        if(StringUtils.isBlank(areaName)){
            return R.error("参数[areaName]所属地区名称不能为空!");
        }
        R checkCode = checkTime(startTime,endTime,90);
        if(checkCode.getCode() == R.ok().getCode()){
            return checkCode;
        }
        // 验证地区
        Long areaId = areaInfoService.getInfoAreaIdByName(areaName);
        if(areaId == 0L){
            return R.error("根据参数[areaName]所属地区名称未查询到对应的地区信息!");
        }

        Map<String, Object> params = new HashedMap();
        params.put("areaId",areaId);
        params.put("areaName",areaName);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        return R.ok().put("list",logEventDetailStatService.getTrafficSearchData(params));
    }

    @ApiOperation(value = "卫生信息数据查询接口",notes = "卫生信息数据查询接口",httpMethod = "GET")
    @GetMapping("/health/searchData")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="areaName",value="地区名称",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="startTime",value="查询开始时间（与结束时间最大选择范围90天）",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="endTime",value="查询结束时间（与开始时间最大选择范围90天内）",dataType = "string")
    })
    public R getPublicHealthSearchData(
            @RequestParam String areaName,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime){
        if(StringUtils.isBlank(areaName)){
            return R.error("参数[areaName]所属地区名称不能为空!");
        }
        R checkCode = checkTime(startTime,endTime,90);
        if(checkCode.getCode() == R.ok().getCode()){
            return checkCode;
        }
        // 验证地区
        Long areaId = areaInfoService.getInfoAreaIdByName(areaName);
        if(areaId == 0L){
            return R.error("根据参数[areaName]所属地区名称未查询到对应的地区信息!");
        }

        Map<String, Object> params = new HashedMap();
        params.put("areaId",areaId);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        return R.ok().put("list",logEventDetailStatService.getHealthSearchData(params));
    }

    /**
     * 检查 开始、结束时间
     * @param startTime
     * @param endTime
     * @param day
     * @return
     */
    public R checkTime(Date startTime,Date endTime,int day){
        if(startTime == null || StringUtils.isBlank(String.valueOf(startTime))){
            return R.error("参数[startTime]开始时间不能为空!");
        }
        if(endTime == null || StringUtils.isBlank(String.valueOf(endTime))){
            return R.error("参数[endTime]结束时间不能为空!");
        }
        // 范围不超过day天
        if(endTime.getTime() < startTime.getTime() ||
                (endTime.getTime() - startTime.getTime()) > day*24*60*60*1000){
            return R.error("参数[startTime]开始时间至参数[endTime]结束时间范围不可超过7天!");
        }

        return R.ok();
    }

}
