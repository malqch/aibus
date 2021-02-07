package com.wntime.ec.common.config;

import com.google.common.collect.ImmutableMap;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import java.util.Map;

/**
 * @Description: 反向代理
 */
@Configuration
public class ProxyServletConfig {
    /**
     * 读取配置文件中路由设置
     */
    @Value("${proxy.servlet-url}")
    private String servletUrl;
    /**
     * 读取配置中代理目标地址
     */
    @Value("${proxy.target-url}")
    private String targetUrl;

    @Bean
    public Servlet createProxyServlet() {
        // 创建新的ProxyServlet
        return new ProxyServlet();
    }

    @Bean
    public ServletRegistrationBean proxyServletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(createProxyServlet(), servletUrl);
        //设置网址以及参数
        Map<String, String> params = ImmutableMap.of(
                "targetUri", targetUrl,
                "log", "true");
        registrationBean.setInitParameters(params);
        return registrationBean;
    }
}