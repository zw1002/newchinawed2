package com.hnqj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Releaseadvert;
import com.hnqj.services.ReleaseadvertServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.hnqj.core.ResultUtils.toJson;
/**
 * Created by nyw on 2017/11/29.
 */
@Controller
@RequestMapping("/advertInfoMgr")
public class ReleaseadvertController extends BaseController{
    @Autowired
    ReleaseadvertServices adServices;
    /*
  *跳转广告信息管理页面  /advertInfoMgr/toAdvertInfoList.do
  * */
    @RequestMapping("/toAdvertInfoList.do")
    public String toAdvertInfoList(){
        return "advert_manager/toAdvertInfoList";
    }

    //获取广告信息列表 advertInfoMgr/getAdvertInfoList.do
    @RequestMapping("/getAdvertInfoList.do")
    public String getAdvertInfoList(HttpServletRequest request, HttpServletResponse response){
        logger.info("getAdvertInfoList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 50 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        List<Releaseadvert> list=adServices.getAllReleaseadvert(pageData);
        pageData.put("limit",0);
        List<Releaseadvert> listCount=adServices.getAllReleaseadvert(pageData);
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(list);
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }
    //添加一条广告信息记录
    /*
    **/
    @RequestMapping("/addAdvertInfo.do")
    public String addAdvertInfoList(HttpServletRequest request, HttpServletResponse response, Model model){
        //获取提交参数
        logger.info("addAdvertInfoList");
        String adpositionid = request.getParameter("adpositionid") == null ? "" : request.getParameter("adpositionid");
        String adprice = request.getParameter("adprice") == null ? "" : request.getParameter("adprice");
        String imgurl = request.getParameter("imgurl") == null ? "" : request.getParameter("imgurl");
        String adurl = request.getParameter("adurl") == null ? "" : request.getParameter("adurl");
        String clickcount = request.getParameter("clickcount") == null ? "0" : request.getParameter("clickcount");
        String clientuid = request.getParameter("clientuid") == null ? "" : request.getParameter("clientuid");
        String adstarttime = request.getParameter("adstarttime") == null ? "" : request.getParameter("adstarttime");
        String adendtime = request.getParameter("adendtime") == null ? "" : request.getParameter("adendtime");
        String adtype = request.getParameter("adtype") == null ? "" : request.getParameter("adtype");
        String strUid=UUID.randomUUID().toString();
        MultiValueMap<String, MultipartFile> multFiles = ((DefaultMultipartHttpServletRequest)request).getMultiFileMap();
        String trainLogImg=imgurl;
        List<MultipartFile> files =multFiles.get("upload");
        String HOMEPATH = request.getSession().getServletContext().getRealPath("/") + "static/uploadImg/advertImg/";
        // 如果目录不存在则创建
        File uploadDir = new File(HOMEPATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if(files!=null) {
            for (MultipartFile file : files) {//读取文件并上保存
                try {
                    String myFileName = file.getOriginalFilename();
                    long fileSize = file.getSize();
                    String newFileName = strUid + myFileName.substring(myFileName.lastIndexOf("."));
                    //保存文件
                    File localFile = new File(HOMEPATH + newFileName);
                    file.transferTo(localFile);
                    trainLogImg = "/static/uploadImg/advertImg/" + newFileName;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //转换为广告信息Model
        PageData adPageData = new PageData();
        adPageData.put("aduid", strUid);
        adPageData.put("adpositionid",adpositionid);//所属广告位ID
        adPageData.put("adprice",adprice);//广告价格
        adPageData.put("imgurl",trainLogImg);//广告位展示资源URL
        adPageData.put("adurl",adurl);//广告位导航URL
        adPageData.put("clickcount",clickcount);//点击量
        adPageData.put("clientuid",clientuid);//客户信息ID
        adPageData.put("starttime",adstarttime);//开始时间
        adPageData.put("endtime",adendtime);//结束时间
        adPageData.put("adtype",adtype);
        adPageData.put("creator",getUser().getFristname());//创建人
        adPageData.put("createtime",getCurrentTime());//创建时间
        //adPageData.put("delflag",0);//删除标志 默认0
        //插入数据库
        try{
            if(adServices.addReleaseadvert(adPageData)>0)
            ResultUtils.writeSuccess(response);
            else
                ResultUtils.writeFailed(response);
        } catch (Exception e) {
            logger.error("addAdvertInfoList e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    @RequestMapping("/delAdvertInfoList.do")
    public String delAdvertInfoList(HttpServletRequest request, HttpServletResponse response){
        logger.info("delAdvertInfoList");
        String jsonTxt = request.getParameter("ids") == null ? "" : request.getParameter("ids");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        String[] idStrs = jsonTxt.split(",");
        try{
            for (String fid:idStrs){
                adServices.delReleaseadvertByFid(fid);
            }
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("delAdvertInfoList e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    //修改更新一条记录
    @RequestMapping("/updateAdvertInfo.do")
    public String updateAdvertInfo(HttpServletRequest request, HttpServletResponse response){
        //获取提交参数
        logger.info("updateAdvertInfo");
        String strUid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        String adpositionid = request.getParameter("adpositionid") == null ? "" : request.getParameter("adpositionid");
        String adprice = request.getParameter("adprice") == null ? "" : request.getParameter("adprice");
        String imgurl = request.getParameter("imgurl") == null ? "" : request.getParameter("imgurl");
        String adurl = request.getParameter("adurl") == null ? "" : request.getParameter("adurl");
        String clickcount = request.getParameter("clickcount") == null ? "0" : request.getParameter("clickcount");
        String clientuid = request.getParameter("clientuid") == null ? "" : request.getParameter("clientuid");
        String adstarttime = request.getParameter("adstarttime") == null ? "" : request.getParameter("adstarttime");
        String adendtime = request.getParameter("adendtime") == null ? "" : request.getParameter("adendtime");
        String adtype = request.getParameter("adtype") == null ? "" : request.getParameter("adtype");

        MultiValueMap<String, MultipartFile> multFiles = ((DefaultMultipartHttpServletRequest)request).getMultiFileMap();
        String trainLogImg=imgurl;
        List<MultipartFile> files =multFiles.get("upload");
        String HOMEPATH = request.getSession().getServletContext().getRealPath("/") + "static/uploadImg/advertImg/";
        // 如果目录不存在则创建
        File uploadDir = new File(HOMEPATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if(files!=null) {
            for (MultipartFile file : files) {//读取文件并上保存
                try {
                    String myFileName = file.getOriginalFilename();
                    long fileSize = file.getSize();
                    String newFileName = strUid + myFileName.substring(myFileName.lastIndexOf("."));
                    //保存文件
                    File localFile = new File(HOMEPATH + newFileName);
                    file.transferTo(localFile);
                    trainLogImg = "/static/uploadImg/advertImg/" + newFileName;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //转换为广告信息Model
        PageData adPageData = new PageData();
        adPageData.put("aduid", strUid);
        adPageData.put("adpositionid",adpositionid);//所属广告位ID
        adPageData.put("adprice",adprice);//广告价格
        adPageData.put("imgurl",trainLogImg);//广告位展示资源URL
        adPageData.put("adurl",adurl);//广告位导航URL
        adPageData.put("clickcount",clickcount);//点击量
        adPageData.put("clientuid",clientuid);//客户信息ID
        adPageData.put("starttime",adstarttime);//开始时间
        adPageData.put("endtime",adendtime);//结束时间
        adPageData.put("adtype",adtype);
        adPageData.put("creator",getUser().getFristname());//创建人
        adPageData.put("createtime",getCurrentTime());//创建时间
        //adPageData.put("delflag",0);//删除标志 默认0
        //插入数据库
        try{
            adServices.updateReleaseadvert(adPageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("updateAdvertInfo e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }


}
