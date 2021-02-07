

package com.wntime.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.entity.AdminUserRole;

import java.util.List;


/**
 * 用户与角色对应关系
 */
public interface AdminUserRoleService extends IService<AdminUserRole> {

    void saveOrUpdate(Long operationId, Long userId, List<Long> roleIdList);

    void addUserRole(Long loginUserId, AdminUser adminUser);

    void updateUserRole(Long loginUserId, AdminUser adminUser);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);

    /**
     * 根据用户ID 来更新用户和角色的is_deleted字段
     */
    int deleteUpdateByUserId(Long[] userIds, Long operationId);

    /**
     * 根据用户ID 来更新用户和角色的is_deleted字段
     */
    int deleteUpdateByRoleId(Long[] roleIds, Long operationId);

    Long getUserRoleId(Long userId, Long roleId);

    Integer deleteUserRole(Long userId, Long operationId);

}
