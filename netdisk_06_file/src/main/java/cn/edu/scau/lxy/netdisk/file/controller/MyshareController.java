package cn.edu.scau.lxy.netdisk.file.controller;

import cn.edu.scau.lxy.netdisk.file.repository.FileRepository;
import cn.edu.scau.lxy.netdisk.file.repository.MyshareRepository;
import cn.edu.scau.lxy.netdisk.file.entity.Myshare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/myshare")
public class MyshareController {

    @Autowired
    private MyshareRepository myshareRepository;
    @Autowired
    private FileRepository fileRepository;

    @PostMapping("add")
    public Myshare add(@RequestParam("time") int time,@RequestParam("code") int code,@RequestParam("uid") long uid, @RequestParam(value = "fid",required = false) String fid, @RequestParam(value = "ffid",required = false) String ffid) throws UnsupportedEncodingException {
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
                myshare.setLink("/myshare/check?"+link);
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

        return myshare;
    }


    @GetMapping("delete")
    public void deleteById(@RequestParam("mids") String mids) {
        String[] midstrs=splitIds(mids);
        for(int i=0;i< midstrs.length;i++){
            myshareRepository.deleteById(Long.parseLong(midstrs[i]));
        }
    }

    @GetMapping("findAll/{uid}")
    public List<Myshare> findAll(@PathVariable("uid") long uid) {
        return myshareRepository.findAll(uid);
    }

    @GetMapping("findByLink")
    public List<Myshare> findByLink(@RequestParam("link") String link){
        return myshareRepository.findByLink(link);
    }

    @GetMapping("updateTimesOfBrowse/{id}")
    public int updateTimesOfBrowse(@PathVariable("id") long id) {
        //在原来的基础上加“1”
        long cnt=myshareRepository.findById(id).getTimesOfBrowse();
        cnt+=1;
        return myshareRepository.updateTimesOfBrowse(id,cnt);
    }

    @GetMapping("updateTimesOfSave/{id}")
    public int updateTimesOfSave(@PathVariable("id") long id) {
        //在原来的基础上加“1”
        long cnt=myshareRepository.findById(id).getTimesOfSave();
        cnt+=1;
        return myshareRepository.updateTimesOfSave(id,cnt);
    }

    @GetMapping("updateTimesOfDownload/{id}")
    public int updateTimesOfDownload(@PathVariable("id") long id) {
        //在原来的基础上加“1”
        long cnt=myshareRepository.findById(id).getTimesOfDownload();
        cnt+=1;
        return myshareRepository.updateTimesOfDownload(id,cnt);
    }

    @GetMapping("/count")
    public int count() {
        return myshareRepository.count();
    }

    //随机生成提取码，4位字母数字组成
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

    //多文件fid分割
    public String[] splitIds(String ids){
        String[] strs=ids.split(",");
        return strs;
    }
}
