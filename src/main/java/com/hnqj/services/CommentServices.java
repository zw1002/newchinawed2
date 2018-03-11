package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Comment;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("comment")
public class CommentServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addComment(PageData pageData) {
	 logger.info("增加Comment");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("CommentMapper.addComment",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delCommentByFid(String fid) {
	 logger.info("删除Comment");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("CommentMapper.deleteCommentByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateComment(PageData pageData) {
	 logger.info("修改Comment");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("CommentMapper.updateComment",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Comment getCommentforId(String fid) {
	 logger.info("通过ID查询Comment");
	Comment	comment=null;
	 try { 
		comment = (Comment) daoSupport.findForObject("CommentMapper.getCommentForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 comment=null; 
	}
	 return comment; 
	}
	public List<Comment> getAllComment(PageData pageData) {
	 logger.info("分页查询Comment");
	List<Comment>	commentList=null;
	 try { 
		commentList = (List<Comment>) daoSupport.findForList("CommentMapper.getAllComment",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 commentList=null; 
	}
	 return commentList; 
	}
//	public List<Comment> selectCommentList() {
//	 logger.info("查询所有Comment");
//	List<Comment>	commentList=null;
//	 try {
//		commentList = (List<Comment>) daoSupport.findForList("CommentMapper.selectCommentList",null);
//	 }catch (Exception e){
//	 e.printStackTrace();
//	 commentList=null;
//	}
//	 return commentList;
//	}
}
