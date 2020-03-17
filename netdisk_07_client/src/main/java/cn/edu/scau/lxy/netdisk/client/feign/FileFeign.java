package cn.edu.scau.lxy.netdisk.client.feign;

import cn.edu.scau.lxy.netdisk.client.entity.File;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "file")
public interface FileFeign {

    @PostMapping("/file/add")
    public int add(@RequestParam("name") String name, @RequestParam("path") String path, @RequestParam("type") long type, @RequestParam("size") long size, @RequestParam("uid") long uid);

    @GetMapping("/file/delete/{id}")
    public int deleteById(@PathVariable("id") long id);

    @GetMapping("/file/findById")
    public File findById(@RequestParam("id") long id);

    @GetMapping("/file/findByPath")
    public List<File> findByPath(@RequestParam("uid") long uid, @RequestParam("path") String path);

    @GetMapping("/file/findByName")
    public List<File> findByName(@RequestParam("uid") long uid, @RequestParam("name") String name);

    @GetMapping("/file/findByType")
    public List<File> findByType(@RequestParam("uid") long uid, @RequestParam("type") long type);

    @GetMapping("/file/findByNameAndType")
    public List<File> findByNameAndType(@RequestParam("uid") long uid, @RequestParam("type") long type, @RequestParam("name") String name);

    @GetMapping("/file/updateName")
    public int updateName(@RequestParam("id") long id, @RequestParam("name") String name);

    /*@GetMapping("/file/updatePath/{id}/{path}/{ffid}")
    public int updatePath(@PathVariable("id") long id, @PathVariable("path") String path, @PathVariable("ffid") long ffid);*/

    @GetMapping("/file/count")
    public int count();

    @GetMapping("/file/countMemory")
    public long countMemory(@RequestParam("uid") long uid);
}
