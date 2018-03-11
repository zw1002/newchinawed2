package com.hnqj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Comment;
import com.hnqj.services.CommentServices;
import com.hnqj.services.UserinfoServices;
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
 * Created by nyw on 2017/11/30.
 */
@Controller
@RequestMapping("/worksCommentMgr")
public class CommentWorkController extends  BaseController{
    @Autowired
    CommentServices commentService;
    @Autowired
    UserinfoServices userinfoServices;
        /*
     *跳转作品评价信息管理页面 /worksCommentMgr/toWorkCommentoList.do
     * */
    @RequestMapping("/toWorkCommentoList.do")
    public String toWorkCommentoList(){
        return "works_manager/toWorkCommentoList";
    }

    //获取信息列表/worksCommentMgr/getCommentList.do
    @RequestMapping("/getCommentList.do")
    public String getCommentList(HttpServletRequest request, HttpServletResponse response){
        logger.info("getCommentList");
        String delflag = request.getParameter("delflg") == null ? "" : request.getParameter("delflg");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 50 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        pageData.put("delflg",delflag);
        List<Comment> list=commentService.getAllComment(pageData);
        pageData.put("limit",0);
        List<Comment> listCount=commentService.getAllComment(pageData);
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(list);
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }
    //添加一条作品评论记录
    /*
    **/
    @RequestMapping("/addComment.do")
    public String addComment(HttpServletRequest request, HttpServletResponse response, Model model){
        //获取提交参数
        logger.info("addComment");
        String jsonTxt = request.getParameter("jsontxt") == null ? "" : request.getParameter("jsontxt");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        JSONObject jsonObj = JSON.parseObject(jsonTxt );
        //转换为作品Model
        PageData trainPageData = new PageData();
        trainPageData.put("uid", UUID.randomUUID().toString());
        trainPageData.put("worksid",jsonObj.getString("worksid"));//作品id
        trainPageData.put("commentuserid",jsonObj.getString("commentuserid"));//评论人id
        trainPageData.put("commenttime",jsonObj.getString("commenttime"));//评论时间
        trainPageData.put("commentinfo",jsonObj.getString("commentinfo"));//评论内容
        trainPageData.put("parentid",jsonObj.getString("parentid"));//父级评论id
        trainPageData.put("okNums",jsonObj.getString("okNums"));//评论点赞数
        trainPageData.put("delflag",0);//删除标志 默认0
        //插入数据库
        try{
            commentService.addComment(trainPageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("addComment e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    @RequestMapping("/delCommentList.do")
    public String delCommentList(HttpServletRequest request, HttpServletResponse response){
        logger.info("delCommentList");
        String jsonTxt = request.getParameter("ids") == null ? "" : request.getParameter("ids");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        String[] idStrs = jsonTxt.split(",");
        try{
            for (String fid:idStrs){
                commentService.delCommentByFid(fid);
            }
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("delCommentList e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    //修改更新一条记录
    @RequestMapping("/checkComment.do")
    public String checkComment(HttpServletRequest request, HttpServletResponse response){
        //获取提交参数
        logger.info("checkComment");
        String struid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        String delflag = request.getParameter("delflag") == null ? "" : request.getParameter("delflag");

        //转换为作品Model
        PageData trainPageData = new PageData();
        trainPageData.put("uid", struid);
        trainPageData.put("delflg",delflag);//删除标志 默认0
        //插入数据库
        try{
            if(commentService.updateComment(trainPageData)>0)
            ResultUtils.writeSuccess(response);
            else
                ResultUtils.writeFailed(response);
        } catch (Exception e) {
            logger.error("checkComment e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
}
