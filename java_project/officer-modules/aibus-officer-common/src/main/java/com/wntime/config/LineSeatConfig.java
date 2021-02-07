package com.wntime.config;

import com.wntime.modules.officer.service.StudentLineSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class LineSeatConfig {

    @Autowired
    StudentLineSeatService lineSeatService;
    @Scheduled(cron = "0 0 0 * * ?")
    public void copyLineSeatID(){
        lineSeatService.copyLineSeatID();
    }
}

