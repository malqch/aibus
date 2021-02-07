package com.wntime.service.controller;

import com.wntime.common.utils.R;
import com.wntime.common.validator.Assert;
import com.wntime.service.common.constant.BusStatusConstant;
import com.wntime.service.common.constant.RunStatusConstant;
import com.wntime.service.service.InfoBusService;
import com.wntime.service.service.LogBusDriveService;
import com.wntime.service.vo.ProgressBarItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ysc
 * 2020/8/26 9:56
 */
@Api(value = "公交分析接口", tags = {"公交分析接口"})
@RequestMapping("/service/bus/stat")
@RestController
public class BusStatController {

    @Autowired
    private InfoBusService infoBusService;

    @ApiOperation(value = "公交状态统计分析", notes = "", httpMethod = "GET")
    @GetMapping("/status")
    public R busStatusStat(@RequestParam(value = "companyId", required = true) Long companyId){

        //获取运行时车辆状态 正常 告警
        //获取售后车辆状态 待修 维修中
        long busCount = infoBusService.getBusCountByCompanyId(companyId);
        int total = Math.toIntExact(busCount);
        if(busCount == 0){
            return R.error("没有数据");
        }
//        List<Map<String,Object>> runStatusCountList = infoBusService.countByRunStatusAndCompanyId(companyId);
//        List<Map<String,Object>> busStatusCountList =  infoBusService.countByBusStatusAndCompanyId(companyId);
//        Map<String, Map<String, Object>> runStatusCodeMap = runStatusCountList.stream().collect(Collectors.toMap(map -> (String)map.get("run_status_code"), map -> map));
//        Map<String, Map<String, Object>> busStatusCodeMap = busStatusCountList.stream().collect(Collectors.toMap(map -> (String) map.get("bus_status_code"), map -> map));
//
//        List<ProgressBarItem> progressBarItemList = new ArrayList<>();
//
//        Map<String, Object> normalRunStatusMap = runStatusCodeMap.get(RunStatusConstant.NORMAL);
//        Map<String, Object> faultRunStatusMap = runStatusCodeMap.get(RunStatusConstant.FAULT);
//        Map<String, Object> waitBusStatusMap = busStatusCodeMap.get(BusStatusConstant.WAIT);
//        Map<String, Object> maintenanceBusStatusMap = busStatusCodeMap.get(BusStatusConstant.MAINTENANCE);
//
//        normalRunStatusMap.put("run_status_name","正常运行车辆");
//        faultRunStatusMap.put("run_status_name","故障告警车辆");
//        waitBusStatusMap.put("bus_status_name","待修车辆");
//        maintenanceBusStatusMap.put("bus_status_name","维修中车辆");
//
//
//        progressBarItemList.add(runStatusMapToItem(normalRunStatusMap));
//        progressBarItemList.add(runStatusMapToItem(faultRunStatusMap));
//        progressBarItemList.add(busStatusMapToItem(waitBusStatusMap));
//        progressBarItemList.add(busStatusMapToItem(maintenanceBusStatusMap));
        // 查询故障数据
        List<ProgressBarItem> result = infoBusService.getBusStatus(companyId);
        for (ProgressBarItem progressBarItem : result) {
            progressBarItem.setTotal(total);
        }
        return  R.ok().put("list",result);
    }
    private ProgressBarItem runStatusMapToItem(Map<String,Object> runStatusMap){
        Long runStatusId = (Long) runStatusMap.get("run_status_id");
        int count = ((Long) runStatusMap.get("run_status_count")).intValue();
        String statusCode = (String) runStatusMap.get("run_status_code");
        String statusName = (String) runStatusMap.get("run_status_name");
        ProgressBarItem progressBarItem = new ProgressBarItem();
        progressBarItem.setId(runStatusId);
        progressBarItem.setName(statusName);
        progressBarItem.setNumber(count);
        return progressBarItem;
    }

    private ProgressBarItem busStatusMapToItem(Map<String,Object> busStatusMap){
        Long busStatusId = (Long) busStatusMap.get("bus_status_id");
        int count = ((Long) busStatusMap.get("bus_status_count")).intValue();
        String statusCode = (String) busStatusMap.get("bus_status_code");
        String statusName = (String) busStatusMap.get("bus_status_name");
        ProgressBarItem progressBarItem = new ProgressBarItem();
        progressBarItem.setId(busStatusId);
        progressBarItem.setName(statusName);
        progressBarItem.setNumber(count);
        return progressBarItem;
    }
}
