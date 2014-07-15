package com.clt.system.account.action;

import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

import com.clt.system.util.BaseAction;
import com.clt.system.util.CLTUtil;
import com.clt.system.ldap.service.LDAPConfigService;

import com.clt.system.to.SysRoleTo;
import com.clt.system.to.SysUserTo;
import com.clt.system.to.SysUserRoleTo;
import com.clt.system.dao.SAPVendorDao;
import com.clt.system.dao.SAPCustomerDao;

import com.clt.system.account.dao.UserDao;
import com.clt.system.account.dao.RoleDao;
import com.clt.system.account.dao.DepartmentDao;
import com.clt.system.account.dao.UserRoleDao;

import org.apache.struts2.ServletActionContext;

/**
 * @file : UserManage.java
 * @author : 900730 Hank Tang
 * @Crteate Date/Time :Jul 29, 2008 10:21:54 AM
 */

public class UserManage extends BaseAction  {
	
	private String userId;
	private String realName;
	private String password;
	private String company;
	private String department;
	private String position;
	private String email;
	private String phoneNum;
	private String active;
	private String lastTime;
	private String lastIp;
	private String userCompany;

	private String updateBy;
	private String[] hidUserList; 		// 取得 userid
	private String[] hidUserChkList;	// 取得 user active
	private String processId;
	private int pageNo;
	private String roleId;	
	private String userRole;
	private String confirmPassword;
	
	// for modify user 
	private String selectUserId;
	private String chkRolesList;

	//For Quotation Project
	private String quoteHeaderUid;
	
	//20110629
	public String userLogin() throws Exception {
		UserDao userDAO = new UserDao();
		LDAPConfigService ldapConfigService = new LDAPConfigService();
		// TODO Auto-generated method stub
		userId =null!=userId?userId:"";
		password =null!=password?password:"";
		
		logger.debug("userId " +userId );
		SysUserTo curUser = userDAO.getActiveCurUser(userId);
		if ( curUser != null ){
			if (curUser.getCompany().equals("CHILIN")) 
			{
				if (password.equals(""))
				{
					addActionMessage(getText("System.login.message.login.fail.passworderror"));
					return INPUT;
				}
				logger.debug("Internal User");
				//1.0 Internal User User Check
				if (ldapConfigService.checkOUUser(curUser.getUserId(), password)) {
					if (curUser.getActive().equals(CLTUtil.USER_ACTIVE)) {
						// 1.0 Get Current session
						session = ServletActionContext.getRequest().getSession(
								true);
						// 1.1 Remove Session value.
						session.removeAttribute(CLTUtil.CUR_USERINFO);
						// 1.2 Set Current User Information to seeion
						session.setAttribute(CLTUtil.CUR_USERINFO,
								curUser);
						// update last login time and IP
						userDAO.saveLastLoginInfo(userId, ServletActionContext.getRequest().getRemoteAddr());
						logger.debug(getText("System.login.message.login.success"));
						
						//是否要導到Quote Function
						String quoteHeaderUid = this.getQuoteHeaderUid();
						quoteHeaderUid = null != quoteHeaderUid ? quoteHeaderUid : "" ;
						if (!quoteHeaderUid.equals("")){
							request.setAttribute("quoteHeaderUid", quoteHeaderUid);
							return "quote";
						}else{
							return SUCCESS;
						}


					} else {
						addActionMessage(getText(
								"System.login.message.login.fail.account.inactive"));
						logger.debug(getText(
								"System.login.message.login.fail.account.inactive"));
						return INPUT;
					}
				} else {
					addActionMessage(getText(
							"System.login.message.login.fail.passworderror"));
					logger.debug(getText(
							"System.login.message.login.fail.passworderror"));
					return INPUT;
				}
			}else{
				logger.debug("Not internal user");
				//1.1 Not internal user User Check
				
				if (userDAO.validate(userId, CLTUtil.encodePasswd(password))) {
					if (curUser.getActive().equals(CLTUtil.USER_ACTIVE)){
						// 1.0 Get Current session
						session = ServletActionContext.getRequest().getSession(
								true);
						// 1.1 Remove Session value.
						session.removeAttribute(CLTUtil.CUR_USERINFO);
						// 1.2 Set Current User Information to seeion
						session.setAttribute(CLTUtil.CUR_USERINFO,
								curUser);
						// update last login time and IP
						userDAO.saveLastLoginInfo(userId, ServletActionContext.getRequest().getRemoteAddr());
						
						logger.debug(getText(
								"System.login.message.login.success"));
						
						//是否要導到Quote Function
						String quoteHeaderUid = this.getQuoteHeaderUid();
						quoteHeaderUid = null != quoteHeaderUid ? quoteHeaderUid : "" ;
						if (!quoteHeaderUid.equals("")){
							request.setAttribute("quoteHeaderUid", quoteHeaderUid);
							return "quote";
						}else{
							return SUCCESS;
						}
					}else{
						addActionMessage(getText("" +
								"System.login.message.login.fail.account.inactive"));
						logger.debug(getText(
								"System.login.message.login.fail.account.inactive"));
						logger.debug("Login Fail");
						return INPUT;
					}

				} else {
					addActionMessage(getText(
							"System.login.message.login.fail.passworderror"));
					logger.debug(getText(
							"System.login.message.login.fail.passworderror"));
					return INPUT;
				}
			}
		}else{
			addActionMessage(getText("System.login.message.login.fail.noid"));
			logger.debug(getText("System.login.message.login.fail.noid"));
			return INPUT;
		}		
	}
	//20110629
	public String userSearchPre() throws Exception {
		
		UserDao userDAO = new UserDao();
		request= ServletActionContext.getRequest();
		ArrayList<SysRoleTo> roleList = (ArrayList<SysRoleTo>)userDAO.getAllRoles();
		roleList =null!=roleList?roleList:new ArrayList<SysRoleTo>();
		
		request.setAttribute("roleList", roleList);
			
		return SUCCESS;
	}

