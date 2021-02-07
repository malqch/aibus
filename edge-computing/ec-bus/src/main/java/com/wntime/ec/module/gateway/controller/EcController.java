package com.wntime.ec.module.gateway.controller;

import cn.hutool.json.JSONUtil;
import com.wntime.ec.common.model.Constant;
import com.wntime.ec.common.util.BaseController;
import com.wntime.ec.common.util.RemoteAlleyesUtil;
import com.wntime.ec.common.util.param.http.HttpRspParam;
import com.wntime.ec.common.util.validator.group.AddGroup;
import com.wntime.ec.module.gateway.service.IEcService;
import com.wntime.ec.module.gateway.vo.*;
import com.wntime.ec.module.sys.vo.OrderAdvertiseAttachQryRspVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外部对接接口
 */
@Api(value = "外部对接接口", tags = "外部对接接口")
@RestController
@RequestMapping("/ec")
@Slf4j
@SuppressWarnings("all")
public class EcController extends BaseController {

    @Value("${remote.alleyes.live_url}")
    private String live_url;

    @Autowired
    private IEcService ecService;


    /**
     * 事件
     *
     * @return
     */
    @PostMapping("/event")
    @ApiOperation(value = "事件上报", notes = "事件上报", response = String.class)
    public HttpRspParam event(@RequestBody @Validated(AddGroup.class) EventReceiveReqVo reqVo) {
        ecService.event(reqVo);
        return success("success");
    }

    /**
     * 状态
     *
     * @return
     */
    @PostMapping("/status")
    public HttpRspParam status(@RequestBody @Validated(AddGroup.class) StatusReceiveReqVo reqVo) {
        ecService.status(reqVo);
        return success("success");
    }

    /**
     * 故障
     *
     * @return
     */
    @PostMapping("/fault")
    public HttpRspParam fault(@RequestBody @Validated(AddGroup.class) FaultReceiveReqVo reqVo) {
        ecService.fault(reqVo);
        return success("success");
    }

    /**
     * 获取摄像头视频流
     */
    @GetMapping("/getCameraFlv")
    @ApiOperation(value = "获取摄像头视频流", notes = "获取摄像头视频流", response = List.class)
    public HttpRspParam getCameraFlv() {
        List result = new ArrayList();

        HttpRspParam<LiveQryRspWrapVo> rsp = RemoteAlleyesUtil.get(live_url, new HttpRspParam<LiveQryRspWrapVo>() {
        }.getClass());

        if (rsp.getCode() == 0) {
            List<LiveQryRspVo> list = rsp.getData().getList();
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();
                map.put("url", list.get(i).getUrl() + "&sport=" + Constant.sshSport);
                map.put("name", "摄像头-" + i);
                map.put("deviceCode", list.get(i).getDevice_code().trim());
                result.add(map);
            }
        }

//        http://192.168.111.2/live?port=1935&app=live&stream=3
//        Map map1 = new HashMap();
//        map1.put("url", "/live?port=1935&app=live&stream=2&sport=17601");
//        map1.put("name", "摄像头-2");
//
//        Map map2 = new HashMap();
//        map2.put("url", "/live?port=1935&app=live&stream=3&sport=17601");
//        map2.put("name", "摄像头-3");
//        result.add(map1);
//        result.add(map2);

        return success(result);
    }

    /**
     * 测试广告
     */
    @Autowired
    WebSocketSender webSocketServer;

    @GetMapping("/getAdvertise")
    @ApiOperation(value = "测试广告", notes = "测试广告", response = List.class)
    public HttpRspParam getAdvertise() {
        List advertiseList = new ArrayList<>();
        List interruptAdList = new ArrayList();

        List<OrderAdvertiseAttachQryRspVo> attchList = new ArrayList<>();
        OrderAdvertiseAttachQryRspVo attach1 = new OrderAdvertiseAttachQryRspVo();
        attach1.setAdvertiseDeliveryId(1001L);
        attach1.setAdvertiseAttachId(1001L);
        attach1.setAttachType(0);
        attach1.setShowTimes(new BigDecimal(3));
        attach1.setSuffix(".jpg");
        attach1.setPositionCode("square");

        OrderAdvertiseAttachQryRspVo attach2 = new OrderAdvertiseAttachQryRspVo();
        attach2.setAdvertiseDeliveryId(1001L);
        attach2.setAdvertiseAttachId(1002L);
        attach2.setAttachType(1);
        attach2.setShowTimes(new BigDecimal(6));
        attach2.setSuffix(".mp4");
        attach2.setPositionCode("square");

        OrderAdvertiseAttachQryRspVo attach3 = new OrderAdvertiseAttachQryRspVo();
        attach3.setAdvertiseDeliveryId(1001L);
        attach3.setAdvertiseAttachId(1003L);
        attach3.setAttachType(0);
        attach3.setShowTimes(new BigDecimal(9));
        attach3.setSuffix(".jpg");
        attach3.setPositionCode("square");

        interruptAdList.add(attach1);
        interruptAdList.add(attach2);
        interruptAdList.add(attach3);

        advertiseList.addAll(interruptAdList);

        List stationAdList = new ArrayList();
        List lineAdList = new ArrayList();


        //推送websocket到前端
        String msg = JSONUtil.toJsonStr(advertiseList);
        webSocketServer.sendAll(msg);
        advertiseList.clear();
        return success("success");
    }

}
