package cn.edu.scau.lxy.netdisk.controller;

import cn.edu.scau.lxy.netdisk.feign.FileFeign;
import cn.edu.scau.lxy.netdisk.feign.FolderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private FileFeign fileFeign;
    @Autowired
    private FolderFeign folderFeign;

    @PostMapping(value = "/uploadFile")
    @ResponseBody
    public Map<String,Object> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        long uid=Long.parseLong(request.getParameter("uid"));
        String uploadDir = request.getParameter("path");

        Map<String,Object> map  = new HashMap<>();

        try {
            upload(file, uploadDir, file.getOriginalFilename(),uid);

            map.put("code",0);
            map.put("msg","上传成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("msg","上传失败");
            return map;
        }
    }



    @PostMapping("/uploadFolder")
    @ResponseBody
    public Map<String,Object> uploadFolder(@RequestParam("uploadFiles") MultipartFile[] files,@RequestParam("uid") long uid,@RequestParam("path") String path){

        Map<String,Object> map  = new HashMap<>();


        try {
            String fname="";
            String fpath="";
            for(int i=0;i<files.length;i++){
                fname=files[i].getOriginalFilename().substring(files[i].getOriginalFilename().lastIndexOf("/")+1);
                fpath=path+files[i].getOriginalFilename().substring(0,files[i].getOriginalFilename().lastIndexOf("/")+1);
                upload(files[i], fpath, fname,uid);
            }

            map.put("code",0);
            map.put("msg","上传成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("msg","上传失败");
            return map;
        }
    }


    //保存文件到本地，更新数据库
    public String upload(MultipartFile file, String path, String fileName,long uid) throws Exception {
        String filePath = path+"/"+fileName;

        //获取文件名后缀
        String suffix=fileName.substring(fileName.lastIndexOf("."));
        long type=6;//文件类型，默认为其它
        long size=file.getSize();//文件大小

        //根据后缀判定文件类型
        if(suffix.equalsIgnoreCase(".png")
                ||suffix.equalsIgnoreCase(".jpg")
                ||suffix.equalsIgnoreCase(".jpeg")
                ||suffix.equalsIgnoreCase(".bmp")
                ||suffix.equalsIgnoreCase(".gif")
                ||suffix.equalsIgnoreCase(".webp")
                ||suffix.equalsIgnoreCase(".psd")
                ||suffix.equalsIgnoreCase(".svg")
                ||suffix.equalsIgnoreCase(".tiff")){
            type=1;
            //System.out.println("图片");
        }else if(suffix.equalsIgnoreCase(".txt")
                ||suffix.equalsIgnoreCase(".doc")
                ||suffix.equalsIgnoreCase(".docx")
                ||suffix.equalsIgnoreCase(".xls")
                ||suffix.equalsIgnoreCase(".html")
                ||suffix.equalsIgnoreCase(".jsp")
                ||suffix.equalsIgnoreCase(".rtf")
                ||suffix.equalsIgnoreCase(".wpd")
                ||suffix.equalsIgnoreCase(".pdf")
                ||suffix.equalsIgnoreCase(".ppt")
                ||suffix.equalsIgnoreCase(".pptx")
                ||suffix.equalsIgnoreCase(".htm")){
            type=2;
            //System.out.println("文档");
        }else if(suffix.equalsIgnoreCase(".mp4")
                ||suffix.equalsIgnoreCase(".avi")
                ||suffix.equalsIgnoreCase(".mov")
                ||suffix.equalsIgnoreCase(".wmv")
                ||suffix.equalsIgnoreCase(".navi")
                ||suffix.equalsIgnoreCase(".3gp")
                ||suffix.equalsIgnoreCase(".mkv")
                ||suffix.equalsIgnoreCase(".f4v")
                ||suffix.equalsIgnoreCase(".rmvb")
                ||suffix.equalsIgnoreCase(".webm")){
            type=3;
            //System.out.println("视频");
        }else if(suffix.equalsIgnoreCase(".torrent")){
            type=4;
            //System.out.println("种子");
        }else if(suffix.equalsIgnoreCase(".mp3")
                ||suffix.equalsIgnoreCase(".wma")
                ||suffix.equalsIgnoreCase(".wav")
                ||suffix.equalsIgnoreCase(".mod")
                ||suffix.equalsIgnoreCase(".ra")
                ||suffix.equalsIgnoreCase(".cd")
                ||suffix.equalsIgnoreCase(".md")
                ||suffix.equalsIgnoreCase(".asf")
                ||suffix.equalsIgnoreCase(".aac")
                ||suffix.equalsIgnoreCase(".vqf")
                ||suffix.equalsIgnoreCase(".ape")
                ||suffix.equalsIgnoreCase(".mid")
                ||suffix.equalsIgnoreCase(".ogg")
                ||suffix.equalsIgnoreCase(".m4a")){
            type=5;
            //System.out.println("音乐");
        }


        File dest = new File(filePath);
        // 判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            String[] tmps=replace(dest.getParent()).split("/");
            String f_folder="";
            for(int i=0;i<tmps.length;i++){
                f_folder="";
                for(int j=0;j<=i;j++){
                    f_folder+=tmps[j]+"/";
                }

                java.io.File ff=new java.io.File(f_folder);
                if(!ff.exists()){
                    ff.mkdir();
                    folderFeign.add(tmps[i],replace(ff.getParent())+"/",uid);
                }
            }


            /*String ffname=fileName.substring(0,fileName.lastIndexOf('/'));
            folderFeign.add(ffname,path,uid);*/
        }

        // 判断文件名是否包含路径
        if(fileName.contains("/")){
            path=path+fileName.substring(0,fileName.lastIndexOf('/')+1);
            fileName=fileName.substring(fileName.lastIndexOf('/')+1);
        }

        //判断文件名字是否重复，重复，重命名
        String tempName=""+fileName;
        String filePath2="";
        String tmp="";
        while(dest.exists()){
            tmp=(tempName.split("\\."))[0]+"(1)"+tempName.substring(tempName.lastIndexOf("."));
            filePath2=path+"/"+tmp;
            dest = new File(filePath2);
            tempName=tmp;
        }

        // 保存文件
        file.transferTo(dest);
        //插入数据库
        fileFeign.add(tempName,path,type,size,uid);

        return dest.getName();
    }


    // '/'替换'\'
    public String replace(String src){
        String dst=src.replaceAll("\\\\","/");
        return dst;
    }
}
