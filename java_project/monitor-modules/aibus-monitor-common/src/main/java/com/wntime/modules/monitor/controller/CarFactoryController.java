package com.wntime.modules.monitor.controller;

//import com.wntime.advert.service.AdvertiseService;
//import com.wntime.advert.vo.AdvertisingStatisticsDataVo;
import com.wntime.common.utils.R;
import com.wntime.event.serive.EventService;
import com.wntime.event.vo.PassengerGenderStatisticsVo;
import com.wntime.maintain.service.MaintenanceService;
import com.wntime.modules.monitor.form.QueryRealTimeCarStatisticsFrom;
import com.wntime.modules.monitor.service.MonitorService;
import com.wntime.modules.monitor.vo.OperationStatisticsCountVo;
import com.wntime.modules.monitor.vo.WeatherVo;
import com.wntime.service.common.service.*;
import com.wntime.service.common.vo.*;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "★汽车厂商controller",tags = {"★国泰大屏接口"})
@RequestMapping("/carFactory")
@RestController
public class CarFactoryController {


    @Resource
    private BusInfoService busInfoService;
    @Resource
    private CompanyLineService companyLineService;

    @Resource
    private BusOrderService busOrderService;
    @Resource
    private EventService eventService;
    @Resource
    private BusDriveLogService busDriveLogService;
    @Resource
    private MaintenanceService maintenanceService;
    @Resource
    private MonitorService monitorService;
    @Resource
    private BusFactoryService busFactoryService;

//    @Resource
//    private AdvertiseService advertiseService;

    @GetMapping("operation/statistics")
    @ApiOperation(value = "汽车企业运行统计",httpMethod = "GET",notes = "查询国泰汽车运营统计数据，包括汽车交付数量、总行驶里程、维护养护次数、承载总人数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "factoryId", value = "公交车厂id", required = true, dataType = "long")
    })
    //@Cacheable(cacheNames = "cache_operation_statistic",key = "#factoryId")
    public R operationStatistics(@RequestParam long factoryId){
        Map<String, Object> result=new HashMap<>();
        result.put("deliver",new OperationStatisticsCountVo("汽车交付量",busOrderService.queryFactoryOrderSum(factoryId)));
        result.put("mileage",new OperationStatisticsCountVo("总行驶里程",busDriveLogService.queryBusTotalMileSum(factoryId)));
        result.put("maintain",new OperationStatisticsCountVo("维护保养车次",maintenanceService.queryFactoryBusMaintenanceCount(factoryId)));
        result.put("bearing",new OperationStatisticsCountVo("承载总人次",eventService.queryPassengerSum(factoryId)));
        return R.ok(result);
    }

    @GetMapping("passenger/gender/statistics")
    @ApiOperation(value = "乘客性别统计",httpMethod = "GET",notes = "查询国泰汽车乘客性别统计")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "factoryId", value = "公交车厂id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = PassengerGenderStatisticsVo.class,responseContainer = "List")
    })
    public R passengerGenderStatistics(@RequestParam long factoryId){
        List<PassengerGenderStatisticsVo> result = eventService.queryPassengerGenderFlowInfoAnalysis(factoryId);
        return R.ok().put("list",result);
    }

    @GetMapping("city/sales/volume")
    @ApiOperation(value = "汽车城市区域销量走势",httpMethod = "GET",notes = "查询国泰汽车实时销量走势")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "factoryId", value = "公交车厂id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = FactorySaleVo.class,responseContainer = "List")
    })
    public R citySaleVolume(@RequestParam long factoryId){
        //查询公交公司所属的区域及坐标
        List<FactorySaleVo> list = busOrderService.queryFactorySalesVolume(factoryId);
        return R.ok().put("list",list);
    }

    @GetMapping("order/statistics")
    @ApiOperation(value = "汽车订单统计",httpMethod = "GET",notes = "查询国泰汽车订单信息统计")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "factoryId", value = "公交车厂id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = OrderStatisticsVo.class,responseContainer = "List")
    })
    public R orderStatistics(@RequestParam long factoryId){
        List<OrderStatisticsVo> result=busOrderService.queryFactoryOrderStatistic(factoryId);
        return R.ok().put("list",result);
    }


//    @GetMapping("city/advertising/range")
//    @ApiOperation(value = "线路广告投放量统计",httpMethod = "GET",notes = "查询国泰汽车按公交排名的广告投放量")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "factoryId", value = "公交车厂id", required = true, dataType = "long")
//    })
//    @ApiResponses({
//            @ApiResponse(code = 200,message = "success",response = AdvertisingStatisticsDataVo.class)
//    })
//    public R advertisingStatistics(@RequestParam long factoryId ,@RequestParam int size){
//        AdvertisingStatisticsDataVo dataVo = advertiseService.queryFactoryBusAdvertiseStatistics(factoryId,size);
//        if(dataVo.getDataX().size()<size){
//            int xSize = dataVo.getDataX().size();
//            for(int i=xSize;i<size;i++){
//                dataVo.addDataX("暂无");
//                dataVo.addDataY(0);
//            }
//        }
//        return R.ok().put("data",dataVo);
//    }

    @GetMapping("bus/realtime/statistics/list")
    @ApiOperation(value = "★查询实时展示车辆列表",httpMethod = "GET",notes = "查询国泰汽车车辆列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "factoryId", value = "公交车厂id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = BusInfoVo.class,responseContainer = "List")
    })
    public R busRealtimeStatisticsList(@Validated QueryRealTimeCarStatisticsFrom queryRealTimeCarStatisticsFrom){
        List<BusInfoVo> list = busInfoService.queryWithFuzzyMatch(queryRealTimeCarStatisticsFrom.getFactoryId(),queryRealTimeCarStatisticsFrom.getKeyword(), queryRealTimeCarStatisticsFrom.getSize());
        return R.ok().put("list",list);
    }

    @GetMapping("bus/realtime/statistics")
    @ApiOperation(value = "★查询实时展示车辆",httpMethod = "GET",notes = "查询国泰汽车具体车辆的实时情况,车辆状态良好的")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busId", value = "公交车id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = BusRealtimeStatisticsVo.class,responseContainer = "List")
    })
    public R busRealtimeStatistics(@RequestParam long busId){
        BusRealtimeStatisticsVo vo= busInfoService.queryBusMonitorStatistics(busId);

        BusBaseInfoVo busInfoVo = busInfoService.queryBusInfo(busId);
        vo.setVinCode(busInfoVo.getVinCode());
        vo.setPlateCode(busInfoVo.getPlateCode());
        BusLineVo busLine = companyLineService.queryBusLine(busId);
        vo.setAreaName(busLine.getAreaName());
        vo.setCompanyLineName(busLine.getCompanyLineCode());
        vo.setCompanyName(busLine.getCompanyName());

        List<BusRealtimeStatisticsVo> result=new ArrayList<>();
        result.add(vo);
        return R.ok().put("list",result);
    }

    @GetMapping("real/weather")
    @ApiOperation(value = "获取实时天气情况")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "factoryId", value = "公交车厂id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = WeatherVo.class)
    })
    public R realWeather(@RequestParam long factoryId) {
        AreaVo areaInfo = busFactoryService.queryFactoryAreaInfo(factoryId);
        WeatherVo res = monitorService.getWeather(areaInfo);
        return R.ok().put("weather",res).put("city",areaInfo.getAreaName());
    }
}
