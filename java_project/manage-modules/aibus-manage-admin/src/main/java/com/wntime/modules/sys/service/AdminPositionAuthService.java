

package com.wntime.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.modules.sys.entity.AdminPositionAuth;
import com.wntime.modules.sys.form.PositionAuthFrom;
import com.wntime.modules.sys.vo.AdminPositionAuthInfoVo;
import com.wntime.modules.sys.vo.AdminPositionAuthVo;
import com.wntime.modules.sys.vo.BusiObjectVo;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 职位与授权对应关系

 */
public interface AdminPositionAuthService extends IService<AdminPositionAuth> {

	void saveOrUpdate(Long positionId, List<PositionAuthFrom> authIdList, Long operationId);

	/**
	 * 根据职位ID，获取授权ID列表
	 */
	List<AdminPositionAuthInfoVo> queryAuthIdList(Long positionId);

	/**
	 * 根据职位ID数组，批量删除
	 */
	int deleteBatch(Long[] positionIds);

	/**
	 *根据职位ID更新字段来进行删除role_right关系
	 */
    int deleteUpdateByPositionId(Long[] positionIds, Long userId);

	int deleteUpdateByAuthId(Long[] authtIds, Long userId);

	List<AdminPositionAuthVo> queryPositionAuthWithUserId(Long positionId);

	List<AdminPositionAuthVo> queryPositionAuthWithClique(Map<String, Object> params);

	List<Long> queryAreaOrgId(Set<Long> positionId, int linkType);
}
