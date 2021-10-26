package com.baidu.firstboot.config;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import javax.sql.DataSource;
@Configuration
public class DruidConfig {
    /**
     * 该方法返回一个初始化了上下文的servlet（指定了一些初始化参数），相当于在web容器中注册了一个性能监控管理的servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean getServletRegistrationBean(){
        ServletRegistrationBean bean=new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //白名单（允许访问指定路劲的ip地址）
        bean.addInitParameter("allow","127.0.0.0,192.168.0.114");
        //黑名单
        bean.addInitParameter("deny","192.168.0.115,192.168.0.116");
        //登录名称
        bean.addInitParameter("loginUsername","root");
        //登录密码
        bean.addInitParameter("loginPassword","admin");
        //表示不能重置数据源
        bean.addInitParameter("resetEnable","false");
        return bean;
    }

    /**
     * 该方法向web容器中注册了一个过滤器，该过滤器专门负责请求的性能监控统计，
     * 并且给过滤器指定要拦截的路劲和要放行的路劲（初始化）
     * @return
     */
    @Bean
    public FilterRegistrationBean getFilterRegistrationBean(){
        FilterRegistrationBean filter=new FilterRegistrationBean();
        //指定过滤器要拦截的路劲
        filter.addUrlPatterns("/*");
        //指定过滤器要放行的路劲
        filter.addInitParameter("exclusions","*.jsp,*.png,*.js,*.css,*.ico,*.gif,/druid/*");
        //指定处理该路劲的过滤器
        filter.setFilter(new WebStatFilter());
        return filter;
    }

    /**
     * 需要使用到连接池的配置信息
     * @return
     * @throws Exception
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource() throws Exception{
//        DruidDataSource dataSource=new DruidDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/test?useSSL=true&useUnicode=true&characterEncoding=utf-8");
//        dataSource.setUsername("root");
//        dataSource.setPassword("admin");
//        dataSource.setMinIdle(5);
//        dataSource.setInitialSize(10);
//        dataSource.setMaxActive(100);
//        dataSource.setMaxWait(2000);
//        dataSource.setFilters("stat,wall");
//        return dataSource;
        return new DruidDataSource();
    }

    /**
     * //以下三个方法是第二讲中新增的配置方法
     * 该方法是向spring容器中注入一个负责监控统计方法性能的对象，这个对象类似于执勤做日志处理的切面类对象
     * @return
     */
    @Bean
    public DruidStatInterceptor getDruidStatInterceptor(){
        return new DruidStatInterceptor();
    }

    /**
     * 该方法是向spring容器中注入一个表示监控性能的切点对象，
     * 换句话说，将要监控的切点封装到了JdkRegexpMethodPointcut类对象中了
     * @return
     */
    @Bean
    @Scope("prototype")//表示每次从容器中获取的都是新对象（默认是单例）
    public JdkRegexpMethodPointcut getMethodPointcut(){
        JdkRegexpMethodPointcut pointcut=new JdkRegexpMethodPointcut();
        //设置切点
        pointcut.setPattern("com.baidu.firstboot.service.impl.*");
        return pointcut;
    }

    /**
     * 该方法的作用是将要监控的切点交给指定的切面处理，相当于起到了切点和切面的枢纽配置作用
     * @param statInterceptor   处理切点的切面
     * @param pointcut  需要监控的七切点
     * @return
     */
    @Bean
    public DefaultPointcutAdvisor getDefaultPointcutAdvisor(
            DruidStatInterceptor statInterceptor,JdkRegexpMethodPointcut pointcut){
        DefaultPointcutAdvisor advisor=new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);//注入切点
        advisor.setAdvice(statInterceptor);//注入切面
        return advisor;
    }
}
