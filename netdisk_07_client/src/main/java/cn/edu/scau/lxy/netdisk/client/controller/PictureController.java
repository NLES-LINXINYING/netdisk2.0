package cn.edu.scau.lxy.netdisk.client.controller;

import cn.edu.scau.lxy.netdisk.client.entityVO.FileVO;
import cn.edu.scau.lxy.netdisk.client.feign.FileFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private FileFeign fileFeign;

    //图片，文件类型为1
    long type=1;

    @GetMapping("/findAll")
    @ResponseBody
    public FileVO findAll(@RequestParam("uid") long uid) {
        return new FileVO(0,"",100,fileFeign.findByType(uid,type));
    }


    @GetMapping("/findByNameAndType")
    @ResponseBody
    public FileVO findByNameAndType(@RequestParam("uid") long uid,@RequestParam("name") String name){
        return new FileVO(0,"",100,fileFeign.findByNameAndType(uid,type,name));
    }
}
