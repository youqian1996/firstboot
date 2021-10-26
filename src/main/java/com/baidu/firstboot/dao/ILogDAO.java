package com.baidu.firstboot.dao;
import com.baidu.firstboot.vo.Log;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface ILogDAO {
    public int doCreateLog(Log log) throws Exception;
}
