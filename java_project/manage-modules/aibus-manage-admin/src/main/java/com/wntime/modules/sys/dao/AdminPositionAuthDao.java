

package com.wntime.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.modules.sys.entity.AdminPositionAuth;
import com.wntime.modules.sys.vo.AdminPositionAuthInfoVo;
import com.wntime.modules.sys.vo.AdminPositionAuthVo;
import com.wntime.modules.sys.vo.BusiObjectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色与菜单对应关系

 */
@Mapper
public interface AdminPositionAuthDao extends BaseMapper<AdminPositionAuth> {

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<AdminPositionAuthInfoVo> queryAuthIdList(Long positionId);

	/**
	 * 根据岗位ID数组，批量删除
	 */
	int deleteBatch(Long[] positionIds);
	/**
	 * 根据角色ID数组，批量更新字段来删除
	 */
	int deleteUpdateByPositionId(@Param(value = "positionIds") Long[] positionIds, @Param(value = "modifyUserId") Long modifyUserId, @Param(value = "modifyDt") Timestamp modifyDt);
    int deleteUpdateByAuthId(@Param(value = "authIds") Long[] authIds, @Param(value = "modifyUserId") Long modifyUserId, @Param(value = "modifyDt") Timestamp modifyDt);


	List<AdminPositionAuthVo> queryPositionAuthWithPositionId(@Param(value = "positionId") Long positionId);

	List<AdminPositionAuthVo> queryOrganizeList();

	List<AdminPositionAuthVo> queryOrganizeListWithClique(Map<String, Object> params);

	List<Long> queryAreaOrgIdByPositionIdAndLinkType(@Param("positionIds") Set<Long> positionIds, @Param("linkType")int linkType);
}
