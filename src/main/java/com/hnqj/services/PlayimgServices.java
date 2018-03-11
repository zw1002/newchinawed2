package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Playimg;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("playimg")
public class PlayimgServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addPlayimg(PageData pageData) {
	 logger.info("增加Playimg");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("PlayimgMapper.addPlayimg",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delPlayimgByFid(String fid) {
	 logger.info("删除Playimg");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("PlayimgMapper.deletePlayimgByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updatePlayimg(PageData pageData) {
	 logger.info("修改Playimg");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("PlayimgMapper.updatePlayimg",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Playimg getPlayimgforId(String fid) {
	 logger.info("通过ID查询Playimg");
	Playimg	playimg=null;
	 try { 
		playimg = (Playimg) daoSupport.findForObject("PlayimgMapper.selectPlayimgList",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 playimg=null; 
	}
	 return playimg; 
	}
	public List<Playimg> getAllPlayimg(PageData pageData) {
	 logger.info("分页查询Playimg");
	List<Playimg>	playimgList=null;
	 try { 
		playimgList = (List<Playimg>) daoSupport.findForList("PlayimgMapper.getAllPlayimg",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 playimgList=null; 
	}
	 return playimgList; 
	}
	public List<Playimg> selectPlayimgList() {
	 logger.info("查询所有Playimg");
	List<Playimg>	playimgList=null;
	 try { 
		playimgList = (List<Playimg>) daoSupport.findForList("PlayimgMapper.selectPlayimgList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 playimgList=null; 
	}
	 return playimgList; 
	}
}
