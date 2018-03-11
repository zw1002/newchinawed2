package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Sysusermgr;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("sysusermgr")
public class SysusermgrServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addSysUser(PageData pageData) {
	 logger.info("增加Sysusermgr");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("SysusermgrMapper.addSysUser",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delSysusermgrByFid(String fid) {
	 logger.info("删除Sysusermgr");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("SysusermgrMapper.deleteSysusermgrByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateSysUser(PageData pageData) {
	 logger.info("修改Sysusermgr");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("SysusermgrMapper.updateSysUser",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Sysusermgr getSysUserByUid(String uid) {
	 logger.info("通过ID查询Sysusermgr");
	Sysusermgr	sysusermgr=null;
	 try { 
		sysusermgr = (Sysusermgr) daoSupport.findForObject("SysusermgrMapper.getSysUserByUid",uid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 sysusermgr=null; 
	}
	 return sysusermgr; 
	}
	public List<Sysusermgr> getAllSysUserInfo(PageData pageData) {
	 logger.info("分页查询Sysusermgr");
	List<Sysusermgr>	sysusermgrList=null;
	 try { 
		sysusermgrList = (List<Sysusermgr>) daoSupport.findForList("SysusermgrMapper.getAllSysUserInfo",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 sysusermgrList=null; 
	}
	 return sysusermgrList; 
	}
	public List<Sysusermgr> selectSysUserList() {
	 logger.info("查询所有Sysusermgr");
	List<Sysusermgr>	sysusermgrList=null;
	 try { 
		sysusermgrList = (List<Sysusermgr>) daoSupport.findForList("SysusermgrMapper.selectSysUserList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 sysusermgrList=null; 
	}
	 return sysusermgrList; 
	}

	public Sysusermgr getUser(PageData pageData) {
		Sysusermgr user = null;
		try {
			user = (Sysusermgr)  daoSupport.findForObject("SysusermgrMapper.selectUser",pageData);
		}catch (Exception e){
			e.printStackTrace();
			user =null;
		}
		return user;
	}
}
