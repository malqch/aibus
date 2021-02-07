

package com.wntime.modules.sys.controller;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.MessageConstant;
import com.wntime.common.utils.R;
import com.wntime.controller.AbstractController;
import com.wntime.modules.sys.entity.AdminRight;
import com.wntime.modules.sys.service.AdminRightService;
import com.wntime.modules.sys.service.ShiroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 系统菜单
 */
@Api(value = "系统菜单接口", tags = {"系统菜单接口"})
@RestController
@RequestMapping("/sys/right")
public class AdminRightCntroller extends AbstractController {
    @Autowired
    private AdminRightService adminRightService;
    @Autowired
    private ShiroService shiroService;

    /**
     * 导航菜单
     */
    @ApiOperation(value = "导航列表", notes = "根据用户ID获取导航列表权限", httpMethod = "GET")
    @GetMapping("/nav")
    public R nav() {
        //查询用户拥有的菜单
        List<AdminRight> rightList = adminRightService.getUserRightList(getUserId());
        //查询用户拥有的权限
        Set<String> permissions = shiroService.getUserPermissions(getUserId());
        return R.ok().put("rightList", rightList).put("permissions", permissions);
    }

    /**
     * 所有菜单列表
     */
    @ApiOperation(value = "查看菜单", notes = "根据用户ID获取是否有查看菜单权限", httpMethod = "GET")
    @GetMapping("/list")
    //@RequiresPermissions("sys:right:list")

    public R list() {
        //查询的时候加上限制条件
        List<AdminRight> rightList = new ArrayList<>();
        List<AdminRight> rightListByUser = new ArrayList<>();
        if (getUserId() == Constant.SUPER_ADMIN) {
            rightList = adminRightService.queryNotButtonList(null);
        } else {
            rightList = adminRightService.queryNotButtonList(getUserId());
            //查询自己所创建的目录或着菜单
            rightListByUser = adminRightService.queryNotButtonListByUser(getUserId());
        }
        for (AdminRight adminRight : rightList) {
            AdminRight parentMenuEntity = adminRightService.getById(adminRight.getParentId());
            if (parentMenuEntity != null) {
                adminRight.setParentName(parentMenuEntity.getName());
            }
        }

        return R.ok().put("rightList", rightList).put("rightListByUser", rightListByUser).put("userId", getUserId());
    }

    /**
     * 非按钮菜单
     */
    @ApiOperation(value = "查询菜单", notes = "查询非按钮菜单(除了增删改查)", httpMethod = "GET")
    @GetMapping("/select")
    //@RequiresPermissions("sys:right:select")
    public R select() {
        //查询列表数据,

        //查询列表数据,只需要目录层次，不加按钮
        // List<AdminRight> rightList = adminRightService.queryCatalogList(getUserId());
        List<AdminRight> rightList = new ArrayList<>();
        if (getUserId() == Constant.SUPER_ADMIN) {
            rightList = adminRightService.queryCatalogList();
        } else {
            rightList = adminRightService.queryNotButtonList(getUserId());
        }
        //添加顶级菜单
        AdminRight root = new AdminRight();
        root.setRightId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        //root.setOpen(true);
        rightList.add(root);

        return R.ok().put("rightList", rightList);
    }

    /**
     * 菜单信息
     */
    @ApiOperation(value = "查看菜单信息", notes = "根据菜单ID来查询菜单信息", httpMethod = "GET")
    @GetMapping("/info/{rightId}")
    //@RequiresPermissions("sys:right:info")
    public R info(@PathVariable("rightId") Long rightId) {
        //查询时候需要加限制条件
        AdminRight right = adminRightService.getById(rightId);
        return R.ok().put("right", right);
    }


    /**
     * 检查路径
     */
    @GetMapping("path")
    public R judgePath(@RequestParam(name = "rightId", required = true) Long rightId,
                       @RequestParam(name = "path", required = true) String path) {

        boolean flag = true;

        List<AdminRight> AdminRights = adminRightService.queryRightPathIsExist(path);

        if (AdminRights != null && AdminRights.size() > 0) {
            if (rightId != 0) {
                for (int i = 0; i < AdminRights.size(); i++) {
                    if (!rightId.equals(AdminRights.get(i).getRightId())) {
                        flag = false;
                    }
                }

            } else {
                flag = false;
            }


        }


        return R.ok().put("isMultiple", flag);
    }


