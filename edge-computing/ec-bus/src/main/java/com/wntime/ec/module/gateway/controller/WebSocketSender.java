package com.wntime.ec.module.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wing
 * @create 2020/11/23 14:46
 * @desc
 */
@Slf4j
@Component("webSocketSender")
public class WebSocketSender {

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    //客户端加入
    public void join(Session session, String id) {
        sessionPools.put(id, session);
//        try {
//            sendMessage(session, "欢迎" + id + "加入连接!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    //删除客户端
    public void remove(String id) {
        sessionPools.remove(id);
    }

    //发送消息
    private void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        }
    }

    //给指定用户发送信息
    public void sendOne(String id, String message) {
        Session session = sessionPools.get(id);
        try {
            sendMessage(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAll(String message) {
        for (Session session : sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
