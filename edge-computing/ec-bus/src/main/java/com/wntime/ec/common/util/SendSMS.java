package com.wntime.ec.common.util;

import cn.net.itown.sms.util.LogUtil;
import cn.net.itown.sms.util.SMS2;
import com.wntime.ec.common.config.SmsInfo;

/**
 * @author wing
 * @create 2020-02-07 14:52
 */
public class SendSMS extends cn.net.itown.sms.util.SendSMS {

    @Override
    public int sendSingleMessage(String id, String content, String phone, String type, String SP) {
//        LogUtil.log("id:" + id + " msg:" + content + " phone:" + phone + " type:" + type + " SP:" + SP);
        String ServerHost = null;
        int port = 0;
        if("DX".equals(SP)){
            ServerHost = SmsInfo.DXHOST;
            port = Integer.parseInt(SmsInfo.DXPORT);
        }else if("LT".equals(SP)){
            ServerHost = SmsInfo.LTHOST;
            port = Integer.parseInt(SmsInfo.LTPORT);
        }else if("YD".equals(SP)){
            ServerHost = SmsInfo.YDHOST;
            port = Integer.parseInt(SmsInfo.YDPORT);
        }

        int timeout = Integer.parseInt(SmsInfo.TIMEOUT);
        SMS2 sms = new SMS2(ServerHost, port, timeout);
        int errno = 0;

        try {
            String[][] message = this.compoMessage(id, type + " " + content, phone);
            errno = sms.sendMessage(message);
        } catch (Exception var10) {
            LogUtil.log(var10);
        }

        return errno;
    }
}
