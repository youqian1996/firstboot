package com.baidu.firstboot.annotations;
import com.baidu.firstboot.controller.LogType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target(ElementType.METHOD)//表示该注解的使用范围是方法之上
@Retention(RetentionPolicy.RUNTIME)//表示该注解在运行是有效
public @interface SystemLog {
    String desc() default "";//表示操作的描述
    LogType type() default LogType.OPRATION;
}
