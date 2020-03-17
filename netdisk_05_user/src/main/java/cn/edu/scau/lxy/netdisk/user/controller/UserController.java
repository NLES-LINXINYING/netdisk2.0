package cn.edu.scau.lxy.netdisk.user.controller;

import cn.edu.scau.lxy.netdisk.common.entity.SingleResult;
import cn.edu.scau.lxy.netdisk.common.entity.StatusCode;
import cn.edu.scau.lxy.netdisk.user.repository.UserRepository;
import cn.edu.scau.lxy.netdisk.common.util.JwtUtil;
import cn.edu.scau.lxy.netdisk.user.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping("/add/{name}/{password}")
    public int add(@PathVariable("name") String name,@PathVariable("password") String password){
        User user=new User();
        user.setName(name);
        //System.out.println(encoder.encode(password).length());
        user.setPassword(encoder.encode(password));
        return userRepository.add(user);
    }

    @GetMapping("/findByNameAndPassword/{name}/{password}")
    public User findByNameAndPassword(@PathVariable("name") String name,@PathVariable("password") String password){
        return userRepository.findByNameAndPassword(name,password);
    }

    @GetMapping("/findByName")
    public User findByName(@RequestParam("name") String name){
        return userRepository.findByName(name);
    }

    @GetMapping("/findAll/{index}/{limit}")
    public List<User> findAll(@PathVariable("index") int index, @PathVariable("limit") int limit){
        return userRepository.findAll(index,limit);
    }

    @GetMapping("/updatePassword1/{name}/{password}")
    public String updatePassword1(@PathVariable("name") String name,@PathVariable("password") String password){
        int result=userRepository.updatePassword1(name,password);
        //System.out.println("修改成功！！！");
        return userRepository.findByName(name).toString();
    }

    @GetMapping("/updatePassword2/{name}/{password2}")
    public String updatePassword2(@PathVariable("name") String name,@PathVariable("password2") String password2){
        int result=userRepository.updatePassword2(name,password2);
        //System.out.println("修改成功！！！");
        return userRepository.findByName(name).toString();
    }

    @GetMapping("/updateUsedMemory")
    public int updateUsedMemory(@RequestParam("id") long id,@RequestParam("usedM") long usedM){
        return userRepository.updateUsedMemory(id,usedM);
    }

    @GetMapping("/updatePhone")
    public int updatePhone(@RequestParam("id") long id,@RequestParam("phone") String phone){
        return userRepository.updatePhone(id,phone);
    }

    @GetMapping("/updateEmail")
    public int updateEmail(@RequestParam("id") long id,@RequestParam("email") String email){
        return userRepository.updateEmail(id,email);
    }

    @GetMapping("/count")
    public int count(){
        int result=userRepository.count();
        //System.out.println("当前用户数量为："+result);
        return result;
    }








    @GetMapping("/login")
    public SingleResult login(@RequestParam String name, @RequestParam String password){
        //先根据用户名查询对象
        User user=userRepository.findByNameAndPassword(name,password);

        if(user==null){
            return new SingleResult(false, StatusCode.LOGINERROR,"登录失败",null);
        }

        //采用JWT生成令牌
        String token="";
        Map<String,Object> map=new HashMap<>();

        if(user.getType()==1){
            token=jwtUtil.createJWT(String.valueOf(user.getId()),user.getName(),"user");
            map.put("token",token);
            map.put("role","user");
        }else{
            token=jwtUtil.createJWT(String.valueOf(user.getId()),user.getName(),"admin");
            map.put("token",token);
            map.put("role","admin");
        }
        return new SingleResult(false, StatusCode.LOGINERROR,"登录成功",map);
    }

    /*
     * 功能描述
     * @author linxinying
     * @date 2020/3/15 21:40
     * @param name
     * @return int
     */
    @GetMapping("/deleteByName/{name}")
    public SingleResult deleteByName(@PathVariable("name") String name, HttpServletRequest request){
        String header=request.getHeader("Authorization");
        if(header==null||header.equals("")){
            return new SingleResult(false,StatusCode.ACCESSERROR,"权限不足1",null);
        }
        if(!header.startsWith("Bearer ")){
            return new SingleResult(false,StatusCode.ACCESSERROR,"权限不足2",null);
        }
        //得到token
        String token=header.substring(7);
        try{
            Claims claims=jwtUtil.parseJWT(token);
            String roles= (String) claims.get("roles");
            if(roles==null||!roles.equals("admin")){
                return new SingleResult(false,StatusCode.ACCESSERROR,"权限不足3",null);
            }
        }catch (Exception e){
            return new SingleResult(false,StatusCode.ACCESSERROR,"权限不足4",null);
        }
        userRepository.deleteByName(name);
        return new SingleResult(true,StatusCode.OK,"删除成功",null);
    }
}
