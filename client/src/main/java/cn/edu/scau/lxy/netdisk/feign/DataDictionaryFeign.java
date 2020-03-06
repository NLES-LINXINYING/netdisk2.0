package cn.edu.scau.lxy.netdisk.feign;

import cn.edu.scau.lxy.netdisk.entity.DataDictionary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "admin")
public interface DataDictionaryFeign {

    @GetMapping("/dataDictionary/add/{type}/{description}/{code}/{value}")
    public int add(@PathVariable("type") String type,@PathVariable("description") String description,@PathVariable("code") long code,@PathVariable("value") String value);


    @GetMapping("/dataDictionary/deleteByID/{id}")
    public int deleteByID(@PathVariable("id") long id);


    @GetMapping("/dataDictionary/findByID/{id}")
    public DataDictionary findByID(@PathVariable("id") long id);


    @GetMapping("/dataDictionary/findAll/{index}/{limit}")
    public List<DataDictionary> findAll(@PathVariable("index") int index, @PathVariable("limit") int limit);


    @GetMapping("/dataDictionary/update/{id}/{type}/{description}/{code}/{value}")
    public int update(@PathVariable("id") long id,@PathVariable("type") String type,@PathVariable("description") String description,@PathVariable("code") long code,@PathVariable("value") String value);

    @GetMapping("/dataDictionary/count")
    public int count();
}
