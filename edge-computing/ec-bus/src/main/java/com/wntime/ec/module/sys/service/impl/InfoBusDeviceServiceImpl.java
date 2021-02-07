package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import com.wntime.ec.module.sys.dao.InfoBusDeviceDao;
import com.wntime.ec.module.sys.entity.InfoBusDevice;
import com.wntime.ec.module.sys.service.IInfoBusDeviceService;
import com.wntime.ec.module.sys.vo.InfoBusDeviceQryReqVo;
import com.wntime.ec.module.sys.vo.InfoBusDeviceQryRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class InfoBusDeviceServiceImpl extends BaseServiceImpl<InfoBusDeviceDao,InfoBusDevice, InfoBusDeviceQryRspVo,InfoBusDeviceQryReqVo> implements IInfoBusDeviceService {

      @Autowired
      InfoBusDeviceDao infoBusDeviceDao;

      @Override
      public void deleteNeedlessByIds(List ids) {
            infoBusDeviceDao.deleteNeedlessByIds(ids);
      }
}
