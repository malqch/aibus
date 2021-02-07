package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import com.wntime.ec.module.sys.dao.OrderAdvertiseAttachDao;
import com.wntime.ec.module.sys.entity.OrderAdvertiseAttach;
import com.wntime.ec.module.sys.service.IOrderAdvertiseAttachService;
import com.wntime.ec.module.sys.vo.OrderAdvertiseAttachQryReqVo;
import com.wntime.ec.module.sys.vo.OrderAdvertiseAttachQryRspVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class OrderAdvertiseAttachServiceImpl extends BaseServiceImpl<OrderAdvertiseAttachDao, OrderAdvertiseAttach, OrderAdvertiseAttachQryRspVo, OrderAdvertiseAttachQryReqVo> implements IOrderAdvertiseAttachService {

    @Autowired
    OrderAdvertiseAttachDao orderAdvertiseAttachDao;

    @Override
    public List<OrderAdvertiseAttachQryRspVo> selectAdvertiseAttachList(OrderAdvertiseAttachQryReqVo attachQryReqVo) {
        return orderAdvertiseAttachDao.selectAdvertiseAttachList(attachQryReqVo);
    }
}
