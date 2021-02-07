package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import com.wntime.ec.module.sys.dao.InfoEventLevelDao;
import com.wntime.ec.module.sys.entity.InfoEventLevel;
import com.wntime.ec.module.sys.service.IInfoEventLevelService;
import com.wntime.ec.module.sys.vo.InfoEventLevelQryReqVo;
import com.wntime.ec.module.sys.vo.InfoEventLevelQryRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InfoEventLevelServiceImpl extends BaseServiceImpl<InfoEventLevelDao,InfoEventLevel, InfoEventLevelQryRspVo,InfoEventLevelQryReqVo> implements IInfoEventLevelService {

      @Autowired
      InfoEventLevelDao infoEventLevelDao;

}
