package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wntime.ec.module.sys.service.IInfoCollectFaultService;
import com.wntime.ec.module.sys.entity.InfoCollectFault;
import com.wntime.ec.module.sys.dao.InfoCollectFaultDao;
import com.wntime.ec.module.sys.vo.InfoCollectFaultQryRspVo;
import com.wntime.ec.module.sys.vo.InfoCollectFaultQryReqVo;

@Service
@Slf4j
public class InfoCollectFaultServiceImpl extends BaseServiceImpl<InfoCollectFaultDao,InfoCollectFault, InfoCollectFaultQryRspVo,InfoCollectFaultQryReqVo> implements IInfoCollectFaultService {

      @Autowired
      InfoCollectFaultDao infoCollectFaultDao;

}
