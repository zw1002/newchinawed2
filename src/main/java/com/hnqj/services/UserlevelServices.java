package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Userlevel;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("userlevel")
public class UserlevelServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addUserlevel(PageData pageData) {
	 logger.info("增加Userlevel");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("UserlevelMapper.addUserlevel",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delUserlevelByFid(String fid) {
	 logger.info("删除Userlevel");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("UserlevelMapper.deleteUserlevelByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateUserlevel(PageData pageData) {
	 logger.info("修改Userlevel");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("UserlevelMapper.updateUserlevel",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Userlevel getUserlevelforId(String fid) {
	 logger.info("通过ID查询Userlevel");
	Userlevel	userlevel=null;
	 try { 
		userlevel = (Userlevel) daoSupport.findForObject("UserlevelMapper.getUserlevelForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 userlevel=null; 
	}
	 return userlevel; 
	}
	public List<Userlevel> getAllUserlevel(PageData pageData) {
	 logger.info("分页查询Userlevel");
	List<Userlevel>	userlevelList=null;
	 try { 
		userlevelList = (List<Userlevel>) daoSupport.findForList("UserlevelMapper.getAllUserlevel",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 userlevelList=null; 
	}
	 return userlevelList; 
	}
	public List<Userlevel> selectUserlevelList() {
	 logger.info("查询所有Userlevel");
	List<Userlevel>	userlevelList=null;
	 try { 
		userlevelList = (List<Userlevel>) daoSupport.findForList("UserlevelMapper.selectUserlevelList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 userlevelList=null; 
	}
	 return userlevelList; 
	}
}
