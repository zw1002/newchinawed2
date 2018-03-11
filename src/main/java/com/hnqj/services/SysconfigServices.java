package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Sysconfig;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("sysconfig")
public class SysconfigServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addSysconfig(PageData pageData) {
	 logger.info("增加Sysconfig");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("SysconfigMapper.addSysconfig",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delSysconfigByFid(String fid) {
	 logger.info("删除Sysconfig");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("SysconfigMapper.deleteSysconfigByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateSysconfig(PageData pageData) {
	 logger.info("修改Sysconfig");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("SysconfigMapper.updateSysconfig",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Sysconfig getSysconfigforId(String fid) {
	 logger.info("通过ID查询Sysconfig");
	Sysconfig	sysconfig=null;
	 try { 
		sysconfig = (Sysconfig) daoSupport.findForObject("SysconfigMapper.getSysconfigForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 sysconfig=null; 
	}
	 return sysconfig; 
	}
	public List<Sysconfig> getAllSysconfig(PageData pageData) {
	 logger.info("分页查询Sysconfig");
	List<Sysconfig>	sysconfigList=null;
	 try { 
		sysconfigList = (List<Sysconfig>) daoSupport.findForList("SysconfigMapper.getAllSysconfig",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 sysconfigList=null; 
	}
	 return sysconfigList; 
	}
	public List<Sysconfig> selectSysconfigList() {
	 logger.info("查询所有Sysconfig");
	List<Sysconfig>	sysconfigList=null;
	 try { 
		sysconfigList = (List<Sysconfig>) daoSupport.findForList("SysconfigMapper.selectSysconfigList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 sysconfigList=null; 
	}
	 return sysconfigList; 
	}
}
