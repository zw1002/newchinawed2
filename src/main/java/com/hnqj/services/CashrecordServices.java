package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Cashrecord;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("cashrecord")
public class CashrecordServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addCashrecord(PageData pageData) {
	 logger.info("增加Cashrecord");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("CashrecordMapper.addCashrecord",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delCashrecordByFid(String fid) {
	 logger.info("删除Cashrecord");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("CashrecordMapper.deleteCashrecordByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateCashrecord(PageData pageData) {
	 logger.info("修改Cashrecord");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("CashrecordMapper.updateCashrecord",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Cashrecord getCashrecordforId(String fid) {
	 logger.info("通过ID查询Cashrecord");
	Cashrecord	cashrecord=null;
	 try { 
		cashrecord = (Cashrecord) daoSupport.findForObject("CashrecordMapper.getCashrecordForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 cashrecord=null; 
	}
	 return cashrecord; 
	}
	public List<Cashrecord> getAllCashrecord(PageData pageData) {
	 logger.info("分页查询Cashrecord");
	List<Cashrecord>	cashrecordList=null;
	 try { 
		cashrecordList = (List<Cashrecord>) daoSupport.findForList("CashrecordMapper.getAllCashrecord",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 cashrecordList=null; 
	}
	 return cashrecordList; 
	}
//	public List<Cashrecord> selectCashrecordList() {
//	 logger.info("查询所有Cashrecord");
//	List<Cashrecord>	cashrecordList=null;
//	 try {
//		cashrecordList = (List<Cashrecord>) daoSupport.findForList("CashrecordMapper.selectCashrecordList",null);
//	 }catch (Exception e){
//	 e.printStackTrace();
//	 cashrecordList=null;
//	}
//	 return cashrecordList;
//	}
}
