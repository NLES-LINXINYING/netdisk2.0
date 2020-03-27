package cn.edu.scau.lxy.netdisk.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 *系统所有页面跳转控制器
 */
@Controller
public class PageController {


    //注册登录页面
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    //管理员所有页面重定向
    @GetMapping("/redirect/admin/{location}")
    public String redirect(@PathVariable("location") String pageName){
        return "/admin/"+pageName;
    }

    //普通用户所有页面重定向
    @GetMapping("/redirect/user/{location}")
    public String redirect2(@PathVariable("location") String pageName){
        return "/user/"+pageName;
    }

    //普通用户所有页面重定向
    @GetMapping("/redirect/user2/{location}")
    public String redirect3(@PathVariable("location") String pageName){
        return "/user2/"+pageName;
    }
}
