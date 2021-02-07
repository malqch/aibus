package com.wntime.ec.module.schedule.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wing
 * @create 2020/9/1 14:58
 * @desc
 */
@Data
public class DsmAlarmRspWrapVo {
    private Long count;
    private List<DsmAlarmRspVo> list = new ArrayList<>();
}


