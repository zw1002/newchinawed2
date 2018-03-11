package com.hnqj.controller;

import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Proportions;
import com.hnqj.services.DistributionServices;
import com.hnqj.services.ProportionsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.hnqj.core.ResultUtils.toDateJson;
import static com.hnqj.core.ResultUtils.toJson;

/**
 * 张威 2017/11
 * 分销比例
 */
@Controller
@RequestMapping("/proportions")
public class ProportionsController extends  BaseController{
    @Autowired
    ProportionsServices proportionsServices;

    /**
     * 分销分成比例管理页面
     * @return
     */
    @RequestMapping("/toProportionsList.do")
    public String toProportionsList(){
        return "sysparameter/proportionslist";
    }
    /**
     * 分销分销管理页面
     * @return
     */
    @RequestMapping("/toDistributionList.do")
    public String toDistributionList(){
        return "sysparameter/distributionlist";
    }
    /**
     *初始化数据
     */
    @RequestMapping("/getProportionsList.do")
    public String getProportionsList(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("getProportionsList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 50 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        List<Proportions> list=proportionsServices.getAllProportions(pageData);
        List<Map<String, Object>> hashMaps=new ArrayList<>();
        for(Proportions proportions:list){
            Map<String, Object> map = new HashMap<>();
            map.put("uid",proportions.getUid());
            map.put("dislevel",proportions.getDislevel());
            map.put("distprod",proportions.getDistprod()+"%");
            hashMaps.add(map);
        }
        List<Proportions> listCount=proportionsServices.selectProportionsList();
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(hashMaps);
        ResultUtils.write(response,toDateJson(tablereturn));
        return null;
    }
    /**
     * 新增
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addProportions.do")
    public String addProportions(HttpServletRequest request, HttpServletResponse response){
        logger.info("addProportions");
        String dislevel = request.getParameter("dislevel") == null ? "" : request.getParameter("dislevel");
        String distprod=request.getParameter("distprod") == null ? "" : request.getParameter("distprod");
        PageData PageData = new PageData();
        String uuid= UUID.randomUUID().toString();
        PageData.put("uid",uuid);
        PageData.put("dislevel",dislevel);
        PageData.put("distprod",distprod);
        try{
            proportionsServices.addProportions(PageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("addProportions e="+e.getMessage());
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
    @RequestMapping("/updateProportions.do")
    public String updateProportions(HttpServletRequest request, HttpServletResponse response){
        logger.debug("updateProportions");
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        String dislevel = request.getParameter("dislevel") == null ? "" : request.getParameter("dislevel");
        String distprod=request.getParameter("distprod") == null ? "" : request.getParameter("distprod");
        PageData PageData = new PageData();
        PageData.put("uid",uid);
        PageData.put("dislevel",dislevel);
        PageData.put("distprod",distprod);
        try{
            proportionsServices.updateProportions(PageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("updateProportions e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 删除
     */
    @RequestMapping("/deleteProportionsByUid.do")
    public void deleteLimitByUid(HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        try{
            proportionsServices.delProportionsByFid(uid);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("deleteProportionsByUid e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
    }

    /**
     * 通过ID获取数据
     */
    @RequestMapping("/getProportionsByUid.do")
    public void getProportionsByUid(HttpServletRequest request, HttpServletResponse response){
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        Proportions proportions=proportionsServices.getProportionsforId(uid);
        ResultUtils.write(response,toJson(proportions));
    }

    }
