package com.hnqj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Collection;
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
 * 作品收藏信息管理
 */

@Controller
@RequestMapping("/adWorksCollectionMgr")
public class WorksCollectionController  extends  BaseController{

    //@Autowired
    //CollectionServices collService;

    //获取收藏信息列表
    @RequestMapping("/getWorksCollectionList.do")
    public String getWorksCollectionList(HttpServletRequest request, HttpServletResponse response){
        logger.info("getWorksCollectionList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 50 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        //List<Collection> list=collService.getAllCollection(pageData);
        //List<Collection> listCount=collService.selectCollectionList();
        //tablereturn.setTotal(listCount.size());
        //tablereturn.setRows(list);
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }
    //添加一条记录
    /*
    **/
    @RequestMapping("/addWorksCollection.do")
    public String addWorksCollection(HttpServletRequest request, HttpServletResponse response, Model model){
        //获取提交参数
        logger.info("addWorksCollection");
        String jsonTxt = request.getParameter("jsontxt") == null ? "" : request.getParameter("jsontxt");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        JSONObject jsonObj = JSON.parseObject(jsonTxt );
        //转换为作品Model
        PageData trainPageData = new PageData();
        trainPageData.put("collectionuid", UUID.randomUUID().toString());
        trainPageData.put("userid",jsonObj.getString("userid"));//收藏人ID
        trainPageData.put("worksid",jsonObj.getString("worksid"));//收藏作品ID
        trainPageData.put("collectiontime",jsonObj.getString("collectiontime"));//收藏时间

         //添加一条记录时需要 更新作品表收藏数+1
        //插入数据库
        try{
            //collService.addCollection(trainPageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("addWorksCollection e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    @RequestMapping("/delWorksCollectionList.do")
    public String delWorksCollectionList(HttpServletRequest request, HttpServletResponse response){
        logger.info("delWorksCollectionList");
        String jsonTxt = request.getParameter("ids") == null ? "" : request.getParameter("ids");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        String[] idStrs = jsonTxt.split(",");
        try{
            for (String fid:idStrs){
                //collService.delCollectionByFid(fid);
            }
            //删除一条记录时需要 作品表收藏数-1
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("delWorksCollectionList e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }

}
