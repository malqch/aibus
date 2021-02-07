package com.wntime.service.service;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.validator.ValidatorUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.service.entity.InfoBusDeviceEntity;
import com.wntime.service.form.DeviceUploadForm;
import com.wntime.service.vo.UploadDeviceVo;

import java.util.List;
import java.util.Map;

/**
 * @desc AI设备表
 * @date 2020-08-25 14:28:17
 */
public interface InfoBusDeviceService extends IService<InfoBusDeviceEntity> {


    PageUtils queryPage(Map<String, Object> params);

    List<InfoBusDeviceEntity> queryListByBusId(Map<String, Object> params);

    InfoBusDeviceEntity getDetailById(Long id);

    void save(InfoBusDeviceEntity infoBusDeviceEntity, Long userId);

    void updateById(InfoBusDeviceEntity infoBusDeviceEntity, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);

    void updateDeviceStatus(InfoBusDeviceEntity busDevice);

    List<UploadDeviceVo> uploadDevice(DeviceUploadForm deviceUploadForm);
}

