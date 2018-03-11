package com.hnqj.controller;

import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Sysusermgr;
import com.hnqj.model.Userinfo;
import com.hnqj.services.AccountServices;
import com.hnqj.services.RolesuserServices;
import com.hnqj.services.SysusermgrServices;
import com.hnqj.services.UserinfoServices;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

import static com.hnqj.core.EncodeUtil.encodeMD5;
import static com.hnqj.core.ResultUtils.toJson;

/**
 * 张威 2017/11
 * 后台用户管理
 */
@Controller
@RequestMapping("/sysuser")
public class SysUserController extends  BaseController{
    @Autowired
    SysusermgrServices sysusermgrServices;

    /**
     * 跳转到后台用户管理页面
     * @return
     */
    @RequestMapping("/toSysUserList.do")
    public String toSysUserList(){
        return "sys/sysuserlist";
    }

    /**
     *初始化后台数据
     */
    @RequestMapping("/getSysUserList.do")
    public String getSysUserList(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("getSysUserList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 50 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        List<Sysusermgr> list=sysusermgrServices.getAllSysUserInfo(pageData);
        List<Sysusermgr> listCount=sysusermgrServices.selectSysUserList();
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(list);
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }
    /**
     * 角色关联用户时获取所有后台用户
     */
    @RequestMapping("/getSysUser.do")
    public String getSysUser(HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.info("getSysUser");
        List<Sysusermgr> list=sysusermgrServices.selectSysUserList();
        ResultUtils.write(response,toJson(list));
        return null;
    }
    /**
     * 新增
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addSysUser.do")
    public String addSysUser(HttpServletRequest request, HttpServletResponse response){
        logger.info("addSysUser");
        String fristname = request.getParameter("fristname") == null ? "" : request.getParameter("fristname");
        String telephone=request.getParameter("telephone") == null ? "" : request.getParameter("telephone");
        String idcard=request.getParameter("idcard") == null ? "" : request.getParameter("idcard");
        String email=request.getParameter("email") == null ? "" : request.getParameter("email");
        String sex=request.getParameter("sex") == null ? "" : request.getParameter("sex");
        String extrainfo=request.getParameter("extrainfo") == null ? "" : request.getParameter("extrainfo");
        //向系统用户表插入数据
        PageData userPageData = new PageData();
        String uuid= UUID.randomUUID().toString();
        userPageData.put("uid",uuid);
        userPageData.put("fristname",fristname);
        userPageData.put("telephone",telephone);
        userPageData.put("idcard",idcard);
        userPageData.put("email",email);
        userPageData.put("sex",sex);
        userPageData.put("statu",1);
        userPageData.put("extrainfo",extrainfo);
        //向帐号表插入数据
        PageData accountPageData = new PageData();
        accountPageData.put("account",getChineseToPinYin("fristname"));//后台用户账号默认用户名全拼
        accountPageData.put("passwd",encodeMD5("000000"));//后台用户密码默认000000
        accountPageData.put("uid",UUID.randomUUID().toString());
        accountPageData.put("userid",uuid);
        accountPageData.put("state",1);
        accountPageData.put("usemobile",1);
        accountPageData.put("usertype",1);//关联用户类型 0会员 1后台用户
        try{
            sysusermgrServices.addSysUser(userPageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("addSysUser e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 后台用户信息修改
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/updateSysUser.do")
    public String updateSysUser(HttpServletRequest request, HttpServletResponse response){
        logger.debug("updateSysUser");
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        String fristname = request.getParameter("fristname") == null ? "" : request.getParameter("fristname");
        String telephone=request.getParameter("telephone") == null ? "" : request.getParameter("telephone");
        String idcard=request.getParameter("idcard") == null ? "" : request.getParameter("idcard");
        String email=request.getParameter("email") == null ? "" : request.getParameter("email");
        String sex=request.getParameter("sex") == null ? "" : request.getParameter("sex");
        String extrainfo=request.getParameter("extrainfo") == null ? "" : request.getParameter("extrainfo");
        //修改后台信息
        PageData userPageData = new PageData();
        userPageData.put("uid",uid);
        userPageData.put("fristname",fristname);
        userPageData.put("telephone",telephone);
        userPageData.put("idcard",idcard);
        userPageData.put("email",email);
        userPageData.put("sex",sex);
        userPageData.put("statu",1);
        userPageData.put("extrainfo",extrainfo);
        try{
            sysusermgrServices.updateSysUser(userPageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("updateSysUser e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 删除
     */
    @RequestMapping("/deleteSysUserByUid.do")
    public void deleteSysUserByUid(HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getParameter("ids") == null ? "" : request.getParameter("ids");
        try{
            sysusermgrServices.delSysusermgrByFid(uid);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("deleteSysUserByUid e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
    }

    /**
     * 通过后台用户ID获取数据
     */
    @RequestMapping("/getSysUserByUid.do")
    public void getSysUserByUid(HttpServletRequest request, HttpServletResponse response){
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        Sysusermgr sysUserByUid=sysusermgrServices.getSysUserByUid(uid);
        ResultUtils.write(response,toJson(sysUserByUid));
    }
    /**
     * 汉字转换拼音
     */
    public static String getChineseToPinYin(String str) {
        // ==== 全拼音 ====
        StringBuffer full_pinyin = new StringBuffer();
        try {
            if (StringUtils.isNotEmpty(str) && StringUtils.isNotBlank(str)) {// 拆分字符串中的每个【文字】
                char[] charArray = str.toCharArray();
                for (char c : charArray) {
                    String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
                    if (pinyins != null && pinyins.length > 0) {
						/*
						 *
						 * 由于当【文字】为多音字时返回的数组元素为多个，如"间",既可读为jian1,又可以读为jian4,
						 * 而我们在匹配搜索时，只会用到jian作匹配，那么这里直接舍去音调。
						 */
                        String pinyinStr = pinyins[0];
                        pinyinStr = pinyinStr.replaceAll("\\d*", "");
                        full_pinyin.append(pinyinStr);
                    }
                }
            }
            return full_pinyin.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
