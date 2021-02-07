package com.wntime.service.common.service;

import java.util.List;

/**
 * @author ysc
 * 2020/9/23 14:43
 */

public interface AreaInfoService {

    /**
     * 获取某区域的所有下级区域
     * @return
     */
    List<Long> getSubAreaIdList(Long areaId);

    String getAreaFullName(Long areaId);

    Long getInfoAreaIdByName(String areaName);

}
