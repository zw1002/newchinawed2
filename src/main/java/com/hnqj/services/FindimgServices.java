package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Findimg;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("findimg")
public class FindimgServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addFindimg(PageData pageData) {
	 logger.info("增加Findimg");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("FindimgMapper.addFindimg",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delFindimgByFid(String fid) {
	 logger.info("删除Findimg");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("FindimgMapper.deleteFindimgByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateFindimg(PageData pageData) {
	 logger.info("修改Findimg");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("FindimgMapper.updateFindimg",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Findimg getFindimgforId(String fid) {
	 logger.info("通过ID查询Findimg");
	Findimg	findimg=null;
	 try { 
		findimg = (Findimg) daoSupport.findForObject("FindimgMapper.getFindimgForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 findimg=null; 
	}
	 return findimg; 
	}
	public List<Findimg> getAllFindimg(PageData pageData) {
	 logger.info("分页查询Findimg");
	List<Findimg>	findimgList=null;
	 try { 
		findimgList = (List<Findimg>) daoSupport.findForList("FindimgMapper.getAllFindimg",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 findimgList=null; 
	}
	 return findimgList; 
	}
	public List<Findimg> selectFindimgList() {
	 logger.info("查询所有Findimg");
	List<Findimg>	findimgList=null;
	 try { 
		findimgList = (List<Findimg>) daoSupport.findForList("FindimgMapper.selectFindimgList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 findimgList=null; 
	}
	 return findimgList; 
	}
}
