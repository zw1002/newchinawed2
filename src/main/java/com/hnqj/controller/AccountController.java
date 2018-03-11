package com.hnqj.controller;

import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Account;
import com.hnqj.model.Sysusermgr;
import com.hnqj.model.Userinfo;
import com.hnqj.services.AccountServices;
import com.hnqj.services.AccountServices;
import com.hnqj.services.SysusermgrServices;
import com.hnqj.services.UserinfoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.hnqj.core.EncodeUtil.encodeMD5;
import static com.hnqj.core.ResultUtils.toJson;

/**
 * 张威 2017/11
 */
@Controller
@RequestMapping("/account")
public class AccountController extends  BaseController{
    @Autowired
    AccountServices accountService;
    @Autowired
    UserinfoServices userinfoServices;
    @Autowired
    SysusermgrServices sysusermgrServices;

    /**
     * 跳转到账号管理页面
     * @return
     */
    @RequestMapping("/toAccountList.do")
    public String toAccountList(){
        return "sys/accountlist";
    }
    //初始化数据
    @RequestMapping("/getAccountList.do")
    public String getAccountList(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("getAccountList");
        int offset = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int limit = request.getParameter("limit") == null ? 0 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",offset);
        pageData.put("limit",limit);
        List<Account> list=accountService.getAllAccounts(pageData);
        List<Account> listCount=accountService.selectAccountList();
        tablereturn.setTotal(listCount.size());
        List<Map<String, String>> hashMaps=new ArrayList<>();
        for(Account account:list){
            Map<String, String> map = new HashMap<>();
            if(account.getUsertype() == 0){//会员用户
                Userinfo user=userinfoServices.getUserInfoByUid(account.getUserid());
                map.put("fname",user.getFristname());
                map.put("usertype","会员");
            }else{//后台用户
                Sysusermgr sysUserByUid=sysusermgrServices.getSysUserByUid(account.getUserid());
                map.put("fname",sysUserByUid.getFristname());
                map.put("usertype","后台");
            }
            map.put("fid",account.getUid());
            map.put("account",account.getAccount());
            hashMaps.add(map);
        }
        tablereturn.setRows(hashMaps);
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }

    /**
     * 删除账户
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/delAccountIds.do")
    @Transactional(rollbackFor=Exception.class)
    public String delAccountIds(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.debug("delAccountIds");
        try{
            String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
            String[] idStrs=uid.split(",");
            accountService.deleteAccountByFid(idStrs[0]);
            accountService.deleteAccountByFid("111119BB26FDE49DCA104A38214B522E2");
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("delAccountIds e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 重置密码
     */
    @RequestMapping("/resetPasswd.do")
    public String resetPasswd(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("resetPasswd");
        try{
            String uid = request.getParameter("uid");
            String passwd=encodeMD5("000000");
            PageData pageData = new PageData();
            pageData.put("uid",uid);
            pageData.put("passwd",passwd);
            accountService.resetPasswd(pageData);
            ResultUtils.writeSuccess(response);
        }catch(Exception e){
            logger.error("resetPasswd e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 修改个人密码
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/updatePersonPassword.do")
    public String updatePersonPassword(HttpServletRequest request, HttpServletResponse response) {
        logger.info("updatePersonPassword");
        String newPassword = request.getParameter("newPassword") == null ? "" : request.getParameter("newPassword");
        Account account=accountService.getAccountforUserId(getUser().getUid());
        PageData pageData = new PageData();
        pageData.put("uid",account.getUid());
        pageData.put("passwd",encodeMD5(newPassword));
        try {
            accountService.resetPasswd(pageData);
            ResultUtils.writeSuccess(response);
        }catch(Exception e){
            logger.error("updatePersonPassword e="+e.toString());
            ResultUtils.writeFailed(response);
        }
        return null;
    }

    /**
     * 根据用户ID获取账号信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getDateByUserId.do")
    public String getDateByUserId(HttpServletRequest request, HttpServletResponse response) {
        logger.info("getDateByUserId");
        try{
            Account account=accountService.getAccountforUserId(getUser().getUid());
            ResultUtils.write(response,toJson(account));
        }catch(Exception e){
            logger.error("getDateByUserId e="+e.toString());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
}

