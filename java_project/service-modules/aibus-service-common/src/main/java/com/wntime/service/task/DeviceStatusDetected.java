package com.wntime.service.task;

import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.wntime.common.utils.DateUtils;
import com.wntime.service.common.vo.ConfigParamVo;
import com.wntime.service.dao.InfoBusDeviceDao;
import com.wntime.service.dao.InfoConfigParamDao;
import com.wntime.service.dao.LogBusDeviceDao;
import com.wntime.service.entity.LogBusDeviceEntity;
import com.wntime.service.region.BusDevice;
import com.wntime.service.region.BusDeviceKey;
import com.wntime.service.repo.BusDeviceRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * 检测边缘计算状态
 * @author 79448
 * @date 2020/9/9 15:37
 */
@Component
public class DeviceStatusDetected {

    @Resource
    private LogBusDeviceDao logBusDeviceDao;
    @Resource
    private InfoBusDeviceDao infoBusDeviceDao;
    @Resource
    private InfoConfigParamDao infoConfigParamDao;
    @Resource
    private BusDeviceRepository busDeviceRepository;

//    @Scheduled(fixedRate = 1 * 1000 * 60 * 2)
//    public void detection(){
//        ConfigParamVo vo = infoConfigParamDao.queryConfigParamByGroupCode("ec_overtime", "ec_overtime");
//        long time= vo.getParamValue().longValue();
//        List<BusDeviceKey> ecList = infoBusDeviceDao.queryOverTimeOnlineEC(new Date(SystemClock.now() - time*1000));
//        if(ecList!=null && !ecList.isEmpty()){
//            infoBusDeviceDao.updateDeviceStatusOffline(ecList);
//
//            Iterable<BusDevice> deviceList = busDeviceRepository.findAllById(ecList);
//            Iterator<BusDevice> iter = deviceList.iterator();
//            while (iter.hasNext()){
//                BusDevice device = iter.next();
//                device.setDeviceStatus(0);
//                device.setUpdateTime(new Date());
//                LogBusDeviceEntity log=new LogBusDeviceEntity();
//                log.setBusDeviceId(device.getKey().getBusDeviceId());
//                log.setDeviceName(device.getDeviceName());
//                log.setDeviceDesc("离线");
//                log.setDeviceStatus(0);
//                log.setCreatedDate(DateUtils.getTimestamp());
//                logBusDeviceDao.insert(log);
//            }
//            busDeviceRepository.saveAll(deviceList);
//        }
//    }
}
