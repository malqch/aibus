package com.wntime.customer.config;

import com.wntime.common.utils.Constant;
import com.wntime.customer.service.ItineraryReceiptService;
import com.wntime.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
public class ScheduledConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ItineraryReceiptService itineraryReceiptService;

    /**
     * 每分钟执行一次
     * 给当天已结束的程序生成对应的下一个行程
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void produceItinerary(){
        logger.info("给当天已结束的行程生成对应的下一个行程....");
        itineraryReceiptService.produceItinerary();
    }

    /**
     * 每天夜里执行
     * 按营运计划生成未开始的行程
     */
    @Scheduled(cron = "0 0 01 * * ?")
    public void produceItinerary1(){
        logger.info("按营运计划生成未开始的行程....");
        itineraryReceiptService.produceItinerary(DateUtil.date2String(new Date(),DateUtil.yyyyMMdd)
                , Constant.LineDirection.UP.getValue(),null);
    }

}
