package com.wntime.ec.common.util;

import com.wntime.ec.common.config.SmsInfo;

/**
 * @author wing
 * @create 2020-02-07 14:46
 */
public class Send extends sendsms.Send {

    @Override
    public int send(String phone, String msg) {
        String server = SmsInfo.SERVER;
        String poitshow = SmsInfo.PORTSHOW;
        String backname = SmsInfo.BACKNAME;
        backname = "";
        SendSMS sendSms = new SendSMS();
        String msgId = server + createSmsId();
        String icp = SMSTool.getPhoneType(phone);
        if (poitshow.equals("1") && backname.length() < 1) {
            backname = "";
        }

        msg = msg + backname;
        int success = sendSms.sendSingleMessage(msgId, msg, phone, server, icp);
        return success;
    }
}
