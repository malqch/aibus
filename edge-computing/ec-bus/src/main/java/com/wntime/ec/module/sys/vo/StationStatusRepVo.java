package com.wntime.ec.module.sys.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StationStatusRepVo {
    private int status;
    private int direction;
    private int id_terminal;
    private int is_first_station;
}
