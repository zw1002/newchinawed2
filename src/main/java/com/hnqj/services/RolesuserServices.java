package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Rolesuser;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("rolesuser")
public class RolesuserServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport;

	/**
	 * 添加
	 * @param pageData
	 * @return
	 */
	public int addRolesUser(PageData pageData){
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.insert("RolesUserMapper.addRolesUser",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}
	/**
	 * 删除信息
	 * @param fid
	 * @return
	 */
	public int deleteRolesUserByFid(String fid){
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.delete("RolesUserMapper.deleteRolesUserByFid",fid);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}

	public List<Rolesuser> getUserByRoleId(String roleId) {
		List<Rolesuser> rolesUserList =null;
		try {
			rolesUserList = (List<Rolesuser>) daoSupport.findForList("RolesUserMapper.getUserByRoleId",roleId);
		}catch (Exception e){
			e.printStackTrace();
			rolesUserList =null;
		}
		return rolesUserList;
	}

	public int delRolesUser(PageData pageData) {
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.delete("RolesUserMapper.delRolesUser",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}
}
