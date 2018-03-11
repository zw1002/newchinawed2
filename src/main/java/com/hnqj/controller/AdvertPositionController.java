package com.hnqj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Advert;
import com.hnqj.services.AdvertServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

import static com.hnqj.core.ResultUtils.toJson;

/**
 * Created by nyw on 2017/12/02.
 * 广告位信息表
 */

@Controller
@RequestMapping("/advert_pos")
public class AdvertPositionController extends  BaseController{

    @Autowired
    AdvertServices advertServices;
/*
     *跳转信息管理页面   /advert_pos/toAdPosition.do
     * */
    @RequestMapping("/toAdPosition.do")
    public String toAdPosition(){
        return "advert_manager/toAdPosition";
    }

    //获取信息列表   /advert_pos/getAdPositionList.do
    @RequestMapping("/getAdPositionList.do")
    public String getAdPositionList(HttpServletRequest request, HttpServletResponse response){
        logger.info("getAdPositionList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 10 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        List<Advert> list=advertServices.getAllAdvert(pageData);
        pageData.put("limit",0);
        List<Advert> listCount=advertServices.getAllAdvert(pageData);
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(list);
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }
    //添加一条记录
    /*/advert_pos/addAdvert.do
    **/
    @RequestMapping("/addAdvert.do")
    public String addAdvert(HttpServletRequest request, HttpServletResponse response, Model model){
        //获取提交参数
        logger.info("addAdvert");
//        String jsonTxt = request.getParameter("jsontxt") == null ? "" : request.getParameter("jsontxt");
//        if(jsonTxt.equals("")){
//            ResultUtils.writeFailed(response);
//        }
//        JSONObject jsonObj = JSON.parseObject(jsonTxt );
        //
        String adposition = request.getParameter("adname") == null ? "" : request.getParameter("adname");
        String adprice = request.getParameter("adprice") == null ? "0" : request.getParameter("adprice");
        String adheight = request.getParameter("adheight") == null ? "0" : request.getParameter("adheight");
        String adwidth = request.getParameter("adwidth") == null ? "0" : request.getParameter("adwidth");


        PageData trainPageData = new PageData();
        trainPageData.put("uid", UUID.randomUUID().toString());
        trainPageData.put("adposition",adposition);//名称
        trainPageData.put("adprice",adprice);//价格
        trainPageData.put("adheight",adheight);//高度
        trainPageData.put("adwidth",adwidth);//宽度

        //插入数据库
        try{
            if(advertServices.addAdvert(trainPageData)>0)
                ResultUtils.writeSuccess(response);
            else
                ResultUtils.writeFailed(response);
        } catch (Exception e) {
            logger.error("addAdvert e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    @RequestMapping("/delAdvert.do")
    public String delAdvert(HttpServletRequest request, HttpServletResponse response){
        logger.info("delAdvert");
        String jsonTxt = request.getParameter("ids") == null ? "" : request.getParameter("ids");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        String[] idStrs = jsonTxt.split(",");
        try{
            for (String fid:idStrs){
                advertServices.delAdvertByFid(fid);
            }
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("delAdvert e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    //修改更新一条记录 /advert_pos/updateAdvert.do
    @RequestMapping("/updateAdvert.do")
    public String updateAdvert(HttpServletRequest request, HttpServletResponse response){
        //获取提交参数
        logger.info("updateAdvert");
//        String jsonTxt = request.getParameter("jsontxt") == null ? "" : request.getParameter("jsontxt");
//        if(jsonTxt.equals("")){
//            ResultUtils.writeFailed(response);
//        }
//        JSONObject jsonObj = JSON.parseObject(jsonTxt );

        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        String adposition = request.getParameter("adname") == null ? "" : request.getParameter("adname");
        String adprice = request.getParameter("adprice") == null ? "0" : request.getParameter("adprice");
        String adheight = request.getParameter("adheight") == null ? "0" : request.getParameter("adheight");
        String adwidth = request.getParameter("adwidth") == null ? "0" : request.getParameter("adwidth");


        PageData trainPageData = new PageData();
        trainPageData.put("uid", uid);
        trainPageData.put("adposition",adposition);//名称
        trainPageData.put("adprice",adprice);//价格
        trainPageData.put("adheight",adheight);//高度
        trainPageData.put("adwidth",adwidth);//宽度
        //插入数据库
        try{
            if(advertServices.updateAdvert(trainPageData)>0)
                ResultUtils.writeSuccess(response);
            else
                ResultUtils.writeFailed(response);

        } catch (Exception e) {
            logger.error("updateAdvert e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }

}
