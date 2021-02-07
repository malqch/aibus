package com.wntime.event.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * @author ysc
 * 2020/9/8 11:35
 */
@Data
public class InfoCollectEventForm {

    /**
     * @desc 主键
     */
    @TableId(value = "collect_event_id", type = IdType.ID_WORKER)
    private Long collectEventId;
    /**
     * @desc 采集内容
     */
    @NotBlank(message = "采集内容不能为空")
    @Size(min = 0,max = 20,message = "采集内容长度应小于20")
    private String collectEvent;
    /**
     * @desc 采集编码
     */
    @Size(min = 0,max = 20,message = "采集编码长度应小于20")
    private String collectCode;
    /**
     * @desc 事件类型Id
     */
    @NotNull(message = "事件类型不能为空")
    private Long eventTypeId;
    /**
     * @desc 事件标签Id
     */
    @NotNull(message = "事件标签不能为空")
    private Long eventTargetId;
    /**
     * @desc 事件级别Id
     */
    @NotNull(message = "事件级别不能为空")
    private Long eventLevelId;
    /**
     * @desc 设备类型Id
     */
    @NotNull(message = "设备类型不能为空")
    private Long deviceTypeId;
    /**
     * @desc 事件大类
     */
    @NotNull(message = "事件大类不能为空")
    private Integer eventType;
    /**
     * @desc 事件小类
     */
    @NotNull(message = "事件小类不能为空")
    private Integer eventDetail;
    /**
     * @desc 是否删除
     */
    private Integer isDeleted;
    /**
     * @desc 是否启用
     */
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

    private Long eventDescriptionId;

    @Size(min = 0,max = 200,message = "事件描述长度应小于200")
    private String descriptionContent;
}
