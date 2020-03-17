package cn.edu.scau.lxy.netdisk.client.controller;

import cn.edu.scau.lxy.netdisk.client.entity.DataDictionary;
import cn.edu.scau.lxy.netdisk.client.entityVO.DataDictionaryVO;
import cn.edu.scau.lxy.netdisk.client.feign.DataDictionaryFeign;
import cn.edu.scau.lxy.netdisk.client.entity.Parameter;
import cn.edu.scau.lxy.netdisk.client.entityVO.ParameterVO;
import cn.edu.scau.lxy.netdisk.client.entityVO.UserVO;
import cn.edu.scau.lxy.netdisk.client.feign.ParameterFeign;
import cn.edu.scau.lxy.netdisk.client.feign.UserlistFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DataDictionaryFeign dataDictionaryFeign;
    @Autowired
    private ParameterFeign parameterFeign;
    @Autowired
    private UserlistFeign userlistFeign;

    //用户管理部分
    @GetMapping("/findAll_user")
    @ResponseBody
    public UserVO findAll_user(@RequestParam("page") int page, @RequestParam("limit") int limit){
        int count=userlistFeign.count();
        return new UserVO(0,"",count,userlistFeign.findAll((page-1)*limit,limit));
    }


    //数据字典部分
    @GetMapping("/add_dict")
    @ResponseBody
    public int add_dict(@RequestParam("type") String type,@RequestParam("description") String description,@RequestParam("code") long code,@RequestParam("value") String value){
        return dataDictionaryFeign.add(type,description,code,value);
    }

    @GetMapping("/deleteByID_dict")
    @ResponseBody
    public int deleteByID(@RequestParam("id") long id){
        return dataDictionaryFeign.deleteByID(id);
    }

    @GetMapping("/findByID_dict")
    @ResponseBody
    public DataDictionary findByID(@RequestParam("id") long id){
        return dataDictionaryFeign.findByID(id);
    }

    @GetMapping("/findAll_dict")
    @ResponseBody
    public DataDictionaryVO findAll(@RequestParam("page") int page, @RequestParam("limit") int limit){
        int count=dataDictionaryFeign.count();
        return new DataDictionaryVO(0,"",count,dataDictionaryFeign.findAll((page-1)*limit,limit));
    }

    @GetMapping("/update_dict")
    @ResponseBody
    public int update(@RequestParam("id") long id,@RequestParam("type") String type,@RequestParam("description") String description,@RequestParam("code") long code,@RequestParam("value") String value){
        DataDictionary dataDictionary=new DataDictionary(id,type,description,code,value);
        return dataDictionaryFeign.update(id,type,description,code,value);
    }

    @GetMapping("/count_dict")
    @ResponseBody
    public int count(){
        return  dataDictionaryFeign.count();
    }



    //系统参数部分
    @GetMapping("/add_param")
    @ResponseBody
    public int add_dict(@RequestParam("name") String name,@RequestParam("value") String value,@RequestParam("description") String description){
        return parameterFeign.add(name,value,description);
    }

    @GetMapping("/deleteByID_param")
    @ResponseBody
    public int deleteByID_param(@RequestParam("id") long id){
        return parameterFeign.deleteByID(id);
    }

    @GetMapping("/findByName_param")
    @ResponseBody
    public Parameter findByName(@RequestParam("name") String name){
        return parameterFeign.findByName(name);
    }

    @GetMapping("/findAll_param")
    @ResponseBody
    public ParameterVO findAll_param(@RequestParam("page") int page, @RequestParam("limit") int limit){
        int count=parameterFeign.count();
        return new ParameterVO(0,"",count,parameterFeign.findAll((page-1)*limit,limit));
    }

    @GetMapping("/update_param")
    @ResponseBody
    public int update(@RequestParam("id") long id,@RequestParam("name") String name,@RequestParam("value") String value,@RequestParam("description") String description){
        return parameterFeign.update(id,name,value,description);
    }

    @GetMapping("/count_param")
    @ResponseBody
    public int count_param(){
        return parameterFeign.count();
    }
}
