package cn.edu.scau.lxy.netdisk.controller;

import cn.edu.scau.lxy.netdisk.entity.File;
import cn.edu.scau.lxy.netdisk.entity.Folder;
import cn.edu.scau.lxy.netdisk.entityVO.AllVO;
import cn.edu.scau.lxy.netdisk.feign.FileFeign;
import cn.edu.scau.lxy.netdisk.feign.FolderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private FileFeign fileFeign;
    @Autowired
    private FolderFeign folderFeign;


    //模糊搜索，忽略大小写
    @PostMapping("/searchByName")
    @ResponseBody
    public AllVO searchByName(@RequestParam("uid") long uid,@RequestParam("name") String name){
        System.out.println(name+" "+uid);

        List<Folder> list1=folderFeign.findByName(uid,name);
        List<File> list2=fileFeign.findByName(uid,name);
        List<Object> list=new ArrayList<>();
        list.addAll(list2);
        list.addAll(list1);

        return new AllVO(0,"",100,list);
    }
}
