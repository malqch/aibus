

package com.wntime.common.utils;

/**
 * 常量
 *
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段m
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";

    /**
     *系统菜单数量
     */
    public static final int SYSTEM_RIGHT_COUNT = 35;
    /**
     *主机模板excel参数数量
     */
    public static final int HOST_INFO_MODEL_PARAM_COUNT = 9;
    /**
     * 历史指标最大查询间隔时间
     */
    public static final long INTERVAL_TIME = 7 * 1000 * 60 * 60 * 24;
    /**
     * 查询集群指标使用的id分隔符
     */
    public static final String SEPARATOR = ",";

    /**
     * 文件分隔符
     */
    public static final String fileSeparator = "/";

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;

    /**
     * 菜单类型
     *

     */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 设备在线状态
     */
    public enum OnlineStatus{

        ONLINE(0),OFFLINE(1),UNKNOWN(-1);

        private int value;
        OnlineStatus(int value) {
            this.value=value;
        }
        public int getValue(){
            return value;
        }
    }

    /**
     * 是否可用
     */
    public enum Enabled{
        ENABLE(1),DISABLE(0);

        private int value;
        Enabled(int value) {
            this.value=value;
        }
        public int getValue(){
            return value;
        }
    }

    /**
     * 是否删除
     */
    public enum Deleted{
        UNDELETED(0),DELETED(1);
        private int value;
        Deleted(int value) {
            this.value=value;
        }
        public int getValue(){
            return value;
        }
    }


    /**
     * 文件类型
     */
    public enum FileType{
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

        public static boolean isNameMatchCode(Long code, String name){
            for(FileType fileType : FileType.values()){
                if(fileType.getCode() == code && fileType.getName().equalsIgnoreCase(name)){
                    return true;
                }
            }
            return false;
        }
    }
    /**
     * 定时任务状态
     *
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum EventTargetGroup{
        LINK("link"),VALUE("value"),CHAR("char"),IMAGE("image");
        private String name;
        EventTargetGroup(String name){
            this.name=name;
        }

        public String getName() {
            return name;
        }
    }



    public enum CarStatus{
        normal(0,"正常"),fault(1,"故障告警"),wait(2,"待修"),maintenance(3,"维修中");
        private int code;
        private String desc;

        CarStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 线路方向
     *
     */
    public enum LineDirection {
        /**
         * 正常
         */
        UP("1"),
        /**
         * 暂停
         */
        DOWN("2");

        private String value;

        LineDirection(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 行程状态
     *
     */
    public enum ItineraryStatus {
        /**
         * 未开始
         */
        NOT_START("1","未开始"),
        /**
         * 行驶中
         */
        ON_THE_ROAD ("2","行驶中"),
        /**
         * 已完成
         */
        DONE ("3","已完成");

        private String code;
        private String name;

        ItineraryStatus(String code,String name) {
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
}
