package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Leavemsg;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("leavemsg")
public class LeavemsgServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addLeavemsg(PageData pageData) {
	 logger.info("增加Leavemsg");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("LeavemsgMapper.addLeavemsg",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delLeavemsgByFid(String fid) {
	 logger.info("删除Leavemsg");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("LeavemsgMapper.deleteLeavemsgByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateLeavemsg(PageData pageData) {
	 logger.info("修改Leavemsg");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("LeavemsgMapper.updateLeavemsg",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Leavemsg getLeavemsgforId(String fid) {
	 logger.info("通过ID查询Leavemsg");
	Leavemsg	leavemsg=null;
	 try { 
		leavemsg = (Leavemsg) daoSupport.findForObject("LeavemsgMapper.getLeavemsgForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 leavemsg=null; 
	}
	 return leavemsg; 
	}
	public List<Leavemsg> getAllLeavemsg(PageData pageData) {
	 logger.info("分页查询Leavemsg");
	List<Leavemsg>	leavemsgList=null;
	 try { 
		leavemsgList = (List<Leavemsg>) daoSupport.findForList("LeavemsgMapper.getAllLeavemsg",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 leavemsgList=null; 
	}
	 return leavemsgList; 
	}
	public List<Leavemsg> selectLeavemsgList() {
	 logger.info("查询所有Leavemsg");
	List<Leavemsg>	leavemsgList=null;
	 try { 
		leavemsgList = (List<Leavemsg>) daoSupport.findForList("LeavemsgMapper.selectLeavemsgList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 leavemsgList=null; 
	}
	 return leavemsgList; 
	}
}
