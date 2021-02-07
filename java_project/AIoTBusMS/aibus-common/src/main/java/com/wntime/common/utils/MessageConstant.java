package com.wntime.common.utils;

import org.apache.http.HttpStatus;


/**
 * 返回信息
 */
public class MessageConstant {

    public static Message SC_NOT_FOUND = new Message(HttpStatus.SC_NOT_FOUND,"路径不存在，请检查路径是否正确");
    public static Message SC_BAD_REQUEST_PARAME_INVALID = new Message(HttpStatus.SC_BAD_REQUEST,"请求参数无效!{0}");

    public static Message SC_PRECONDITION_FAILED_CLUSTER_STATUS_INVALID = new Message(HttpStatus.SC_PRECONDITION_FAILED,"集群状态无效!{0}");
    public static Message SC_PRECONDITION_FAILED_NOT_FOUND_JOB_DETAIL = new Message(HttpStatus.SC_PRECONDITION_FAILED,"未查询到作业明细!");
    public static Message SC_PRECONDITION_FAILED_JOB_IS_EXECUTEING = new Message(HttpStatus.SC_PRECONDITION_FAILED,"作业正在执行中，请稍后再试!");

    public static Message SC_NAME_EXIST=new Message(HttpStatus.SC_PRECONDITION_FAILED,"该{0}已被其他用户创建,请重试");

    public static Message SYSTEM_NOT_DELETE=new Message(HttpStatus.SC_PRECONDITION_FAILED,"{0}不能删除");

    public static Message SYSTEM_RIGHT_SUB=new Message(HttpStatus.SC_PRECONDITION_FAILED,"请先删除子菜单");

    public static Message FORMAT_NOT_CORRECT=new Message(HttpStatus.SC_PRECONDITION_FAILED,"{0}格式不正确!请确认");

    public static Message USER_PASSWORD_ERROR=new Message(HttpStatus.SC_PRECONDITION_FAILED,"密码错误,请重试");

    public static Message IP_NOT_CORRECT=new Message(HttpStatus.SC_PRECONDITION_FAILED,"{0}");


    static class Message {
        private static final long serialVersionUID = -7720780054515670116L;
        private int code;
        private String message;

        public Message(int code,String message){
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
