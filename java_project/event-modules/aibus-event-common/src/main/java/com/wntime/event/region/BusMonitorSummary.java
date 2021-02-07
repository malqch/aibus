package com.wntime.event.region;

import com.wntime.modules.gemfire.key.BusDateKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

/**
 * 车辆实时状态监测概览
 * @author 79448
 * @date 2020/8/25 18:26
 */
@Getter
@Setter
@Region("bus_monitor_summary")
public class BusMonitorSummary {

    @Id
    private BusDateKey key;


    /**
     * 重点人员检测次数
     */
    private int keyPersonDiscernCount;

    /**
     * 可燃气体告警次数
     */
    private int combustibleGasAlarmCount;

    /**
     * 干扰驾驶次数
     */
    private int interferenceDriveCount;

    /**
     * 识别乘客数
     */
    private int passengerCount;

    /**
     * 司机状态
     */
    private boolean driverCondition;

    /**
     * 体温异常数量
     */
    private int bodyTemperatureAbnormalCount;


    /**
     * 男性人数
     */
    private int maleCount;

    /**
     * 女性人数
     */
    private int femaleCount;

    /**
     * 儿童人数
     */
    private int childrenCount;

    /**
     * 成年人数
     */
    private int adultCount;

    /**
     * 老年人数
     */
    private int oldCount;

    /**
     * 下车总人数
     */
    private int downNum;

    /**
     * 上车总人数
     */
    private int boardNum;

    private Long busStationId;
    private String busStationName;
    /**
     * 本站下车人数
     */
    private int stationDownNum;
    /**
     * 本站上车
     */
    private int stationBoardNum;

    public void incrementKeyPersonDiscernCount(){
        this.keyPersonDiscernCount+=1;
    }
    public void incrementCombustibleGasAlarmCount(){
        this.combustibleGasAlarmCount+=1;
    }
    public void incrementInterferenceDriveCount(){
        this.interferenceDriveCount+=1;
    }

    public void incrementBodyTemperatureAbnormalCount(){
        this.bodyTemperatureAbnormalCount+=1;
    }

    public void addMaleCount(int maleCount){
        this.maleCount+=maleCount;
    }
    public void addFemaleCount(int femaleCount){
        this.femaleCount+=femaleCount;
    }

    public void addChildrenCount(int childrenCount){
        this.childrenCount+=childrenCount;
    }
    public void addAdultCount(int adultCount){
        this.adultCount+=adultCount;
    }
    public void addOldCount(int oldCount){
        this.oldCount+=oldCount;
    }

    public void incrementDownNum(int num){
        this.downNum+=num;
    }
    public BusMonitorSummary() {
    }

    public BusMonitorSummary(Long busId) {
        this.key =new BusDateKey(busId) ;
    }

    public BusMonitorSummary(BusDateKey key) {
        this.key = key;
    }
}
