package com.wntime.event.controller;

import com.wntime.common.utils.R;
import com.wntime.common.validator.Assert;
import com.wntime.event.service.LogEventDetailService;
import com.wntime.service.common.util.LineChartUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ysc
 * 2020/8/28 10:21
 */
@Api(value = "环境数据分析接口",tags = {"环境数据分析接口"})
@RequestMapping("/event/env")
@RestController
public class EnvEventStatController {

    @Autowired
    private LogEventDetailService logEventDetailService;

    @ApiOperation(value = "公交车站温度统计", notes = "", httpMethod = "GET")
    @GetMapping("/temperature/stat")
    public R getTemperatureByStationId(@RequestParam Long busStationId,
                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date startTime,
                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime){
        List<Map<String,Object>> list = logEventDetailService.getTemperatureByStationId(busStationId,startTime,endTime);
        Assert.isEmpty(list,"没有数据");
        return R.ok().put("data", LineChartUtil.creatLineChartDataFromMap(list,"time_range","data_value"));
    }

    @ApiOperation(value = "公交车站湿度统计", notes = "", httpMethod = "GET")
    @GetMapping("/humidity/stat")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public R getHumidityByStationId(@RequestParam Long busStationId,
                                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date startTime,
                                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime){
        List<Map<String,Object>> list = logEventDetailService.getHumidityByStationId(busStationId,startTime,endTime);
        Assert.isEmpty(list,"没有数据");
        return R.ok().put("data",LineChartUtil.creatLineChartDataFromMap(list,"time_range","data_value"));
    }
    @ApiOperation(value = "公交车站pm2.5统计", notes = "", httpMethod = "GET")
    @GetMapping("/pm_2_5/stat")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public R getPm_2_5ByStationId(@RequestParam Long busStationId,
                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date startTime,
                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime){
        List<Map<String,Object>> list = logEventDetailService.getPm_2_5ByStationId(busStationId,startTime,endTime);
        Assert.isEmpty(list,"没有数据");
        return R.ok().put("data",LineChartUtil.creatLineChartDataFromMap(list,"time_range","data_value"));
    }
}
