package cn.edu.scau.lxy.netdisk.file.controller;

import cn.edu.scau.lxy.netdisk.common.entity.MultiResult;
import cn.edu.scau.lxy.netdisk.common.entity.StatusCode;
import cn.edu.scau.lxy.netdisk.file.repository.FileRepository;
import cn.edu.scau.lxy.netdisk.file.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linxinying
 * @description 全部文件文件管理
 * @date 2020/3/18 9:26
 */
@RestController
@RequestMapping("/all")
public class AllController {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FolderRepository folderRepository;


    /*
     * 功能描述 根据用户ID和路径查询文件夹和文件
     * @author linxinying
     * @date 2020/3/18 9:46
     * @param uid 用户ID
     * @param path 路径
     * @return cn.edu.scau.lxy.netdisk.common.entity.MultiResult
     */
    @GetMapping("/findByPath")
    public MultiResult findByPath(HttpServletRequest request){
        long uid=Long.parseLong(request.getParameter("uid"));
        String path=request.getParameter("path");
        //System.out.println(path);

        List<Object> list=new ArrayList<>();
        list.addAll(folderRepository.findByPath(uid,path));
        list.addAll(fileRepository.findByPath(uid,path));
        return new MultiResult(StatusCode.OK,"查询成功",list.size(),list);
    }



    /*
     * 功能描述 根据用户ID和文件名查询文件，模糊查询
     * @author linxinying
     * @date 2020/3/18 9:50
     * @param uid
     * @param fname
     * @return cn.edu.scau.lxy.netdisk.common.entity.MultiResult
     */
    @GetMapping("/findByName")
    public MultiResult findByName(@RequestParam long uid,@RequestParam String name){
        List<Object> list=new ArrayList<>();
        list.addAll(folderRepository.findByName(uid,name));
        list.addAll(fileRepository.findByName(uid,name));
        return new MultiResult(StatusCode.OK,"查询成功",list.size(),list);
    }
}
