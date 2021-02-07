package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import com.wntime.ec.module.sys.dao.InfoDeviceTypeDao;
import com.wntime.ec.module.sys.entity.InfoDeviceType;
import com.wntime.ec.module.sys.service.IInfoDeviceTypeService;
import com.wntime.ec.module.sys.vo.InfoDeviceTypeQryReqVo;
import com.wntime.ec.module.sys.vo.InfoDeviceTypeQryRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InfoDeviceTypeServiceImpl extends BaseServiceImpl<InfoDeviceTypeDao,InfoDeviceType, InfoDeviceTypeQryRspVo,InfoDeviceTypeQryReqVo> implements IInfoDeviceTypeService {

      @Autowired
      InfoDeviceTypeDao infoDeviceTypeDao;

}
