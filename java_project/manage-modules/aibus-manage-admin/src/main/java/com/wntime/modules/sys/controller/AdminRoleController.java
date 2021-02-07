

package com.wntime.modules.sys.controller;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.*;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.modules.sys.entity.AdminRole;
import com.wntime.modules.sys.service.AdminRoleRightService;
import com.wntime.modules.sys.service.AdminRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色管理
 */
@Api(value = "角色管理接口", tags = {"系统角色管理"})
@RestController
@RequestMapping("/sys/role")
public class AdminRoleController extends AbstractController {
    @Autowired
    private AdminRoleService adminRoleService;
    @Autowired
    private AdminRoleRightService adminRoleRightService;

    /**
     * 角色列表
     */
    @ApiOperation(value = "角色列表", httpMethod = "GET", notes = "获取用户创建的角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "用户名", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "用户名", required = false, dataType = "String")
    })
    @GetMapping("/list")
    //@RequiresPermissions("sys:role:list")
    public R list(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己创建的角色列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            params.put("create_user_id", getUserId());
        }

        List<Long> owerRoleList = new ArrayList<>();
        PageUtils page = adminRoleService.queryPageList(params);

        /*if (getUserId() != Constant.SUPER_ADMIN) {
            owerRoleList = adminRoleService.queryOwnerCreate(getUserId());
        }*/

        return R.ok().put("page", page).put("owerRoleList", owerRoleList).put("userId", getUserId());
    }

    /**
     * 角色列表
     */
    @ApiOperation(value = "拥有角色列表", httpMethod = "GET", notes = "查询用户所拥有的角色列表")
    @GetMapping("/select/{userId}")
//	@RequiresPermissions("sys:role:select")
    public R select(@PathVariable("userId") Long userId) {
        Map<String, Object> map = new HashMap<>();


        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            map.put("create_user_id", getUserId());
            map.put("user_id", userId);
        }
        if (userId == 0) {
            map.remove("user_id");
        }
        List<AdminRole> list = adminRoleService.listByUser(map);

        return R.ok().put("list", list);
    }

    @ApiOperation(value = "查询当前登录用户角色", httpMethod = "GET", notes = "查询当前登录用户角色")
    @GetMapping("/selectUserRole")
    public R selectUserRole() {

        List<AdminRole> list = adminRoleService.selectUserRole(getUserId());

        return R.ok().put("list", list);
    }

    /**
     * 角色信息
     */
    @ApiOperation(value = "菜单信息", httpMethod = "GET", notes = "获取菜单信息")
    @GetMapping("/info/{roleId}")
    //@RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId) {
        AdminRole role = adminRoleService.getById(roleId);


        if (role != null) {

            List<Long> menuIdList = adminRoleRightService.queryRightIdList(roleId);
//            role.setRightIdList(menuIdList);

            List<String> menuIdListStr = menuIdList.stream().map(s -> s.toString()).collect(Collectors.toList());

            role.setRightIdList(menuIdListStr);

            return R.ok().put("role", role);
        }

        return R.error("修改角色不存在");
        //查询角色对应的菜单
    }

    /**
     * 保存角色
     */
    @ApiOperation(value = "添加角色", httpMethod = "POST", notes = "")
    @SysLog("保存角色")
    @PostMapping("/save")
    //@RequiresPermissions("sys:role:save")
    public R save(@RequestBody AdminRole role) {
        ValidatorUtils.validateEntity(role);
        //判断角色是否存在
        List<AdminRole> AdminRoles = adminRoleService.queryRoleIsExist(role);
        if (AdminRoles != null && AdminRoles.size() > 0) {
            return R.error(MessageConstant.SC_NAME_EXIST, "角色名");
        } else {

            role.setRoleId(null);
            role.setCreateUserId(getUserId());
            role.setCreateDt(DateUtils.getTimestamp());
            //"1" 代表true   "0"代表false
            role.setIsEnabled("1");
            role.setIsDeleted("0");
            adminRoleService.saveRole(role, getUserId());

            return R.ok();
        }


    }

    /**
     * 修改角色
     */
    @ApiOperation(value = "修改角色", httpMethod = "POST", notes = "")
    @SysLog("修改角色")
    @PostMapping("/update")
    //@RequiresPermissions("sys:role:update")
    public R update(@RequestBody AdminRole role) {
        ValidatorUtils.validateEntity(role);
        List<AdminRole> AdminRoles = adminRoleService.queryRoleIsExist(role);
        if (AdminRoles != null && AdminRoles.size() > 0) {
            for (int i = 0; i < AdminRoles.size(); i++) {
                if (AdminRoles.get(i).getRoleId().equals(role.getRoleId())) {
                    role.setModifyUserId(getUserId());
                    role.setModifyDt(DateUtils.getTimestamp());
                    //"1" 代表true   "0"代表false
                    role.setIsEnabled("1");
                    role.setIsDeleted("0");
                    adminRoleService.update(role, getUserId());
                    return R.ok();
                }
            }


            return R.error(MessageConstant.SC_NAME_EXIST, "角色名");
        } else {
            role.setModifyUserId(getUserId());
            role.setModifyDt(DateUtils.getTimestamp());
            //"1" 代表true   "0"代表false
            role.setIsEnabled("1");
            role.setIsDeleted("0");
            adminRoleService.update(role, getUserId());

            return R.ok();
        }
    }

    /**
     * 删除角色
     */
    @ApiOperation(value = "删除角色", httpMethod = "POST", notes = "")
    @SysLog("删除角色")
    @PostMapping("/delete")
    //@RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody Long[] roleIds) {
        //操作人id
        Long userId = getUserId();

        //删除角色，不是真正的删除，只需要更改字段即可
        adminRoleService.deleteBatch(roleIds, userId);

        return R.ok();
    }
}
