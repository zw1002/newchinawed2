package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Merch;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("merch")
public class MerchServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addMerch(PageData pageData) {
	 logger.info("增加Merch");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("MerchMapper.addMerch",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delMerchByFid(String fid) {
	 logger.info("删除Merch");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("MerchMapper.deleteMerchByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateMerch(PageData pageData) {
	 logger.info("修改Merch");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("MerchMapper.updateMerch",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Merch getMerchforId(String uid) {
	 logger.info("通过ID查询Merch");
	Merch	merch=null;
	 try { 
		merch = (Merch) daoSupport.findForObject("MerchMapper.getMerchForId",uid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 merch=null; 
	}
	 return merch; 
	}
	public List<Merch> getAllMerch(PageData pageData) {
	 logger.info("分页查询Merch");
	List<Merch>	merchList=null;
	 try { 
		merchList = (List<Merch>) daoSupport.findForList("MerchMapper.getAllMerch",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 merchList=null; 
	}
	 return merchList; 
	}
	public List<Merch> selectMerchList() {
	 logger.info("查询所有Merch");
	List<Merch>	merchList=null;
	 try { 
		merchList = (List<Merch>) daoSupport.findForList("MerchMapper.selectMerchList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 merchList=null; 
	}
	 return merchList; 
	}

	/**
	 * 审核或者冻结商户
	 * @param pageData
     */
	public int updateMerchStatu(PageData pageData) {
		logger.info("审核或者冻结商户");
		int iFlag = 0;
		try {
			iFlag = (int) daoSupport.update("MerchMapper.updateMerchStatu", pageData);
		} catch (Exception e) {
			e.printStackTrace();
			iFlag = 0;
		}
		return iFlag;
	}

	public List<Merch> getApplyMerch(PageData pageData) {
		logger.info("分页查询所有申请Merch");
		List<Merch>	merchList=null;
		try {
			merchList = (List<Merch>) daoSupport.findForList("MerchMapper.getApplyMerch",pageData);
		}catch (Exception e){
			e.printStackTrace();
			merchList=null;
		}
		return merchList;
	}

	public List<Merch> selectApplyMerchList() {
		logger.info("查询所有申请Merch");
		List<Merch>	merchList=null;
		try {
			merchList = (List<Merch>) daoSupport.findForList("MerchMapper.selectApplyMerchList",null);
		}catch (Exception e){
			e.printStackTrace();
			merchList=null;
		}
		return merchList;
	}

	public List<Merch> getAllMerchByCondition(PageData page) {
		logger.info("根据条件分页搜索商户");
		List<Merch>	merchList=null;
		try {
			merchList = (List<Merch>) daoSupport.findForList("MerchMapper.getAllMerchByCondition",page);
		}catch (Exception e){
			e.printStackTrace();
			merchList=null;
		}
		return merchList;
	}

	public List<Merch> selectMerchListByCondition() {
		logger.info("根据条件搜索商户");
		List<Merch>	merchList=null;
		try {
			merchList = (List<Merch>) daoSupport.findForList("MerchMapper.selectMerchListByCondition",null);
		}catch (Exception e){
			e.printStackTrace();
			merchList=null;
		}
		return merchList;
	}

	public int updateMerchScore(PageData pageData) {
		logger.info("修改积分");
		int iFlag =0;
		try {
			iFlag = (int) daoSupport.update("MerchMapper.updateMerchScore",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}

	public int updateCycle(PageData pageData) {
		logger.info("修改结算周期");
		int iFlag =0;
		try {
			iFlag = (int) daoSupport.update("MerchMapper.updateCycle",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}
}
