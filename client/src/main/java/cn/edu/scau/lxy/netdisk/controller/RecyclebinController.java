package cn.edu.scau.lxy.netdisk.controller;

import cn.edu.scau.lxy.netdisk.entityVO.MyshareVO;
import cn.edu.scau.lxy.netdisk.entityVO.RecyclebinVO;
import cn.edu.scau.lxy.netdisk.feign.DeleteFeign;
import cn.edu.scau.lxy.netdisk.feign.MyshareFeign;
import cn.edu.scau.lxy.netdisk.feign.RecyclebinFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recyclebin")
public class RecyclebinController {

    @Autowired
    private RecyclebinFeign recyclebinFeign;
    @Autowired
    private DeleteFeign deleteFeign;

    @GetMapping("/findAll")
    @ResponseBody
    public RecyclebinVO findAll(@RequestParam("uid") long uid) {
        return new RecyclebinVO(0,"",100,recyclebinFeign.findAll(uid));
    }

    @PostMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam("rids") String rids){
        System.out.println(rids);
        recyclebinFeign.delete(rids);
    }

    @PostMapping("/revert")
    @ResponseBody
    public void revert(@RequestParam("rids") String rids){
        System.out.println(rids);
        recyclebinFeign.revert(rids);
    }
}
