package com.baidu.firstboot.config;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Configuration
public class GlobalHandlerController implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView("pages/global_error");
        String errorMsg = null;
        if (e instanceof ArithmeticException){
            errorMsg="出现算数异常啦";
        }else if (e instanceof NullPointerException){
            errorMsg="出现空指针异常啦";
        }else if(e instanceof NumberFormatException){
            errorMsg="出现数字格式化异常啦";
        }else {
            errorMsg="我他妈也不知道这是个啥异常！";
        }
        mv.addObject("msg",errorMsg);
        return mv;
    }
}
