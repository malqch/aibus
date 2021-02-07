package com.wntime.ec.module.schedule.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wing
 * @create 2020/9/1 15:51
 * @desc
 */
@Data
public class DsmFileDownloadRspVo {
    List<String> image = new ArrayList<>();
    List<String> video = new ArrayList<>();
}
