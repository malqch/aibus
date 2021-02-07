package com.wntime.service.common.service;


import com.wntime.service.common.vo.TunnelConfig;
import com.wntime.service.common.vo.TunnelVo;

public interface VideoTunnelService {

    TunnelVo bind(long busId, long userId,String deviceCode);

    void unbind(long busId, long userId);

    TunnelVo checkTunnel(long busId);

    TunnelConfig getConfig();

    void updateBusTunnelPort(long busId);
}
