package com.hnqj.services;

import com.hnqj.core.PageData;
import com.hnqj.dao.DaoSupportImpl;
import com.hnqj.model.Graph;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("graph")
public class GraphServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addGraph(PageData pageData) {
	 logger.info("增加Graph");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("GraphMapper.addGraph",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delGraphByFid(String fid) {
	 logger.info("删除Graph");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("GraphMapper.deleteGraphByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateGraph(PageData pageData) {
	 logger.info("修改Graph");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("GraphMapper.updateGraph",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Graph getGraphforId(String fid) {
	 logger.info("通过ID查询Graph");
	Graph	graph=null;
	 try { 
		graph = (Graph) daoSupport.findForObject("GraphMapper.getGraphForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 graph=null; 
	}
	 return graph; 
	}
	public List<Graph> getAllGraph(PageData pageData) {
	 logger.info("分页查询Graph");
	List<Graph>	graphList=null;
	 try { 
		graphList = (List<Graph>) daoSupport.findForList("GraphMapper.getAllGraph",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 graphList=null; 
	}
	 return graphList; 
	}
	public List<Graph> selectGraphList(PageData pageData) {
	 logger.info("查询所有Graph");
	List<Graph>	graphList=null;
	 try { 
		graphList = (List<Graph>) daoSupport.findForList("GraphMapper.selectGraphList",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 graphList=null; 
	}
	 return graphList; 
	}

	public List<Graph> getGraphByUserId(String userid) {
		logger.info("查询个人所有Graph");
		List<Graph>	graphList=null;
		try {
			graphList = (List<Graph>) daoSupport.findForList("GraphMapper.getGraphByUserId",userid);
		}catch (Exception e){
			e.printStackTrace();
			graphList=null;
		}
		return graphList;
	}

	public List<Graph> getAllGraphOrderByMoney(PageData pagedata) {
		logger.info("根据个数查询高额Graph");
		List<Graph>	graphList=null;
		try {
			graphList = (List<Graph>) daoSupport.findForList("GraphMapper.getAllGraphOrderByMoney",pagedata);
		}catch (Exception e){
			e.printStackTrace();
			graphList=null;
		}
		return graphList;
	}

	public List<Graph> getAllGraphOrderByHot(PageData pageData) {
		logger.info("根据个数查询最热Graph");
		List<Graph>	graphList=null;
		try {
			graphList = (List<Graph>) daoSupport.findForList("GraphMapper.getAllGraphOrderByHot",pageData);
		}catch (Exception e){
			e.printStackTrace();
			graphList=null;
		}
		return graphList;
	}

	public List<Graph> getAllGraphOrderByNew(PageData pageData) {
		logger.info("根据个数查询最新Graph");
		List<Graph>	graphList=null;
		try {
			graphList = (List<Graph>) daoSupport.findForList("GraphMapper.getAllGraphOrderByNew",pageData);
		}catch (Exception e){
			e.printStackTrace();
			graphList=null;
		}
		return graphList;
	}

	public int updateGraphTicknums(PageData pageData) {
		logger.info("修改任务点击量");
		int iFlag =0;
		try {
			iFlag = (int) daoSupport.delete("GraphMapper.updateGraphTicknums",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}

	public int updateGraphFavcount(PageData graphData) {
		logger.info("修改任务收藏量");
		int iFlag =0;
		try {
			iFlag = (int) daoSupport.delete("GraphMapper.updateGraphFavcount",graphData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}

	public int updateGraphSelection(PageData pageData) {
		logger.info("选稿后修改稿件数据");
		int iFlag =0;
		try {
			iFlag = (int) daoSupport.delete("GraphMapper.updateGraphSelection",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}

	public int updateGraphForBidSubmission(PageData pageData) {
		logger.info("选稿后修改求图任务的稿件ID");
		int iFlag =0;
		try {
			iFlag = (int) daoSupport.delete("GraphMapper.updateGraphForBidSubmission",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}

	public List<Graph> getMySubmissionByUserId(String userid) {
		logger.info("获取个人稿件");
		List<Graph>	graphList=null;
		try {
			graphList = (List<Graph>) daoSupport.findForList("GraphMapper.getMySubmissionByUserId",userid);
		}catch (Exception e){
			e.printStackTrace();
			graphList=null;
		}
		return graphList;
	}

	public List<Graph> getExcellentCase(PageData pageData) {
		logger.info("获取成功案例");
		List<Graph>	graphList=null;
		try {
			graphList = (List<Graph>) daoSupport.findForList("GraphMapper.getExcellentCase",pageData);
		}catch (Exception e){
			e.printStackTrace();
			graphList=null;
		}
		return graphList;
	}

	public int updateGraphState(PageData pageData) {
		logger.info("修改任务审核状态以及审核信息");
		int iFlag =0;
		try {
			iFlag = (int) daoSupport.delete("GraphMapper.updateGraphState",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}
}
