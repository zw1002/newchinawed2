package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Dict;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("dict")
public class DictServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addDict(PageData pageData) {
	 logger.info("增加Dict");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("DictMapper.addDict",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delDictByFid(String fid) {
	 logger.info("删除Dict");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("DictMapper.deleteDictByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateDict(PageData pageData) {
	 logger.info("修改Dict");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("DictMapper.updateDict",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Dict getDictforId(String fid) {
	 logger.info("通过ID查询Dict");
	Dict	dict=null;
	 try { 
		dict = (Dict) daoSupport.findForObject("DictMapper.getDictForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 dict=null; 
	}
	 return dict; 
	}
	public List<Dict> getAllDict(PageData pageData) {
	 logger.info("分页查询Dict");
	List<Dict>	dictList=null;
	 try { 
		dictList = (List<Dict>) daoSupport.findForList("DictMapper.getAllDict",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 dictList=null; 
	}
	 return dictList; 
	}
	public List<Dict> selectDictList(String parentId) {
	 logger.info("查询所有Dict");
	List<Dict>	dictList=null;
	 try { 
		dictList = (List<Dict>) daoSupport.findForList("DictMapper.selectDictList",parentId);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 dictList=null; 
	}
	 return dictList; 
	}

	public List<Dict> getParentModule() {
		logger.info("查询所有父级");
		List<Dict>	dictList=null;
		try {
			dictList = (List<Dict>) daoSupport.findForList("DictMapper.getParentModule",null);
		}catch (Exception e){
			e.printStackTrace();
			dictList=null;
		}
		return dictList;
	}

	public void delChildByFid(String ids) {
		logger.info("删除子级Dict");
		int iFlag = 0;
		try {
			iFlag = (int) daoSupport.delete("DictMapper.delChildByFid", ids);
		} catch (Exception e) {
			e.printStackTrace();
			iFlag = 0;
		}
	}

	public List<Dict> selectDictLists() {
		logger.info("查询所有Dict");
		List<Dict>	dictList=null;
		try {
			dictList = (List<Dict>) daoSupport.findForList("DictMapper.selectDictLists",null);
		}catch (Exception e){
			e.printStackTrace();
			dictList=null;
		}
		return dictList;
	}
	public List<Dict> getDictForKeys(String keys) {
		logger.info("查询指定Dict");
		List<Dict>	dictList=null;
		try {
			dictList = (List<Dict>) daoSupport.findForList("DictMapper.getDictForKeys",keys);
		}catch (Exception e){
			e.printStackTrace();
			dictList=null;
		}
		return dictList;
	}
}
