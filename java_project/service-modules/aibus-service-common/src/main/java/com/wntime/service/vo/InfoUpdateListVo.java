package com.wntime.service.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author peishuaipeng
 */

@Data
public class InfoUpdateListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String updateListId;


    private String createUserName;


    private String modifiedUserName;

    /**
     * 更新类型
     */

    @NotNull(message = "更新类型不能为空")
    private String updateType;

    private String updateTypeName;
    /**
     * 更新描述
     */

    @NotEmpty(message = "更新描述不能为空")
    @Size(max = 128, message = "更新描述长度应小于128")
    private String updateDesc;
    /**
     * 文件路径
     */

    private String updateUrl;
    /**
     * 是否发布
     */

    private Integer isPublished;

    private Integer isDeleted;

    private String originalFileName;

    private Timestamp publishDate;
    /**
     * 创建人
     */

    private String createdBy;
    /**
     * 创建时间
     */

    private Timestamp createdDate;
    /**
     * 修改人
     */

    private String modifiedBy;
    /**
     * 修改时间
     */

    private Timestamp modifiedDate;

}
