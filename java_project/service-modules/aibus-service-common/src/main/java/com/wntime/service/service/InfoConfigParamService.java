package com.wntime.service.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.service.entity.InfoConfigParamEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 配置参数表
 * @date 2020-08-25 14:28:17
 */
public interface InfoConfigParamService extends IService<InfoConfigParamEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<InfoConfigParamEntity> getListByParamGroup(String paramGroup);

    InfoConfigParamEntity getDetailById(Long id);

    List<InfoConfigParamEntity> getConfigBusMilesList();

    void save(InfoConfigParamEntity infoConfigParam, Long userId);

    void updateById(InfoConfigParamEntity infoConfigParam, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);

    InfoConfigParamEntity getFaultTypeKnowledge();
}

