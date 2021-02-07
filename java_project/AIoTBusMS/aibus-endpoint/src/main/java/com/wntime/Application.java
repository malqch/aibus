package com.wntime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * .
 *
 * <p>
 *
 * @author <a href="https://blog.csdn.net/yidichaxiang" target="_blank">Sleeber</a>
 * @since 1.3.0
 *
 * <p><b>Note:</b>
 * <p>2020/8/24 13:48: created.<br>
 */
@SpringBootApplication
@EnableScheduling
@EnableEntityDefinedRegions(basePackages = "com.wntime")
@EnableGemfireRepositories(basePackages = "com.wntime")
public class Application {

//    @PostConstruct
//    void started() {
//        //时区设置：中国上海
//        //time.zone: "Asia/Shanghai"
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
//    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }

}



