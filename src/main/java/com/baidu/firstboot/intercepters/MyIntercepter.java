package com.baidu.firstboot.intercepters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

public class MyIntercepter implements HandlerInterceptor {
    private static long startTime;
    private static long endTime;
    //输出日志信息使用的对象
    private final Logger logger= LoggerFactory.getLogger(this.getClass().getName());

    /**
     * 预处理回调方法，实现处理器（控制器请求）的预处理（比如说登录检测）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o 目标方法的对象
     * @return  如果为true，表示放行继续执行（调用下一个拦截器或直接进去目标方法），
     * 如果是false，表示流程中断，不会调用其他拦截器或执行目标方法
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object o) throws Exception {
        HandlerMethod handlerMethod=(HandlerMethod) o;
        //取得目标方法的名称（还可以取得目标方法的参数类型以及你传递的对应的值）
        String targetMethodName=handlerMethod.getMethod().getName();
        startTime=System.currentTimeMillis();
        logger.info("开始计时："+new SimpleDateFormat("HH:mm:ss:SSS").
                format(startTime)+" targetMethodName:"+targetMethodName);
        return true;
    }

    /**
     * 后处理回调方法，实现处理器的后调处理（但是在渲染视图之前）
     * 此时可以在该方法中通过ModelAndView（模型视图对象）进行数据处理
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView  该参数也可以是null
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView!=null){
            logger.info("ViewName:"+modelAndView.getViewName());
        }
    }

    /**
     * 请求处理完毕之后回调该方法，在视图渲染完毕之后回调，
     * 比如在性能监控操作中就可以在该方法记录结束时间并且输出消耗的时间
     * 还可以进行一些资源的清理，类似于try-catch-finally中的finally
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        //保存日志信息（可以保存到数据库，也可以使用io流的形式保存到磁盘）
        //输出日志信息
        logger.info("访问地址："+httpServletRequest.getRequestURI()+",请求方式："+httpServletRequest.getMethod());
        endTime=System.currentTimeMillis();
        logger.info("计时结束："+new SimpleDateFormat("HH:mm:ss:SSS").
                format(endTime)+",耗时："+(endTime-startTime)+"毫秒");
    }
}
