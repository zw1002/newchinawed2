package com.hnqj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Train;
import com.hnqj.services.TrainServices;
import com.hnqj.services.UserinfoServices;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.hnqj.core.ResultUtils.toJson;

/**
 * Created by nyw on 2017/11/28.
 */
@Controller
@RequestMapping("/trainMgr")
public class TrainController  extends  BaseController{
    @Autowired
    TrainServices trainService;
    @Autowired
    UserinfoServices userinfoServices;

    /*
    *跳转名师信息管理页面trainMgr/toTrainList.do
    * */
    @RequestMapping("/toTrainList.do")
    public String toTrainList(){
        return "advert_manager/trainList";
    }
    //获取信息列表
    @RequestMapping("/getTrainList.do")
    public String getTrainList(HttpServletRequest request, HttpServletResponse response){
        logger.info("getTrainList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 50 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        List<Train> list=trainService.getAllTrain(pageData);
        pageData.put("limit",0);
        List<Train> listCount=trainService.getAllTrain(pageData);
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(list);
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }
    //添加一条名师记录
    /*
    *trainMgr/addTrain.do
    * */
    @RequestMapping("/addTrain.do")
    public String addTrainList(HttpServletRequest request, HttpServletResponse response, Model model){
        //获取提交参数
        logger.info("addTrainList");

        String trainname = request.getParameter("trainname") == null ? "" : request.getParameter("trainname");
        String traintime = request.getParameter("traintime") == null ? "" : request.getParameter("traintime");
        String trainaddress = request.getParameter("trainaddress") == null ? "" : request.getParameter("trainaddress");
        String phone = request.getParameter("phone") == null ? "" : request.getParameter("phone");
        String logoimg = request.getParameter("logoimg") == null ? "" : request.getParameter("logoimg");
        String topflag = request.getParameter("topflag") == null ? "-1" : request.getParameter("topflag");
        String price = request.getParameter("price") == null ? "0" : request.getParameter("price");
        String delflag = request.getParameter("delflag") == null ? "0" : request.getParameter("delflag");
        String legtime = request.getParameter("legtime") == null ? "" : request.getParameter("legtime");
        String coursename = request.getParameter("coursename") == null ? "" : request.getParameter("coursename");
        String trainclass = request.getParameter("trainclass") == null ? "" : request.getParameter("trainclass");
        String trainmethod = request.getParameter("trainmethod") == null ? "" : request.getParameter("trainmethod");
        String traincontent = request.getParameter("traincontent") == null ? "" : request.getParameter("traincontent");
        String endtime = request.getParameter("endtime") == null ? "" : request.getParameter("endtime");
        String uuid=UUID.randomUUID().toString();
        if(topflag.equals("")) topflag="-1";
        MultiValueMap<String, MultipartFile> multFiles = ((DefaultMultipartHttpServletRequest)request).getMultiFileMap();
        String trainLogImg="";
        List<MultipartFile> files =multFiles.get("upload");
        String HOMEPATH = request.getSession().getServletContext().getRealPath("/") + "static/uploadImg/trainLogoImg/";
        // 如果目录不存在则创建
        File uploadDir = new File(HOMEPATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        for(MultipartFile file:files){//读取文件并上保存
            try{
                String myFileName = file.getOriginalFilename();
                long fileSize = file.getSize();
                String newFileName=uuid+myFileName.substring(myFileName.lastIndexOf("."));
                //保存文件
                File localFile = new File(HOMEPATH + newFileName);
                file.transferTo(localFile);
                trainLogImg= "/static/uploadImg/trainLogoImg/"+newFileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //转换为名师Model
        PageData trainPageData = new PageData();
        trainPageData.put("teruid", uuid);
        trainPageData.put("trainname",trainname);//培训名称
        trainPageData.put("traintime",traintime);//培训时间
        trainPageData.put("trainaddress",trainaddress);//培训地址
        trainPageData.put("phone",phone);//联系电话
        trainPageData.put("logoimg",trainLogImg);//logo 图片地址
        trainPageData.put("topflag",topflag);//是否置顶  0 否 1 2 3置顶顺序 ，不可以有同一顺序位
        trainPageData.put("price",price);//投标价格
        trainPageData.put("legtime",legtime);//有效期
        trainPageData.put("coursename",coursename);//培训课程名称
        trainPageData.put("trainclass",trainclass);//培训课时
        trainPageData.put("trainmethod",trainmethod);//培训方式
        trainPageData.put("traincontent",traincontent);//培训内容
        trainPageData.put("endtime",legtime);//培训截止时间
        trainPageData.put("creator",getUser().getFristname());//发布人
        trainPageData.put("createtime",getCurrentTime());//发布时间
        trainPageData.put("delflag",delflag);//删除标志 默认0
        //插入数据库
        try{
            if(trainService.addTrain(trainPageData)>0)
                ResultUtils.writeSuccess(response);
            else
                ResultUtils.writeFailed(response);

        } catch (Exception e) {
            logger.error("addTrainList e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    /*
    * 删除一条或多条记录
    * 多条记录ID用逗号分隔
    * */
    @RequestMapping("/delTrainList.do")
    public String delTrainList(HttpServletRequest request, HttpServletResponse response){
        logger.info("delTrainList");
        String jsonTxt = request.getParameter("ids") == null ? "" : request.getParameter("ids");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        String[] idStrs = jsonTxt.split(",");
        try{
            for (String fid:idStrs){
                trainService.delTrainByFid(fid);
            }
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("delTrainList e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    //修改更新一条记录
    @RequestMapping("/updateTrain.do")
    public String updateTrain(HttpServletRequest request, HttpServletResponse response){
        //获取提交参数
        logger.info("updateTrain");
        String strUid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        String trainname = request.getParameter("trainname") == null ? "" : request.getParameter("trainname");
        String traintime = request.getParameter("traintime") == null ? "" : request.getParameter("traintime");
        String trainaddress = request.getParameter("trainaddress") == null ? "" : request.getParameter("trainaddress");
        String phone = request.getParameter("phone") == null ? "" : request.getParameter("phone");
        String logoimg = request.getParameter("logoimg") == null ? "" : request.getParameter("logoimg");
        String topflag = request.getParameter("topflag") == null ? "-1" : request.getParameter("topflag");
        String price = request.getParameter("price") == null ? "0" : request.getParameter("price");
        String delflag = request.getParameter("delflag") == null ? "0" : request.getParameter("delflag");
        String legtime = request.getParameter("legtime") == null ? "" : request.getParameter("legtime");
        String coursename = request.getParameter("coursename") == null ? "" : request.getParameter("coursename");
        String trainclass = request.getParameter("trainclass") == null ? "" : request.getParameter("trainclass");
        String trainmethod = request.getParameter("trainmethod") == null ? "" : request.getParameter("trainmethod");
        String traincontent = request.getParameter("traincontent") == null ? "" : request.getParameter("traincontent");
        String endtime = request.getParameter("endtime") == null ? "" : request.getParameter("endtime");
        if(strUid.isEmpty()) {
            ResultUtils.writeFailed(response);
            return "";
        }
         if(topflag.equals("")) topflag="-1";
        MultiValueMap<String, MultipartFile> multFiles = ((DefaultMultipartHttpServletRequest)request).getMultiFileMap();
        String trainLogImg=logoimg;
        List<MultipartFile> files =multFiles.get("upload");
        String HOMEPATH = request.getSession().getServletContext().getRealPath("/") + "static/uploadImg/trainLogoImg/";
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
                    trainLogImg = "/static/uploadImg/trainLogoImg/" + newFileName;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //转换为名师Model
        PageData trainPageData = new PageData();
        trainPageData.put("teruid",strUid);
        trainPageData.put("trainname",trainname);//培训名称
        trainPageData.put("traintime",traintime);//培训时间
        trainPageData.put("trainaddress",trainaddress);//培训地址
        trainPageData.put("phone",phone);//联系电话
        trainPageData.put("logoimg",trainLogImg);//logo 图片地址
        trainPageData.put("topflag",topflag);//是否置顶  0 否 1 2 3置顶顺序 ，不可以有同一顺序位
        trainPageData.put("price",price);//投标价格
        trainPageData.put("legtime",legtime);//有效期
        trainPageData.put("coursename",coursename);//培训课程名称
        trainPageData.put("trainclass",trainclass);//培训课时
        trainPageData.put("trainmethod",trainmethod);//培训方式
        trainPageData.put("traincontent",traincontent);//培训内容
        trainPageData.put("endtime",legtime);//培训截止时间
        trainPageData.put("creator",getUser().getFristname());//发布人
        trainPageData.put("createtime",getCurrentTime());//发布时间
        trainPageData.put("delflag",delflag);//删除标志 默认0
        //插入数据库
        try{
            if(trainService.updateTrain(trainPageData)>0)
                ResultUtils.writeSuccess(response);
            else
                ResultUtils.writeFailed(response);

        } catch (Exception e) {
            logger.error("updateTrain e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
}
