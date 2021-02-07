package com.wntime.ec.common.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * shiro 认证
 */
public class OAuth2Realm extends AuthorizingRealm {

//    @Autowired
//    private IShiroService shiroService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        GfmAdminUser user = (GfmAdminUser)principals.getPrimaryPrincipal();
//        Long userId = user.getUserId();
//
//        //用户权限列表
//        Set<String> permsSet = shiroService.getUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();
        Session session = ShiroUtil.getSession();

//        FsmsLoginRespVo data = (FsmsLoginRespVo) session.getAttribute("user");
//        if(CommonUtil.isEmpty(data)){
//            throw new BusinessException(ExceptionConstant.SC_UNAUTHORIZED, "token失效，请重新登录");
//        }
        SimpleAuthenticationInfo info = null;
//        info = new SimpleAuthenticationInfo(data, accessToken, getName());
        return info;
    }
}
