package com.wntime.service.controller;

import com.wntime.common.utils.R;
import com.wntime.controller.AbstractController;
import com.wntime.service.entity.InfoBusDeviceEntity;
import com.wntime.service.form.UpdateDeviceStatusForm;
import com.wntime.service.service.InfoBusDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "设备状态controller",tags = "设备状态接口")
@RequestMapping("/device/status")
@RestController
public class DeviceStatusController extends AbstractController {

    @Autowired
    private InfoBusDeviceService infoBusDeviceService;

    @PostMapping("/update")
    @ApiOperation(value = "更新设备状态")
    public R updateStatus(@RequestBody  @Validated UpdateDeviceStatusForm updateDeviceStatusForm){
        InfoBusDeviceEntity busDevice = infoBusDeviceService.getById(updateDeviceStatusForm.getBusDeviceId());
        if(busDevice==null){
            return R.error("设备信息不存在");
        }
        if(!updateDeviceStatusForm.getBusId().equals(busDevice.getBusId()) ){
            return R.error("公交车id不匹配");
        }
        if(!updateDeviceStatusForm.getDeviceTypeId().equals(busDevice.getDeviceTypeId())){
            return R.error("设备类型不匹配");
        }
        busDevice.setDeviceStatus(updateDeviceStatusForm.getStatus());
        infoBusDeviceService.updateDeviceStatus(busDevice);
        return  R.ok();
    }
}
