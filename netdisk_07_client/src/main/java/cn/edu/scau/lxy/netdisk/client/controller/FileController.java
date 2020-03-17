package cn.edu.scau.lxy.netdisk.client.controller;

import cn.edu.scau.lxy.netdisk.client.entity.File;
import cn.edu.scau.lxy.netdisk.client.entityVO.FileVO;
import cn.edu.scau.lxy.netdisk.client.feign.FileFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileFeign fileFeign;


    @GetMapping("/add")
    @ResponseBody
    public int add(@RequestParam("name") String name, @RequestParam("path") String path, @RequestParam("type") long type, @RequestParam("size") long size, @RequestParam("uid") long uid) {
        return fileFeign.add(name, path, type, size, uid);
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public int deleteById(@PathVariable("id") long id) {
        return fileFeign.deleteById(id);
    }

    @GetMapping("/findById")
    @ResponseBody
    public File findById(@RequestParam("id") long id){
        return fileFeign.findById(id);
    }

    @GetMapping("/findByPath")
    @ResponseBody
    public FileVO findByPath(@RequestParam("uid") long uid, @RequestParam("path") String path) {
        return new FileVO(0,"",100,fileFeign.findByPath(uid,path));
    }

    @GetMapping("/findByName")
    @ResponseBody
    public FileVO findByName(@RequestParam("uid") long uid, @RequestParam("name") String name) {
        return new FileVO(0,"",100,fileFeign.findByName(uid,name));
    }

    @GetMapping("/findByType")
    @ResponseBody
    public FileVO findByType(@RequestParam("uid") long uid, @RequestParam("type") long type) {
        return new FileVO(0,"",100,fileFeign.findByType(uid,type));
    }

    @GetMapping("/updateName")
    @ResponseBody
    public int updateName(@RequestParam("id") long id, @RequestParam("name") String name) {
        return fileFeign.updateName(id, name);
    }

    @GetMapping("/count")
    @ResponseBody
    public int count() {
        return fileFeign.count();
    }
}
