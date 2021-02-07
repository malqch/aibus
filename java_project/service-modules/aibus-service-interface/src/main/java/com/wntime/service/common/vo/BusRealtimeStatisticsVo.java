package com.wntime.service.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/26 12:01
 */
@ApiModel
@Setter
@Getter
public class BusRealtimeStatisticsVo {

    @ApiModelProperty(value = "公交车id",required = true,dataType = "long")
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long busId;
    @ApiModelProperty(value = "车牌",required = true)
    private String plateCode;
    @ApiModelProperty(value = "vin码",required = true)
    private String vinCode;
    @ApiModelProperty(value = "区域",required = true)
    private String areaName;
    @ApiModelProperty(value = "公交公司名称",required = true)
    private String companyName;
    @ApiModelProperty(value = "公交公司线路",required = true)
    private String companyLineName;

    /**
     * 电量百分比
     * electricityQuantity
     */
    @ApiModelProperty(value = "电量百分比",dataType = "double",required = true)
    private double batteryEnergyRatio;

    /**
     * 电机状态
     */
    @ApiModelProperty(value = "电机状态",dataType = "int",required = true)
    private int busMotorStatus;

    /**
     * 乘客满座率
     */
    @ApiModelProperty(value = "乘客满座率",dataType = "double",required = true)
    private double fullSeatRate;

    @ApiModelProperty(value = "车速",dataType = "double",required = true)
    private double busSpeed;
    /**
     * 重点人员检测次数
     */
    @ApiModelProperty(value = "AI人脸重点人群识别人次",required = true)
    private MonitorItemVo keyPersonDiscernCount;

    @ApiModelProperty(value = "可燃物告警次数",required = true)
    private MonitorItemVo gas;

    @ApiModelProperty(value = "AI人脸识别人次",required = true)
    private MonitorItemVo aiFace;

    @ApiModelProperty(value = "司机走神",required = true)
    private MonitorItemVo distracted;

    @ApiModelProperty(value = "疲劳驾驶",required = true)
    private MonitorItemVo fatigueDriver;

    @ApiModelProperty(value = "乘客体温异常数",required = true)
    private MonitorItemVo temperatureAbnormal;


    public BusRealtimeStatisticsVo(Long busId) {
        this.busId = busId;
        this.keyPersonDiscernCount=new MonitorItemVo("AI人脸重点人群识别人次",0);
        this.gas=new MonitorItemVo("可燃物告警次数",0);
        this.aiFace=new MonitorItemVo("AI人脸识别",0);
        this.distracted = new MonitorItemVo("司机走神",0);
        this.fatigueDriver = new MonitorItemVo("疲劳驾驶",0);
        this.temperatureAbnormal=new MonitorItemVo("乘客体温异常",0);
    }
}
