package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Worklabel;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("worklabel")
public class WorklabelServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addWorklabel(PageData pageData) {
	 logger.info("增加Worklabel");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("WorklabelMapper.addWorklabel",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delWorklabelByFid(String fid) {
	 logger.info("删除Worklabel");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("WorklabelMapper.deleteWorklabelByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateWorklabel(PageData pageData) {
	 logger.info("修改Worklabel");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("WorklabelMapper.updateWorklabel",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Worklabel getWorklabelforId(String fid) {
	 logger.info("通过ID查询Worklabel");
	Worklabel	worklabel=null;
	 try { 
		worklabel = (Worklabel) daoSupport.findForObject("WorklabelMapper.getWorklabelForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 worklabel=null; 
	}
	 return worklabel; 
	}
	public List<Worklabel> getAllWorklabel(PageData pageData) {
	 logger.info("分页查询Worklabel");
	List<Worklabel>	worklabelList=null;
	 try { 
		worklabelList = (List<Worklabel>) daoSupport.findForList("WorklabelMapper.getAllWorklabel",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 worklabelList=null; 
	}
	 return worklabelList; 
	}
//	public List<Worklabel> selectWorklabelList() {
//	 logger.info("查询所有Worklabel");
//	List<Worklabel>	worklabelList=null;
//	 try {
//		worklabelList = (List<Worklabel>) daoSupport.findForList("WorklabelMapper.selectWorklabelList",null);
//	 }catch (Exception e){
//	 e.printStackTrace();
//	 worklabelList=null;
//	}
//	 return worklabelList;
//	}
}
