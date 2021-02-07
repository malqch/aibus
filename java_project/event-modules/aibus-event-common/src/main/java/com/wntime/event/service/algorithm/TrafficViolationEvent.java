package com.wntime.event.service.algorithm;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.wntime.common.utils.R;
import com.wntime.event.entity.InfoEventTargetEntity;
import com.wntime.event.entity.LogEventAttachEntity;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.service.EventDealAlgorithm;
import com.wntime.event.service.InfoEventTargetService;
import com.wntime.event.service.LogEventAttachService;
import com.wntime.service.common.service.ConfigParamService;
import com.wntime.service.common.vo.ConfigParamVo;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 交通违章事件
 * @author 79448
 * @date 2020/8/25 17:06
 */
@Component
public class TrafficViolationEvent extends AbstractEventHandle implements EventDealAlgorithm {

    @Resource
    private ConfigParamService configParamService;
    @Resource
    private LogEventAttachService logEventAttachService;
    @Resource
    private InfoEventTargetService infoEventTargetService;
    @Resource
    private RestTemplate restTemplate;

    @Override
    public R deal(EventReportForm eventReportForm) {
        Map<String, Object> tags = eventReportForm.getTags();
        Object longitude = tags.get("longitude");
        Object latitude = tags.get("latitude");

        AtomicReference<String> address=new AtomicReference<>();
        Optional<ConfigParamVo> config = configParamService.queryConfigByGroupCode("gps_converter", "gps_location");
        config.ifPresent(configParamVo -> {
            String url = configParamVo.getParamChar()+"&output=json&location="+latitude+","+ longitude;

            String map = restTemplate.getForObject(url, String.class);
            JSONObject obj = JSONObject.parseObject(map);
            if(String.valueOf(obj.get("status")).equals("0")){
                address.set(String.valueOf(((JSONObject)obj.get("result")).get("formatted_address")));
            }

        });
        if(address.get() !=null ){
            LogEventAttachEntity logEventAttachEntity = new LogEventAttachEntity();
            logEventAttachEntity.setCreatedDate(new Timestamp(eventReportForm.getEventDate()));
            logEventAttachEntity.setEventDetailId(eventReportForm.getEventDetailId());
            InfoEventTargetEntity target = infoEventTargetService.getOne(new QueryWrapper<InfoEventTargetEntity>().eq("event_target_code", "address"));
            logEventAttachEntity.setEventTargetId(target.getEventTargetId());
            logEventAttachEntity.setCollectAttachChar(address.get());
            logEventAttachService.save(logEventAttachEntity);
        }

        return R.ok();
    }

}
