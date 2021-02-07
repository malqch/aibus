package com.wntime.ec.module.schedule.vo;

import java.util.List;

public class GeoconvRspWarpVo {
    private int status;
    private List<GeoconvRspVo> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<GeoconvRspVo> getResult() {
        return result;
    }

    public void setResult(List<GeoconvRspVo> result) {
        this.result = result;
    }
}
