package cn.edu.scau.lxy.netdisk.controller;

import cn.edu.scau.lxy.netdisk.entity.User;
import cn.edu.scau.lxy.netdisk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/add/{name}/{password}")
    public int add(@PathVariable("name") String name,@PathVariable("password") String password){
        User user=new User();
        user.setName(name);
        user.setPassword(password);
        /*user.setPhone(phone);
        user.setEmail(email);*/
        System.out.println("插入成功！！！");
        return userRepository.add(user);
    }

    @GetMapping("/deleteByName/{name}")
    public int deleteByName(@PathVariable("name") String name){
        int result=userRepository.deleteByName(name);
        System.out.println("删除记录条数："+result);
        return result;
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
        System.out.println("修改成功！！！");
        return userRepository.findByName(name).toString();
    }

    @GetMapping("/updatePassword2/{name}/{password2}")
    public String updatePassword2(@PathVariable("name") String name,@PathVariable("password2") String password2){
        int result=userRepository.updatePassword2(name,password2);
        System.out.println("修改成功！！！");
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
        System.out.println("当前用户数量为："+result);
        return result;
    }
}
