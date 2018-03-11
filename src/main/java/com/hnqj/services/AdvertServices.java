package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Advert;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("advert")
public class AdvertServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addAdvert(PageData pageData) {
	 logger.info("增加Advert");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("AdvertMapper.addAdvert",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delAdvertByFid(String fid) {
	 logger.info("删除Advert");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("AdvertMapper.deleteAdvertByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateAdvert(PageData pageData) {
	 logger.info("修改Advert");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("AdvertMapper.updateAdvert",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Advert getAdvertforId(String fid) {
	 logger.info("通过ID查询Advert");
	Advert	advert=null;
	 try { 
		advert = (Advert) daoSupport.findForObject("AdvertMapper.getAdvertForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 advert=null; 
	}
	 return advert; 
	}
	public List<Advert> getAllAdvert(PageData pageData) {
	 logger.info("分页查询Advert");
	List<Advert>	advertList=null;
	 try { 
		advertList = (List<Advert>) daoSupport.findForList("AdvertMapper.getAllAdvert",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 advertList=null; 
	}
	 return advertList; 
	}
	public List<Advert> selectAdvertList() {
	 logger.info("查询所有Advert");
	List<Advert>	advertList=null;
	 try { 
		advertList = (List<Advert>) daoSupport.findForList("AdvertMapper.selectAdvertList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 advertList=null; 
	}
	 return advertList; 
	}
}
