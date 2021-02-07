package com.wntime.ec.common.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

/**
 * 定时器多线程配置
 */
@Configuration
@EnableAsync
public class ScheduleConfig implements SchedulingConfigurer, AsyncConfigurer {

    /**
     * 同步任务
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        TaskScheduler taskScheduler = getTaskScheduler();
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler);
    }

    /**
     * 异步任务
     */
    @Override
    public Executor getAsyncExecutor() {
        Executor executor = getAsyncTaskScheduler();
        return executor;
    }

    /**
     * 异常处理
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

    /**
     * 同步定时器配置
     */
    @Bean(name = "taskScheduler", destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler getTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("ec-task-");
        // 线程内容执行完后60秒停止
        scheduler.setAwaitTerminationSeconds(60);
        // 当调度器shutdown被调用时等待当前被调度的任务完成
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }

    @Bean(name = "asyncTaskScheduler", destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler getAsyncTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("ec-asynctask-");
        // 线程内容执行完后60秒停止
        scheduler.setAwaitTerminationSeconds(60);
        // 当调度器shutdown被调用时等待当前被调度的任务完成
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }


//    @Bean
//    public ThreadPoolTaskExecutor getThreadPool() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(20);//核心线程数
//        executor.setMaxPoolSize(50);//最大线程数
//        executor.setQueueCapacity(100);//队列大小
//        executor.setThreadNamePrefix("ec-executor-");//线程名前缀
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());//拒绝策略 - 抛错
//        executor.setKeepAliveSeconds(60);
//        executor.initialize();//初始化线程池
//        return executor;
//    }
}