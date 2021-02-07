package com.wntime.common.task;

import com.wntime.common.utils.R;

import java.util.Map;

/**
 * @author wing
 * @create 2019-10-24 16:05
 */
public interface ITaskBean {

    /**
     * 执行任务
     * @param paramsMap
     * @return
     */
    R run(Map<String, Object> paramsMap);

    /**
     * 启动任务
     *
     * @return
     */
    default R start(Map<String, Object> paramsMap){
        return R.ok();
    };

    /**
     * 停止任务
     *
     * @return
     */
    default R stop(){
        return R.ok();
    };

    /**
     * 重启任务
     *
     * @return
     */
    default R restart(){
        return R.ok();
    }

    /**
     * 获取任务状态
     *
     * @return
     */
    default R status(){
        return R.ok();
    }
}
