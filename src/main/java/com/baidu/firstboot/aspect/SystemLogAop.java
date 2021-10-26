package com.baidu.firstboot.aspect;
import com.baidu.firstboot.annotations.SystemLog;
import com.baidu.firstboot.service.ILogService;
import com.baidu.firstboot.utils.IpInfoUtil;
import com.baidu.firstboot.utils.ThreadPoolUtil;
import com.baidu.firstboot.vo.Log;
import groovy.util.logging.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@Aspect
@Component
@Slf4j
public class SystemLogAop {
    /**
     * 保存到该属性的数据只能当前线程访问，其他线程是不能访问的，在多线程的应用中保证了数据的安全性
     */
    private static final ThreadLocal<Date> timeThreadLocal=new NamedThreadLocal<>("timeThread");
    @Resource
    private ILogService logService;
    @Resource
    private IpInfoUtil ipInfoUtil;
    @Autowired
    private HttpServletRequest request;

    /**
     * 定义切点
     * 原始的配置@Pointcut("excution(* com.baidu.firstboot.controller..*.*(..))")
     * 新配置：表示打上注解SystemLog的方法才进行日志处理
     */
    @Pointcut("@annotation(com.baidu.firstboot.annotations.SystemLog)")
    public void controllerAspect(){
    }

    /**
     * 前置通知（在执行目标方法之前就进入了该方法）
     * @param joinPoint
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint){
        //线程绑定变量（这个被绑定的数据只有当前线程可见）
        System.out.println("前置通知");
        Date startTime=new Date();
        timeThreadLocal.set(startTime);
    }
    @AfterReturning(pointcut = "controllerAspect()")
    public void doAfter(JoinPoint joinPoint){
        String username="";
        try {
            String desc= (String) getControllerMethodInfo(joinPoint).get("desc");
            //创建一个日志对象
            Log log=new Log();
            //取得用户名
            if(Objects.equals(getControllerMethodInfo(joinPoint).get("type"),0)){
                username=request.getSession().getAttribute("username").toString();
                log.setUsername(username);
            }
            //日志标题
            log.setDescription(desc);
            //日志类型
            log.setLogType((int)getControllerMethodInfo(joinPoint).get("type"));
            //请求方式
            log.setRequestType(request.getMethod());
            //请求路劲
            log.setRequestUrl(request.getRequestURI());
            //取得ip地址
            log.setIp(ipInfoUtil.getIpAddress(request));
            //取得开始时间
            long startTime=timeThreadLocal.get().getTime();
            //取得结束时间
            long endTime=System.currentTimeMillis();
            //请求耗费时间
            log.setCostTime(endTime-startTime);
            //操作时间
            log.setCreateTime(new Date());
            //使用线程池中的线程来保存日志信息
            ThreadPoolUtil.getPool().execute(new SaveLogThread(log,logService));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 定义一个内部类，用来保存日志信息
     */
    private static class SaveLogThread implements Runnable{
        private Log log;
        private ILogService logService;
        public SaveLogThread(Log log, ILogService logService) {
            this.log = log;
            this.logService = logService;
        }
        @Override
        public void run() {
            try {
                logService.addLog(log);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取方法的注解中描述的信息
     * @param joinPoint 切点对象
     * @return
     */
    public static Map<String,Object> getControllerMethodInfo(JoinPoint joinPoint) throws ClassNotFoundException {
        Map<String,Object> map=new HashMap<>(16);
        //获取目标类名称
        String targetClassName=joinPoint.getTarget().getClass().getName();
        //取得方法名称
        String targetMethodName=joinPoint.getSignature().getName();
        //取得方法的参数
        Object[] arguments=joinPoint.getArgs();
        //使用反射创建目标类的Class类对象
        Class targetClass=Class.forName(targetClassName);
        //获取目标类中的所有方法
        Method[] methods=targetClass.getMethods();
        String desc="";//日志标题描述
        Integer type=null;//保存日志类型
        for (Method method:methods){//遍历目标类的方法
            if(!method.getName().equals(targetMethodName)){//使用遍历到的方法名称和目标方法名称比较
                continue;//跳出当前循环
            }
            //取得的是方法参数类型Class类数组
            Class[] classes=method.getParameterTypes();
            if (classes.length!=arguments.length){
                //当前迭代到的方法的参数个数如果和目标方法参数个数不等，则表示当前方法和目标方法互为重载方法
                continue;
            }
            //取得目标方法上的注解信息
            desc=method.getAnnotation(SystemLog.class).desc();
            type=method.getAnnotation(SystemLog.class).type().ordinal();
            map.put("desc",desc);
            map.put("type",type);
        }
        return map;
    }
}
