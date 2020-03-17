package cn.edu.scau.lxy.netdisk.client.controller;

import cn.edu.scau.lxy.netdisk.client.feign.FolderFeign;
import cn.edu.scau.lxy.netdisk.client.entity.File;
import cn.edu.scau.lxy.netdisk.client.entity.Folder;
import cn.edu.scau.lxy.netdisk.client.entity.Myshare;
import cn.edu.scau.lxy.netdisk.client.entityVO.AllVO;
import cn.edu.scau.lxy.netdisk.client.entityVO.MyshareVO;
import cn.edu.scau.lxy.netdisk.client.feign.FileFeign;
import cn.edu.scau.lxy.netdisk.client.feign.MyshareFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/myshare")
public class MyShareController {

    @Autowired
    private MyshareFeign myshareFeign;
    @Autowired
    private FileFeign fileFeign;
    @Autowired
    private FolderFeign folderFeign;


    @GetMapping("/findAll")
    @ResponseBody
    public MyshareVO findAll(@RequestParam("uid") long uid) {
        return new MyshareVO(0,"",100,myshareFeign.findAll(uid));
    }

    @PostMapping("/add")
    @ResponseBody
    public Myshare add(@RequestParam("time") int time, @RequestParam("code") int code,
                       @RequestParam("uid") long uid, @RequestParam(value = "fid",required = false) String fid, @RequestParam(value = "ffid",required = false) String ffid) {
        return myshareFeign.add(time,code,uid,fid,ffid);
    }

    @PostMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam("mids") String mids) {
        System.out.println(mids);
        myshareFeign.deleteById(mids);
    }

    @GetMapping("/check")
    @ResponseBody
    public AllVO check(@RequestParam("link") String link, @RequestParam("code") String code) throws UnsupportedEncodingException {
        List<Myshare> list=myshareFeign.findByLink(link);
        //提取码不正确
        if(!list.get(0).getCode().equals(code)){
            return new AllVO(1,"",100,null);
        }

        List<Object> all=new ArrayList<>();
        List<Folder> fflist=new ArrayList<>();
        List<File> flist=new ArrayList<>();

        String paramstr=URLDecoder.decode(link.substring(link.lastIndexOf("?")+1),"UTF-8");
        String[] strs=paramstr.split("&");
        String[] ffids;
        String[] fids;
        for(int i=0;i<strs.length;i++){
            System.out.println(strs[i]);
            if(strs[i].contains("ffid")){
                ffids=splitIds(strs[i].substring(strs[i].lastIndexOf("=")+1));
                //System.out.println(ffids[0]);
                for(int j=0;j<ffids.length;j++){
                    fflist.add(folderFeign.findById(Long.parseLong(ffids[j])));
                }
            }else{
                fids=splitIds(strs[i].substring(strs[i].lastIndexOf("=")+1));
                //System.out.println(fids[0]);
                for(int j=0;j<fids.length;j++){
                    flist.add(fileFeign.findById(Long.parseLong(fids[j])));
                }
            }
        }
        all.addAll(fflist);
        all.addAll(flist);

        return new AllVO(0,"",100,all);
    }

    //多文件fid分割
    public String[] splitIds(String ids){
        String[] strs=ids.split(",");
        return strs;
    }
}
