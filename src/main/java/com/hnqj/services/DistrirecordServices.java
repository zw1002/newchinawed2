package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Distrirecord;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("distrirecord")
public class DistrirecordServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addDistrirecord(PageData pageData) {
	 logger.info("增加Distrirecord");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("DistrirecordMapper.addDistrirecord",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delDistrirecordByFid(String fid) {
	 logger.info("删除Distrirecord");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("DistrirecordMapper.deleteDistrirecordByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateDistrirecord(PageData pageData) {
	 logger.info("修改Distrirecord");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("DistrirecordMapper.updateDistrirecord",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Distrirecord getDistrirecordforId(String fid) {
	 logger.info("通过ID查询Distrirecord");
	Distrirecord	distrirecord=null;
	 try { 
		distrirecord = (Distrirecord) daoSupport.findForObject("DistrirecordMapper.getDistrirecordForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 distrirecord=null; 
	}
	 return distrirecord; 
	}
	public List<Distrirecord> getAllDistrirecord(PageData pageData) {
	 logger.info("分页查询Distrirecord");
	List<Distrirecord>	distrirecordList=null;
	 try { 
		distrirecordList = (List<Distrirecord>) daoSupport.findForList("DistrirecordMapper.getAllDistrirecord",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 distrirecordList=null; 
	}
	 return distrirecordList; 
	}
	public List<Distrirecord> selectDistrirecordList() {
	 logger.info("查询所有Distrirecord");
	List<Distrirecord>	distrirecordList=null;
	 try { 
		distrirecordList = (List<Distrirecord>) daoSupport.findForList("DistrirecordMapper.selectDistrirecordList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 distrirecordList=null; 
	}
	 return distrirecordList; 
	}
}
