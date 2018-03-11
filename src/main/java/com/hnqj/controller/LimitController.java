package com.hnqj.controller;

import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Limit;
import com.hnqj.services.LimitServices;
import com.hnqj.services.LimitServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.zip.DataFormatException;

import static com.hnqj.core.ResultUtils.toDateJson;
import static com.hnqj.core.ResultUtils.toJson;

/**
 * 张威 2017/11
 * 提现额度
 */
@Controller
@RequestMapping("/limit")
public class LimitController extends  BaseController{
    @Autowired
    LimitServices limitServices;
    /**
     * 跳转到提现额度管理页面
     * @return
     */
    @RequestMapping("/toLimitList.do")
    public String toLimitList(){
        return "sysparameter/limitlist";
    }

    /**
     *初始化后台数据
     */
    @RequestMapping("/getLimitList.do")
    public String getLimitList(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("getLimitList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 50 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        List<Limit> list=limitServices.getAllLimit(pageData);
        List<Limit> listCount=limitServices.selectLimitList();
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(list);
        ResultUtils.write(response,toDateJson(tablereturn));
        return null;
    }
    /**
     * 新增
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addLimit.do")
    public String addLimit(HttpServletRequest request, HttpServletResponse response){
        logger.info("addLimit");
        String mininum = request.getParameter("mininum") == null ? "" : request.getParameter("mininum");
        String maxinum=request.getParameter("maxinum") == null ? "" : request.getParameter("maxinum");
        String drawallimit = request.getParameter("drawallimit") == null ? "" : request.getParameter("drawallimit");
        PageData PageData = new PageData();
        String uuid= UUID.randomUUID().toString();
        PageData.put("uid",uuid);
        PageData.put("mininum",mininum);
        PageData.put("maxinum",maxinum);
        PageData.put("drawallimit",drawallimit);
        PageData.put("createtime",new Date());
        try{
            limitServices.addLimit(PageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("addLimit e="+e.getMessage());
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
    @RequestMapping("/updateLimit.do")
    public String updateLimit(HttpServletRequest request, HttpServletResponse response){
        logger.debug("updateLimit");
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        String mininum = request.getParameter("mininum") == null ? "" : request.getParameter("mininum");
        String maxinum=request.getParameter("maxinum") == null ? "" : request.getParameter("maxinum");
        String drawallimit = request.getParameter("drawallimit") == null ? "" : request.getParameter("drawallimit");
        PageData PageData = new PageData();
        PageData.put("uid",uid);
        PageData.put("mininum",mininum);
        PageData.put("maxinum",maxinum);
        PageData.put("drawallimit",drawallimit);
        PageData.put("createtime",new Date());
        try{
            limitServices.updateLimit(PageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("updateLimit e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 删除
     */
    @RequestMapping("/deleteLimitByUid.do")
    public void deleteLimitByUid(HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        try{
            limitServices.delLimitByFid(uid);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("deleteLimitByUid e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
    }

    /**
     * 通过ID获取数据
     */
    @RequestMapping("/getLimitByUid.do")
    public void getLimitByUid(HttpServletRequest request, HttpServletResponse response){
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        Limit Limit=limitServices.getLimitByUid(uid);
        ResultUtils.write(response,toJson(Limit));
    }

}
