package cn.edu.scau.lxy.netdisk.admin.controller;

import cn.edu.scau.lxy.netdisk.admin.repository.UserRepository;
import cn.edu.scau.lxy.netdisk.common.entity.MultiResult;
import cn.edu.scau.lxy.netdisk.common.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/findAll")
    public MultiResult findAll(@RequestParam int page, @RequestParam int limit) {
        try {
            int count = userRepository.count();
            List<Object> list = userRepository.findAll((page - 1) * limit, limit);
            return new MultiResult(StatusCode.OK, "查询成功", count, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new MultiResult(StatusCode.ERROR, "查询失败", 0, null);
        }
    }

    @GetMapping("/count")
    public int count() {
        return userRepository.count();
    }
}
