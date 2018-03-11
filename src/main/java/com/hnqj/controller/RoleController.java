package com.hnqj.controller;

import com.hnqj.model.*;
import com.hnqj.services.RolesServices;
import com.hnqj.services.RolesmodulesServices;
import com.hnqj.services.RolesuserServices;
import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.services.SysusermgrServices;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.hnqj.core.ResultUtils.toJson;

/**
 * 张威 2017/11
 */
@Controller
@RequestMapping("/role")
public class RoleController extends  BaseController {
    @Autowired
    RolesServices rolesServices;
    @Autowired
    RolesmodulesServices rolesmodulesServices;
    @Autowired
    RolesuserServices rolesuserServices;
    @Autowired
    SysusermgrServices sysusermgrServices;

    /**
     * 跳转到角色管理页面
     * @return
     */
    @RequestMapping("/roleList.do")
    public String roleList(){
        return "sys/roleList";
    }
    /**
     * 获取角色列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/getRoleList.do")
    public String getRoleList(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("getRoleList");
        //偏移量
        int offset = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        // 每页行数
        int limit = request.getParameter("limit") == null ? 0 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn = new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",offset);
        pageData.put("limit",limit);
        List<Roles> roleList=rolesServices.getAllRoles(pageData);
        tablereturn.setTotal(roleList.size());
        tablereturn.setRows(roleList);
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }
    /**
     * 根据id查看角色详情
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findRoleByFid.do")
    public String findRoleByFid(HttpServletRequest request, HttpServletResponse response){
        logger.info("findRoleByFid");
        String fids = request.getParameter("fid");
        String[] fid=fids.split(",");
        Roles role =null;
        for (String id:fid){
            role = rolesServices.selectRolesByFid(id);
            ResultUtils.write(response,toJson(role));
        }
        return null;
    }
    /**
     * 增加角色
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addRole.do")
    public String addRole(HttpServletRequest request, HttpServletResponse response){
        logger.info("addRole");
        try{
            String fid =  request.getParameter("fid") == "" ? UUID.randomUUID().toString() : request.getParameter("fid");
            String roleName = request.getParameter("roleName");
            String roleDescription = request.getParameter("roleDescription");
            String roleCreator = request.getParameter("roleCreator");
            PageData pageData = new PageData();
            pageData.put("uid",fid);
            pageData.put("roleName",roleName);
            pageData.put("roleCreator",roleCreator);
            pageData.put("roleDescription",roleDescription);
            pageData.put("roleCtime",new Date());
            pageData.put("roleEnabled",0);
            rolesServices.addRoles(pageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("addMenu e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 更新角色
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/updateRole.do")
    public String updateRole(HttpServletRequest request, HttpServletResponse response){
        logger.debug("updateRole");
        try{
            String fid =  request.getParameter("fid");
            String roleName = request.getParameter("roleName");
            String roleDescription = request.getParameter("roleDescription");
            String roleCreator = request.getParameter("roleCreator");
            Roles role=new Roles();
            PageData pageData = new PageData();
            pageData.put("uid",fid);
            pageData.put("roleName",roleName);
            pageData.put("roleCreator",roleCreator);
            pageData.put("roleDescription",roleDescription);
            pageData.put("roleCtime",new Date());
            pageData.put("roleEnabled",0);
            rolesServices.updateRoles(pageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("updateRole e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 删除角色
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/delRoleFids.do")
    @Transactional
    public String delRoleFids(HttpServletRequest request, HttpServletResponse response, Roles role){
        logger.debug("delRoleFids");
        logger.info(request.getParameter("ids"));
        try{
            String ids = request.getParameter("ids") == null ? "" : request.getParameter("ids");
            String[] idStrs=ids.split(",");
            for (String fid:idStrs){
                rolesServices.deleteRolesByFid(fid);
            }
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("delRoleFids e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 分配权限
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addRoleMoudle.do")
    public String addRoleMoudle(HttpServletRequest request, HttpServletResponse response){
        logger.info("addRoleMoudle");
        try{
            String role_id = request.getParameter("role_id");
            String module_ids = request.getParameter("module_id");//前台传过来的菜单ID
            List<Rolesmodules> list=rolesmodulesServices.getModuleByRoleId(role_id);//已经关联的菜单
            String[] mdoule_id = module_ids.split(",");
            List<String> lit= Arrays.asList(mdoule_id);//数组转换为集合
            List<String> arrayList=new ArrayList<String>(lit);
            for(Rolesmodules rolesModules:list) {//遍历已经关联的菜单
                if (lit.contains(rolesModules.getMdId())) {
                    arrayList.remove(rolesModules.getMdId());
                }else{
                    PageData pageData = new PageData();
                    pageData.put("role_id",role_id);
                    pageData.put("mdId",rolesModules.getMdId());
                    rolesmodulesServices.delRolesModulesByMdId(pageData);
                }
            }
            for(String id:arrayList ){
                Rolesmodules roleModules = new Rolesmodules();
                PageData pageData = new PageData();
                pageData.put("uid",UUID.randomUUID().toString());
                pageData.put("mdId",id);
                pageData.put("roleId",role_id);
                pageData.put("rmCtime",new Date());
                rolesmodulesServices.addRolesModule(pageData);
            }
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("addRoleMoudle e="+e);
            e.printStackTrace();
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    @RequestMapping("/getRoleMoudles.do")
    public String getRoleMoudles(HttpServletRequest request, HttpServletResponse response){
        String role_id = request.getParameter("role_id");
        List roleModuList  = rolesmodulesServices.getModuleByRoleId(role_id);
        ResultUtils.write(response,toJson(roleModuList));
        return null;
    }
    /**
     * 关联用户
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addRoleUser.do")
    public String addRoleUser(HttpServletRequest request, HttpServletResponse response){
        logger.info("addRoleUser");
        try{
            String role_id = request.getParameter("roleId");
            String user_ids = request.getParameter("user_id");
            List<Rolesuser> list=rolesuserServices.getUserByRoleId(role_id);
            String[] user_id = user_ids.split(",");
            List<String> lit=Arrays.asList(user_id);
            List<String> arrayList=new ArrayList<String>(lit);
            for(Rolesuser rolesUser:list) {
                if (lit.contains(rolesUser.getUserId())) {
                    arrayList.remove(rolesUser.getUserId());
                }else {
                    PageData pageData = new PageData();
                    pageData.put("userId", rolesUser.getUserId());
                    pageData.put("roleId", rolesUser.getRoleId());
                    rolesuserServices.delRolesUser(pageData);
                }
            }
            for(String id:arrayList ){
                String fid=UUID.randomUUID().toString();
                PageData pageData = new PageData();
                pageData.put("uid",fid);
                pageData.put("userId",id);
                pageData.put("roleId",role_id);
                pageData.put("roleCtime",new Date());
                pageData.put("isdel",1);
                rolesuserServices.addRolesUser(pageData);
            }
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("addRoleUser e="+e);
            e.printStackTrace();
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 根据角色ID获取此角色关联的菜单
     */
    @RequestMapping("/getModuleByRoleId.do")
    public String getModuleByRoleId(HttpServletRequest request, HttpServletResponse response){
        logger.info("getModuleByRoleId");
        String roleId=request.getParameter("roleid")==null ? "" :request.getParameter("roleid");
        List<Rolesmodules> list=rolesmodulesServices.getModuleByRoleId(roleId);
        List<Map<String,String>> hashMaps=new ArrayList<>();
        for(Rolesmodules rolesModules:list){
            Map<String,String> map=new HashedMap();
            map.put("roleid",rolesModules.getMdId());
            hashMaps.add(map);
        }
        ResultUtils.write(response,toJson(hashMaps));
        return null;
    }
    /**
     * 根绝角色ID获取关联的用户
     */
    @RequestMapping("/getUserByRileId.do")
    public String getUserByRileId(HttpServletRequest request, HttpServletResponse response){
        logger.info("getUserByRileId");
        String roleId=request.getParameter("roleid")==null ? "" :request.getParameter("roleid");
        List<Rolesuser> list=rolesuserServices.getUserByRoleId(roleId);
        List<Map<String,String>> hashMaps=new ArrayList<>();
        for(Rolesuser rolesUser:list){
            Map<String,String> map=new HashedMap();
            Sysusermgr sysusermgr=sysusermgrServices.getSysUserByUid(rolesUser.getUserId());
            map.put("fname",sysusermgr.getFristname());
            map.put("fid",sysusermgr.getUid());
            hashMaps.add(map);
        }
        ResultUtils.write(response,toJson(hashMaps));
        return null;
    }
}
