package com.wntime.modules.sys.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class AdminUserPositionVo {
    @JsonSerialize(using = LongToStringSerialize.class)
    private long positionId;
    private String positionName;
    private int selected;

    private int isSelfonly;//0 1

    @JsonIgnore
    private long areaOrgId;
    @JsonIgnore
    private long positionAuthId;
    private List<IcabCommissioner> busiObjectId=new ArrayList<>();  //select

    @Setter
    @Getter
    public static class IcabCommissioner{
        @JsonSerialize(using = LongToStringSerialize.class)
        private long icabId;
        @JsonSerialize(using = LongToStringSerialize.class)
        private long positionAuthId;
        private int isSelfonly;
        private String label;
        private List<Commissioner> options;

        public IcabCommissioner(long icabId,long positionId,long positionAuthId, String label, Long icabCommId,String commName,int isSelfOnly,int selected) {
            this.icabId = icabId;
            this.label = label;
            this.positionAuthId=positionAuthId;
            this.isSelfonly=isSelfOnly;
            this.options = new ArrayList<>();
            if(icabCommId!=null){
                this.options.add(new Commissioner(icabId,icabCommId,commName,selected,positionId,positionAuthId));
            }

        }
    }

    @Setter
    @Getter
    public static class Commissioner{
        @JsonSerialize(using = LongToStringSerialize.class)
        private long icabId;
        @JsonSerialize(using = LongToStringSerialize.class)
        private long value;
        private String label;
        private int selected;
        @JsonSerialize(using = LongToStringSerialize.class)
        private long positionAuthId;
        @JsonSerialize(using = LongToStringSerialize.class)
        private long positionId;


        public Commissioner(long icabId,long value, String label,int selected,long positionId,long positionAuthId) {
            this.icabId=icabId;
            this.value = value;
            this.label = label;
            this.selected=selected;
            this.positionId=positionId;
            this.positionAuthId=positionAuthId;
        }
    }
}


