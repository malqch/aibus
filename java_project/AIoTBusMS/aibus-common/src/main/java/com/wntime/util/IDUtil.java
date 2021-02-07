package com.wntime.util;

public class IDUtil {

    private static byte[] lock = new byte[0];

    // 位数，默认是5位
    private final static long w = 100000;

    /**
     * 生成数字时间戳加随机数,默认随机数5位
     * @return 格式：1589509988870-11267
     */
    public static String createTimeID() {
        long r = 0;
        synchronized (lock) {
            r = (long) (Math.random() * w);
        }
        return System.currentTimeMillis() + "-" + String.valueOf(r);
    }

    public static String randomPassword() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }

    public static void main(String[] args) {
        for (int j = 0; j < 2000; j++) {
            System.out.println(createTimeID());
        }
    }

}
