package com.baidu.firstboot.utils;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * 获取ip的工具类
 */
@Component
public class IpInfoUtil {
    public String getIpAddress(HttpServletRequest request){
        //只有使用了http代理或者使用了负载均衡服务器（比如Ngnix）时候才会有该请求头信息
        String ip=request.getHeader("x-forwarded-for");
        if (ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            //如果使用了apache的http代理的时候会有该请求头信息（Proxy-Client-IP）
            ip=request.getHeader("Proxy-Client-IP");
        }
        if (ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            //apache与weblogic进行整合的时候会有该请求头信息（WL-Proxy-Client-IP）
            ip=request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            //此时是没有使用任何代理的普通http请求
            ip=request.getRemoteAddr();
            if ("0:0:0:0:0:0:0:1".equals(ip)){//本机ipv6地址
                ip="127.0.0.1";//本机ipv4地址
            }
            if ("127.0.0.1".equals(ip)){
                //根据网卡信息取得代表本机ip的一个对象
                InetAddress inetAddress=null;
                try {
                    inetAddress=InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                //通过inetAddress取得真是的ip地址
                ip=inetAddress.getHostAddress();
            }
        }
        //如果通过了多层代理可能出现多个ip的情况，第一个ip就是客户端的真实ip，多个ip之间按照“,”分割
        //192.168.91.1,192.168.91.2,192.168.91.3
        if (ip!=null && ip.length()>15){
            if (ip.indexOf(",")>0){
                ip=ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }
}
