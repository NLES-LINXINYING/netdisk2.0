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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/copy")
public class CopyController {

    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private FileRepository fileRepository;

    @PostMapping("copydemo")
    public void copy(@RequestParam("ffids") String ffids,@RequestParam("fids") String fids,@RequestParam("path") String path,@RequestParam("uid") long uid) throws IOException {
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
                java.io.File src=new java.io.File(folder.getPath(),folder.getName());
                copyFile(src,path,uid);
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
                copyFile(src,path,uid);
            }
        }
    }


    public void copyFile(java.io.File source,String dest,long uid)throws IOException {
        System.out.println("copy");

        //创建目的地文件夹
        java.io.File destfile = new java.io.File(dest);
        if(!destfile.exists()){
            destfile.mkdir();
        }

        //source是文件夹
        if(source.isDirectory()){
            java.io.File file1 = new java.io.File(dest+source.getName());
            file1.mkdir();

            //更新数据库
            Folder folderdata=folderRepository.findByNameAndPath(source.getName(),replace(source.getParent())+"/");
            folderdata.setPath(dest);
            folderdata.setUid(uid);
            folderRepository.add(folderdata);

            java.io.File[] files = source.listFiles();
            if(files.length==0){
                return;
            }else{
                for(int i = 0 ;i<files.length;i++){
                    copyFile(files[i],replace(file1.getPath())+"/",uid);
                }
            }
        }

        //source是文件
        else if(source.isFile()){
            //System.out.println("file");
            FileInputStream fis = new FileInputStream(source);
            java.io.File dfile = new java.io.File(dest+source.getName());
            //System.out.println(dest+source.getName());
            if(!dfile.exists()){
                dfile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(dfile);
            byte[] b = new byte[1024];
            int len;
            while ((len = fis.read(b))!=-1) {
                fos.write(b, 0 , len);
            }

            fos.close();
            fis.close();

            File filedata=fileRepository.findByNameAndPath(source.getName(),replace(source.getParent())+"/");
            //System.out.println(source.getName()+" "+replace(source.getParent())+"/");
            filedata.setPath(dest);
            filedata.setUid(uid);
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
