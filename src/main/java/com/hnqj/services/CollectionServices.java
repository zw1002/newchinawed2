package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Collection;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("collection")
public class CollectionServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addCollection(PageData pageData) {
	 logger.info("增加Collection");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("CollectionMapper.addCollection",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delCollectionByFid(String fid) {
	 logger.info("删除Collection");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("CollectionMapper.deleteCollectionByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateCollection(PageData pageData) {
	 logger.info("修改Collection");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("CollectionMapper.updateCollection",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Collection getCollectionforId(String fid) {
	 logger.info("通过ID查询Collection");
	Collection	collection=null;
	 try { 
		collection = (Collection) daoSupport.findForObject("CollectionMapper.getCollectionForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 collection=null; 
	}
	 return collection; 
	}
	public List<Collection> getAllCollection(PageData pageData) {
	 logger.info("分页查询Collection");
	List<Collection>	collectionList=null;
	 try { 
		collectionList = (List<Collection>) daoSupport.findForList("CollectionMapper.getAllCollection",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 collectionList=null; 
	}
	 return collectionList; 
	}
	public List<Collection> selectCollectionList() {
	 logger.info("查询所有Collection");
	List<Collection>	collectionList=null;
	 try { 
		collectionList = (List<Collection>) daoSupport.findForList("CollectionMapper.selectCollectionList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 collectionList=null; 
	}
	 return collectionList; 
	}
}
