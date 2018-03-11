package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Client;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("client")
public class ClientServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addClient(PageData pageData) {
	 logger.info("增加Client");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("ClientMapper.addClient",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delClientByFid(String fid) {
	 logger.info("删除Client");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("ClientMapper.deleteClientByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateClient(PageData pageData) {
	 logger.info("修改Client");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("ClientMapper.updateClient",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Client getClientforId(String fid) {
	 logger.info("通过ID查询Client");
	Client	client=null;
	 try { 
		client = (Client) daoSupport.findForObject("ClientMapper.getClientForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 client=null; 
	}
	 return client; 
	}
	public List<Client> getAllClient(PageData pageData) {
	 logger.info("分页查询Client");
	List<Client>	clientList=null;
	 try { 
		clientList = (List<Client>) daoSupport.findForList("ClientMapper.getAllClient",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 clientList=null; 
	}
	 return clientList; 
	}
//	public List<Client> selectClientList() {
//	 logger.info("查询所有Client");
//	List<Client>	clientList=null;
//	 try {
//		clientList = (List<Client>) daoSupport.findForList("ClientMapper.selectClientList",null);
//	 }catch (Exception e){
//	 e.printStackTrace();
//	 clientList=null;
//	}
//	 return clientList;
//	}
}
