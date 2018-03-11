package com.hnqj.controller;

import com.alibaba.fastjson.JSONObject;
import com.hnqj.core.MenuTree;
import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.model.*;
import com.hnqj.services.AccountServices;
import com.hnqj.services.RolesServices;
import com.hnqj.services.SysusermgrServices;
import com.hnqj.services.UserinfoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.hnqj.core.EncodeUtil.encodeMD5;

@Controller
public class loginController extends BaseController{
    @Autowired
    UserinfoServices userinfoServices;
    @Autowired
    SysusermgrServices sysusermgrServices;
    @Autowired
    AccountServices accountServices;
    @Autowired
    RolesServices rolesServices;

    @RequestMapping(value = "login")
    public String login(){
      return  "login";
    }
    @RequestMapping(value = "demo")
    public String demo(){
        return  "demo";
    }
    /**
     * 用户登录,
     * 使用POST传的JSON格式登录,手机页面可以同样调用,注意查看ajax使用方法;
     * @param requestJson
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/dologin.do")
    @ResponseBody
    public String dologin(@RequestBody JSONObject requestJson, HttpServletRequest request, HttpServletResponse response) {
        String account=requestJson.containsKey("account")==true?requestJson.getString("account"):"";
        String passd=requestJson.containsKey("password")==true?requestJson.getString("password"):"";
        String password=encodeMD5(passd);
        PageData pageData = new PageData();
        pageData.put("account",account);
        pageData.put("passwd",password);
        Sysusermgr user=sysusermgrServices.getUser(pageData);
        Map<String, String> map = new HashMap<>();
        if (user!=null) {
            /*
            tokenService.deleteByUserId(user.getFid());
            Token token=new Token();
            PageData tokenPageData = new PageData();
            String fid = UUID.randomUUID().toString();
            tokenPageData.put("fid",fid);
            tokenPageData.put("user_id",user.getFid());
            tokenPageData.put("create_date",new Date());
            tokenPageData.put("invalid_date",new Date(System.currentTimeMillis()+30*60*1000));
            tokenService.addToken(tokenPageData);
            */
            this.getSession().setAttribute("user", user);
            map.put("state", "true");
            map.put("msg", "登录成功");
            //map.put("token", token.getFid());
            map.put("UserName",user.getFristname());
            map.put("UserId",user.getUid());
            map.put("MobilePhone",user.getTelephone());
            ResultUtils.write(response,map);
        } else {
            map.put("state", "false");
            map.put("msg", "登录失败");
            map.put("token", "");
        }
        ResultUtils.write(response,map);
        return null;
    }
    /**
     * 用户登录成功
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.do")
    @OperAnnotation(moduleName = "用户登录", option = "登录页")
    public String index(HttpServletRequest request, Model model) {
        Sysusermgr user =this.getUser();
        if (user != null) {
            request.getSession().setAttribute("user",user);
            List<MenuTree> menuList = getMenu(user);
            model.addAttribute("menuList", menuList);
            return "frame";
        } else {
            return "login";
        }
    }

    /**
     * 生成权限菜单
     *
     * @param user
     * @return
     */
    private List<MenuTree> getMenu(Sysusermgr user) {
        List<MenuTree> menuList = new ArrayList<MenuTree>();
        try {
            List<Roles> roles = rolesServices.selectRoleByUser(user);
            if (roles.size() > 0) {
                List<Modules> list = new ArrayList<Modules>();
                for (Roles role : roles) {
                    list.addAll(rolesServices.getModuleListByRoleId(role));
                }
                List<Modules> list2 = list;
                for (Modules module : list) {
                    if (module.getPmId().equalsIgnoreCase("0")) {//是否为子节点
                        MenuTree menu = new MenuTree();
                        menu.setFid(module.getUid());
                        menu.setTitle(module.getMdName());
                        menu.setUrl(module.getMdAddress());
                        menu.setIcon(module.getMdImg());
                        for (Modules moduleBean2 : list2) {
                            if (moduleBean2.getPmId().equalsIgnoreCase(module.getUid())) {
                                MenuTree childmenu = new MenuTree();
                                childmenu.setFid(moduleBean2.getUid());
                                childmenu.setTitle(moduleBean2.getMdName());
                                childmenu.setUrl(moduleBean2.getMdAddress());
                                childmenu.setIcon(moduleBean2.getMdImg());
                                if (!menu.getChildrenMenu().contains(childmenu))
                                    menu.addChildrenMenu(childmenu);//排重
                            }
                        }
                        if (!menuList.contains(menu))
                            menuList.add(menu);//排重
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return menuList;
    }

    /**
     * 手机端修改密码
     * @param requestJson
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/updatePassword.do")
    @ResponseBody
    public String updatePassword(@RequestBody JSONObject requestJson, HttpServletRequest request, HttpServletResponse response) {
        String userid = requestJson.containsKey("userid") == true ? requestJson.getString("userid") : "";
        String passd = requestJson.containsKey("password") == true ? requestJson.getString("password") : "";
        Account account=accountServices.getAccountforUserId(userid);
        PageData pageData = new PageData();
        pageData.put("fid",account.getUid());
        pageData.put("passwd",encodeMD5(passd));
        accountServices.updateAccount(pageData);
        return null;
    }
    /**
     * 退出
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/toLogin.do")
    public String toLogin(HttpServletRequest request, Model model) {
        this.getSession().invalidate();
        return "login";
    }

}
