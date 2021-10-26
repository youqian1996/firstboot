package com.baidu.firstboot.controller;
import com.baidu.firstboot.annotations.SystemLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
@Controller
@RequestMapping("/emp/*")
public class EmpController extends BaseController {
    @RequestMapping("/add")
    @SystemLog(desc = "添加员工")
    public String addEmp(HttpServletRequest request){
        //伪登录
        request.getSession().setAttribute("username","李四");
        return "pages/add_emp";
    }
}
