package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wntime.ec.module.sys.service.IInfoAdvertiseTargetService;
import com.wntime.ec.module.sys.entity.InfoAdvertiseTarget;
import com.wntime.ec.module.sys.dao.InfoAdvertiseTargetDao;
import com.wntime.ec.module.sys.vo.InfoAdvertiseTargetQryRspVo;
import com.wntime.ec.module.sys.vo.InfoAdvertiseTargetQryReqVo;

@Service
@Transactional
@Slf4j
public class InfoAdvertiseTargetServiceImpl extends BaseServiceImpl<InfoAdvertiseTargetDao,InfoAdvertiseTarget, InfoAdvertiseTargetQryRspVo,InfoAdvertiseTargetQryReqVo> implements IInfoAdvertiseTargetService {

      @Autowired
      InfoAdvertiseTargetDao infoAdvertiseTargetDao;

}
