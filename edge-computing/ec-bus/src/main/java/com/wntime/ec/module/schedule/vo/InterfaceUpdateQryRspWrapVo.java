package com.wntime.ec.module.schedule.vo;

import com.wntime.ec.module.sys.entity.InfoUpdateList;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wing
 * @create 2019-12-27 10:17
 */
@Data
public class InterfaceUpdateQryRspWrapVo {
    List<InfoUpdateList> list = new ArrayList<>();
}
