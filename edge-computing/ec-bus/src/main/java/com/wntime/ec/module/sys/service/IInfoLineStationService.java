package com.wntime.ec.module.sys.service;

import com.wntime.ec.common.util.mybatis.service.IBaseService;
import com.wntime.ec.module.sys.entity.InfoLineStation;
import com.wntime.ec.module.sys.vo.InfoLineStationQryReqVo;
import com.wntime.ec.module.sys.vo.InfoLineStationQryRspVo;

import java.util.List;

public interface IInfoLineStationService extends IBaseService<InfoLineStation, InfoLineStationQryRspVo, InfoLineStationQryReqVo> {

    List<InfoLineStationQryRspVo> selectLineStation(InfoLineStationQryReqVo param);
}
