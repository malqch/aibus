package com.wntime.service.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.utils.DateUtils;
import com.wntime.service.common.service.ConfigParamInfoService;
import com.wntime.service.common.service.InfoUpdateListService;
import com.wntime.service.common.vo.ConfigParamVo;
import com.wntime.service.entity.InfoConfigParamEntity;
import com.wntime.service.service.InfoConfigParamService;
import com.wntime.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

/**
 * 检查基本数据有没有进行操作，如果有，就更新updateList中的基础数据更新
 * @author ysc
 * 2020/11/25 18:21
 */
@Component
public class MetaDataUpdate {

    @Resource
    private InfoUpdateListService infoUpdateListService;
    @Resource
    private ConfigParamInfoService configParamInfoService;
    @Resource
    private InfoConfigParamService infoConfigParamService;

    /* 需要检查的基础数据表列表 */
    private static final String [] METADATA_CHECK_TABLE_LIST = new String[]{
            "INFO_BUS_STATION",
            "INFO_COLLECT_EVENT",
            "INFO_DEVICE_TYPE",
            "INFO_EVENT_EXTEND",
            "INFO_EVENT_LEVEL",
            "INFO_EVENT_TARGET",
            "INFO_EVENT_TYPE",
            "INFO_LINE_STATION",
            "PLAN_BUS_SERVICE"
    };

    /* 需要检查的人员信息列表 */
    private static final String [] PERSONNEL_DATA_CHECK_TABLE_LIST = new String[]{
            "INFO_STUDENT",
            "INFO_GUARDIAN",
            "INFO_AUTHORIZE",
            "INFO_SAFETY_OFFICER",
            "INFO_DRIVER"
    };

    private String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Author Buxl
     * @Description 检查基础数据是否有更新
     * @Date 10:47 2021/1/24
     * @Param []
     * @return void
     **/
    @Scheduled(fixedRate = 1 * 1000 * 60)
    public void mateDateCheck(){
        ConfigParamVo metaDataCheckTime = configParamInfoService.queryConfigByGroupCode("metadata_check", "metadata_check_time").get();
        LocalDateTime dateTime = LocalDateTime.parse(metaDataCheckTime.getParamChar(), dateTimeFormatter);
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        for (String tableName : METADATA_CHECK_TABLE_LIST) {
            if(infoUpdateListService.isTableUpdate(tableName,date)){
                infoUpdateListService.updateData("1", 1L, DateUtils.getTimestampNoMs());
                break;
            }
        }
        String now = dateTimeFormatter.format(LocalDateTime.now());
        InfoConfigParamEntity newMetaDataCheckTime = new InfoConfigParamEntity();
        newMetaDataCheckTime.setConfigParamId(metaDataCheckTime.getConfigParamId());
        newMetaDataCheckTime.setParamChar(now);
        infoConfigParamService.updateById(newMetaDataCheckTime);
    }

    /**
     * @Author Buxl
     * @Description 检查人员信息是否有更新
     * @Date 10:46 2021/1/24
     * @Param []
     * @return void
     **/
    @Scheduled(fixedRate = 1 * 1000 * 60)
    public void personnelDataCheck(){
        ConfigParamVo personnelDataCheckTime = configParamInfoService.queryConfigByGroupCode("metadata_check", "personnel_data_check_time").get();
        LocalDateTime preUpdateDateTime = LocalDateTime.parse(personnelDataCheckTime.getParamChar(), dateTimeFormatter);
        Date preUpdateDate = Date.from(preUpdateDateTime.atZone(ZoneId.systemDefault()).toInstant());

        LocalDateTime nowTime = LocalDateTime.now();
        Date now  = Date.from(nowTime.atZone(ZoneId.systemDefault()).toInstant());
        String nowStr = dateTimeFormatter.format(nowTime);

        for (String tableName : PERSONNEL_DATA_CHECK_TABLE_LIST) {
            if(infoUpdateListService.isTableUpdate1(tableName,preUpdateDate)){
                infoUpdateListService.updateData("3", 1L, new Timestamp(now.getTime()));
                break;
            }
        }

        InfoConfigParamEntity newPersonnelDataCheckTime = new InfoConfigParamEntity();
        newPersonnelDataCheckTime.setConfigParamId(personnelDataCheckTime.getConfigParamId());
        newPersonnelDataCheckTime.setParamChar(nowStr);
        infoConfigParamService.updateById(newPersonnelDataCheckTime);
    }


}
