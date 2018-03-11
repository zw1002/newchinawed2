package com.hnqj.controller;

import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.*;
import com.hnqj.services.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.hnqj.core.ResultUtils.toDateJson;
import static com.hnqj.core.ResultUtils.toDateTimeJson;
import static com.hnqj.core.ResultUtils.toJson;

/**
 * 张威 2017/11
 * 商户管理
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController extends  BaseController{
    @Autowired
    MerchServices merchServices;
    @Autowired
    MerchcycleServices merchcycleServices;
    @Autowired
    UserinfoServices userinfoServices;
    @Autowired
    IntegralServices integralServices;
    @Autowired
    MerchdownServices merchdownServices;
    /**
     * 跳转到商户管理页面
     * @return
     */
    @RequestMapping("/toMerchantList.do")
    public String toMerchantList(){
        return "merchant/merchantlist";
    }
    /**
     * 跳转到申请商户管理页面
     * @return
     */
    @RequestMapping("/toApplyMerchantList.do")
    public String toApplyMerchantList(){
        return "merchant/applymerchantlist";
    }
    /**
     * 跳转到审核商户管理页面
     * @return
     */
    @RequestMapping("/toExamineMerchantList.do")
    public String toExamineMerchantList(HttpServletRequest request, Model model){
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        String statu = request.getParameter("statu") == null ? "" : request.getParameter("statu");
        model.addAttribute("id",id);//商户Id传到页面
        model.addAttribute("statu",statu);//商户Id传到页面
        return "merchant/examinemerchant";
    }

    /**
     * 跳转到商户降级记录页面
     * @return
     */
    @RequestMapping("/toMerchDownList.do")
    public String toMerchDownList(){
        return "merchant/merchdownlist";
    }

    /**
     * 跳转到结算管理页面
     * @return
     */
    @RequestMapping("/toSettlementList.do")
    public String toSettlementList(){
        return "merchant/settlementlist";
    }

    /**
     * 初始化数据
     */
    @RequestMapping("/getMerchantList.do")
    public String getMerchantList(HttpServletRequest request, HttpServletResponse response) {
        logger.info("getMerchantList");
        String statu = request.getParameter("statu") == null ? "" : request.getParameter("statu");
        String status = request.getParameter("status") == null ? "" : request.getParameter("status");
        String grade = request.getParameter("grade") == null ? "" : request.getParameter("grade");
        String merchname = request.getParameter("merchname") == null ? "" : request.getParameter("merchname");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 10 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        try{
            List<Merch> list=null;
            List<Merch> listCount=null;
            List<Map<String, Object>> hashMaps=new ArrayList<>();
            if(statu.equalsIgnoreCase("0")){//获取申请列表
                list= merchServices.getApplyMerch(pageData);
                listCount=merchServices.selectApplyMerchList();
            }else if(statu.equalsIgnoreCase("1")){//商户管理
                list= merchServices.getAllMerch(pageData);
                listCount=merchServices.selectMerchList();
            }else{//根据条件商户搜索
                PageData page = new PageData();
                page.put("offset",currentPage);
                page.put("limit",showCount);
                if(status.equalsIgnoreCase("0")){
                    page.put("statu","");
                }else{
                    page.put("statu",status);
                }
                if(!grade.equalsIgnoreCase("0")){
                    Integral integral=integralServices.selectIntegralByGrade(grade);
                    page.put("grade",grade);
                    page.put("mininum",integral.getMininum());
                    page.put("maxinum",integral.getMaxinum());
                }else{
                    page.put("grade","");
                }
                page.put("merchname",merchname);
                list= merchServices.getAllMerchByCondition(page);
                listCount=merchServices.selectMerchListByCondition();
            }
            for(Merch merch:list){
                Map<String, Object> map = new HashMap<>();
                map.put("uid",merch.getUid());
                map.put("uids",merch.getUid());
                map.put("merchname",merch.getMerchname());
                map.put("userinfouid",userinfoServices.getUserInfoByUid(merch.getUserinfouid()).getFristname());
                map.put("bondvalue",merch.getBondvalue());
                map.put("builddatetime",merch.getBuilddatetime());
                PageData pageDatas = new PageData();
                pageDatas.put("scroe",merch.getMerchscroe());
                Integral integral=integralServices.selectIntegralByScroe(pageDatas);
                if(integral.getGrade() == 1){
                    map.put("merchscroe","初级");
                }else if(integral.getGrade() == 2){
                    map.put("merchscroe","中级");
                }else if(integral.getGrade() == 3){
                    map.put("merchscroe","高级");
                }else{
                    map.put("merchscroe","特级");
                }
                map.put("statu",merch.getStatu());
                if(merch.getStatu() == 0){
                    map.put("status","待审核");
                }else if(merch.getStatu() == 1){
                    map.put("status","审核通过");
                }else if(merch.getStatu() == 2){
                    map.put("status","审核不通过");
                }else{
                    map.put("status","已冻结");
                }
                hashMaps.add(map);
            }
            tablereturn.setTotal(listCount.size());
            tablereturn.setRows(hashMaps);
            ResultUtils.write(response,toDateJson(tablereturn));
        }catch(Exception e){
            logger.error("applyShop e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }

    /**
     * 商户审核初始化数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getExamineMerchantList.do")
    public String getExamineMerchantList(HttpServletRequest request, HttpServletResponse response) {
        logger.info("getExamineMerchantList");
        String merchId = request.getParameter("merchId") == null ? "" : request.getParameter("merchId");
        List<Map<String, Object>> hashMaps=new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        try{
            Merch merch=merchServices.getMerchforId(merchId);
            map.put("uid",merch.getUid());
            map.put("merchname",merch.getMerchname());
            Userinfo userinfo=userinfoServices.getUserInfoByUid(merch.getUserinfouid());
            if(!StringUtils.isBlank(userinfo.getBankaddr())){
                String[] banks=userinfo.getBankaddr().split(",");
                map.put("bankaddress",banks[0]);
                map.put("bankname",banks[1]);
                map.put("bankaccount",userinfo.getBankcode());
            }else{
                map.put("bankaddress","");
                map.put("bankname","");
                map.put("bankaccount","");
            }
            if(!StringUtils.isBlank(userinfo.getIdpic())){
                map.put("idpic",userinfo.getIdpic());
            }else{
                map.put("idpic","");
            }
            map.put("username",userinfo.getFristname());
            map.put("userIcCode",userinfo.getIccode());
            map.put("telephone",userinfo.getTelephone());
            map.put("bondvalue",merch.getBondvalue());
            map.put("builddatetime",merch.getBuilddatetime());
            PageData pageDatas = new PageData();
            pageDatas.put("scroe",merch.getMerchscroe());
            Integral integral=integralServices.selectIntegralByScroe(pageDatas);
            if(integral.getGrade() == 1){
                map.put("merchscroe","初级");
            }else if(integral.getGrade() == 2){
                map.put("merchscroe","中级");
            }else if(integral.getGrade() == 3){
                map.put("merchscroe","高级");
            }else{
                map.put("merchscroe","特级");
            }
            map.put("merchscroes",merch.getMerchscroe());
            map.put("statu",merch.getStatu());
            hashMaps.add(map);
            ResultUtils.write(response,toDateJson(hashMaps));
        }catch(Exception e){
            logger.error("getExamineMerchantList e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 开店申请
     */
    @RequestMapping("/applyShop.do")
        public String applyShop(HttpServletRequest request, HttpServletResponse response) {
        logger.info("applyShop");
        String merchname = request.getParameter("merchname") == null ? "" : request.getParameter("merchname");
        String userinfouid = request.getParameter("userinfouid") == null ? "" : request.getParameter("userinfouid");
        String bondvalue = request.getParameter("bondvalue") == null ? "" : request.getParameter("bondvalue");
        String builddatetime = request.getParameter("builddatetime") == null ? "" : request.getParameter("builddatetime");
        String merchscroe = request.getParameter("merchscroe") == null ? "" : request.getParameter("merchscroe");
        PageData pageData=new PageData();
        pageData.put("uid", UUID.randomUUID().toString());
        pageData.put("merchname",merchname);
        pageData.put("userinfouid",userinfouid);
        pageData.put("bondvalue",bondvalue);
        pageData.put("builddatetime",builddatetime);
        pageData.put("merchscroe",merchscroe);
        pageData.put("statu",0);
        try{
            merchServices.addMerch(pageData);
            ResultUtils.writeSuccess(response);
        }catch(Exception e){
            logger.error("applyShop e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 店铺状态修改--审核或者冻结
     */
    @RequestMapping("/examineOrFrozen.do")
    public String examineOrFrozen(HttpServletRequest request, HttpServletResponse response) {
        logger.info("examineOrFrozen");
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        String statu = request.getParameter("statu") == null ? "" : request.getParameter("statu");
        PageData pageData=new PageData();
        pageData.put("uid",uid);
        pageData.put("statu",statu);
        try{
            merchServices.updateMerchStatu(pageData);
            ResultUtils.writeSuccess(response);
        }catch(Exception e){
            logger.error("examineOrFrozen e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 店铺信息修改
     */
    @RequestMapping("/updateShop.do")
    public String updateShop(HttpServletRequest request, HttpServletResponse response) {
        logger.info("updateShop");
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        String merchname = request.getParameter("merchname") == null ? "" : request.getParameter("merchname");
        PageData pageData=new PageData();
        pageData.put("uid",uid);
        pageData.put("merchname",merchname);
        Merch merch=merchServices.getMerchforId(uid);
        if(merch.getStatu() == 2){
            pageData.put("statu",0);
        }else{
            pageData.put("statu",merch.getStatu());
        }
        try{
            merchServices.updateMerch(pageData);
            ResultUtils.writeSuccess(response);
        }catch(Exception e){
            logger.error("applyShop e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }

    /**
     * 降级
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downgrade.do")
    public String downgrade(HttpServletRequest request, HttpServletResponse response) {
        logger.info("downgrade");
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        String delmerchscroe = request.getParameter("delmerchscroe") == null ? "" : request.getParameter("delmerchscroe");
        String merchscroes = request.getParameter("merchscroes") == null ? "" : request.getParameter("merchscroes");
        try{
            int merchscroe= Integer.parseInt(merchscroes)-Integer.parseInt(delmerchscroe);
            //修改店铺的积分值
            PageData pageData=new PageData();
            pageData.put("uid",id);
            pageData.put("merchscroe",merchscroe);
            merchServices.updateMerchScore(pageData);
            //向降级表插入降级记录
            PageData pageDatas=new PageData();
            pageDatas.put("uid",UUID.randomUUID().toString());
            pageDatas.put("merchuid",id);
            pageDatas.put("merchscore",delmerchscroe);
            pageDatas.put("downdatetime",new Date());
            pageDatas.put("befermerchscore",merchscroes);
            pageDatas.put("aftermerchscore",merchscroe);
            merchdownServices.addMerchdown(pageDatas);
            ResultUtils.writeSuccess(response);
        }catch(Exception e){
            logger.error("downgrade e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 初始化商户降级列表
     */
    @RequestMapping("/getMerchDownList.do")
    public String getMerchDownList(HttpServletRequest request, HttpServletResponse response) {
        logger.info("getMerchDownList");
        String merchname = request.getParameter("merchname") == null ? "" : request.getParameter("merchname");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 10 : Integer.parseInt(request.getParameter("limit"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        pageData.put("merchname",merchname);
        try{
            List<Map<String, Object>> hashMaps=new ArrayList<>();
            List<Merchdown> list= merchdownServices.getAllMerchdown(pageData);
            List<Merchdown> listCount=merchdownServices.selectMerchdownList(pageData);
            for(Merchdown merchdown:list){
                Map<String, Object> map = new HashMap<>();
                map.put("uid",merchdown.getUid());
                map.put("merchname",merchServices.getMerchforId(merchdown.getMerchuid()).getMerchname());
                map.put("downdatetime",merchdown.getDowndatetime());
                map.put("befermerchscore",merchdown.getBefermerchscore());
                map.put("merchscore",merchdown.getMerchscore());
                map.put("aftermerchscore",merchdown.getAftermerchscore());
                hashMaps.add(map);
            }
            tablereturn.setTotal(listCount.size());
            tablereturn.setRows(hashMaps);
            ResultUtils.write(response,toDateTimeJson(tablereturn));
        }catch(Exception e){
            logger.error("getMerchDownList e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
    /**
     * 审核时修改商户的抽成周期
     */
    @RequestMapping("/updateCycle.do")
    public String updateCycle(HttpServletRequest request, HttpServletResponse response) {
        logger.info("updateCycle");
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        String cycle = request.getParameter("cycle") == null ? "" : request.getParameter("cycle");
        PageData pageData=new PageData();
        pageData.put("uid",uid);
        pageData.put("cycle",cycle);
        try{
            merchServices.updateCycle(pageData);
            ResultUtils.writeSuccess(response);
        }catch(Exception e){
            logger.error("updateCycle e="+e.getMessage());
        ResultUtils.writeFailed(response);
        }
        return null;
    }
    //初始化商户结算数据
    @RequestMapping("/getSettlementList.do")
    public String getSettlementList(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("getSettlementList");
        int offset = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int limit = request.getParameter("limit") == null ? 0 : Integer.parseInt(request.getParameter("limit"));
        String merchname = request.getParameter("merchname") == null ? "" : request.getParameter("merchname");
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",offset);
        pageData.put("limit",limit);
        pageData.put("merchname",merchname);
        List<Merchcycle> list=merchcycleServices.getAllMerchcycle(pageData);
        List<Merchcycle> listCount=merchcycleServices.selectMerchcycleList(pageData);
        tablereturn.setTotal(listCount.size());
        List<Map<String, Object>> hashMaps=new ArrayList<>();
        for(Merchcycle merchcycle:list){
            Map<String, Object> map = new HashMap<>();
            map.put("uid",merchcycle.getUid());
            map.put("accountprice",merchcycle.getAccountprice());
            map.put("addtime",merchcycle.getAddtime());
            map.put("prevture",merchcycle.getPrevture());
            map.put("fcbl",merchcycle.getFcbl());
            map.put("merchname",merchServices.getMerchforId(merchcycle.getMerchid()).getMerchname());
            hashMaps.add(map);
        }
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(hashMaps);
        ResultUtils.write(response,toDateTimeJson(tablereturn));
        return null;
    }

    }
