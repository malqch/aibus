package com.wntime.service.common.region;

import com.wntime.modules.gemfire.key.BusDateKey;
import com.wntime.service.common.vo.MonitorItemVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 公交车实时监控统计
 * @author 79448
 * @date 2020/8/26 17:17
 */
@Setter
@Getter
@Region("bus_realtime_monitor_statistics")
public class BusRealtimeMonitorStatistics {

    @Id
    private BusDateKey key;

    /**
     * 乘客满座率
     */
    private double fullSeatRate;

    /**
     * 荷载人数
     */
    private int peopleLoadCount;
    /**
     * 乘客数量
     */
    private int passengerCount;
    /**
     * 重点人员检测次数
     */
    private MonitorItemVo keyPersonDiscernCount;

    private MonitorItemVo gas;

    private MonitorItemVo aiFace;

    @ApiModelProperty(value = "司机走神",required = true)
    private MonitorItemVo distracted;

    @ApiModelProperty(value = "疲劳驾驶",required = true)
    private MonitorItemVo fatigueDriver;

    private MonitorItemVo temperatureAbnormal;


    public void setKeyPersonDiscernCount(int keyPersonDiscernCount){
       this.keyPersonDiscernCount=new MonitorItemVo("AI人脸重点人群识别人次",keyPersonDiscernCount);
    }

    public void setGas (int gasCount){
        this.gas=new MonitorItemVo("可燃物告警次数",gasCount);
    }
    public void setAiFace(int aiFaceCount){
        this.aiFace=new MonitorItemVo("AI人脸识别",aiFaceCount);
    }

    public void setDistracted(int count) {
        this.distracted = new MonitorItemVo("司机走神",0);
    }

    public void setFatigueDriver(int count) {
        this.fatigueDriver = new MonitorItemVo("疲劳驾驶",0);
    }

    public void incrementDistracted(){
        this.distracted.incrementCount();
    }

    public void incrementFatigueDriver(){
        this.fatigueDriver.incrementCount();
    }

    public void setTemperatureAbnormal(int count){
        this.temperatureAbnormal=new MonitorItemVo("乘客体温异常",count);
    }

    public double getFullSeatRate() {
        if(peopleLoadCount==0){
            return 0.0;
        }else {
            return new BigDecimal(passengerCount).divide(new BigDecimal(peopleLoadCount),2, RoundingMode.HALF_EVEN).doubleValue();
        }
    }

    public BusRealtimeMonitorStatistics() {
        setKeyPersonDiscernCount(0);
        setGas(0);
        setAiFace(0);
        setDistracted(0);
        setFatigueDriver(0);
        setTemperatureAbnormal(0);
    }

    public BusRealtimeMonitorStatistics(Long busId) {
        this.key = new BusDateKey(busId);
        setKeyPersonDiscernCount(0);
        setGas(0);
        setAiFace(0);
        setDistracted(0);
        setFatigueDriver(0);
        setTemperatureAbnormal(0);
    }

    public BusRealtimeMonitorStatistics(BusDateKey key) {
        this.key = key;
        setKeyPersonDiscernCount(0);
        setGas(0);
        setAiFace(0);
        setDistracted(0);
        setFatigueDriver(0);
        setTemperatureAbnormal(0);
    }
}
