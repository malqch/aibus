package com.wntime.event.service.impl;

import com.wntime.datasource.annotation.DataSource;
import com.wntime.event.dao.*;
import com.wntime.event.serive.EventBaseService;
import com.wntime.event.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 79448
 * @date 2020/8/31 11:58
 */
@Service
public class EventBaseServiceImpl implements EventBaseService {

    @Resource
    private InfoCollectEventDao infoCollectEventDao;
    @Resource
    private InfoEventExtendDao infoEventExtendDao;
    @Resource
    private InfoEventLevelDao infoEventLevelDao;
    @Resource
    private InfoEventTargetDao infoEventTargetDao;
    @Resource
    private InfoEventTypeDao infoEventTypeDao;

    @Override
    @DataSource("read")
    public List<InfoCollectEventVo> queryCollectEvent() {
        return infoCollectEventDao.queryList();
    }

    @Override
    @DataSource("read")
    public List<InfoEventExtendVo> queryEventExtend() {
        return infoEventExtendDao.queryList();
    }

    @Override
    @DataSource("read")
    public List<InfoEventLevelVo> queryEventLevel() {
        return infoEventLevelDao.queryList();
    }

    @Override
    @DataSource("read")
    public List<InfoEventTargetVo> queryEventTarget() {
        return infoEventTargetDao.queryList();
    }

    @Override
    @DataSource("read")
    public List<InfoEventTypeVo> queryEventType() {
        return infoEventTypeDao.queryList();
    }
}
