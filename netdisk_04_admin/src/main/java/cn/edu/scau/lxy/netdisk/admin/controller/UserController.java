package cn.edu.scau.lxy.netdisk.admin.controller;

import cn.edu.scau.lxy.netdisk.admin.entity.User;
import cn.edu.scau.lxy.netdisk.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/findAll/{index}/{limit}")
    public List<User> findAll(@PathVariable("index") int index, @PathVariable("limit") int limit){
        return userRepository.findAll(index,limit);
    }

    @GetMapping("/count")
    public int count(){
        return userRepository.count();
    }
}
