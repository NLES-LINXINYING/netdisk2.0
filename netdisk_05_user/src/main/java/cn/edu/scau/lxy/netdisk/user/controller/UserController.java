package cn.edu.scau.lxy.netdisk.user.controller;

import cn.edu.scau.lxy.netdisk.common.entity.SingleResult;
import cn.edu.scau.lxy.netdisk.common.entity.StatusCode;
import cn.edu.scau.lxy.netdisk.user.entity.File;
import cn.edu.scau.lxy.netdisk.user.repository.FileRepository;
import cn.edu.scau.lxy.netdisk.user.repository.ParameterRepository;
import cn.edu.scau.lxy.netdisk.user.repository.UserRepository;
import cn.edu.scau.lxy.netdisk.common.util.JwtUtil;
import cn.edu.scau.lxy.netdisk.user.entity.User;
import io.jsonwebtoken.Claims;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParameterRepository parameterRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private JwtUtil jwtUtil;


    /*
     * 功能描述 修改密码时判断用户输入的原密码是否正确
     * @author linxinying
     * @date 2020/3/26 20:30
     * @param name
     * @param password
     * @return java.lang.String
     */
    @GetMapping("/checkPassword1")
    public SingleResult checkPassword1(HttpServletRequest request){
        String token=request.getHeader("Authorization");
        token=token.substring(7);
        String oldPW=request.getParameter("password");

        Claims claims=jwtUtil.parseJWT(token);
        String username=claims.getSubject();
        User user=userRepository.findByName(username);

        Boolean flag=encoder.matches(oldPW,user.getPassword());

        return new SingleResult(StatusCode.OK,"查询成功",1, flag);
    }

    /*
     * 功能描述 修改登陆密码
     * @author linxinying
     * @date 2020/3/26 20:42
     * @param name
     * @param password
     * @return java.lang.String
     */
    @PutMapping("/updatePassword1")
    public SingleResult updatePassword1(HttpServletRequest request){
        String token=request.getHeader("Authorization");
        token=token.substring(7);
        String newPw=request.getParameter("password");

        Claims claims=jwtUtil.parseJWT(token);
        String username=claims.getSubject();

        int result=userRepository.updatePassword1(username,encoder.encode(newPw));
        return new SingleResult(StatusCode.OK,"",1,result);
    }




    @GetMapping("/updatePassword2/{name}/{password2}")
    public String updatePassword2(@PathVariable("name") String name,@PathVariable("password2") String password2){
        int result=userRepository.updatePassword2(name,password2);
        //System.out.println("修改成功！！！");
        return userRepository.findByName(name).toString();
    }


    /*
     * 功能描述 根据token和新手机号修改手机号
     * @author linxinying
     * @date 2020/3/26 21:41
     * @param id
     * @param phone
     * @return int
     */
    @PutMapping("/updatePhone")
    public SingleResult updatePhone(HttpServletRequest request){
        String token=request.getHeader("Authorization");
        token=token.substring(7);
        String phone=request.getParameter("phone");

        Claims claims=jwtUtil.parseJWT(token);
        long uid=Long.parseLong(claims.getId());

        int result= userRepository.updatePhone(uid,phone);
        return new SingleResult(StatusCode.OK,"",1,result);
    }


    /*
     * 功能描述 根据token和新邮箱号修改邮箱号
     * @author linxinying
     * @date 2020/3/26 22:21
     * @param id
     * @param email
     * @return int
     */
    @PutMapping("/updateEmail")
    public SingleResult updateEmail(HttpServletRequest request){
        String token=request.getHeader("Authorization");
        token=token.substring(7);
        String email=request.getParameter("email");

        Claims claims=jwtUtil.parseJWT(token);
        long uid=Long.parseLong(claims.getId());

        int result= userRepository.updateEmail(uid,email);
        return new SingleResult(StatusCode.OK,"",1,result);
    }



    /*
     * 功能描述 登录
     * @author linxinying
     * @date 2020/3/17 19:11
     * @param name 用户姓名
     * @param password 密码
     * @return cn.edu.scau.lxy.netdisk.common.entity.SingleResult 返回包含role和token
     */
    @GetMapping("/login")
    public SingleResult login(@RequestParam String name, @RequestParam String password){
        //先根据用户名查询对象
        User user=userRepository.findByName(name);

        if(user==null||!encoder.matches(password,user.getPassword())){
            return new SingleResult(StatusCode.LOGINERROR,"登录失败",1,null);
        }

        //采用JWT生成令牌
        String token="";
        Map<String,Object> map=new HashMap<>();

        if(user.getType()==1){
            token=jwtUtil.createJWT(String.valueOf(user.getId()),user.getName(),"user");
            map.put("token",token);
            map.put("role","user");
        }else{
            token=jwtUtil.createJWT(String.valueOf(user.getId()),user.getName(),"admin");
            map.put("token",token);
            map.put("role","admin");
        }
        map.put("uid",user.getId());
        map.put("uname",user.getName());
        return new SingleResult(StatusCode.OK,"登录成功",1, map);
    }


    /*
     * 功能描述 退出登录 todo
     * @author linxinying
     * @date 2020/3/18 9:12
     * @param name
     * @return cn.edu.scau.lxy.netdisk.common.entity.SingleResult
     */
    @GetMapping("/logout")
    public SingleResult logout(@RequestParam String name){
        return null;
    }


    /* //todo
     * 功能描述 删除用户
     * @author linxinying
     * @date 2020/3/15 21:40
     * @param name
     * @return int
     */
    @DeleteMapping("/deleteByName/{name}")
    public SingleResult deleteByName(@PathVariable("name") String name, HttpServletRequest request){
        String header=request.getHeader("Authorization");
        if(header==null||header.equals("")){
            return new SingleResult(StatusCode.ACCESSERROR,"权限不足1",1,null);
        }
        if(!header.startsWith("Bearer ")){
            return new SingleResult(StatusCode.ACCESSERROR,"权限不足2",1,null);
        }
        //得到token
        String token=header.substring(7);
        try{
            Claims claims=jwtUtil.parseJWT(token);
            String roles= (String) claims.get("roles");
            if(roles==null||!roles.equals("admin")){
                return new SingleResult(StatusCode.ACCESSERROR,"权限不足3",1,null);
            }
        }catch (Exception e){
            return new SingleResult(StatusCode.ACCESSERROR,"权限不足4",1,null);
        }
        userRepository.deleteByName(name);
        return new SingleResult(StatusCode.OK,"删除成功",1,null);
    }


    /*
     * 功能描述：根据token获取用户姓名
     * @author linxinying
     * @date 2020/3/17 18:48
     * @param request
     * @return cn.edu.scau.lxy.netdisk.common.entity.SingleResult
     */
    @GetMapping("/getUserName")
    public SingleResult getUserName(HttpServletRequest request){
        String token=request.getHeader("Authorization");
        token=token.substring(7);

        Claims claims=jwtUtil.parseJWT(token);
        String username=claims.getSubject();
        User user=userRepository.findByName(username);
        return new SingleResult(StatusCode.OK,"查询成功",1, username);
    }



    /*
     * 功能描述：根据token获取用户信息
     * @author linxinying
     * @date 2020/3/17 18:48
     * @param request
     * @return cn.edu.scau.lxy.netdisk.common.entity.SingleResult
     */
    @GetMapping("/getUserInfo")
    public SingleResult getUserInfo(HttpServletRequest request){
        String token=request.getHeader("Authorization");
        token=token.substring(7);

        Claims claims=jwtUtil.parseJWT(token);
        String username=claims.getSubject();
        User user=userRepository.findByName(username);
        return new SingleResult(StatusCode.OK,"查询成功",1, user);
    }


    /*
     * 功能描述 普通用户注册（会自动生成目录）
     * @author linxinying
     * @date 2020/3/17 22:02
     * @param user
     * @return cn.edu.scau.lxy.netdisk.common.entity.SingleResult
     */
    @PostMapping("/register")
    public SingleResult signUp(@RequestBody User user){
        User user1=userRepository.findByName(user.getName());
        if(user1==null){
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.add(user);

            String mypath=parameterRepository.findByName("syspath").getValue()+user.getName()+"/";
            java.io.File file=new java.io.File(mypath);
            java.io.File file1=new java.io.File(mypath+"回收站/");
            java.io.File file2=new java.io.File(mypath+"私密空间/");

            //判断目标文件父目录是否存在，不存在则创建
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }

            //创建用户自己的目录
            file.mkdir();
            file1.mkdir();
            file2.mkdir();

            return new SingleResult(StatusCode.OK,"注册成功",1,null);
        }
        return new SingleResult(StatusCode.ERROR,"注册失败，该用户已存在",0,null);
    }



    /*
     * 功能描述 更新用户当前已用空间容量
     * @author linxinying
     * @date 2020/3/18 10:17
     * @param uid
     * @return cn.edu.scau.lxy.netdisk.common.entity.SingleResult
     */
    @PutMapping("/updateUsedMemory")
    public SingleResult updateUsedMemory(@RequestParam long uid){
        long total=0;
        List<File> list=fileRepository.findByUid(uid);
        for(int i=0;i<list.size();i++){
            total+=list.get(i).getSize();
        }
        userRepository.updateUsedMemory(uid,total);
        return new SingleResult(StatusCode.OK,"更新成功",1,total);
    }


    /*
     * 功能描述 修改用户头像
     * @author linxinying
     * @date 2020/3/28 10:22
     * @param file
     * @param request
     * @return cn.edu.scau.lxy.netdisk.common.entity.SingleResult
     */
    @PostMapping("/updatePicture")
    public SingleResult updatePicture(@RequestParam MultipartFile file, HttpServletRequest request){
        String token=request.getHeader("Authorization");
        token=token.substring(7);

        Claims claims=jwtUtil.parseJWT(token);
        long uid=Long.parseLong(claims.getId());
        String uname=claims.getSubject();


        //头像保存路径
        String uploadDir = "D:/upload/"+uname+"/";
        java.io.File pfile=new java.io.File(uploadDir);

        //判断存储头像的路径是否存在
        if(!pfile.exists()){
            //不存在则创建
            pfile.mkdirs();
        }

        String fname=file.getOriginalFilename();
        String suffix=fname.substring(fname.lastIndexOf('.'));
        //创建文件
        java.io.File cfile=new java.io.File(pfile,uname+suffix);

        try {
            //使用此方法必须要绝对路径且文件夹必须存在，否则报错
            file.transferTo(cfile);

            userRepository.updatePicture(uid,uploadDir+uname+suffix);

            return new SingleResult(StatusCode.OK,"上传头像成功",1,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new SingleResult(StatusCode.ERROR,"上传头像失败",0,null);
        }
    }



    /*
     * 功能描述 请求用户头像,默认跳过token认证，因为img,无法携带请求头
     * @author linxinying
     * @date 2020/3/28 12:59
     * @param request
     * @param response
     * @return void
     */
    @GetMapping("/getPicture")
    public void getPicture(HttpServletRequest request, HttpServletResponse response)  {
        long uid=Long.parseLong(request.getParameter("uid"));

        String imgPath=userRepository.findById(uid).getPicture();

        InputStream is=null;
        OutputStream os=null;

        try{
            is=new FileInputStream(imgPath);
            os=response.getOutputStream();

            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = is.read(buffer)) != -1) {
                os.write(buffer, 0, count);
                os.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
                os.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
