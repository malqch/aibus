package com.wntime.ec.common.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.wntime.ec.common.model.Constant;
import com.wntime.ec.module.schedule.vo.GeoconvRspWarpVo;
import com.wntime.ec.module.sys.vo.InfoLineStationQryRspVo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GpsUtil {

    /**
     * @Author Buxl
     * @Description 根据Gps原始坐标获取百度偏移后的坐标
     * @Date 11:24 2021/1/27
     * @Param geoconv_url: 百度url
     *          longitude: gps获取的经度
     *          latitude: gps获取的纬度
     * @return java.util.Map<java.lang.String, java.lang.Double>
     **/
    public static Map<String,Double> getBaiduXY(String geoconv_url,String longitude,String latitude){
        String url = geoconv_url + "&coords=" + longitude + "," + latitude + "&from=1&to=5&output=json";
        String geoconv = HttpRequest.get(url).timeout(-1).execute().body();
        GeoconvRspWarpVo geoconvRspWarpVo = JSONUtil.toBean(geoconv, GeoconvRspWarpVo.class);
        Map<String,Double> baiduXY = new HashMap<String,Double>();
        if(geoconvRspWarpVo.getStatus() == 0){
            baiduXY.put("longitude",geoconvRspWarpVo.getResult().get(0).getX());
            baiduXY.put("latitude",geoconvRspWarpVo.getResult().get(0).getY());
            return baiduXY;
        }
        return null;
    }
    
    /**
     * @Author Buxl
     * @Description 根据坐标位置获取车站id
     * @Date 11:33 2021/1/27
     * @Param [longitude, latitude]
     * @return java.lang.Long
     **/
    public static Long getBusStationIdByXY(double longitude, double latitude){
        Map<Long, List<InfoLineStationQryRspVo>> lineStationMap = Constant.lineStationMap;
        for(Map.Entry<Long,List<InfoLineStationQryRspVo>> entry : lineStationMap.entrySet()){
            List<InfoLineStationQryRspVo> lineStationList = entry.getValue();
            for(InfoLineStationQryRspVo lineStation : lineStationList){
                BigDecimal busStationLongitude = lineStation.getBusStationLongitude();
                BigDecimal busStationLatitude = lineStation.getBusStationLatitude();
                Integer busStationDeviation = lineStation.getBusStationDeviation();
                double distance = DistanceUtil.getShortestDistance(longitude, latitude, busStationLongitude.doubleValue(), busStationLatitude.doubleValue());
                if(distance <= busStationDeviation){
                    return lineStation.getBusStationId();
                }
            }
        }
        return null;
    }
}
