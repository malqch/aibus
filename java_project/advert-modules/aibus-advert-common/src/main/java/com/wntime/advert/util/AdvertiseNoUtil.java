package com.wntime.advert.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author ysc
 * 2020/11/6 14:03
 */
public class AdvertiseNoUtil {

    private static final String ORDER_CODE_HEAD = "GT";
    private static final String ORDER_CODE_TAIL = "X";

    public static String generateNo(Long userId){
        long l = new Date().getTime() /1000;
        String s = userId.toString();
        //要是id长度小于3
        if(s.length() < 3){
            s = "000" + s;
        }
        return ORDER_CODE_HEAD + reverse(String.valueOf(l)) + s.substring(s.length() - 2) + getRandom(3) + ORDER_CODE_TAIL;
    }

    /**
     * 生成固定长度随机码
     * @param n    长度
     */
    private static long getRandom(long n) {
        long min = 1,max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        long rangeLong = (((long) (new Random().nextDouble() * (max - min)))) + min ;
        return rangeLong;
    }

    private static String reverse(String x){
        return new StringBuffer(x).reverse().toString();
    }

}
