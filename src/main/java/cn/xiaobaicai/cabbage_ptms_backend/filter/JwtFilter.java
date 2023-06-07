package cn.xiaobaicai.cabbage_ptms_backend.filter;
 
import cn.xiaobaicai.cabbage_ptms_backend.util.JwtUtil;
import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
 
/**
 * JWT过滤器，拦截除login外所有的请求
 * @author hetongxue
 */
@Slf4j
@WebFilter(filterName = "JwtFilter",urlPatterns = {"/user/*","/student/*","/admin/*","/teacher/*","/file/*"})
public class JwtFilter implements Filter
 {
     /**
      * 排除的URL，一般为登录的URl
      */
     private static String EXCLUDE="/user/login";

     /**
      * 排除预检请求
      */
     private static String OPTIONS="OPTIONS";

     @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("JwtFilter init......");
    }
 
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
 
        response.setCharacterEncoding("UTF-8");
        //获取 header里的token
        final String token = request.getHeader("authorization");
        //获取 每次请求的路径
        String path=request.getServletPath();
        if (OPTIONS.equals(request.getMethod())||EXCLUDE.equals(path)) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
        }
        //登录直接放行
        // Except OPTIONS, other request should be checked by JWT
        else {
 
            if (token == null) {
                response.getWriter().write("没有token！");
                return;
            }
 
            Map<String, Claim> userData = JwtUtil.verifyToken(token);
            if (userData == null) {
                response.getWriter().write("token不合法！");
                return;
            }
            Integer id = userData.get("id").asInt();
            String userName = userData.get("userName").asString();
            String userAccount= userData.get("userAccount").asString();
            //拦截器 拿到用户信息，放到request中
            request.getSession().setAttribute("id", id);
            request.getSession().setAttribute("userName", userName);
            request.getSession().setAttribute("userAccount", userAccount);
            chain.doFilter(req, res);
        }
    }
 
    @Override
    public void destroy() {
        System.out.println("JwtFilter destroy......");
    }
}