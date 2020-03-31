/*
package cn.edu.scau.lxy.netdisk.client.filter;

import cn.edu.scau.lxy.netdisk.client.entity.User;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

*/
/*
 * 功能描述: 过滤器（已作废，无法进行鉴权）
 * @author linxinying
 * @date 2020/3/16 20:22
 *//*

@Component
@WebFilter(urlPatterns = {"/redirect/**"},filterName = "userFilter")
@ServletComponentScan
public class UserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //System.out.println("过滤器被初始化了");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("user");
        if(user==null){
            response.sendRedirect("/login");
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {
        //System.out.println("过滤器被销毁了");
    }
}
*/
