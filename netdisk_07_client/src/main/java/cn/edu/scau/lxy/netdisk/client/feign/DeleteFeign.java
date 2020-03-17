package cn.edu.scau.lxy.netdisk.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "file")
public interface DeleteFeign {
    @PostMapping("/delete/deletedemo")
    public void delete(@RequestParam("ffids") String ffids, @RequestParam("fids") String fids, @RequestParam("uid") long uid,@RequestParam("uname") String uname);
}
