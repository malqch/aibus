package com.wntime.service.aspect;

import com.wntime.common.utils.Constant;
import com.wntime.service.entity.InfoBusDeviceEntity;
import com.wntime.service.service.InfoBusDeviceService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 79448
 * @date 2020/9/9 10:19
 */
@Aspect
@Component
public class InfoUpdateAspect {

    @Resource
    private InfoBusDeviceService infoBusDeviceService;

    @Pointcut(value = "execution(* com.wntime.service.controller.AppUpdateController.getUpdateList(..)) && args(deviceId,busId)", argNames = "deviceId,busId")
    public void pointCut(Long deviceId, long busId) {
    }

    /**
     * 拦截agent更新接口，修改agent请求时间,拦截到的请求都表示该agent为在线
     *
     * @param deviceId     设备id
     * @param busId 公交id
     */
    @Before(value = "pointCut(deviceId,busId)", argNames = "deviceId,busId")
    public void before(Long deviceId, long busId) {
        if(deviceId!=null){
            InfoBusDeviceEntity device = infoBusDeviceService.getById(deviceId);
            device.setDeviceStatus(1);
            infoBusDeviceService.updateDeviceStatus(device);
        }
    }
}
