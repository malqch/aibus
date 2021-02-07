package com.wntime.ec.common.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 */
@Configuration
public class ShiroConfig {

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean getShiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        //securityManager
        shiroFilter.setSecurityManager(securityManager);

        //过滤器
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2", new OAuth2Filter());
        shiroFilter.setFilters(filters);

        //过滤规则
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/druid/**", "anon");
        filterMap.put("/actuator/**", "anon");
        filterMap.put("/h2-console", "anon");
        filterMap.put("/h2-console/**", "anon");
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/captcha.jpg", "anon");
        filterMap.put("/", "anon");
        filterMap.put("/static/**", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/auth/login", "anon");
        filterMap.put("/gateway/**", "anon");   //提供给外部系统接口
        filterMap.put("/ec/**", "anon");   //提供给外部系统接口
        filterMap.put("/live", "anon");   //百目视频流
        filterMap.put("/evidence/**", "anon");   //存储本地视频
        filterMap.put("/advertise/**", "anon");   //存储广告
        filterMap.put("/monitor/**", "anon");   //实时视频
        filterMap.put("/**", "oauth2");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    @Bean("securityManager")
    public SecurityManager getSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getOAuth2Realm());
        securityManager.setSessionManager(getSessionManager());
//        securityManager.setCacheManager(getEhCacheManager());
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    @Bean("oAuth2Realm")
    OAuth2Realm getOAuth2Realm() {
        OAuth2Realm oAuth2Realm = new OAuth2Realm();
        return oAuth2Realm;
    }

    //自定义sessionManager
    @Bean("sessionManager")
    public DefaultWebSessionManager getSessionManager() {
        return new TokenSessionManager();
    }

    /*@Resource(name = "ehCacheManager")
    CacheManager cacheManager;

    @Bean("shiroEhCacheManager")
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        //将ehcacheManager转换成shiro包装后的ehcacheManager对象
        em.setCacheManager(cacheManager);
        return em;
    }*/

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

    @Bean
    public FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setEnabled(true);
        registration.setOrder(Integer.MAX_VALUE - 1);
        registration.addUrlPatterns("/*");
        return registration;
    }

}
