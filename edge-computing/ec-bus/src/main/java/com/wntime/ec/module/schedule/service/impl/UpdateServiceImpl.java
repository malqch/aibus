package com.wntime.ec.module.schedule.service.impl;

import cn.hutool.http.HttpUtil;
import com.wntime.ec.common.model.Constant;
import com.wntime.ec.common.model.FsmsRsp;
import com.wntime.ec.common.util.*;
import com.wntime.ec.common.util.exception.BusinessException;
import com.wntime.ec.common.util.exception.SystemException;
import com.wntime.ec.common.util.param.http.HttpRspParam;
import com.wntime.ec.module.schedule.service.IUpdateService;
import com.wntime.ec.module.schedule.vo.*;
import com.wntime.ec.module.sys.entity.*;
import com.wntime.ec.module.sys.service.*;
import com.wntime.ec.module.sys.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

/**
 * @author wing
 * @create 2020-08-28 15:08
 */
@Service
@Slf4j
public class UpdateServiceImpl implements IUpdateService {

    //是否有更新
    @Value("${remote.server.update_control_url}")
    private String update_control_url;

    //基础数据更新接口
    @Value("${remote.server.update_data_url}")
    private String update_data_url;

    //人员信息更新接口
    @Value("${remote.server.update_personne_url}")
    private String update_personne_url;

    @Value("${remote.server.get_businfo_url}")
    private String get_businfo_url;

    @Value("${remote.server.device_upload_url}")
    private String device_upload_url;

    @Value("${remote.server.bus_status_upload_url}")
    private String bus_status_upload_url;

    @Value("${remote.server.advertise_url}")
    private String advertise_url;

    //ssh端口获取接口
    @Value("${remote.server.ssh_url}")
    private String ssh_url;

    @Value("${remote.alleyes.vin_url}")
    private String vin_url;

    @Value("${remote.alleyes.device_url}")
    private String device_url;

    @Value("${remote.alleyes.car_url}")
    private String car_url;

    @Value("${remote.alleyes.feat_add_url}")
    private String feat_add_url;

    @Value("${remote.alleyes.feat_del_url}")
    private String feat_del_url;

    @Value("${local.shell.sshScript}")
    private String localShellSshScript;

    @Value("${local.cache.advertise}")
    private String advertiseDir;

    @Value("${bus.vin}")
    private String vin;

    @Autowired
    ILocalInfoBusService localInfoBusService;
    @Autowired
    IInfoBusDeviceService infoBusDeviceService;
    @Autowired
    IInfoUpdateListService infoUpdateListService;
    @Autowired
    IInfoDeviceTypeService infoDeviceTypeService;
    @Autowired
    IInfoCollectEventService infoCollectEventService;
    @Autowired
    IInfoEventExtendService infoEventExtendService;
    @Autowired
    IInfoEventTypeService infoEventTypeService;
    @Autowired
    IInfoEventLevelService infoEventLevelService;
    @Autowired
    IInfoEventTargetService infoEventTargetService;
    @Autowired
    IInfoCollectFaultService infoCollectFaultService;
    @Autowired
    IInfoFaultExtendService infoFaultExtendService;
    @Autowired
    IInfoFaultTypeService infoFaultTypeService;
    @Autowired
    IInfoFaultLevelService infoFaultLevelService;
    @Autowired
    IInfoFaultTargetService infoFaultTargetService;
    @Autowired
    IInfoBusStationService infoBusStationService;
    @Autowired
    IInfoLineStationService infoLineStationService;
    @Autowired
    IPlanBusServiceService planBusServiceService;
    @Autowired
    IInfoAdvertisePositionService infoAdvertisePositionService;
    @Autowired
    IInfoAdvertiseTargetService infoAdvertiseTargetService;
    @Autowired
    IOrderDeliveryAreaService orderDeliveryAreaService;
    @Autowired
    IOrderDeliveryTargetService orderDeliveryTargetService;
    @Autowired
    IOrderAdvertiseAttachService orderAdvertiseAttachService;
    @Autowired
    IOrderAdvertiseDeliveryService orderAdvertiseDeliveryService;

    /**
     * 上一个速度坐标位置
     */
    private static String preSpeedXY;

