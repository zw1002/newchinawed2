package com.hnqj.controller;

import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Integral;
import com.hnqj.services.IntegralServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.hnqj.core.ResultUtils.toJson;

/**
 * 张威 2017/11
 * 店铺等级
 */
@Controller
@RequestMapping("/integral")
public class IntegralController extends  BaseController{
    @Autowired
    IntegralServices integralServices;
    /**
     * 跳转到店铺等级管理页面
     * @return
     */
    @RequestMapping("/toIntegralList.do")
    public String toIntegralList(){
        return "sysparameter/integrallist";
    }

    /**
     *初始化后台数据
     */
    @RequestMapping("/getIntegralList.do")
    public String getIntegralList(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("getIntegralList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 50 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        List<Integral> list=integralServices.getAllIntegral(pageData);
        List<Map<String, Object>> hashMaps=new ArrayList<>();
        for(Integral Integral:list){
            Map<String, Object> map = new HashMap<>();
            map.put("uid",Integral.getUid());
            map.put("mininum",Integral.getMininum());
            map.put("maxinum",Integral.getMaxinum());
            if(Integral.getGrade() == 1){
                map.put("grade","初级店铺");
            }else if(Integral.getGrade() == 2){
                map.put("grade","中级店铺");
            }else if(Integral.getGrade() == 3){
                map.put("grade","高级店铺");
            }else{
                map.put("grade","特级店铺");
            }
            map.put("divided",Integral.getDivided()+"%");
            map.put("basedeposit",Integral.getBasedeposit());
            hashMaps.add(map);
        }
        List<Integral> listCount=integralServices.selectIntegralList();
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(hashMaps);
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }
    /**
     * 新增
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addIntegral.do")
    public String addIntegral(HttpServletRequest request, HttpServletResponse response){
        logger.info("addIntegral");
        String mininum = request.getParameter("mininum") == null ? "" : request.getParameter("mininum");
        String maxinum=request.getParameter("maxinum") == null ? "" : request.getParameter("maxinum");
        String grade=request.getParameter("grade") == null ? "" : request.getParameter("grade");
        String divided=request.getParameter("divided") == null ? "" : request.getParameter("divided");
        String basedeposit=request.getParameter("basedeposit") == null ? "" : request.getParameter("basedeposit");
        PageData PageData = new PageData();
        String uuid= UUID.randomUUID().toString();
        PageData.put("uid",uuid);
        PageData.put("mininum",mininum);
        PageData.put("maxinum",maxinum);
        PageData.put("grade",grade);
        PageData.put("divided",divided);
        PageData.put("basedeposit",basedeposit);
        try{
            integralServices.addIntegral(PageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("addIntegral e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 信息修改
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/updateIntegral.do")
    public String updateIntegral(HttpServletRequest request, HttpServletResponse response){
        logger.debug("updateIntegral");
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        String mininum = request.getParameter("mininum") == null ? "" : request.getParameter("mininum");
        String maxinum=request.getParameter("maxinum") == null ? "" : request.getParameter("maxinum");
        String grade=request.getParameter("grade") == null ? "" : request.getParameter("grade");
        String divided=request.getParameter("divided") == null ? "" : request.getParameter("divided");
        String basedeposit=request.getParameter("basedeposit") == null ? "" : request.getParameter("basedeposit");
        PageData PageData = new PageData();
        PageData.put("uid",uid);
        PageData.put("mininum",mininum);
        PageData.put("maxinum",maxinum);
        PageData.put("grade",grade);
        PageData.put("divided",divided);
        PageData.put("basedeposit",basedeposit);
        try{
            integralServices.updateIntegral(PageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("updateIntegral e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 删除
     */
    @RequestMapping("/deleteIntegralByUid.do")
    public void deleteIntegralByUid(HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        try{
            integralServices.delIntegralByFid(uid);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("deleteIntegralByUid e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
    }

    /**
     * 通过ID获取数据
     */
    @RequestMapping("/getIntegralByUid.do")
    public void getIntegralByUid(HttpServletRequest request, HttpServletResponse response){
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        Integral Integral=integralServices.getIntegralByUid(uid);
        ResultUtils.write(response,toJson(Integral));
    }

}
