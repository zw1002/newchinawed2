package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Unsubscribe;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("unsubscribe")
public class UnsubscribeServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addUnsubscribe(PageData pageData) {
	 logger.info("增加Unsubscribe");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("UnsubscribeMapper.addUnsubscribe",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delUnsubscribeByFid(String fid) {
	 logger.info("删除Unsubscribe");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("UnsubscribeMapper.deleteUnsubscribeByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateUnsubscribe(PageData pageData) {
	 logger.info("修改Unsubscribe");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("UnsubscribeMapper.updateUnsubscribe",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Unsubscribe getUnsubscribeforId(String fid) {
	 logger.info("通过ID查询Unsubscribe");
	Unsubscribe	unsubscribe=null;
	 try { 
		unsubscribe = (Unsubscribe) daoSupport.findForObject("UnsubscribeMapper.getUnsubscribeForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 unsubscribe=null; 
	}
	 return unsubscribe; 
	}
	public List<Unsubscribe> getAllUnsubscribe(PageData pageData) {
	 logger.info("分页查询Unsubscribe");
	List<Unsubscribe>	unsubscribeList=null;
	 try { 
		unsubscribeList = (List<Unsubscribe>) daoSupport.findForList("UnsubscribeMapper.getAllUnsubscribe",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 unsubscribeList=null; 
	}
	 return unsubscribeList; 
	}
	public List<Unsubscribe> selectUnsubscribeList() {
	 logger.info("查询所有Unsubscribe");
	List<Unsubscribe>	unsubscribeList=null;
	 try { 
		unsubscribeList = (List<Unsubscribe>) daoSupport.findForList("UnsubscribeMapper.selectUnsubscribeList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 unsubscribeList=null; 
	}
	 return unsubscribeList; 
	}
}
