package com.hnqj.controller;

import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Distribution;
import com.hnqj.model.Limit;
import com.hnqj.model.Proportions;
import com.hnqj.services.AccountServices;
import com.hnqj.services.DistributionServices;
import com.hnqj.services.LimitServices;
import com.hnqj.services.ProportionsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.hnqj.core.ResultUtils.toDateJson;
import static com.hnqj.core.ResultUtils.toJson;

/**
 * 张威 2017/11
 * 分销关系
 */
@Controller
@RequestMapping("/distribution")
public class DistributionController extends  BaseController{
    @Autowired
    DistributionServices distributionServices;
    @Autowired
    ProportionsServices proportionsServices;
    /**
     * 新增分销管理记录
     */
    @RequestMapping("/addDistribution.do")
    public String addDistribution(HttpServletRequest request, HttpServletResponse response) {
        logger.info("addDistribution");
        String parentid = request.getParameter("parentid") == null ? "" : request.getParameter("parentid");
        String userseltid = request.getParameter("userseltid") == null ? "" : request.getParameter("userseltid");
        PageData pageData1 = new PageData();
        pageData1.put("dislevel","2");
        Proportions proportions=proportionsServices.getProportionsByLevel(pageData1);
        PageData pageData = new PageData();
        String uuid= UUID.randomUUID().toString();
        pageData.put("uid",uuid);
        pageData.put("parentid",parentid);
        pageData.put("userseltid",userseltid);
        pageData.put("distprod",proportions.getDistprod());
        try{
            Distribution distribution=distributionServices.getDistributionforParentId(parentid);
            if(distribution != null){//如果parentid之上还有parentid，添加三级分销记录
                PageData pageData2 = new PageData();
                pageData2.put("dislevel","3");
                Proportions proportions2=proportionsServices.getProportionsByLevel(pageData2);
                PageData pageData3 = new PageData();
                pageData3.put("uid",UUID.randomUUID().toString());
                pageData3.put("parentid",distribution.getUid());
                pageData3.put("userseltid",userseltid);
                pageData3.put("distprod",proportions2.getDistprod());
                distributionServices.addDistribution(pageData3);
            }
            distributionServices.addDistribution(pageData);
            ResultUtils.writeSuccess(response);
        } catch (Exception e) {
            logger.error("addDistribution e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }

    }
