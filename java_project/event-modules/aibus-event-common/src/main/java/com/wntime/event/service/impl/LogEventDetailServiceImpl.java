package com.wntime.event.service.impl;

import cn.hutool.core.codec.Base64Encoder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mchange.util.Base64FormatException;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.common.validator.Assert;
import com.wntime.event.config.EventFileConfig;
import com.wntime.event.dao.LogEventDetailDao;
import com.wntime.event.entity.LogEventDetailEntity;
import com.wntime.event.serive.LogEventDetailStatService;
import com.wntime.event.service.LogEventAttachService;
import com.wntime.event.service.LogEventDetailService;
import com.wntime.event.vo.*;
import com.wntime.service.common.vo.StatisticsResultVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("logEventDetailService")
public class LogEventDetailServiceImpl extends ServiceImpl<LogEventDetailDao, LogEventDetailEntity> implements LogEventDetailService, LogEventDetailStatService {

    @Autowired
    private LogEventAttachService logEventAttachService;
    @Resource
    private EventFileConfig eventFileConfig;

    /*
    查询公共环境信息
     */
    @Override
    public List<EventEnvSearchApiVo> getEnvSearchData(Map<String, Object> params) {
        return getBaseMapper().getEnvSearchData(params);
    }

    /*
    查询公共交通违章信息
     */
    @Override
    public List<EventTrafficSearchApiVo> getTrafficSearchData(Map<String, Object> params) {
        // 照片处理
        List<EventTrafficSearchApiVo> eventEnvSearchApiVoList = getBaseMapper().getTrafficSearchData(params);
        String imgUrl = "";
        if(eventEnvSearchApiVoList != null && eventEnvSearchApiVoList.size() > 0){
            for(EventTrafficSearchApiVo vo : eventEnvSearchApiVoList){
                imgUrl = vo.getImage1().replaceAll(" ","");
                if (imgUrl.startsWith(eventFileConfig.getFileUrl())){
                    imgUrl = eventFileConfig.getFilePath() + imgUrl.substring(eventFileConfig.getFileUrl().length());
                    vo.setImage1(readInputStream(imgUrl));
                }else{
                    vo.setImage1("");
                }

                imgUrl = vo.getImage2().replaceAll(" ","");
                if (imgUrl.startsWith(eventFileConfig.getFileUrl())){
                    imgUrl = eventFileConfig.getFilePath() + imgUrl.substring(eventFileConfig.getFileUrl().length());
                    vo.setImage2(readInputStream(imgUrl));
                }else{
                    vo.setImage2("");
                }

                imgUrl = vo.getImage3().replaceAll(" ","");
                if (imgUrl.startsWith(eventFileConfig.getFileUrl())){
                    imgUrl = eventFileConfig.getFilePath() + imgUrl.substring(eventFileConfig.getFileUrl().length());
                    vo.setImage3(readInputStream(imgUrl));
                }else{
                    vo.setImage3("");
                }
            }
        }
        return eventEnvSearchApiVoList;
    }
    /*
    查询公共卫生信息
     */
    @Override
    public List<EventHealthSearchApiVo> getHealthSearchData(Map<String, Object> params) {
        // 照片处理
        List<EventHealthSearchApiVo> eventHealthSearchApiVoList = getBaseMapper().getHealthSearchData(params);
        String imgUrl = "";
        if(eventHealthSearchApiVoList != null && eventHealthSearchApiVoList.size() > 0){
            for(EventHealthSearchApiVo vo : eventHealthSearchApiVoList){
                imgUrl = vo.getImage().replaceAll(" ","");
                if (imgUrl.startsWith(eventFileConfig.getFileUrl())){
                    imgUrl = eventFileConfig.getFilePath() + imgUrl.substring(eventFileConfig.getFileUrl().length());
                    vo.setImage(readInputStream(imgUrl));
                }else{
                    vo.setImage("");
                }
            }
        }
        return eventHealthSearchApiVoList;
    }

