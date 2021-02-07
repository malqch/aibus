package com.wntime.service.vo;

import lombok.Data;

/**
 * @author ysc
 * 2020/8/29 13:20
 */
@Data
public class InputItem {

    private String name;
    private String displayName;
    private Object value;
    private boolean canEdit;

    public InputItem() {
    }

    public InputItem(String name, String displayName, Object value, boolean canEdit) {
        this.name = name;
        this.displayName = displayName;
        this.value = value;
        this.canEdit = canEdit;
    }
}
