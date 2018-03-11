package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Proportions;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("proportions")
public class ProportionsServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addProportions(PageData pageData) {
	 logger.info("增加Proportions");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("ProportionsMapper.addProportions",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delProportionsByFid(String fid) {
	 logger.info("删除Proportions");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("ProportionsMapper.deleteProportionsByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateProportions(PageData pageData) {
	 logger.info("修改Proportions");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("ProportionsMapper.updateProportions",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Proportions getProportionsforId(String fid) {
	 logger.info("通过ID查询Proportions");
	Proportions	proportions=null;
	 try { 
		proportions = (Proportions) daoSupport.findForObject("ProportionsMapper.getProportionsForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 proportions=null; 
	}
	 return proportions; 
	}
	public List<Proportions> getAllProportions(PageData pageData) {
	 logger.info("分页查询Proportions");
	List<Proportions>	proportionsList=null;
	 try { 
		proportionsList = (List<Proportions>) daoSupport.findForList("ProportionsMapper.getAllProportions",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 proportionsList=null; 
	}
	 return proportionsList; 
	}
	public List<Proportions> selectProportionsList() {
	 logger.info("查询所有Proportions");
	List<Proportions>	proportionsList=null;
	 try { 
		proportionsList = (List<Proportions>) daoSupport.findForList("ProportionsMapper.selectProportionsList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 proportionsList=null; 
	}
	 return proportionsList; 
	}

	public Proportions getProportionsByLevel(PageData pageData) {
		logger.info("通过分销级别查询Proportions");
		Proportions	proportions=null;
		try {
			proportions = (Proportions) daoSupport.findForObject("ProportionsMapper.getProportionsByLevel",pageData);
		}catch (Exception e){
			e.printStackTrace();
			proportions=null;
		}
		return proportions;
	}
}
