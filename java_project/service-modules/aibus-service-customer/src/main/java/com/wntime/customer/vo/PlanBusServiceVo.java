package com.wntime.customer.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wntime.customer.entity.PlanBusServiceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ysc
 * 2020/9/9 19:19
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class PlanBusServiceVo extends PlanBusServiceEntity {

    private Long busTypeId;
    private String busTypeName;
    private String vinCode;
    private String createdUserName;
    private String modifiedUserName;
}
