package com.wntime.modules.officer.dto;

import io.swagger.models.auth.In;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

@Data
public class StudentInfo {
    /**
     * @desc 姓名
     */
    @NotBlank(message = "学生姓名不能为空")
    private String fullName;
    /**
     * @desc 性别
     */
    private String sex;

    /**
     * @desc 年龄
     */
    private Integer age;

    /**
     * @desc 身份证号
     */
    private String idNo;

    //公交线路名称
    private String companyLineName;

    @Min(1)
    private Integer seatNo; // 坐位号

    //上车站名
    @NotBlank(message = "上车站名不能为空")
    private String upBusStationName;

    //下车站名
    @NotBlank(message = "下车站名不能为空")
    private String offBusStationName;

    // 监护人姓名
    @NotBlank(message = "监护人姓名不能为空")
    private String GuardianName;

    // 监护人身份证号
    @NotBlank(message = "监护人身份证号不能为空")
    private String GuardianIdNo;

    /**
     * @desc 与学生关系
     */
    private String relationStudent;

    /**
     * @desc 手机号
     */
    @NotBlank(message = "监护人手机号不能为空")
    private String mobileNumber;

    public StudentInfo(Map<String, Object> map) {
        this.fullName = map.get("学生姓名").toString();
        this.sex = map.get("学生性别").toString();
        try {
            this.age = StringUtils.isNotEmpty(map.get("学生年龄").toString()) ? Integer.parseInt(map.get("学生年龄").toString()) : null;
        }catch (Exception e){
            e.printStackTrace();
        }
        this.idNo= map.get("学生身份证号").toString();
        this.companyLineName=map.get("校车线路").toString();
        this.seatNo = StringUtils.isNotEmpty(map.get("校车座位号").toString()) ? Integer.parseInt(map.get("校车座位号").toString()) : null;
        this.upBusStationName=map.get("上学接车站点").toString();
        this.offBusStationName=map.get("放学下车站点").toString();
        this.GuardianName=map.get("监护人姓名").toString();
        this.GuardianIdNo=map.get("监护人身份证号").toString();
        this.relationStudent=map.get("监护人与学生关系").toString();
        this.mobileNumber=map.get("监护人手机号").toString();
    }
}
