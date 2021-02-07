package com.wntime.service.common.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.exception.RRException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ysc
 * 2020/9/21 10:05
 */

public class UniqueCheckHelper {

    /**
     * 唯一性校验 添加时用
     * @param service 校验用的service
     * @param paramName 校验参数名
     * @param paramValue 校验参数值
     * @param <T> 校验的对象类型
     * @return 唯一为true，有重复为false
     */
    public static <T> void assertIsUnique(IService<T> service, String paramName, String paramValue,String msg) {
        assertIsUnique(service, paramName, paramValue, null, null,msg);
    }

    public static <T> void assertIsUnique(IService<T> service, String paramName, String paramValue, String idField, Long id,String msg) {
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put(paramName,paramValue);
        assertIsUnique(service, paramMap, idField, id,msg);
    }

    public static <T> void assertIsUnique(IService<T> service,  Map<String,Object> paramMap,String msg) {
        assertIsUnique(service, paramMap, null, null,msg);
    }

    public static <T> void assertIsUnique(IService<T> service, Map<String,Object> paramMap, String idField, Long id,String msg) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (idField != null) {
            queryWrapper.ne(idField, id);
        }
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            queryWrapper.eq(entry.getKey(), entry.getValue());
        }
        queryWrapper.eq("is_deleted",0);
        T obj = service.getOne(queryWrapper);
        if(obj != null){
            throw new RRException(msg);
        }
    }

    /**
     * lambdaQueryWrapper由于版本有bug暂时用不了
     * @param service
     * @param obj
     * @param paramFuncs
     * @param idFunc
     * @param <T>
     * @return
     */
    public static <T> boolean assertIsUnique(IService<T> service, T obj, SFunction<T,Object>[] paramFuncs, SFunction<T,Object> idFunc) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        Object id = idFunc.apply(obj);
        if(id != null){
            lambdaQueryWrapper.ne(idFunc,id);
        }
        for (SFunction<T, Object> paramFunc : paramFuncs) {
            lambdaQueryWrapper.eq(paramFunc,paramFunc.apply(obj));
        }
        T t = service.getOne(lambdaQueryWrapper);
        return t == null;
    }

}
