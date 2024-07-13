package com.example.batchservice.config;

import com.example.batchservice.constants.ThreadPoolConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
            ThreadPoolConstants.THREAD_POOL_CORE_SIZE,
            ThreadPoolConstants.THREAD_POOL_MAX_SIZE,
            ThreadPoolConstants.THREAD_POOL_KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(ThreadPoolConstants.QUEUE_CAPACITY),
            new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
