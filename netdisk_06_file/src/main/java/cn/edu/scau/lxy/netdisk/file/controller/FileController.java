package cn.edu.scau.lxy.netdisk.file.controller;


import cn.edu.scau.lxy.netdisk.common.entity.MultiResult;
import cn.edu.scau.lxy.netdisk.common.entity.SingleResult;
import cn.edu.scau.lxy.netdisk.common.entity.StatusCode;
import cn.edu.scau.lxy.netdisk.file.repository.FileRepository;
import cn.edu.scau.lxy.netdisk.file.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileRepository fileRepository;


    /*
     * 功能描述 新建文件
     * @author linxinying
     * @date 2020/3/18 10:40
     * @param file
     * @return int
     */
    @PostMapping("/add")
    public SingleResult add(@RequestBody File file) {
        fileRepository.add(file);
        return new SingleResult(StatusCode.OK,"新建成功",1,null);
    }



    /*
     * 功能描述 根据文件ID删除文件
     * @author linxinying
     * @date 2020/3/18 10:43
     * @param id
     * @return int
     */
    @DeleteMapping("/delete/{id}")
    public SingleResult deleteById(@PathVariable long id) {
        fileRepository.deleteById(id);
        return new SingleResult(StatusCode.OK,"删除成功",1,null);
    }



    /*
     * 功能描述 根据文件ID查询文件
     * @author linxinying
     * @date 2020/3/18 10:44
     * @param id
     * @return cn.edu.scau.lxy.netdisk.file.entity.File
     */
    @GetMapping("/findById")
    public SingleResult findById(@RequestParam long id) {
        File file = fileRepository.findById(id);
        return new SingleResult(StatusCode.OK,"查询成功",1,file);
    }



    /*
     * 功能描述 根据用户ID和文件类型查找文件，文件分类
     * @author linxinying
     * @date 2020/3/18 10:02
     * @param uid
     * @param type
     * @return cn.edu.scau.lxy.netdisk.common.entity.MultiResult
     */
    @GetMapping("/findByType")
    public MultiResult findByType(HttpServletRequest request) {
        long uid=Long.parseLong(request.getParameter("uid"));
        long type=Long.parseLong(request.getParameter("type"));
        List<Object> list=fileRepository.findByType(uid,type);
        return new MultiResult(0,"",list.size(),list);
    }



    /*
     * 功能描述 根据用户ID、文件名、文件类型查询文件，模糊查询
     * @author linxinying
     * @date 2020/3/18 10:51
     * @param uid
     * @param type
     * @param name
     * @return java.util.List<java.lang.Object>
     */
    @GetMapping("/findByNameAndType")
    public MultiResult findByNameAndType(@RequestParam("uid") long uid, @RequestParam("type") long type, @RequestParam("name") String name) {
        List<Object> list = fileRepository.findByNameAndType(uid, type,name);
        return new MultiResult(0,"",list.size(),list);
    }



    /*
     * 功能描述 根据文件ID修改文件名
     * @author linxinying
     * @date 2020/3/18 10:52
     * @param id
     * @param name
     * @return int
     */
    @PutMapping("/updateName")
    public SingleResult updateName(@RequestParam long id, @RequestParam String name) {

        File file=fileRepository.findById(id);
        File file1=fileRepository.findByNameAndPath(name,file.getPath());

        if(file1!=null){
            return new SingleResult(StatusCode.ERROR,"重命名失败",0,null);
        }

        java.io.File src=new java.io.File(file.getPath()+file.getName());
        java.io.File dst=new java.io.File(file.getPath()+name);
        src.renameTo(dst);
        int result=fileRepository.updateName(id, name);

        return new SingleResult(StatusCode.OK,"重命名成功",0,result);
    }


    /*
     * 功能描述 根据用户ID统计各个类别文件个数
     * @author linxinying
     * @date 2020/3/25 17:41
     * @param uid
     * @return cn.edu.scau.lxy.netdisk.common.entity.SingleResult
     */
    @GetMapping("/numOfType")
    public SingleResult numOfType(@RequestParam long uid){
        Map<String,Integer> map=new HashMap<>();
        map.put("picture",fileRepository.findByType(uid,1).size());
        map.put("word",fileRepository.findByType(uid,2).size());
        map.put("video",fileRepository.findByType(uid,3).size());
        map.put("torrent",fileRepository.findByType(uid,4).size());
        map.put("music",fileRepository.findByType(uid,5).size());
        map.put("other",fileRepository.findByType(uid,6).size());
        return new SingleResult(StatusCode.OK,"查询成功",1,map);
    }


    /*
     * 功能描述 根据用户ID统计各个类别文件空间占用量
     * @author linxinying
     * @date 2020/3/25 17:41
     * @param uid
     * @return cn.edu.scau.lxy.netdisk.common.entity.SingleResult
     */
    @GetMapping("/memoryOfType")
    public SingleResult memoryOfType(@RequestParam long uid){
        Map<String,Long> map=new HashMap<>();
        List<Object> list=new ArrayList<>();
        long total=0;

        list=fileRepository.findByType(uid,1);
        for(Object ob:list){
            total+=((File)ob).getSize();
        }
        map.put("picture",total);

        list=fileRepository.findByType(uid,2);
        total=0;
        for(Object ob:list){
            total+=((File)ob).getSize();
        }
        map.put("word",total);

        list=fileRepository.findByType(uid,3);
        total=0;
        for(Object ob:list){
            total+=((File)ob).getSize();
        }
        map.put("video",total);

        list=fileRepository.findByType(uid,4);
        total=0;
        for(Object ob:list){
            total+=((File)ob).getSize();
        }
        map.put("torrent",total);

        list=fileRepository.findByType(uid,5);
        total=0;
        for(Object ob:list){
            total+=((File)ob).getSize();
        }
        map.put("music",total);

        list=fileRepository.findByType(uid,6);
        total=0;
        for(Object ob:list){
            total+=((File)ob).getSize();
        }
        map.put("other",total);


        return new SingleResult(StatusCode.OK,"查询成功",1,map);
    }



    /*
     * 功能描述 根据用户ID统计文件总个数
     * @author linxinying
     * @date 2020/3/25 18:49
     * @param uid
     * @return cn.edu.scau.lxy.netdisk.common.entity.SingleResult
     */
    @GetMapping("/numOfFile")
    public SingleResult numOfFile(@RequestParam long uid){
        return new SingleResult(StatusCode.OK,"查询成功",1,fileRepository.countByUid(uid));
    }

    /*@GetMapping("findByPath")
    public List<Object> findByPath(@RequestParam("uid") long uid, @RequestParam("path") String path) {
        return fileRepository.findByPath(uid,path);
    }

    @GetMapping("findByName")
    public List<Object> findByName(@RequestParam("uid") long uid, @RequestParam("name") String name) {
        return fileRepository.findByName(uid, name);
    }*/


}