    /**
     * 保存
     */
    @ApiOperation(value = "添加菜单", notes = "根据用户ID获取是否有添加单个菜单权限，并操作", httpMethod = "POST")
    @SysLog("保存菜单")
    @PostMapping("/save")
    //@RequiresPermissions("sys:right:save")
    public R save(@RequestBody AdminRight right) {
        //数据校验
        verifyForm(right);
        //防止重复，在同一个parent目录下不能重复
        List<AdminRight> AdminRights = adminRightService.queryRightIsExist(right);
        if (AdminRights != null && AdminRights.size() > 0) {
            return R.error(MessageConstant.SC_NAME_EXIST, "菜单名或目录名");
        } else {

            right.setRightId(null);
            right.setCreateDt(DateUtils.getTimestamp());
            right.setCreateUserId(getUserId());
            right.setIsDeleted("0");
            right.setIsEnabled("1");
            adminRightService.save(right);

            return R.ok();

        }
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改菜单", notes = "根据用户ID获取是否有修改单个菜单权限，并操作", httpMethod = "POST")
    @SysLog("修改菜单")
    @PostMapping("/update")
    //@RequiresPermissions("sys:right:update")
    public R update(@RequestBody AdminRight right) {
        //数据校验
        verifyForm(right);
        List<AdminRight> AdminRights = adminRightService.queryRightIsExist(right);
        if (AdminRights != null && AdminRights.size() > 0) {
            for (int i = 0; i < AdminRights.size(); i++) {
//                if (AdminRights.get(i).getRightId() == right.getRightId()) {
                if (AdminRights.get(i).getRightId().equals(right.getRightId())) {
                    right.setModifyDt(DateUtils.getTimestamp());
                    right.setModifyUserId(getUserId());
                    right.setIsDeleted("0");
                    right.setIsEnabled("1");
                    adminRightService.updateById(right);
                    return R.ok();
                }
            }
            return R.error(MessageConstant.SC_NAME_EXIST, "菜单名");
        } else {


            right.setModifyDt(DateUtils.getTimestamp());
            right.setModifyUserId(getUserId());
            right.setIsDeleted("0");
            right.setIsEnabled("1");
            adminRightService.updateById(right);
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除菜单", notes = "根据用户ID获取是否有删除单个菜单权限，并操作", httpMethod = "POST")
    @SysLog("删除菜单")
    @PostMapping("/delete/{rightId}")
    //@RequiresPermissions("sys:right:delete")
    public R delete(@PathVariable("rightId") long rightId) {
        /*if (rightId <= Constant.SYSTEM_RIGHT_COUNT) {
            return R.error(MessageConstant.SYSTEM_NOT_DELETE, "系统菜单");
        }*/


        //判断是否有子菜单或按钮
        List<AdminRight> rightList = adminRightService.queryListParentId(rightId);
        if (rightList.size() > 0) {
            return R.error(MessageConstant.SYSTEM_RIGHT_SUB);
        }

        adminRightService.delete(rightId);

        return R.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(AdminRight right) {
        if (StringUtils.isBlank(right.getName())) {
            throw new RRException("菜单名称不能为空");
        }

        if (right.getParentId() == null) {
            throw new RRException("上级菜单不能为空");
        }

        //菜单
        if (right.getType() == Constant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(right.getEndpointPath())) {
                throw new RRException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (right.getParentId() != 0) {
            AdminRight parentright = adminRightService.getById(right.getParentId());
            parentType = parentright.getType();
        }

        //目录、菜单
        if (right.getType() == Constant.MenuType.CATALOG.getValue() ||
                right.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new RRException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (right.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new RRException("上级菜单只能为菜单类型");
            }
            return;
        }
    }
}
