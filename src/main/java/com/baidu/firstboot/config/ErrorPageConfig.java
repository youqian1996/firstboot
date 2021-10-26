package com.baidu.firstboot.config;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry errorPageRegistry) {
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND,"/error-404.html");
        ErrorPage errorPage405 = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED,"/error-405.html");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error-500.html");
        errorPageRegistry.addErrorPages(errorPage404,errorPage405,errorPage500);
    }
}
