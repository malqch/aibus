package com.wntime.modules.monitor.controller;

import com.wntime.common.utils.R;
import com.wntime.controller.AbstractController;
import com.wntime.event.serive.EventService;
import com.wntime.event.vo.BusAlarmInfoVo;
import com.wntime.event.vo.BusPassengerStatisticsVo;
import com.wntime.fault.service.FaultInfoService;
import com.wntime.fault.vo.BusFaultInfoVo;
import com.wntime.fault.vo.FaultInfoVo;
import com.wntime.modules.monitor.vo.BusDeviceStatusResponseVo;
import com.wntime.modules.monitor.vo.BusLineInfoVo;
import com.wntime.service.common.service.*;
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
import java.util.concurrent.*;


@Api(value = "★公交详细信息",tags = {"★公交详情接口"})
@RequestMapping("bus/detail")
@RestController
public class BusDetailController extends AbstractController {

    @Resource
    private FaultInfoService faultInfoService;
    @Resource
    private EventService eventService;
    @Resource
    private BusInfoService busInfoService;
    @Resource
    private CompanyLineService companyLineService;
    @Resource
    private BusDriveLogService busDriveLogService;
    @Resource
    private BusDeviceService busDeviceService;
    @Resource
    private VideoTunnelService videoTunnelService;

    private final Map<Long,ScheduledFuture> env=new ConcurrentHashMap<>();


    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    @GetMapping(value = "base/info")
    @ApiOperation(value = "公交基础信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busId", value = "公交车id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = BusLineInfoVo.class)
    })
    public R baseInfo(@RequestParam long busId){
        BusLineInfoVo vo=new BusLineInfoVo();
        vo.setBusId(busId);
        BusBaseInfoVo busInfo = busInfoService.queryBusInfo(busId);
        vo.setPlateCode(busInfo.getPlateCode());
        vo.setVinCode(busInfo.getVinCode());
        BusLineVo busLineInfo = companyLineService.queryBusLine(busId);
        if(busLineInfo!=null){
            vo.setCompanyName(busLineInfo.getCompanyName());
            vo.setCompanyLineName(busLineInfo.getCompanyLineCode());
            vo.setServiceTime(busLineInfo.getServiceTime());

        }
        return R.ok().put("info",vo);
    }
    @GetMapping(value = "monitor/info")
    @ApiOperation(value = "公交行驶数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busId", value = "公交车id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = BusRealtimeMonitorVo.class)
    })
    public R monitorInfo(@RequestParam long busId){
        BusRealtimeMonitorVo monitorVo = busDriveLogService.queryByBusId(busId);
        return R.ok().put("monitor",monitorVo);
    }


    @ApiOperation(value = "故障分级统计")
    @GetMapping(value = "fault/level/statistics")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busId", value = "公交车id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = FaultInfoVo.class,responseContainer = "List")
    })
    public R faultLevelStatistics(@RequestParam long busId){
        Collection<FaultInfoVo> list = faultInfoService.queryBusFaultLevelStatistics(busId);
        return R.ok().put("list",list);
    }

    @GetMapping(value = "warn")
    @ApiOperation(value = "当日AI识别告警")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busId", value = "公交车id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = BusAlarmInfoVo.class,responseContainer = "List")
    })
    public R warn(@RequestParam long busId){
        Collection<BusAlarmInfoVo> result = eventService.queryBusAlarmInfos(busId);
        return R.ok().put("alarms",result);
    }

    @GetMapping(value = "fault")
    @ApiOperation(value = "当日故障告警信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busId", value = "公交车id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = BusFaultInfoVo.class,responseContainer = "List")
    })
    public R fault(@RequestParam long busId){
        List<BusFaultInfoVo> list = faultInfoService.queryBusFaultInfos(busId);
        return R.ok().put("faults",list);
    }

    @GetMapping(value = "device/status")
    @ApiOperation(value = "设备状态监控")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busId", value = "公交车id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = BusDeviceStatusResponseVo.class,responseContainer = "Map")
    })
    public R deviceStatus(@RequestParam long busId){
        Map result = busDeviceService.queryBusDeviceStatus(busId);
        return R.ok(result);
    }


    @GetMapping(value = "passenger/statistics")
    @ApiOperation(value = "乘客统计")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "busId", value = "公交车id", required = true, dataType = "long")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "success",response = BusPassengerStatisticsVo.class)
    })
    public R passengerStatistics(@RequestParam long busId){
        BusPassengerStatisticsVo vo = eventService.queryPassengerFlowInfoAnalysis(busId);
        return R.ok().put("statistics",vo);
    }


    @ApiOperation(value = "公交视频查看",httpMethod = "GET")
    @GetMapping("/video/bus/open")
    public R open(@RequestParam("busId") long busId,@RequestParam @NotNull(message = "设备编号不能为空") String deviceCode){
        ScheduledFuture future = env.get(busId);
        if (future != null && future.getDelay(TimeUnit.MILLISECONDS)>0) {
           if( future.cancel(true)){
               env.remove(busId);
           }
        }
        TunnelVo tunnel = videoTunnelService.bind(busId, getUserId(),deviceCode);
        return R.ok().put("url",tunnel.getIp()).put("sport",tunnel.getSport());
    }


    @ApiOperation(value = "关闭公交视频查看",httpMethod = "GET")
    @GetMapping("/video/bus/close")
    public R close(@RequestParam("busId") long busId){

        ScheduledFuture future = env.get(busId);
        if (future == null || !future.isDone()) {
            //定时任务还存在 并且剩余时间大于零  取消定时任务
            if (future != null && future.getDelay(TimeUnit.MILLISECONDS)>0) {

                future.cancel(true);
            }
            env.put(busId, executorService.schedule(() ->
                            videoTunnelService.unbind(busId, getUserId())
                    //延迟1分钟
                    , 1, TimeUnit.MINUTES));
        }
        return R.ok();
    }
}
