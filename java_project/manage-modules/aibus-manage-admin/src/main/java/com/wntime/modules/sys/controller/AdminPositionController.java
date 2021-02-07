

package com.wntime.modules.sys.controller;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.*;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.modules.sys.entity.AdminPosition;
import com.wntime.modules.sys.form.AddAdminPositionForm;
import com.wntime.modules.sys.service.AdminPositionAuthService;
import com.wntime.modules.sys.service.AdminPositionService;
import com.wntime.modules.sys.vo.AdminPositionAuthVo;
import com.wntime.modules.sys.vo.AdminPositionVo;
import com.wntime.modules.sys.vo.AdminUserPositionVo;
import com.wntime.modules.sys.vo.BusiObjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 角色管理
 */
@Api(value = "角色岗位接口", tags = {"系统岗位管理"})
@RestController
@RequestMapping("/sys/position")
public class AdminPositionController extends AbstractController {
    @Autowired
    private AdminPositionService adminPositionService;

    @Autowired
    private AdminPositionAuthService adminPositionAuthService;

    /**
     * 岗位列表
     */
    @ApiOperation(value = "岗位列表", httpMethod = "GET", notes = "获取用户创建的岗位列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "用户名", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "用户名", required = false, dataType = "String")
    })
    @GetMapping("/list")
    //@RequiresPermissions("sys:position:list")
    public R list(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己创建的岗位列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            params.put("create_user_id", getUserId());
        }
        List<Long> owerPositionList = new ArrayList<>();
        /*if (getUserId() != Constant.SUPER_ADMIN) {
            owerPositionList = adminPositionService.queryOwnerCreate(getUserId());
        }*/
        PageUtils page = adminPositionService.queryPageList(params);
        return R.ok().put("page", page).put("owerPositionList", owerPositionList).put("userId", getUserId());
    }

    /**
     * 岗位列表
     */
    @ApiOperation(value = "拥有岗位列表", httpMethod = "GET", notes = "查询用户所拥有的角色列表")
    @GetMapping("/select/user")
//	@RequiresPermissions("sys:role:select")
    public R select(@RequestParam(value = "userId", required = false) Long userId) {
        Collection<AdminUserPositionVo> list = adminPositionService.listByUser(userId, getUserId());

        return R.ok().put("list", list);
    }

    /**
     * 岗位信息--获取岗位对应的集群列表
     */
    @ApiOperation(value = "查看岗位详情", httpMethod = "GET", notes = "包含岗位的组织权限")
    @GetMapping("/info")
    //@RequiresPermissions("sys:position:info")
    public R info(@RequestParam(value = "positionId", required = false) Long positionId) {
        //对于修改的来说，自己拥有的和
        AdminPosition position = adminPositionService.getById(positionId);
        AdminPositionVo vo = new AdminPositionVo();
        if (position != null) {
            BeanUtils.copyProperties(position, vo);
            vo.setIsEnabled(Integer.parseInt(position.getIsEnabled()));
            vo.setIsDeleted(Integer.parseInt(position.getIsDeleted()));
        }
        //查询岗位所有的权限
        List<AdminPositionAuthVo> list = adminPositionAuthService.queryPositionAuthWithUserId(positionId);
        vo.setPositionAuth(list);
        return R.ok().put("position", vo);
    }

    /**
     * 岗位信息--获取岗位权限对应的授权公司列表
     */
    @ApiOperation(value = "查看岗位详情", httpMethod = "GET", notes = "包含岗位的组织权限")
    @GetMapping("/infoCompanyListByPer")
    //@RequiresPermissions("sys:position:info")
    public R infoCompanyListByPer(@RequestParam Map<String, Object> params) {
        //查询岗位所有的权限
        List<AdminPositionAuthVo> list = adminPositionAuthService.queryPositionAuthWithClique(params);
        return R.ok().put("list", list);
    }

    /**
     * 保存岗位
     */
    @ApiOperation(value = "添加岗位", httpMethod = "POST", notes = "")
    @SysLog("保存岗位")
    @PostMapping("/save")
    //@RequiresPermissions("sys:position:save")
    public R save(@RequestBody AddAdminPositionForm position) {
        ValidatorUtils.validateEntity(position);

        adminPositionService.savePosition(position, getUserId());
        return R.ok();
    }

    /**
     * 修改岗位
     */
    @ApiOperation(value = "修改岗位", httpMethod = "POST", notes = "")
    @SysLog("修改岗位")
    @PostMapping("/update")
    //@RequiresPermissions("sys:position:update")
    public R update(@RequestBody AddAdminPositionForm position) {
        ValidatorUtils.validateEntity(position);
        adminPositionService.savePosition(position, getUserId());
        return R.ok();
    }

    /**
     * 删除岗位
     */
    @ApiOperation(value = "删除岗位", httpMethod = "POST", notes = "")
    @SysLog("删除岗位")
    @PostMapping("/delete")
    //@RequiresPermissions("sys:position:delete")
    public R delete(@RequestBody Long[] positionIds) {
        //操作人id
        Long userId = getUserId();

        //删除岗位和岗位授权，不是真正的删除，只需要更改字段即可
        adminPositionService.deleteBatch(positionIds, userId);
        return R.ok();
    }

}
