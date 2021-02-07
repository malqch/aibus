

package com.wntime.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.R;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.entity.AdminUserToken;

/**
 * 用户Token

 */
public interface AdminUserTokenService extends IService<AdminUserToken> {

	/**
	 * 生成token
	 * @param
	 */
	R createToken(AdminUser user);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(String userId);


	R createHomeToken(AdminUser user);

    void updateTokenTime(AdminUserToken tokenEntity);
}
