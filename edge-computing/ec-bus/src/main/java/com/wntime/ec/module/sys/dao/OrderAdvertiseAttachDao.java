package com.wntime.ec.module.sys.dao;

import com.wntime.ec.common.util.mybatis.dao.BaseDao;
import com.wntime.ec.module.sys.entity.OrderAdvertiseAttach;
import com.wntime.ec.module.sys.vo.OrderAdvertiseAttachQryReqVo;
import com.wntime.ec.module.sys.vo.OrderAdvertiseAttachQryRspVo;

import java.util.List;

public interface OrderAdvertiseAttachDao extends BaseDao<OrderAdvertiseAttach, OrderAdvertiseAttachQryRspVo, OrderAdvertiseAttachQryReqVo> {

    List<OrderAdvertiseAttachQryRspVo> selectAdvertiseAttachList(OrderAdvertiseAttachQryReqVo attachQryReqVo);

}
