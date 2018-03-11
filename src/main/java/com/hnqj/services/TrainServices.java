package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Train;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("train")
public class TrainServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addTrain(PageData pageData) {
	 logger.info("增加Train");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("TrainMapper.addTrain",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delTrainByFid(String fid) {
	 logger.info("删除Train");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("TrainMapper.deleteTrainByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateTrain(PageData pageData) {
	 logger.info("修改Train");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("TrainMapper.updateTrain",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Train getTrainforId(String fid) {
	 logger.info("通过ID查询Train");
	Train	train=null;
	 try { 
		train = (Train) daoSupport.findForObject("TrainMapper.getTrainForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 train=null; 
	}
	 return train; 
	}
	public List<Train> getAllTrain(PageData pageData) {
	 logger.info("分页查询Train");
	List<Train>	trainList=null;
	 try { 
		trainList = (List<Train>) daoSupport.findForList("TrainMapper.getAllTrain",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 trainList=null; 
	}
	 return trainList; 
	}
}
