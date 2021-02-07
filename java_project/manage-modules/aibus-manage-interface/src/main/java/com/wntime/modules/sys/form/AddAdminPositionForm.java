package com.wntime.modules.sys.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
public class AddAdminPositionForm {
    private Long positionId;
    @NotBlank(message = "岗位名不能为空")
    @Size(max = 32,message = "岗位名不能超过32个字符")
    private String name;
    @NotBlank(message = "岗位描述不能为空")
    @Size(max = 128,message = "岗位描述不能超过128个字符")
    private String description;
    @NotBlank(message = "授权范围不能为空")
    private String systemAuth;//系统授权范围manage\monitorz\monitorj\webapp\edge

    private String isEnabled;
   //  @Size(max= 1,message = "最多赋予一项授权")
    private List<PositionAuthFrom> auth;
}
