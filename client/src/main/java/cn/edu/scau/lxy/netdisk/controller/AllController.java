package cn.edu.scau.lxy.netdisk.controller;

import cn.edu.scau.lxy.netdisk.entity.File;
import cn.edu.scau.lxy.netdisk.entity.Folder;
import cn.edu.scau.lxy.netdisk.entityVO.AllVO;
import cn.edu.scau.lxy.netdisk.entityVO.FileVO;
import cn.edu.scau.lxy.netdisk.feign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/all")
public class AllController {

    @Autowired
    private FileFeign fileFeign;
    @Autowired
    private FolderFeign folderFeign;
    @Autowired
    private CopyFeign copyFeign;
    @Autowired
    private MoveFeign moveFeign;
    @Autowired
    private DeleteFeign deleteFeign;


    @PostMapping("/findByPath")
    @ResponseBody
    public AllVO findByPath(HttpServletRequest request) throws UnsupportedEncodingException {
        long uid=Long.parseLong(request.getParameter("uid"));
        String path=request.getParameter("path");

        java.net.URLDecoder urlDecoder=new java.net.URLDecoder();
        String path1=urlDecoder.decode(request.getParameter("path"),"utf-8");
        //System.out.println(path1);

        List<Folder> list1=folderFeign.findByPath(uid,path1);
        List<File> list2=fileFeign.findByPath(uid,path1);
        List<Object> list=new ArrayList<>();
        list.addAll(list1);
        list.addAll(list2);

        return new AllVO(0,"",100,list);
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

    @GetMapping("/count")
    @ResponseBody
    public int count() {
        return fileFeign.count();
    }

    @PostMapping("/copyto")
    @ResponseBody
    public void copyto(@RequestParam("ffids") String ffids,@RequestParam("fids") String fids,@RequestParam("path") String path,@RequestParam("uid") long uid){
        System.out.println(ffids+" "+fids+" "+path);
        copyFeign.copy(ffids,fids,path,uid);
    }

    @PostMapping("/moveto")
    @ResponseBody
    public void moveto(@RequestParam("ffids") String ffids,@RequestParam("fids") String fids,@RequestParam("path") String path){
        //System.out.println(ffids+" "+fids+" "+path);
        moveFeign.move(ffids,fids,path);
    }

    @PostMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam("ffids") String ffids,@RequestParam("fids") String fids,@RequestParam("uid") long uid,@RequestParam("uname") String uname){
        System.out.println(ffids+" "+fids+" "+uid+" "+uname+" ");
        deleteFeign.delete(ffids,fids,uid,uname);
    }
}
