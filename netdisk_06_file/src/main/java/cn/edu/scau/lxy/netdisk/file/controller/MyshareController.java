package cn.edu.scau.lxy.netdisk.file.controller;

import cn.edu.scau.lxy.netdisk.common.entity.MultiResult;
import cn.edu.scau.lxy.netdisk.common.entity.SingleResult;
import cn.edu.scau.lxy.netdisk.common.entity.StatusCode;
import cn.edu.scau.lxy.netdisk.common.util.JwtUtil;
import cn.edu.scau.lxy.netdisk.file.entity.File;
import cn.edu.scau.lxy.netdisk.file.entity.Folder;
import cn.edu.scau.lxy.netdisk.file.entity.Myshare;
import cn.edu.scau.lxy.netdisk.file.repository.FileRepository;
import cn.edu.scau.lxy.netdisk.file.repository.FolderRepository;
import cn.edu.scau.lxy.netdisk.file.repository.MyshareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/myshare")
public class MyshareController {

    @Autowired
    private MyshareRepository myshareRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private JwtUtil jwtUtil;


    /*
     * 功能描述 创建分享链接
     * @author linxinying
     * @date 2020/3/19 20:50
     * @param time 链接有效时间（0，1，2分别代表1天、7天、永久）
     * @param code 提取码
     * @param uid 用户ID
     * @param fid 文件ID数组
     * @param ffid 文件夹ID数组
     * @return cn.edu.scau.lxy.netdisk.file.entity.Myshare
     */
    @PostMapping("/add")
    @ResponseBody
    public SingleResult add(@RequestParam("time") int time, @RequestParam("code") int code, @RequestParam("uid") long uid, @RequestParam(value = "fid",required = false) String fid, @RequestParam(value = "ffid",required = false) String ffid) throws UnsupportedEncodingException {
       System.out.println(ffid+fid);
        Myshare myshare=new Myshare();

        //创建时间
        long curtime=System.currentTimeMillis();
        Timestamp timeOfShare=new Timestamp(curtime);
        myshare.setTimeOfShare(timeOfShare);

        //有效时间
        Timestamp timeofInvalid;
        if(time==0){
            timeofInvalid=new Timestamp(curtime+86400000);
        }else if (time==1){
            timeofInvalid=new Timestamp(curtime+86400000*7);
        }else {
            timeofInvalid=new Timestamp(curtime);
        }
        myshare.setTimeOfInvalid(timeofInvalid);

        //提取码
        String code1="";
        if(code==1){
            code1+=getCode();
        }
        myshare.setCode(code1);
        myshare.setUid(uid);

        //链接
        String tmp="";
        if(!ffid.equals("null")&&fid.equals("null")){
            tmp+="ffid="+ffid;
        }
        else if(ffid.equals("null")&&!fid.equals("null")){
            tmp+="fid="+fid;
        }else{
            tmp+="ffid="+ffid+"&fid="+fid;
        }
        System.out.println(tmp);
        String link= java.net.URLEncoder.encode(tmp,"UTF-8");
        System.out.println(link);

        if(!ffid.equals("null")){
            String[] ffids=splitIds(ffid);
            for(int i=0;i<ffids.length;i++){
                myshare.setFfid(Long.parseLong(ffids[i]));
                myshare.setLink("http://localhost:8763/file/myshare/check?"+link);
                myshareRepository.addfolder(myshare);
            }
        }
        if(!fid.equals("null")){
            String[] fids=splitIds(fid);
            for(int i=0;i<fids.length;i++){
                myshare.setFid(Long.parseLong(fids[i]));
                myshare.setLink("/myshare/check?"+link);
                myshareRepository.addfile(myshare);
            }
        }

        return new SingleResult(StatusCode.OK,"创建成功",1,myshare);
    }




    /*
     * 功能描述 取消分享，支持多个mid
     * @author linxinying
     * @date 2020/3/21 13:03
     * @param mids
     * @return void
     */
    @GetMapping("/delete")
    @ResponseBody
    public void deleteById(@RequestParam("mids") String mids) {
        String[] midstrs=splitIds(mids);
        for(int i=0;i< midstrs.length;i++){
            myshareRepository.deleteById(Long.parseLong(midstrs[i]));
        }
    }



    /*
     * 功能描述 根据用户ID查询所有分享
     * @author linxinying
     * @date 2020/3/21 13:09
     * @param uid
     * @return cn.edu.scau.lxy.netdisk.common.entity.MultiResult
     */
    @GetMapping("/findAll")
    @ResponseBody
    public MultiResult findAll(HttpServletRequest request) {
        long uid=Long.parseLong(request.getParameter("uid"));
        List<Object> list= myshareRepository.findAll(uid);
        return new MultiResult(StatusCode.OK,"查询成功",list.size(),list);
    }


