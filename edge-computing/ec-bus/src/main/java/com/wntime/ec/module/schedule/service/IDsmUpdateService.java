package com.wntime.ec.module.schedule.service;

public interface IDsmUpdateService {
    /**
     * @Author Buxl
     * @Description 从dsm获取司机违规信息
     * @Date 11:39 2021/1/27
     * @Param []
     * @return void
     **/
    void getDSMInfo();

    /**
     * @Author Buxl
     * @Description 从dsm获取车速
     * @Date 11:39 2021/1/27
     * @Param []
     * @return void
     **/
    void getDSMSpeed();
}
