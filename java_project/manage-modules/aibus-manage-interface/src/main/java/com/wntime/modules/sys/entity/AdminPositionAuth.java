package com.wntime.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wntime.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
@TableName("admin_position_auth")
public class AdminPositionAuth extends BaseEntity {
    private static final long serialVersionUID = 5552399196663551489L;

    @TableId(value = "position_auth_id", type = IdType.ID_WORKER)
    private Long positionAuthId;

    private long positionId;

    private Long companyId;

    private String isDeleted;

    private Long createUserId;

    private Timestamp createDt;

    private Long modify_user_id;

    private Timestamp modify_dt;

}
