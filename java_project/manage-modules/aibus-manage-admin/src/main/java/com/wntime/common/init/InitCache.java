package com.wntime.common.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class InitCache implements CommandLineRunner {


    @Override
    public void run(String... args){
        //查询需要缓存的系统参数，并将其放入缓存中
    }


}
