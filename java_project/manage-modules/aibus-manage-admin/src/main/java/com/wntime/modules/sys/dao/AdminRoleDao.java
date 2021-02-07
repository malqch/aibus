

package com.wntime.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.modules.sys.entity.AdminRole;
import com.wntime.modules.sys.vo.AdminUserRoleVo;
import com.wntime.modules.sys.vo.UserRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 */
@Mapper
public interface AdminRoleDao extends BaseMapper<AdminRole> {

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(@Param(value = "userId") Long userId, @Param(value = "createUserId") long createUserId);

    /**
     * 根据角色ID数组，批量更新字段来进行删除
     */
    int deleteUpdate(@Param(value = "roleIds") Long[] roleIds, @Param(value = "modifyUserId") Long modifyUserId, @Param(value = "modifyDt") Timestamp modifyDt);


    List<Long> queryAdminRoleList(@Param(value = "createUserId") Long createUserId);

    List<Object> queryAdminRoleListAll(Map<String, Object> params);

    List<AdminRole> listByUser(Map<String, Object> params);

    List<AdminRole> selectUserRole(Long userId);

    List<AdminRole> selectOtherRole();

    List<AdminRole> queryRoleIsExist(AdminRole role);

    List<UserRoleVo> queryHomeUserRoleName(@Param("userId") long userId);

    List<AdminUserRoleVo> queryRolesWithUser(@Param("userId")Long userId);
}
