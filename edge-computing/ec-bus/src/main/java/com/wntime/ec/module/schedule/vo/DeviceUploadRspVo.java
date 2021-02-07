package com.wntime.ec.module.schedule.vo;

import com.wntime.ec.module.sys.entity.InfoBusDevice;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wing
 * @create 2020/9/1 10:14
 * @desc
 */
@Data
public class DeviceUploadRspVo {
    private List<InfoBusDevice> devices = new ArrayList<InfoBusDevice>();
}
