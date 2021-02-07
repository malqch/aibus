

package com.wntime.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.modules.sys.dao.AdminRoleDao;
import com.wntime.modules.sys.entity.AdminRole;
import com.wntime.modules.sys.service.AdminRoleRightService;
import com.wntime.modules.sys.service.AdminRoleService;
import com.wntime.modules.sys.service.AdminUserRoleService;
import com.wntime.modules.sys.service.AdminUserService;
import com.wntime.modules.sys.vo.AdminUserRoleVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 角色
 */
@Service("adminRoleService")
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleDao, AdminRole> implements AdminRoleService {
    @Autowired
    private AdminRoleRightService adminRoleRightService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminUserRoleService adminUserRoleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String) params.get("name");
        Long createUserId = (Long) params.get("createUserId");

        IPage<AdminRole> page = this.page(
                new Query<AdminRole>().getPage(params),
                new QueryWrapper<AdminRole>()
                        .like(StringUtils.isNotBlank(roleName), "name", roleName)
                        .eq(createUserId != null, "create_user_id", createUserId).eq("is_deleted", "0")
        );

        return new PageUtils(page);
    }

    @Override
    public List<AdminRole> listByUser(Map<String, Object> params) {
        return this.baseMapper.listByUser(params);
    }

    @Override
    public List<AdminRole> selectUserRole(Long userId) {
        return this.baseMapper.selectUserRole(userId);
    }

    @Override
    public List<AdminRole> selectOtherRole() {
        return this.baseMapper.selectOtherRole();
    }

    @Override
    public List<Object> queryAdminRoleListAll(Map<String, Object> params) {
        return this.baseMapper.queryAdminRoleListAll(params);
    }

    @Override
    public PageUtils queryPageList(Map<String, Object> params) {
        Integer currPage = Integer.parseInt((String) params.get("page"));
        Integer pageSize = Integer.parseInt((String) params.get("limit"));
        Page<Object> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        return new PageUtils(page.setRecords(this.baseMapper.queryAdminRoleListAll(params)));
    }

    @Override
    public List<Long> queryOwnerCreate(Long createUserId) {
        return this.baseMapper.queryAdminRoleList(createUserId);
    }

    @Override
    public List<AdminRole> queryRoleIsExist(AdminRole role) {
        return this.baseMapper.queryRoleIsExist(role);
    }

    @Override
    public List<AdminUserRoleVo> queryUserRole(Long userId) {
        return this.baseMapper.queryRolesWithUser(userId);
    }


    @Override
    public AdminRole getById(Serializable roleId) {

        QueryWrapper<AdminRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.eq("is_deleted", "0").eq("is_enabled", "1");
        return this.baseMapper.selectOne(queryWrapper);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(AdminRole role, Long createUserId) {
        role.setCreateDt(DateUtils.getTimestamp());
        this.save(role);

        //检查权限是否越权
        checkPrems(role, createUserId);

        //保存角色与菜单关系
        adminRoleRightService.saveOrUpdate(role.getRoleId(), role.getRightIdList(), role.getCreateUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AdminRole role, Long createUserId) {
        this.updateById(role);

        //检查权限是否越权
        checkPrems(role, createUserId);

        //更新角色与菜单关系
        adminRoleRightService.saveOrUpdate(role.getRoleId(), role.getRightIdList(), role.getCreateUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds, Long userId) {


        //删除角色
        this.deleteUpdate(roleIds, userId);

        //删除角色与菜单关联
        adminRoleRightService.deleteUpdateByRoleId(roleIds, userId);

        //删除角色与用户关联
        adminUserRoleService.deleteUpdateByRoleId(roleIds, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUpdate(Long[] roleIds, Long userId) {

        return this.baseMapper.deleteUpdate(roleIds, userId, DateUtils.getTimestamp());
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void deleteBatch(Long[] roleIds) {
//        //删除角色
//        this.removeByIds(Arrays.asList(roleIds));
//
//        //删除角色与菜单关联
//        adminRoleRightService.deleteBatch(roleIds);
//
//        //删除角色与用户关联
//        adminUserRoleService.deleteBatch(roleIds);
//    }


    @Override
    public List<Long> queryRoleIdList(Long userId, Long createUserId) {
        return baseMapper.queryRoleIdList(userId, createUserId);
    }


    /**
     * 检查权限是否越权
     */
    private void checkPrems(AdminRole role, Long createUserId) {
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (createUserId == Constant.SUPER_ADMIN) {
            return;
        }

        //查询用户所拥有的菜单列表
        List<Long> rightIdList = adminUserService.queryAllRightId(createUserId);

        //判断是否越权
        if (!rightIdList.containsAll(role.getRightIdList())) {
            throw new RRException("新增角色的权限，已超出你的权限范围");
        }
    }
}
