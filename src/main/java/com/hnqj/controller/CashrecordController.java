package com.hnqj.controller;

import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Cashrecord;
import com.hnqj.services.CashrecordServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.hnqj.core.ResultUtils.toJson;

/**
 * Created by nyw on 2017/12/02.
 * 提现审核控制类
 */

@Controller
@RequestMapping("/cashMgr")
public class CashrecordController extends  BaseController{

    @Autowired
    CashrecordServices dealService;
    /*
         *跳转信息管理页面   /cashMgr/toCashrecordList.do
         * */
    @RequestMapping("/toCashrecordList.do")
    public String toOrderList(){
        return "deal_manager/toWithdrawalList";
    }

    //获取信息列表
    @RequestMapping("/getCashList.do")
    public String getCashList(HttpServletRequest request, HttpServletResponse response){
        logger.info("getCashList");
        String cashState = request.getParameter("cashstate") == null ? "" : request.getParameter("cashstate");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 20 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        pageData.put("cashstate",cashState);
        List<Cashrecord> list=dealService.getAllCashrecord(pageData);
        pageData.put("limit",0);
        List<Cashrecord> listCount=dealService.getAllCashrecord(pageData);
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(list);
        ResultUtils.write(response,toJson(tablereturn));
        return null;
    }
    //更新审核结果
    @RequestMapping("/checkCash.do")
    public String checkCash(HttpServletRequest request, HttpServletResponse response){
        logger.info("checkCash");
        String cashuid = request.getParameter("cashuid") == null ? "" : request.getParameter("cashuid");
        String cashstate = request.getParameter("cashstate") == null ? "0" : request.getParameter("cashstate");
        try {
            PageData pageData = new PageData();
            pageData.put("cashuid", cashuid);
            pageData.put("cashstate", cashstate);
            pageData.put("checkpeople", getUser().getUid());
            pageData.put("checktime", getCurrentTime());

            if(dealService.updateCashrecord(pageData)>0)
                ResultUtils.writeSuccess(response);
            else
                ResultUtils.writeFailed(response);

        }
        catch (Exception ee)
        {
            ResultUtils.writeFailed(response);
        }
        return null;
    }
}
