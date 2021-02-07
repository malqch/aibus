package com.wntime.ec.module.sys.dao;

import com.wntime.ec.common.util.mybatis.dao.BaseDao;
import com.wntime.ec.module.sys.entity.InfoBusDevice;
import com.wntime.ec.module.sys.vo.InfoBusDeviceQryReqVo;
import com.wntime.ec.module.sys.vo.InfoBusDeviceQryRspVo;

import java.util.List;

public interface InfoBusDeviceDao extends BaseDao<InfoBusDevice, InfoBusDeviceQryRspVo, InfoBusDeviceQryReqVo> {

    void deleteNeedlessByIds(List ids);

}
