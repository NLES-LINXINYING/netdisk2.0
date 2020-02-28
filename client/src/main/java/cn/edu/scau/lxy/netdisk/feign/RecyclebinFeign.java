package cn.edu.scau.lxy.netdisk.feign;

import cn.edu.scau.lxy.netdisk.entity.Recyclebin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.List;

@FeignClient(value = "file")
public interface RecyclebinFeign {

    @GetMapping("/recyclebin/findAll")
    public List<Recyclebin> findAll(@RequestParam("uid") long uid) ;

    @PostMapping("/recyclebin/delete")
    public void delete(@RequestParam("rids") String rids);

    @PostMapping("/recyclebin/revert")
    public void revert(@RequestParam("rids") String rids);
}
