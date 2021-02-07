package com.wntime.advert.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.advert.constant.AdvertiseConstant;
import com.wntime.advert.entity.OrderAdvertiseDeliveryEntity;
import com.wntime.advert.service.OrderAdvertiseDeliveryService;
import com.wntime.advert.util.FileHelper;
import com.wntime.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ysc
 * 2020/11/9 11:40
 */
@Component
public class CheckAdvertOfflineTask {

    @Autowired
    private OrderAdvertiseDeliveryService orderAdvertiseDeliveryService;
    @Autowired
    private FileHelper fileHelper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //每1分钟请求一次，将播放完的广告状态调整为offline
//    @Scheduled(fixedRate = 60 * 1000)
//    public void run(){
//        List<OrderAdvertiseDeliveryEntity> list = orderAdvertiseDeliveryService.list(new QueryWrapper<OrderAdvertiseDeliveryEntity>()
//                .eq("check_status", AdvertiseConstant.DELIVERY));
//
//        List<OrderAdvertiseDeliveryEntity> offlineList = list.stream()
//                .map(advertise ->{
//                    Date end = advertise.getDeliveryEnd();
//                    Date now = new Date();
//                    if(now.after(end)){
//                        advertise.setCheckStatus(AdvertiseConstant.OFFLINE);
//                        logger.info("清除用户"+ advertise.getCreatedBy()+"的广告"+ advertise.getAdvertiseNo()+"的素材");
//                        fileHelper.deleteDir(fileHelper.getRelativeDir(advertise.getCreatedBy(),advertise.getAdvertiseNo()));
//                    }
//                    return advertise;
//                })
//                .filter(advertise -> advertise.getCheckStatus() == AdvertiseConstant.OFFLINE)
//                .collect(Collectors.toList());
//        if(!offlineList.isEmpty()) {
//            orderAdvertiseDeliveryService.updateBatchById(offlineList);
//        }
//    }
}
