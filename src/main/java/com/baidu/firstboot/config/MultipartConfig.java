package com.baidu.firstboot.config;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.MultipartConfigElement;
@Configuration
public class MultipartConfig {
    @Bean
    public MultipartConfigElement getMultipartConfigEliment(){
        MultipartConfigFactory factory=new MultipartConfigFactory();
        factory.setMaxFileSize("10MB");
        factory.setMaxRequestSize("50MB");
        return factory.createMultipartConfig();
    }
}
