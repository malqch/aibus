package com.wntime.event.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 79448
 * @date 2020/8/28 19:58
 */
@Setter
@Getter
public class AlarmInfoItem implements Comparable<AlarmInfoItem>{

    @JsonSerialize(using = LongToStringSerialize.class)
    private long eventTypeId;

    private String eventTypeCode;

    private String type;

    private String desc;

    private String level;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventDate;

    private List<String> images;


    public AlarmInfoItem() {
        this.images=new ArrayList<>();
    }

    public void addImage(String image){
        this.images.add(image);
    }

    @Override
    public int compareTo(AlarmInfoItem o) {
        int compare=o.getEventDate().compareTo(this.eventDate);
        return compare==0?1:compare;
    }
}
