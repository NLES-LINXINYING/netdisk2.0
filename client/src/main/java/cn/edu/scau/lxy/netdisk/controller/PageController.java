package cn.edu.scau.lxy.netdisk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 *系统所有页面跳转控制器
 */
@Controller
public class PageController {

    /*//注册登录页面
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    //系统首页
    @RequestMapping("/homepage")
    public String homepage(){
        return "homepage";
    }

    //全部文件页面
    @RequestMapping("/all")
    public String all(){
        return "all";
    }

    //图片页面
    @RequestMapping("/picture")
    public String picture(){
        return "picture";
    }

    //文档页面
    @RequestMapping("/word")
    public String word(){
        return "word";
    }

    //视频页面
    @RequestMapping("/video")
    public String video(){
        return "video";
    }

    //种子页面
    @RequestMapping("/torrent")
    public String torrent(){
        return "torrent";
    }

    //音乐页面
    @RequestMapping("/music")
    public String music(){
        return "music";
    }

    //其它页面
    @RequestMapping("/other")
    public String other(){
        return "other";
    }

    //我的分享页面
    @RequestMapping("/myshare")
    public String myshare(){
        return "myshare";
    }

    //接收分享页面
    @RequestMapping("/acceptShare")
    public String acceptShare(){
        return "acceptShare";
    }

    //回收站页面
    @RequestMapping("/recyclebin")
    public String recyclebin(){
        return "recyclebin";
    }

    //扩容页面
    @RequestMapping("/expand")
    public String expand(){
        return "expand";
    }

    //创建分享页面
    @RequestMapping("/addShare")
    public String addShare(){
        return "addShare";
    }

    //复制-选择目录页面
    @RequestMapping("/selectPath")
    public String selectPath(){
        return "selectPath";
    }

    //移动-选择目录页面
    @RequestMapping("/selectPath2")
    public String selectPath2(){
        return "selectPath2";
    }

    //个人资料页面
    @RequestMapping("/personalCenter")
    public String personalCenter(){
        return "personalCenter";
    }

    //修改密码页面
    @RequestMapping("/updatePassword1")
    public String updatePassword1(){
        return "updatePassword1";
    }

    //修改二级密码页面
    @RequestMapping("/updatePassword2")
    public String updatePassword2(){
        return "updatePassword2";
    }

    //手机绑定页面
    @RequestMapping("/phoneBinding")
    public String phoneBinding(){
        return "phoneBinding";
    }

    //邮箱绑定页面
    @RequestMapping("/emailBinding")
    public String emailBinding(){
        return "emailBinding";
    }*/



    //注册登录页面
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    //管理员所有页面重定向
    @RequestMapping("/redirect/admin/{location}")
    public String redirect(@PathVariable("location") String pageName){
        return "/admin/"+pageName;
    }

    //普通用户所有页面重定向user
    @RequestMapping("/redirect/user/{location}")
    public String redirect2(@PathVariable("location") String pageName){
        return "/user/"+pageName;
    }
}
