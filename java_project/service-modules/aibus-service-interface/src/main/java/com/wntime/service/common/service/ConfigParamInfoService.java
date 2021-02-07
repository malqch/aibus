package com.wntime.service.common.service;

import com.wntime.service.common.vo.ConfigParamVo;
import com.wntime.service.entity.InfoConfigParamEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author ysc
 * 2020/11/5 15:03
 */
public interface ConfigParamInfoService {

    List<InfoConfigParamEntity> getListByParamGroup(String paramGroup);

    InfoConfigParamEntity getDetailById(Long configId);

    Optional<ConfigParamVo> queryConfigByGroupCode(String group, String code);
}
