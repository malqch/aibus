package com.wntime.service.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.service.dao.LogBusDeviceDao;
import com.wntime.service.entity.LogBusDeviceEntity;
import com.wntime.service.service.LogBusDeviceService;

/**
 * @desc 设备日志表
 *
 * @date 2020-09-02 13:49:03
 */
@Service("logBusDeviceService")
public class LogBusDeviceServiceImpl extends ServiceImpl<LogBusDeviceDao, LogBusDeviceEntity> implements LogBusDeviceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
            int currPage = 1;
            int pageSize = 10;
            if (params.get("page") != null) {
                currPage = Integer.parseInt((String) params.get("page"));
            }
            if (params.get("page") != null) {
                pageSize = Integer.parseInt((String) params.get("limit"));
            }
            Page<LogBusDeviceEntity> page = new Page<>(currPage, pageSize);
            params.put("page", page);
            return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
        }

        @Override
        public LogBusDeviceEntity getDetailById(Long id) {
            return this.getById(id);
        }

        @Override
        public void save(LogBusDeviceEntity logBusDeviceEntity, Long userId) {
            logBusDeviceEntity.setLogDeviceId(null);
            logBusDeviceEntity.setCreatedDate(DateUtils.getTimestamp());
            this.save(logBusDeviceEntity);
        }

        @Override
        public void updateById(LogBusDeviceEntity logBusDeviceEntity, Long userId) {
            this.updateById(logBusDeviceEntity);
        }


}
