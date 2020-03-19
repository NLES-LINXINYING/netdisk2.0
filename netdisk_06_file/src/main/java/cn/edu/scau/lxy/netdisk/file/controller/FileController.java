package cn.edu.scau.lxy.netdisk.file.controller;


import cn.edu.scau.lxy.netdisk.common.entity.MultiResult;
import cn.edu.scau.lxy.netdisk.common.entity.SingleResult;
import cn.edu.scau.lxy.netdisk.common.entity.StatusCode;
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
     * 功能描述 根据用户ID和文件类型查找文件
     * @author linxinying
     * @date 2020/3/18 10:02
     * @param uid
     * @param type
     * @return cn.edu.scau.lxy.netdisk.common.entity.MultiResult
     */
    @GetMapping("/findByType")
    public MultiResult findByType(@RequestParam("uid") long uid, @RequestParam("type") long type) {
        List<Object> list=fileRepository.findByType(uid,type);
        return new MultiResult(0,"",list.size(),list);
    }



    /*
     * 功能描述 根据用户ID、文件名、文件类型查询文件
     * @author linxinying
     * @date 2020/3/18 10:51
     * @param uid
     * @param type
     * @param name
     * @return java.util.List<java.lang.Object>
     */
    @GetMapping("/findByNameAndType")
    public List<Object> findByNameAndType(@RequestParam("uid") long uid, @RequestParam("type") long type, @RequestParam("name") String name) {
        return fileRepository.findByNameAndType(uid, type,name);
    }



    /*
     * 功能描述 根据文件ID修改文件名
     * @author linxinying
     * @date 2020/3/18 10:52
     * @param id
     * @param name
     * @return int
     */
    @PutMapping("updateName")
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




    /*@GetMapping("findByPath")
    public List<Object> findByPath(@RequestParam("uid") long uid, @RequestParam("path") String path) {
        return fileRepository.findByPath(uid,path);
    }

    @GetMapping("findByName")
    public List<Object> findByName(@RequestParam("uid") long uid, @RequestParam("name") String name) {
        return fileRepository.findByName(uid, name);
    }*/

}
