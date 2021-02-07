package com.wntime.service.vo;

import lombok.Data;

/**
 * @author ysc
 * 2020/8/26 10:17
 */
@Data
public class ProgressBarItem {

    private Long id;
    private String name;
    private int total;
    private int number;
}
