package com.wntime.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.wntime.advert.service.AdvertiseService;
import com.wntime.common.utils.R;
import com.wntime.customer.service.ItineraryReceiptService;
import com.wntime.event.serive.EventBaseService;
import com.wntime.fault.service.FaultInfoService;
import com.wntime.modules.officer.service.PeopleBasicFactsService;
import com.wntime.service.common.entity.InfoBusEntity;
import com.wntime.service.common.entity.InfoUpdateListEntity;
import com.wntime.service.common.service.*;
import com.wntime.service.common.vo.TunnelVo;
import com.wntime.service.form.DeviceUploadForm;
import com.wntime.service.form.QueryBusInfoFrom;
//import com.wntime.advert.form.ReportAdvertiseLogForm;
import com.wntime.service.service.InfoBusDeviceService;
import com.wntime.service.service.InfoBusService;
import com.wntime.service.service.InfoBusTypeService;
import com.wntime.service.vo.UploadDeviceVo;
import com.wntime.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 79448
 * @date 2020/8/31 11:11
 */
@Api(value = "边缘计算controller",tags = "边缘计算接口")
@RestController
@RequestMapping("app")
public class AppUpdateController {

    @Resource
    private InfoUpdateListService infoUpdateListService;
    @Resource
    private EventBaseService eventBaseService;
    @Resource
    private FaultInfoService faultInfoService;
    @Resource
    private InfoBusTypeService infoBusTypeService;
    @Resource
    private InfoBusService infoBusService;
    @Resource
    private InfoBusDeviceService infoBusDeviceService;
    @Resource
    private VideoTunnelService videoTunnelService;
    @Resource
    private BusStationService busStationService;
    @Resource
    private CompanyLineService companyLineService;

    @Resource
    private BusServiceLogService busServiceLogService;

    @Resource
    private PeopleBasicFactsService peopleBasicFactsService;

    @PostMapping("getUpdateList")
    @ApiOperation("检测基础信息是否有更新")
    public R getUpdateList(@RequestParam(required = false,name = "deviceId")Long deviceId, @RequestParam(name = "busId") long busId){
        List<InfoUpdateListEntity> result = infoUpdateListService.list(
                new QueryWrapper<InfoUpdateListEntity>()
                        .eq("is_deleted", 0)
                        .eq("is_published", 1));
        return R.ok().put("list",result);
    }

    @PostMapping("getParam")
    @ApiOperation("基础信息更新，返回事件、故障相关表数据以及设备类型")
    public R getParam(@RequestParam(name = "busId") long busId){
        return R.ok().put("infoDeviceType",infoBusTypeService.queryDeviceType())
                .put("infoCollectEvent",eventBaseService.queryCollectEvent())
                .put("infoEventExtend",eventBaseService.queryEventExtend())
                .put("infoEventType",eventBaseService.queryEventType())
                .put("infoEventLevel",eventBaseService.queryEventLevel())
                .put("infoEventTarget",eventBaseService.queryEventTarget())
//                .put("infoCollectFault",faultInfoService.queryCollectFault())
//                .put("infoFaultExtend",faultInfoService.queryFaultExtend())
//                .put("infoFaultType",faultInfoService.queryFaultType())
//                .put("infoFaultLevel",faultInfoService.queryFaultLevel())
//                .put("infoFaultTarget",faultInfoService.queryFaultTarget())
                .put("infoBusStation",busStationService.queryBusStations())
                .put("infoLineStation",companyLineService.queryCompanyLines())
                .put("planBusService",companyLineService.queryBusLinePlanServices(busId));
//                .put("infoAdvertisePosition",advertiseService.queryPositionList())
//                .put("infoAdvertiseTarget",advertiseService.queryTargetList());
    }


    @PostMapping("getPersonnelList")
    @ApiOperation("基础信息更新，返回事件、故障相关表数据以及设备类型")
    public R getPersonnelList(@RequestParam(name = "busId") long busId,@RequestParam(name = "date") String date) throws ParseException {
        return R.ok().put("infoPersonnelList",peopleBasicFactsService.getAllPersonnelByTimestamp(busId,DateUtil.string2Date(date,DateUtil.yyyy_MM_dd_HH_mm_ss)));
    }

    @PostMapping("getBusInfo")
    @ApiOperation("获取公交车信息")
    public R getBusInfo(@RequestBody @Validated QueryBusInfoFrom queryBusInfoFrom){
        return R.ok().put("busInfo",infoBusService.queryBusByVin(queryBusInfoFrom.getVin()));
    }

    @PostMapping("device/upload")
    @ApiOperation(value = "更新设备信息",notes = "更新公交车上的设备，以上报的为主")
    public R uploadDevice(@RequestBody @Validated DeviceUploadForm deviceUploadForm){
        List<UploadDeviceVo> list = infoBusDeviceService.uploadDevice(deviceUploadForm);
        return R.ok().put("devices",list);
    }


    @ApiOperation("检测公交车是否打开通道")
    @PostMapping("video/tunnel/check/{busId}")
    @Cacheable(cacheNames = "cache_tunnel_config",key = "#busId")
    public R checkTunnel(@PathVariable("busId") long busId){
        TunnelVo tunnel = videoTunnelService.checkTunnel(busId);
        if(tunnel.getSport()==0) {
            tunnel.setPassword("");
            tunnel.setUsername("");
        }
        return R.ok().put("sport",tunnel.getSport())
                .put("ip",tunnel.getIp())
                .put("password",tunnel.getPassword())
                .put("port",tunnel.getPort())
                .put("username",tunnel.getUsername());

    }

//    @PostMapping("advertise/{companyLineId}")
//    @ApiOperation(value = "获取线路投放的广告",notes = "获取线路投放的广告")
//    public R advertise(@PathVariable("companyLineId")long companyLineId){
//        Map<String, Object> result = advertiseService.queryCompanyLineAdvertise(companyLineId);
//        return R.ok(result);
//    }

//    @PostMapping("report/advertise/log")
//    @ApiOperation(value = "上报广告日志",httpMethod = "POST",notes = "上报公交车上广告播放的日志")
//    public R reportAdvertiseLog(@RequestBody @Validated ReportAdvertiseLogForm reportAdvertiseLogForm){
//        advertiseService.checkAdvertiseDelivery(reportAdvertiseLogForm.getAdvertiseDeliveryId());
//        advertiseService.checkAdvertisePosition(reportAdvertiseLogForm.getAdvertisePositionId());
//        int count = infoBusService.count(new QueryWrapper<InfoBusEntity>().eq("bus_id", reportAdvertiseLogForm.getBusId()).eq("is_enabled", 1).eq("is_deleted", 0));
//        if(count==0){return R.error("公交车不存在");}
//        companyLineService.checkCompanyLine(reportAdvertiseLogForm.getCompanyLineId());
//        //新增一条广告投放日志
//        int rideCount = busServiceLogService.queryBusKeepRide(reportAdvertiseLogForm.getBusId(), reportAdvertiseLogForm.getCompanyLineId());
//        reportAdvertiseLogForm.setPeopleCount(rideCount);
//        //投放日志的覆盖人数  ->查询当前公交车的乘坐人数
//        advertiseService.insertAdvertiseLog(reportAdvertiseLogForm);
//        //当该公交车上车时 并且该广告未下架 ->增加覆盖人数
//        return R.ok();
//    }

    public static void main(String[] args) {
        System.out.println(DateUtil.date2String(new Date(),"yyyy-MM-dd HH:mm:ss"));
    }
}
