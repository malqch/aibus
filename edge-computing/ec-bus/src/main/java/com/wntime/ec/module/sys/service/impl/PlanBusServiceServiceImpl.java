package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import com.wntime.ec.module.sys.dao.PlanBusServiceDao;
import com.wntime.ec.module.sys.entity.PlanBusService;
import com.wntime.ec.module.sys.service.IPlanBusServiceService;
import com.wntime.ec.module.sys.vo.PlanBusServiceQryReqVo;
import com.wntime.ec.module.sys.vo.PlanBusServiceQryRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class PlanBusServiceServiceImpl extends BaseServiceImpl<PlanBusServiceDao,PlanBusService, PlanBusServiceQryRspVo,PlanBusServiceQryReqVo> implements IPlanBusServiceService {

      @Autowired
      PlanBusServiceDao planBusServiceDao;

}
