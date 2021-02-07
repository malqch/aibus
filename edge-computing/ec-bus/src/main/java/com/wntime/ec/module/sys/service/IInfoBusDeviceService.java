package com.wntime.ec.module.sys.service;

import com.wntime.ec.common.util.mybatis.service.IBaseService;
import com.wntime.ec.module.sys.entity.InfoBusDevice;
import com.wntime.ec.module.sys.vo.InfoBusDeviceQryReqVo;
import com.wntime.ec.module.sys.vo.InfoBusDeviceQryRspVo;

import java.util.List;

public interface IInfoBusDeviceService extends IBaseService<InfoBusDevice, InfoBusDeviceQryRspVo, InfoBusDeviceQryReqVo> {

    void deleteNeedlessByIds(List delList);

}
