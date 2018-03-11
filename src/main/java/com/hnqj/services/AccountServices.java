package com.hnqj.services;

import com.hnqj.core.PageData;

import com.hnqj.model.Account;

import javax.annotation.Resource;
import com.hnqj.dao.DaoSupportImpl;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
@Service("account")
public class AccountServices {

protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "daoSupportImpl")

	private DaoSupportImpl daoSupport;

	/**
	 * 添加
	 * @param pageData
	 * @return
	 */
	public int addAccount(PageData pageData){
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.insert("AccountMapper.addAccount",pageData);
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
	public int updateAccount(PageData pageData){
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.update("AccountMapper.updateAccount",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}

	/**
	 * 删除指定信息
	 * 删除用户时设置帐号不可用
	 * @param fid
	 * @return
	 */
	public int deleteAccountByFid(String fid){
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.delete("AccountMapper.deleteAccountByFid",fid);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}

	/**
	 * 查询所有信息
	 * @return
	 */
	public List<Account> selectAccountList(){
		List<Account> accountList =null;
		try {
			accountList = (List<Account>) daoSupport.findForList("AccountMapper.selectAccountList",null);
		}catch (Exception e){
			e.printStackTrace();
			accountList =null;
		}
		return accountList;
	}

	/**
	 * 通过用户ID查询账号
	 * @param fid
     * @return
     */
	public Account getAccountforUserId(String fid) {
		Account account = null;
		try {
			account =(Account) daoSupport.findForObject("AccountMapper.getAccountforUserId",fid);
		}catch (Exception e){
			e.printStackTrace();
			account =null;
		}
		return account;
	}

	//分页查询账号信息
	public List<Account> getAllAccounts(PageData pageData) {
		List<Account> accountList =null;
		try {
			accountList = (List<Account>) daoSupport.findForList("AccountMapper.getAllAccounts",pageData);
		}catch (Exception e){
			e.printStackTrace();
			accountList =null;
		}
		return accountList;
	}
	//重置密码
	public int resetPasswd(PageData pageData) {
		int iFlag =0;
		try {
			iFlag = (int)daoSupport.update("AccountMapper.resetPasswd",pageData);
		}catch (Exception e){
			e.printStackTrace();
			iFlag=0;
		}
		return iFlag;
	}

	public Account getAccountByRecord(String referee) {
		Account account = null;
		try {
			account =(Account) daoSupport.findForObject("AccountMapper.getAccountByRecord",referee);
		}catch (Exception e){
			e.printStackTrace();
			account =null;
		}
		return account;
	}
}
