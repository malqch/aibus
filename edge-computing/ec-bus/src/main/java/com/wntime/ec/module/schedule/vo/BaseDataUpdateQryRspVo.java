package com.wntime.ec.module.schedule.vo;

import com.wntime.ec.module.sys.entity.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wing
 * @create 2019-12-16 15:18
 */
@Data
public class BaseDataUpdateQryRspVo {
    List<InfoDeviceType> infoDeviceType = new ArrayList();
    List<InfoCollectEvent> infoCollectEvent = new ArrayList();
    List<InfoEventExtend> infoEventExtend = new ArrayList();
    List<InfoEventType> infoEventType = new ArrayList();
    List<InfoEventLevel> infoEventLevel = new ArrayList();
    List<InfoEventTarget> infoEventTarget = new ArrayList();
    List<InfoCollectFault> infoCollectFault = new ArrayList();
    List<InfoFaultExtend> infoFaultExtend = new ArrayList();
    List<InfoFaultType> infoFaultType = new ArrayList();
    List<InfoFaultLevel> infoFaultLevel = new ArrayList();
    List<InfoFaultTarget> infoFaultTarget = new ArrayList();
    List<InfoBusStation> infoBusStation = new ArrayList<>();
    List<InfoLineStation> infoLineStation = new ArrayList<>();
    List<PlanBusService> planBusService = new ArrayList<>();
    List<InfoAdvertisePosition> infoAdvertisePosition = new ArrayList<>();
    List<InfoAdvertiseTarget> infoAdvertiseTarget = new ArrayList<>();
    List<InfoPersonnel> infoPersonnelList = new ArrayList<>();
}
