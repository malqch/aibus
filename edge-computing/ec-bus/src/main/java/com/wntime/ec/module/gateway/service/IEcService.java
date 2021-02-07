package com.wntime.ec.module.gateway.service;

import com.wntime.ec.common.model.FsmsRsp;
import com.wntime.ec.module.gateway.vo.EventReceiveReqVo;
import com.wntime.ec.module.gateway.vo.FaultReceiveReqVo;
import com.wntime.ec.module.gateway.vo.StatusReceiveReqVo;

/**
 * @author wing
 * @create 2020/8/28 14:27
 * @desc
 */
public interface IEcService {

    FsmsRsp event(EventReceiveReqVo reqVo);

    void status(StatusReceiveReqVo reqVo);

    void fault(FaultReceiveReqVo reqVo);

    void playAdLog(long advertiseDeliveryId, long advertiseAttachId);


}
