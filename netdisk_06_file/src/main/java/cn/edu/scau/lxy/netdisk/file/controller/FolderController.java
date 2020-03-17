package cn.edu.scau.lxy.netdisk.file.controller;

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

    //新建文件夹时自动获取系统当前时间
    @GetMapping("add")
    public int add(@RequestParam("name") String name, @RequestParam("path") String path,@RequestParam("uid") long uid){
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
                if(dir.mkdir()){
                    System.out.println("创建目录--"+path+name+"--成功！");
                }else{
                    System.out.println("创建目录--"+path+name+"--失败！");
                }
            }
            return 1;
        }
        return 0;
    }

    @GetMapping("delete/{id}")
    public int deleteById(@PathVariable("id") long id){
        return folderRepository.deleteById(id);
    }

    @GetMapping("findById")
    public Folder findById(@RequestParam("id") long id){
        return folderRepository.findById(id);
    }

    @GetMapping("findByPath")
    public List<Folder> findByPath(@RequestParam("uid") long uid, @RequestParam("path") String path) throws UnsupportedEncodingException {
        return folderRepository.findByPath(uid,path);
    }

    @GetMapping("findByName")
    public List<Folder> findByName(@RequestParam("uid") long uid,@RequestParam("name") String name){
        return folderRepository.findByName(uid,name);
    }

    @GetMapping("updateName")
    public int updateName(@RequestParam("id") long id,@RequestParam("name") String name){
        Folder folder=folderRepository.findById(id);
        Folder folder1=folderRepository.findByNameAndPath(name,folder.getPath());
        if(folder1!=null){
            return 0;
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
        return folderRepository.updateName(id,name);
    }

    @GetMapping("updatePath")
    public int updatePath(@RequestParam("id") long id,@RequestParam("path") String path){
        return folderRepository.updatePath(id,path);
    }

    @GetMapping("/count")
    public int count(){
        return folderRepository.count();
    }
}
