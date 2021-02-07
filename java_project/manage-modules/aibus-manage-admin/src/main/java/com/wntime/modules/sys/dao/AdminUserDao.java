

package com.wntime.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.vo.AdminUserVo;
import com.wntime.modules.sys.vo.UserNameAndId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 系统用户

 */
@Mapper
public interface AdminUserDao extends BaseMapper<AdminUser> {

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	AdminUser queryByLoginName(String loginName);
	/**
	 *
	 * 根据用户ID数组，批量更新字段来进行删除
	 */
	int deleteUpdateByUserId(@Param(value = "userIds") Long[] userIds, @Param(value = "modifyUserId") Long modifyUserId, @Param(value = "modifyDt") Timestamp modifyDt);

	//多表关联查询
    List<Object> queryAdminUserList(Map<String, Object> params);

	List<AdminUser> queryUserIsExist(AdminUser user);

	List<Long> queryAllPositionId(@Param(value = "positionId") Long positionId, @Param(value = "createUserId") Long createUserId);


	List<UserNameAndId> queryUserNameIds();

	Optional<AdminUserVo> queryUserById(@Param("userId")long userId);
}
