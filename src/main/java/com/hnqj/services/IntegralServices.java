package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Integral;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("integral")
public class IntegralServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addIntegral(PageData pageData) {
	 logger.info("增加Integral");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("IntegralMapper.addIntegral",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delIntegralByFid(String fid) {
	 logger.info("删除Integral");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("IntegralMapper.deleteIntegralByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateIntegral(PageData pageData) {
	 logger.info("修改Integral");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("IntegralMapper.updateIntegral",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Integral getIntegralByUid(String fid) {
	 logger.info("通过ID查询Integral");
	Integral Integral=null;
	 try { 
		Integral = (Integral) daoSupport.findForObject("IntegralMapper.getIntegralByUid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 Integral=null; 
	}
	 return Integral; 
	}
	public List<Integral> getAllIntegral(PageData pageData) {
	 logger.info("分页查询Integral");
	List<Integral>	IntegralList=null;
	 try { 
		IntegralList = (List<Integral>) daoSupport.findForList("IntegralMapper.getAllIntegral",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 IntegralList=null; 
	}
	 return IntegralList; 
	}
	public List<Integral> selectIntegralList() {
	 logger.info("查询所有Integral");
	List<Integral>	IntegralList=null;
	 try { 
		IntegralList = (List<Integral>) daoSupport.findForList("IntegralMapper.selectIntegralList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 IntegralList=null; 
	}
	 return IntegralList; 
	}

	public Integral selectIntegralByScroe(PageData pagedata) {
		logger.info("通过积分查询Integral");
		Integral Integral=null;
		try {
			Integral = (Integral) daoSupport.findForObject("IntegralMapper.selectIntegralByScroe",pagedata);
		}catch (Exception e){
			e.printStackTrace();
			Integral=null;
		}
		return Integral;
	}

	public Integral selectIntegralByGrade(String grade) {
		logger.info("通过等级查询Integral");
		Integral Integral=null;
		try {
			Integral = (Integral) daoSupport.findForObject("IntegralMapper.selectIntegralByGrade",grade);
		}catch (Exception e){
			e.printStackTrace();
			Integral=null;
		}
		return Integral;
	}
}
