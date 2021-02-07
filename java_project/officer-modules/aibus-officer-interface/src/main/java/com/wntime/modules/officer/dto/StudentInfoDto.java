package com.wntime.modules.officer.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StudentInfoDto {
    private Long id;

    /**
     *
     */
    private Long basicId;

    /**
     *
     */
    private String studentName;

    /**
     *
     */
    private String studentTakePhoto;

    /**
     *
     */
    private String studentSex;

    /**
     *
     */
    private Integer studentAge;

    /**
     *
     */
    private Long upCompanyLineId;

    /**
     *
     */
    private String upCompanyLineName;

    /**
     *
     */
    private Long offCompanyLineId;

    /**
     *
     */
    private String offCompanyLineName;

    /**
     *
     */
    private Long seatNo;

    /**
     *
     */
    private Long upStationId;

    /**
     *
     */
    private String upBusStationName;

    /**
     *
     */
    private Long offStationId;

    /**
     *
     */
    private String offBusStationName;

    /**
     *
     */
    private String guardianName;

    /**
     *
     */
    private String relationStudent;

    /**
     *
     */
    private String mobileNumber;

    /**
     *
     */
    private Long guardianId;

    /**
     *
     */
    private Long classesId;

    /**
     *
     */
    private Long schoolId;

    /**
     *
     */
    private Date createDt;

    /**
     *
     */
    private Long createUserId;

    /**
     *
     */
    private Long modifyUserId;

    /**
     *
     */
    private Date modifyDt;
    private String guardianTakePhoto;

    private String studentIdNo;
    private String guardianIdNo;
}
