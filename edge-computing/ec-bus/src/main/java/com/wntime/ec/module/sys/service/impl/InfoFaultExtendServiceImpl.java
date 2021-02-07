package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import com.wntime.ec.module.sys.dao.InfoFaultExtendDao;
import com.wntime.ec.module.sys.entity.InfoFaultExtend;
import com.wntime.ec.module.sys.service.IInfoFaultExtendService;
import com.wntime.ec.module.sys.vo.InfoFaultExtendQryReqVo;
import com.wntime.ec.module.sys.vo.InfoFaultExtendQryRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InfoFaultExtendServiceImpl extends BaseServiceImpl<InfoFaultExtendDao,InfoFaultExtend, InfoFaultExtendQryRspVo,InfoFaultExtendQryReqVo> implements IInfoFaultExtendService {

      @Autowired
      InfoFaultExtendDao infoFaultExtendDao;

}
