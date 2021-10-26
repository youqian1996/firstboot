package com.baidu.firstboot.vo;
import java.io.Serializable;
import java.util.Date;
public class Log implements Serializable {
    private Long lid;//日志在数据库中对应的id
    private String description;//日志的标题描述
    private Integer logType;//日志的类型 1表示登录日志 2表示操作日志
    private String requestUrl;//操作的路劲信息
    private String requestType;//请求类型（比如post或get等）
    private String username;//进行当前操作的用户名
    private String ip;//执行当前操作的用户的电脑ip地址
    private Long costTime;//执行当前操作的消耗时间
    private Date createTime;//执行当前操作的时间

    public Long getLid() {
        return lid;
    }

    public void setLid(Long lid) {
        this.lid = lid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Log{" +
                "lid=" + lid +
                ", description='" + description + '\'' +
                ", logType=" + logType +
                ", requestUrl='" + requestUrl + '\'' +
                ", requestType='" + requestType + '\'' +
                ", username='" + username + '\'' +
                ", ip='" + ip + '\'' +
                ", costTime=" + costTime +
                ", createTime=" + createTime +
                '}';
    }
}
