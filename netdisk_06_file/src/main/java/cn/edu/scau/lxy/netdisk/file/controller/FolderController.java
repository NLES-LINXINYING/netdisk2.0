package cn.edu.scau.lxy.netdisk.file.controller;

import cn.edu.scau.lxy.netdisk.common.entity.MultiResult;
import cn.edu.scau.lxy.netdisk.common.entity.SingleResult;
import cn.edu.scau.lxy.netdisk.common.entity.StatusCode;
import cn.edu.scau.lxy.netdisk.file.repository.FileRepository;
import cn.edu.scau.lxy.netdisk.file.repository.FolderRepository;
import cn.edu.scau.lxy.netdisk.file.entity.File;
import cn.edu.scau.lxy.netdisk.file.entity.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/folder")
public class FolderController {

    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private FileRepository fileRepository;


    /*
     * 功能描述 新建文件夹，自动获取系统当前时间
     * @author linxinying
     * @date 2020/3/19 20:23
     * @param name 文件夹名
     * @param path 文件夹所在路径
     * @param uid  用户ID
     * @return SingleResult
     */
    @PostMapping("/add")
    public SingleResult add(@RequestParam String name, @RequestParam String path, @RequestParam long uid){
        Folder ff=folderRepository.findByNameAndPath(name,path);

        //文件夹名字不冲突，可以创建
        if( ff == null ){
            Timestamp time=new Timestamp(System.currentTimeMillis());
            Folder folder=new Folder();
            folder.setName(name);
            folder.setPath(path);
            folder.setModifyTime(time);
            folder.setUid(uid);
            int result = folderRepository.add(folder);
            if(result>0){
                java.io.File dir=new java.io.File(path+name);
                dir.mkdir();
                /*if(dir.mkdir()){
                    System.out.println("创建目录--"+path+name+"--成功！");
                }else{
                    System.out.println("创建目录--"+path+name+"--失败！");
                }*/
            }
            return new SingleResult(StatusCode.OK,"创建成功",1,null);
        }
        return new SingleResult(StatusCode.ERROR,"创建失败",0,null);
    }


    @DeleteMapping("/delete/{id}")
    public int deleteById(@PathVariable long id){
        return folderRepository.deleteById(id);
    }

    @GetMapping("/findById")
    public Folder findById(@RequestParam("id") long id){
        return folderRepository.findById(id);
    }



    /*
     * 功能描述 根据目录搜索目录
     * @author linxinying
     * @date 2020/3/20 8:51
     * @param uid
     * @param path
     * @return java.util.List<java.lang.Object>
     */
    @GetMapping("/findByPath")
    public MultiResult findByPath(@RequestParam long uid, @RequestParam String path) throws UnsupportedEncodingException {
        List<Object> list = folderRepository.findByPath(uid,path);
        return new MultiResult(StatusCode.OK,"查询成功",1,list);
    }

    @GetMapping("/findByName")
    public List<Object> findByName(@RequestParam("uid") long uid,@RequestParam("name") String name){
        return folderRepository.findByName(uid,name);
    }



    /*
     * 功能描述 文件夹重命名，需要遍历该文件夹下所有文件和文件夹，修改其路径
     * @author linxinying
     * @date 2020/3/20 9:33
     * @param id 文件夹ID
     * @param name 新名字
     * @return int
     */
    @PutMapping("/updateName")
    public SingleResult updateName(@RequestParam long id,@RequestParam String name){
        Folder folder=folderRepository.findById(id);
        Folder folder1=folderRepository.findByNameAndPath(name,folder.getPath());
        if(folder1!=null){
            return new SingleResult(StatusCode.ERROR,"重命名失败",0,null);
        }
        java.io.File src=new java.io.File(folder.getPath()+folder.getName());
        java.io.File dst=new java.io.File(folder.getPath()+name);
        if(src.renameTo(dst)){
            List<Folder> fflist=folderRepository.fuzzyQueryByPath(folder.getPath()+folder.getName()+"/");
            Folder foldertmp=new Folder();
            String path;
            String[] pathstrs;
            int ff_len=fflist.size();
            if(ff_len>0){
                for(int i=0;i<ff_len;i++){
                    foldertmp=folderRepository.findById(fflist.get(i).getId());
                    path=foldertmp.getPath();
                    pathstrs=path.split(folder.getPath()+folder.getName()+"/");
                    if(pathstrs.length==0){
                        folderRepository.updatePath(fflist.get(i).getId(),folder.getPath()+name+"/");
                    }else{
                        folderRepository.updatePath(fflist.get(i).getId(),folder.getPath()+name+"/"+pathstrs[1]);
                    }
                }
            }
            List<File> flist=fileRepository.fuzzyQueryByPath(folder.getPath()+folder.getName()+"/");
            File filetmp=new File();
            int f_len=flist.size();
            if(f_len>0){
                for(int i=0;i<ff_len;i++){
                    filetmp=fileRepository.findById(flist.get(i).getId());
                    path=filetmp.getPath();
                    pathstrs=path.split(folder.getPath()+folder.getName()+"/");
                    if(pathstrs.length==0){
                        fileRepository.updatePath(flist.get(i).getId(),folder.getPath()+name+"/");
                    }else{
                        fileRepository.updatePath(flist.get(i).getId(),folder.getPath()+name+"/"+pathstrs[1]);
                    }
                }
            }
        }
        int result=folderRepository.updateName(id,name);

        return new SingleResult(StatusCode.OK,"重命名成功",0,result);
    }



    @GetMapping("updatePath")
    public int updatePath(@RequestParam("id") long id,@RequestParam("path") String path){
        return folderRepository.updatePath(id,path);
    }


    /*
     * 功能描述 根据用户ID统计文件夹总个数
     * @author linxinying
     * @date 2020/3/25 18:49
     * @param uid
     * @return cn.edu.scau.lxy.netdisk.common.entity.SingleResult
     */
    @GetMapping("/numOfFolder")
    public SingleResult numOfFolder(@RequestParam long uid){
        return new SingleResult(StatusCode.OK,"查询成功",1,folderRepository.countByUid(uid));
    }
}
