package com.wntime.ec.module.gateway.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wing
 * @create 2020/9/2 13:50
 * @desc
 */
@Data
public class LiveQryRspWrapVo {
    List<LiveQryRspVo> list = new ArrayList();
}
