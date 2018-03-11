package com.hnqj.controller;

import com.hnqj.core.PageData;
import com.hnqj.core.ResultUtils;
import com.hnqj.core.TableReturn;
import com.hnqj.model.Dealrecord;
import com.hnqj.model.Merch;
import com.hnqj.model.Userinfo;
import com.hnqj.model.Works;
import com.hnqj.services.DealrecordServices;
import com.hnqj.services.MerchServices;
import com.hnqj.services.UserinfoServices;
import com.hnqj.services.WorksServices;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.hnqj.core.ResultUtils.toDateJson;
import static com.hnqj.core.ResultUtils.toJson;

/**
 * Created by nyw on 2017/12/02.
 * 交易订单控制类
 */

@Controller
@RequestMapping("/dealOrderMgr")
public class DealOrderController  extends  BaseController{
    @Autowired
    DealrecordServices dealService;
    @Autowired
    UserinfoServices userinfoServices;
    @Autowired
    WorksServices worksServices;
    @Autowired
    MerchServices merchServices;
    /*
         *跳转信息管理页面   /dealOrderMgr/toDealList.do
         * */
    @RequestMapping("/toDealList.do")
    public String toOrderList(){
        return "deal_manager/toOrderList";
    }

    //获取信息列表
    @RequestMapping("/getDealorderList.do")
    public String getDealorderList(HttpServletRequest request, HttpServletResponse response){
        logger.info("getDealorderList");
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 50 : Integer.parseInt(request.getParameter("limit"));
        int flag = request.getParameter("flag") == null ? 0 : Integer.parseInt(request.getParameter("flag"));
        String creatTime = request.getParameter("creatTime") == null ? "" : request.getParameter("creatTime");
        String creatTimes = request.getParameter("creatTimes") == null ? "" : request.getParameter("creatTimes");
        TableReturn tablereturn =new TableReturn();
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        pageData.put("flag",flag);
        pageData.put("creatTime",creatTime);
        pageData.put("creatTimes",creatTimes);
        List<Dealrecord> list=dealService.getAllDealrecord(pageData);
        List<Dealrecord> listCount=dealService.selectDealrecordList(pageData);
        List<Map<String, Object>> hashMaps=new ArrayList<>();
        for(Dealrecord dealrecord:list){
            Map<String, Object> map = new HashMap<>();
            map.put("dealuid",dealrecord.getDealuid());
            Userinfo userinfo=userinfoServices.getUserInfoByUid(dealrecord.getPayuserid());
            String [] workstr=dealrecord.getBusinesid().split(",");
            String str="";
            for(int i=0;i<workstr.length;i++){
                str += worksServices.getWorksforId(workstr[i]).getWorksname()+",";
            }
            map.put("payuser",userinfo.getFristname());
            map.put("workname",str);
            map.put("dealtime",dealrecord.getDealtime());
            map.put("dealprice",dealrecord.getDealprice());
            if(dealrecord.getDealstate().equalsIgnoreCase("0")){
                map.put("dealstate","进行中");
            }else{
                map.put("dealstate","已完成");
            }
            hashMaps.add(map);
        }
        tablereturn.setTotal(listCount.size());
        tablereturn.setRows(hashMaps);
        ResultUtils.write(response,toDateJson(tablereturn));
        return null;
    }
    /**
     * 导出订单明细
     * @param request
     * @param response
     */
    @RequestMapping("/exportExcelDealorderList.do")
    public void exportExcelDealorderList(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 500000 : Integer.parseInt(request.getParameter("limit"));
        int flag = request.getParameter("flag") == null ? 0 : Integer.parseInt(request.getParameter("flag"));
        String creatTime = request.getParameter("creatTime") == null ? "" : request.getParameter("creatTime");
        String creatTimes = request.getParameter("creatTimes") == null ? "" : request.getParameter("creatTimes");
        String filename=creatTime+"至"+creatTimes+"订单信息明细";
        PageData pageData = new PageData();
        pageData.put("offset",currentPage);
        pageData.put("limit",showCount);
        pageData.put("flag",flag);
        pageData.put("creatTime",creatTime);
        pageData.put("creatTimes",creatTimes);
        List<Dealrecord> list=dealService.getAllDealrecord(pageData);
        HSSFWorkbook wb = new HSSFWorkbook();//创建excel工作簿
        HSSFSheet sheet = wb.createSheet("Sheet");//在Excel工作簿中建一工作表，其名为缺省值, 也可以指定Sheet名称，例如Sheet
        HSSFCellStyle style = wb.createCellStyle();//生成一个样式
        HSSFFont font = wb.createFont(); // 生成一个字体
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        //style.setWrapText(true); // 自动换行
        font.setFontHeightInPoints((short) 14);
        font.setFontName("宋体");//设置字体
        style.setFont(font);// 把字体样式 应用到当前样式
        HSSFRow row = sheet.createRow((int) 0);
        CellRangeAddress cra=new CellRangeAddress(0, 0, 0, 6);//合并
        sheet.addMergedRegion(cra);//合并第一行1到10
        //表头
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(filename);
        cell.setCellStyle(style);
        row = sheet.createRow(1);//设置表头起始行
        mycreateCell(row,0,"序号",style);
        mycreateCell(row,1,"订单号",style);
        mycreateCell(row,2,"购买人",style);
        mycreateCell(row,3,"作品信息",style);
        mycreateCell(row,4,"交易时间",style);
        mycreateCell(row,5,"交易金额",style);
        mycreateCell(row,6,"交易状态",style);
        for (int i = 0; i < list.size(); i++)
        {
            int j = i+2;//设置数据起始行
            row = sheet.createRow(j);
            //设值
            mycreateCell(row,0,i,style);
            mycreateCell(row,1,String.valueOf(list.get(i).getDealuid()),style);
            Userinfo userinfo=userinfoServices.getUserInfoByUid(list.get(i).getPayuserid());
            mycreateCell(row,2,String.valueOf(userinfo.getFristname()),style);
            String [] workstr=list.get(i).getBusinesid().split(",");
            String str="";
            for(int k=0;k<workstr.length;k++){
                str += worksServices.getWorksforId(workstr[k]).getWorksname()+",";
            }
            mycreateCell(row,3,String.valueOf(str),style);
            SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mycreateCell(row,4,sdf2.format(sdf1.parse(String.valueOf(list.get(i).getDealtime()))),style);
            mycreateCell(row,5,String.valueOf(list.get(i).getDealprice()),style);
            if(list.get(i).getDealstate().equalsIgnoreCase("0")){
                mycreateCell(row,6,String.valueOf("进行中"),style);
            }else{
                mycreateCell(row,6,String.valueOf("已完成"),style);
            }
            sheet.autoSizeColumn((short)0); //调整第一列宽度，自适应
            sheet.autoSizeColumn((short)1); //调整第二列宽度，自适应
            sheet.autoSizeColumn((short)2); //调整第三列宽度，自适应
            sheet.autoSizeColumn((short)3); //调整第四列宽度，自适应
            sheet.autoSizeColumn((short)4); //调整第五列宽度，自适应
            sheet.autoSizeColumn((short)5); //调整第五列宽度，自适应
            sheet.autoSizeColumn((short)6); //调整第五列宽度，自适应
        }
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.reset();// 清空输出流
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=\""
                    + new String((filename + ".xls").getBytes("GBK"),
                    "ISO8859_1") + "\"");
            OutputStream ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        }catch (Exception e) {
            logger.error("exportExcelDealorderList e=" + e.getMessage());
            ResultUtils.writeFailed(response);
        }
    }
    //设置表格数据格式--字符串
    public static void mycreateCell(HSSFRow row,int i,String value,HSSFCellStyle style)
    {
        HSSFCell cell = row.createCell(i);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
    //设置表格数据格式--字符串
    public static void mycreateCell(HSSFRow row, int i, Date value, HSSFCellStyle style)
    {
        HSSFCell cell = row.createCell(i);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
    //数值型
    public void mycreateCell(HSSFRow row,int i,double value,HSSFCellStyle style)
    {
        HSSFCell cell = row.createCell(i);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
    //整型
    public static void mycreateCell(HSSFRow row, int i, Integer value, HSSFCellStyle style)
    {
        HSSFCell cell = row.createCell(i);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
}
