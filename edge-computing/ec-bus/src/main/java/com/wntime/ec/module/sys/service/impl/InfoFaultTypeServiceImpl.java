package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import com.wntime.ec.module.sys.dao.InfoFaultTypeDao;
import com.wntime.ec.module.sys.entity.InfoFaultType;
import com.wntime.ec.module.sys.service.IInfoFaultTypeService;
import com.wntime.ec.module.sys.vo.InfoFaultTypeQryReqVo;
import com.wntime.ec.module.sys.vo.InfoFaultTypeQryRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InfoFaultTypeServiceImpl extends BaseServiceImpl<InfoFaultTypeDao,InfoFaultType, InfoFaultTypeQryRspVo,InfoFaultTypeQryReqVo> implements IInfoFaultTypeService {

      @Autowired
      InfoFaultTypeDao infoFaultTypeDao;

}
