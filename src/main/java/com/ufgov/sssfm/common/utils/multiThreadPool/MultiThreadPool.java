package com.ufgov.sssfm.common.utils.multiThreadPool;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Description springboot线程池
 * @Author xcuy
 * @Date 2018/8/4
 **/
@Configuration
@EnableAsync
public class MultiThreadPool implements AsyncConfigurer {

     @Override
     public Executor getAsyncExecutor() {
         ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
         taskExecutor.setCorePoolSize(5);// 最小线程数
         taskExecutor.setMaxPoolSize(10);// 最大线程数
         taskExecutor.setQueueCapacity(25);// 等待队列
         taskExecutor.initialize();
         System.out.println("线程池启动了");

         return taskExecutor;
     }


     @Override
     public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
             return null;
      }
}
