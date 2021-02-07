

package com.wntime.modules.sys.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 登录表单
 */

@ApiModel(value="登录表单对象",description="登录表单")
public class SysLoginForm implements Serializable {
    private static final long serialVersionUID = 7985200834484139745L;

    @ApiModelProperty(value="用户名",name="username",example="admin")
    private String loginName;
    @ApiModelProperty(value="密码",name="password",example="admin")
    private String password;

    @ApiModelProperty(value="验证码",name="captcha",example="4fp5e")
    private String captcha;

    @ApiModelProperty(value="UUID",name="uuid",example="3b457de0-3150-41db-8974-c4be989217bd")
    private String uuid;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
