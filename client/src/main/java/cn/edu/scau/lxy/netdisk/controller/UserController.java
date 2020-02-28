package cn.edu.scau.lxy.netdisk.controller;

import cn.edu.scau.lxy.netdisk.entity.User;
import cn.edu.scau.lxy.netdisk.entityVO.UserVO;
import cn.edu.scau.lxy.netdisk.feign.FileFeign;
import cn.edu.scau.lxy.netdisk.feign.ParamFeign;
import cn.edu.scau.lxy.netdisk.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserFeign userFeign;
    @Autowired
    private ParamFeign paramFeign;
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

    @GetMapping("/deleteByName/{name}")
    @ResponseBody
    public int deleteByName(@PathVariable("name") String name){
        return userFeign.deleteByName(name);
    }

    @GetMapping("/findByName/{name}")
    @ResponseBody
    public User findByName(@PathVariable("name") String name){
        return userFeign.findByName(name);
    }

    @GetMapping("/findAll")
    @ResponseBody
    public UserVO findAll(@RequestParam("page") int page, @RequestParam("limit") int limit){
        return new UserVO(0,"",100,userFeign.findAll((page-1)*limit,limit));
    }

    @GetMapping("/updatePassword1")
    @ResponseBody
    public String updatePassword1(@RequestParam("name") String name,@RequestParam("password") String password){
        userFeign.updatePassword1(name,password);
        return userFeign.findByName(name).getPassword();
    }

    @GetMapping("/updatePassword2/{name}/{password2}")
    @ResponseBody
    public String updatePassword2(@PathVariable("name") String name,@PathVariable("password2") String password2){
        userFeign.updatePassword2(name,password2);
        return userFeign.findByName(name).toString();
    }

    @GetMapping("/count")
    @ResponseBody
    public String count(){
        int result=userFeign.count();
        return "当前用户数量为："+result;
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

            return 1;
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

    @PostMapping("/chgPath")
    @ResponseBody
    public void chgPath(HttpSession session,@RequestParam("path") String path){
        if(session!=null){
            session.setAttribute("curpath",path);
        }
    }

    @PostMapping("/chgPage")
    @ResponseBody
    public void chgPage(HttpSession session,@RequestParam("page") String page){
        if(session!=null){
            session.setAttribute("page",page);
        }
    }

    @PostMapping("/chgTmpname")
    @ResponseBody
    public void chgTmpname(HttpSession session,@RequestParam("tmpname") String name){
        if(session!=null){
            session.setAttribute("tmpname",name);
        }
    }

    @GetMapping("/updateUsedMemory")
    @ResponseBody
    public long updateUsedMemory(@RequestParam("uid") long id){
        long total=fileFeign.countMemory(id);
        userFeign.updateUsedMemory(id,total);
        return total;
    }

    @GetMapping("/updatePhone")
    @ResponseBody
    public long updatePhone(@RequestParam("uid") long id,@RequestParam("phone") String phone){
        return userFeign.updatePhone(id,phone);
    }

    @GetMapping("/updateEmail")
    @ResponseBody
    public long updateEmail(@RequestParam("uid") long id,@RequestParam("email") String email){
        return userFeign.updateEmail(id,email);
    }

    @GetMapping("getPhone")
    @ResponseBody
    public String getPhone(@RequestParam("name") String name){
        return userFeign.findByName(name).getPhone();
    }

    @GetMapping("getEmail")
    @ResponseBody
    public String getEmail(@RequestParam("name") String name){
        return userFeign.findByName(name).getEmail();
    }
}
