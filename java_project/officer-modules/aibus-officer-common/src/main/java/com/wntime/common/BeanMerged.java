package com.wntime.common;

import cn.hutool.core.bean.BeanUtil;

import java.util.Map;

public class BeanMerged {
    public static Map<String,Object> merge (Object master,Object slave){
        Map<String, Object> map = BeanUtil.beanToMap(master);
        Map<String, Object> map1 = BeanUtil.beanToMap(slave);
        map1.putAll(map);
        return map1;
    }
}
