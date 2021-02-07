package com.wntime.ec.module.sys.dao;

import com.wntime.ec.common.util.mybatis.dao.BaseDao;
import com.wntime.ec.module.sys.entity.InfoLineStation;
import com.wntime.ec.module.sys.vo.InfoLineStationQryReqVo;
import com.wntime.ec.module.sys.vo.InfoLineStationQryRspVo;

import java.util.List;

public interface InfoLineStationDao extends BaseDao<InfoLineStation, InfoLineStationQryRspVo, InfoLineStationQryReqVo> {

    List<InfoLineStationQryRspVo> selectLineStation(InfoLineStationQryReqVo param);

}
