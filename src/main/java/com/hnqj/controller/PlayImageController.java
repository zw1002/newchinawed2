package com.hnqj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Playimg;
import com.hnqj.services.PlayimgServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.hnqj.core.ResultUtils.toJson;

/**
 * Created by nyw on 2017/11/30.
 */
@Controller
@RequestMapping("/playImageMgr")
public class PlayImageController  extends  BaseController{
    @Autowired
    PlayimgServices playimgService;

    /*
     *跳转轮播信息管理页面  /playImageMgr/toPlayimgList.do
     * */
    @RequestMapping("/toPlayimgList.do")
    public String toPlayimgList(){
        return "business/toPlayimgList";
    }

    //获取信息列表
    @RequestMapping("/getPlayimgList.do")
    public String getPlayimgList(HttpServletRequest request, HttpServletResponse response){
        logger.info("getPlayimgList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 20 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        List<Playimg> list=playimgService.getAllPlayimg(pageData);
        List<Playimg> listCount=playimgService.selectPlayimgList();
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(list);
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }
    //添加一条轮播图片记录
    /*
    **/
    @RequestMapping("/addPlayimg.do")
    public String addPlayimg(HttpServletRequest request, HttpServletResponse response){
        //获取提交参数
        logger.info("addPlayimg");
        String worksid = request.getParameter("worksid") == null ? "" : request.getParameter("worksid");
        String playsort = request.getParameter("playsort") == null ? "" : request.getParameter("playsort");
        String navurl = request.getParameter("navurl") == null ? "" : request.getParameter("navurl");
        String playtype = request.getParameter("playtype") == null ? "" : request.getParameter("playtype");
        String typeremark = request.getParameter("typeremark") == null ? "" : request.getParameter("typeremark");
        MultiValueMap<String, MultipartFile> multFiles = ((DefaultMultipartHttpServletRequest)request).getMultiFileMap();
        List<MultipartFile> files =multFiles.get("upload");
        String HOMEPATH = request.getSession().getServletContext().getRealPath("/") + "static/uploadImg/";
        // 如果目录不存在则创建
        File uploadDir = new File(HOMEPATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        for(MultipartFile file:files){//读取文件并上保存
            try{
            String myFileName = file.getOriginalFilename();
            long fileSize = file.getSize();
            //保存文件
            File localFile = new File(HOMEPATH + myFileName);
            file.transferTo(localFile);
            worksid= "/static/uploadImg/"+myFileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //转换为作品Model
        PageData trainPageData = new PageData();
        trainPageData.put("playuid", UUID.randomUUID().toString());
        trainPageData.put("imgurl",worksid);//图片作品ID或URL，建议为URL
        trainPageData.put("playsort",playsort);//轮播图片顺序
        trainPageData.put("navurl",navurl);//图片导航URL
        trainPageData.put("playtype",playtype);//轮播图片类型
        trainPageData.put("typeremark",typeremark);//类型说明
        trainPageData.put("creator",getUser().getFristname());//创建人ID
        trainPageData.put("createtime",getCurrentTime());//创建时间
        trainPageData.put("delflg",0);//删除标志 默认0
        //插入数据库
        try{
            playimgService.addPlayimg(trainPageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("addPlayimg e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    @RequestMapping("/delPlayimgList.do")
    public String delPlayimgList(HttpServletRequest request, HttpServletResponse response){
        logger.info("delPlayimgList");
        String jsonTxt = request.getParameter("ids") == null ? "" : request.getParameter("ids");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        String[] idStrs = jsonTxt.split(",");
        try{
            for (String fid:idStrs){
                playimgService.delPlayimgByFid(fid);
            }
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("delPlayimgList e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    //修改更新一条记录
    @RequestMapping("/updatePlayimg.do")
    public String updatePlayimg(HttpServletRequest request, HttpServletResponse response){
        //获取提交参数
        logger.info("updatePlayimg");
        String worksid = request.getParameter("worksid") == null ? "" : request.getParameter("worksid");
        String playsort = request.getParameter("playsort") == null ? "" : request.getParameter("playsort");
        String navurl = request.getParameter("navurl") == null ? "" : request.getParameter("navurl");
        String playtype = request.getParameter("playtype") == null ? "" : request.getParameter("playtype");
        String typeremark = request.getParameter("typeremark") == null ? "" : request.getParameter("typeremark");
        String fuid = request.getParameter("fuid") == null ? "" : request.getParameter("fuid");
        MultiValueMap<String, MultipartFile> multFiles = ((DefaultMultipartHttpServletRequest)request).getMultiFileMap();
        List<MultipartFile> files =multFiles.get("upload");
        String HOMEPATH = request.getSession().getServletContext().getRealPath("/") + "static/uploadImg/";
        // 如果目录不存在则创建
        File uploadDir = new File(HOMEPATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        for(MultipartFile file:files){//读取文件并上保存
            try{
                String myFileName = file.getOriginalFilename();
                //long fileSize = file.getSize();
                //保存文件
                File localFile = new File(HOMEPATH + myFileName);
                file.transferTo(localFile);
                worksid= "/static/uploadImg/"+myFileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //转换为作品Model
        PageData trainPageData = new PageData();
        trainPageData.put("playuid", fuid);
        trainPageData.put("imgurl",worksid);//图片作品ID或URL，建议为URL
        trainPageData.put("playsort",playsort);//轮播图片顺序
        trainPageData.put("navurl",navurl);//图片导航URL
        trainPageData.put("playtype",playtype);//轮播图片类型
        trainPageData.put("typeremark",typeremark);//类型说明
        trainPageData.put("creator",getUser().getFristname());//创建人ID
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        df.format(new Date());// new Date()为获取当前系统时间
        trainPageData.put("createtime",df.format(new Date()));//创建时间
        trainPageData.put("delflg",0);//删除标志 默认0
        //插入数据库
        try{
            playimgService.updatePlayimg(trainPageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("updatePlayimg e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }

}
