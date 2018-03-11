package com.hnqj.controller;

import com.hnqj.core.ResultUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hnqj.core.PageData;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Groomapply;
import com.hnqj.services.GroomapplyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/worksGroomMgr")
public class WorksGroomApplyController extends  BaseController{
    @Autowired
    GroomapplyServices groomService;
    /*
    *跳转信息管理页面 /worksGroomMgr/toWorkLabelList.do
    * */
    @RequestMapping("/toWorkLabelList.do")
    public String toWorkGroomList(){
        return "works_manager/toWorksGroomApply";
    }

    //获取信息列表  /worksGroomMgr/getLabelList.do
    @RequestMapping("/getLabelList.do")
    public String getGroomList(HttpServletRequest request, HttpServletResponse response){
        logger.info("getGroomList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 50 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        List<Groomapply> list=groomService.getAllGroomapply(pageData);
        List<Groomapply> listCount=groomService.selectGroomapplyList();
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(list);
        ResultUtils.write(response,ResultUtils.toJson(tablereturn));
        return null;
    }
    //添加一条推荐
    /*
    **/
    @RequestMapping("/addGroom.do")
    public String addGroom(HttpServletRequest request, HttpServletResponse response, Model model){
        //获取提交参数
        logger.info("addGroom");
        String jsonTxt = request.getParameter("jsontxt") == null ? "" : request.getParameter("jsontxt");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        JSONObject jsonObj = JSON.parseObject(jsonTxt );
        //转换为Model
        PageData trainPageData = new PageData();
        trainPageData.put("uid", UUID.randomUUID().toString());
        trainPageData.put("labelname",jsonObj.getString("labelname"));//标签名称
        trainPageData.put("labelnum",jsonObj.getString("labelnum"));//标签被使用次数
        trainPageData.put("codeid",jsonObj.getString("codeid"));//所属作品分类ID
        //插入数据库
        try{
            groomService.addGroomapply(trainPageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("addGroom e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    @RequestMapping("/delGroomList.do")
    public String delGroomList(HttpServletRequest request, HttpServletResponse response){
        logger.info("delGroomList");
        String jsonTxt = request.getParameter("ids") == null ? "" : request.getParameter("ids");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        String[] idStrs = jsonTxt.split(",");
        try{
            for (String fid:idStrs){
                groomService.delGroomapplyByFid(fid);
                //labelService.delWorklabelByFid(fid);
            }
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("delGroomList e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    //修改更新一条记录
    @RequestMapping("/updateGroom.do")
    public String updateGroom(HttpServletRequest request, HttpServletResponse response){
        //获取提交参数
        logger.info("updateGroom");
        String jsonTxt = request.getParameter("jsontxt") == null ? "" : request.getParameter("jsontxt");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        JSONObject jsonObj = JSON.parseObject(jsonTxt );

        //转换为作品Model
        PageData trainPageData = new PageData();
        trainPageData.put("uid", UUID.randomUUID().toString());
        trainPageData.put("labelname",jsonObj.getString("labelname"));//标签名称
        trainPageData.put("labelnum",jsonObj.getString("labelnum"));//标签被使用次数
        trainPageData.put("codeid",jsonObj.getString("codeid"));//所属作品分类ID
        //插入数据库
        try{
            groomService.updateGroomapply(trainPageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("updateGroom e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
}
