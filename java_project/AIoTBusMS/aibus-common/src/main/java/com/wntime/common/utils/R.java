

package com.wntime.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.http.HttpStatus;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.*;

/**
 * 返回数据
 *
 */
@ApiModel(description="返回的响应对象")
public class R implements Serializable {
	private static final long serialVersionUID = -1243837890442299554L;

	@ApiModelProperty(value = "返回码",name = "code",notes = "0 代表成功")
	private int code;

	@ApiModelProperty(value = "返回消息",name = "message")
	private String message;

	@ApiModelProperty(value = "返回详细消息",name = "detailMessage")
	private String detailMessage;

	@ApiModelProperty(value = "返回数据",name = "data")
	private HashMap<String,Object> data;


	public R() {
		this.code = 0;
		this.message = "success";
		this.detailMessage = "";
		this.data = new HashMap<String, Object>();
	}

	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.code = code;
		r.message = msg;
		return r;
	}

	public static R error(MessageConstant.Message message, Object ... arguments) {
		R r = new R();
		r.code = message.getCode();
		r.message = MessageFormat.format(message.getMessage(),arguments);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.message = msg;
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.data.putAll(map);
		return r;
	}
	
	public static R ok() {
		R r = new R();
		r.message = "成功返回数据!";
		return r;
	}

	public R put(String key, Object value) {
		this.data.put(key,value);
		return this;
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

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	public HashMap<String, Object> getData() {
		return data;
	}

	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}


	public void setData(String data, Object Object) {
		this.data.put(data, Object);
	}
}
