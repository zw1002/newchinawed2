package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Notify;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("notify")
public class NotifyServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addNotify(PageData pageData) {
	 logger.info("增加Notify");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("NotifyMapper.addNotify",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delNotifyByFid(String fid) {
	 logger.info("删除Notify");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("NotifyMapper.deleteNotifyByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateNotify(PageData pageData) {
	 logger.info("修改Notify");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("NotifyMapper.updateNotify",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Notify getNotifyforId(String fid) {
	 logger.info("通过ID查询Notify");
	Notify	notify=null;
	 try { 
		notify = (Notify) daoSupport.findForObject("NotifyMapper.getNotifyForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 notify=null; 
	}
	 return notify; 
	}
	public List<Notify> getAllNotify(PageData pageData) {
	 logger.info("分页查询Notify");
	List<Notify>	notifyList=null;
	 try { 
		notifyList = (List<Notify>) daoSupport.findForList("NotifyMapper.getAllNotify",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 notifyList=null; 
	}
	 return notifyList; 
	}
	public List<Notify> selectNotifyList() {
	 logger.info("查询所有Notify");
	List<Notify>	notifyList=null;
	 try { 
		notifyList = (List<Notify>) daoSupport.findForList("NotifyMapper.selectNotifyList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 notifyList=null; 
	}
	 return notifyList; 
	}
}
