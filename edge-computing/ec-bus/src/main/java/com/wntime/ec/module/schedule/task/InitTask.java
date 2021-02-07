package com.wntime.ec.module.schedule.task;

import com.wntime.ec.common.model.Constant;
import com.wntime.ec.module.schedule.service.IInitService;
import com.wntime.ec.module.schedule.service.IUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wing
 * @create 2019-12-12 18:43
 */
@Component
@Slf4j
@Order(1)
public class InitTask implements ApplicationRunner {

    @Autowired
    IInitService initService;

    @Autowired
    IUpdateService updateService;


    /**
     * 系统启动，初始化数据
     */
    @Override
    public void run(ApplicationArguments args) {
        log.info("EdgeComputing init start ...");
//        String[] sourceArgs = args.getSourceArgs();
//        for (String arg : sourceArgs) {
//            System.out.print(arg + " ");
//        }

        try {
            //初始化数据库表
            initService.initTable();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(Constant.ErrorCode.INIT_ERROR.getInfo(e.getMessage()));
            throw new RuntimeException("init table failed");
        }

        try{
            //获取vin、车辆信息
            updateService.updateBusInfo();
        }catch (Exception e){
            e.printStackTrace();
            log.error(Constant.ErrorCode.INIT_BUSINFO_ERROR.getInfo(e.getMessage()));
        }

        try {
            //更新基础数据
            updateService.update();
        } catch (Exception e){
            e.printStackTrace();
            log.error(Constant.ErrorCode.UPDATE_BASE_DATA_ERROR.getInfo(e.getMessage()));
        }

        try {
            updateService.uploadDevice();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(Constant.ErrorCode.UPLOAD_DEVICE_ERROR.getInfo(e.getMessage()));
        }

//        try {
//            updateService.checkCarStatus();
//        } catch (Exception e) {
//            log.error(Constant.ErrorCode.CHECK_CAR_STATUS_ERROR.getInfo(e.getMessage()));
//        }

//        try {
//            updateService.updateAdvertise();
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(Constant.ErrorCode.UPDATE_ADVERTISE_ERROR.getInfo(e.getMessage()));
//        }

        log.info("EdgeComputing init finished ...");
    }
}
