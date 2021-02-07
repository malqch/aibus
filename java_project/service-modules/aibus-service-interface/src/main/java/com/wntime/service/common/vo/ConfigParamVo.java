package com.wntime.service.common.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/9/1 10:19
 */
@Setter
@Getter
public class ConfigParamVo {

    private Long configParamId;

    private String paramName;

    private String paramCode;

    private String paramGroup;

    private Double paramValue;

    private String paramChar;
}
