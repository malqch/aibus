package com.wntime.ec.common.config;

import com.wntime.ec.common.util.GlobalControllerExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionConfig {

    @Bean
    public GlobalControllerExceptionHandler getGlobalControllerExceptionHandler() {
        return new GlobalControllerExceptionHandler();
    }


}
