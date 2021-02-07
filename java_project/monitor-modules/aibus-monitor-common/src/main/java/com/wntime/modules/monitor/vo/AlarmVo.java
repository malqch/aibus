package com.wntime.modules.monitor.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/10/30 15:35
 */
@Setter
@Getter
public class AlarmVo {
    /**
     * 灾害预警
     * 四川省乐山市气象台发布大雾黄色预警
     */
    private String title;
    /**
     * 大雾,
     */
    private String type;
    /**
     * 黄色,
     */
    private String level;
    /**
     * 预警中(V3版本默认为空),
     */
    private String status;
    /**
     * 我县出现能见度小于500米、大于等于200米的浓雾并将持续至10时左右，请注意交通安全，减少户外活动，预防浓雾带来的不利影响。,
     */
    private String description;
    /**
     * 2015-09-23T07:02:00+08:00（各级政府发布预警时间）
     */
    @JsonProperty("pub_date")
    private String pubDate;
}
