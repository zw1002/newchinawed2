package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Distribution;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("distribution")
public class DistributionServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addDistribution(PageData pageData) {
	 logger.info("增加Distribution");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("DistributionMapper.addDistribution",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delDistributionByFid(String fid) {
	 logger.info("删除Distribution");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("DistributionMapper.deleteDistributionByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateDistribution(PageData pageData) {
	 logger.info("修改Distribution");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("DistributionMapper.updateDistribution",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Distribution getDistributionforId(String fid) {
	 logger.info("通过ID查询Distribution");
	Distribution	distribution=null;
	 try { 
		distribution = (Distribution) daoSupport.findForObject("DistributionMapper.getDistributionForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 distribution=null; 
	}
	 return distribution; 
	}
	public List<Distribution> getAllDistribution(PageData pageData) {
	 logger.info("分页查询Distribution");
	List<Distribution>	distributionList=null;
	 try { 
		distributionList = (List<Distribution>) daoSupport.findForList("DistributionMapper.getAllDistribution",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 distributionList=null; 
	}
	 return distributionList; 
	}
	public List<Distribution> selectDistributionList() {
	 logger.info("查询所有Distribution");
	List<Distribution>	distributionList=null;
	 try { 
		distributionList = (List<Distribution>) daoSupport.findForList("DistributionMapper.selectDistributionList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 distributionList=null; 
	}
	 return distributionList; 
	}

	public Distribution getDistributionforParentId(String parentid) {
		logger.info("通过parentid查询Distribution");
		Distribution distribution=null;
		try {
			distribution = (Distribution) daoSupport.findForObject("DistributionMapper.getDistributionforParentId",parentid);
		}catch (Exception e){
			e.printStackTrace();
			distribution=null;
		}
		return distribution;
	}
}