	public String userSearch() throws Exception {
		
		request= ServletActionContext.getRequest();
        UserDao userDAO = new UserDao();		
		List result = new ArrayList();
		List userList = new ArrayList();
		
		this.userId = null != this.userId ? this.userId : "";
		this.roleId = null != this.roleId ? roleId : "";
		this.pageNo = 0 == this.pageNo ? 1: this.pageNo;		

		//for paging
		int pageSize = CLTUtil.REPORT_PAGE_SIZE;
        int resultSize = -1;
        int pages = -1;

		userList = (ArrayList)userDAO.getUserList(this.userId, this.roleId);
		resultSize = null == userList? 0 : userList.size();
		pages = CLTUtil.calcPages(resultSize, pageSize);
		result = CLTUtil.cutResult(userList, this.pageNo, pageSize);
		
		if( result == null ) {
			addActionMessage("No data found.");
			return INPUT;
		}
		
		request.setAttribute(CLTUtil.PAGE_SIZE, "" + pageSize);
        request.setAttribute(CLTUtil.RESULT_SIZE, "" + resultSize);
        request.setAttribute(CLTUtil.PAGES, "" + pages);
        request.setAttribute(CLTUtil.PAGENO, "" + this.pageNo);        
        request.setAttribute("userId", this.userId);
        request.setAttribute("roleId", this.roleId);       
		request.setAttribute("result", result);
		
		return SUCCESS;
	}
	
	public String disableUser() throws Exception{
		
		request= ServletActionContext.getRequest();
		String curUser="admin";		
		int updateNum=0;
		SysUserTo curUserTo =  (SysUserTo)  request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
		if (null != curUserTo) {
			curUser = curUserTo.getUserId();
		}

		hidUserChkList = null != hidUserChkList ? hidUserChkList : new String[]{};
		hidUserList = null != hidUserList ? hidUserList : new String[]{};
				
		if(hidUserChkList.length > 0 ) {
			UserDao userDAO = new UserDao();					
			for(int i =0; i < hidUserChkList.length; i++ ) {
				// 先確定active 狀態是否改變
				String dbActive = userDAO.getUserDetail(hidUserList[i].toString()).getActive().toString();
				if(!dbActive.equals(hidUserChkList[i].toString())){
					updateNum += userDAO.updateUserActive(hidUserList[i].toString(),curUser,hidUserChkList[i].toString());
				}
			}
		}
		addActionMessage("Action had been finished, Updated " + updateNum + " record(s).");
		
		return SUCCESS;
	}
	
	public String userCreatePre() throws Exception {		
		
		DepartmentDao departmentDAO = new DepartmentDao();		
		ArrayList department = new ArrayList();
		department 	= (ArrayList) departmentDAO.searchDepartmentList("");
		
		request= ServletActionContext.getRequest();		
		request.setAttribute("department", department);
		
        return SUCCESS;		
	}
	
