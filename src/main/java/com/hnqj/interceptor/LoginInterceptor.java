package com.hnqj.interceptor;

import com.hnqj.model.Sysusermgr;
import com.hnqj.model.Userinfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/7/31.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        Sysusermgr userName = (Sysusermgr) session.getAttribute("user");
        if (userName == null||requestURI.endsWith(".jsp")) {
            //不符合条件的，跳转到登录界面
            String CONTENT_TYPE = "text/html; charset=GBK";
            response.setContentType(CONTENT_TYPE);
            java.io.PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<script>");
            out.println("window.open ('"+request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+request.getContextPath()+"/login.do','_top')");
            out.println("</script>");
            out.println("</html>");
            out.close();
            return false;
        }
        return true;
    }
}