    @Override
    public void update() throws Exception {
        if (Constant.isEntered) {
            Long busDeviceId = null;
            InfoBusDeviceQryRspVo infoBusDeviceQryRspVo = infoBusDeviceService.selectById(Constant.DeviceDesc.m_i_native_ec.getCode());
            if (CommonUtil.isNotEmpty(infoBusDeviceQryRspVo)) {
                busDeviceId = infoBusDeviceQryRspVo.getBusDeviceId();
            }

            Map updateParam = new HashMap();
            updateParam.put("busId", Constant.localInfoBus.getBusId());
            updateParam.put("deviceId", busDeviceId);
            FsmsRsp<InterfaceUpdateQryRspWrapVo> resp = RemoteServerUtil.form(update_control_url, updateParam, new FsmsRsp<InterfaceUpdateQryRspWrapVo>() {
            }.getClass());
            int code = resp.getCode();
            if (code == 0) {
                List<InfoUpdateList> data = resp.getData().getList();
                for (InfoUpdateList rspVo : data) {
                    try {
                        //对比本地数据库，看是否需要更新
                        String updateType = rspVo.getUpdateType();
                        Date serverPublishDate = rspVo.getPublishDate();

                        InfoUpdateListQryReqVo param = new InfoUpdateListQryReqVo();
                        param.setUpdateType(updateType);
                        List<InfoUpdateListQryRspVo> infoUpdateListQryRespVos = infoUpdateListService.selectList(param);
                        InfoUpdateListQryRspVo local = infoUpdateListQryRespVos.get(0);
                        Date localPublishDate = local.getPublishDate();

                        //如果服务端时间比本地时间大，就更新
                        if (serverPublishDate.after(localPublishDate)) {
                            try {
                                if (Constant.UpdateType.BASE_DATA.getCode().equals(updateType)) {
                                    //基础数据更新
                                    updateBaseData();
                                }else if(Constant.UpdateType.PERSONNEL_DATA.getCode().equals(updateType)){
                                    updatePersonneData(localPublishDate);
                                }
                                //更新本地数据库
                                local.setPublishDate(serverPublishDate);
                                local.setIsPublished(rspVo.getIsPublished());
                                local.setUpdateUrl(rspVo.getUpdateUrl());
                                local.setModifiedDate(new Date());
                                infoUpdateListService.updateById(local);
                            } catch (SystemException e) {
                                e.printStackTrace();
                                throw new BusinessException("update BASE_DATA error," + e.getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.warn(Constant.ErrorCode.UPDATE_ERROR.getInfo(rspVo.toString() + e.getMessage()));
                    }
                }

                //获取线路，车站列表到 缓存
                List<PlanBusServiceQryRspVo> companyLineIds = getCompanyLineIds(Constant.localInfoBus.getBusId());
                for (PlanBusServiceQryRspVo planBusServiceQryRspVo : companyLineIds) {
                    Long companyLineId = planBusServiceQryRspVo.getCompanyLineId();
                    //查询线路站点列表到缓存

                    InfoLineStationQryReqVo param = new InfoLineStationQryReqVo();
                    param.setCompanyLineId(companyLineId);
                    List<InfoLineStationQryRspVo> infoLineStationQryRspVos = infoLineStationService.selectLineStation(param);

                    Constant.lineStationMap.put(companyLineId, infoLineStationQryRspVos);
                }
            } else {
                log.warn(Constant.ErrorCode.UPDATE_ERROR.getInfo("call remote server fsms getUpdateList error," + update_control_url));
            }
        }
    }

    void updateBaseData() throws Exception {
        Map updateParam = new HashMap();
        updateParam.put("busId", Constant.localInfoBus.getBusId());
        FsmsRsp<BaseDataUpdateQryRspVo> rsp = RemoteServerUtil.form(update_data_url, updateParam, new FsmsRsp<BaseDataUpdateQryRspVo>() {
        }.getClass());
        BaseDataUpdateQryRspVo data = rsp.getData();

        try {
            List<InfoDeviceType> infos = data.getInfoDeviceType();
            updateInfoDeviceType(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_device_type" + e.getMessage()));
        }

        try {
            List<InfoCollectEvent> infos = data.getInfoCollectEvent();
            updateInfoCollectEvent(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_collect_event" + e.getMessage()));
        }

        try {
            List<InfoEventExtend> infos = data.getInfoEventExtend();
            updateInfoEventExtend(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_event_extend" + e.getMessage()));
        }

        try {
            List<InfoEventType> infos = data.getInfoEventType();
            updateInfoEventType(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_event_type" + e.getMessage()));
        }

        try {
            List<InfoEventLevel> infos = data.getInfoEventLevel();
            updateInfoEventLevel(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_event_level" + e.getMessage()));
        }
        try {
            List<InfoEventTarget> infos = data.getInfoEventTarget();
            updateInfoEventTarget(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_event_target" + e.getMessage()));
        }

        try {
            List<InfoCollectFault> infos = data.getInfoCollectFault();
            updateInfoCollectFault(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_collect_fault" + e.getMessage()));
        }

        try {
            List<InfoFaultExtend> infos = data.getInfoFaultExtend();
            updateInfoFaultExtend(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_fault_extend" + e.getMessage()));
        }

        try {
            List<InfoFaultType> infos = data.getInfoFaultType();
            updateInfoFaultType(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_fault_type" + e.getMessage()));
        }
        try {
            List<InfoFaultLevel> infos = data.getInfoFaultLevel();
            updateInfoFaultLevel(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_fault_level" + e.getMessage()));
        }
        try {
            List<InfoFaultTarget> infos = data.getInfoFaultTarget();
            updateInfoFaultTarget(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_fault_target" + e.getMessage()));
        }
        try {
            List<InfoBusStation> infos = data.getInfoBusStation();
            updateInfoBusStation(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_bus_station" + e.getMessage()));
        }
        try {
            List<InfoLineStation> infos = data.getInfoLineStation();
            updateInfoLineStation(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_line_station" + e.getMessage()));
        }
        try {
            List<PlanBusService> infos = data.getPlanBusService();
            updatePlanBusService(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("plan_bus_service" + e.getMessage()));
        }
        try {
            List<InfoAdvertisePosition> infos = data.getInfoAdvertisePosition();
            updateInfoAdvertisePosition(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_advertise_position" + e.getMessage()));
        }
        try {
            List<InfoAdvertiseTarget> infos = data.getInfoAdvertiseTarget();
            updateInfoAdvertiseTarget(infos);
        } catch (Exception e) {
            log.warn(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo("info_advertise_target" + e.getMessage()));
        }
    }

    /**
     * 人员信息
     * @param date
     * @throws Exception
     */
    void updatePersonneData(Date date) throws Exception {
        Map updateParam = new HashMap();
        updateParam.put("date", DateUtil.date2String(date,"yyyy-MM-dd HH:mm:ss"));
        updateParam.put("busId", Constant.localInfoBus.getBusId());
        FsmsRsp<BaseDataUpdateQryRspVo> rsp = RemoteServerUtil.form(update_personne_url, updateParam, new FsmsRsp<BaseDataUpdateQryRspVo>() {
        }.getClass());
        BaseDataUpdateQryRspVo data = rsp.getData();
        List<InfoPersonnel> personnelList = data.getInfoPersonnelList();
        for(InfoPersonnel personnel : personnelList){
            InfoPersonnelVo vo = new InfoPersonnelVo();
            HttpRspParam featRsp = null;
            if("1".equals(personnel.getIsDeleted())){
                //删除
                vo.setUnique_identity(personnel.getUniqueIdentity().toString());
                featRsp = RemoteAlleyesUtil.post(feat_del_url,vo, new HttpRspParam() {}.getClass());
            }else{
                //新增
                vo.setImg(personnel.getImg());
                vo.setId_no(personnel.getIdNo());
                vo.setName(personnel.getName());
                vo.setUnique_identity(personnel.getUniqueIdentity().toString());
                vo.setSublib_type(personnel.getSublibType().intValue());
                featRsp = RemoteAlleyesUtil.post(feat_add_url,vo, new HttpRspParam() {}.getClass());
            }

            if (featRsp.getCode() == 0) {
                log.info("人员信息下发成功, personnel info : " + personnel.toString());
            }else{
                log.error("人员信息下发失败, personnel info : " + personnel.toString() + " Rsp : " + featRsp.toString());
            }
        }
    }

    private void updateInfoFaultTarget(List<InfoFaultTarget> infos) {
        //先删除
        infoFaultTargetService.delete(new InfoFaultTargetQryReqVo());

        //再插入
        for (InfoFaultTarget info : infos) {
            infoFaultTargetService.insert(info);
        }
    }

    private void updateInfoFaultLevel(List<InfoFaultLevel> infos) {
        //先删除
        infoFaultLevelService.delete(new InfoFaultLevelQryReqVo());

        //再插入
        for (InfoFaultLevel info : infos) {
            infoFaultLevelService.insert(info);
        }
    }

    private void updateInfoFaultType(List<InfoFaultType> infos) {
        //先删除
        infoFaultTypeService.delete(new InfoFaultTypeQryReqVo());

        //再插入
        for (InfoFaultType info : infos) {
            infoFaultTypeService.insert(info);
        }
    }

    private void updateInfoFaultExtend(List<InfoFaultExtend> infos) {
        //先删除
        infoFaultExtendService.delete(new InfoFaultExtendQryReqVo());

        //再插入
        for (InfoFaultExtend info : infos) {
            infoFaultExtendService.insert(info);
        }
    }

    private void updateInfoCollectFault(List<InfoCollectFault> infos) {
        //先删除
        infoCollectFaultService.delete(new InfoCollectFaultQryReqVo());

        //再插入
        for (InfoCollectFault info : infos) {
            infoCollectFaultService.insert(info);
        }
    }

    private void updateInfoEventTarget(List<InfoEventTarget> infos) {
        //先删除
        infoEventTargetService.delete(new InfoEventTargetQryReqVo());

        //再插入
        for (InfoEventTarget info : infos) {
            infoEventTargetService.insert(info);
        }
    }

    private void updateInfoEventLevel(List<InfoEventLevel> infos) {
        //先删除
        infoEventLevelService.delete(new InfoEventLevelQryReqVo());

        //再插入
        for (InfoEventLevel info : infos) {
            infoEventLevelService.insert(info);
        }
    }

    private void updateInfoEventType(List<InfoEventType> infos) {
        //先删除
        infoEventTypeService.delete(new InfoEventTypeQryReqVo());

        //再插入
        for (InfoEventType info : infos) {
            infoEventTypeService.insert(info);
        }
    }

    private void updateInfoEventExtend(List<InfoEventExtend> infos) {
        //先删除
        infoEventExtendService.delete(new InfoEventExtendQryReqVo());

        //再插入
        for (InfoEventExtend info : infos) {
            infoEventExtendService.insert(info);
        }
    }

    private void updateInfoCollectEvent(List<InfoCollectEvent> infos) {
        //先删除
        infoCollectEventService.delete(new InfoCollectEventQryReqVo());

        //再插入
        for (InfoCollectEvent info : infos) {
            infoCollectEventService.insert(info);
        }
    }

    private void updateInfoDeviceType(List<InfoDeviceType> infos) {
        //先删除
        infoDeviceTypeService.delete(new InfoDeviceTypeQryReqVo());

        //再插入
        for (InfoDeviceType info : infos) {
            infoDeviceTypeService.insert(info);
        }
    }

    private void updateInfoBusStation(List<InfoBusStation> infos) {
        //先删除
        infoBusStationService.delete(new InfoBusStationQryReqVo());

        //再插入
        for (InfoBusStation info : infos) {
            infoBusStationService.insert(info);
        }
    }

    private void updateInfoLineStation(List<InfoLineStation> infos) {
        //先删除
        infoLineStationService.delete(new InfoLineStationQryReqVo());

        //再插入
        for (InfoLineStation info : infos) {
            infoLineStationService.insert(info);
        }
    }

    private void updatePlanBusService(List<PlanBusService> infos) {
        //先删除
        planBusServiceService.delete(new PlanBusServiceQryReqVo());

        //再插入
        for (PlanBusService info : infos) {
            planBusServiceService.insert(info);
        }
    }

    private void updateInfoAdvertisePosition(List<InfoAdvertisePosition> infos) {
        //先删除
        infoAdvertisePositionService.delete(new InfoAdvertisePositionQryReqVo());

        //再插入
        for (InfoAdvertisePosition info : infos) {
            infoAdvertisePositionService.insert(info);
        }
    }

    private void updateInfoAdvertiseTarget(List<InfoAdvertiseTarget> infos) {
        //先删除
        infoAdvertiseTargetService.delete(new InfoAdvertiseTargetQryReqVo());

        //再插入
        for (InfoAdvertiseTarget info : infos) {
            infoAdvertiseTargetService.insert(info);
        }
    }

    private void updateOrderDeliveryArea(List<OrderDeliveryArea> infos, Long companyLineId) {
        //先删除
        OrderDeliveryAreaQryReqVo param = new OrderDeliveryAreaQryReqVo();
        param.setCompanyLineId(companyLineId);
        orderDeliveryAreaService.delete(param);

        //再插入
        for (OrderDeliveryArea info : infos) {
            orderDeliveryAreaService.insert(info);
        }
    }

    private void updateOrderDeliveryTarget(List<OrderDeliveryTarget> infos, Long companyLineId) {
        //先删除
        OrderDeliveryTargetQryReqVo param = new OrderDeliveryTargetQryReqVo();
        param.setCompanyLineId(companyLineId);
        orderDeliveryTargetService.delete(param);

        //再插入
        for (OrderDeliveryTarget info : infos) {
            info.setCompanyLineId(companyLineId);
            orderDeliveryTargetService.insert(info);
        }
    }

    private void updateOrderAdvertiseAttach(List<OrderAdvertiseAttach> infos, Long companyLineId) {
        //先删除
        OrderAdvertiseAttachQryReqVo param = new OrderAdvertiseAttachQryReqVo();
        param.setCompanyLineId(companyLineId);
        orderAdvertiseAttachService.delete(param);

        //再插入
        for (OrderAdvertiseAttach info : infos) {
            String attachLink = info.getAttachLink();
            String suffix = attachLink.substring(attachLink.lastIndexOf(".")).toLowerCase();
            info.setSuffix(suffix);
            info.setCompanyLineId(companyLineId);
            orderAdvertiseAttachService.insert(info);
        }
    }

    @Override
    public void updateBusInfo() {
        if (!Constant.isEntered) {
            //调用百目获取vin
//            HttpRspParam<AlleyesVinQryRspVo> rsp = RemoteAlleyesUtil.get(vin_url, new HttpRspParam<AlleyesVinQryRspVo>() {
//            }.getClass());

//            if (rsp.getCode() == 0) {
//                String vin = rsp.getData().getVin();

                //调用服务端获取车辆信息
                BusInfoQryReqVo busInfoQryReqVo = new BusInfoQryReqVo();
                busInfoQryReqVo.setVin(vin);
                FsmsRsp<BusInfoQryRspVo> busRsp = RemoteServerUtil.post(get_businfo_url, busInfoQryReqVo, new FsmsRsp<BusInfoQryRspVo>() {
                }.getClass());

                if (busRsp.getCode() == 0) {
                    LocalInfoBus busRspData = busRsp.getData().getBusInfo();
                    busRspData.setLocalId(Constant.Bus.ONE.getLocalId());

                    localInfoBusService.updateById(busRspData);

                    Long busId = busRspData.getBusId();

                    if (CommonUtil.isNotEmpty(busId) && CommonUtil.isNotEmpty(vin)) {
                        Constant.isEntered = true;
                        Constant.localInfoBus = busRspData;
                    }
                }
//            }
        }
    }

    @Override
    public void checkCarStatus() {
        if (Constant.isEntered) {
            //调用百目获取车辆状态信息
            HttpRspParam<AlleyesCarStatusQryRspVo> rsp = RemoteAlleyesUtil.get(car_url, new HttpRspParam<AlleyesCarStatusQryRspVo>() {
            }.getClass());

            if (rsp.getCode() == 0) {
                AlleyesCarStatusQryRspVo data = rsp.getData();

                CarStatusInfo carStatusInfo = new CarStatusInfo();
                carStatusInfo.setBusId(Constant.localInfoBus.getBusId());
                String bus_battery_current = data.getBus_battery_current();
                String busBatteryCurrent = "";
                if (CommonUtil.isNotEmpty(bus_battery_current)) {
                    String[] s = bus_battery_current.split("_");
                    busBatteryCurrent = s[0];
                }
                carStatusInfo.setBusBatteryCurrent(busBatteryCurrent);

                String bus_battery_energy = data.getBus_battery_energy();
                String busBatteryEnergy = "";
                if (CommonUtil.isNotEmpty(bus_battery_energy)) {
                    String[] s = bus_battery_energy.split("_");
                    busBatteryEnergy = s[0];
                }
                carStatusInfo.setBusBatteryEnergy(busBatteryEnergy);

                String bus_battery_status = data.getBus_battery_status();
                String busBatteryStatus = "";
                if (CommonUtil.isNotEmpty(bus_battery_status)) {
                    String[] s = bus_battery_status.split("_");
                    busBatteryStatus = s[0];
                }
                carStatusInfo.setBusBatteryStatus(busBatteryStatus);


                String bus_battery_temperature = data.getBus_battery_temperature();
                String busBatteryTemperature = "";
                if (CommonUtil.isNotEmpty(bus_battery_temperature)) {
                    String[] s = bus_battery_temperature.split("_");
                    busBatteryTemperature = s[0];
                }
                carStatusInfo.setBusBatteryTemperature(busBatteryTemperature);


                String bus_battery_voltage = data.getBus_battery_voltage();
                String busBatteryVoltage = "";
                if (CommonUtil.isNotEmpty(bus_battery_voltage)) {
                    String[] s = bus_battery_voltage.split("_");
                    busBatteryVoltage = s[0];
                }
                carStatusInfo.setBusBatteryVoltage(busBatteryVoltage);

                String bus_motor_status = data.getBus_motor_status();
                String busMotorStatus = "";
                if (CommonUtil.isNotEmpty(bus_motor_status)) {
                    String[] s = bus_motor_status.split("_");
                    busMotorStatus = s[0];
                }
                carStatusInfo.setBusMotorStatus(busMotorStatus);

                String speed = data.getSpeed();
                String speedValue = "";
                if (CommonUtil.isNotEmpty(speed)) {
                    String[] s = speed.split("_");
                    speedValue = s[0];
                }
                carStatusInfo.setBusSpeed(speedValue);

                String bus_status = data.getBus_status();
                String busStatus = "1";
//                if (CommonUtil.isNotEmpty(bus_status)) {
//                    String[] s = bus_status.split("_");
//                    busStatus = s[0];
//                }
                carStatusInfo.setBusStatus(busStatus);

                String total_mile = data.getTotal_mile();
                String totalMile = "";
                if (CommonUtil.isNotEmpty(total_mile)) {
                    String[] s = total_mile.split("_");
                    totalMile = s[0];
                }
                carStatusInfo.setBusTotalMile(totalMile);

                String surplus_mile = data.getSurplus_mile();
                String surplusMile = "";
                if (CommonUtil.isNotEmpty(surplus_mile)) {
                    String[] s = surplus_mile.split("_");
                    surplusMile = s[0];
                }
                carStatusInfo.setSurplusMile(surplusMile);

                carStatusInfo.setFactoryLatitude(data.getLatitude());
                carStatusInfo.setFactoryLongitude(data.getLongitude());
                Constant.CurrentCarStatusInfo = carStatusInfo;

                //如果电压不为0
                if (!Constant.CurrentCarStatusInfo.getBusBatteryVoltage().equals("0")) {
                    //上传服务端
                    FsmsRsp busRsp = RemoteServerUtil.post(bus_status_upload_url, carStatusInfo, new FsmsRsp() {
                    }.getClass());

                    if (busRsp.getCode() != 0) {
                        log.error(Constant.ErrorCode.UPLOAD_CAR_STATUS_ERROR.getInfo(rsp.getMsg()));
                    }
                }
            } else {
                log.error(Constant.ErrorCode.GET_CAR_STATUS_ERROR.getInfo(rsp.getMsg()));
            }
        }
    }

    @Override
    public void uploadDevice() {
        if (Constant.isEntered) {
            //调用百目获取车辆状态信息
            HttpRspParam<AlleyesDeviceQryRspWrapVo> rsp = RemoteAlleyesUtil.get(device_url, new HttpRspParam<AlleyesDeviceQryRspWrapVo>() {
            }.getClass());

            /*************          测试代码开始        ***************/
//            HttpRspParam<AlleyesDeviceQryRspWrapVo> rsp = new HttpRspParam();
//            rsp.setCode(0);
//            AlleyesDeviceQryRspWrapVo v = new AlleyesDeviceQryRspWrapVo();
//            List<AlleyesDeviceQryRspVo> l = new ArrayList<AlleyesDeviceQryRspVo>();
//            AlleyesDeviceQryRspVo e1 = new AlleyesDeviceQryRspVo(Constant.DeviceDesc.f_i_native_video.getCode(),Constant.DeviceDesc.f_o_native_video.getCode(),1);
//            AlleyesDeviceQryRspVo e2 = new AlleyesDeviceQryRspVo(Constant.DeviceDesc.b_i_native_video.getCode(),Constant.DeviceDesc.f_i_native_video.getCode(),1);
//            AlleyesDeviceQryRspVo e4 = new AlleyesDeviceQryRspVo(Constant.DeviceDesc.f_g_temperature_video.getCode(),Constant.DeviceDesc.f_g_native_video.getCode(),1);
//            AlleyesDeviceQryRspVo e5 = new AlleyesDeviceQryRspVo(Constant.DeviceDesc.f_g_native_video.getCode(),Constant.DeviceDesc.f_g_temperature_video.getCode(),1);
////            AlleyesDeviceQryRspVo e6 = new AlleyesDeviceQryRspVo(Constant.DeviceDesc.f_d_dsm_driver.getCode(),Constant.DeviceDesc.f_d_dsm_driver.getCode(),1);
////            AlleyesDeviceQryRspVo e7 = new AlleyesDeviceQryRspVo(Constant.DeviceDesc.m_i_native_5g.getCode(),Constant.DeviceDesc.m_i_native_5g.getCode(),1);
////            AlleyesDeviceQryRspVo e8 = new AlleyesDeviceQryRspVo(Constant.DeviceDesc.m_i_native_4g.getCode(),Constant.DeviceDesc.m_i_native_4g.getCode(),1);
//            l.add(e1);l.add(e2);l.add(e4);l.add(e5);
//            v.setList(l);
//            rsp.setData(v);
            /***********          测试代码结束        ***************/

            if (rsp.getCode() == 0) {
                //查询设备类型
                List<InfoDeviceTypeQryRspVo> deviceTypeQryRspVos = infoDeviceTypeService.selectList(new InfoDeviceTypeQryReqVo());
                Map<String, Map> typeMap = transListToMap(deviceTypeQryRspVos);

                List<AlleyesDeviceQryRspVo> list = rsp.getData().getList();
                list.add(new AlleyesDeviceQryRspVo(Constant.DeviceDesc.f_d_dsm_driver.getCode(),Constant.DeviceDesc.f_d_dsm_driver.getCode(),1));
                if (CommonUtil.isNotEmpty(list)) {
                    List unDelList = new ArrayList();
                    unDelList.add(Constant.DeviceDesc.m_i_native_ec.getCode());     //虚拟边缘计算设备
                    for (AlleyesDeviceQryRspVo deviceRspVo : list) {
                        String device_code = deviceRspVo.getDevice_code();
                        String device_desc_code = deviceRspVo.getDevice_desc_code();
                        Integer device_status = deviceRspVo.getDevice_status();
                        String deviceName = Constant.DeviceDesc.getNameByCode(device_desc_code);

                        unDelList.add(device_code);

                        String[] descCodeAry = device_desc_code.split("_");
                        String typeCode = descCodeAry[descCodeAry.length - 1];

                        //设备类型
                        Map deviceTypeMap = typeMap.get(typeCode);
                        Long typeId = (Long) deviceTypeMap.get("deviceTypeId");
//                    String typeName = (String) deviceTypeMap.get("deviceTypeName");


                        InfoBusDeviceQryRspVo infoBusDeviceQryRspVo = infoBusDeviceService.selectById(device_code);
                        //更新
                        if (CommonUtil.isNotEmpty(infoBusDeviceQryRspVo)) {
                            infoBusDeviceQryRspVo.setBusId(Constant.localInfoBus.getBusId());
                            infoBusDeviceQryRspVo.setDeviceDescCode(device_desc_code);
                            infoBusDeviceQryRspVo.setDeviceStatus(device_status);
                            infoBusDeviceQryRspVo.setDeviceTypeId(typeId);
                            infoBusDeviceQryRspVo.setDeviceName(deviceName);
                            infoBusDeviceService.updateById(infoBusDeviceQryRspVo);
                        }
                        //插入
                        else {
                            infoBusDeviceQryRspVo = new InfoBusDeviceQryRspVo();
                            infoBusDeviceQryRspVo.setDeviceCode(device_code);
                            infoBusDeviceQryRspVo.setBusId(Constant.localInfoBus.getBusId());
                            infoBusDeviceQryRspVo.setDeviceDescCode(device_desc_code);
                            infoBusDeviceQryRspVo.setDeviceStatus(device_status);
                            infoBusDeviceQryRspVo.setDeviceTypeId(typeId);
                            infoBusDeviceQryRspVo.setDeviceName(deviceName);
                            infoBusDeviceService.insert(infoBusDeviceQryRspVo);
                        }
                    }

                    //删除多余设备
                    infoBusDeviceService.deleteNeedlessByIds(unDelList);
                }

                //边缘计算
                String device_code = Constant.DeviceDesc.m_i_native_ec.getCode();
                String device_desc_code = Constant.DeviceDesc.m_i_native_ec.getCode();
                Integer device_status = 1;
                String deviceName = Constant.DeviceDesc.m_i_native_ec.getName();

                String[] descCodeAry = device_desc_code.split("_");
                String typeCode = descCodeAry[descCodeAry.length - 1];

                //设备类型
                Map deviceTypeMap = typeMap.get(typeCode);
                Long typeId = (Long) deviceTypeMap.get("deviceTypeId");
//                    String typeName = (String) deviceTypeMap.get("deviceTypeName");


                InfoBusDeviceQryRspVo infoBusDeviceQryRspVo = infoBusDeviceService.selectById(device_code);
                //更新
                if (CommonUtil.isNotEmpty(infoBusDeviceQryRspVo)) {
                    infoBusDeviceQryRspVo.setBusId(Constant.localInfoBus.getBusId());
                    infoBusDeviceQryRspVo.setDeviceDescCode(device_desc_code);
                    infoBusDeviceQryRspVo.setDeviceStatus(device_status);
                    infoBusDeviceQryRspVo.setDeviceTypeId(typeId);
                    infoBusDeviceQryRspVo.setDeviceName(deviceName);
                    infoBusDeviceService.updateById(infoBusDeviceQryRspVo);
                }
                //插入
                else {
                    infoBusDeviceQryRspVo = new InfoBusDeviceQryRspVo();
                    infoBusDeviceQryRspVo.setDeviceCode(device_code);
                    infoBusDeviceQryRspVo.setBusId(Constant.localInfoBus.getBusId());
                    infoBusDeviceQryRspVo.setDeviceDescCode(device_desc_code);
                    infoBusDeviceQryRspVo.setDeviceStatus(device_status);
                    infoBusDeviceQryRspVo.setDeviceTypeId(typeId);
                    infoBusDeviceQryRspVo.setDeviceName(deviceName);
                    infoBusDeviceService.insert(infoBusDeviceQryRspVo);
                }

                //上传设备
                DeviceUploadReqVo deviceUploadReqVo = new DeviceUploadReqVo();
                deviceUploadReqVo.setBusId(Constant.localInfoBus.getBusId());
                List infoBusDeviceQryRspVos = infoBusDeviceService.selectList(new InfoBusDeviceQryReqVo());
                deviceUploadReqVo.setDevices(infoBusDeviceQryRspVos);

                FsmsRsp<DeviceUploadRspVo> busRsp = RemoteServerUtil.post(device_upload_url, deviceUploadReqVo, new FsmsRsp<DeviceUploadRspVo>() {
                }.getClass());

                if (busRsp.getCode() == 0) {
                    List<InfoBusDevice> devices = busRsp.getData().getDevices();
                    for (InfoBusDevice device : devices) {
                        infoBusDeviceService.updateById(device);
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void updateAdvertise() {
        if (Constant.isEntered) {
            //查询线路信息
            List<PlanBusServiceQryRspVo> companyLineIds = getCompanyLineIds(Constant.localInfoBus.getBusId());

            for (PlanBusServiceQryRspVo planBusServiceQryRspVo : companyLineIds) {
                Long companyLineId = planBusServiceQryRspVo.getCompanyLineId();

                FsmsRsp<AdvertiseUpdateQryRspVo> advertiseRsp = RemoteServerUtil.post(advertise_url + "/" + companyLineId, null, new FsmsRsp<AdvertiseUpdateQryRspVo>() {
                }.getClass());

                if (advertiseRsp.getCode() == 0) {
                    AdvertiseUpdateQryRspVo data = advertiseRsp.getData();
                    List<OrderDeliveryArea> orderDeliveryArea = data.getOrderDeliveryArea();
                    updateOrderDeliveryArea(orderDeliveryArea, companyLineId);

                    List<OrderDeliveryTarget> orderDeliveryTarget = data.getOrderDeliveryTarget();
                    updateOrderDeliveryTarget(orderDeliveryTarget, companyLineId);

                    List<OrderAdvertiseAttach> orderAdvertiseAttach = data.getOrderAdvertiseAttach();
                    updateOrderAdvertiseAttach(orderAdvertiseAttach, companyLineId);

                    //查询本地已保存广告
                    OrderAdvertiseDeliveryQryReqVo orderAdvertiseDeliveryQryReqVo = new OrderAdvertiseDeliveryQryReqVo();
                    orderAdvertiseDeliveryQryReqVo.setCompanyLineId(companyLineId);
                    List<Long> dbIds = orderAdvertiseDeliveryService.selectOrderAdvertiseDeliveryIds(orderAdvertiseDeliveryQryReqVo);
                    List<Long> newIds = new ArrayList<>();
                    List<OrderAdvertiseDelivery> orderAdvertiseDelivery = data.getOrderAdvertiseDelivery();
                    for (OrderAdvertiseDelivery advertiseDelivery : orderAdvertiseDelivery) {
                        Long advertiseDeliveryId = advertiseDelivery.getAdvertiseDeliveryId();
                        newIds.add(advertiseDeliveryId);

                        //新数据 下载附件
                        if (!dbIds.contains(advertiseDeliveryId)) {
                            File advertisePath = new File(advertiseDir + File.separator + advertiseDeliveryId);
                            if (!advertisePath.exists()) {
                                advertisePath.mkdirs();
                            }

                            OrderAdvertiseAttachQryReqVo attachQryReqVo = new OrderAdvertiseAttachQryReqVo();
                            attachQryReqVo.setAdvertiseDeliveryId(advertiseDeliveryId);
                            attachQryReqVo.setCompanyLineId(companyLineId);
                            List<OrderAdvertiseAttachQryRspVo> attachQryRspVos = orderAdvertiseAttachService.selectList(attachQryReqVo);

                            for (OrderAdvertiseAttachQryRspVo attachQryRspVo : attachQryRspVos) {
                                String attachLink = attachQryRspVo.getAttachLink();
                                String suffix = attachLink.substring(attachLink.lastIndexOf(".")).toLowerCase();
                                Long advertiseAttachId = attachQryRspVo.getAdvertiseAttachId();

                                File attachFile = new File(advertisePath, advertiseAttachId.toString() + suffix);
                                if(!attachFile.exists()){
                                    HttpUtil.downloadFile(attachLink, attachFile);
                                }
                            }

                            //保存数据库
                            advertiseDelivery.setCompanyLineId(companyLineId);
                            orderAdvertiseDeliveryService.insert(advertiseDelivery);
                        }
                    }
                    //已删除数据，删除附件
                    for (Long dbId : dbIds) {
                        if (!newIds.contains(dbId)) {
                            //删除数据库
                            OrderAdvertiseDeliveryQryReqVo delParam = new OrderAdvertiseDeliveryQryReqVo();
                            delParam.setAdvertiseDeliveryId(dbId);
                            delParam.setCompanyLineId(companyLineId);
                            orderAdvertiseDeliveryService.delete(delParam);

                            //删除文件
                            OrderAdvertiseDeliveryQryReqVo countParam = new OrderAdvertiseDeliveryQryReqVo();
                            countParam.setAdvertiseDeliveryId(dbId);
                            Number count = orderAdvertiseDeliveryService.selectCount(countParam);
                            if(count.intValue() <= 0){
                                File advertisePath = new File(advertiseDir + File.separator + dbId);
                                deleteDir(advertisePath);
                            }
                        }
                    }
                } else {
                    throw new BusinessException(advertiseRsp.getMessage());
                }
            }
        }
    }

    //根据车辆ID获取线路ID
    private List<PlanBusServiceQryRspVo> getCompanyLineIds(long busId) {
        try {
            PlanBusServiceQryReqVo param = new PlanBusServiceQryReqVo();
            param.setBusId(busId);
            Date now = new Date();
            param.setBeginDateEnd(now);
            param.setEndDateStart(now);
            param.setOrderBy(" order by plan_service_id asc ");
            List<PlanBusServiceQryRspVo> rspVos = planBusServiceService.selectList(param);
            if (CommonUtil.isEmpty(rspVos)) {
                throw new BusinessException("线路不存在");
            }

            return rspVos;
        } catch (Exception e) {
            throw new BusinessException("查询线路错误");
        }
    }

    private Map<String, Map> transListToMap(List<InfoDeviceTypeQryRspVo> infoDeviceTypeQryRespVos) {
        Map<String, Map> result = new HashMap<>();
        for (InfoDeviceTypeQryRspVo infoDeviceTypeQryRespVo : infoDeviceTypeQryRespVos) {
            String typeCode = infoDeviceTypeQryRespVo.getDeviceTypeCode();
            Long deviceTypeId = infoDeviceTypeQryRespVo.getDeviceTypeId();

            if (CommonUtil.isNotEmpty(typeCode) && CommonUtil.isNotEmpty(deviceTypeId)) {
                Map map = new HashMap();
                map.put("deviceTypeId", deviceTypeId);
                map.put("deviceTypeName", infoDeviceTypeQryRespVo.getDeviceTypeName());
                result.put(typeCode, map);
            }
        }
        return result;
    }

    /**
     * 定时获取ssh通道端口，并建立、关闭 连接
     */
    @Override
    public void checkSport() throws Exception {
        if (Constant.isEntered) {
            FsmsRsp<SshQryRspVo> resp = RemoteServerUtil.form(ssh_url + "/" + Constant.localInfoBus.getBusId(), new HashMap(), new FsmsRsp<SshQryRspVo>() {
            }.getClass());

            SshQryRspVo sshQryRspVo = resp.getData();
            int sport = sshQryRspVo.getSport();

            if (sport == 0) {
                Constant.sshSport = 0;
                //关闭ssh
                Process ps = Runtime.getRuntime().exec(localShellSshScript + " close x x x x x");
                ps.waitFor();
            } else {
                Constant.sshSport = sport;
                String ip = sshQryRspVo.getIp();
                int port = sshQryRspVo.getPort();
                String username = sshQryRspVo.getUsername();
                String passwrod = sshQryRspVo.getPassword();

                //打开ssh
                Process ps = Runtime.getRuntime().exec(localShellSshScript + " open " + ip + " " + port + " " + username + " " + passwrod + " " + sport);
                ps.waitFor();
            }
        }
    }

    public boolean deleteDir(File dir) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
            if(dir.delete()) {
                log.info(dir.getAbsolutePath() + "目录已被删除！");
                return true;
            } else {
                log.info(dir.getAbsolutePath() + "目录删除失败！");
                return false;
            }
        }
        //没有对应文件的时候，认为已经删除了
        return true;
    }
}
