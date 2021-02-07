package com.wntime.event.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${wntime.thread.pool.corePoolSize:10}")
    private int corePoolSize;

    @Value("${wntime.thread.pool.maxPoolSize:20}")
    private int maxPoolSize;

    @Value("${wntime.thread.pool.keepAliveSeconds:60}")
    private int keepAliveSeconds;

    @Value("${wntime.thread.pool.queueCapacity:512}")
    private int queueCapacity;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,maxPoolSize,
                keepAliveSeconds, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(queueCapacity));

        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable ex , Method method , Object... params) {
                logger.error("线程池执行任务发生未知异常.", ex);
            }
        };
    }
}
