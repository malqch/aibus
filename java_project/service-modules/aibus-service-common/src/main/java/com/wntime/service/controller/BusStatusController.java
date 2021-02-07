package com.wntime.service.controller;

import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.R;
import com.wntime.service.common.entity.LogBusDriveEntity;
import com.wntime.service.common.form.CarStatusInfoForm;
import com.wntime.service.common.service.BusDriveLogService;
import com.wntime.service.form.ReportBusStatusForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 79448
 * @date 2020/8/26 10:45
 */
@Api(value = "公交车状态controller",tags = "公交车状态接口")
@RequestMapping("/bus/status")
@RestController
public class BusStatusController {

    @Resource
    private BusDriveLogService busDriveLogService;

    @PostMapping("report")
    @ApiOperation(value = "上报公交车行驶数据",notes = "上报公交车当前的数据，包括行驶里程、剩余行驶里程、电池温度、电机状态、总电压、总电流、时速、电量等数据")
    public R report(@RequestBody @Validated ReportBusStatusForm reportBusStatusForm){
        LogBusDriveEntity logBusDriveEntity=new LogBusDriveEntity();
        BeanUtils.copyProperties(reportBusStatusForm,logBusDriveEntity);

        logBusDriveEntity.setBusLatitude(reportBusStatusForm.getFactoryLatitude());
        logBusDriveEntity.setBusLongitude(reportBusStatusForm.getFactoryLongitude());
        logBusDriveEntity.setBusId(reportBusStatusForm.getBusId());
        logBusDriveEntity.setIsEnabled(Constant.Enabled.ENABLE.getValue());
        logBusDriveEntity.setCreatedDate(DateUtils.getTimestamp());
        logBusDriveEntity.setBusStatus((long)reportBusStatusForm.getBusStatus());
        Integer motorStatus=reportBusStatusForm.getBusMotorStatus();
        if(motorStatus==3){
            motorStatus=0;
        }else {
            motorStatus=1;
        }
        logBusDriveEntity.setBusMotorStatus((long)motorStatus);
        Integer batteryStatus=reportBusStatusForm.getBusBatteryStatus();
        if(batteryStatus==3){
            batteryStatus=0;
        }else {
            batteryStatus=1;
        }
        logBusDriveEntity.setBusBatteryStatus((long)batteryStatus);
        busDriveLogService.insertBusDriveLog(logBusDriveEntity);
        return R.ok();
    }
}
