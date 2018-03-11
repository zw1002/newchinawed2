package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Dealuidchild;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("dealuidchild")
public class DealuidchildServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addDealuidchild(PageData pageData) {
	 logger.info("增加Dealuidchild");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("DealuidchildMapper.addDealuidchild",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delDealuidchildByFid(String fid) {
	 logger.info("删除Dealuidchild");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("DealuidchildMapper.deleteDealuidchildByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateDealuidchild(PageData pageData) {
	 logger.info("修改Dealuidchild");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("DealuidchildMapper.updateDealuidchild",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Dealuidchild getDealuidchildforId(String fid) {
	 logger.info("通过ID查询Dealuidchild");
	Dealuidchild	dealuidchild=null;
	 try { 
		dealuidchild = (Dealuidchild) daoSupport.findForObject("DealuidchildMapper.getDealuidchildForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 dealuidchild=null; 
	}
	 return dealuidchild; 
	}
	public List<Dealuidchild> getAllDealuidchild(PageData pageData) {
	 logger.info("分页查询Dealuidchild");
	List<Dealuidchild>	dealuidchildList=null;
	 try { 
		dealuidchildList = (List<Dealuidchild>) daoSupport.findForList("DealuidchildMapper.getAllDealuidchild",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 dealuidchildList=null; 
	}
	 return dealuidchildList; 
	}
	public List<Dealuidchild> selectDealuidchildList() {
	 logger.info("查询所有Dealuidchild");
	List<Dealuidchild>	dealuidchildList=null;
	 try { 
		dealuidchildList = (List<Dealuidchild>) daoSupport.findForList("DealuidchildMapper.selectDealuidchildList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 dealuidchildList=null; 
	}
	 return dealuidchildList; 
	}
}
