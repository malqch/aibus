package com.wntime.ec.common.util;

import com.wntime.ec.common.config.SmsInfo;

/**
 * @author wing
 * @create 2020-02-07 15:00
 */
public class SMSTool {
    public static String getPhoneType(String phone) {
        String dxStr = SmsInfo.DX;
        String ltStr = SmsInfo.LT;
        String ydStr = SmsInfo.YD;
        String phone3before = phone.substring(0, 3);
        String phone4before = phone.substring(0, 4);
        String sReturn = "YD";
        if (dxStr.indexOf(phone3before) > -1) {
            sReturn = "DX";
        }

        if (ltStr.indexOf(phone3before) > -1) {
            sReturn = "LT";
        }

        if (ydStr.indexOf(phone3before) > -1) {
            sReturn = "YD";
        }

        if (phone4before.equals("1703")) {
            sReturn = "YD";
        }

        if (phone4before.equals("1705")) {
            sReturn = "YD";
        }

        if (phone4before.equals("1706")) {
            sReturn = "YD";
        }

        if (phone4before.equals("1440")) {
            sReturn = "YD";
        }

        if (phone4before.equals("1707")) {
            sReturn = "LT";
        }

        if (phone4before.equals("1708")) {
            sReturn = "LT";
        }

        if (phone4before.equals("1709")) {
            sReturn = "LT";
        }

        if (phone4before.equals("1700")) {
            sReturn = "DX";
        }

        if (phone4before.equals("1701")) {
            sReturn = "DX";
        }

        if (phone4before.equals("1702")) {
            sReturn = "DX";
        }

        if (phone4before.equals("1349")) {
            sReturn = "DX";
        }

        if (phone4before.equals("1401")) {
            sReturn = "DX";
        }

        return sReturn;
    }
}
