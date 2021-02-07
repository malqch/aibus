package com.wntime.service.common.service;

import com.wntime.service.common.vo.ConfigParamVo;

import java.util.Optional;

public interface ConfigParamService {

    Optional<ConfigParamVo> queryConfigByGroupCode(String group, String code);
}
