package com.wntime.ec.module.schedule.vo;

import com.wntime.ec.module.sys.entity.OrderAdvertiseAttach;
import com.wntime.ec.module.sys.entity.OrderAdvertiseDelivery;
import com.wntime.ec.module.sys.entity.OrderDeliveryArea;
import com.wntime.ec.module.sys.entity.OrderDeliveryTarget;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wing
 * @create 2020/11/7 16:40
 * @desc
 */
@Data
public class AdvertiseUpdateQryRspVo {
    List<OrderAdvertiseDelivery> orderAdvertiseDelivery = new ArrayList();
    List<OrderAdvertiseAttach> orderAdvertiseAttach = new ArrayList();
    List<OrderDeliveryArea> orderDeliveryArea = new ArrayList();
    List<OrderDeliveryTarget> orderDeliveryTarget = new ArrayList();
}
