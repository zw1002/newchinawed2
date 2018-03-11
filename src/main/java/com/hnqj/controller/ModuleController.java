package com.hnqj.controller;

import com.hnqj.services.ModulesServices;
import com.hnqj.core.*;
import com.hnqj.model.*;
import com.hnqj.services.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.hnqj.core.ResultUtils.toJson;

@Controller
@RequestMapping("/module")
public class ModuleController extends BaseController{
    @Autowired
    private ModulesServices modulesServices;

    @RequestMapping("/toMenuList.do")
    public String toMenuList(){
        return "sys/menulist";
    }
    /**
     * 取菜单列表
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequestMapping("/getModuleList.do")
    public String getModuleList(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("getModuleList");
        //偏移量
        int offset = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        // 每页行数
        int limit = request.getParameter("limit") == null ? 0 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",offset);
        pageData.put("limit",limit);
        List<Modules> glist=modulesServices.getAllModule(pageData);
        List<Modules> listCount=modulesServices.getAllModuleCount();
        for (Modules module:glist){
            if(module.getPmId().equalsIgnoreCase("0")){
                module.setPmId("");
            }else{
                module.setPmId(modulesServices.selectModuleByFid(module.getPmId()).getMdName());
            }
        }
        tablereturn.setRows(glist);
        tablereturn.setTotal(listCount.size());
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }
    /**
     * 删除菜单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/delModulefIds.do")
    @Transactional
    public String delModulefIds(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.debug("delModulefIds");
        try{
            String ids = request.getParameter("ids") == null ? "" : request.getParameter("ids");
            String[] idStrs=ids.split(",");
            for (String id:idStrs){
                modulesServices.deleteModuleByFid(id);
            }
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("delModulefIds e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }

    /**
     * 根据id查看菜单详情
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findModuleByFid.do")
    public String findModuleByFid(HttpServletRequest request, HttpServletResponse response){
        logger.info("findModuleByFid");
        String uids = request.getParameter("fid");
        String[] uid=uids.split(",");
        Modules module=modulesServices.selectModuleByFid(uid[0]);
        ResultUtils.write(response,toJson(module));
        return null;
    }
    /**
     * 增加菜单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addModule.do")
    public String addModule(HttpServletRequest request, HttpServletResponse response){
        logger.info("addModule");
        try{
            Modules module=new Modules();
            String fid = UUID.randomUUID().toString();
            String mdName = request.getParameter("mdName") == null ? "" : request.getParameter("mdName");
            String mdCode = request.getParameter("mdCode") == null ? "" : request.getParameter("mdCode");
            String pmId = request.getParameter("pmId") == null ? "" : request.getParameter("pmId");
            String mdAddress = request.getParameter("mdAddress") == null ? "" : request.getParameter("mdAddress");
            String mdImg = request.getParameter("mdImg") == null ? "" : request.getParameter("mdImg");
            String mdMethod = request.getParameter("mdMethod") == null ? "" : request.getParameter("mdMethod");
            String mdIschild = request.getParameter("mdIschild") == null ? "" : request.getParameter("mdIschild");
            String mdOrdernum = request.getParameter("mdOrdernum") == null ? "" : request.getParameter("mdOrdernum");
            PageData pageData = new PageData();
            pageData.put("uid",fid);
            pageData.put("mdAddress",mdAddress);
            pageData.put("mdCode",mdCode);
            pageData.put("mdName",mdName);
            pageData.put("pmId",pmId);
            pageData.put("mdImg",mdImg);
            pageData.put("mdMethod",mdMethod);
            pageData.put("mdIschild",mdIschild);
            pageData.put("mdEnabled",0);
            pageData.put("mdOrdernum",mdOrdernum);
            modulesServices.addModule(pageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("addModule e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 更新菜单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/updateModule.do")
    public String updateModule(HttpServletRequest request, HttpServletResponse response){
        logger.debug("updateModule");
        Modules module=new Modules();
        try{
            String fid = request.getParameter("fid") == null ? "" : request.getParameter("fid");
            String mdName = request.getParameter("mdName") == null ? "" : request.getParameter("mdName");
            String mdCode = request.getParameter("mdCode") == null ? "" : request.getParameter("mdCode");
            String pmId = request.getParameter("pmId") == null ? "" : request.getParameter("pmId");
            String mdAddress = request.getParameter("mdAddress") == null ? "" : request.getParameter("mdAddress");
            String mdImg = request.getParameter("mdImg") == null ? "" : request.getParameter("mdImg");
            String mdMethod = request.getParameter("mdMethod") == null ? "" : request.getParameter("mdMethod");
            String mdIschild = request.getParameter("mdIschild") == null ? "" : request.getParameter("mdIschild");
            String mdOrdernum = request.getParameter("mdOrdernum") == null ? "" : request.getParameter("mdOrdernum");
            PageData pageData = new PageData();
            pageData.put("uid",fid);
            pageData.put("mdAddress",mdAddress);
            pageData.put("mdCode",mdCode);
            pageData.put("mdName",mdName);
            pageData.put("pmId",pmId);
            pageData.put("mdImg",mdImg);
            pageData.put("mdMethod",mdMethod);
            pageData.put("mdIschild",mdIschild);
            pageData.put("mdEnabled",0);
            pageData.put("mdOrdernum",mdOrdernum);
            modulesServices.updateModule(pageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("updateModule e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     *是否包含子菜单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getParentModule.do")
    public String getParentModule(HttpServletRequest request, HttpServletResponse response){
        List moduList  = modulesServices.getParentModule();
        ResultUtils.write(response,toJson(moduList));
        return null;
    }
    /**
     * 权限树
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getModuleTree.do")
    public String getModuleTree(HttpServletRequest request, HttpServletResponse response){
        List<Modules> moudles=modulesServices.selectModuleList();
        List<TreeReturn> Trees= new ArrayList<TreeReturn>();
        for(Modules moudle:moudles){
            TreeReturn tree=new TreeReturn();
            tree.setId(moudle.getUid());
            tree.setpId(moudle.getPmId());
            tree.setName(moudle.getMdName());
            tree.setOpen(StringUtils.equalsIgnoreCase(moudle.getPmId(),"-1"));
            tree.setIsParent(StringUtils.equalsIgnoreCase(moudle.getPmId(),"-1"));
            Trees.add(tree);
        }
        ResultUtils.write(response,Trees);
        return null;
    }
    /**
     * 检查用户名
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/checkUserName.do")
    public String checkUserName(HttpServletRequest request, HttpServletResponse response){
        String username= request.getParameter("username");
        Map<String, Boolean> map = new HashMap<>();
        map.put("valid", true);
        ResultUtils.write(response,map);
        return null;
    }
}
