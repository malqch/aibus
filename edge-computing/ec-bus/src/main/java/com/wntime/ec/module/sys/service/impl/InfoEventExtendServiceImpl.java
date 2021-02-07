package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import com.wntime.ec.module.sys.dao.InfoEventExtendDao;
import com.wntime.ec.module.sys.entity.InfoEventExtend;
import com.wntime.ec.module.sys.service.IInfoEventExtendService;
import com.wntime.ec.module.sys.vo.InfoEventExtendQryReqVo;
import com.wntime.ec.module.sys.vo.InfoEventExtendQryRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InfoEventExtendServiceImpl extends BaseServiceImpl<InfoEventExtendDao,InfoEventExtend, InfoEventExtendQryRspVo,InfoEventExtendQryReqVo> implements IInfoEventExtendService {

      @Autowired
      InfoEventExtendDao infoEventExtendDao;

}
