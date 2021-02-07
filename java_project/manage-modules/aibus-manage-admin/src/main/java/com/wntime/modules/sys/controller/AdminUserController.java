package com.wntime.modules.sys.controller;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.MessageConstant;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.Assert;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.common.validator.group.AddGroup;
import com.wntime.common.validator.group.UpdateGroup;
import com.wntime.controller.AbstractController;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.form.AdminUserFrom;
import com.wntime.modules.sys.form.PasswordForm;
import com.wntime.modules.sys.service.*;
import com.wntime.modules.sys.vo.AdminUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 */
@Api(value = "系统用户管理", tags = {"系统用户管理"})
@RestController
@RequestMapping("/sys/user")
public class
AdminUserController extends AbstractController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminUserRoleService adminUserRoleService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private AdminUserPositionService adminUserPositionService;
    @Resource
    private AdminPositionService adminPositionService;

    /**
     * 所有用户列表
     */

    @ApiOperation(value = "用户列表", httpMethod = "GET", notes = "获取登录用户创建用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "用户名", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "用户名", dataType = "String")
    })
    @GetMapping("/list")
    //@RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        //只有超级管理员，才能查看所有管理员列表
        /*if (getUserId() != Constant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }*/

//		PageUtils page = adminUserService.queryPage(params);
        //两个表关联查询
        PageUtils page = adminUserService.queryPageList(params);
        return R.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @ApiOperation(value = "登录用户信息", httpMethod = "GET", notes = "登录用户信息")
    @GetMapping("/info")
    public R info() {
        //如果可以登录，则用户肯定存在
        return R.ok().put("user",
                getUser());
    }

    /**
     * 修改登录用户密码
     */
    @ApiOperation(value = "修改密码", httpMethod = "POST", notes = "修改密码")
    @SysLog("修改密码")
    @PostMapping("/password")
    public R password(@RequestBody PasswordForm form) {
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");

        //sha256加密
        String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

        //更新密码
        boolean flag = adminUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return R.error(MessageConstant.USER_PASSWORD_ERROR);
        }

        return R.ok();
    }

    /**
     * 用户信息
     */
    @ApiOperation(value = "获取登录用户所属的角色列表", httpMethod = "GET", notes = "获取登录用户所属的角色列表")
    @GetMapping("/info/{userId}")
    //@RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId) {
        AdminUser user = adminUserService.getById(userId);


        if (user != null) {
            //获取用户所属的角色列表
            List<Long> roleIdList = adminUserRoleService.queryRoleIdList(userId);
            List<Long> positionIdList = adminUserPositionService.queryPositionIdList(userId);
            user.setRoleIdList(roleIdList);
            user.setPositionIdList(positionIdList);
            return R.ok().put("user", user);
        }

        return R.error("该用户不存在");
    }


    @ApiOperation(value = "获取用户详细", httpMethod = "GET", notes = "获取用户详细")
    @GetMapping("/detail")
    //@RequiresPermissions("sys:user:info")
    public R detail(@RequestParam(value = "userId", required = false) Long userId) {
        AdminUserVo user;
        if (userId != null) {
            user = adminUserService.queryUserInfo(userId);
        } else {
            user = new AdminUserVo();
        }
        //获取用户所属的角色列表

        user.setRoles(adminRoleService.queryUserRole(userId));
        user.setPositions(adminPositionService.listByUser(userId, getUserId()));
        return R.ok().put("user", user);

    }


    /**
     * 保存用户
     */
    @ApiOperation(value = "保存用户", httpMethod = "POST", notes = "保存用户")
    @SysLog("保存用户")
    @PostMapping("/save")
    //@RequiresPermissions("sys:user:save")
    public R save(@RequestBody AdminUserFrom user) {

        ValidatorUtils.validateEntity(user, AddGroup.class);


        adminUserService.addUser(user, getUserId());
        return R.ok();
    }

    /**
     * 修改用户
     */
    @ApiOperation(value = "修改用户", httpMethod = "POST", notes = "修改用户")
    @SysLog("修改用户")
    @PostMapping("/update")
    public R updateUserRole(@RequestBody AdminUserFrom user) {

        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        adminUserService.updateUser(user, getUserId());
        return R.ok();
    }


    /**
     * 删除用户
     */
    @ApiOperation(value = "删除用户", httpMethod = "POST", notes = "删除用户")
    @SysLog("删除用户")
    @PostMapping("/delete")
    //@RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error(MessageConstant.SYSTEM_NOT_DELETE, "系统管理员");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error(MessageConstant.SYSTEM_NOT_DELETE, "当前用户");
        }
        //删除用户的操作人
        Long userId = getUserId();
        adminUserService.deleteBatch(userIds, userId);

        return R.ok();
    }

    @GetMapping("/tree")
    public R tree() {
        return R.ok().put("list", adminUserService.queryUserList());
    }

    /**
     * 检验1:选择专管、监管、商家、工商所时候必须选择对应的值
     */
    private R validatorRole(AdminUser user) {

        if (!StringUtils.isEmpty(user.getRoleIcabAdminId())) {
            if (StringUtils.isEmpty(user.getRoleIcabAdmin())) return R.error("请选择专管区域");
        }
        if (!StringUtils.isEmpty(user.getRoleIcabCommissionerId())) {
            if (StringUtils.isEmpty(user.getRoleIcabCommissioner())) return R.error("请选择监管区域");
        }
        if (!StringUtils.isEmpty(user.getRoleRestaurantId())) {
            if (StringUtils.isEmpty(user.getRoleRestaurant())) return R.error("请选择一个商家");
        }
        if (!StringUtils.isEmpty(user.getRoleIcabId())) {
            if (StringUtils.isEmpty(user.getRoleIcab())) return R.error("请选择一个工商所");
        }

        //选择专管角色后不能选择其它角色
        if (!StringUtils.isEmpty(user.getRoleIcabAdminId())) {
            if (!StringUtils.isEmpty(user.getRoleIcabCommissionerId()) || !StringUtils.isEmpty(user.getRoleRestaurantId())
                    || !StringUtils.isEmpty(user.getRoleIcabId()) || user.getRoleOther().size() > 0) {
                return R.error("选择专管角色后不能选择其它角色!");
            }
        }
        //选择监管角色后不能选择其它角色
        if (!StringUtils.isEmpty(user.getRoleIcabCommissionerId())) {
            if (!StringUtils.isEmpty(user.getRoleIcabAdminId()) || !StringUtils.isEmpty(user.getRoleRestaurantId())
                    || !StringUtils.isEmpty(user.getRoleIcabId()) || user.getRoleOther().size() > 0) {
                return R.error("选择监管角色后不能选择其它角色!");
            }
        }

        if (!StringUtils.isEmpty(user.getRoleIcabAdminId()) || !StringUtils.isEmpty(user.getRoleIcabCommissionerId())
                || !StringUtils.isEmpty(user.getRoleRestaurantId()) || !StringUtils.isEmpty(user.getRoleIcabId())
                || user.getRoleOther() != null && user.getRoleOther().size() > 0) {
            logger.info("通过角色校验");
            return null;
        }

        return R.error("请选择一个角色");
    }

    /**
     * @param companyId 根据公司对应查询销售员ID
     * @return
     */
    @GetMapping("/getByOrderSell")
    @ApiOperation(value = "根据公交公司查询对应公司的销售员ID列表", httpMethod = "GET", notes = "根据公交公司查询对应公司的销售员ID列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公交公司ID", dataType = "int")
    })
    public R getByOrderSells(Long companyId) {
        logger.info("根据公交公司查询对应公司的销售员ID列表", companyId);
        List<AdminUser> list = adminUserPositionService.getByOrderSells(companyId);
        return R.ok().put("selection", list);
    }

}
