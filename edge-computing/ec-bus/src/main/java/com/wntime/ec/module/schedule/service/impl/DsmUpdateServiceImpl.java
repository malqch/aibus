package com.wntime.ec.module.schedule.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wntime.ec.common.model.Constant;
import com.wntime.ec.common.model.FsmsRsp;
import com.wntime.ec.common.util.CommonUtil;
import com.wntime.ec.common.util.DateUtil;
import com.wntime.ec.common.util.GpsUtil;
import com.wntime.ec.common.util.RemoteAlleyesUtil;
import com.wntime.ec.common.util.param.http.HttpRspParam;
import com.wntime.ec.module.gateway.service.IEcService;
import com.wntime.ec.module.gateway.vo.EventReceiveReqVo;
import com.wntime.ec.module.schedule.service.IDsmUpdateService;
import com.wntime.ec.module.schedule.vo.*;
import com.wntime.ec.module.sys.service.IInfoBusDeviceService;
import com.wntime.ec.module.sys.service.IInfoCollectEventService;
import com.wntime.ec.module.sys.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DsmUpdateServiceImpl implements IDsmUpdateService {

    @Value("${remote.dsm.username}")
    private String dsmUsername;

    @Value("${remote.dsm.passwd}")
    private String dsmPasswd;

    @Value("${remote.dsm.login_url}")
    private String login_url;

    @Value("${remote.dsm.alarm_url}")
    private String alarm_url;

    @Value("${remote.dsm.vehicle_status_url}")
    private String vehicle_status_url;

    @Value("${remote.dsm.file_url}")
    private String file_url;

    @Value("${bus.dsm.carid}")
    private String carid;

    @Value("${remote.baidu.geoconv_url}")
    private String geoconv_url;

    @Value("${remote.alleyes.status_station_url}")
    private String status_station_url;

    @Value("${speed.max}")
    private String speedMax;

    /**
     * 上一个速度坐标位置
     */
    private static String preSpeedXY;
    /**
     * 车速连续为0的次数
     */
    private static int speedZeroCount;
    /**
     * 车辆状态 1 到站 2 离站
     */
    private static int carStationStatus;

    /**
     * 行程方向 1 上行 2 下行
     */
    private static int direction;

    /**
     * 车站是否为终点站
     * key : bus_station_id + direction
     * value : 否1 是 2
     */
    private Map<String,Integer> idTerminalMap = new HashMap<>();

    /**
     * 车站是否为起点站
     * key : bus_station_id + direction
     * value : 否1 是 2
     */
    private Map<String,Integer> isFirstStationMap = new HashMap<>();

    /**
     * 事件上报状态
     * key : bus_station_id + status(到站/离站)
     * 从终点站离开后清空
     */
    private Map<String,Boolean> eventReportStatusMap = new HashMap<>();

    @Autowired
    private IInfoBusDeviceService infoBusDeviceService;

    @Autowired
    IInfoCollectEventService infoCollectEventService;

    @Autowired
    private IEcService ecService;

    /**
     * @Author Buxl
     * @Description 从dsm获取司机违规信息
     * @Date 11:39 2021/1/27
     * @Param []
     * @return void
     **/
    @Override
    public void getDSMInfo() {
        if (Constant.isEntered) {
            Date currentDate = DateUtil.operateDate(new Date(), -1, DateUtil.TimeUnit.MINUTE);

            long nowTime = currentDate.getTime();
            if (Constant.DSM_SESSION_TIME == null || Constant.DSM_SESSION == null || (nowTime - Constant.DSM_SESSION_TIME) > 5 * 60 * 1000) {
                loginDsm(nowTime);
            }

            if (Constant.DSM_ALARM_LAST_ENDTIME == null) {
                Constant.DSM_ALARM_LAST_ENDTIME = DateUtil.date2String(currentDate, DateUtil.yyyy_MM_dd_HH_mm_ss);
            }

            String currentDateStr = DateUtil.date2String(currentDate, DateUtil.yyyy_MM_dd_HH_mm_ss);
            Map alarmParam = new HashMap();
            alarmParam.put("beginTime", Constant.DSM_ALARM_LAST_ENDTIME);
            alarmParam.put("endTime", currentDateStr);
            alarmParam.put("selectValue", carid);
            alarmParam.put("alarmtype", "1,2,3,4,5,11");
            String alarmRsp = HttpRequest.post(alarm_url).form(alarmParam).timeout(-1).header("Cookie", "JSESSIONID=" + Constant.DSM_SESSION).execute().body();

            DsmAlarmRspWrapVo alarmRspWrap = JSONUtil.toBean(alarmRsp, DsmAlarmRspWrapVo.class);
            List<DsmAlarmRspVo> list = alarmRspWrap.getList();

            try {
                if (CommonUtil.isNotEmpty(list)) {
                    for (DsmAlarmRspVo dsmAlarmRspVo : list) {
                        String alarmtype = dsmAlarmRspVo.getAlarmtype();

                        InfoCollectEventQryReqVo infoCollectEventQryReqVo = new InfoCollectEventQryReqVo();
                        infoCollectEventQryReqVo.setCollectEvent(alarmtype);
                        List<InfoCollectEventQryRspVo> infoCollectEventQryRspVos = infoCollectEventService.selectList(infoCollectEventQryReqVo);
                        if (CommonUtil.isNotEmpty(infoCollectEventQryRspVos)) {
                            InfoCollectEventQryRspVo infoCollectEventQryRspVo = infoCollectEventQryRspVos.get(0);
                            Integer eventType = infoCollectEventQryRspVo.getEventType();
                            Integer eventDetail = infoCollectEventQryRspVo.getEventDetail();

                            String driverName = dsmAlarmRspVo.getDriverName();
                            Long uploadtime = dsmAlarmRspVo.getUploadtime();
                            String position = dsmAlarmRspVo.getPosition();
                            String attachfiledir = dsmAlarmRspVo.getAttachfiledir();

                            Map detailMap = new HashMap();

                            if (CommonUtil.isNotEmpty(position)) {
                                String[] positionAry = position.split(",");
                                detailMap.put("latitude", positionAry[1]);
                                detailMap.put("longitude", positionAry[0]);
                            }

                            if (CommonUtil.isNotEmpty(attachfiledir)) {
                                Map fileParam = new HashMap();
                                fileParam.put("file", attachfiledir);
                                String fileRsp = HttpRequest.post(file_url).form(fileParam).timeout(-1).header("Cookie", "JSESSIONID=" + Constant.DSM_SESSION).execute().body();
                                DsmFileDownloadRspVo fileDownloadRspVo = JSONUtil.toBean(fileRsp, DsmFileDownloadRspVo.class);
                                List<String> image = fileDownloadRspVo.getImage();

                                if (CommonUtil.isNotEmpty(image)) {
                                    for (int i = 1; i <= image.size(); i++) {
                                        try {
                                            for (int j = 0; j < 10; j++) {
                                                ByteArrayOutputStream out = new ByteArrayOutputStream();
                                                HttpUtil.download("http://" + image.get(i - 1), out, true);
                                                byte[] imgBytes = out.toByteArray();
                                                String eventImg = Base64.encodeBase64String(imgBytes);
                                                detailMap.put("img" + i, eventImg);
                                                if (imgBytes.length <= 0) {
                                                    Thread.sleep(5000);
                                                } else {
                                                    break;
                                                }
                                            }
                                        } catch (Exception e) {
                                            log.error(e.getMessage());
                                        }
                                    }
                                }
                            }

                            detailMap.put("driver", driverName);

                            EventReceiveReqVo eventReceiveReqVo = new EventReceiveReqVo();
                            eventReceiveReqVo.setEvent_type(eventType);
                            eventReceiveReqVo.setEvent_detail(eventDetail);
                            eventReceiveReqVo.setEvent_time(new Date(uploadtime));
                            eventReceiveReqVo.setDetail(detailMap);

                            InfoBusDeviceQryReqVo infoBusDeviceQryReqVo = new InfoBusDeviceQryReqVo();
                            infoBusDeviceQryReqVo.setDeviceDescCode("f_d_dsm_driver");
                            List<InfoBusDeviceQryRspVo> infoBusDeviceQryRspVos = infoBusDeviceService.selectList(infoBusDeviceQryReqVo);
                            eventReceiveReqVo.setDevice_code(infoBusDeviceQryRspVos.get(0).getDeviceCode());

                            ecService.event(eventReceiveReqVo);
                        } else {
                            log.error(Constant.ErrorCode.DSM_ALARM_TYPE_NOT_EXISTS_ERROR.getInfo(alarmtype));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error(Constant.ErrorCode.GET_DSM_INFO_ERROR.getInfo(e.getMessage()));
            } finally {
                Constant.DSM_ALARM_LAST_ENDTIME = currentDateStr;
            }
        }
    }

    /**
     * @Author Buxl
     * @Description 从dsm获取车速
     * @Date 11:39 2021/1/27
     * @Param []
     * @return void
     **/
    @Override
    public void getDSMSpeed() {
        if (Constant.isEntered) {
            Date currentDate = DateUtil.operateDate(new Date(), -1, DateUtil.TimeUnit.MINUTE);

            long nowTime = currentDate.getTime();
            if (Constant.DSM_SESSION_TIME == null || Constant.DSM_SESSION == null || (nowTime - Constant.DSM_SESSION_TIME) > 5 * 60 * 1000) {
                loginDsm(nowTime);
            }

            Map params = new HashMap();
            params.put("selectValue", carid);
            params.put("toMap", "2");
            String vehicleStatusRsp = HttpRequest.post(vehicle_status_url).form(params).timeout(-1).header("Cookie", "JSESSIONID=" + Constant.DSM_SESSION).execute().body();
            DsmVehicleStatusRspWarpVo vehicleStatusObj = JSONUtil.toBean(vehicleStatusRsp, DsmVehicleStatusRspWarpVo.class);
            if(vehicleStatusObj != null && vehicleStatusObj.getStatusList() != null){
                DsmVehicleStatusRspVo rspVo = vehicleStatusObj.getStatusList().get(0);

                //行驶情况 - 时速
                EventReceiveReqVo eventReceiveReqVo = new EventReceiveReqVo();
                eventReceiveReqVo.setEvent_type(11);
                eventReceiveReqVo.setEvent_detail(1);
                eventReceiveReqVo.setEvent_time(new Date(rspVo.getDateTime()));
                eventReceiveReqVo.setDevice_code(getDeviceCodeByDescCode("f_d_dsm_driver"));

                //处理gps坐标偏移
                String longitude = BigDecimal.valueOf(rspVo.getLongitude()).divide(BigDecimal.valueOf(1000000)).toString();
                String latitude = BigDecimal.valueOf(rspVo.getLatitude()).divide(BigDecimal.valueOf(1000000)).toString();
                double speed = rspVo.getSpeed();
//                //TODO test
//                speed = 70;
                Map<String,Double> baiduXY = GpsUtil.getBaiduXY(geoconv_url,longitude,latitude);
                boolean isArrive = false;
                boolean isLeave = false;
                if(baiduXY != null){
                    Map<String,Object> detailMap = new HashMap<String, Object>();
                    detailMap.put("speed",speed);
                    detailMap.putAll(baiduXY);
                    eventReceiveReqVo.setDetail(detailMap);

                    Long busStationId = GpsUtil.getBusStationIdByXY(baiduXY.get("longitude"),baiduXY.get("latitude"));
                    if(busStationId != null){
                        //根据GPS坐标找到了车站
                        if(speed == 0.0){
                            //速度为0时判断是否为到站
                            if(speedZeroCount > 3){
                                //到站
                                isArrive = true;
                                carStationStatus = 1;
                                speedZeroCount = 0;
                            }
                            speedZeroCount++;
                        }else{
                            speedZeroCount = 0;
                            if(1 == carStationStatus){
                                isLeave = true;
                                carStationStatus = 2;
                            }
                        }
                    }

                    if(!(preSpeedXY != null && preSpeedXY.equals(rspVo.getLongitude().toString() + "," + rspVo.getLatitude().toString()))){
                        //上报事件: 行驶情况 - 时速
                        FsmsRsp rsp = ecService.event(eventReceiveReqVo);
                        if(rsp != null && rsp.getData() != null && busStationId != null){
                            //方向 上行 1 /下行 2
                            direction = (Integer) ((Map)rsp.getData()).get("direction");

                            //是否为终点站 否1 / 是 2
                            Integer id_terminal = (Integer) ((Map)rsp.getData()).get("id_terminal");
                            idTerminalMap.put(busStationId.toString() + direction,id_terminal);

                            //是否为终点站 否1 / 是 2
                            Integer is_first_station = (Integer) ((Map)rsp.getData()).get("is_first_station");
                            isFirstStationMap.put(busStationId.toString() + direction,is_first_station);
                        }
                        //计算是否超速
                        if(speed > Double.valueOf(speedMax)){
                            //上报超速事件
                            eventReceiveReqVo.setEvent_type(10);
                            eventReceiveReqVo.setEvent_detail(1);
                            eventReceiveReqVo.setDevice_code(getDeviceCodeByDescCode("m_i_native_ec"));
                            ecService.event(eventReceiveReqVo);
                        }
                    }
                    preSpeedXY = rspVo.getLongitude().toString() + "," + rspVo.getLatitude().toString();



                    if(busStationId != null && (isArrive || isLeave)){
                        //检测事件是否下发上报过了
                        int status = isArrive ? 1 : 2;
                        String eventReportStatusMapKey = busStationId.toString() + status;
                        String idTerminalMapMey = busStationId.toString() + direction;
                        if(eventReportStatusMap.get(eventReportStatusMapKey) == null){
                            //事件还没有上报过
                            eventReportStatusMap.put(eventReportStatusMapKey,Boolean.TRUE);
                            //将到站、离站事件下发到百目
                            StationStatusRepVo vo = new StationStatusRepVo();
                            vo.setDirection(direction);
                            vo.setId_terminal(idTerminalMap.get(idTerminalMapMey));
                            vo.setIs_first_station(isFirstStationMap.get(idTerminalMapMey));
                            vo.setStatus(status);
                            log.info("车辆到站离站事件下发百目, vo : " + vo.toString());
                            HttpRspParam rep = RemoteAlleyesUtil.post(status_station_url,vo, new HttpRspParam() {}.getClass());
                            if(rep.getCode() == 0){
                                log.info("车辆到站离站事件下发百目成功, rsp : " + rep.toString());
                            }else{
                                log.info("车辆到站离站事件下发百目失败, rsp : " + rep.toString());
                            }

                            //将到站、离站事件上报
                            eventReceiveReqVo.setEvent_type(2);
                            eventReceiveReqVo.setEvent_detail(isArrive ? 5 : 6);
                            eventReceiveReqVo.setDevice_code(getDeviceCodeByDescCode("m_i_native_ec"));

                            detailMap = new HashMap<String, Object>();
                            detailMap.putAll(baiduXY);
                            eventReceiveReqVo.setDetail(detailMap);

                            ecService.event(eventReceiveReqVo);

                            if(2 == vo.getId_terminal() && isLeave){
                                eventReportStatusMap.clear();
                            }
                        }
                    }
                }
            }
        }
    }

    private void loginDsm(long sessionTime){
        //登录
        Map loginParam = new HashMap();
        loginParam.put("account", dsmUsername);
        loginParam.put("password", dsmPasswd);

        HttpResponse res = HttpRequest.post(login_url).form(loginParam).timeout(-1).execute();
        String cookie = res.header("Set-Cookie");
        String session = cookie.split(";")[0].split("=")[1];
        Constant.DSM_SESSION = session;
        Constant.DSM_SESSION_TIME = sessionTime;
    }

    private String getDeviceCodeByDescCode(String descCode){
        InfoBusDeviceQryReqVo infoBusDeviceQryReqVo = new InfoBusDeviceQryReqVo();
        infoBusDeviceQryReqVo.setDeviceDescCode(descCode);
        List<InfoBusDeviceQryRspVo> infoBusDeviceQryRspVos = infoBusDeviceService.selectList(infoBusDeviceQryReqVo);
        return infoBusDeviceQryRspVos.get(0).getDeviceCode();
    }
}
