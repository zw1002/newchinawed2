package com.hnqj.controller;

import com.hnqj.model.Userinfo;
import com.hnqj.services.AccountServices;
import com.hnqj.services.RolesuserServices;
import com.hnqj.services.UserinfoServices;
import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.hnqj.core.EncodeUtil.encodeMD5;
import static com.hnqj.core.ResultUtils.toJson;

/**
 * 张威 2017/11
 * 会员管理
 */
@Controller
@RequestMapping("/user")
public class UserInfoController extends  BaseController{
    @Autowired
    UserinfoServices userinfoServices;
    @Autowired
    AccountServices accountServices;
    @Autowired
    RolesuserServices rolesuserServices;

    /**
     * 跳转到会员用户管理页面
     * @return
     */
    @RequestMapping("/toUserInfoList.do")
    public String toUserInfoList(){
        return "sys/userinfolist";
    }

    /**
     *初始化会员数据
     */
    @RequestMapping("/getUserInfoList.do")
    public String getUserInfoList(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("getUserInfoList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 50 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        List<Userinfo> list=userinfoServices.getAllUserInfo(pageData);
        List<Userinfo> listCount=userinfoServices.selectUserInfoList();
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(list);
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }
    /**
     * 会员注册
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userInfoRegister.do")
    public String userInfoRegister(HttpServletRequest request, HttpServletResponse response){
        logger.info("userInfoRegister");
        String fristname = request.getParameter("fristname") == null ? "" : request.getParameter("fristname");
        String telephone=request.getParameter("telephone") == null ? "" : request.getParameter("telephone");
        String acount=request.getParameter("account") == null ? "" : request.getParameter("account");
        String password=request.getParameter("password") == null ? "" : request.getParameter("password");
        String iccode=request.getParameter("iccode") == null ? "" : request.getParameter("iccode");
        String usrpicurl=request.getParameter("usrpicurl") == null ? "" : request.getParameter("usrpicurl");
        //向用户表插入数据
        PageData userPageData = new PageData();
        String uuid= UUID.randomUUID().toString();
        userPageData.put("uid",uuid);
        userPageData.put("fristname",fristname);
        userPageData.put("telephone",telephone);
        userPageData.put("iccode",iccode);
        userPageData.put("usrpicurl",usrpicurl);
        //向帐号表插入数据
        PageData accountPageData = new PageData();
        accountPageData.put("account",acount);
        accountPageData.put("passwd",encodeMD5(password));
        accountPageData.put("uid",UUID.randomUUID().toString());
        accountPageData.put("userid",uuid);
        accountPageData.put("state",1);
        accountPageData.put("usemobile",1);
        accountPageData.put("usertype",0);//关联用户类型 0会员 1后台用户
        try{
            accountServices.addAccount(accountPageData);
            userinfoServices.userInfoRegister(userPageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("UserInfoRegister e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 会员信息修改
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/updateUserInfo.do")
    public String updateUserInfo(HttpServletRequest request, HttpServletResponse response){
        logger.debug("updateUserInfo");
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        String fristname = request.getParameter("fristname") == null ? "" : request.getParameter("fristname");
        String telephone=request.getParameter("telephone") == null ? "" : request.getParameter("telephone");
        String iccode=request.getParameter("iccode") == null ? "" : request.getParameter("iccode");
        String usrpicurl=request.getParameter("usrpicurl") == null ? "" : request.getParameter("usrpicurl");
        String idpic=request.getParameter("idpic") == null ? "" : request.getParameter("idpic");
        String idpicback=request.getParameter("idpicback") == null ? "" : request.getParameter("idpicback");
        //修改会员信息
        PageData userPageData = new PageData();
        userPageData.put("uid",uid);
        userPageData.put("fristname",fristname);
        userPageData.put("telephone",telephone);
        userPageData.put("iccode",iccode);
        userPageData.put("usrpicurl",usrpicurl);
        userPageData.put("idpic",idpic);
        userPageData.put("idpicback",idpicback);
        try{
            userinfoServices.updateUserInfo(userPageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("updateUserInfo e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }

    /**
     * 通过会员ID获取数据
     */
    @RequestMapping("/getUserInfoByUid.do")
    public void getUserInfoByUid(HttpServletRequest request, HttpServletResponse response){
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        Userinfo userinfo=userinfoServices.getUserInfoByUid(uid);
        ResultUtils.write(response,toJson(userinfo));
    }
    /**
     * 初始化密码
     */
    @RequestMapping("/resetPasswd.do")
    public String resetPasswd(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("resetPasswd");
        try{
            String uid = request.getParameter("uid");
            String passwd=encodeMD5("000000");//重置密码为000000，和帐号管理页面保持一致
            PageData pageData = new PageData();
            pageData.put("uid",uid);
            pageData.put("passwd",passwd);
            accountServices.resetPasswd(pageData);
            ResultUtils.writeSuccess(response);
        }catch(Exception e){
            logger.error("resetPasswd e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
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
