package com.wntime.ec.module.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.wntime.ec.common.model.Constant;
import com.wntime.ec.common.model.FsmsRsp;
import com.wntime.ec.common.util.CommonUtil;
import com.wntime.ec.common.util.DistanceUtil;
import com.wntime.ec.common.util.GpsUtil;
import com.wntime.ec.common.util.RemoteServerUtil;
import com.wntime.ec.common.util.exception.BusinessException;
import com.wntime.ec.module.gateway.controller.WebSocketSender;
import com.wntime.ec.module.gateway.service.IEcService;
import com.wntime.ec.module.gateway.vo.*;
import com.wntime.ec.module.sys.entity.InfoCollectEvent;
import com.wntime.ec.module.sys.entity.InfoCollectFault;
import com.wntime.ec.module.sys.service.*;
import com.wntime.ec.module.sys.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author wing
 * @create 2020/8/28 14:28
 * @desc
 */
@Service("ecServiceImpl")
@Slf4j
public class EcServiceImpl implements IEcService {

    @Value("${remote.server.event_upload_url}")
    private String event_upload_url;

    @Value("${remote.server.device_status_upload_url}")
    private String device_status_upload_url;

    @Value("${remote.server.fault_upload_url}")
    private String fault_upload_url;

    @Value("${remote.server.ad_playlog_url}")
    private String ad_playlog_url;

    @Autowired
    WebSocketSender webSocketSender;
    @Autowired
    IInfoBusDeviceService infoBusDeviceService;
    @Autowired
    IInfoCollectEventService infoCollectEventService;
    @Autowired
    IInfoCollectFaultService infoCollectFaultService;
    @Autowired
    IOrderAdvertiseDeliveryService orderAdvertiseDeliveryService;
    @Autowired
    IOrderAdvertiseAttachService orderAdvertiseAttachService;
    @Autowired
    IOrderDeliveryAreaService orderDeliveryAreaService;
    @Autowired
    IOrderDeliveryTargetService orderDeliveryTargetService;

