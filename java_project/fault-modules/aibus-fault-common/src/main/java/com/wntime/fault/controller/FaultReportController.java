package com.wntime.fault.controller;

import com.alibaba.fastjson.JSONObject;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.R;
import com.wntime.fault.entity.*;
import com.wntime.fault.form.FaultReportForm;
import com.wntime.fault.service.*;
import com.wntime.service.common.entity.LogBusDriveEntity;
import com.wntime.service.common.form.CarStatusInfoForm;
import com.wntime.service.common.service.BusDriveLogService;
import com.wntime.service.common.service.BusInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author 79448
 * @date 2020/8/26 13:58
 */
@Api(value = "故障上报controller",tags = "故障上报接口")
@RestController
@RequestMapping("/fault")
public class FaultReportController {

    @Resource
    private InfoFaultTypeService infoFaultTypeService;
    @Resource
    private InfoFaultLevelService infoFaultLevelService;
    @Resource
    private InfoCollectFaultService infoCollectFaultService;
    @Resource
    private InfoFaultTargetService infoFaultTargetService;
    @Resource
    private BusDriveLogService busDriveLogService;
    @Resource
    private LogFaultDetailService logFaultDetailService;

    @Resource
    private LogFaultAttachService logFaultAttachService;
    @Resource
    private BusInfoService busInfoService;

    @ApiOperation(value = "故障上报",notes = "上报公交车上的故障信息，来自于CAN总线采集到的数据")
    @PostMapping("/report")
    public R report(@RequestBody @Validated FaultReportForm faultReportForm){
        System.out.println("故障上报数据: "+JSONObject.toJSONString(faultReportForm));
        busInfoService.checkBusNormal(faultReportForm.getBusId());
        InfoCollectFaultEntity collectFault = infoCollectFaultService.getById(faultReportForm.getCollectEventId());
        if(collectFault==null){
            return R.error("故障采集不正确");
        }
        InfoFaultLevelEntity faultLevel = infoFaultLevelService.getById(faultReportForm.getEventLevelId());
        if(faultLevel==null){
            return R.error("故障级别不正确");
        }
        faultReportForm.setFaultLevelName(faultLevel.getFaultLevelName());
        faultReportForm.setFaultLevelCode(faultLevel.getFaultLevelCode());
        InfoFaultTargetEntity faultTarget = infoFaultTargetService.getById(faultReportForm.getEventTargetId());
        if(faultTarget==null){
            return R.error("故障标签不正确");
        }
        faultReportForm.setFaultTargetCode(faultTarget.getFaultTargetCode());
        InfoFaultTypeEntity faultType = infoFaultTypeService.getById(faultReportForm.getEventTypeId());
        if(faultType==null){
            return R.error("故障类型不正确");
        }
        faultReportForm.setFaultTypeName(faultType.getFaultTypeName());

        Timestamp eventDate=new Timestamp(faultReportForm.getEventDate());
        LogBusDriveEntity logBusDriveEntity=new LogBusDriveEntity();
        CarStatusInfoForm carInfo = faultReportForm.getCarStatusInfo();
        BeanUtils.copyProperties(carInfo,logBusDriveEntity);
        logBusDriveEntity.setBusLatitude(carInfo.getFactoryLatitude());
        logBusDriveEntity.setBusLongitude(carInfo.getFactoryLongitude());
        logBusDriveEntity.setBusId(faultReportForm.getBusId());
        logBusDriveEntity.setIsEnabled(Constant.Enabled.ENABLE.getValue());
        logBusDriveEntity.setCreatedDate(eventDate);
        logBusDriveEntity.setBusStatus(0L);

        Integer motorStatus=carInfo.getBusMotorStatus();
        if(motorStatus==3){
            motorStatus=0;
        }else {
            motorStatus=1;
        }
        logBusDriveEntity.setBusMotorStatus((long)motorStatus);
        Integer batteryStatus=carInfo.getBusBatteryStatus();
        if(batteryStatus==3){
            batteryStatus=0;
        }else {
            batteryStatus=1;
        }
        logBusDriveEntity.setBusBatteryStatus((long)batteryStatus);
        busDriveLogService.insertBusDriveLog(logBusDriveEntity);

        busDriveLogService.busDriveStatus(faultReportForm.getBusId(),0);

        LogFaultDetailEntity logEventDetailEntity=new LogFaultDetailEntity();
        logEventDetailEntity.setBusDriveId(logBusDriveEntity.getBusDriveId());
        logEventDetailEntity.setCollectFaultId(faultReportForm.getCollectEventId());
        logEventDetailEntity.setIsEnabled(Constant.Enabled.ENABLE.getValue());
        logEventDetailEntity.setBusId(faultReportForm.getBusId());
        logEventDetailEntity.setCreatedDate(eventDate);
        logEventDetailEntity.setFaultLevelId(faultReportForm.getEventLevelId());
        logEventDetailEntity.setFaultTargetId(faultReportForm.getEventTargetId());
        logEventDetailEntity.setFaultLevelId(faultReportForm.getEventLevelId());
        logEventDetailEntity.setFaultTypeId(faultReportForm.getEventTypeId());
        logFaultDetailService.saveFaultDetail(logEventDetailEntity);

        logFaultDetailService.saveBusFault(faultReportForm);

        logFaultAttachService.saveFaultAttach(faultReportForm.getBusId(),faultReportForm.getTags(),logEventDetailEntity.getFaultDetailId(),faultReportForm.getEventDate());
        return R.ok();
    }
}
