

package com.wntime.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.modules.sys.entity.AdminPosition;
import com.wntime.modules.sys.vo.AdminPositionAuthVo;
import com.wntime.modules.sys.vo.AdminUserPositionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 角色管理

 */
@Mapper
public interface AdminPositionDao extends BaseMapper<AdminPosition> {

	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
	/**
	 *
	 * 根据角色ID数组，批量更新字段来进行删除
	 */
	int deleteUpdate(@Param(value = "positionIds") Long[] positionIds, @Param(value = "modifyUserId") Long modifyUserId, @Param(value = "modifyDt") Timestamp modifyDt);


    List<Long> queryAdminPositionList(@Param(value = "createUserId") Long createUserId);

    List<Object> queryAdminPositionListAll(Map<String, Object> params);

    int queryPositionIsExist(@Param("name")String  positionName,@Param("positionId")Long positionId);

    List<AdminUserPositionVo> listByUser(@Param(value = "userId") Long userId,@Param("loginUserId")Long loginUserId);

    List<Long> queryPositionIdList(@Param(value = "userId") Long userId, @Param(value = "createUserId") Long createUserId);


}
