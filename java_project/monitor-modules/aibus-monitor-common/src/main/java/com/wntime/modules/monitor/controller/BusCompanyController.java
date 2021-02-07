package com.wntime.modules.monitor.controller;

import com.wntime.common.utils.R;
import com.wntime.controller.AbstractController;
import com.wntime.event.serive.EventService;
import com.wntime.event.vo.HarmfulAlarmVo;
import com.wntime.event.vo.LineFullSeatRateRankVo;
import com.wntime.event.vo.PassengerFlowVo;
import com.wntime.modules.monitor.service.MonitorService;
import com.wntime.modules.monitor.vo.AlarmVo;
import com.wntime.modules.monitor.vo.DriverStatusVo;
import com.wntime.modules.monitor.vo.FutureWeatherVo;
import com.wntime.modules.monitor.vo.WeatherVo;
import com.wntime.service.common.service.BusCompanyService;
import com.wntime.service.common.service.BusInfoService;
import com.wntime.service.common.service.CompanyLineService;
import com.wntime.service.common.vo.*;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Api(value = "★公交公司controller",tags = {"★公交大屏接口"})
@RequestMapping("/busCompany")
@RestController
public class BusCompanyController extends AbstractController {

    @Resource
    private EventService eventService;

    @Resource
    private BusInfoService busInfoService;

    @Resource
    private BusCompanyService busCompanyService;

    @Resource
    private CompanyLineService companyLineService;

    @Resource
    private MonitorService monitorService;