    @Override
    public FsmsRsp event(EventReceiveReqVo reqVo) {
        if (Constant.isEntered) {
            Integer event_type = reqVo.getEvent_type();
            Integer event_detail = reqVo.getEvent_detail();
            Date event_time = reqVo.getEvent_time();
            String device_code = reqVo.getDevice_code();
            Map detail = reqVo.getDetail();

            //查询事件信息
            InfoCollectEvent event = getInfoCollectEventByEventTypeDetail(event_type, event_detail);
            if (CommonUtil.isEmpty(event)) {
                throw new BusinessException("事件类型不存在");
            }

            EventUploadReqVo eventUploadReqVo = new EventUploadReqVo();
            //查询设备信息
            if (CommonUtil.isNotEmpty(device_code)) {
                InfoBusDeviceQryRspVo infoBusDeviceQryRspVo = infoBusDeviceService.selectById(device_code);
                if (CommonUtil.isEmpty(infoBusDeviceQryRspVo)) {
                    throw new BusinessException(device_code + "设备不存在");
                }
                eventUploadReqVo.setBusDeviceId(infoBusDeviceQryRspVo.getBusDeviceId());
            }

            eventUploadReqVo.setBusId(Constant.localInfoBus.getBusId());
            eventUploadReqVo.setCollectEventId(event.getCollectEventId());
            eventUploadReqVo.setDeviceTypeId(event.getDeviceTypeId());
            eventUploadReqVo.setEventDate(event_time);
            eventUploadReqVo.setEventLevelId(event.getEventLevelId());
            eventUploadReqVo.setEventTargetId(event.getEventTargetId());
            eventUploadReqVo.setEventTypeId(event.getEventTypeId());


            Set<Map.Entry<String, Object>> set = detail.entrySet();
            Iterator<Map.Entry<String, Object>> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> map = it.next();
                String key = map.getKey();
                Object value = map.getValue();

                if (value instanceof String) {
                    String v = (String) value;
                    String[] s = v.split("_");
                    detail.put(key, s[0]);
                }
            }

            Object longitude = detail.get("longitude");
            Object latitude = detail.get("latitude");
            if(longitude != null && latitude != null){
                Long busStationId = GpsUtil.getBusStationIdByXY(Double.parseDouble(longitude.toString()), Double.parseDouble(latitude.toString()));
                if(busStationId != null){
                    detail.put("station_id", busStationId.toString());
                }
            }

            eventUploadReqVo.setTags(detail);
            eventUploadReqVo.setCarStatusInfo(Constant.CurrentCarStatusInfo);

            //调用接口
            FsmsRsp rsp = RemoteServerUtil.post(event_upload_url, eventUploadReqVo, new FsmsRsp() {
            }.getClass());
            if (rsp.getCode() != 0) {
                log.error(Constant.ErrorCode.UPLOAD_EVENT_ERROR.getInfo(rsp.getMessage()));
            }
            return rsp;
        }else{
            return null;
        }
    }

    private void setAttachList(List<OrderAdvertiseAttachQryRspVo> advertiseList, Map param) {
        Long companyLineId = (Long) param.get("companyLineId");
        List<Long> adIdList = orderDeliveryAreaService.selectAdvertiseDeliveryList(param);

        List adItemSet = new ArrayList();
        for (Long advertiseDeliveryId : adIdList) {
            AdItem adItem = new AdItem();
            adItem.setAdDeliveryId(advertiseDeliveryId);

            //排序用
            OrderDeliveryTargetQryReqVo targetQryReqVo = new OrderDeliveryTargetQryReqVo();
            targetQryReqVo.setAdvertiseDeliveryId(advertiseDeliveryId);
            targetQryReqVo.setCompanyLineId(companyLineId);
            List<OrderDeliveryTargetQryRspVo> targetQryRspVos = orderDeliveryTargetService.selectAdvertiseTargetList(targetQryReqVo);
            int score = 0;
            for (OrderDeliveryTargetQryRspVo targetQryRspVo : targetQryRspVos) {
                String targetCode = targetQryRspVo.getAdvertiseTargetCode();
                Integer count = Constant.advertiseTagCountMap.get(targetCode);
                if (count != null) {
                    score += count;
                }
            }
            adItem.setScore(score);
            adItemSet.add(adItem);
        }
        Collections.sort(adItemSet);
        for (Iterator<AdItem> it = adItemSet.iterator(); it.hasNext(); ) {
            AdItem next = it.next();
            //附件
            OrderAdvertiseAttachQryReqVo attachQryReqVo = new OrderAdvertiseAttachQryReqVo();
            attachQryReqVo.setAdvertiseDeliveryId(next.getAdDeliveryId());
            attachQryReqVo.setCompanyLineId(companyLineId);
            List<OrderAdvertiseAttachQryRspVo> attachList = orderAdvertiseAttachService.selectAdvertiseAttachList(attachQryReqVo);
            advertiseList.addAll(attachList);
        }
    }


    @Override
    public void status(StatusReceiveReqVo reqVo) {
        if (Constant.isEntered) {
            String device_code = reqVo.getDevice_code();
            Integer device_status = reqVo.getDevice_status();
            Date event_time = reqVo.getEvent_time();

            //查询设备信息
            InfoBusDeviceQryRspVo infoBusDeviceQryRspVo = infoBusDeviceService.selectById(device_code);
            if (CommonUtil.isEmpty(infoBusDeviceQryRspVo)) {
                throw new BusinessException("设备不存在");
            }

            StatusUploadReqVo statusUploadReqVo = new StatusUploadReqVo();
            statusUploadReqVo.setBusId(Constant.localInfoBus.getBusId());
            statusUploadReqVo.setBusDeviceId(infoBusDeviceQryRspVo.getBusDeviceId());
            statusUploadReqVo.setDeviceTypeId(infoBusDeviceQryRspVo.getDeviceTypeId());
            statusUploadReqVo.setStatus(device_status);

            //调用接口
            FsmsRsp rsp = RemoteServerUtil.post(device_status_upload_url, statusUploadReqVo, new FsmsRsp() {
            }.getClass());
            if (rsp.getCode() != 0) {
                log.error(Constant.ErrorCode.UPLOAD_DEVICE_STATUS_ERROR.getInfo(rsp.getMessage()));
            }
        }
    }

    @Override
    public void fault(FaultReceiveReqVo reqVo) {
        if (Constant.isEntered) {
            String event_type = reqVo.getFault_type();
            String event_detail = reqVo.getFault_detail();
            Date event_time = reqVo.getFault_time();
            String level = reqVo.getLevel();
            Map detail = reqVo.getDetail();

            //查询事件信息
            InfoCollectFault fault = getInfoCollectFaultByEventTypeDetail(event_type, event_detail);
            if (CommonUtil.isEmpty(fault)) {
                throw new BusinessException("故障类型不存在");
            }

            FaultUploadReqVo faultUploadReqVo = new FaultUploadReqVo();
            faultUploadReqVo.setBusId(Constant.localInfoBus.getBusId());
            faultUploadReqVo.setCollectEventId(fault.getCollectEventId());
            faultUploadReqVo.setEventDate(event_time);
            faultUploadReqVo.setEventLevelId(fault.getFaultLevelId());
            faultUploadReqVo.setEventTargetId(fault.getFaultTargetId());
            faultUploadReqVo.setEventTypeId(fault.getFaultTypeId());
            faultUploadReqVo.setCarStatusInfo(Constant.CurrentCarStatusInfo);
            faultUploadReqVo.setTags(detail);

            //调用接口
            FsmsRsp rsp = RemoteServerUtil.post(fault_upload_url, faultUploadReqVo, new FsmsRsp() {
            }.getClass());
            if (rsp.getCode() != 0) {
                log.error(Constant.ErrorCode.UPLOAD_FAULT_ERROR.getInfo(rsp.getMessage()));
            }
        }
    }

    @Override
    public void playAdLog(long advertiseDeliveryId, long advertiseAttachId) {
        Long companyLineId = Constant.currentCompanyLineId;

        OrderAdvertiseAttachQryReqVo attachQryReqVo = new OrderAdvertiseAttachQryReqVo();
        attachQryReqVo.setAdvertiseAttachId(advertiseAttachId);
        attachQryReqVo.setCompanyLineId(companyLineId);
        List<OrderAdvertiseAttachQryRspVo> attachList = orderAdvertiseAttachService.selectList(attachQryReqVo);
        if (CommonUtil.isNotEmpty(attachList)) {
            OrderAdvertiseAttachQryRspVo attachQryRspVo = attachList.get(0);

            AdLogUploadReqVo adLogUploadReqVo = new AdLogUploadReqVo();
            adLogUploadReqVo.setAdvertiseDeliveryId(advertiseDeliveryId);
            adLogUploadReqVo.setAdvertisePositionId(attachQryRspVo.getAdvertisePositionId());
            adLogUploadReqVo.setAttachType(attachQryRspVo.getAttachType());
            adLogUploadReqVo.setBusId(Constant.localInfoBus.getBusId());
            adLogUploadReqVo.setCompanyLineId(companyLineId);
            adLogUploadReqVo.setShowTimes(attachQryRspVo.getShowTimes().intValue());

            //调用接口
            FsmsRsp rsp = RemoteServerUtil.post(ad_playlog_url, adLogUploadReqVo, new FsmsRsp() {
            }.getClass());
            if (rsp.getCode() != 0) {
                log.error(Constant.ErrorCode.UPLOAD_AD_PLAYLOG_ERROR.getInfo(rsp.getMessage()));
            }
        }
    }

    private InfoCollectFault getInfoCollectFaultByEventTypeDetail(String event_type, String event_detail) {
        InfoCollectFaultQryReqVo infoCollectFaultQryReqVo = new InfoCollectFaultQryReqVo();
        infoCollectFaultQryReqVo.setFaultType(event_type);
        infoCollectFaultQryReqVo.setFaultDetail(event_detail);
        List<InfoCollectFaultQryRspVo> infoCollectFaultQryRspVos = infoCollectFaultService.selectList(infoCollectFaultQryReqVo);
        if (CommonUtil.isNotEmpty(infoCollectFaultQryRspVos)) {
            return infoCollectFaultQryRspVos.get(0);
        }
        return null;
    }

    private InfoCollectEvent getInfoCollectEventByEventTypeDetail(Integer event_type, Integer event_detail) {
        InfoCollectEventQryReqVo infoCollectEventQryReqVo = new InfoCollectEventQryReqVo();
        infoCollectEventQryReqVo.setEventType(event_type);
        infoCollectEventQryReqVo.setEventDetail(event_detail);
        List<InfoCollectEventQryRspVo> infoCollectEventQryRspVos = infoCollectEventService.selectList(infoCollectEventQryReqVo);
        if (CommonUtil.isNotEmpty(infoCollectEventQryRspVos)) {
            return infoCollectEventQryRspVos.get(0);
        }
        return null;
    }

    private Long setCurrentLineAndStation(double longitude, double latitude) {
        try {
            //计算当前线路（上行还是下行），计算当前/下一站点
            Set<Map.Entry<Long, List<InfoLineStationQryRspVo>>> entrySet = Constant.lineStationMap.entrySet();
            Iterator<Map.Entry<Long, List<InfoLineStationQryRspVo>>> it = entrySet.iterator();

            List<Map> lineList = new ArrayList();
            Map<Long, Station> stationMap = new HashMap();
            while (it.hasNext()) {
                Map.Entry<Long, List<InfoLineStationQryRspVo>> next = it.next();
                Long companyLineId = next.getKey();
                List<InfoLineStationQryRspVo> valueList = next.getValue();
                int weight = 0;
                int index = 0;
                for (InfoLineStationQryRspVo vo : valueList) {
                    Long busStationId = vo.getBusStationId();
                    Long lineStationId = vo.getLineStationId();
                    if (
                            (CommonUtil.isNotEmpty(Constant.lastBusStationId) && Constant.lastBusStationId.longValue() == busStationId.longValue())
                                    || (CommonUtil.isNotEmpty(Constant.lastBusStationId2) && Constant.lastBusStationId2.longValue() == busStationId.longValue())) {
                        weight++;
                    }
                    BigDecimal busStationLongitude = vo.getBusStationLongitude();
                    BigDecimal busStationLatitude = vo.getBusStationLatitude();
                    Integer busStationDeviation = vo.getBusStationDeviation();
                    double distance = DistanceUtil.getShortestDistance(longitude, latitude, busStationLongitude.doubleValue(), busStationLatitude.doubleValue());
                    if (distance <= busStationDeviation) {
                        Station station = new Station(busStationId, lineStationId, index);
                        stationMap.put(companyLineId, station);
                        weight += 2;
                        break;
                    }
                    index++;
                }
                Map line = new HashMap();
                line.put("companyLineId", companyLineId);
                line.put("score", weight);
                lineList.add(line);
            }

            //获取当前线路及车站
            Map firstScoreLine = getMaxScoreLine(lineList);
            Long firstLineId = (Long) firstScoreLine.get("companyLineId");            //线路1
            int firstLineScore = (int) firstScoreLine.get("score");
            Station firstLineStation = stationMap.get(firstLineId);

            Map secondScoreLine = getMinScoreLine(lineList);
            Long secondLineId = (Long) secondScoreLine.get("companyLineId");          //线路2
            int secondLineScore = (int) secondScoreLine.get("score");
            Station secondLineStation = stationMap.get(secondLineId);


            //如果只有一条线或者两条线得分不同
            if ((firstLineId.longValue() == secondLineId.longValue()) || (firstLineScore > secondLineScore)) {
                Long lineStationId = firstLineStation.getLineStationId();
                Long busStationId = firstLineStation.getBusStationId();
                int index = firstLineStation.getIndex();
                List<InfoLineStationQryRspVo> infoLineStations = Constant.lineStationMap.get(firstLineId);

                //上一站
                Constant.lastBusStationId = busStationId;
                Constant.lastBusStationId2 = null;

                //当前站
                Constant.currentLineStationId = lineStationId;                          //当前车站ID
                Constant.currentBusStationId = busStationId;                            //当前车站ID

                //下一站
                int size = infoLineStations.size();
                if ((index + 1) < size) {
                    InfoLineStationQryRspVo vo = infoLineStations.get(index + 1);
                    Constant.nextLineStationId = vo.getLineStationId();
                } else {
                    Constant.nextLineStationId = null;
                }

                Constant.currentCompanyLineId = firstLineId;
                return firstLineId;
            }
            //如果有两条线，且得分相等
            else {
                /**
                 * 0分 未定位到当前车站，且没有之前车站信息     --忽略
                 * 1分 有之前车站信息，未定位到当前站          --忽略
                 * 2分 定位到当前站，没有之前车站信息
                 * 3分 定位到当前站，有之前车站信息
                 */
                if (firstLineScore == 2 || firstLineScore == 3) {
                    Long firstLineStationId = firstLineStation.getLineStationId();
                    Long firstBusStationId = firstLineStation.getBusStationId();
                    int firstIndex = firstLineStation.getIndex();
                    List<InfoLineStationQryRspVo> firstInfoLineStations = Constant.lineStationMap.get(firstLineId);

//                Long secondLineStationId = secondLineStation.getLineStationId();
                    Long secondBusStationId = secondLineStation.getBusStationId();
//                int secondIndex = secondLineStation.getIndex();
//                List<InfoLineStationQryRspVo> secondInfoLineStations = Constant.lineStationMap.get(secondLineId);

                    //上一站
                    Constant.lastBusStationId = firstBusStationId;
                    Constant.lastBusStationId2 = secondBusStationId;

                    //当前站
                    Constant.currentLineStationId = firstLineStationId;                       //当前车站ID
                    Constant.currentBusStationId = firstBusStationId;                         //当前车站ID

                    //下一站
                    int size = firstInfoLineStations.size();
                    if ((firstIndex + 1) < size) {
                        InfoLineStationQryRspVo vo = firstInfoLineStations.get(firstIndex + 1);
                        Constant.nextLineStationId = vo.getLineStationId();
                    } else {
                        Constant.nextLineStationId = null;
                    }

                    Constant.currentCompanyLineId = firstLineId;
                    return firstLineId;
                }
                return Constant.currentCompanyLineId;
            }
        } catch (Exception e) {
            return Constant.currentCompanyLineId;
        }
    }

    private Map getMaxScoreLine(List<Map> list) {
        Map max = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if ((int) list.get(i).get("score") > (int) max.get("score")) {
                max = list.get(i);
            }
        }
        return max;
    }

    private Map getMinScoreLine(List<Map> list) {
        Map min = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if ((int) list.get(i).get("score") <= (int) min.get("score")) {
                min = list.get(i);
            }
        }
        return min;
    }

    /**
     * 根据位置信息获取车站id
     * @param longitude
     * @param latitude
     * @return
     */
    private Long getBusStationIdByLocation(double longitude, double latitude) {
        //车辆所在的线路
        Map<Long,List<InfoLineStationQryRspVo>> lineStationMap = Constant.lineStationMap;
        for(Map.Entry<Long,List<InfoLineStationQryRspVo>> entry : lineStationMap.entrySet()){
            List<InfoLineStationQryRspVo> lineStationList = entry.getValue();
            for(InfoLineStationQryRspVo lineStation : lineStationList){
                BigDecimal busStationLongitude = lineStation.getBusStationLongitude();
                BigDecimal busStationLatitude = lineStation.getBusStationLatitude();
                Integer busStationDeviation = lineStation.getBusStationDeviation();
                double distance = DistanceUtil.getShortestDistance(longitude, latitude, busStationLongitude.doubleValue(), busStationLatitude.doubleValue());
                if(distance <= busStationDeviation){
                    return lineStation.getBusStationId();
                }
            }
        }
        return null;
    }
}
