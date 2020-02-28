package cn.edu.scau.lxy.netdisk.feign;

import cn.edu.scau.lxy.netdisk.entity.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "file")
public interface CopyFeign {
    @PostMapping("/copy/copydemo")
    public void copy(@RequestParam("ffids") String ffids,@RequestParam("fids") String fids,@RequestParam("path") String path,@RequestParam("uid") long uid);
}
