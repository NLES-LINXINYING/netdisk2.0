package cn.edu.scau.lxy.netdisk.client.controller;

import cn.edu.scau.lxy.netdisk.client.entity.Folder;
import cn.edu.scau.lxy.netdisk.client.entityVO.FolderVO;
import cn.edu.scau.lxy.netdisk.client.feign.FolderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/folder")
public class FolderController {

    @Autowired
    private FolderFeign folderFeign;


    @PostMapping("/add")
    @ResponseBody
    public int newFolder(@RequestParam("name") String name, @RequestParam("path") String path, @RequestParam("uid") long uid) {
        int result=folderFeign.add(name, path, uid);
        System.out.println(result);
        return result;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public int deleteById(@PathVariable("id") long id) {
        return folderFeign.deleteById(id);
    }

    @GetMapping("/findById")
    @ResponseBody
    public Folder findById(@RequestParam("id") long id){
        return folderFeign.findById(id);
    }

    @PostMapping("/findByPath")
    @ResponseBody
    public FolderVO findByPath(@RequestParam("uid") long uid, @RequestParam("path") String path) throws UnsupportedEncodingException {
        return new FolderVO(0,"",100,folderFeign.findByPath(uid, path));
    }

    @GetMapping("/findByName")
    @ResponseBody
    public FolderVO findByName(@RequestParam("uid") long uid, @RequestParam("name") String name) {
        return new FolderVO(0,"",100,folderFeign.findByName(uid, name));
    }

    @GetMapping("/updateName")
    @ResponseBody
    public int updateName(@RequestParam("id") long id, @RequestParam("name") String name) {
        return folderFeign.updateName(id, name);
    }

    @GetMapping("/updatePath/{id}/{path}/{ffid}")
    @ResponseBody
    public int updatePath(@PathVariable("id") long id, @PathVariable("path") String path, @PathVariable("ffid") long ffid) {
        return folderFeign.updatePath(id, path, ffid);
    }

    @GetMapping("/count")
    @ResponseBody
    public int count() {
        return folderFeign.count();
    }
}
