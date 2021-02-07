package com.wntime.modules.sys.service.impl;

import com.wntime.common.exception.RRException;
import com.wntime.modules.sys.dao.AdminUserPositionDao;
import com.wntime.modules.sys.service.AdminUserPositionAuthService;
import com.wntime.modules.sys.vo.UserPositionVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminUserPositionAuthServiceImpl implements AdminUserPositionAuthService {

    @Resource
    private AdminUserPositionDao adminUserPositionDao;

    @Override
    public List<UserPositionVo> queryUserPositionAuth(long userId) {
        return adminUserPositionDao.queryUserPositionAuth(userId);
    }

    @Override
    public List<UserPositionVo> checkUserPositionAuth(long userId, long positionAuthId) {
        return adminUserPositionDao.queryUserPositionAuthCount(userId, positionAuthId);
    }
}
