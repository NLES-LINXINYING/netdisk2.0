package cn.edu.scau.lxy.netdisk.feign;

import cn.edu.scau.lxy.netdisk.entity.Myshare;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "file")
public interface MyshareFeign {

    @PostMapping("/myshare/add")
    public Myshare add(@RequestParam("time") int time,@RequestParam("code") int code,
                   @RequestParam("uid") long uid, @RequestParam(value = "fid",required = false) String fid,@RequestParam(value = "ffid",required = false) String ffid);

    @GetMapping("/myshare/delete")
    public void deleteById(@RequestParam("mids") String mids);

    @GetMapping("/myshare/findAll/{uid}")
    public List<Myshare> findAll(@PathVariable("uid") long uid);

    @GetMapping("/myshare/findByLink")
    public List<Myshare> findByLink(@RequestParam("link") String link);

    @GetMapping("/myshare/updateTimesOfBrowse/{id}")
    public int updateTimesOfBrowse(@PathVariable("id") long id);

    @GetMapping("/myshare/updateTimesOfSave/{id}")
    public int updateTimesOfSave(@PathVariable("id") long id) ;

    @GetMapping("/myshare/updateTimesOfDownload/{id}")
    public int updateTimesOfDownload(@PathVariable("id") long id);

    @GetMapping("/myshare/count")
    public int count();
}
