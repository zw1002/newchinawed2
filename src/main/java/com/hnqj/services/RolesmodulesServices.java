package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Account;
import com.hnqj.model.Roles;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import com.hnqj.model.Rolesmodules;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("rolesmodules")
public class RolesmodulesServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport;

	/**
	 * 添加
	 * @param pageData
	 * @return
	 */
	public int addRolesModule(PageData pageData){
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.insert("RolesModulesMapper.addRolesModule",pageData);
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


	public List<Rolesmodules> getModuleByRoleId(String roleId) {
		List<Rolesmodules> rolesModulesList =null;
		try {
			rolesModulesList = (List<Rolesmodules>) daoSupport.findForList("RolesModulesMapper.getModuleByRoleId",roleId);
		}catch (Exception e){
			e.printStackTrace();
			rolesModulesList =null;
		}
		return rolesModulesList;
	}

	public int delRolesModulesByMdId(PageData pageData) {
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.delete("RolesModulesMapper.delRolesModulesByMdId",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}
}
