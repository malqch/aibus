package com.wntime.service.form;

import com.wntime.service.common.form.CarStatusInfoForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author 79448
 * @date 2020/8/26 10:48
 */
@Setter
@Getter
@ApiModel
public class ReportBusStatusForm extends CarStatusInfoForm {
    @PositiveOrZero(message = "公交车id不能为负数")
    @NotNull(message = "公交车id不能为空")
    @ApiModelProperty(value = "公交车id",required = true,dataType = "java.long.Long",allowEmptyValue = false)
    private Long busId;
}