	//Get Himax AD data
	public String fetchADUser() throws Exception {
	
		String userId = request.getParameter("userId");
		userId = null!=userId?userId:"";
			
		LDAPConfigService ldapConfigService = new LDAPConfigService();					
		SysUserTo adUser = ldapConfigService.getOUUser(userId);			   
		    
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
			
		StringBuilder sb = new StringBuilder();
		PrintWriter out = response.getWriter();
		String employeId , realName, email, mailDomain;
			
		logger.debug(adUser);
			
		try{
			if (adUser != null) {							
				employeId = adUser.getUserId();
				employeId = null!=employeId?employeId:"";				
				email = adUser.getEmail().substring(0,
							adUser.getEmail().indexOf("@"));
				email = null != email ? email : "";				
				
				mailDomain = adUser.getEmail().substring(adUser.getEmail().indexOf("@") + 1,
												adUser.getEmail().length());
				mailDomain = null != mailDomain ? mailDomain : "";
				
				realName = adUser.getRealName();
				realName = null!=realName?realName:"";				
				
				sb.append(employeId).append("|").append(email).append("|").
								append(mailDomain).append("|").append(realName.trim());
				logger.debug(sb);
				
				out.println(sb);
				out.close();
				return NONE;
			}else {
				out.print("ERROR");
				out.close();
				logger.debug("ERROR");
				return NONE;
			}
		}catch(Exception e){
			out.print("ERROR");
			out.close();
			logger.debug("ERROR");
			return NONE;
		}
	}
	
