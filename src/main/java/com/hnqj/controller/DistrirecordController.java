package com.hnqj.controller;

import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.*;
import com.hnqj.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

import static com.hnqj.core.ResultUtils.toDateJson;

/**
 * 张威 2017/11
 * 分销记录控制层
 */
@Controller
@RequestMapping("/distrirecord")
public class DistrirecordController extends  BaseController{
    @Autowired
    DistrirecordServices distrirecordServices;
    @Autowired
    WorksServices worksServices;
    @Autowired
    UserinfoServices userinfoServices;
    @Autowired
    AccountServices accountServices;
    /**
     * 获取所有分销记录
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/getDistrirecordList.do")
    public String getDistrirecordList(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("getDistrirecordList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 50 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        List<Distrirecord> list=distrirecordServices.getAllDistrirecord(pageData);
        List<Map<String, Object>> hashMaps=new ArrayList<>();
        for(Distrirecord distrirecord:list){
            Map<String, Object> map = new HashMap<>();
            Works works=worksServices.getWorksforId(distrirecord.getWorksid());
            map.put("workname",works.getWorksname());
            Account account=accountServices.getAccountByRecord(distrirecord.getReferee());
            Userinfo userinfo=userinfoServices.getUserInfoByUid(account.getUserid());
            map.put("parentname",userinfo.getFristname());
            map.put("level",distrirecord.getLevel());
            Userinfo userinfo1=userinfoServices.getUserInfoByUid(distrirecord.getUserid());
            map.put("username",userinfo1.getFristname());
            map.put("proportion",distrirecord.getProportion()+"%");
            double f3 = new BigDecimal(distrirecord.getProportion()/100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            double f4 =f3 * Double.parseDouble(String.valueOf(works.getPrice()));
            map.put("price",f4);
            hashMaps.add(map);
        }
        List<Distrirecord> listCount=distrirecordServices.selectDistrirecordList();
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(hashMaps);
        ResultUtils.write(response,toDateJson(tablereturn));
        return null;
    }

    }
