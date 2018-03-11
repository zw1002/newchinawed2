package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Merchcycle;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("merchcycle")
public class MerchcycleServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addMerchcycle(PageData pageData) {
	 logger.info("增加Merchcycle");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("MerchcycleMapper.addMerchcycle",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delMerchcycleByFid(String fid) {
	 logger.info("删除Merchcycle");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("MerchcycleMapper.deleteMerchcycleByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateMerchcycle(PageData pageData) {
	 logger.info("修改Merchcycle");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("MerchcycleMapper.updateMerchcycle",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Merchcycle getMerchcycleforId(String fid) {
	 logger.info("通过ID查询Merchcycle");
	Merchcycle	merchcycle=null;
	 try { 
		merchcycle = (Merchcycle) daoSupport.findForObject("MerchcycleMapper.getMerchcycleForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 merchcycle=null; 
	}
	 return merchcycle; 
	}
	public List<Merchcycle> getAllMerchcycle(PageData pageData) {
	 logger.info("分页查询Merchcycle");
	List<Merchcycle>	merchcycleList=null;
	 try { 
		merchcycleList = (List<Merchcycle>) daoSupport.findForList("MerchcycleMapper.getAllMerchcycle",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 merchcycleList=null; 
	}
	 return merchcycleList; 
	}
	public List<Merchcycle> selectMerchcycleList(PageData pageData) {
	 logger.info("查询所有Merchcycle");
	List<Merchcycle>	merchcycleList=null;
	 try { 
		merchcycleList = (List<Merchcycle>) daoSupport.findForList("MerchcycleMapper.selectMerchcycleList",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 merchcycleList=null; 
	}
	 return merchcycleList; 
	}
}
