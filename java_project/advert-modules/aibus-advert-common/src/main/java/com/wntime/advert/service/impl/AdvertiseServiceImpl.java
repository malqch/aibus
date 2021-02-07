package com.wntime.advert.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.advert.config.AdvertFileConfig;
import com.wntime.advert.constant.AdvertiseConstant;
import com.wntime.advert.dao.*;
import com.wntime.advert.entity.InfoAdvertisePositionEntity;
import com.wntime.advert.entity.LogAdvertiseShowEntity;
import com.wntime.advert.entity.OrderAdvertiseDeliveryEntity;
import com.wntime.advert.form.ReportAdvertiseLogForm;
import com.wntime.advert.service.AdvertiseService;
import com.wntime.advert.vo.*;
import com.wntime.common.exception.RRException;
import com.wntime.datasource.annotation.DataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author 79448
 * @date 2020/11/6 16:23
 */
@Service
public class AdvertiseServiceImpl implements AdvertiseService {

    @Resource
    private InfoAdvertisePositionDao infoAdvertisePositionDao;
    @Resource
    private InfoAdvertiseTargetDao infoAdvertiseTargetDao;
    @Resource
    private OrderDeliveryAreaDao orderDeliveryAreaDao;
    @Resource
    private AdvertFileConfig advertFileConfig;
    @Resource
    private LogAdvertiseShowDao logAdvertiseShowDao;
    @Resource
    private OrderAdvertiseDeliveryDao orderAdvertiseDeliveryDao;

    @Override
    @DataSource("read")
    public List<InfoAdvertisePositionVo> queryPositionList() {
        return infoAdvertisePositionDao.queryList();
    }

    @Override
    @DataSource("read")
    public List<InfoAdvertiseTargetVo> queryTargetList() {
        return infoAdvertiseTargetDao.queryList();
    }

