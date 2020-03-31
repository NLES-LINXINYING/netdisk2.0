package cn.edu.scau.lxy.netdisk.file.controller;

import cn.edu.scau.lxy.netdisk.file.repository.FileRepository;
import cn.edu.scau.lxy.netdisk.file.repository.FolderRepository;
import cn.edu.scau.lxy.netdisk.file.repository.RecyclebinRepository;
import cn.edu.scau.lxy.netdisk.file.entity.File;
import cn.edu.scau.lxy.netdisk.file.entity.Folder;
import cn.edu.scau.lxy.netdisk.file.entity.Recyclebin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Timestamp;

@RestController
@RequestMapping("")
public class DeleteController {

    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private RecyclebinRepository recyclebinRepository;


    /*
     * 功能描述 文件非彻底删除，移动到回收站
     * @author linxinying
     * @date 2020/3/20 9:51
     * @param ffids
     * @param fids
     * @param uid
     * @param uname
     * @return void
     */
    @PostMapping("/delete")
    public void delete(@RequestParam("ffids") String ffids,@RequestParam("fids") String fids,@RequestParam("uid") long uid,@RequestParam("uname") String uname) throws IOException {
        //回收站路径
        String recyclePath="D:/upload/"+uname+"/回收站/";

        java.io.File rb_dir=new java.io.File(recyclePath);
        if(!rb_dir.exists()){
            rb_dir.mkdirs();
        }

        //删除时间、有效时间
        long curTime=System.currentTimeMillis();
        Timestamp deltime=new Timestamp(curTime);
        Timestamp efftime=new Timestamp(curTime+30*86400000);


        Folder folder=new Folder();
        File file=new File();
        Recyclebin recyclebin=new Recyclebin();

        if(!ffids.equals("null")){
            String[] ffidstrs=splitIds(ffids);
            for(int i=0;i<ffidstrs.length;i++){
                folder=folderRepository.findById(Long.parseLong(ffidstrs[i]));
                java.io.File src=new java.io.File(folder.getPath(),folder.getName());
                deleteFile(src,recyclePath,uid,deltime,efftime);
                //把本地文件夹移动到回收站
                src.renameTo(new java.io.File(recyclePath+folder.getName()));

                //插入回收站
                Recyclebin recyclebin1=new Recyclebin();
                recyclebin1.setTimeOfDelete(deltime);
                recyclebin1.setTimeOfEffective(efftime);
                recyclebin1.setOldPath(folder.getPath());
                recyclebin1.setUid(uid);
                recyclebin1.setFfid(folder.getId());
                recyclebinRepository.addFolder(recyclebin1);
            }
        }

        if(!fids.equals("null")){
            String[] fidstrs=splitIds(fids);
            for(int i=0;i<fidstrs.length;i++){
                file=fileRepository.findById(Long.parseLong(fidstrs[i]));
                java.io.File src=new java.io.File(file.getPath()+file.getName());
                deleteFile(src,recyclePath,uid,deltime,efftime);
                //把本地文件移动到回收站
                src.renameTo(new java.io.File(recyclePath+file.getName()));

                //插入回收站
                Recyclebin recyclebin1=new Recyclebin();
                recyclebin1.setFid(file.getId());
                recyclebin1.setTimeOfDelete(deltime);
                recyclebin1.setTimeOfEffective(efftime);
                recyclebin1.setOldPath(file.getPath());
                recyclebin1.setUid(uid);
                recyclebinRepository.addFile(recyclebin1);
            }
        }
    }


    //遍历多层目录
    public void deleteFile(java.io.File source,String recyclePath,long uid,Timestamp deltime,Timestamp efftime)throws IOException {

        //source是文件夹
        if(source.isDirectory()){

            //修改文件夹路径
            Folder folderdata=folderRepository.findByNameAndPath(source.getName(),replace(source.getParent())+"/");
            folderRepository.updatePath(folderdata.getId(),recyclePath);


            //遍历文件夹
            java.io.File[] files = source.listFiles();
            if(files.length==0){
                return;
            }else{
                for(int i = 0 ;i<files.length;i++){
                    deleteFile(files[i],recyclePath+source.getName()+"/",uid,deltime,efftime);
                }
            }
        }

        //source是文件
        else if(source.isFile()){
            //修改文件的路径
            File filedata=fileRepository.findByNameAndPath(source.getName(),replace(source.getParent())+"/");
            fileRepository.updatePath(filedata.getId(),recyclePath);
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




    /*//从回收站删除资源
    @PostMapping("/deleteFromRB")
    public void deleteFromRB(@RequestParam("rids") String rids) throws IOException {
        Recyclebin recyclebin=new Recyclebin();
        Folder folder=new Folder();
        File file=new File();

        String[] ridstrs=splitIds(rids);
        for(int i=0;i<ridstrs.length;i++){
            recyclebin=recyclebinRepository.findById(Long.parseLong(ridstrs[i]));
            recyclebinRepository.delete(recyclebin.getId());
            if(recyclebin.getFfid()!=0){
                folder=folderRepository.findById(recyclebin.getFfid());
                //本地删除文件
                java.io.File folder1=new java.io.File(folder.getPath()+folder.getName());
                deleteFile2(folder1);
                folder1.delete();
            }else{
                file=fileRepository.findById(recyclebin.getFid());
                //本地删除文件
                java.io.File file1=new java.io.File(file.getPath()+file.getName());
                deleteFile2(file1);
                file1.delete();
            }
        }
    }


    public void deleteFile2(java.io.File source)throws IOException {

        //source是文件夹
        if(source.isDirectory()){
            java.io.File file1 = new java.io.File(replace(source.getParent())+"/"+source.getName());

            //数据库删除
            Folder folderdata=folderRepository.findByNameAndPath(source.getName(),replace(source.getParent())+"/");
            folderRepository.deleteById(folderdata.getId());

            java.io.File[] files = source.listFiles();
            if(files.length==0){
                return;
            }else{
                for(int i = 0 ;i<files.length;i++){
                    deleteFile2(files[i]);
                }
            }
        }

        //source是文件
        else if(source.isFile()){
            File filedata=fileRepository.findByNameAndPath(source.getName(),replace(source.getParent())+"/");
            fileRepository.deleteById(filedata.getId());
        }
    }*/
}