    /*
     * 功能描述 根据链接和提取码提取资源
     * @author linxinying
     * @date 2020/3/21 15:14
     * @param link
     * @param code
     * @return cn.edu.scau.lxy.netdisk.common.entity.MultiResult
     */
    @GetMapping("/check")
    @ResponseBody
    public MultiResult check(@RequestParam("link") String link,@RequestParam("code") String code) throws UnsupportedEncodingException {
        List<Myshare> list=myshareRepository.findByLink(link);

        //提取码不正确
        if(!list.get(0).getCode().equals(code)){
            return new MultiResult(StatusCode.ACCESSERROR,"提取码不正确",0,null);
        }

        List<Object> all=new ArrayList<>();
        List<Folder> fflist=new ArrayList<>();
        List<File> flist=new ArrayList<>();

        String paramstr= URLDecoder.decode(link.substring(link.lastIndexOf("?")+1),"UTF-8");
        String[] strs=paramstr.split("&");
        String[] ffids;
        String[] fids;
        for(int i=0;i<strs.length;i++){
            System.out.println(strs[i]);
            if(strs[i].contains("ffid")){
                ffids=splitIds(strs[i].substring(strs[i].lastIndexOf("=")+1));
                //System.out.println(ffids[0]);
                for(int j=0;j<ffids.length;j++){
                    fflist.add(folderRepository.findById(Long.parseLong(ffids[j])));
                }
            }else{
                fids=splitIds(strs[i].substring(strs[i].lastIndexOf("=")+1));
                //System.out.println(fids[0]);
                for(int j=0;j<fids.length;j++){
                    flist.add(fileRepository.findById(Long.parseLong(fids[j])));
                }
            }
        }
        all.addAll(fflist);
        all.addAll(flist);

        return new MultiResult(StatusCode.OK,"提取成功",all.size(),all);
    }



    /*
     * 功能描述 随机生成4位字母数字组成的提取码
     * @author linxinying
     * @date 2020/3/19 21:55
     * @param 无
     * @return java.lang.String
     */
    public String getCode(){
        Random random=new Random();
        StringBuffer code=new StringBuffer();
        String charStr="tkc0nuz1q2xl3wh4go5d7j8pavbefimr9sy6";
        int length=4;
        for(int i=0;i<length;i++){
            int index=random.nextInt(length);
            code.append(charStr.charAt(index));
        }
        return code.toString();
    }



    /*
     * 功能描述 多文件id分割
     * @author linxinying
     * @date 2020/3/19 21:54
     * @param ids ID数组
     * @return java.lang.String[]
     */
    public String[] splitIds(String ids){
        String[] strs=ids.split(",");
        return strs;
    }



    /*@PostMapping("/getShare")
    public void getShare(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //获取文件的绝对路径
        String fpath=request.getParameter("path")+request.getParameter("name");
        System.out.println(fpath);

        //获取文件名
        String fname=request.getParameter("name");

        //设置content-disposition响应头控制浏览器以下载文件的形式打开文件
        response.reset();
        response.setContentType("application/force-download");
        response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(fname,"UTF-8"));

        //获取文件输入流
        InputStream in=new FileInputStream(fpath);

        //将缓冲区的数据传输到客户端浏览器
        int len=0;
        byte[] buffer=new byte[1024];
        OutputStream out=response.getOutputStream();
        while((len=in.read(buffer))>0){
            out.write(buffer,0,len);
        }
        out.flush();
        in.close();
        out.close();
    }*/




    @GetMapping("/updateTimesOfBrowse/{id}")
    public int updateTimesOfBrowse(@PathVariable("id") long id) {
        //在原来的基础上加“1”
        long cnt=myshareRepository.findById(id).getTimesOfBrowse();
        cnt+=1;
        return myshareRepository.updateTimesOfBrowse(id,cnt);
    }

    @GetMapping("/updateTimesOfSave/{id}")
    public int updateTimesOfSave(@PathVariable("id") long id) {
        //在原来的基础上加“1”
        long cnt=myshareRepository.findById(id).getTimesOfSave();
        cnt+=1;
        return myshareRepository.updateTimesOfSave(id,cnt);
    }

    @GetMapping("/updateTimesOfDownload/{id}")
    public int updateTimesOfDownload(@PathVariable("id") long id) {
        //在原来的基础上加“1”
        long cnt=myshareRepository.findById(id).getTimesOfDownload();
        cnt+=1;
        return myshareRepository.updateTimesOfDownload(id,cnt);
    }



}
