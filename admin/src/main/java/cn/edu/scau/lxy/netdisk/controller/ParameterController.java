package cn.edu.scau.lxy.netdisk.controller;

import cn.edu.scau.lxy.netdisk.entity.Parameter;
import cn.edu.scau.lxy.netdisk.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parameter")
public class ParameterController {
    @Autowired
    private ParameterRepository parameterRepository;

    @GetMapping("/add")
    public int add(@RequestParam("name") String name,@RequestParam("value") String value,@RequestParam("description") String description){
        Parameter parameter=new Parameter();
        parameter.setName(name);
        parameter.setValue(value);
        parameter.setDescription(description);
        return parameterRepository.add(parameter);
    }

    @GetMapping("/deleteByID/{id}")
    public int deleteByID(@PathVariable("id") long id){
        return parameterRepository.deleteByID(id);
    }

    @GetMapping("/findByName/{name}")
    public Parameter findByName(@PathVariable("name") String name){
        return parameterRepository.findByName(name);
    }

    @GetMapping("/findAll/{index}/{limit}")
    public List<Parameter> findAll(@PathVariable("index") int index,@PathVariable("limit") int limit){
        return parameterRepository.findAll(index,limit);
    }

    @GetMapping("/update")
    public int update(@RequestParam("id") long id,@RequestParam("name") String name,@RequestParam("value") String value,@RequestParam("description") String description){
        Parameter parameter=new Parameter();
        parameter.setId(id);
        parameter.setName(name);
        parameter.setValue(value);
        parameter.setDescription(description);
        return parameterRepository.update(parameter);
    }

    @GetMapping("/count")
    public int count(){
        return parameterRepository.count();
    }
}
