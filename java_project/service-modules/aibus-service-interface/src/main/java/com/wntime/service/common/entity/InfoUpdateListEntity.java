package com.wntime.service.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author peishuaipeng
 * 更新信息表
 */
@Data
@TableName("info_update_list")
public class InfoUpdateListEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "update_list_id", type = IdType.ID_WORKER)

    private Long updateListId;

    @TableField(exist = false)
    private String createUserName;

    @TableField(exist = false)
    private String modifiedUserName;

    /**
     * 更新类型
     */

    private String updateType;
    /**
     * 更新描述
     */

    private String updateDesc;
    /**
     * 文件路径
     */

    private String updateUrl;
    /**
     * 是否发布
     */

    private Integer isPublished;

    /**
     * 发布时间
     */

    private Timestamp publishDate;

    private Integer isDeleted;

    private String originalFileName;

    /**
     * 创建人
     */

    private Long createdBy;
    /**
     * 创建时间
     */

    private Timestamp createdDate;
    /**
     * 修改人
     */

    private Long modifiedBy;
    /**
     * 修改时间
     */

    private Timestamp modifiedDate;

}
