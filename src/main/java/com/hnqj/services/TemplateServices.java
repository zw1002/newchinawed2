package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Template;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("template")
public class TemplateServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport; 

	public int addTemplate(PageData pageData) {
	 logger.info("增加Template");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.insert("TemplateMapper.addTemplate",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int delTemplateByFid(String fid) {
	 logger.info("删除Template");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.delete("TemplateMapper.deleteTemplateByFid",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public int updateTemplate(PageData pageData) {
	 logger.info("修改Template");
	 int iFlag =0; 
	 try { 
		iFlag = (int) daoSupport.update("TemplateMapper.updateTemplate",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 iFlag=0; 
	}
	 return iFlag; 
	}
	public Template getTemplateforId(String fid) {
	 logger.info("通过ID查询Template");
	Template	template=null;
	 try { 
		template = (Template) daoSupport.findForObject("TemplateMapper.getTemplateForId",fid);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 template=null; 
	}
	 return template; 
	}
	public List<Template> getAllTemplate(PageData pageData) {
	 logger.info("分页查询Template");
	List<Template>	templateList=null;
	 try { 
		templateList = (List<Template>) daoSupport.findForList("TemplateMapper.getAllTemplate",pageData);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 templateList=null; 
	}
	 return templateList; 
	}
	public List<Template> selectTemplateList() {
	 logger.info("查询所有Template");
	List<Template>	templateList=null;
	 try { 
		templateList = (List<Template>) daoSupport.findForList("TemplateMapper.selectTemplateList",null);
	 }catch (Exception e){ 
	 e.printStackTrace(); 
	 templateList=null; 
	}
	 return templateList; 
	}
}
