package com.baidu.firstboot.service.impl;
import com.baidu.firstboot.dao.ILogDAO;
import com.baidu.firstboot.service.ILogService;
import com.baidu.firstboot.vo.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.sql.DataSource;
@Service
public class LogServiceImpl implements ILogService {
    @Resource
    private ILogDAO logDAO;
    @Resource
    private DataSource dataSource;
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addLog(Log log) throws Exception {
        System.out.println(dataSource.getClass());
        System.out.println(log);
        return logDAO.doCreateLog(log)>0;
    }
}
