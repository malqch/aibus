package com.wntime.entity;

import com.wntime.common.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.sql.Timestamp;

public class BaseEntity implements Serializable {

    @ApiModelProperty(value="创建人ID",name="createUserId",example="1")
    @Null(message="创建人ID必须为空", groups = {UpdateGroup.class})
    private Long createUserId;

    @ApiModelProperty(value="创建时间",name="createDt",example="2019-09-24 13:25:01")
    @Null(message="创建时间必须为空", groups = {UpdateGroup.class})
    private Timestamp createDt;

    @ApiModelProperty(value="修改人ID",name="modifyUserId",example="1")
    private Long modifyUserId;

    @ApiModelProperty(value="修改时间",name="modifyDt",example="2019-09-24 13:25:01")
    private Timestamp modifyDt;

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Timestamp getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Timestamp createDt) {
        this.createDt = createDt;
    }

    public Long getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public Timestamp getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(Timestamp modifyDt) {
        this.modifyDt = modifyDt;
    }
}
