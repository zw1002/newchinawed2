package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Modules;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("modules")
public class ModulesServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport;

	/**
	 * 添加
	 * @param pageData
	 * @return
	 */
	public int addModule(PageData pageData){
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.insert("ModuleMapper.addModule",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}

	/**
	 * 更新
	 * @param pageData
	 * @return
	 */
	public int updateModule(PageData pageData){
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.update("ModuleMapper.updateModule",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}

	/**
	 * 删除指定信息
	 * @param fid
	 * @return
	 */
	public int deleteModuleByFid(String fid){
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.delete("ModuleMapper.deleteModuleByFid",fid);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}
	/**
	 * 根据产品ID查询信息
	 * @param uid
	 * @return
	 */
	public Modules selectModuleByFid(String uid){
		Modules module =null;
		try {
			module = (Modules) daoSupport.findForObject("ModuleMapper.selectModuleByFid",uid);
		}catch (Exception e){
			e.printStackTrace();
			module =null;
		}
		return module;
	}
	/**
	 * 查询所有信息
	 * @return
	 */
	public List<Modules> selectModuleList(){
		List<Modules> moduleList =null;
		try {
			moduleList = (List<Modules>) daoSupport.findForList("ModuleMapper.selectModuleList",null);
		}catch (Exception e){
			e.printStackTrace();
			moduleList =null;
		}
		return moduleList;
	}
	//查询信息
	public List<Modules> getAllModule(PageData pageData) {
		List<Modules> moduleList =null;
		try {
			moduleList = (List<Modules>) daoSupport.findForList("ModuleMapper.getAllModule",pageData);
		}catch (Exception e){
			e.printStackTrace();
			moduleList =null;
		}
		return moduleList;
	}
	//查询信息
	public List<Modules> getAllModuleCount() {
		List<Modules> moduleList =null;
		try {
			moduleList = (List<Modules>) daoSupport.findForList("ModuleMapper.getAllModuleCount",null);
		}catch (Exception e){
			e.printStackTrace();
			moduleList =null;
		}
		return moduleList;
	}
	//是否包含子菜单
	public List<Modules> getParentModule(){
		List<Modules> moduleList =null;
		try {
			moduleList = (List<Modules>) daoSupport.findForList("ModuleMapper.getParentModule",null);
		}catch (Exception e){
			e.printStackTrace();
			moduleList =null;
		}
		return moduleList;
	}
}
