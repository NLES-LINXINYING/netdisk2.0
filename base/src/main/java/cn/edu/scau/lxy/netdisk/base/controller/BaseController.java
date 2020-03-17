package cn.edu.scau.lxy.netdisk.base.controller;

import cn.edu.scau.lxy.netdisk.base.pojo.Role;
import cn.edu.scau.lxy.netdisk.base.service.RoleService;
import cn.edu.scau.lxy.netdisk.common.entity.SingleResult;
import cn.edu.scau.lxy.netdisk.common.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/role")
public class BaseController {
    @Autowired
    private RoleService roleService;

    @PostMapping("")
    public SingleResult add(@RequestBody Role role){
        roleService.save(role);
        return new SingleResult(true, StatusCode.OK,"添加成功",null);
    }

    @DeleteMapping("/{roleId}")
    public SingleResult deleteById(@PathVariable String roleId){
        roleService.deleteById(roleId);
        return new SingleResult(true, StatusCode.OK,"删除成功",null);
    }

    @GetMapping("")
    public SingleResult findAll(){
        return new SingleResult(true, StatusCode.OK,"查询全部成功",roleService.findAll());
    }

    @GetMapping("/{roleId}")
    public SingleResult findById(@PathVariable String roleId){
        return new SingleResult(true, StatusCode.OK,"查询ID成功",roleService.findById(roleId));
    }

    @PutMapping("/{roleId}")
    public SingleResult update(@PathVariable String roleId, @RequestBody Role role){
        role.setId(roleId);
        roleService.update(role);
        return new SingleResult(true, StatusCode.OK,"修改成功",null);
    }
}
