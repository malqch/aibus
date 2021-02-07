package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wntime.ec.module.sys.service.IInfoFaultLevelService;
import com.wntime.ec.module.sys.entity.InfoFaultLevel;
import com.wntime.ec.module.sys.dao.InfoFaultLevelDao;
import com.wntime.ec.module.sys.vo.InfoFaultLevelQryRspVo;
import com.wntime.ec.module.sys.vo.InfoFaultLevelQryReqVo;

@Service
@Slf4j
public class InfoFaultLevelServiceImpl extends BaseServiceImpl<InfoFaultLevelDao,InfoFaultLevel, InfoFaultLevelQryRspVo,InfoFaultLevelQryReqVo> implements IInfoFaultLevelService {

      @Autowired
      InfoFaultLevelDao infoFaultLevelDao;

}
