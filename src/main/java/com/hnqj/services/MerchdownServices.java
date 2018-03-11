package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Merchdown;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("merchdown")
public class MerchdownServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addMerchdown(PageData pageData) {
	 logger.info("增加Merchdown");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("MerchdownMapper.addMerchdown",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delMerchdownByFid(String fid) {
	 logger.info("删除Merchdown");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("MerchdownMapper.deleteMerchdownByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateMerchdown(PageData pageData) {
	 logger.info("修改Merchdown");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("MerchdownMapper.updateMerchdown",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Merchdown getMerchdownforId(String fid) {
	 logger.info("通过ID查询Merchdown");
	Merchdown	merchdown=null;
	 try { 
		merchdown = (Merchdown) daoSupport.findForObject("MerchdownMapper.getMerchdownForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 merchdown=null; 
	}
	 return merchdown; 
	}
	public List<Merchdown> getAllMerchdown(PageData pageData) {
	 logger.info("分页查询Merchdown");
	List<Merchdown>	merchdownList=null;
	 try { 
		merchdownList = (List<Merchdown>) daoSupport.findForList("MerchdownMapper.getAllMerchdown",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 merchdownList=null; 
	}
	 return merchdownList; 
	}
	public List<Merchdown> selectMerchdownList(PageData pageData) {
	 logger.info("查询所有Merchdown");
	List<Merchdown>	merchdownList=null;
	 try { 
		merchdownList = (List<Merchdown>) daoSupport.findForList("MerchdownMapper.selectMerchdownList",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 merchdownList=null; 
	}
	 return merchdownList; 
	}
}
