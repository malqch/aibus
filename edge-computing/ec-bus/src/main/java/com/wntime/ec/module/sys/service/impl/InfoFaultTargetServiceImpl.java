package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import com.wntime.ec.module.sys.dao.InfoFaultTargetDao;
import com.wntime.ec.module.sys.entity.InfoFaultTarget;
import com.wntime.ec.module.sys.service.IInfoFaultTargetService;
import com.wntime.ec.module.sys.vo.InfoFaultTargetQryReqVo;
import com.wntime.ec.module.sys.vo.InfoFaultTargetQryRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InfoFaultTargetServiceImpl extends BaseServiceImpl<InfoFaultTargetDao,InfoFaultTarget, InfoFaultTargetQryRspVo,InfoFaultTargetQryReqVo> implements IInfoFaultTargetService {

      @Autowired
      InfoFaultTargetDao infoFaultTargetDao;

}
