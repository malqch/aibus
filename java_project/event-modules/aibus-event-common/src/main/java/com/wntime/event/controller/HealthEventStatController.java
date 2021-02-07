package com.wntime.event.controller;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.event.service.LogEventDetailService;
import com.wntime.event.vo.EventHealthTempStatVo;
import com.wntime.service.common.service.BusStationService;
import com.wntime.service.common.vo.BusStationVo;
import com.wntime.service.common.vo.StatisticsResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ysc
 * 2020/8/28 10:21
 */
@Api(value = "公共卫生数据分析",tags = {"公共卫生数据分析"})
@RequestMapping("/event/health")
@RestController
public class HealthEventStatController {

    @Autowired
    private LogEventDetailService logEventDetailService;

    @Autowired
    private BusStationService busStationService;

    @ApiOperation(value = "公共卫生体温统计（指定公司、线路）", notes = "公共卫生体温统计（指定公司、线路）", httpMethod = "GET")
    @GetMapping("/temperature/statByCompanyAndLine")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query",name="companyId",value="公司Id",required = true,dataType = "long"),
            @ApiImplicitParam(paramType = "query",name="companyLineId",value="公交线路Id",required = true,dataType = "long"),
            @ApiImplicitParam(paramType = "query",name="busStationName",value="线路站点名",required = false,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="params",value="参数",dataType = "string")
    })
    public R getTemperatureStatByCompanyAndLine(@RequestParam Map<String, Object> params){
        // 获取指定公司/线路下的统计站点
        List<BusStationVo> busStationVos = busStationService.getStationsByCompanyAndLine(params);
        List<Long> busStationIdList = new ArrayList<>();
        if(busStationVos != null && busStationVos.size() > 0){
            busStationIdList = busStationVos.stream().map(BusStationVo::getBusStationId).collect(Collectors.toList());
        }else{
            return R.ok().put("list",new PageUtils(new ArrayList<>(), 0, 0, 0));
        }

//        // 查询公司下车辆
//        List<Long> companyIdList = new ArrayList<>();
//        companyIdList.add(Long.parseLong(String.valueOf(params.get("companyId"))));
//        List<InfoBusEntity> infoBusEntityList = busInfoService.getAllBusByAreaId(0L, companyIdList);
//        if (infoBusEntityList == null || infoBusEntityList.size() == 0) {
//            return R.ok().put("list", new PageUtils(new ArrayList<>(), 0, 0, 0));
//        }
//        List<Long> busIdList = infoBusEntityList.stream().map(InfoBusEntity::getBusId).collect(Collectors.toList());
//        params.put("busIdList", busIdList);
        params.put("busStationIdList",busStationIdList);
        PageUtils<EventHealthTempStatVo> pages = logEventDetailService.getTempStatByCompanyAndLinePage(params);
        return R.ok().put("list",pages);
    }

    @ApiOperation(value = "查看公共卫生体温照片", notes = "查看公共卫生体温照片", httpMethod = "GET")
    @GetMapping("/temperature/healthDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="eventDetailId",value="事件日志Ids",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="params",value="参数",dataType = "string")
    })
    public R getTemperatureStatDetailByEventDetailId(@RequestParam Map<String, Object> params){
        // 获取指定公司/线路下的统计站点
        List<Long> eventDetailIdList = new ArrayList<>();
        if(params.get("eventDetailId") != null){
            String ids =  String.valueOf(params.get("eventDetailId"));
            eventDetailIdList = Arrays.stream(ids.split(",")).map(str -> Long.parseLong(str)).collect(Collectors.toList());
        }
        List<StatisticsResultVo> detailImgList = logEventDetailService.getTempDetailByEventDetailId(eventDetailIdList);
        return R.ok().put("list",detailImgList);
    }
}
