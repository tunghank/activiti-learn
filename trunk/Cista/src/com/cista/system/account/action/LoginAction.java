package com.cista.system.account.action;




import java.io.PrintWriter;

import com.cista.system.account.dao.UserDao;
import com.cista.system.ldap.service.LDAPConfigService;
import com.cista.system.to.SysUserTo;
import com.cista.system.util.BaseAction;
import com.cista.system.util.CistaUtil;


import org.apache.struts2.ServletActionContext;

/**
 * @file : UserManage.java
 * @author : 900730 Hank Tang
 * @Crteate Date/Time :Jul 29, 2008 10:21:54 AM
 */

public class LoginAction extends BaseAction  {
	
	private String userId;
	private String password;

	//20110629
	public String UserLogin() throws Exception {
		
		try{
			UserDao userDAO = new UserDao();
			LDAPConfigService ldapConfigService = new LDAPConfigService();
			// TODO Auto-generated method stub
			userId =null!=userId?userId:"";
			password =null!=password?password:"";
			
			logger.debug("userId " +userId );
			SysUserTo curUser = userDAO.getActiveCurUser(userId);
			if ( curUser != null ){
				if (curUser.getCompany().equals(CistaUtil.CISTA_SITE)) 
				{
					if (password.equals(""))
					{
						addActionMessage(getText("System.login.message.login.fail.passworderror"));
						return INPUT;
					}
					logger.debug("Internal User");
					//1.0 Internal User User Check
					if (ldapConfigService.checkOUUser(curUser.getUserId(), password)) {
						if (curUser.getActive().equals(CistaUtil.USER_ACTIVE)) {
							// 1.0 Get Current session
							session = ServletActionContext.getRequest().getSession(
									true);
							// 1.1 Remove Session value.
							session.removeAttribute(CistaUtil.CUR_USERINFO);
							// 1.2 Set Current User Information to seeion
							session.setAttribute(CistaUtil.CUR_USERINFO,
									curUser);
							// update last login time and IP
							userDAO.saveLastLoginInfo(userId, ServletActionContext.getRequest().getRemoteAddr());
							logger.debug(getText("System.login.message.login.success"));
	
	
	
						} else {
							addActionMessage(getText(
									"System.login.message.login.fail.account.inactive"));
							logger.debug(getText(
									"System.login.message.login.fail.account.inactive"));
							return INPUT;
						}
					} else {
						addActionMessage(getText("System.login.message.login.fail.passworderror"));
						logger.debug(getText("System.login.message.login.fail.passworderror"));
						return INPUT;
					}
				}else{
					logger.debug("Not internal user");
					//1.1 Not internal user User Check
					
					if (userDAO.validate(userId, CistaUtil.encodePasswd(password))) {
						if (curUser.getActive().equals(CistaUtil.USER_ACTIVE)){
							// 1.0 Get Current session
							session = ServletActionContext.getRequest().getSession(true);
							// 1.1 Remove Session value.
							session.removeAttribute(CistaUtil.CUR_USERINFO);
							// 1.2 Set Current User Information to seeion
							session.setAttribute(CistaUtil.CUR_USERINFO,curUser);
							// update last login time and IP
							userDAO.saveLastLoginInfo(userId, ServletActionContext.getRequest().getRemoteAddr());
							
							logger.debug(getText("System.login.message.login.success"));
							
	
						}else{
							addActionMessage(getText("" +"System.login.message.login.fail.account.inactive"));
							logger.debug(getText("System.login.message.login.fail.account.inactive"));
							logger.debug("Login Fail");
							return INPUT;
						}
	
					} else {
						addActionMessage(getText("System.login.message.login.fail.passworderror"));
						logger.debug(getText("System.login.message.login.fail.passworderror"));
						return INPUT;
					}
				}
				return SUCCESS;
			}else{
				addActionMessage(getText("System.login.message.login.fail.noid"));
				logger.debug(getText("System.login.message.login.fail.noid"));
				return INPUT;
			}
		} catch (Exception e) {
			addActionMessage(e.toString());
			e.printStackTrace();
			logger.error(e.toString());

			return ERROR;
		}
		
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}




}