    /**
     * order_advertise_delivery order_advertise_attach order_delivery_area order_delivery_target
     * @param companyLineId
     * @return
     */
    @Override
    public Map<String,Object> queryCompanyLineAdvertise(long companyLineId) {
        Map<String,Object> result=new HashMap<>(4);
        List<AdvertiseDeliveryDetailVo> list = orderDeliveryAreaDao.queryDeliveryAreaDetailByCompanyLineId(companyLineId);
        Set<OrderDeliveryAreaVo> areaVos=new HashSet<>();
        Set<OrderAdvertiseAttachVo> attachVos=new HashSet<>();
        Set<OrderAdvertiseDeliveryVo> deliveryVos=new HashSet<>();
        Set<OrderDeliveryTargetVo> targetVos=new HashSet<>();
        if(list !=null && !list.isEmpty()){
            for(AdvertiseDeliveryDetailVo vo:list){
                OrderDeliveryAreaVo areaVo = new OrderDeliveryAreaVo();
                if (vo.getDeliveryAreaId() != null) {
                    areaVo.setDeliveryAreaId(vo.getDeliveryAreaId());
                    areaVo.setCompanyLineId(vo.getCompanyLineId());
                    areaVo.setAdvertiseDeliveryId(vo.getAdvertiseDeliveryId());
                    areaVo.setLineStationId(vo.getLineStationId());
                    areaVos.add(areaVo);
                }

                OrderAdvertiseAttachVo attachVo = new OrderAdvertiseAttachVo();
                if (vo.getAdvertiseAttachId() != null) {
                    attachVo.setAdvertiseAttachId(vo.getAdvertiseAttachId());
                    attachVo.setAdvertiseDeliveryId(vo.getAdvertiseDeliveryId());
                    attachVo.setAdvertisePositionId(vo.getAdvertisePositionId());
                    attachVo.setShowTimes(vo.getShowTimes());
                    attachVo.setAttachType(vo.getAttachType());
                    attachVo.setAttachLink(advertFileConfig.getFileUrl() + "/" + vo.getAttachLink());
                    attachVos.add(attachVo);
                }

                OrderAdvertiseDeliveryVo deliveryVo = new OrderAdvertiseDeliveryVo();
                deliveryVo.setAdvertiseDeliveryId(vo.getAdvertiseDeliveryId());
                deliveryVo.setAdvertiseDeliveryType(vo.getAdvertiseDeliveryType());
                deliveryVo.setDeliveryBegin(vo.getDeliveryBegin());
                deliveryVo.setDeliveryEnd(vo.getDeliveryEnd());
                deliveryVo.setIsInterrupt(vo.getIsInterrupt());
                deliveryVo.setInterruptNotice(vo.getInterruptNotice());
                deliveryVo.setAdvertiseNo(vo.getAdvertiseNo());
                deliveryVos.add(deliveryVo);

                OrderDeliveryTargetVo targetVo = new OrderDeliveryTargetVo();
                if (vo.getDeliveryTargetId() != null) {
                    targetVo.setDeliveryTargetId(vo.getDeliveryTargetId());
                    targetVo.setAdvertiseDeliveryId(vo.getAdvertiseDeliveryId());
                    targetVo.setAdvertiseTargetId(vo.getAdvertiseTargetId());
                    targetVos.add(targetVo);
                }
            }
        }
        result.put("orderDeliveryArea",areaVos);
        result.put("orderAdvertiseAttach",attachVos);
        result.put("orderAdvertiseDelivery",deliveryVos);
        result.put("orderDeliveryTarget",targetVos);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAdvertiseLog(ReportAdvertiseLogForm reportAdvertiseLogForm) {
        LogAdvertiseShowEntity entity = logAdvertiseShowDao.queryBusLatelyAdvertiseLog(reportAdvertiseLogForm.getBusId(), reportAdvertiseLogForm.getCompanyLineId(),reportAdvertiseLogForm.getAdvertiseDeliveryId());
        if(entity!=null){
            //累计播放时长
            entity.setShowTimes(entity.getShowTimes()+reportAdvertiseLogForm.getShowTimes());
            entity.setPeopleCount(reportAdvertiseLogForm.getPeopleCount());
            logAdvertiseShowDao.updateById(entity);
        }else {
            entity=new LogAdvertiseShowEntity();
            BeanUtils.copyProperties(reportAdvertiseLogForm,entity);
            entity.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            logAdvertiseShowDao.insert(entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseAdvertiseShowLogPeopleCount(long busId,long companyLineId) {
        //查询播放日志中广告是否下架
        Long logId = logAdvertiseShowDao.queryBusLatelyAdvertiseLogId(busId, companyLineId);
        if(logId!=null){
            logAdvertiseShowDao.increaseLogPeopleCount(logId);
        }
    }

    @Override
    @DataSource("read")
    public AdvertisingStatisticsDataVo queryFactoryBusAdvertiseStatistics(long factoryId,int size) {
        List<CompanyLinePeopleCountVo> list = logAdvertiseShowDao.queryPeopleCountGroupByCompanyLine(factoryId,size);
        AdvertisingStatisticsDataVo dataVo=new AdvertisingStatisticsDataVo();
        if(list!=null && !list.isEmpty()){
            int max=list.get(0).getPeopleCount();
            dataVo.setMax((int)Math.ceil(max +max * 0.2));
            for(CompanyLinePeopleCountVo vo: list){
                dataVo.addDataX(vo.getCompanyLineCode());
                dataVo.addDataY(vo.getPeopleCount());
            }
        }
        return dataVo;
    }

    @Override
    @DataSource("read")
    public void checkAdvertiseDelivery(long advertiseDeliveryId) {
        Integer count = orderAdvertiseDeliveryDao.selectCount(new QueryWrapper<OrderAdvertiseDeliveryEntity>()
                .eq("advertise_delivery_id", advertiseDeliveryId)
                .eq("check_status", AdvertiseConstant.DELIVERY)
                .le("delivery_begin", new Date()));
        if(count==null || count ==0){
            throw new RRException("广告投放单不存在");
        }
    }

    @Override
    @DataSource("read")
    public void checkAdvertisePosition(long advertisePositionId) {
        Integer count = infoAdvertisePositionDao.selectCount(new QueryWrapper<InfoAdvertisePositionEntity>().eq("advertise_position_id", advertisePositionId));
        if(count==null || count==0){
            throw new RRException("广告投放位不正确");
        }
    }

}
