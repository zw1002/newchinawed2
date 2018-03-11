package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Shoppingcart;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("shoppingcart")
public class ShoppingcartServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addShoppingcart(PageData pageData) {
	 logger.info("增加Shoppingcart");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("ShoppingcartMapper.addShoppingcart",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delShoppingcartByFid(String fid) {
	 logger.info("删除Shoppingcart");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("ShoppingcartMapper.deleteShoppingcartByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateShoppingcart(PageData pageData) {
	 logger.info("修改Shoppingcart");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("ShoppingcartMapper.updateShoppingcart",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Shoppingcart getShoppingcartforId(String fid) {
	 logger.info("通过ID查询Shoppingcart");
	Shoppingcart	shoppingcart=null;
	 try { 
		shoppingcart = (Shoppingcart) daoSupport.findForObject("ShoppingcartMapper.getShoppingcartForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 shoppingcart=null; 
	}
	 return shoppingcart; 
	}
	public List<Shoppingcart> getAllShoppingcart(PageData pageData) {
	 logger.info("分页查询Shoppingcart");
	List<Shoppingcart>	shoppingcartList=null;
	 try { 
		shoppingcartList = (List<Shoppingcart>) daoSupport.findForList("ShoppingcartMapper.getAllShoppingcart",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 shoppingcartList=null; 
	}
	 return shoppingcartList; 
	}
	public List<Shoppingcart> selectShoppingcartList() {
	 logger.info("查询所有Shoppingcart");
	List<Shoppingcart>	shoppingcartList=null;
	 try { 
		shoppingcartList = (List<Shoppingcart>) daoSupport.findForList("ShoppingcartMapper.selectShoppingcartList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 shoppingcartList=null; 
	}
	 return shoppingcartList; 
	}
}
