

package com.wntime.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.modules.sys.entity.AdminRole;
import com.wntime.modules.sys.vo.AdminUserRoleVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 角色
 */
public interface AdminRoleService extends IService<AdminRole> {

    PageUtils queryPage(Map<String, Object> params);

    List<AdminRole> listByUser(Map<String, Object> params);

    List<AdminRole> selectUserRole(Long userId);

    List<AdminRole> selectOtherRole();

    List<Object> queryAdminRoleListAll(Map<String, Object> params);

    @Override
    AdminRole getById(Serializable roleId);

    void saveRole(AdminRole role, Long createUserId);

    void update(AdminRole role, Long createUserId);

    void deleteBatch(Long[] roleIds, Long userId);


    /**
     * 通过更新字段来删除角色
     */
    int deleteUpdate(Long[] roleIds, Long userId);

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long userId, Long createUserId);

    PageUtils queryPageList(Map<String, Object> params);

    //查询只是用户查询的
    List<Long> queryOwnerCreate(Long createUserId);

    List<AdminRole> queryRoleIsExist(AdminRole role);

    List<AdminUserRoleVo> queryUserRole(Long userId);
}
