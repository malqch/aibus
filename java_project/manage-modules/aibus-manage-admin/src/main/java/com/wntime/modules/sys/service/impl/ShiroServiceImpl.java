

package com.wntime.modules.sys.service.impl;

import com.wntime.common.utils.Constant;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.dao.AdminRightDao;
import com.wntime.modules.sys.dao.AdminUserDao;
import com.wntime.modules.sys.dao.AdminUserTokenDao;
import com.wntime.modules.sys.entity.AdminRight;
import com.wntime.modules.sys.entity.AdminUserToken;
import com.wntime.modules.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private AdminRightDao adminRightDao;
    @Autowired
    private AdminUserDao adminUserDao;
    @Autowired
    private AdminUserTokenDao adminUserTokenDao;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<AdminRight> menuList = adminRightDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(AdminRight menu : menuList){
                permsList.add(menu.getAuthDetails());
            }
        }else{
            permsList = adminUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public AdminUserToken queryByToken(String token) {
        return adminUserTokenDao.queryByToken(token);
    }

    @Override
    public AdminUser queryUser(Long userId) {
        return adminUserDao.selectById(userId);
    }
}
