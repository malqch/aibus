

package com.wntime.modules.sys.service;

import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.entity.AdminUserToken;

import java.util.Set;

/**
 * shiro相关接口

 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    AdminUserToken queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    AdminUser queryUser(Long userId);
}
