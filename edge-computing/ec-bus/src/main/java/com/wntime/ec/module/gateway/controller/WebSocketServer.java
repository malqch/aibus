package com.wntime.ec.module.gateway.controller;

import com.wntime.ec.module.gateway.service.IEcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Slf4j
@Controller
@ServerEndpoint(value = "/gateway/advertise/{id}")
public class WebSocketServer {

    private static IEcService ecService;
    private static WebSocketSender webSocketSender;

    @Autowired
    public void setEcService(IEcService ecService) {
        WebSocketServer.ecService = ecService;
    }
    @Autowired
    public void setWebSocketSender(WebSocketSender webSocketSender) {
        WebSocketServer.webSocketSender = webSocketSender;
    }


    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "id") String id) {
        webSocketSender.join(session, id);
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "id") String id) {
        webSocketSender.remove(id);
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String message) throws IOException {
        try {
            String[] msgAry = message.split(",");
            String advertiseDeliveryId = msgAry[0];
            String advertiseAttachId = msgAry[1];

            ecService.playAdLog(Long.parseLong(advertiseDeliveryId), Long.parseLong(advertiseAttachId));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("广告websocket发生错误", throwable.fillInStackTrace());
    }
}