package cn.edu.scau.lxy.netdisk.feign;

import cn.edu.scau.lxy.netdisk.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "user")
public interface UserFeign {

    @GetMapping("/user/add/{name}/{password}")
    public int add(@PathVariable("name") String name,@PathVariable("password") String password);

    @GetMapping("/user/deleteByName/{name}")
    public int deleteByName(@PathVariable("name") String name);

    @GetMapping("/user/findByName")
    public User findByName(@RequestParam("name") String name);

    @GetMapping("/user/findByNameAndPassword/{name}/{password}")
    public User findByNameAndPassword(@PathVariable("name") String name,@PathVariable("password") String password);

    @GetMapping("/user/findAll/{index}/{limit}")
    public List<User> findAll(@PathVariable("index") int index, @PathVariable("limit") int limit);

    @GetMapping("/user/updatePassword1/{name}/{password}")
    public String updatePassword1(@PathVariable("name") String name,@PathVariable("password") String password);

    @GetMapping("/user/updatePassword2/{name}/{password2}")
    public String updatePassword2(@PathVariable("name") String name,@PathVariable("password2") String password2);

    @GetMapping("/user/count")
    public int count();

    @GetMapping("/user/updateUsedMemory")
    public int updateUsedMemory(@RequestParam("id") long id, @RequestParam("usedM") long usedM);

    @GetMapping("/user/updatePhone")
    public int updatePhone(@RequestParam("id") long id,@RequestParam("phone") String phone);

    @GetMapping("/user/updateEmail")
    public int updateEmail(@RequestParam("id") long id,@RequestParam("email") String email);
}
