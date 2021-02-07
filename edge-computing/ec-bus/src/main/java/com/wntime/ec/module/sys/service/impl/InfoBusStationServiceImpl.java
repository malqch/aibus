package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wntime.ec.module.sys.service.IInfoBusStationService;
import com.wntime.ec.module.sys.entity.InfoBusStation;
import com.wntime.ec.module.sys.dao.InfoBusStationDao;
import com.wntime.ec.module.sys.vo.InfoBusStationQryRspVo;
import com.wntime.ec.module.sys.vo.InfoBusStationQryReqVo;

@Service
@Transactional
@Slf4j
public class InfoBusStationServiceImpl extends BaseServiceImpl<InfoBusStationDao,InfoBusStation, InfoBusStationQryRspVo,InfoBusStationQryReqVo> implements IInfoBusStationService {

      @Autowired
      InfoBusStationDao infoBusStationDao;

}