    /**
     * 图片转换Base64
     * @param url
     * @return
     */
    private static String readInputStream(String url){
        try{
            InputStream inStream = new FileInputStream(url);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while( (len = inStream.read(buffer)) != -1 ){
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            //关闭输入流
            inStream.close();
            //把outStream里的数据写入
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(outStream.toByteArray());
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogEventDetailVo> page = new Query<LogEventDetailVo>().getPage(params);
        params.put("page", page);
        page.setRecords(getBaseMapper().listDetail(params));
        return new PageUtils(page);
    }

    @Override
    public String getParamConfigStationName() {

        return getBaseMapper().getParamConfigStationName();
    }

    @Override
    public PageUtils getTempStatByCompanyAndLinePage(Map<String, Object> params) {

        Integer currPage = Integer.parseInt((String) params.get("page"));
        Integer pageSize = Integer.parseInt((String) params.get("limit"));
        Page<EventHealthTempStatVo> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        if(params.get("companyLineId") != null && StringUtils.isNotEmpty(String.valueOf(params.get("companyLineId")))){
            params.put("companyLineId", Long.parseLong(String.valueOf(params.get("companyLineId"))));
        }else{
            params.put("companyLineId", null);
        }
        return new PageUtils(page.setRecords(getBaseMapper().getTempStatByCompanyAndLinePage(params)));
    }

    @Override
    public PageUtils getTrafficStatByCompanyPage(Map<String, Object> params) {
        Integer currPage = Integer.parseInt((String) params.get("page"));
        Integer pageSize = Integer.parseInt((String) params.get("limit"));
        Page<EventTrafficStatVo> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        return new PageUtils(page.setRecords(getBaseMapper().getTrafficStatByCompanyAndLinePage(params)));
    }

    @Override
    public List<EventTrafficStatVo> getTrafficStatByCompanyPageExport(Map<String, Object> params) {
        List<Long> busIdList = (List<Long>)params.get("busIdList");
        Date startTime = null;
        Date endTime = null;
        if(params.get("startTime") != null){
            startTime = DateUtils.stringToDate(String.valueOf(params.get("startTime")), DateUtils.DATE_TIME_PATTERN);
        }

        if(params.get("endTime") != null){
            endTime = DateUtils.stringToDate(String.valueOf(params.get("endTime")), DateUtils.DATE_TIME_PATTERN);
        }
        List<EventTrafficStatVo> eventTrafficStatVos = getBaseMapper().getTrafficStatByCompanyAndLinePage(params);
        return eventTrafficStatVos;
    }

    @Override
    public List<StatisticsResultVo> getTempDetailByEventDetailId(List<Long> eventDetailIdList) {
        return this.baseMapper.getTempDetailByEventDetailId(eventDetailIdList) ;
    }

    @Override
    public List<Map<String, Object>> getTrafficEventCountByCompanyIdGroupByBus(Long companyId, Date startTime) {
        //todo eventTypeCode待修改
        return getBaseMapper().getEventCountByEventTypeAndCompanyIdGroupByBus("traffic_code", companyId, startTime);
    }

    @Override
    public List<Map<String, Object>> getTrafficEventCountByCompanyIdGroupByTimeWindow(Long companyId, Date startTime,Date endTime) {
        return getBaseMapper().getEventCountByEventTypeAndCompanyIdGroupByTimeWindow("traffic_code", companyId, startTime,endTime);
    }

    @Override
    public List<Map<String, Object>> getTemperatureByStationId(Long stationId, Date startTime, Date endTime) {
        List<Long> logEventDetailIdList = getBaseMapper().getLogEventDetailIdListByStationId(stationId, startTime, endTime);
        Assert.isEmpty(logEventDetailIdList,"没有数据");
        return getBaseMapper().getEnvMetricDataPointsByLogEventDetailIdList(logEventDetailIdList, "temperature");
    }

    @Override
    public List<Map<String, Object>> getHumidityByStationId(Long stationId, Date startTime, Date endTime) {
        List<Long> logEventDetailIdList = getBaseMapper().getLogEventDetailIdListByStationId(stationId, startTime, endTime);
        Assert.isEmpty(logEventDetailIdList,"没有数据");
        return getBaseMapper().getEnvMetricDataPointsByLogEventDetailIdList(logEventDetailIdList, "humidity");
    }

    @Override
    public List<Map<String, Object>> getPm_2_5ByStationId(Long stationId, Date startTime, Date endTime) {
        List<Long> logEventDetailIdList = getBaseMapper().getLogEventDetailIdListByStationId(stationId, startTime, endTime);
        Assert.isEmpty(logEventDetailIdList,"没有数据");
        return getBaseMapper().getEnvMetricDataPointsByLogEventDetailIdList(logEventDetailIdList, "pm25");
    }

    @Override
    public List<Map<String, Object>> getEventCountByCompanyIdGroupByTrafficEventType(Long companyId) {

        return getBaseMapper().getEventCountByCompanyIdGroupByTrafficEventType(companyId);
    }

    @Override
    public LogEventDetailVo getDetailById(Long eventDetailId) {
        return getBaseMapper().getDetail(eventDetailId);
    }

    @Override
    public LogEventDetailVo getDetailWithAttachById(Long eventDetailId) {
        LogEventDetailVo logEventDetail = getBaseMapper().getDetail(eventDetailId);
        Assert.isNull(logEventDetail,"没有对应数据");
        logEventDetail.setEventAttachList(logEventAttachService.listAttachByDetailId(eventDetailId));
        return logEventDetail;
    }


}
