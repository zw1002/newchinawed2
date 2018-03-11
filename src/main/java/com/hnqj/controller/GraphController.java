package com.hnqj.controller;

import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Account;
import com.hnqj.model.Graph;
import com.hnqj.model.Sysusermgr;
import com.hnqj.model.Userinfo;
import com.hnqj.services.GraphServices;
import com.hnqj.services.UserinfoServices;
import com.hnqj.services.WorksServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.hnqj.core.ResultUtils.toDateJson;
import static com.hnqj.core.ResultUtils.toDateTimeJson;
import static com.hnqj.core.ResultUtils.toJson;

@Controller
@RequestMapping("/graph")
public class GraphController extends  BaseController {
    @Autowired
    GraphServices graphServices;
    @Autowired
    UserinfoServices userinfoServices;

    /**
     * 跳转到求图管理页面
     * @return
     */
    @RequestMapping("/toGraphList.do")
    public String toGraphList(){
        return "deal_manager/graphList";
    }

    //初始化未审核求图数据
    @RequestMapping("/getGraphList.do")
    public String getGraphList(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("getGraphList");
        int offset = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int limit = request.getParameter("limit") == null ? 0 : Integer.parseInt(request.getParameter("limit"));
        int flag = request.getParameter("flag") == null ? 0 : Integer.parseInt(request.getParameter("flag"));
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",offset);
        pageData.put("limit",limit);
        pageData.put("flag",flag);
        List<Graph> list=graphServices.getAllGraph(pageData);
        List<Graph> listCount=graphServices.selectGraphList(pageData);
        tablereturn.setTotal(listCount.size());
        List<Map<String, Object>> hashMaps=new ArrayList<>();
        for(Graph graph:list){
            Map<String, Object> map = new HashMap<>();
            map.put("uid",graph.getUid());
            Userinfo userinfo=userinfoServices.getUserInfoByUid(graph.getUserid());
            map.put("username",userinfo.getFristname());
            map.put("graphtitle",graph.getGraphtitle());
            map.put("graphtype",graph.getGraphtype());
            map.put("graphclassification",graph.getGraphclassification());
            map.put("moneyreward",graph.getMoneyreward());
            map.put("addtime",graph.getAddtime());
            map.put("endtime",graph.getEndtime());
            hashMaps.add(map);
        }
        tablereturn.setRows(hashMaps);
        ResultUtils.write(response,toDateJson(tablereturn));
        return null;
    }

    /**
     * 求图审核
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/examineGraph.do")
    public String examineGraph(HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.info("examineGraph");
        String uid = request.getParameter("uid") == null ? "" : request.getParameter("uid");
        int statu = request.getParameter("statu") == null ? 0 : Integer.parseInt(request.getParameter("statu"));
        try{
            PageData pageData=new PageData();
            pageData.put("uid",uid);
            pageData.put("displayflag",statu);
            pageData.put("checkuser",getUser().getUid());
            pageData.put("chacktime",new Date());
            int number=graphServices.updateGraphState(pageData);
            if(number == 1){
                ResultUtils.writeSuccess(response);
            }else{
                ResultUtils.writeFailed(response);
            }
        }catch(Exception e){
            logger.error("examineGraph e="+e.getMessage());
            ResultUtils.writeFailed(response);
        }
        return null;
    }
}
