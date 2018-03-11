package com.hnqj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Works;
import com.hnqj.services.MerchServices;
import com.hnqj.services.UserinfoServices;
import com.hnqj.services.WorksServices;
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
 * Created by nyw on 2017/11/28.
 */
@Controller
@RequestMapping("/worksMgr")
public class tbWorksController extends  BaseController{
    @Autowired
    WorksServices worksService;
    @Autowired
    UserinfoServices userinfoServices;
    @Autowired
    MerchServices merchServices;
    /*
   *跳转作品信息管理页面/worksMgr/toWorksList.do
   * */
    @RequestMapping("/toWorksList.do")
    public String toWorksList(){
        return "works_manager/toWorksList";
    }

    //获取信息列表
    @RequestMapping("/getWorksList.do")
    public String getWorksList(HttpServletRequest request, HttpServletResponse response){
        logger.info("getWorksList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 20 : Integer.parseInt(request.getParameter("limit"));
        String displayFlag = request.getParameter("displayFlag") == null ? null : request.getParameter("displayFlag");
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("displayflag",displayFlag);
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        List<Works> list=worksService.getAllWorks(pageData);
        pageData.put("limit",0);
        List<Works> listCount=worksService.getAllWorks(pageData);
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(list);
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }


    //更新作品状态/worksMgr/updateWorksState.do?uid=&displayFlag=
    @RequestMapping("/updateWorksState.do")
    public String updateWorksState(HttpServletRequest request, HttpServletResponse response){
        logger.info("updateWorksState");
        String strUid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        String displayFlag = request.getParameter("displayFlag") == null ? null : request.getParameter("displayFlag");

        PageData pageData = new PageData();
        pageData.put("displayflag",displayFlag);
        pageData.put("uid",strUid);
        pageData.put("checkuser",getUser().getUid());
        try
        {
            if(worksService.UpdateWorkState(pageData)>0)
            {
                ResultUtils.writeSuccess(response);
                return null;
            }

        }
        catch (Exception ee)
        {
            ee.printStackTrace();
        }
        ResultUtils.writeFailed(response);
        return null;
    }

    //添加一条作品记录
    /*
    **/
    @RequestMapping("/addWorks.do")
    public String addWorksList(HttpServletRequest request, HttpServletResponse response, Model model){
        //获取提交参数
        logger.info("addWorksList");
        String jsonTxt = request.getParameter("jsontxt") == null ? "" : request.getParameter("jsontxt");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        JSONObject jsonObj = JSON.parseObject(jsonTxt );
        //转换为作品Model
        PageData trainPageData = new PageData();
        trainPageData.put("uid", UUID.randomUUID().toString());
        trainPageData.put("workstype",jsonObj.getString("workstype"));//作品类型
        trainPageData.put("worksname",jsonObj.getString("worksname"));//作品名称
        trainPageData.put("uptime",jsonObj.getString("uptime"));//上传时间
        trainPageData.put("samllurl",jsonObj.getString("samllurl"));//缩略图地址
        trainPageData.put("worksurl",jsonObj.getString("worksurl"));//logo 原图片地址
        trainPageData.put("watermakeurl",jsonObj.getString("watermakeurl"));//水印图地址
        trainPageData.put("dpinum",jsonObj.getString("dpinum"));//分辨率
        trainPageData.put("imgsize",jsonObj.getString("imgsize"));//图片大不
        trainPageData.put("imgformart",jsonObj.getString("imgformart"));//图片格式
        trainPageData.put("colrmodel",jsonObj.getString("colrmodel"));//颜色模式
        trainPageData.put("downcount",jsonObj.getString("downcount"));//下载量
        trainPageData.put("favcount",jsonObj.getString("favcount"));//收藏量
        trainPageData.put("displayflag",jsonObj.getString("displayflag"));//显示标志
        trainPageData.put("checkuser",jsonObj.getString("checkuser"));//审核人
        trainPageData.put("chacktime",jsonObj.getString("chacktime"));//审核时间

        trainPageData.put("userid",jsonObj.getString("userid"));//作品所有人
        trainPageData.put("ticknums",jsonObj.getString("ticknums"));//点击量
        trainPageData.put("oknums",jsonObj.getString("oknums"));//点赞量
        trainPageData.put("workremark",jsonObj.getString("workremark"));//作品说明
        trainPageData.put("worklabel",jsonObj.getString("worklabel"));//作品标签
        trainPageData.put("price",jsonObj.getString("price"));//作品设定价格
        trainPageData.put("delflag",0);//删除标志 默认0
        //插入数据库
        try{
            worksService.addWorks(trainPageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("addWorksList e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    @RequestMapping("/delWorksList.do")
    public String delWorksList(HttpServletRequest request, HttpServletResponse response){
        logger.info("delWorksList");
        String jsonTxt = request.getParameter("ids") == null ? "" : request.getParameter("ids");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        String[] idStrs = jsonTxt.split(",");
        try{
            for (String fid:idStrs){
                worksService.delWorksByFid(fid);
            }
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("delWorksList e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    //修改更新一条记录
    @RequestMapping("/updateWorks.do")
    public String updateWorks(HttpServletRequest request, HttpServletResponse response){
        //获取提交参数
        logger.info("updateWorks");
        String jsonTxt = request.getParameter("jsontxt") == null ? "" : request.getParameter("jsontxt");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        JSONObject jsonObj = JSON.parseObject(jsonTxt );

        //转换为作品Model
        PageData trainPageData = new PageData();
        trainPageData.put("uid", jsonObj.getString("teruid"));
        trainPageData.put("workstype",jsonObj.getString("workstype"));//作品类型
        trainPageData.put("worksname",jsonObj.getString("worksname"));//作品名称
        trainPageData.put("uptime",jsonObj.getString("uptime"));//上传时间
        trainPageData.put("samllurl",jsonObj.getString("samllurl"));//缩略图地址
        trainPageData.put("worksurl",jsonObj.getString("worksurl"));//logo 原图片地址
        trainPageData.put("watermakeurl",jsonObj.getString("watermakeurl"));//水印图地址
        trainPageData.put("dpinum",jsonObj.getString("dpinum"));//分辨率
        trainPageData.put("imgsize",jsonObj.getString("imgsize"));//图片大不
        trainPageData.put("imgformart",jsonObj.getString("imgformart"));//图片格式
        trainPageData.put("colrmodel",jsonObj.getString("colrmodel"));//颜色模式
        trainPageData.put("downcount",jsonObj.getString("downcount"));//下载量
        trainPageData.put("favcount",jsonObj.getString("favcount"));//收藏量
        trainPageData.put("displayflag",jsonObj.getString("displayflag"));//显示标志
        trainPageData.put("checkuser",jsonObj.getString("checkuser"));//审核人
        trainPageData.put("chacktime",jsonObj.getString("chacktime"));//审核时间

        trainPageData.put("merchid",jsonObj.getString("userid"));//作品所有人
        trainPageData.put("ticknums",jsonObj.getString("ticknums"));//点击量
        trainPageData.put("oknums",jsonObj.getString("oknums"));//点赞量
        trainPageData.put("workremark",jsonObj.getString("workremark"));//作品说明
        trainPageData.put("worklabel",jsonObj.getString("worklabel"));//作品标签
        trainPageData.put("price",jsonObj.getString("price"));//作品设定价格
        trainPageData.put("delflag",0);//删除标志 默认0
        //插入数据库
        try{
            worksService.updateWorks(trainPageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("updateWorks e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }



}
