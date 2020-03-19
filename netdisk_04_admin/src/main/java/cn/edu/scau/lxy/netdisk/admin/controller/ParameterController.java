package cn.edu.scau.lxy.netdisk.admin.controller;

import cn.edu.scau.lxy.netdisk.admin.entity.Parameter;
import cn.edu.scau.lxy.netdisk.admin.repository.ParameterRepository;
import cn.edu.scau.lxy.netdisk.common.entity.MultiResult;
import cn.edu.scau.lxy.netdisk.common.entity.SingleResult;
import cn.edu.scau.lxy.netdisk.common.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parameter")
public class ParameterController {
    @Autowired
    private ParameterRepository parameterRepository;

    @PostMapping("/add")
    public SingleResult add(@RequestBody Parameter parameter){
        try{parameterRepository.add(parameter);
        return new SingleResult(StatusCode.OK,"增加成功",1,null);}catch (Exception e){
            e.printStackTrace();
            return new SingleResult(StatusCode.ERROR, "增加失败", 0, null);
        }
    }

    @DeleteMapping("/deleteByID/{id}")
    public SingleResult deleteByID(@PathVariable long id){
        try{parameterRepository.deleteByID(id);
        return new SingleResult(StatusCode.OK,"删除成功",1,null);}catch (Exception e){
            e.printStackTrace();
            return new SingleResult(StatusCode.ERROR, "删除失败", 0, null);
        }
    }

    @GetMapping("/findByName/{name}")
    public SingleResult findByName(@PathVariable("name") String name){
        try{Parameter parameter= parameterRepository.findByName(name);
        return new SingleResult(StatusCode.OK,"查询成功",1,parameter);}catch (Exception e){
            e.printStackTrace();
            return new SingleResult(StatusCode.ERROR, "查询失败", 0, null);
        }
    }

    @GetMapping("/findAll")
    public MultiResult findAll(@RequestParam int page,@RequestParam int limit){
        try{int count=parameterRepository.count();
        List<Object> list = parameterRepository.findAll((page-1)*limit,limit);
        return new MultiResult(StatusCode.OK,"删除成功",count,list);}catch (Exception e){
            e.printStackTrace();
            return new MultiResult(StatusCode.ERROR, "查询失败", 0, null);
        }
    }

    @PutMapping("/update")
    public SingleResult update(@RequestBody Parameter parameter){
        try{parameterRepository.update(parameter);
        return new SingleResult(StatusCode.OK,"修改成功",1,null);}catch(Exception e){
            e.printStackTrace();
            return new SingleResult(StatusCode.ERROR, "修改失败", 0, null);
        }
    }

    @GetMapping("/count")
    public int count(){
        return parameterRepository.count();
    }
}
