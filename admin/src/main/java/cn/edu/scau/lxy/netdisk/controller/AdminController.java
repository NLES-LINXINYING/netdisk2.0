package cn.edu.scau.lxy.netdisk.controller;

import cn.edu.scau.lxy.netdisk.entity.Parameter;
import cn.edu.scau.lxy.netdisk.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ParameterRepository parameterRepository;

    @GetMapping("/param")
    public Parameter findByName(@RequestParam("name") String name){
        System.out.println(name);
        return parameterRepository.findByName(name);
    }
}
