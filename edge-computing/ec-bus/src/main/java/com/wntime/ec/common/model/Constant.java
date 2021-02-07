package com.wntime.ec.common.model;

import com.wntime.ec.module.schedule.vo.CarStatusInfo;
import com.wntime.ec.module.sys.entity.LocalInfoBus;
import com.wntime.ec.module.sys.vo.InfoLineStationQryRspVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wing
 * 常量
 */
public class Constant {
    public static boolean isEntered = false;                    //是否已获取车辆信息（包括vin）
    public static LocalInfoBus localInfoBus = null;             //车辆信息
    public static CarStatusInfo CurrentCarStatusInfo = null;    //车辆状态
    public static int sshSport = 0;                             //ssh通道打
    public static Long lastBusStationId;    //正向
    public static Long lastBusStationId2;//反向
    public static Long currentBusStationId;
    public static Long currentLineStationId;
    public static Long nextLineStationId;
    public static Long currentCompanyLineId;
    public static Map<Long, List<InfoLineStationQryRspVo>> lineStationMap = new HashMap<>();
    public static Map<String, Integer> advertiseTagCountMap = new HashMap();           //广告标签人数

    static {
        resetAdvertiseTagCount();
    }

    public static void addAdvertiseTagCount(String tagName) {
        Integer count = Constant.advertiseTagCountMap.get(tagName);
        if (count != null) {
            Constant.advertiseTagCountMap.put(tagName, count + 1);
        }
    }

    public static void resetAdvertiseTagCount() {
        advertiseTagCountMap.put("male", 0);   //女性
        advertiseTagCountMap.put("female", 0); //男性
        advertiseTagCountMap.put("1", 0);  //儿童
        advertiseTagCountMap.put("2", 0);  //成年
        advertiseTagCountMap.put("3", 0);  //老年
    }


    public static String DSM_SESSION = null;

    public static Long DSM_SESSION_TIME = null;     //dsm系统session有效期假设为10分钟更新一次

    public static String DSM_ALARM_LAST_ENDTIME = null;    //dsm系统告警上次结束时间

    //本地数据库保存的表
    public enum H2Table {
        local_info_bus("local_info_bus", "公交车表"),
        info_bus_device("info_bus_device", "AI设备表"),
        info_collect_event("info_collect_event", "事件采集表"),
        info_collect_fault("info_collect_fault", "故障采集表"),
        info_device_type("info_device_type", "设备类型"),
        info_event_extend("info_event_extend", "事件拓展表"),
        info_event_level("info_event_level", "事件级别表"),
        info_event_target("info_event_target", "事件标签表"),
        info_event_type("info_event_type", "事件类型表"),
        info_fault_extend("info_fault_extend", "故障扩展表"),
        info_fault_level("info_fault_level", "故障级别表"),
        info_fault_target("info_fault_target", "故障标签表"),
        info_fault_type("info_fault_type", "故障类型表"),
        info_bus_station("info_bus_station", "公交车站表"),
        info_line_station("info_line_station", "线路车站表"),
        plan_bus_service("plan_bus_service", "营运计划表"),
        info_advertise_position("info_advertise_position", "广告位表"),
        info_advertise_target("info_advertise_target", "广告标签表"),
        order_advertise_delivery("order_advertise_delivery", "广告投放单"),
        order_advertise_attach("order_advertise_attach", "广告附件表"),
        order_delivery_area("order_delivery_area", "投放范围表"),
        order_delivery_target("order_delivery_target", "投放标签表"),
        info_update_list("info_update_list", "更新信息表");

        private String code;
        private String name;

