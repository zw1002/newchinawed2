package com.hnqj.controller;

import com.hnqj.core.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

  /*  @Resource(name="clientService")
    ClientService clientService;*/

  /*  @RequestMapping(value="index")
    public String getIndex(){
        return "index";
    }
*/
    @RequestMapping(value = "addClient")
    @ResponseBody
    public Map<String,Object> insertData(String username, String password){
        Map<String,Object> result = new HashMap<String,Object>();
        PageData pageData = new PageData();
        pageData.put("username",username);
        pageData.put("password",password);
       /* int iflag = clientService.addClientInfo(pageData);
        if(iflag==0){
            result.put("info","用户添加成功！");
        }else{
            result.put("info","用户添加失败！");
        }*/
        return result;
    }
}
