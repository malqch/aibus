package com.wntime.service.common.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.service.common.entity.LogBusDriveEntity;
import com.wntime.service.common.vo.BusRealtimeMonitorVo;

public interface BusDriveLogService extends IService<LogBusDriveEntity> {
    /**
     * 保存实时上报的行驶日志
     * @param logBusDriveEntity
     */
    void insertBusDriveLog(LogBusDriveEntity logBusDriveEntity);

    /**
     * 保存事件上传 故障障报的行驶状态
     * @param busId
     * @param busStatus
     */
    void busDriveStatus(long busId,int busStatus);

    BusRealtimeMonitorVo queryByBusId(long busId);

    int queryBusTotalMileSum(long factoryId);
}
