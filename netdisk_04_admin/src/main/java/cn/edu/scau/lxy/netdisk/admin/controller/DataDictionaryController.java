package cn.edu.scau.lxy.netdisk.admin.controller;

import cn.edu.scau.lxy.netdisk.admin.entity.DataDictionary;
import cn.edu.scau.lxy.netdisk.admin.repository.DataDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dataDictionary")
public class DataDictionaryController {

    @Autowired
    private DataDictionaryRepository dataDictionaryRepository;


    @GetMapping("/add/{type}/{description}/{code}/{value}")
    public int add(@PathVariable("type") String type,@PathVariable("description") String description,@PathVariable("code") long code,@PathVariable("value") String value){
        DataDictionary dataDictionary=new DataDictionary();
        dataDictionary.setDict_type(type);
        dataDictionary.setDict_description(description);
        dataDictionary.setCode(code);
        dataDictionary.setCode_value(value);
        return dataDictionaryRepository.add(dataDictionary);
    }

    @GetMapping("/deleteByID/{id}")
    public int deleteByID(@PathVariable("id") long id){
        return dataDictionaryRepository.deleteByID(id);
    }

    @GetMapping("/findByID/{id}")
    public DataDictionary findByID(@PathVariable("id") long id){
        return dataDictionaryRepository.findByID(id);
    }

    @GetMapping("/findAll/{index}/{limit}")
    public List<DataDictionary> findAll(@PathVariable("index") int index, @PathVariable("limit") int limit){
        return dataDictionaryRepository.findAll(index,limit);
    }

    @GetMapping("/update/{id}/{type}/{description}/{code}/{value}")
    public int update(@PathVariable("id") long id,@PathVariable("type") String type,@PathVariable("description") String description,@PathVariable("code") long code,@PathVariable("value") String value){
        return dataDictionaryRepository.update(id,type,description,code,value);
    }

    @GetMapping("/count")
    public int count(){
        return dataDictionaryRepository.count();
    }
}