        H2Table(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    //更新类型
    public enum UpdateType {
        BASE_DATA("1", "基础数据更新"),       //基础数据
        EC("2", "边缘计算更新"),              //边缘计算
        PERSONNEL_DATA("3", "人员信息更新");          //人员信息

        private String code;
        private String name;

        UpdateType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 小米设备采集数据，数值转换
     */
    public enum MiData {
        OPEN("open", 0), CLOSE("close", 1),     //门窗
        ON("on", 0), OFF("off", 1), UNKNOWN("unknown", 2)       //插座
        ;

        private String code;
        private Integer value;

        MiData(String code, Integer value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public Integer getValue() {
            return value;
        }

        public static Integer getValueByCode(String code) {
            for (MiData miData : MiData.values()) {
                if (miData.getCode().equalsIgnoreCase(code.trim())) {
                    return miData.getValue();
                }
            }
            return null;
        }
    }

    //设备厂商
    public enum ManufactoryType {
        MI("mi", "小米"), ALLEYES("alleyes", "百目");

        private String code;
        private String name;

        ManufactoryType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 是、否
     */
    public enum YN {

        Y("Y", "是"),
        N("N", "否");

        private String code;
        private String name;

        YN(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 真、假
     */
    public enum TrueFalse {
        TRUE(true, "是"),
        FALSE(false, "否");

        private boolean code;
        private String name;

        TrueFalse(boolean code, String name) {
            this.code = code;
            this.name = name;
        }

        public boolean getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 图片类型
     */
    public enum ImgType {
        JPG(1, "jpg"), JPEG(2, "jpeg"), PNG(3, "png"), GIF(4, "gif"), BMP(5, "bmp");

        private Integer code;
        private String name;

        ImgType(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static boolean contails(String name) {
            for (ImgType imgType : ImgType.values()) {
                if (imgType.getName().equalsIgnoreCase(name)) {
                    return true;
                }
            }
            return false;
        }

        public static boolean isNameMatchCode(Integer code, String name) {
            for (ImgType imgType : ImgType.values()) {
                if (imgType.getCode() == code && imgType.getName().equalsIgnoreCase(name)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 文件类型
     */
    public enum FileType {
        JDK(1L, "jdk"), GEODE(2L, "geode"), JAR(3L, "jar"),
        ZIP(4L, "zip"), TAR(4L, "tar"), TARGZ(4L, "tar.gz"), TGZ(4L, "tgz"), GZ(4L, "gz"), BZ2(4L, "bz2"), RAR(4L, "rar"),
        XML(5L, "xml"), PROPERTIES(5L, "properties");

        private Long code;
        private String name;

        FileType(Long code, String name) {
            this.code = code;
            this.name = name;
        }

        public Long getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static boolean isNameMatchCode(Long code, String name) {
            for (FileType fileType : FileType.values()) {
                if (fileType.getCode() == code && fileType.getName().equalsIgnoreCase(name)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum DeviceDesc {
        f_o_native_video("f_o_native_video", "前外摄像头"),
        b_o_native_video("b_o_native_video", "后外摄像头"),
        f_i_native_video("f_i_native_video", "前内摄像头"),
        b_i_native_video("b_i_native_video", "后内摄像头"),
        f_g_native_video("f_g_native_video", "前门摄像头"),
        b_g_native_video("b_g_native_video", "后门摄像头"),
        f_g_temperature_video("f_g_temperature_video", "测温摄像头"),
        f_d_dsm_driver("f_d_dsm_driver", "DSM摄像头"),
        b_o_iot("b_o_iot", "环境测量仪"),
        m_i_native_nvr("m_i_native_nvr", "NVR"),
        m_i_native_5g("m_i_native_5g", "5g路由器"),
        m_i_native_4g("m_i_native_4g", "4g路由器"),
        m_i_native_dangerous("m_i_native_dangerous", "危险品"),
        m_i_native_ec("m_i_native_ec", "边缘计算");

        private String code;
        private String name;

        DeviceDesc(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public static String getNameByCode(String code) {
            for (DeviceDesc obj : DeviceDesc.values()) {
                if (obj.getCode().equalsIgnoreCase(code.trim())) {
                    return obj.getName();
                }
            }
            return null;
        }
    }

    public enum ErrorCode {
        //系统错误
        INIT_ERROR(1000, "ec init failed"),
        BUS_ISNULL(1001, "local_bus_info not init"),
        UNKNOWN_EXCEPTION(1002, "unknown exception"),
        //校验错误

        ADVERTISE_TAG_EVENT_ERROR(2000, "unknown exception"),


        //与服务端对接错误 - 事件
        UPLOAD_EVENT_ERROR(3100, "upload event error"),
        UPLOAD_DEVICE_STATUS_ERROR(3101, "upload device status error"),
        UPLOAD_FAULT_ERROR(3102, "upload fault error"),
        CHECK_SPORT_ERROR(3103, "scheduled get ssh sport error"),
        UPLOAD_AD_PLAYLOG_ERROR(3104, "upload ad play log error"),

        //与服务端对接错误 - 基础信息
        UPDATE_ERROR(3200, "update error"),
        UPDATE_BASE_DATA_ERROR(3201, "update base data error"),
        INIT_BUSINFO_ERROR(3202, "init bus info error"),
        UPDATE_BUSINFO_ERROR(3203, "update bus info error"),
        CHECK_ISENTERED_ERROR(3204, "check isEntered error"),
        UPLOAD_DEVICE_ERROR(3205, "upload device error"),
        CHECK_CAR_STATUS_ERROR(3206, "check car status error"),
        UPLOAD_CAR_STATUS_ERROR(3207, "upload car status error"),
        GET_DSM_INFO_ERROR(3208, "get dsm info error"),
        UPDATE_ADVERTISE_ERROR(3209, "update advertise error"),

        //与百目对接
        GET_CAR_STATUS_ERROR(3301, "get alleyes car status info error -> /v1/alleyes/status/car "),

        //与DSM系统对接
        DSM_ALARM_TYPE_NOT_EXISTS_ERROR(3401, "dsm_alarm_type_not_exists"),
        DSM_IMAGE_NOT_EXISTS_ERROR(3402, "dsm_image_not_exists");

        private int code;
        private String name;

        ErrorCode(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getInfo(String info) {
            return "{" + code + ":" + name + ":" + info + "}";
        }
    }

    public enum Bus {
        ONE(1, "商家表ID");

        private Integer localId;
        private String name;

        Bus(Integer localId, String name) {
            this.localId = localId;
            this.name = name;
        }

        public Integer getLocalId() {
            return localId;
        }
    }

}
