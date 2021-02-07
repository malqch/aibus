package com.wntime.service.common.service;

import com.itextpdf.text.List;
import com.wntime.service.common.form.OrderBusDeliveryBatchForm;
import com.wntime.service.common.vo.BusBaseInfoVo;
import com.wntime.service.common.vo.BusInfoBatteryTypeVo;

/**
 * @author 79448
 * @date 2020/8/26 16:08
 */
public interface BusBatteryInfoService {

    void saveImportBusBatteryInfo(OrderBusDeliveryBatchForm orderBusDeliveryBatchForm);
}
