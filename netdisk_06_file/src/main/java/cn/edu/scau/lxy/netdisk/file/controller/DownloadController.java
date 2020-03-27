package cn.edu.scau.lxy.netdisk.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author linxinying
 * @description 文件下载
 * @date 2020/3/20 8:17
 */
@Controller
@RequestMapping("/download")
public class DownloadController {


    /*
     * 功能描述 单文件下载
     * @author linxinying
     * @date 2020/3/20 8:18
     * @param request
     * @param response
     * @return void
     */
    @GetMapping("/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //获取文件的绝对路径
        String fpath=request.getParameter("path")+request.getParameter("name");
        System.out.println(fpath);

        //获取文件名
        String fname=request.getParameter("name");

        //设置content-disposition响应头控制浏览器以下载文件的形式打开文件
        response.reset();
        response.setContentType("application/force-download");
        response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(fname,"UTF-8"));

        //获取文件输入流
        InputStream in=new FileInputStream(fpath);

        //将缓冲区的数据传输到客户端浏览器
        int len=0;
        byte[] buffer=new byte[1024];
        OutputStream out=response.getOutputStream();
        while((len=in.read(buffer))>0){
            out.write(buffer,0,len);
        }
        out.flush();
        in.close();
        out.close();
    }
}