package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Dealrecord;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("dealrecord")
public class DealrecordServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addDealrecord(PageData pageData) {
	 logger.info("增加Dealrecord");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("DealrecordMapper.addDealrecord",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delDealrecordByFid(String fid) {
	 logger.info("删除Dealrecord");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("DealrecordMapper.deleteDealrecordByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateDealrecord(PageData pageData) {
	 logger.info("修改Dealrecord");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("DealrecordMapper.updateDealrecord",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Dealrecord getDealrecordforId(String fid) {
	 logger.info("通过ID查询Dealrecord");
	Dealrecord	dealrecord=null;
	 try { 
		dealrecord = (Dealrecord) daoSupport.findForObject("DealrecordMapper.getDealrecordForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 dealrecord=null; 
	}
	 return dealrecord; 
	}
	public List<Dealrecord> getAllDealrecord(PageData pageData) {
	 logger.info("分页查询Dealrecord");
	List<Dealrecord>	dealrecordList=null;
	 try { 
		dealrecordList = (List<Dealrecord>) daoSupport.findForList("DealrecordMapper.getAllDealrecord",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 dealrecordList=null; 
	}
	 return dealrecordList; 
	}
	public List<Dealrecord> selectDealrecordList(PageData pageData) {
	 logger.info("查询所有Dealrecord");
	List<Dealrecord>	dealrecordList=null;
	 try { 
		dealrecordList = (List<Dealrecord>) daoSupport.findForList("DealrecordMapper.selectDealrecordList",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 dealrecordList=null; 
	}
	 return dealrecordList; 
	}
}
