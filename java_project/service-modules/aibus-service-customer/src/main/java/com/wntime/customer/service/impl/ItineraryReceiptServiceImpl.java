package com.wntime.customer.service.impl;

import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.customer.dao.ItineraryReceiptDao;
import com.wntime.customer.entity.ItineraryReceiptEntity;
import com.wntime.customer.service.ItineraryReceiptService;
import com.wntime.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;



@Service("itineraryReceiptService")
public class ItineraryReceiptServiceImpl extends ServiceImpl<ItineraryReceiptDao, ItineraryReceiptEntity> implements ItineraryReceiptService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ItineraryReceiptEntity> page = this.page(
                new Query<ItineraryReceiptEntity>().getPage(params),
                new QueryWrapper<ItineraryReceiptEntity>()
        );

        return new PageUtils(page);
    }

    /***
     * @Author Buxl
     * @Description 根据车辆id查询未开始的行程
     * @Date 23:33 2021/1/22
     * @Param [busId]
     * @return java.util.List<com.wntime.customer.entity.ItineraryReceiptEntity>
     **/
    @Override
    public List<ItineraryReceiptEntity> getItineraryByCon(Map<String,Object> params){
        return getBaseMapper().getItineraryByCon(params);
    }

    /**
     * @Author Buxl
     * @Description 根据营运计划生成未出行的行程
     * @Date 17:00 2021/1/21
     * @Param [itineraryDate, direction, busId]
     * @return void
     **/
    @Override
    public void produceItinerary(){
        //1、查询当天已结束的行程
        QueryWrapper queryWrapper = new QueryWrapper<ItineraryReceiptEntity>()
                .eq("itinerary_date",DateUtil.date2String(new Date(),DateUtil.yyyyMMdd))
                .eq("itinerary_status","3");
        List<ItineraryReceiptEntity> finishedItineraryList = super.list(queryWrapper);
        finishedItineraryList.forEach(finishedItinerary ->{
            //2、判断行程结束时间是否已超过了20分钟
            Timestamp itineraryEndTime = finishedItinerary.getItineraryEndTime();
            if( ((System.currentTimeMillis() - itineraryEndTime.getTime()) / 1000 / 60) > 20){
                String nextItineraryDate = "";
                String nextItineraryDirection = "";
                if("1".equals(finishedItinerary.getItineraryDirection())){
                    //上行,下一个行程为当天的下行
                    nextItineraryDate = finishedItinerary.getItineraryDate();
                    nextItineraryDirection = "2";
                }else{
                    //下行,下一个行程为第二天的的上行
                    nextItineraryDate = DateUtils.format(
                            DateUtils.addDateDays(
                                DateUtils.stringToDate(finishedItinerary.getItineraryDate(), DateUtil.yyyyMMdd),1)
                            ,DateUtil.yyyyMMdd);
                    nextItineraryDirection = "1";
                }

                //3、查询是否有下一个行程
                QueryWrapper wrapper = new QueryWrapper<ItineraryReceiptEntity>()
                        .eq("itinerary_date",nextItineraryDate)
                        .eq("itinerary_direction",nextItineraryDirection)
                        .eq("bus_id",finishedItinerary.getBusId());
                int count = super.count(wrapper);
                if(count == 0){
                    //4、生成下一个行程
                    produceItinerary(nextItineraryDate,nextItineraryDirection,finishedItinerary.getBusId());
                }
            }
        });
    }

    /**
     * @Author Buxl
     * @Description 根据营运计划生成未出行的行程
     * @Date 17:00 2021/1/21
     * @Param [itineraryDate, direction, busId]
     * @return void
     **/
    @Override
    public void produceItinerary(String itineraryDate,String direction,Long busId){
        if(StringUtils.isEmpty(itineraryDate)){
            itineraryDate = DateUtil.date2String(new Date(),DateUtil.yyyyMMdd);
        }
        if (StringUtils.isEmpty(direction)){
            direction = Constant.LineDirection.UP.getValue();
        }
        //获取行程计划，此时行程表中还没有数据，如果查到计划了，则生成行程
        List<ItineraryReceiptEntity> itineraryPlanList = getBaseMapper().getItineraryPlan(itineraryDate,direction,busId);
        for(ItineraryReceiptEntity itineraryPlan : itineraryPlanList){
            if (itineraryPlan != null){
                int dataCount = super.count(new QueryWrapper<ItineraryReceiptEntity>()
                        .eq("plan_service_id",itineraryPlan.getPlanServiceId())
                        .eq("itinerary_date",itineraryDate));
                if(dataCount > 0){
                    continue;
                }
                itineraryPlan.setItineraryDate(itineraryDate);
                itineraryPlan.setItineraryStatus(Constant.ItineraryStatus.NOT_START.getCode());
                itineraryPlan.setIsBackup("0");
                itineraryPlan.setReportStatus("0");

                String predictDepartTime = itineraryDate + " "
                        + (StringUtils.isEmpty(itineraryPlan.getExpectedArrivalTime()) ? "06:00" : itineraryPlan.getExpectedArrivalTime());

                if(Constant.LineDirection.DOWN.getValue().equals(direction)){
                    predictDepartTime = itineraryDate + " "
                            + (StringUtils.isEmpty(itineraryPlan.getExpectedArrivalTime()) ? "16:00" : itineraryPlan.getExpectedArrivalTime());
                }
                try {
                    itineraryPlan.setPredictDepartTime(DateUtil.string2Date(predictDepartTime,"yyyyMMdd HH:mm"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                itineraryPlan.setItineraryDirection(direction);
                itineraryPlan.setCreateDt(DateUtils.getTimestamp());
                super.save(itineraryPlan);
            }
        }
    }

    /**
     * @Author Buxl
     * @Description 根据行程id查询行程的车站列表
     * @Date 8:30 2021/1/27
     * @Param [itineraryId]
     * @return java.util.List<com.wntime.customer.entity.ItineraryReceiptEntity>
     **/
    @Override
    public List<ItineraryReceiptEntity> getStationByItinerary(Long itineraryId) {
        return getBaseMapper().getStationByItinerary(itineraryId);
    }

    /**
     * @Author Buxl
     * @Description 查询行程下一停靠站的GPS
     * @Date 10:04 2021/1/28
     * @Param [itineraryId]
     * @return com.wntime.customer.entity.ItineraryReceiptEntity
     **/
    @Override
    public ItineraryReceiptEntity getNextStationGPS(Long itineraryId) {
        return getBaseMapper().getNextStationGPS(itineraryId);
    }

    /**
     * @Author Buxl
     * @Description 统计行程回单的数据
     * @Date 10:00 2021/1/29
     * @Param [itineraryId]
     * @return com.wntime.customer.entity.ItineraryReceiptEntity
     **/
    @Override
    @Async
    public List<ItineraryReceiptEntity> statReceiptData(Long itineraryId) {
        List<ItineraryReceiptEntity> itineraryReceiptEntityList = getBaseMapper().statReceiptData(itineraryId);
        itineraryReceiptEntityList.forEach(entry ->{
            entry.setModifyDt(DateUtils.getTimestamp());
            entry.setItineraryStatus("3");
            entry.setDrivingViolationTotal(entry.getDrivingViolationTotal() == null ? 0 : entry.getDrivingViolationTotal());
            entry.setNoAuthorizeWarn(entry.getNoAuthorizeWarn() == null ? 0 : entry.getNoAuthorizeWarn());
            entry.setLoadTotal(entry.getLoadTotal() == null ? 0 : entry.getLoadTotal());
            entry.setLeaveTotal(entry.getLeaveTotal() == null ? 0 : entry.getLeaveTotal());
            entry.setOffBusTotal(entry.getOffBusTotal() == null ? 0 : entry.getOffBusTotal());
            entry.setBodyTemperatureAbnormalTotal(entry.getBodyTemperatureAbnormalTotal() == null ? 0 : entry.getBodyTemperatureAbnormalTotal());
            entry.setNotWearMaskTotal(entry.getNotWearMaskTotal() == null ? 0 : entry.getNotWearMaskTotal());
            entry.setNotHandDisinfectionTotal(entry.getNotHandDisinfectionTotal() == null ? 0 : entry.getNotHandDisinfectionTotal());
            entry.setStudentViolationTotal(entry.getStudentViolationTotal() == null ? 0 : entry.getStudentViolationTotal());
            entry.setStudentViolationCount(entry.getStudentViolationCount() == null ? 0 : entry.getStudentViolationCount());
            //未上车人数 = 学生总数 - 请假人数 - 已上车人数(实载学生人数)
            entry.setNonTotal(entry.getStudentCount() - entry.getLeaveTotal() - entry.getLoadTotal());
            super.updateById(entry);
        });
        return itineraryReceiptEntityList;
    }

}