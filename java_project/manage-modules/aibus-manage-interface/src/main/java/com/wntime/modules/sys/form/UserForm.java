

package com.wntime.modules.sys.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 登录表单
 */

@ApiModel(value="用户对象",description="用户对象表单")
public class UserForm implements Serializable {

    private static final long serialVersionUID = 8185297053049427374L;
    @ApiModelProperty(value="用户名",name="userName",example="张三")
    private String userName;
    @ApiModelProperty(value="登录名",name="loginName",example="tom")
    private String loginName;
    @ApiModelProperty(value="密码",name="password",example="password")
    private String password;
    @ApiModelProperty(value="密码",name="email",example="tom@163.com")
    private String email;
    @ApiModelProperty(value="手机号",name="mobile",example="13612345678")
    private String mobile;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
