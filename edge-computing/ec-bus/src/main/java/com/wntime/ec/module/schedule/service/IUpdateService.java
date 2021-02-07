package com.wntime.ec.module.schedule.service;

import java.io.IOException;

/**
 * @author wing
 * @create 2020-08-28 15:08
 */
public interface IUpdateService {
    void update() throws Exception;

    void updateBusInfo();

    void checkCarStatus();

    void uploadDevice();

    void updateAdvertise();

    void checkSport() throws Exception;
}
