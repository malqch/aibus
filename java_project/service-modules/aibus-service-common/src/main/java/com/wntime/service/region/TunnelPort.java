package com.wntime.service.region;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

@Region("tunnel_port")
public class TunnelPort {
    @Id
    private Integer port;
    private boolean used;

    public TunnelPort() {
    }

    public TunnelPort(Integer port) {
        this.port = port;
    }

    public TunnelPort(Integer port, boolean used) {
        this.port = port;
        this.used = used;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
