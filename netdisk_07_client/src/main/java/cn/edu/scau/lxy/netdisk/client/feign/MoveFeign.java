package cn.edu.scau.lxy.netdisk.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "file")
public interface MoveFeign {
    @PostMapping("/move/movedemo")
    public void move(@RequestParam("ffids") String ffids, @RequestParam("fids") String fids, @RequestParam("path") String path);
}
