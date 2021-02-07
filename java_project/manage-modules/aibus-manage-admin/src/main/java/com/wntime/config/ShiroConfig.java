package com.wntime.config;

import com.wntime.common.oauth2.OAuth2Filter;
import com.wntime.modules.sys.oauth2.OAuth2Realm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 */
@Configuration
public class ShiroConfig {

    @Bean("securityManager")
    public SecurityManager securityManager(OAuth2Realm oAuth2Realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(oAuth2Realm);
        securityManager.setSessionManager(sessionManager());
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        //oauth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2", new OAuth2Filter());
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>();
        /*filterMap.put("/update/**", "anon");
        filterMap.put("/sn/**", "anon");
        filterMap.put("/selection/**", "anon");
        filterMap.put("/deviceI/**", "anon");
        filterMap.put("/auto/**", "anon");
        filterMap.put("/info/**", "anon");
        filterMap.put("/rule/**", "anon");*/
        filterMap.put("/getUpdatePackage/**", "anon");
        filterMap.put("/image/**", "anon");
        filterMap.put("/take_photo/**", "anon");
        filterMap.put("/images/**", "anon");
        filterMap.put("/spot/eventFile/**","anon");
        filterMap.put("/faultFile/**","anon");
        filterMap.put("/eventFile/**","anon");
        filterMap.put("/officer/file/**","anon");
        filterMap.put("/officer/image/**","anon");
        filterMap.put("/auth_photo/**","anon");
        filterMap.put("/health_code/**","anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/callback/**", "anon");
        filterMap.put("/druid/**", "anon");
        filterMap.put("/app/**", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/sys/public/token", "anon");//获取公共服务Token
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/captcha.jpg", "anon");
       // filterMap.put("/carFactory/**", "anon");
        filterMap.put("/bus/status/**", "anon");
        filterMap.put("/event/report","anon");
        filterMap.put("/fault/report","anon");
        filterMap.put("/device/status/**","anon");
        filterMap.put("/home/login","anon");
        filterMap.put("/cache/init/**","anon");
        filterMap.put("/cache/clear/**","anon");
        filterMap.put("/monitoring","anon");
        //filterMap.put("/busCompany/**","anon");
        filterMap.put("/file/**","anon");
        //filterMap.put("/bus/detail/**","anon");
        filterMap.put("/**", "oauth2");

        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    //自定义sessionManager
    @Bean("sessionManager")
    public DefaultWebSessionManager sessionManager() {
        return new TokenSessionManager();
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