	public String userSave() throws Exception {
		
		 // 1.0 Get Current User
		SysUserTo curUser = (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
		SysUserTo newUser = new SysUserTo();
		UserDao userDao = new UserDao();
		UserRoleDao userRoleDao = new UserRoleDao();
		
		String userRole = this.userRole;
		
		//1.1 Set Himax & Subcon 相同共有的值
		newUser.setUserId(this.userId);
		newUser.setRealName(this.realName);
		newUser.setCreateBy(curUser.getUserId());
		newUser.setPhoneNum(this.phoneNum);
		newUser.setDepartment(this.department);
		newUser.setPosition(this.position);
		newUser.setEmail(this.email);		
		newUser.setActive("1");
		newUser.setProcessId("");
		
		//1.2 user 已經存在判斷
		SysUserTo oldUser = new SysUserTo();
		oldUser = userDao.getActiveCurUser(this.userId);
		if( oldUser != null){
			addActionMessage(getText("IE.createUser.message.fail.userIdIsInDB"));
			return INPUT;
		}		
		//1.3 Get Himax User Form Data
		if ( userRole.equals(CLTUtil.CLT_ROLE)){
			logger.debug("Himax User");			
			newUser.setCompany("Himax");
			newUser.setPassword("N/A");			
		}else{
			logger.debug("Subcon User");
			newUser.setCompany(this.company);
			newUser.setPassword(CLTUtil.encodePasswd(password));
		}
		
		// 新增 user
		int result = userDao.insertUser(newUser);		
		if (result != 1){
			addActionMessage(getText("IE.createUser.message.fail.insertUserError"));
			return INPUT;
		}
		
//		// 新增 user role
//		// 腳色 身分判斷 ???????????????????????????????????????????????
//		int intRoleId = (userRole.equals(CLTUtil.HIMAX_ROLE)?2:3);
//		// ??????????????????????????????????????????????
//		result = userRoleDao.insertUserRole(this.userId, intRoleId);
//		if (result != 1){
//			addActionMessage(getText("IE.createUser.message.fail.insertUserRoleError"));
//			// 新增失敗,刪除user
//			userDao.deleteUser(this.userId);
//			return INPUT;
//		}
		
		// send mail to new user		
		String mailSubject = CLTUtil.getMessage("IE.email.createUser.subject", this.realName);				
		CLTUtil.sendInitialUserMail(request , response , mailSubject, newUser,curUser.getEmail());

		addActionMessage(getText("IE.createUser.message.success.insertUserInDB"));
		return SUCCESS;
	}
	
	// for user profile	
	public String userProfilePre() throws Exception{		
	
		SysUserTo inUser = new SysUserTo();	
		UserDao userDAO = new UserDao();
		
		session = ServletActionContext.getRequest().getSession(true);
		inUser = (SysUserTo) session.getAttribute(CLTUtil.CUR_USERINFO); 
		inUser = userDAO.getUserDetail(inUser.getUserId());		
		request.setAttribute("CurUser", inUser);	
		
		return SUCCESS;
	}
	
	public String userProfile() throws Exception{
		
		UserDao userDAO = new UserDao();
		SysUserTo curUser = new SysUserTo();		
		curUser = userDAO.getActiveCurUser(this.userId);
		
		if (curUser != null){
			
			this.phoneNum = this.phoneNum != null ? this.phoneNum :"";			
			curUser.setPhoneNum(this.phoneNum);
			curUser.setLastIp(this.lastIp);
			curUser.setLastTime(this.lastTime);
			curUser.setUpdateBy(this.userId);
			curUser.setPassword(CLTUtil.encodePasswd(password));
			int result = userDAO.updateUser(curUser);
			if (result != 1){
				addActionMessage(getText("IE.modifyProfile.message.fail"));
				return INPUT;
			}else
				addActionMessage(getText("IE.modifyProfile.message.success"));
		}
		
		curUser = userDAO.getActiveCurUser(this.userId);
		request.setAttribute("CurUser", curUser);	
		
		// reset session user data (修改自己 更新session)
		session = ServletActionContext.getRequest().getSession(true);
		session.removeAttribute(CLTUtil.CUR_USERINFO);		
		session.setAttribute(CLTUtil.CUR_USERINFO,curUser);
		
		return SUCCESS;
	}
	
	// modify user data
	public String modifyUserPre() throws Exception{
		
		logger.debug(this.userCompany);
		
		UserDao userDao = new UserDao();
		RoleDao roleDao = new RoleDao();
		UserRoleDao userRoleDao = new UserRoleDao();
		SysUserRoleTo curUserRole = new SysUserRoleTo();

		DepartmentDao departmentDao = new DepartmentDao();
		SAPVendorDao  sapVendorDao = new SAPVendorDao();
		SAPCustomerDao sapCustomerDao = new SAPCustomerDao();
		
		// user Detail - for all
		SysUserTo curUser = new SysUserTo();
		curUser = userDao.getUserDetail(this.selectUserId);
		request.setAttribute("CurUser", curUser);
		
		// department List - for himax user
		ArrayList departmentList = new ArrayList();
		departmentList 	= (ArrayList) departmentDao.searchDepartmentList("");
		request.setAttribute("DepartmentList", departmentList);
		
		// company List
		ArrayList companyList = new ArrayList();
		
		if (this.userCompany.equals(CLTUtil.VENDOR_ROLE)){		
			companyList = (ArrayList) sapVendorDao.getAllVendor();
		}else if (this.userCompany.equals(CLTUtil.CUSTOMER_ROLE)){
			companyList = (ArrayList) sapCustomerDao.getAllCustomer();
		}
		
		request.setAttribute("userCompany",this.userCompany);
		request.setAttribute("CompanyList", companyList);	
		
		return INPUT;
	}
	
	public String modifyUser() throws Exception{
		UserDao userDao = new UserDao();
		UserRoleDao userRoleDao = new UserRoleDao();
		SysUserTo newUser = new SysUserTo();		
		newUser = userDao.getUserDetail(this.userId);
		
		SysUserTo curUser = (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
		
		if (newUser != null){
			String strCompany = newUser.getCompany()!=null?newUser.getCompany():"";
			newUser.setPhoneNum(this.phoneNum == null ? "": this.phoneNum);
			newUser.setRealName(this.realName);
			newUser.setDepartment(this.department);
			newUser.setPosition(this.position);
			newUser.setUpdateBy(this.updateBy);			
			
			//判斷user
			if (strCompany.equals(CLTUtil.CLT_ROLE)){
				newUser.setEmail(this.email);
			}else{			
				newUser.setPassword(CLTUtil.encodePasswd(this.password));
				newUser.setCompany(this.company);
			}	
				
			// set user
			int updateUserResult = userDao.updateUser(newUser);			
			if (updateUserResult != 1){
				addActionMessage(getText("IE.user.modify.message.modify.fail"));
				return INPUT ;							
			}
			
			// 更新自己時 ,重設session 資料
			if (curUser.getUserId().equals(this.userId)){
				session = ServletActionContext.getRequest().getSession(true);
				session.removeAttribute(CLTUtil.CUR_USERINFO);		
				session.setAttribute(CLTUtil.CUR_USERINFO,curUser);
			}
			
//			// set user role			
//			int updateUserRoleResult = userRoleDao.updateUserRole(chkRolesList,this.userId);
//			if (updateUserRoleResult != 1){						
//				addActionMessage(getText("IE.user.modify.message.modifyUserRole.fail"));
//				return INPUT ;	
//			}
		}	
		addActionMessage(getText("IE.user.modify.message.modify.success"));
		return SUCCESS;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String[] getHidUserList() {
		return hidUserList;
	}

	public void setHidUserList(String[] hidUserList) {
		this.hidUserList = hidUserList;
	}

	public String[] getHidUserChkList() {
		return hidUserChkList;
	}

	public void setHidUserChkList(String[] hidUserChkList) {
		this.hidUserChkList = hidUserChkList;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getSelectUserId() {
		return selectUserId;
	}

	public void setSelectUserId(String selectUserId) {
		this.selectUserId = selectUserId;
	}

	public String getChkRolesList() {
		return chkRolesList;
	}

	public void setChkRolesList(String chkRolesList) {
		this.chkRolesList = chkRolesList;
	}

	public String getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
	}
	public String getQuoteHeaderUid() {
		return quoteHeaderUid;
	}
	public void setQuoteHeaderUid(String quoteHeaderUid) {
		this.quoteHeaderUid = quoteHeaderUid;
	}


}
