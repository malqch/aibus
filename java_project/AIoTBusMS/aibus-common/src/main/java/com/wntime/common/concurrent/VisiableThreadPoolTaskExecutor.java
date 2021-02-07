package com.wntime.common.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String threadPoolName;

    public VisiableThreadPoolTaskExecutor(String threadPoolName){
        super();
        this.threadPoolName = threadPoolName;
    }

    private void showThreadPoolInfo(){
        if(logger.isInfoEnabled()){
            ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
            if (null == threadPoolExecutor) {
                return;
            }
            logger.info(threadPoolName + " ==> " + threadPoolExecutor.toString());
        }
    }

    @Override
    public void execute(Runnable task) {
        showThreadPoolInfo();
        super.execute(task);
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        showThreadPoolInfo();
        super.execute(task, startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        showThreadPoolInfo();
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        showThreadPoolInfo();
        return super.submit(task);
    }

}
