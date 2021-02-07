package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import com.wntime.ec.module.sys.dao.InfoEventTargetDao;
import com.wntime.ec.module.sys.entity.InfoEventTarget;
import com.wntime.ec.module.sys.service.IInfoEventTargetService;
import com.wntime.ec.module.sys.vo.InfoEventTargetQryReqVo;
import com.wntime.ec.module.sys.vo.InfoEventTargetQryRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InfoEventTargetServiceImpl extends BaseServiceImpl<InfoEventTargetDao,InfoEventTarget, InfoEventTargetQryRspVo,InfoEventTargetQryReqVo> implements IInfoEventTargetService {

      @Autowired
      InfoEventTargetDao infoEventTargetDao;

}
