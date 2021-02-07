package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import com.wntime.ec.module.sys.dao.LocalInfoBusDao;
import com.wntime.ec.module.sys.entity.LocalInfoBus;
import com.wntime.ec.module.sys.service.ILocalInfoBusService;
import com.wntime.ec.module.sys.vo.LocalInfoBusQryReqVo;
import com.wntime.ec.module.sys.vo.LocalInfoBusQryRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LocalInfoBusServiceImpl extends BaseServiceImpl<LocalInfoBusDao,LocalInfoBus, LocalInfoBusQryRspVo,LocalInfoBusQryReqVo> implements ILocalInfoBusService {

      @Autowired
      LocalInfoBusDao localInfoBusDao;

}
