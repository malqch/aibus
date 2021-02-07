package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wntime.ec.module.sys.service.IInfoAdvertisePositionService;
import com.wntime.ec.module.sys.entity.InfoAdvertisePosition;
import com.wntime.ec.module.sys.dao.InfoAdvertisePositionDao;
import com.wntime.ec.module.sys.vo.InfoAdvertisePositionQryRspVo;
import com.wntime.ec.module.sys.vo.InfoAdvertisePositionQryReqVo;

@Service
@Transactional
@Slf4j
public class InfoAdvertisePositionServiceImpl extends BaseServiceImpl<InfoAdvertisePositionDao,InfoAdvertisePosition, InfoAdvertisePositionQryRspVo,InfoAdvertisePositionQryReqVo> implements IInfoAdvertisePositionService {

      @Autowired
      InfoAdvertisePositionDao infoAdvertisePositionDao;

}
