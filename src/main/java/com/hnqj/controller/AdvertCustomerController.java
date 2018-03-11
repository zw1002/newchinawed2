package com.hnqj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Client;
import com.hnqj.services.ClientServices;
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
 * 广告客户信息表
 */

@Controller
@RequestMapping("/adClientMgr")
public class AdvertCustomerController  extends  BaseController{

    @Autowired
    ClientServices clientService;
/*
     *跳转信息管理页面   /adClientMgr/toClientList.do
     * */
    @RequestMapping("/toClientList.do")
    public String toClientList(){
        return "advert_manager/toClientList";
    }

    //获取信息列表 /adClientMgr/getClientList.do
    @RequestMapping("/getClientList.do")
    public String getClientList(HttpServletRequest request, HttpServletResponse response){
        logger.info("getClientList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 10 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        List<Client> list=clientService.getAllClient(pageData);
        pageData.put("limit",0);
        List<Client> listCount=clientService.getAllClient(pageData);
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(list);
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }
    //添加一条记录
    /*
    **/
    @RequestMapping("/addClient.do")
    public String addClient(HttpServletRequest request, HttpServletResponse response, Model model){
        //获取提交参数
        logger.info("addClient");
        String clientname = request.getParameter("clientname") == null ? "" : request.getParameter("clientname");
        String phone = request.getParameter("phone") == null ? "" : request.getParameter("phone");
        String email = request.getParameter("email") == null ? "" : request.getParameter("email");
        String address = request.getParameter("address") == null ? "" : request.getParameter("address");

        //转换为作品Model
        PageData trainPageData = new PageData();
        trainPageData.put("clintuid", UUID.randomUUID().toString());
        trainPageData.put("clientname",clientname);//客户名称
        trainPageData.put("phone",phone);//电话
        trainPageData.put("email",email);//邮箱
        trainPageData.put("address",address);//联系地址
        trainPageData.put("creator",getUser().getFristname());//联系地址
        trainPageData.put("createtime",getCurrentTime());//联系地址

        //插入数据库
        try{
            if(clientService.addClient(trainPageData)>0)
                ResultUtils.writeSuccess(response);
            else
                ResultUtils.writeFailed(response);
        } catch (Exception e) {
            logger.error("addClient e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    @RequestMapping("/delClientList.do")
    public String delClientList(HttpServletRequest request, HttpServletResponse response){
        logger.info("delClientList");
        String jsonTxt = request.getParameter("ids") == null ? "" : request.getParameter("ids");
        if(jsonTxt.equals("")){
            ResultUtils.writeFailed(response);
        }
        String[] idStrs = jsonTxt.split(",");
        try{
            for (String fid:idStrs){
                clientService.delClientByFid(fid);
            }
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("delClientList e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }
    //修改更新一条记录
    @RequestMapping("/updateClient.do")
    public String updateClient(HttpServletRequest request, HttpServletResponse response){
        //获取提交参数
        logger.info("updateClient");
        String clientuid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        String clientname = request.getParameter("clientname") == null ? "" : request.getParameter("clientname");
        String phone = request.getParameter("phone") == null ? "" : request.getParameter("phone");
        String email = request.getParameter("email") == null ? "" : request.getParameter("email");
        String address = request.getParameter("address") == null ? "" : request.getParameter("address");

        //转换为作品Model
        PageData trainPageData = new PageData();
        trainPageData.put("clintuid", clientuid);
        trainPageData.put("clientname",clientname);//客户名称
        trainPageData.put("phone",phone);//电话
        trainPageData.put("email",email);//邮箱
        trainPageData.put("address",address);//联系地址
        trainPageData.put("creator",getUser().getFristname());//联系地址
        trainPageData.put("createtime",getCurrentTime());//联系地址
        //插入数据库
        try{
            if(clientService.updateClient(trainPageData)>0)
                ResultUtils.writeSuccess(response);
            else
                ResultUtils.writeFailed(response);
        } catch (Exception e) {
            logger.error("updateClient e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return "";
    }

}
