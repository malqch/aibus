package com.wntime.event.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 事件采集表
 * @date 2020-08-25 13:34:24
 */
@Data
@TableName("info_collect_event")
public class InfoCollectEventEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * @desc 主键
     */
    @TableId(value = "collect_event_id", type = IdType.ID_WORKER)
    private Long collectEventId;
    /**
     * @desc 采集内容
     */
    @NotBlank(message = "采集内容不能为空")
    private String collectEvent;
    /**
     * @desc 采集编码
     */
    @NotBlank(message = "采集编码不能为空")
    @Size(max = 64, message = "事件类型名称长度应小于20")
    private String collectCode;
    /**
     * @desc 事件类型Id
     */
    @NotNull(message = "事件类型")
    private Long eventTypeId;
    /**
     * @desc 事件标签Id
     */
    private Long eventTargetId;
    /**
     * @desc 事件级别Id
     */
    private Long eventLevelId;
    /**
     * @desc 设备类型Id
     */
    private Long deviceTypeId;
    /**
     * @desc 事件大类
     */
    @NotNull
    private Integer eventType;
    /**
     * @desc 事件小类
     */
    @NotNull
    private Integer eventDetail;
    /**
     * @desc 是否删除
     */
    private Integer isDeleted;
    /**
     * @desc 是否启用
     */
    @Range(min = 0, max = 1,message = "是否启用只有是否两个值")
    private Integer isEnabled;
    /**
     * @desc 创建人
     */
    private Long createdBy;
    /**
     * @desc 创建时间
     */
    private Timestamp createdDate;
    /**
     * @desc 修改人
     */
    private Long modifiedBy;
    /**
     * @desc 修改时间
     */
    private Timestamp modifiedDate;

}
