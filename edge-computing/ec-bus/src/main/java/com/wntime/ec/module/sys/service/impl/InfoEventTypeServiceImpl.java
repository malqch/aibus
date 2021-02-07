package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import com.wntime.ec.module.sys.dao.InfoEventTypeDao;
import com.wntime.ec.module.sys.entity.InfoEventType;
import com.wntime.ec.module.sys.service.IInfoEventTypeService;
import com.wntime.ec.module.sys.vo.InfoEventTypeQryReqVo;
import com.wntime.ec.module.sys.vo.InfoEventTypeQryRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InfoEventTypeServiceImpl extends BaseServiceImpl<InfoEventTypeDao,InfoEventType, InfoEventTypeQryRspVo,InfoEventTypeQryReqVo> implements IInfoEventTypeService {

      @Autowired
      InfoEventTypeDao infoEventTypeDao;

}
