package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import com.wntime.ec.module.sys.dao.InfoCollectEventDao;
import com.wntime.ec.module.sys.entity.InfoCollectEvent;
import com.wntime.ec.module.sys.service.IInfoCollectEventService;
import com.wntime.ec.module.sys.vo.InfoCollectEventQryReqVo;
import com.wntime.ec.module.sys.vo.InfoCollectEventQryRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InfoCollectEventServiceImpl extends BaseServiceImpl<InfoCollectEventDao,InfoCollectEvent, InfoCollectEventQryRspVo,InfoCollectEventQryReqVo> implements IInfoCollectEventService {

      @Autowired
      InfoCollectEventDao infoCollectEventDao;

}
