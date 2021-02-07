package com.wntime.common.utils;

import com.wntime.entity.AdminParametersEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheUtils {

    /**
     * 系统参数
     *  {param_type:{param_code:AdminParametersEntity}}
     */
    public static Map<String, Map<String, AdminParametersEntity>> adminParametersMap = new HashMap<String, Map<String, AdminParametersEntity>>();

    public static List<Map> softWareTypeList = new ArrayList<>();
}
