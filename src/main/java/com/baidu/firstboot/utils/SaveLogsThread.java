package com.baidu.firstboot.utils;
public class SaveLogsThread implements Runnable{
    @Override
    public void run() {
        //现在是伪实现，实际开发中是保存到数据库
        System.out.println("保存日志成功！");
    }
}
