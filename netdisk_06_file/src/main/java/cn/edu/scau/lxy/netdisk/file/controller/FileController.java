package cn.edu.scau.lxy.netdisk.file.controller;


import cn.edu.scau.lxy.netdisk.file.repository.FileRepository;
import cn.edu.scau.lxy.netdisk.file.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @PostMapping("add")
    public int add(@RequestParam("name") String name, @RequestParam("path") String path, @RequestParam("type") long type, @RequestParam("size") long size, @RequestParam("uid") long uid) {
        File file = new File();
        file.setName(name);
        file.setPath(path);
        file.setType(type);
        file.setSize(size);
        file.setUid(uid);
        return fileRepository.add(file);
    }

    @GetMapping("delete/{id}")
    public int deleteById(@PathVariable("id") long id) {
        return fileRepository.deleteById(id);
    }

    @GetMapping("findById")
    public File findById(@RequestParam("id") long id) {
        return fileRepository.findById(id);
    }

    @GetMapping("findByPath")
    public List<File> findByPath(@RequestParam("uid") long uid, @RequestParam("path") String path) {
        return fileRepository.findByPath(uid,path);
    }

    @GetMapping("findByName")
    public List<File> findByName(@RequestParam("uid") long uid, @RequestParam("name") String name) {
        return fileRepository.findByName(uid, name);
    }

    @GetMapping("findByType")
    public List<File> findByType(@RequestParam("uid") long uid, @RequestParam("type") long type) {
        return fileRepository.findByType(uid, type);
    }

    @GetMapping("findByNameAndType")
    public List<File> findByNameAndType(@RequestParam("uid") long uid, @RequestParam("type") long type, @RequestParam("name") String name) {
        return fileRepository.findByNameAndType(uid, type,name);
    }

    @GetMapping("updateName")
    public int updateName(@RequestParam("id") long id, @RequestParam("name") String name) {
        File file=fileRepository.findById(id);
        File file1=fileRepository.findByNameAndPath(name,file.getPath());
        if(file1!=null){
            return 0;
        }
        java.io.File src=new java.io.File(file.getPath()+file.getName());
        java.io.File dst=new java.io.File(file.getPath()+name);
        src.renameTo(dst);
        return fileRepository.updateName(id, name);
    }

    /*@GetMapping("updatePath/{id}/{path}/{ffid}")
    public int updatePath(@PathVariable("id") long id, @PathVariable("path") String path, @PathVariable("ffid") long ffid) {
        return fileRepository.updatePath(id, path, ffid);
    }*/

    @GetMapping("/count")
    public int count() {
        return fileRepository.count();
    }

    @GetMapping("/countMemory")
    public long countMemory(@RequestParam("uid") long uid){
        long usedMemory=0;
        List<File> list=fileRepository.findByUid(uid);
        for(int i=0;i<list.size();i++){
            usedMemory += list.get(i).getSize();
        }
        return usedMemory;
    }
}
