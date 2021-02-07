package com.wntime.ec.common.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wntime.ec.common.util.param.http.HttpRspParam;

/**
 * @author wing
 * @create 2019-12-12 18:36
 */
public class RemoteHaUtil {

    /**
     * 调用Go
     */
    public static HttpRspParam post(String apiUrl, Object param, Class<? extends HttpRspParam> resultType) {
        HttpRspParam result = null;
        try {
            if(CommonUtil.isEmpty(param)){
                param = "";
            }
            String resp = HttpUtil.post(apiUrl, JSONUtil.toJsonStr(param));
            result = JSONUtil.toBean(resp, resultType);

        } catch (Exception e) {
            result = new HttpRspParam(500, "call HA error", e.getMessage());
        }
        return result;
    }
}
