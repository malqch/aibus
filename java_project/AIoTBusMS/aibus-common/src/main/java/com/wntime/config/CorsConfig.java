package com.wntime.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
        registry.addMapping("/monitoring").exposedHeaders("X-Frame-Options");
    }

    @Value("${path.updatePackage}")
    private String updateDir;

    @Value(("${path.requestUpdatePackageUrl}"))
    private String requestUpdatePackageUrl;

    @Value("${wntime.common.dataPath}")
    private String commonDataPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/" + requestUpdatePackageUrl + "/**").addResourceLocations("file:" + updateDir + "/");
        registry.addResourceHandler("/image/**").addResourceLocations("file:" + commonDataPath + "/");
    }
}
