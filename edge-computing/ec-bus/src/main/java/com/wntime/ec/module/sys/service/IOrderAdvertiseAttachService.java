package com.wntime.ec.module.sys.service;

import com.wntime.ec.common.util.mybatis.service.IBaseService;
import com.wntime.ec.module.sys.entity.OrderAdvertiseAttach;
import com.wntime.ec.module.sys.vo.OrderAdvertiseAttachQryReqVo;
import com.wntime.ec.module.sys.vo.OrderAdvertiseAttachQryRspVo;

import java.util.List;

public interface IOrderAdvertiseAttachService extends IBaseService<OrderAdvertiseAttach, OrderAdvertiseAttachQryRspVo, OrderAdvertiseAttachQryReqVo> {

    List<OrderAdvertiseAttachQryRspVo> selectAdvertiseAttachList(OrderAdvertiseAttachQryReqVo attachQryReqVo);


}
