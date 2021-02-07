package com.wntime.ec.common.config;

import com.wntime.ec.common.util.SpringContextUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wing
 * @create 2019-12-10 15:18
 */
@Configuration
public class SpringConfig {

    @Bean
    public SpringContextUtil getSpringContextUtil() {
        return new SpringContextUtil();
    }


}
