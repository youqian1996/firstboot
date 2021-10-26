package com.baidu.firstboot.controller;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class BaseController {
    @Resource
    private MessageSource messageSource;
    /**
     * 根据指定key值读取资源文件中的信息
     * @param key
     * @param args
     * @return
     */
    public String getMsg(String key,String...args){
        //Locale.getDefault():取得当前系统的语言环境信息
        return messageSource.getMessage(key,args, Locale.getDefault());
    }
    @InitBinder
    public void initBinder(WebDataBinder binder){
        //创建一个日期格式化类
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //追加一个自定义的转换器，遇到java.util.Date类型，就是用定义好的SimpleDateFormat类来转换
        binder.registerCustomEditor(java.util.Date.class,new CustomDateEditor(sdf,false));
    }

    public String upload(String name, HttpServletRequest request){
       if (request instanceof StandardMultipartHttpServletRequest){
           StandardMultipartHttpServletRequest req=(StandardMultipartHttpServletRequest) request;
           //取得上传的文件对象集合
           List<MultipartFile> list=req.getFiles("photo");
            //对集合进行迭代
           Iterator<MultipartFile> iter=list.iterator();
           while (iter.hasNext()){
               MultipartFile multipartFile=iter.next();
               System.out.println(multipartFile.getName());
               System.out.println(multipartFile.getOriginalFilename());
               System.out.println(multipartFile.getSize());
               System.out.println(multipartFile.getContentType());
               //文件保存
               try {
                   String suffix=multipartFile.getOriginalFilename().split("\\.")[1];
                   multipartFile.transferTo(new File("D:"+File.separator+"data"+File.separator
                       +UUID.randomUUID().toString().replaceAll("-","")+suffix));
               }catch (Exception e){
                   e.printStackTrace();
               }
               System.out.println("*********************************");
           }
       }
        return "上传成功！";
    }
}
