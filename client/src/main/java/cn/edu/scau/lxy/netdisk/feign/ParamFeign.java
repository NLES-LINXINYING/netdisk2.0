package cn.edu.scau.lxy.netdisk.feign;

import cn.edu.scau.lxy.netdisk.entity.File;
import cn.edu.scau.lxy.netdisk.entity.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "admin")
public interface ParamFeign {
    @GetMapping("/admin/param")
    public Parameter findByName(@RequestParam("name") String name);
}
