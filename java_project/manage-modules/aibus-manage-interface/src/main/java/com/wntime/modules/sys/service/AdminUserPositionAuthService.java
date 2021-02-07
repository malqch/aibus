package com.wntime.modules.sys.service;

import com.wntime.modules.sys.vo.UserPositionVo;

import java.util.List;

public interface AdminUserPositionAuthService {

    /**
     * 查看用户所拥有的岗位数据权限
     * @param userId
     * @return
     */
    List<UserPositionVo> queryUserPositionAuth(long userId);

    /**
     * 检验用户是否包含指定的岗位权限
     * @param userId
     * @param positionAuthId
     * @return
     */
    List<UserPositionVo> checkUserPositionAuth(long userId,long positionAuthId);
}
