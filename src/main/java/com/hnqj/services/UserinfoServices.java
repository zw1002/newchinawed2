package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Userinfo;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;

@Service("userinfo")
public class UserinfoServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport;

	/**
	 * 会员用户注册
	 * @param pageData
	 * @return
	 */
	public int userInfoRegister(PageData pageData){
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.insert("UserinfoMapper.userInfoRegister",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}

	/**
	 * 修改会员信息
	 * @param pageData
	 * @return
	 */
	public int updateUserInfo(PageData pageData){
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.update("UserinfoMapper.updateUserInfo",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}

	/**
	 * 根据会员ID查询用户信息
	 * @param fid
	 * @return
	 */
	public Userinfo getUserInfoByUid(String fid){
		Userinfo user =null;
		try {
			user = (Userinfo) daoSupport.findForObject("UserinfoMapper.getUserInfoByUid",fid);
		}catch (Exception e){
			e.printStackTrace();
			user =null;
		}
		return user;
	}

	/**
	 * 查询所有的用户信息
	 * @return
	 */
	public List<Userinfo> selectUserInfoList(){
		List<Userinfo> userList =null;
		try {
			userList = (List<Userinfo>) daoSupport.findForList("UserinfoMapper.selectUserInfoList",null);
		}catch (Exception e){
			e.printStackTrace();
			userList =null;
		}
		return userList;
	}

	/**
	 * 获取登录用户信息
	 * @param pageData
     * @return
     */
	@Cacheable(value="remote",key="'getUser'")
	public Userinfo getUser(PageData pageData) {
		Userinfo user = null;
		try {
			user = (Userinfo)  daoSupport.findForObject("UserinfoMapper.selectUser",pageData);
		}catch (Exception e){
			e.printStackTrace();
			user =null;
		}
		return user;
	}
	//分页获取所有用户数据
	public List<Userinfo> getAllUserInfo(PageData pageData) {
		List<Userinfo> userList =null;
		try {
			userList = (List<Userinfo>) daoSupport.findForList("UserinfoMapper.getAllUserInfo",pageData);
		}catch (Exception e){
			e.printStackTrace();
			userList =null;
		}
		return userList;
	}
}
