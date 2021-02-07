package com.wntime.modules.gemfire.util;

/**
 * @author 79448
 * @date 2020/8/28 10:03
 */
public class DistanceUtil {
    private static final Double EARTH_RADIUS = 6378137.0;

    // 转换弧度
    private static double rad(double d) {
        return d * Math.PI / 180.00;
    }

    public static double getShortestDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return Math.round(s * 1000d) / 1000d;   //得到米数

    }



    public static double vincentyConstantB = 6356752.314245;
    public static double vincentyConstantF = 1 / 298.257223563;


    public static double distVincenty(double y1, double x1, double y2, double x2) {
        double b = vincentyConstantB;
        double f = vincentyConstantF;


        double L = rad(x2 - x1);
        double U1 = Math.atan((1 - f) * Math.tan(rad(y1)));
        double U2 = Math.atan((1 - f) * Math.tan(rad(y2)));
        double sinU1 = Math.sin(U1);
        double cosU1 = Math.cos(U1);
        double sinU2 = Math.sin(U2);
        double cosU2 = Math.cos(U2);
        double lambda = L;
        double lambdaP = 2 * Math.PI;
        double iterLimit = 20;


        double sinLambda = 0.0d;
        double cosLambda = 0.0d;
        double sinSigma = 0.0d;
        double cosSigma = 0.0d;
        double sigma = 0.0d;
        double alpha = 0.0d;
        double cosSqAlpha = 0.0d;
        double cos2SigmaM = 0.0d;
        double C = 0.0d;


        while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0) {
            sinLambda = Math.sin(lambda);
            cosLambda = Math.cos(lambda);
            sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda)
                    + (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda) * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));
            if (sinSigma == 0) {
                return 0;
            }
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
            sigma = Math.atan2(sinSigma, cosSigma);
            alpha = Math.asin(cosU1 * cosU2 * sinLambda / sinSigma);
            cosSqAlpha = Math.cos(alpha) * Math.cos(alpha);
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
            C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
            lambdaP = lambda;
            lambda = L + (1 - C) * f * Math.sin(alpha)
                    * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
        }
        if (iterLimit == 0) {
            return 0.0;
        }
        double uSq = cosSqAlpha * (EARTH_RADIUS * EARTH_RADIUS - b * b) / (b * b);
        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)
                - B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
        Double s = b * A * (sigma - deltaSigma);

        return Math.round(s * 10000d) / 10000d;
    }


}