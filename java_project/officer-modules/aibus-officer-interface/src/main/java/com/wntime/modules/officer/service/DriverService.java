package com.wntime.modules.officer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.modules.officer.dto.DriverInfoDto;
import com.wntime.modules.officer.entity.DriverEntity;
import com.wntime.modules.officer.from.DriverFrom;

import java.util.List;
import java.util.Map;

/**
 * 驾驶员信息表
 *
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
public interface DriverService extends IService<DriverEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<DriverEntity> getPrimaryDriverList();

    void saveDriverInfo(DriverFrom driver);

    DriverInfoDto queryOne(Long id);

    /***
     * @Author Buxl
     * @Description 根据证件号查询司机信息
     * @Date 14:13 2021/1/23
     * @Param [idNo]
     * @return com.wntime.modules.officer.entity.DriverEntity
     **/
    DriverEntity getDriverByIdNo(String idNo);
}

