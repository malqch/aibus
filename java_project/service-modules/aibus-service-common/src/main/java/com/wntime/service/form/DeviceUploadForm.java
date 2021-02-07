package com.wntime.service.form;

import com.wntime.service.vo.DeviceUploadItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 79448
 * @date 2020/8/31 14:01
 */
@Setter
@Getter
public class DeviceUploadForm {
    @ApiModelProperty(value = "公交车id",required = true)
    @NotNull(message = "公交车id不能为空")
    @Positive(message = "公交车id必须大于0")
    private Long busId;
    @NotNull(message = "设备列表不能为空")
    @Size(min = 1,message = "至少上传一个设备")
    private List<DeviceUploadItem> devices;
}
