package cn.edu.scau.lxy.netdisk.feign;

import cn.edu.scau.lxy.netdisk.entity.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "admin")
public interface ParameterFeign {
    @GetMapping("/parameter/add")
    public int add(@RequestParam("name") String name,@RequestParam("value") String value,@RequestParam("description") String description);

    @GetMapping("/parameter/deleteByID/{id}")
    public int deleteByID(@PathVariable("id") long id);

    @GetMapping("/parameter/findByName/{name}")
    public Parameter findByName(@PathVariable("name") String name);

    @GetMapping("/parameter/findAll/{index}/{limit}")
    public List<Parameter> findAll(@PathVariable("index") int index, @PathVariable("limit") int limit);

    @GetMapping("/parameter/update")
    public int update(@RequestParam("id") long id, @RequestParam("name") String name, @RequestParam("value") String value, @RequestParam("description") String description);


    @GetMapping("/parameter/count")
    public int count();
}
