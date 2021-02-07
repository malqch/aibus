package com.wntime.ec.common.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wntime.ec.common.util.param.http.HttpRspParam;

import java.util.Map;

/**
 * @author wing
 * @create 2020-02-26 13:57
 */
public class RemoteAlleyesUtil {

    /**
     * 调用远程服务端 form表单提交
     */
    public static HttpRspParam form(String apiUrl, Map param, Class<? extends HttpRspParam> resultType) {
        HttpRspParam result = null;
        String rsp = null;
        try {
            rsp = HttpUtil.post(apiUrl, param);
            result = JSONUtil.toBean(rsp, resultType);
        } catch (Exception e) {
            result = new HttpRspParam(500, "call remote alleyes server error:" + apiUrl + ":" + param + rsp, e.getMessage());
        }
        return result;
    }

    /**
     * 调用远程服务端 body
     */
    public static HttpRspParam post(String apiUrl, Object param, Class<? extends HttpRspParam> resultType) {
        HttpRspParam result = null;
        String rsp = null;
        try {
            if(CommonUtil.isEmpty(param)){
                param = "";
            }
            rsp = HttpUtil.post(apiUrl, JSONUtil.toJsonStr(param));
            result = JSONUtil.toBean(rsp, resultType);
        } catch (Exception e) {
            result = new HttpRspParam(500, "call remote fsms server error:" + apiUrl + ":" + param + rsp, e.getMessage());
        }
        return result;
    }

    /**
     * 调用远程服务端 get
     */
    public static HttpRspParam get(String apiUrl, Class<? extends HttpRspParam> resultType) {
        HttpRspParam result = null;
        String rsp = null;
        try {
            rsp = HttpUtil.get(apiUrl);
            result = JSONUtil.toBean(rsp, resultType);
        } catch (Exception e) {
            result = new HttpRspParam(500, "call remote alleyes server error:" + apiUrl + ":" + rsp, e.getMessage());
        }
        return result;
    }

}
