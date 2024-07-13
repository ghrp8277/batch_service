package com.example.batchservice.constants;

public class ThreadPoolConstants {
    public static final int THREAD_POOL_CORE_SIZE = 50; // 필요에 따라 조정
    public static final int THREAD_POOL_MAX_SIZE = 200; // 필요에 따라 조정
    public static final int THREAD_POOL_KEEP_ALIVE_TIME = 60;
    public static final int QUEUE_CAPACITY = 2000; // 큐 용량 추가

    public static final int RETRY_SLEEP_TIME = 1000;
    public static final int RETRY_COUNT = 3;
    public static final int EXECUTOR_SHUTDOWN_WAIT_TIME = 1;
    public static final int EXECUTOR_SHUTDOWN_NOW_WAIT_TIME = 1;
}