    @GetMapping("list")
    @ApiOperation(value = "查询公交公司列表",httpMethod = "GET",notes = "查询当前公司子公司，如果是子公司则返回本身")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公交公司id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = BusCompanyBaseInfoVo.class,responseContainer = "list")
    })
    public R companyList(@RequestParam long companyId){
        List<BusCompanyBaseInfoVo> list = busCompanyService.queryCompanyList(companyId);
        return R.ok().put("list",list);
    }

    @GetMapping("/default/busId")
    @ApiOperation(value = "查询公交车id",httpMethod = "GET",notes = "根据公交公司id随机获取一辆(正常的)公交车id")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公交公司id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = Long.class)
    })
    public R randomBusId(@RequestParam long companyId){
        Long busId = companyLineService.queryCompanyLineBusId(companyId);
        return R.ok().put("busId",busId==null ? "" : String.valueOf(busId));
    }

    @GetMapping("operation")
    @ApiOperation(value = "车辆运行情况",httpMethod = "GET",notes = "通过公交公司id获取车辆的分类数据，包括正常运行车辆，报警车辆，待修车辆，原厂维修车辆")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公交公司id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = BusStatusSummaryItemVo.class,responseContainer = "list")
    })
    public R busOperation(@RequestParam long companyId){
        Map<String, Object> result = busInfoService.queryBusStatusSummary(companyId);
        return R.ok(result);
    }



    @GetMapping("lines")
    @ApiOperation(value = "公交线路运行",httpMethod = "GET",notes = "查询公交公司所有的运行线路")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公交公司id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = CompanyLineNameVo.class,responseContainer = "list")
    })
    public R lines(@RequestParam long companyId){
        List<CompanyLineNameVo> list = companyLineService.queryCompanyLineNames(companyId);
        return R.ok().put("list",list);
    }

    @GetMapping("line/overview")
    @ApiOperation(value = "公交线路运行概览",httpMethod = "GET",notes = "查询公交公司所有的运行线路，包含线路的起始站信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公交公司id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = CompanyLineOverviewVo.class,responseContainer = "list")
    })
    public R lineOverview(@RequestParam long companyId){
        Collection<CompanyLineOverviewVo> list = companyLineService.queryCompanyLineOverview(companyId);
        return R.ok().put("list",list);
    }

    @GetMapping("line/stations")
    @ApiOperation(value = "公交线路站点信息",httpMethod = "GET",notes = "查询公交公司 运行线路的站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公交公司id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "companyLineName", value = "线路名称", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = BusLineStationVo.class,responseContainer = "list")
    })
    public R lineOverview(@RequestParam long companyId,@RequestParam @NotNull String companyLineName){
        List<BusLineStationVo> list = companyLineService.queryCompanyLineStations(companyId, companyLineName);
        return R.ok().put("lines",list);
    }

    @GetMapping("line")
    @ApiOperation(value = "公交实时运行情况",httpMethod = "GET",notes = "通过具体的线路或关键字搜索公交车列表，包括每辆车的运行状态,通过搜索关键字（vin、车牌去找对应车所在线路）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公交公司id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "companyLineId", value = "线路名称", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "vin码、车牌等", required = false, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = BusOperationVo.class,responseContainer = "list")
    })
    public R lineInfo(@RequestParam long companyId,@RequestParam long companyLineId, @RequestParam(required = false) String keyword){
        //查询出两条线路
        List<BusOperationVo> result = companyLineService.queryCompanyLines(companyId, companyLineId, keyword);
        return R.ok().put("list",result);
    }

    @GetMapping("real/weather")
    @ApiOperation(value = "获取实时天气情况")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公交公司id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = WeatherVo.class)
    })
    public R realWeather(@RequestParam long companyId) {
        AreaVo areaInfo = busCompanyService.queryCompanyAreaInfo(companyId);
        WeatherVo res = monitorService.getWeather(areaInfo);
        return R.ok().put("weather",res).put("city",areaInfo.getAreaName());
    }

    @GetMapping("weather/alarm")
    @ApiOperation(value = "天气预警播报",httpMethod = "GET",notes = "查询一段时间内某个地区的天气情况")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公交公司id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = FutureWeatherVo.class,responseContainer = "map")
    })
    public R weather(@RequestParam long companyId){
        AreaVo areaInfo = busCompanyService.queryCompanyAreaInfo(companyId);
        List<AlarmVo> res = monitorService.getAlarm(areaInfo);
        List<FutureWeatherVo> weathers = monitorService.getFutureWeather(areaInfo);
        return R.ok().put("alarms",res).put("weathers",weathers).put("city",areaInfo.getAreaName());
    }


    @GetMapping("loadFactor/rank")
    @ApiOperation(value = "满座率排名",httpMethod = "GET",notes = "查询公交公司运营下满座的公交车线路排行")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公交公司id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = LineFullSeatRateRankVo.class)
    })
    public R loadFactorRange(@RequestParam long companyId,@RequestParam(required = false,defaultValue = "5") Integer size){
        LineFullSeatRateRankVo result = eventService.queryCompanyLineFullSeatRateRank(companyId,size);
        if(result.getDataX().size()<size){
            int xSize = result.getDataX().size();
            for(int i=xSize;i<size;i++){
                result.addDataX("暂无");
                result.addDataY(0);
            }
        }
        return R.ok().put("rankData",result);
    }


    @GetMapping("realtime/driver/status")
    @ApiOperation(value = "★公交车驾驶员实时状态查看",httpMethod = "GET",notes = "查询指定公交车的实时状态等")
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = DriverStatusVo.class,responseContainer = "Map")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busCompanyId", value = "公交公司id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "busId", value = "公交车id", required = true, dataType = "long")
    })
    public R realTimDriverStatus(@RequestParam long busCompanyId, @RequestParam long busId){
        Map result = eventService.driverViolationMap(busId);
        return R.ok(result);
    }

    @GetMapping("realtime/bus/status")
    @ApiOperation(value = "★公交车实时行驶信息查看")
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = BusDrivingBaseDataVo.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busCompanyId", value = "公交公司id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "busId", value = "公交车id", required = true, dataType = "long")
    })
    public R realtimeBusStatus(@RequestParam long busCompanyId, @RequestParam long busId){
        BusDrivingBaseDataVo vo = busInfoService.queryBusDrivingData(busId);
        BusLineVo line = companyLineService.queryBusLine(busId);
        if(line!=null){
            vo.setCompanyLineName(line.getCompanyLineCode());
        }
        return R.ok().put("drivingData",vo);
    }

    @GetMapping("realtime/passenger/status")
    @ApiOperation(value = "★公交车乘客量实时数据信息")
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = PassengerFlowVo.class,responseContainer = "List")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busCompanyId", value = "公交公司id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "busId", value = "公交车id", required = true, dataType = "long")
    })
    public R realtimePassengerStatus(@RequestParam long busCompanyId, @RequestParam long busId){
        List<PassengerFlowVo> result = eventService.queryPassengerFlowInfo(busId);

        return R.ok().put("list",result);
    }

    @GetMapping("realtime/environment/status")
    @ApiOperation(value = "★公交车内环境实时数据")
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = HarmfulAlarmVo.class,responseContainer = "List")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busCompanyId", value = "公交公司id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "busId", value = "公交车id", required = true, dataType = "long")
    })
    public R realtimeEnvironmentStatus(@RequestParam long busCompanyId, @RequestParam long busId){
        List<HarmfulAlarmVo> result = eventService.queryHarmfulAlarmInfo(busId);
        return R.ok().put("list",result);
    }


}
