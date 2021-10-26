package com.baidu.firstboot.service;
import com.baidu.firstboot.vo.Log;
public interface ILogService {
    /**
     * 添加日志的方法
     * @param log
     * @return
     * @throws Exception
     */
    public boolean addLog(Log log) throws Exception;
}
