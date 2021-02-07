package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wntime.ec.module.sys.service.IInfoLineStationService;
import com.wntime.ec.module.sys.entity.InfoLineStation;
import com.wntime.ec.module.sys.dao.InfoLineStationDao;
import com.wntime.ec.module.sys.vo.InfoLineStationQryRspVo;
import com.wntime.ec.module.sys.vo.InfoLineStationQryReqVo;

import java.util.List;

@Service
@Transactional
@Slf4j
public class InfoLineStationServiceImpl extends BaseServiceImpl<InfoLineStationDao,InfoLineStation, InfoLineStationQryRspVo,InfoLineStationQryReqVo> implements IInfoLineStationService {

      @Autowired
      InfoLineStationDao infoLineStationDao;

      @Override
      public List<InfoLineStationQryRspVo> selectLineStation(InfoLineStationQryReqVo param) {
            return infoLineStationDao.selectLineStation(param);
      }

}
