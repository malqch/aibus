package com.wntime.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wntime.common.validator.group.AddGroup;
import com.wntime.common.validator.group.UpdateGroup;
import com.wntime.dto.RoleOther;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;

@ApiModel(value = "用户对象", description = "用户表单")
@TableName("admin_user")
public class AdminUser {
    private static final long serialVersionUID = 1102656224856551056L;

    @TableId(value = "user_id", type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    //专管员
    @TableField(exist = false)
    private String roleIcabAdmin;

    @TableField(exist = false)
    private String roleIcabAdminId;

    //监管员
    @TableField(exist = false)
    private String roleIcabCommissioner;

    @TableField(exist = false)
    private String roleIcabCommissionerId;


    //工商所
    @TableField(exist = false)
    private String roleIcab;

    @TableField(exist = false)
    private String roleIcabId;

    //商家Id
    @TableField(exist = false)
    private String roleRestaurant;

    //商家角色Id
    @TableField(exist = false)
    private String roleRestaurantId;

    //其它角色
    @TableField(exist = false)
    private List<RoleOther> roleOther;

    @TableField(exist = false)
    private List<Long> roleIdList;
    @TableField(exist = false)
    private List<Long> positionIdList;
    @TableField(exist = false)
    private List<Long> areaOrgIds;

    private String salt;
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
    private String mobile;
    private String isEnabled;
    private String isDeleted;
    private Long createUserId;
    private Timestamp createDt;
    private Long modifyUserId;
    private Timestamp modifyDt;

//    private Long businessObjectId;
    //token
    @TableField(exist = false)
    private String token;
    //过期时间
    @TableField(exist = false)
    private Long tokenExpireTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long UserId) {
        this.userId = UserId;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public List<Long> getPositionIdList() {
        return positionIdList;
    }

    public void setPositionIdList(List<Long> positionIdList) {
        this.positionIdList = positionIdList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String UserName) {
        this.userName = UserName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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


    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(Long tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Timestamp getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Timestamp createDt) {
        this.createDt = createDt;
    }

    public Long getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public Timestamp getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(Timestamp modifyDt) {
        this.modifyDt = modifyDt;
    }

    /*public Long getBusinessObjectId() {
        return businessObjectId;
    }

    public void setBusinessObjectId(Long businessObjectId) {
        this.businessObjectId = businessObjectId;
    }*/

    public String getRoleIcabAdmin() {
        return roleIcabAdmin;
    }

    public void setRoleIcabAdmin(String roleIcabAdmin) {
        this.roleIcabAdmin = roleIcabAdmin;
    }

    public String getRoleIcabCommissioner() {
        return roleIcabCommissioner;
    }

    public void setRoleIcabCommissioner(String roleIcabCommissioner) {
        this.roleIcabCommissioner = roleIcabCommissioner;
    }

    public String getRoleIcab() {
        return roleIcab;
    }

    public void setRoleIcab(String roleIcab) {
        this.roleIcab = roleIcab;
    }

    public String getRoleRestaurant() {
        return roleRestaurant;
    }

    public void setRoleRestaurant(String roleRestaurant) {
        this.roleRestaurant = roleRestaurant;
    }

    public List<RoleOther> getRoleOther() {
        return roleOther;
    }

    public void setRoleOther(List<RoleOther> roleOther) {
        this.roleOther = roleOther;
    }

    public String getRoleIcabAdminId() {
        return roleIcabAdminId;
    }

    public void setRoleIcabAdminId(String roleIcabAdminId) {
        this.roleIcabAdminId = roleIcabAdminId;
    }

    public String getRoleIcabCommissionerId() {
        return roleIcabCommissionerId;
    }

    public void setRoleIcabCommissionerId(String roleIcabCommissionerId) {
        this.roleIcabCommissionerId = roleIcabCommissionerId;
    }

    public String getRoleIcabId() {
        return roleIcabId;
    }

    public void setRoleIcabId(String roleIcabId) {
        this.roleIcabId = roleIcabId;
    }

    public String getRoleRestaurantId() {
        return roleRestaurantId;
    }

    public void setRoleRestaurantId(String roleRestaurantId) {
        this.roleRestaurantId = roleRestaurantId;
    }

    public List<Long> getAreaOrgIds() {
        return areaOrgIds;
    }

    public void setAreaOrgIds(List<Long> areaOrgIds) {
        this.areaOrgIds = areaOrgIds;
    }
}
