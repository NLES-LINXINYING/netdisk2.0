package cn.edu.scau.lxy.netdisk.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/*
 *系统所有页面跳转控制器
 */
@Controller
@RequestMapping("/netdisk/v1")
public class PageController {

    /*
     * 功能描述 注册登录页面
     * @author linxinying
     * @date 2020/3/29 11:06
     * @param
     * @return java.lang.String
     */
    @GetMapping("/login")
    public String login(){
        return "/login";
    }


    /*
     * 功能描述  管理员所有页面重定向
     * @author linxinying
     * @date 2020/3/29 11:06
     * @param pageName
     * @return java.lang.String
     */
    @GetMapping("/admin/{location}")
    public String redirect(@PathVariable("location") String pageName){
        return "/admin/"+pageName;
    }



    /*
     * 功能描述  普通用户所有页面重定向
     * @author linxinying
     * @date 2020/3/29 11:07
     * @param pageName
     * @return java.lang.String
     */
    @GetMapping("/user/{location}")
    public String redirect3(@PathVariable("location") String pageName, HttpServletRequest request){
        return "/user/"+pageName;
    }
}
