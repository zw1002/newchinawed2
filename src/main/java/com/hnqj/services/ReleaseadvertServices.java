package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Releaseadvert;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("releaseadvert")
public class ReleaseadvertServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addReleaseadvert(PageData pageData) {
	 logger.info("增加Releaseadvert");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("ReleaseadvertMapper.addReleaseadvert",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delReleaseadvertByFid(String fid) {
	 logger.info("删除Releaseadvert");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("ReleaseadvertMapper.deleteReleaseadvertByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateReleaseadvert(PageData pageData) {
	 logger.info("修改Releaseadvert");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("ReleaseadvertMapper.updateReleaseadvert",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Releaseadvert getReleaseadvertforId(String fid) {
	 logger.info("通过ID查询Releaseadvert");
	Releaseadvert	releaseadvert=null;
	 try { 
		releaseadvert = (Releaseadvert) daoSupport.findForObject("ReleaseadvertMapper.getReleaseadvertForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 releaseadvert=null; 
	}
	 return releaseadvert; 
	}
	public List<Releaseadvert> getAllReleaseadvert(PageData pageData) {
	 logger.info("分页查询Releaseadvert");
	List<Releaseadvert>	releaseadvertList=null;
	 try { 
		releaseadvertList = (List<Releaseadvert>) daoSupport.findForList("ReleaseadvertMapper.getAllReleaseadvert",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 releaseadvertList=null; 
	}
	 return releaseadvertList; 
	}
	public List<Releaseadvert> selectReleaseadvertList() {
	 logger.info("查询所有Releaseadvert");
	List<Releaseadvert>	releaseadvertList=null;
	 try { 
		releaseadvertList = (List<Releaseadvert>) daoSupport.findForList("ReleaseadvertMapper.selectReleaseadvertList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 releaseadvertList=null; 
	}
	 return releaseadvertList; 
	}
}
