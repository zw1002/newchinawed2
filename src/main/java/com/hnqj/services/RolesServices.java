package com.hnqj.services;

import com.hnqj.controller.OperAnnotation;
import com.hnqj.core.PageData;

import com.hnqj.model.*;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("roles")
public class RolesServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport;

	/**
	 * 添加
	 * @param pageData
	 * @return
	 */
	public int addRoles(PageData pageData){
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.insert("RolesMapper.addRoles",pageData);
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
	public int updateRoles(PageData pageData){
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.update("RolesMapper.updateRoles",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}

	/**
	 * 删除指定的库存信息
	 * @param fid
	 * @return
	 */
	public int deleteRolesByFid(String fid){
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.delete("RolesMapper.deleteRolesByFid",fid);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}
	/**
	 * 根据产品ID查询信息
	 * @param fid
	 * @return
	 */
	public Roles selectRolesByFid(String fid){
		Roles roles =null;
		try {
			roles = (Roles) daoSupport.findForObject("RolesMapper.selectRolesByFid",fid);
		}catch (Exception e){
			e.printStackTrace();
			roles =null;
		}
		return roles;
	}
	/**
	 * 查询所有信息
	 * @return
	 */
	public List<Account> selectStockInfoList(){
		List<Account> accountList =null;
		try {
			accountList = (List<Account>) daoSupport.findForList("RolesMapper.selectStockInfoList",null);
		}catch (Exception e){
			e.printStackTrace();
			accountList =null;
		}
		return accountList;
	}

	@OperAnnotation(moduleName= "权限管理",option="selectRoleByUser")
	public List<Roles> selectRoleByUser(Sysusermgr user){
		List<Roles> rolesList =null;
		String user_id = user.getUid();
		try {
			rolesList = (List<Roles>) daoSupport.findForList("RolesMapper.selectRoleByUserId",user_id);
		}catch (Exception e){
			e.printStackTrace();
			rolesList =null;
		}
		return rolesList;
	}
	public List<Modules> getModuleListByRoleId(Roles role) {
		List<Modules> moduleList =null;
		String role_id = role.getUid();
		try {
			moduleList = (List<Modules>) daoSupport.findForList("ModuleMapper.getModuleListByRoleId",role_id);
		}catch (Exception e){
			e.printStackTrace();
			moduleList =null;
		}
		return moduleList;
	}
	//查询信息
	public List<Roles> getAllRoles(PageData pageData) {
		List<Roles> rolesList =null;
		try {
			rolesList = (List<Roles>) daoSupport.findForList("RolesMapper.getAllRoles",pageData);
		}catch (Exception e){
			e.printStackTrace();
			rolesList =null;
		}
		return rolesList;
	}
}
