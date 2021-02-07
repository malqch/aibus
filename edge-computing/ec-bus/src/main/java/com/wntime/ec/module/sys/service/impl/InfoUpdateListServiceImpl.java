package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import com.wntime.ec.module.sys.dao.InfoUpdateListDao;
import com.wntime.ec.module.sys.entity.InfoUpdateList;
import com.wntime.ec.module.sys.service.IInfoUpdateListService;
import com.wntime.ec.module.sys.vo.InfoUpdateListQryReqVo;
import com.wntime.ec.module.sys.vo.InfoUpdateListQryRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InfoUpdateListServiceImpl extends BaseServiceImpl<InfoUpdateListDao,InfoUpdateList, InfoUpdateListQryRspVo,InfoUpdateListQryReqVo> implements IInfoUpdateListService {

      @Autowired
      InfoUpdateListDao infoUpdateListDao;

}
