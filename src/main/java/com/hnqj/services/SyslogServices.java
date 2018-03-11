package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Syslog;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("syslog")
public class SyslogServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addSyslog(PageData pageData) {
	 logger.info("增加Syslog");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("SyslogMapper.addSyslog",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delSyslogByFid(String fid) {
	 logger.info("删除Syslog");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("SyslogMapper.deleteSyslogByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateSyslog(PageData pageData) {
	 logger.info("修改Syslog");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("SyslogMapper.updateSyslog",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Syslog getSyslogforId(String fid) {
	 logger.info("通过ID查询Syslog");
	Syslog	syslog=null;
	 try { 
		syslog = (Syslog) daoSupport.findForObject("SyslogMapper.getSyslogForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 syslog=null; 
	}
	 return syslog; 
	}
	public List<Syslog> getAllSyslog(PageData pageData) {
	 logger.info("分页查询Syslog");
	List<Syslog>	syslogList=null;
	 try { 
		syslogList = (List<Syslog>) daoSupport.findForList("SyslogMapper.getAllSyslog",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 syslogList=null; 
	}
	 return syslogList; 
	}
	public List<Syslog> selectSyslogList() {
	 logger.info("查询所有Syslog");
	List<Syslog>	syslogList=null;
	 try { 
		syslogList = (List<Syslog>) daoSupport.findForList("SyslogMapper.selectSyslogList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 syslogList=null; 
	}
	 return syslogList; 
	}
}
