package com.wntime.advert.config;

import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.wntime.common.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;

/**
 * @author 79448
 * @date 2020/8/26 14:19
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "wntime.advert.upload")
public class AdvertFileConfig implements WebMvcConfigurer {

    private String filePath;
    private String fileResourcePath;
    private String fileUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileResourcePath + "/**").addResourceLocations("file:" + filePath + "/");
    }
}
