package com.wntime.ec.module.schedule.task;

import com.wntime.ec.common.model.Constant;
import com.wntime.ec.module.schedule.service.IDsmUpdateService;
import com.wntime.ec.module.schedule.service.IUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wing
 * @create 2019-12-12 11:39
 * 定时更新基础数据
 */
@Component
@Slf4j
@Order(2)
public class UpdateTask {

    @Autowired
    IUpdateService updateService;

    @Autowired
    IDsmUpdateService dsmUpdateService;

    /**
     * 每隔10分钟更新一次基础数据
     */
    @Scheduled(initialDelay = 1000 * 60 * 5, fixedDelay = 1000 * 60 * 1)
    public void update() {
        try {
            updateService.update();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(Constant.ErrorCode.UPDATE_ERROR.getInfo(e.getMessage()));
        }
    }

    /**
     * 每隔1分钟检查一次vin
     */
    @Scheduled(initialDelay = 1000 * 20, fixedDelay = 1000 * 60 * 1)
    public void updateBusInfo() {
        try {
            updateService.updateBusInfo();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(Constant.ErrorCode.UPDATE_BUSINFO_ERROR.getInfo(e.getMessage()));
        }
    }

    /**
     * 每隔1秒检查一次车辆状态
     */
//    @Scheduled(initialDelay = 1000 * 30, fixedDelay = 1000)
    public void checkCarStatus() {
        try {
            updateService.checkCarStatus();
        } catch (Exception e) {
            log.error(Constant.ErrorCode.CHECK_CAR_STATUS_ERROR.getInfo(e.getMessage()));
        }
    }

    /**
     * 每隔2分钟 上传设备信息
     */
    @Scheduled(initialDelay = 1000 * 60, fixedDelay = 1000 * 60 * 2)
    public void uploadDevice() {
        try {
            updateService.uploadDevice();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(Constant.ErrorCode.UPLOAD_DEVICE_ERROR.getInfo(e.getMessage()));
        }
    }

    /**
     * 每隔10分钟 更新广告信息
     */
//    @Scheduled(initialDelay = 1000 * 60, fixedDelay = 1000 * 60 * 1)
    public void uploadAdvertise() {
        try {
            updateService.updateAdvertise();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(Constant.ErrorCode.UPDATE_ADVERTISE_ERROR.getInfo(e.getMessage()));
        }
    }

    /**
     * 每隔3秒 检查一次 ssh远程端口
     */
//    @Scheduled(initialDelay = 1000 * 60 * 1, fixedRate = 1000 * 3)
    public void checkSport() {
        try {
            updateService.checkSport();
        }catch (Exception e){
            e.printStackTrace();
            log.error(Constant.ErrorCode.CHECK_SPORT_ERROR.getInfo(e.getMessage()));
        }
    }

    /**
     * 每隔1分钟检查一次司机违规
     */
    @Scheduled(initialDelay = 1000 * 60 * 1, fixedDelay = 1000 * 60 * 1)
    public void getDSMInfo() {
        try {
            dsmUpdateService.getDSMInfo();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(Constant.ErrorCode.GET_DSM_INFO_ERROR.getInfo(e.getMessage()));
        }
    }

    /**
     * 每隔2s检查一次司机违规
     */
    @Scheduled(initialDelay = 1000 * 30 * 1, fixedDelay = 1000 * 2)
    public void getDSMSpeed() {
        try {
            dsmUpdateService.getDSMSpeed();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(Constant.ErrorCode.GET_DSM_INFO_ERROR.getInfo(e.getMessage()));
        }
    }
}
