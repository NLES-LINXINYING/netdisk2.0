package cn.edu.scau.lxy.netdisk.controller;

import cn.edu.scau.lxy.netdisk.entity.User;
import cn.edu.scau.lxy.netdisk.entityVO.UserVO;
import cn.edu.scau.lxy.netdisk.feign.FileFeign;
import cn.edu.scau.lxy.netdisk.feign.ParameterFeign;
import cn.edu.scau.lxy.netdisk.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/netdisk")
public class SignInAndSignUpController {

    @Autowired
    private UserFeign userFeign;
    @Autowired
    private ParameterFeign paramFeign;
    @Autowired
    private FileFeign fileFeign;


    @PostMapping("/register")
    @ResponseBody
    public int add(HttpServletRequest request){
        String name=request.getParameter("name");
        String password=request.getParameter("password");

        User user=userFeign.findByName(name);
        if(user==null){
            userFeign.add(name,password);
            String mypath=paramFeign.findByName("syspath").getValue()+name+"/";
            java.io.File file=new java.io.File(mypath);
            java.io.File file1=new java.io.File(mypath+"回收站/");
            java.io.File file2=new java.io.File(mypath+"私密空间/");

            //判断目标文件父目录是否存在，不存在则创建
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }

            //创建用户自己的目录
            file.mkdir();
            file1.mkdir();
            file2.mkdir();

            return 1;
        }

        return 0;
    }

    @PostMapping("/login")
    @ResponseBody
    public int login(HttpServletRequest request, HttpSession session){
        String name=request.getParameter("name");
        String password=request.getParameter("password");

        User user=userFeign.findByName(name);
        if(user!=null && user.getPassword().equals(password)){

            //查找文件存储路径，+ ”uname/“
            String path=paramFeign.findByName("syspath").getValue();
            path+=user.getName()+"/";
            //System.out.println(path);

            session.setAttribute("user",user);
            session.setAttribute("status","logined");
            session.setAttribute("id",user.getId());
            session.setAttribute("name",user.getName());
            session.setAttribute("password1",user.getPassword());
            session.setAttribute("password2",user.getPassword2());
            session.setAttribute("phone",user.getPhone());
            session.setAttribute("email",user.getEmail());
            session.setAttribute("picture",user.getPicture());
            session.setAttribute("totalMemory",user.getTotalMemory());
            session.setAttribute("usedMemory",user.getUsedMemory());
            session.setAttribute("syspath",path);
            session.setAttribute("curpath",path);
            session.setAttribute("tmpname","test");
            session.setAttribute("page","/all");

            //根据用户类型，返回1（普通用户），2（管理员）
            if(user.getType()==1){
                return 1;
            }else if(user.getType()==2){
                return 2;
            }
        }
        return 0;
    }

    @PostMapping("/logout")
    @ResponseBody
    public int logout(HttpSession session){
        if(session!=null){
            session.invalidate();
            return 1;
        }
        return 0;
    }
}
