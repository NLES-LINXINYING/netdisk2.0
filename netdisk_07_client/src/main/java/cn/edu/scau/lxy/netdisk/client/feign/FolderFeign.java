package cn.edu.scau.lxy.netdisk.client.feign;

import cn.edu.scau.lxy.netdisk.client.entity.Folder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "file")
public interface FolderFeign {

    @GetMapping("/folder/add")
    public int add(@RequestParam("name") String name, @RequestParam("path") String path, @RequestParam("uid") long uid);

    @GetMapping("/folder/delete/{id}")
    public int deleteById(@PathVariable("id") long id);

    @GetMapping("/folder/findById")
    public Folder findById(@RequestParam("id") long id);

    @GetMapping("/folder/findByPath")
    public List<Folder> findByPath(@RequestParam("uid") long uid, @RequestParam("path") String path);

    @GetMapping("/folder/findByName")
    public List<Folder> findByName(@RequestParam("uid") long uid,@RequestParam("name") String name);

    @GetMapping("/folder/updateName")
    public int updateName(@RequestParam("id") long id,@RequestParam("name") String name);

    @GetMapping("/folder/updatePath/{id}/{path}/{ffid}")
    public int updatePath(@PathVariable("id") long id,@PathVariable("path") String path,@PathVariable("ffid") long ffid);

    @GetMapping("/folder/count")
    public int count();
}
