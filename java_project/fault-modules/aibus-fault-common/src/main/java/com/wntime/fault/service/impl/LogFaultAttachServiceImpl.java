package com.wntime.fault.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.FileUtil;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.fault.config.FaultFileConfig;
import com.wntime.fault.dao.InfoFaultTargetDao;
import com.wntime.fault.dao.LogFaultAttachDao;
import com.wntime.fault.entity.InfoFaultTargetEntity;
import com.wntime.fault.entity.LogFaultAttachEntity;
import com.wntime.fault.service.LogFaultAttachService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;


@Service("logFaultAttachService")
public class LogFaultAttachServiceImpl extends ServiceImpl<LogFaultAttachDao, LogFaultAttachEntity> implements LogFaultAttachService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private FaultFileConfig faultFileConfig;

    @Resource
    private InfoFaultTargetDao infoFaultTargetDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogFaultAttachEntity> page = this.page(
                new Query<LogFaultAttachEntity>().getPage(params),
                new QueryWrapper<LogFaultAttachEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<LogFaultAttachEntity> queryListByEventId(Long faultDetailId) {
        return getBaseMapper().queryPageList(faultDetailId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFaultAttach(long busId,Map<String, Object> tags,Long faultDetailId,long faultDate) {
        if(tags==null)return;
        //保存标签数据
        Set<Map.Entry<String, Object>> entrySet = tags.entrySet();
        Iterator<Map.Entry<String, Object>> iter = entrySet.iterator();
        List<LogFaultAttachEntity> attaches = new ArrayList<>();
        while (iter.hasNext()) {
            Map.Entry<String, Object> next = iter.next();
            Object value = next.getValue();
            if (value == null) continue;
            LogFaultAttachEntity logFaultAttachEntity = new LogFaultAttachEntity();
            logFaultAttachEntity.setCreatedDate(new Timestamp(faultDate));
            logFaultAttachEntity.setFaultDetailId(faultDetailId);
            InfoFaultTargetEntity target = infoFaultTargetDao.selectOne(new QueryWrapper<InfoFaultTargetEntity>().eq("fault_target_code", next.getKey()));
            logFaultAttachEntity.setFaultTargetId(target.getFaultTargetId());
            if (target.getFaultTargetGrope().equals(Constant.EventTargetGroup.LINK.getName())) {
                logFaultAttachEntity.setCollectAttachLink(Long.valueOf(String.valueOf(value)) );
            } else if (target.getFaultTargetGrope().equals(Constant.EventTargetGroup.VALUE.getName())) {
                logFaultAttachEntity.setCollectAttachValue(Double.valueOf(String.valueOf(value)) );
            } else if (target.getFaultTargetGrope().equals(Constant.EventTargetGroup.CHAR.getName())) {
                logFaultAttachEntity.setCollectAttachChar((String) value);
            }else if(target.getFaultTargetGrope().equals(Constant.EventTargetGroup.IMAGE.getName())){
                //判断如果是文件就要保存到文档
                value = saveFile(IdWorker.get32UUID() + ".png", (String) value, "/" + busId + "/");
                logFaultAttachEntity.setCollectAttachChar((String) value);
            }
            //将属于设置回去 主要是方便其他地方或者转化后的值
            tags.put(next.getKey(),value);
            attaches.add(logFaultAttachEntity);
        }
        saveBatch(attaches);
    }
    public String saveFile(String fileName, String base64Str, String childPath) {
        String filePath = faultFileConfig.getTodayPath() + childPath + fileName;
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileCopyUtils.copy(FileUtil.base64UrlToInputStream(base64Str), new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            logger.error("文件保存失败", e);
        }
        return faultFileConfig.getFileUrl() + filePath.substring(faultFileConfig.getFilePath().length());
    }
}
