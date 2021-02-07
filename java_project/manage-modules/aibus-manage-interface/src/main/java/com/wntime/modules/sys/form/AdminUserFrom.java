package com.wntime.modules.sys.form;

import com.wntime.common.validator.group.AddGroup;
import com.wntime.common.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
public class AdminUserFrom {

    private Long userId;

    private List<Long> roleIdList;

    @Size(min = 1,message = "至少勾选一个岗位", groups = {AddGroup.class, UpdateGroup.class})
    private List<PositionObjectId> positionIdList;

    @ApiModelProperty(value = "用户名", name = "userName", example = "张三")
    @NotBlank(message = "用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String userName;

    @ApiModelProperty(value = "登录名", name = "loginName", example = "tom")
    @NotBlank(message = "登录名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String loginName;

    @ApiModelProperty(value = "密码", name = "password", example = "password")
    @NotBlank(message = "密码不能为空", groups = AddGroup.class)
    private String password;

    @ApiModelProperty(value = "邮件", name = "email", example = "tom@163.com")
    @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Email(message = "邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
    private String email;

    @NotBlank(message = "手机号不能为空",groups = {AddGroup.class})
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码不正确",groups = {AddGroup.class,UpdateGroup.class})
    private String mobile;

    private String isEnabled;
    private String isDeleted;


    @Setter
    @Getter
    public static class PositionObjectId{
        private Long positionAuthId;
        private long positionId;
        private Long busiObjectId;

        public PositionObjectId() {
        }

        public PositionObjectId(Long positionAuthId, long positionId, Long busiObjectId) {
            this.positionAuthId = positionAuthId;
            this.positionId = positionId;
            this.busiObjectId = busiObjectId;
        }
    }
}