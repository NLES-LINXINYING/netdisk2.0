package cn.edu.scau.lxy.netdisk.file.controller;

import cn.edu.scau.lxy.netdisk.file.repository.FileRepository;
import cn.edu.scau.lxy.netdisk.file.repository.FolderRepository;
import cn.edu.scau.lxy.netdisk.file.entity.File;
import cn.edu.scau.lxy.netdisk.file.entity.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/move")
public class MoveController {

    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private FileRepository fileRepository;

    @PostMapping("movedemo")
    public void move(@RequestParam("ffids") String ffids,@RequestParam("fids") String fids,@RequestParam("path") String path) throws IOException {
        System.out.println(path);
        Folder folder=new Folder();
        File file=new File();

        if(!ffids.equals("null")){
            String[] ffidstrs=splitIds(ffids);
            long[] ffidarr=new long[ffidstrs.length];
            for(int i=0;i<ffidstrs.length;i++){
                ffidarr[i]=Long.parseLong(ffidstrs[i]);
                System.out.println(ffidarr[i]);

                folder=folderRepository.findById(ffidarr[i]);
                java.io.File src=new java.io.File(folder.getPath()+folder.getName());
                moveFile(src,path);
                src.renameTo(new java.io.File(path+src.getName()));
                System.out.println(path+src.getName());
            }
        }

        if(!fids.equals("null")){
            String[] fidstrs=splitIds(fids);
            long[] fidarr=new long[fidstrs.length];
            for(int i=0;i<fidstrs.length;i++){
                fidarr[i]=Long.parseLong(fidstrs[i]);
                System.out.println(fidarr[i]);

                file=fileRepository.findById(fidarr[i]);
                java.io.File src=new java.io.File(file.getPath()+file.getName());
                System.out.println(file.getName());
                moveFile(src,path);
            }
        }
    }


    public void moveFile(java.io.File source,String dest)throws IOException {
        System.out.println("move");

        //创建目的地文件夹
        java.io.File destfile = new java.io.File(dest);
        if(!destfile.exists()){
            destfile.mkdir();
        }

        //source是文件夹
        if(source.isDirectory()){
            java.io.File file1=new java.io.File(dest+source.getName());

            //source.renameTo(new java.io.File(dest+source.getName()));
            //file1.mkdir();

            //数据库删除
            Folder folderdata=folderRepository.findByNameAndPath(source.getName(),replace(source.getParent())+"/");
            System.out.println(replace(source.getParent())+"/"+source.getName());
            folderRepository.deleteById(folderdata.getId());

            //数据库插入
            folderdata.setPath(dest);
            folderRepository.add(folderdata);

            java.io.File[] files = source.listFiles();
            if(files.length==0){
                return;
            }else{
                for(int i = 0 ;i<files.length;i++){
                    moveFile(files[i],replace(file1.getPath())+"/");
                }
            }
        }

        //source是文件
        else if(source.isFile()){
            source.renameTo(new java.io.File(dest+source.getName()));

            //数据库删除
            File filedata=fileRepository.findByNameAndPath(source.getName(),replace(source.getParent())+"/");
            System.out.println(source.getName()+" "+replace(source.getParent())+"/");
            fileRepository.deleteById(filedata.getId());

            //插入新记录
            filedata.setPath(dest);
            fileRepository.add(filedata);
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
