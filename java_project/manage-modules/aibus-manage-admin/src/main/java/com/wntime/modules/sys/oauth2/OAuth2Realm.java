package com.wntime.modules.sys.oauth2;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.wntime.common.oauth2.OAuth2Token;
import com.wntime.common.utils.CommonUtil;
import com.wntime.common.utils.ShiroUtils;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.dao.AdminRoleDao;
import com.wntime.modules.sys.entity.AdminUserToken;
import com.wntime.modules.sys.service.AdminUserPositionAuthService;
import com.wntime.modules.sys.service.AdminUserTokenService;
import com.wntime.modules.sys.service.ShiroService;
import com.wntime.modules.sys.vo.UserPositionVo;
import com.wntime.modules.sys.vo.UserRoleVo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 认证

 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private ShiroService shiroService;
    @Resource
    private AdminRoleDao adminRoleDao;
    @Resource
    private AdminUserTokenService adminUserTokenService;
    @Resource
    private AdminUserPositionAuthService adminUserPositionAuthService;
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        AdminUser user = (AdminUser)principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        //用户权限列表
        Set<String> permsSet = shiroService.getUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        Session session = ShiroUtils.getSession();

        Object obj = session.getAttribute("user");

        if(CommonUtil.isEmpty(obj)){
            //根据accessToken，查询用户信息
            AdminUserToken tokenEntity = shiroService.queryByToken(accessToken);
            if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
                throw new IncorrectCredentialsException("token失效，请重新登录");
            }else {
                Long userId = tokenEntity.getUserId();
                session.setAttribute("user", shiroService.queryUser(userId));
                session.setTimeout(tokenEntity.getExpireTime().getTime()- SystemClock.now());
                List<UserRoleVo> roleNames = adminRoleDao.queryHomeUserRoleName(userId);
                Optional.ofNullable(roleNames).ifPresent(roles->{
                    session.setAttribute("role",roleNames.stream().filter(s->s.getRoleName()!=null).collect(Collectors.toList()));
                    session.setAttribute("roleIds",roleNames.stream().map(UserRoleVo::getRoleId).collect(Collectors.toList()));
                });
                List<UserPositionVo> auth = adminUserPositionAuthService.queryUserPositionAuth(userId);
                session.setAttribute("positionAuth",auth);
                ShiroUtils.deleteSession(session.getId().toString());
                ShiroUtils.putSession(accessToken,session);
            }
            //throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        AdminUser user = (AdminUser) session.getAttribute("user");
        //账号锁定
        if("0".equals(user.getIsEnabled())){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        //adminUserTokenService.updateTokenTime(tokenEntity);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}
