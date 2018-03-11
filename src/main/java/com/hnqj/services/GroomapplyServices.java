package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Groomapply;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("groomapply")
public class GroomapplyServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addGroomapply(PageData pageData) {
	 logger.info("增加Groomapply");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("GroomapplyMapper.addGroomapply",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delGroomapplyByFid(String fid) {
	 logger.info("删除Groomapply");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("GroomapplyMapper.deleteGroomapplyByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateGroomapply(PageData pageData) {
	 logger.info("修改Groomapply");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("GroomapplyMapper.updateGroomapply",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Groomapply getGroomapplyforId(String fid) {
	 logger.info("通过ID查询Groomapply");
	Groomapply	groomapply=null;
	 try { 
		groomapply = (Groomapply) daoSupport.findForObject("GroomapplyMapper.getGroomapplyForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 groomapply=null; 
	}
	 return groomapply; 
	}
	public List<Groomapply> getAllGroomapply(PageData pageData) {
	 logger.info("分页查询Groomapply");
	List<Groomapply>	groomapplyList=null;
	 try { 
		groomapplyList = (List<Groomapply>) daoSupport.findForList("GroomapplyMapper.getAllGroomapply",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 groomapplyList=null; 
	}
	 return groomapplyList; 
	}
	public List<Groomapply> selectGroomapplyList() {
	 logger.info("查询所有Groomapply");
	List<Groomapply>	groomapplyList=null;
	 try { 
		groomapplyList = (List<Groomapply>) daoSupport.findForList("GroomapplyMapper.selectGroomapplyList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 groomapplyList=null; 
	}
	 return groomapplyList; 
	}
}
