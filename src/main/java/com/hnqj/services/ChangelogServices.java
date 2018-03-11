package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Changelog;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("changelog")
public class ChangelogServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addChangelog(PageData pageData) {
	 logger.info("增加Changelog");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("ChangelogMapper.addChangelog",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delChangelogByFid(String fid) {
	 logger.info("删除Changelog");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("ChangelogMapper.deleteChangelogByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateChangelog(PageData pageData) {
	 logger.info("修改Changelog");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("ChangelogMapper.updateChangelog",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Changelog getChangelogforId(String fid) {
	 logger.info("通过ID查询Changelog");
	Changelog	changelog=null;
	 try { 
		changelog = (Changelog) daoSupport.findForObject("ChangelogMapper.getChangelogForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 changelog=null; 
	}
	 return changelog; 
	}
	public List<Changelog> getAllChangelog(PageData pageData) {
	 logger.info("分页查询Changelog");
	List<Changelog>	changelogList=null;
	 try { 
		changelogList = (List<Changelog>) daoSupport.findForList("ChangelogMapper.getAllChangelog",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 changelogList=null; 
	}
	 return changelogList; 
	}
	public List<Changelog> selectChangelogList() {
	 logger.info("查询所有Changelog");
	List<Changelog>	changelogList=null;
	 try { 
		changelogList = (List<Changelog>) daoSupport.findForList("ChangelogMapper.selectChangelogList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 changelogList=null; 
	}
	 return changelogList; 
	}
}
