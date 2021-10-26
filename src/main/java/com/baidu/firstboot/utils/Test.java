package com.baidu.firstboot.utils;
import java.util.concurrent.ThreadPoolExecutor;
public class Test {
    public static void main(String[] args) {
        //execute()方法表示执行新任务
        ThreadPoolExecutor poolExecutor=ThreadPoolUtil.getPool();
        poolExecutor.execute(new SaveLogsThread());
        poolExecutor.execute(new SaveLogsThread());
        poolExecutor.execute(new SaveLogsThread());
        //取得的是当前线程池中的实际线程数
        System.out.println(poolExecutor.getPoolSize());
        //取得的是线程池的核心线程数量
        System.out.println(poolExecutor.getCorePoolSize());
    }
}
