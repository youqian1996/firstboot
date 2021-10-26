package com.baidu.firstboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
@SpringBootApplication//@SpringBootApplication=@EnableAutoConfiguration+ComponentScan+其他配置注解
//继承SpringBootServletInitializer的作用是在启动项目的时候使用外置的tomcat进行启动
public class FirstbootApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(FirstbootApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(FirstbootApplication.class, args);
    }
}
