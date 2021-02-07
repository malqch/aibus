

package com.wntime.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.modules.sys.entity.AdminRight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单管理

 */
@Mapper
public interface AdminRightDao extends BaseMapper<AdminRight> {

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<AdminRight> queryListParentId(Long parentId);

	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<AdminRight> queryNotButtonList(@Param("createUserId") Long createUserId);

	/**
	 *
	 * 通过修改字段来进行删除
	 */
	void deleteUpdate(Long rightId);

    List<AdminRight> queryRightIsExist(AdminRight right);

	List<AdminRight> queryCatalogList();

	List<AdminRight> queryRightPathIsExist(@Param("path") String path);

    List<AdminRight> queryNotButtonListByUser(Long userId);
}
