package com.hnqj.controller;

import com.hnqj.model.Sysusermgr;
import com.hnqj.model.Userinfo;
import com.hnqj.services.UserinfoServices;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 张威 2017/11
 */
public class BaseController {
    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    UserinfoServices userinfoServices;
    /**
     * 获取Session
     *
     * @return
     */
    public HttpSession getSession() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
        return request.getSession();
    }
    /**
     * 获取用户
     *
     * @return
     */
    public Sysusermgr getUser() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
        Sysusermgr user = (Sysusermgr) request.getSession().getAttribute("user");
        return user;
    }
    /**
     * 获取当前系统时间
     *
     * @return
     */
    public String getCurrentTime() {

        Date dNow = new Date( );
        SimpleDateFormat ft =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strCurrent=ft.format(dNow);
        return strCurrent;
    }
}
