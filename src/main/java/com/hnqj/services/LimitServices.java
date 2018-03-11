package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Limit;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("limit")
public class LimitServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addLimit(PageData pageData) {
	 logger.info("增加Limit");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("LimitMapper.addLimit",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delLimitByFid(String fid) {
	 logger.info("删除Limit");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("LimitMapper.deleteLimitByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateLimit(PageData pageData) {
	 logger.info("修改Limit");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("LimitMapper.updateLimit",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Limit getLimitByUid(String fid) {
	 logger.info("通过ID查询Limit");
	Limit	limit=null;
	 try { 
		limit = (Limit) daoSupport.findForObject("LimitMapper.getLimitByUid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 limit=null; 
	}
	 return limit; 
	}
	public List<Limit> getAllLimit(PageData pageData) {
	 logger.info("分页查询Limit");
	List<Limit>	limitList=null;
	 try { 
		limitList = (List<Limit>) daoSupport.findForList("LimitMapper.getAllLimit",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 limitList=null; 
	}
	 return limitList; 
	}
	public List<Limit> selectLimitList() {
	 logger.info("查询所有Limit");
	List<Limit>	limitList=null;
	 try { 
		limitList = (List<Limit>) daoSupport.findForList("LimitMapper.selectLimitList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 limitList=null; 
	}
	 return limitList; 
	}
}
