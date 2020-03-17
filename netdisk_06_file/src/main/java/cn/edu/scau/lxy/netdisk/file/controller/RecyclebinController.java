package cn.edu.scau.lxy.netdisk.file.controller;

import cn.edu.scau.lxy.netdisk.file.repository.FileRepository;
import cn.edu.scau.lxy.netdisk.file.repository.FolderRepository;
import cn.edu.scau.lxy.netdisk.file.repository.RecyclebinRepository;
import cn.edu.scau.lxy.netdisk.file.entity.File;
import cn.edu.scau.lxy.netdisk.file.entity.Folder;
import cn.edu.scau.lxy.netdisk.file.entity.Recyclebin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/recyclebin")
public class RecyclebinController {

    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private RecyclebinRepository recyclebinRepository;


    @GetMapping("findAll")
    public List<Recyclebin> findAll(@RequestParam("uid") long uid) {
        return recyclebinRepository.findAll(uid);
    }

    @PostMapping("delete")
    public void delete(@RequestParam("rids") String rids) throws IOException {
        Recyclebin recyclebin=new Recyclebin();
        Folder folder=new Folder();
        File file=new File();

        String[] ridstrs=splitIds(rids);
        for(int i=0;i<ridstrs.length;i++){
            recyclebin=recyclebinRepository.findById(Long.parseLong(ridstrs[i]));

            if(recyclebin.getFfid()!=0){
                folder=folderRepository.findById(recyclebin.getFfid());
                recyclebinRepository.deleteByFFid(folder.getFfid());

                //本地删除文件
                java.io.File folder1=new java.io.File(folder.getPath()+folder.getName()+"/");
                deleteFile(folder1);
            }else{
                file=fileRepository.findById(recyclebin.getFid());
                recyclebinRepository.deleteByFid(file.getId());

                //本地删除文件
                java.io.File file1=new java.io.File(file.getPath()+file.getName());
                deleteFile(file1);
            }
        }
    }

    @PostMapping("revert")
    public void revert(@RequestParam("rids") String rids) throws IOException {
        Recyclebin recyclebin=new Recyclebin();
        Folder folder=new Folder();
        File file=new File();

        String[] ridstrs=splitIds(rids);
        for(int i=0;i<ridstrs.length;i++){
            recyclebin=recyclebinRepository.findById(Long.parseLong(ridstrs[i]));
            recyclebinRepository.delete(recyclebin.getId());

            if(recyclebin.getFfid()!=0){
                folder=folderRepository.findById(recyclebin.getFfid());

                java.io.File folder1=new java.io.File(folder.getPath()+folder.getName());
                revertFile(folder1,recyclebin.getOldPath());

                folder1.renameTo(new java.io.File(recyclebin.getOldPath()+folder1.getName()));
            }else{
                file=fileRepository.findById(recyclebin.getFid());
                recyclebinRepository.deleteByFid(file.getId());

                java.io.File file1=new java.io.File(file.getPath()+file.getName());
                revertFile(file1,recyclebin.getOldPath());

                file1.renameTo(new java.io.File(recyclebin.getOldPath()+file1.getName()));
            }
        }
    }


    //递归处理多层目录
    public void deleteFile(java.io.File source)throws IOException {

        //source是文件夹
        if(source.isDirectory()){

            java.io.File file1 = new java.io.File(replace(source.getParent())+"/"+source.getName());

            //删除folder记录
            Folder folderdata=folderRepository.findByNameAndPath(source.getName(),replace(source.getParent())+"/");
            folderRepository.deleteById(folderdata.getId());

            java.io.File[] files = source.listFiles();
            if(files.length==0){
                source.delete();
                return;
            }else{
                for(int i = 0 ;i<files.length;i++){
                    deleteFile(files[i]);
                }
                source.delete();
            }
        }

        //source是文件
        else if(source.isFile()){
            File filedata=fileRepository.findByNameAndPath(source.getName(),replace(source.getParent())+"/");

            //删除file记录
            fileRepository.deleteById(filedata.getId());

            source.delete();
        }
    }


    //递归处理多层目录
    public void revertFile(java.io.File source,String path)throws IOException {

        //source是文件夹
        if(source.isDirectory()){
            java.io.File file1 = new java.io.File(replace(source.getParent())+"/"+source.getName());

            //修改folder记录
            Folder folderdata=folderRepository.findByNameAndPath(source.getName(),replace(source.getParent())+"/");
            folderRepository.updatePath(folderdata.getId(),path);

            java.io.File[] files = source.listFiles();
            if(files.length==0){
                return;
            }else{
                for(int i = 0 ;i<files.length;i++){
                    revertFile(files[i],path+file1.getName()+"/");
                }
            }
        }

        //source是文件
        else if(source.isFile()){
            File filedata=fileRepository.findByNameAndPath(source.getName(),replace(source.getParent())+"/");

            //修改file记录
            fileRepository.updatePath(filedata.getId(),path);
        }
    }


    //多文件fid分割
    public String[] splitIds(String ids){
        String[] strs=ids.split(",");
        return strs;
    }

    // '/'替换'\'
    public String replace(String src){
        String dst=src.replaceAll("\\\\","/");
        return dst;
    }
}
