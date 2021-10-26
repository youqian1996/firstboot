package com.baidu.firstboot.utils;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class ThreadPoolUtil {
    //线程缓冲队列
    private static BlockingQueue<Runnable> blockingDeque=new ArrayBlockingQueue<>(100);
    //线程池中的核心线程数
    private static final int SIZE_CORE_POOL=5;
    //线程池维护线程的最大数量
    private static final int SIZE_MAX_POOL=10;
    //线程池维护线程允许的最大空闲时间
    private static final long ALIVE_TIME=2000;
    //创建线程池对象
    private static ThreadPoolExecutor poolExecutor= new ThreadPoolExecutor(SIZE_CORE_POOL,SIZE_MAX_POOL,ALIVE_TIME,
            TimeUnit.MILLISECONDS,blockingDeque, new ThreadPoolExecutor.CallerRunsPolicy());
    //在静态代码块中启动所有的核心线程
    static{
        poolExecutor.prestartAllCoreThreads();
    }
    //取得线程池的方法
    public static ThreadPoolExecutor getPool(){
        return poolExecutor;
    }
}
