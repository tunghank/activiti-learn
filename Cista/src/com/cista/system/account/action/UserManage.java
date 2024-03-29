package com.cista.system.account.action;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

import com.cista.system.account.dao.DepartmentDao;
import com.cista.system.account.dao.RoleDao;
import com.cista.system.account.dao.UserDao;
import com.cista.system.account.dao.UserRoleDao;
import com.cista.system.dao.SAPCustomerDao;
import com.cista.system.dao.SAPVendorDao;
import com.cista.system.ldap.service.LDAPConfigService;
import com.cista.system.to.ExtJSGridTo;
import com.cista.system.to.SysRoleTo;
import com.cista.system.to.SysUserRoleTo;
import com.cista.system.to.SysUserTo;
import com.cista.system.util.BaseAction;
import com.cista.system.util.CistaUtil;

import com.google.gson.Gson;

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

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	//20110629
	public String UserSearchPre() throws Exception {
		
		UserDao userDAO = new UserDao();
		request= ServletActionContext.getRequest();
		ArrayList<SysRoleTo> roleList = (ArrayList<SysRoleTo>)userDAO.getAllRoles();
		roleList =null!=roleList?roleList:new ArrayList<SysRoleTo>();
		
		request.setAttribute("roleList", roleList);
			
		return SUCCESS;
	}

	public String AjaxUserSearchLike() throws Exception {
		
		try {
			request= ServletActionContext.getRequest();
			//for paging        
	        int total;//分頁。。。數據總數       
	        int iStart;//分頁。。。每頁開始數據
	        int iLimit;//分頁。。。每一頁數據
	        
	        String start = request.getParameter("start");
            String limit = request.getParameter("limit");
            iStart = Integer.parseInt(start);
            iLimit = Integer.parseInt(limit);
            
            logger.debug("start: " + Integer.parseInt(start));
            logger.debug("limit: " + limit);
			
            
			String userId = request.getParameter("query"); 
			userId = null != userId ? userId : "";
			logger.debug("userId " + userId);
			
	        UserDao userDAO = new UserDao();		
	
			List<SysUserTo> userList = new ArrayList<SysUserTo>();
			
			if(userId.equals("")){
				userList = (ArrayList<SysUserTo>)userDAO.showAllUsers();
			}else{
				userList = userDAO.getUsersDetailList(userId);
			}
			
			//logger.debug("userList Size " + userList.size());
			//分頁
			total=userList.size();
			int end=iStart+iLimit;
			if(end>total){//不能總數
				end=total;
			}		
			
			List<SysUserTo> resultList = new ArrayList<SysUserTo>();
			for(int i=iStart;i<end;i++){//只加載當前頁面數據
				//logger.debug(userList.get(i).toString());
				resultList.add(userList.get(i));
			}
			//logger.debug(resultList.toString());
			
			ExtJSGridTo extJSGridTo = new ExtJSGridTo();
			extJSGridTo.setTotal(total);
			extJSGridTo.setRoot(resultList);
			
			Gson gson = new Gson();
			String jsonData = gson.toJson(extJSGridTo);
			//logger.debug(jsonData);
			
			// 1.5 Set AJAX response
			CistaUtil.ajaxResponseData(response, jsonData);			

		} catch (Exception e) {
			this.addActionMessage("ERROR");
			e.printStackTrace();
			logger.error(e.toString());
			addActionMessage(e.toString());
          	//AJAX
          	try{
  		    	response.setContentType("text/html; charset=UTF-8");
  				PrintWriter out = response.getWriter();
  				String returnResult = "ERROR" ;

  				logger.debug(returnResult);
  				logger.debug("Error");
  				out.print(returnResult);
  				out.close();
          	}catch(Exception ex){
          		ex.printStackTrace();
                logger.error(ex.toString());
                return NONE;
          	}

		}
		
   		return NONE;
	}
	
	public String AjaxUserSearch() throws Exception {
		
		try {
			request= ServletActionContext.getRequest();
			//for paging        
	        int total;//分頁。。。數據總數       
	        int iStart;//分頁。。。每頁開始數據
	        int iLimit;//分頁。。。每一頁數據
	        
	        String start = request.getParameter("start");
            String limit = request.getParameter("limit");
            iStart = Integer.parseInt(start);
            iLimit = Integer.parseInt(limit);
            
            logger.debug("start: " + Integer.parseInt(start));
            logger.debug("limit: " + limit);
			
            
			String userId = request.getParameter("query"); 
			userId = null != userId ? userId : "";
			logger.debug("userId " + userId);
			
	        UserDao userDAO = new UserDao();		
	
			List<SysUserTo> userList = new ArrayList<SysUserTo>();
			
			if(userId.equals("")){
				userList = (ArrayList<SysUserTo>)userDAO.showAllUsers();
			}else{
				userList = userDAO.getUserDetailList(userId);
			}
			
			//logger.debug("userList Size " + userList.size());
			//分頁
			total=userList.size();
			int end=iStart+iLimit;
			if(end>total){//不能總數
				end=total;
			}		
			
			List<SysUserTo> resultList = new ArrayList<SysUserTo>();
			for(int i=iStart;i<end;i++){//只加載當前頁面數據
				//logger.debug(userList.get(i).toString());
				resultList.add(userList.get(i));
			}
			//logger.debug(resultList.toString());
			
			ExtJSGridTo extJSGridTo = new ExtJSGridTo();
			extJSGridTo.setTotal(total);
			extJSGridTo.setRoot(resultList);
			
			Gson gson = new Gson();
			String jsonData = gson.toJson(extJSGridTo);
			//logger.debug(jsonData);
			
			// 1.5 Set AJAX response
			CistaUtil.ajaxResponseData(response, jsonData);			

		} catch (Exception e) {
			this.addActionMessage("ERROR");
			e.printStackTrace();
			logger.error(e.toString());
			addActionMessage(e.toString());
          	//AJAX
          	try{
  		    	response.setContentType("text/html; charset=UTF-8");
  				PrintWriter out = response.getWriter();
  				String returnResult = "ERROR" ;

  				logger.debug(returnResult);
  				logger.debug("Error");
  				out.print(returnResult);
  				out.close();
          	}catch(Exception ex){
          		ex.printStackTrace();
                logger.error(ex.toString());
                return NONE;
          	}

		}
		
   		return NONE;
	}

	
	public String AjaxUserDelete() throws Exception {
		
		try {
			request= ServletActionContext.getRequest();
			//for paging        
	        int total;//分頁。。。數據總數       
	        int iStart;//分頁。。。每頁開始數據
	        int iLimit;//分頁。。。每一頁數據
	        
	        String start = request.getParameter("start");
            String limit = request.getParameter("limit");
            iStart = Integer.parseInt(start);
            iLimit = Integer.parseInt(limit);
            
            logger.debug("start: " + Integer.parseInt(start));
            logger.debug("limit: " + limit);
			
            
			String userId = request.getParameter("query"); 
			userId = null != userId ? userId : "";
			logger.debug("userId " + userId);
			
	        UserDao userDAO = new UserDao();		
	
			List<SysUserTo> userList = new ArrayList<SysUserTo>();
			String messageString="";
			
			if(userId.equals("")){
				messageString = getText("System.createUser.message.fail.deleteUserInDB" + " " + "No User ID");
				CistaUtil.ajaxResponse(response, messageString, CistaUtil.AJAX_RSEPONSE_ERROR);
				
				return NONE;
			}else{
				int i  = userDAO.deleteUser(userId);
				if (i <= 0){
					messageString = getText("System.createUser.message.fail.deleteUserInDB");
					CistaUtil.ajaxResponse(response, messageString, CistaUtil.AJAX_RSEPONSE_ERROR);
					
					return NONE;
				}else{
					userList = (ArrayList<SysUserTo>)userDAO.showAllUsers();
				}
			}
			
			//logger.debug("userList Size " + userList.size());
			//分頁
			total=userList.size();
			int end=iStart+iLimit;
			if(end>total){//不能總數
				end=total;
			}		
			
			List<SysUserTo> resultList = new ArrayList<SysUserTo>();
			for(int i=iStart;i<end;i++){//只加載當前頁面數據
				//logger.debug(userList.get(i).toString());
				resultList.add(userList.get(i));
			}
			//logger.debug(resultList.toString());
			
			ExtJSGridTo extJSGridTo = new ExtJSGridTo();
			extJSGridTo.setTotal(total);
			extJSGridTo.setRoot(resultList);
			
			Gson gson = new Gson();
			String jsonData = gson.toJson(extJSGridTo);
			//logger.debug(jsonData);
			
			// 1.5 Set AJAX response
			CistaUtil.ajaxResponseData(response, jsonData);

		} catch (Exception e) {
			this.addActionMessage("ERROR");
			e.printStackTrace();
			logger.error(e.toString());
			addActionMessage(e.toString());
          	//AJAX
          	try{
  		    	response.setContentType("text/html; charset=UTF-8");
  				PrintWriter out = response.getWriter();
  				String returnResult = "ERROR" ;

  				logger.debug(returnResult);
  				logger.debug("Error");
  				out.print(returnResult);
  				out.close();
          	}catch(Exception ex){
          		ex.printStackTrace();
                logger.error(ex.toString());
                return NONE;
          	}

		}
		
   		return NONE;
	}
	
	public String AjaxUserDisable() throws Exception {
		
		try {
			request= ServletActionContext.getRequest();
			//for paging        
	        int total;//分頁。。。數據總數       
	        int iStart;//分頁。。。每頁開始數據
	        int iLimit;//分頁。。。每一頁數據
	        
	        String start = request.getParameter("start");
            String limit = request.getParameter("limit");
            iStart = Integer.parseInt(start);
            iLimit = Integer.parseInt(limit);
            
            logger.debug("start: " + Integer.parseInt(start));
            logger.debug("limit: " + limit);
			
            
			String userId = request.getParameter("query"); 
			userId = null != userId ? userId : "";
			logger.debug("userId " + userId);
			
	        UserDao userDAO = new UserDao();		
	
			List<SysUserTo> userList = new ArrayList<SysUserTo>();
			String messageString="";
			
			if(userId.equals("")){
				messageString = getText("System.createUser.message.fail.disableUserInDB" + " " + "No User ID");
				CistaUtil.ajaxResponse(response, messageString, CistaUtil.AJAX_RSEPONSE_ERROR);
				
				return NONE;
			}else{
				SysUserTo curUser = (SysUserTo)request.getSession().getAttribute(CistaUtil.CUR_USERINFO);
				logger.debug("Cur User : " + curUser.getUserId());
				int i  = userDAO.disableUser(userId, curUser.getUserId());
				if (i <= 0){
					messageString = getText("System.createUser.message.fail.disableUserInDB");
					CistaUtil.ajaxResponse(response, messageString, CistaUtil.AJAX_RSEPONSE_ERROR);
					
					return NONE;
				}else{
					userList = (ArrayList<SysUserTo>)userDAO.showAllUsers();
				}
			}
			
			//logger.debug("userList Size " + userList.size());
			//分頁
			total=userList.size();
			int end=iStart+iLimit;
			if(end>total){//不能總數
				end=total;
			}		
			
			List<SysUserTo> resultList = new ArrayList<SysUserTo>();
			for(int i=iStart;i<end;i++){//只加載當前頁面數據
				//logger.debug(userList.get(i).toString());
				resultList.add(userList.get(i));
			}
			//logger.debug(resultList.toString());
			
			ExtJSGridTo extJSGridTo = new ExtJSGridTo();
			extJSGridTo.setTotal(total);
			extJSGridTo.setRoot(resultList);
			
			Gson gson = new Gson();
			String jsonData = gson.toJson(extJSGridTo);
			//logger.debug(jsonData);
			
			// 1.5 Set AJAX response
			CistaUtil.ajaxResponseData(response, jsonData);

		} catch (Exception e) {
			this.addActionMessage("ERROR");
			e.printStackTrace();
			logger.error(e.toString());
			addActionMessage(e.toString());
          	//AJAX
          	try{
  		    	response.setContentType("text/html; charset=UTF-8");
  				PrintWriter out = response.getWriter();
  				String returnResult = "ERROR" ;

  				logger.debug(returnResult);
  				logger.debug("Error");
  				out.print(returnResult);
  				out.close();
          	}catch(Exception ex){
          		ex.printStackTrace();
                logger.error(ex.toString());
                return NONE;
          	}

		}
		
   		return NONE;
	}
	
	
	public String disableUser() throws Exception{
		
		request= ServletActionContext.getRequest();
		String curUser="admin";		
		int updateNum=0;
		SysUserTo curUserTo =  (SysUserTo)  request.getSession().getAttribute(CistaUtil.CUR_USERINFO);
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
	
	/**
	 * UserCreatePre
	 * @return 
	 * @throws Exception
	 */
	
	public String UserCreatePre() throws Exception {		
		
		
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
	
	/**
	 * UserSave
	 * @return
	 * @throws Exception
	 */
	public String UserSave() throws Exception {

		
		try {
			String messageString = "Save Finish";
			 // 1.0 Get Current User
			request= ServletActionContext.getRequest();
			SysUserTo curUser = (SysUserTo)request.getSession().getAttribute(CistaUtil.CUR_USERINFO);
			logger.debug("Cur User : " + curUser.getUserId());
			
			UserDao userDao = new UserDao();
			
			//1.2 GET AJAX Data
			String data = request.getParameter("data");
			data = null != data ? data : "";
			logger.debug("data " + data);
			
			//final List<SysUserTo> addUserList= JsonUtil.getList4Json(data , SysUserTo.class);
			Gson gson = new Gson();
			SysUserTo userInfo = gson.fromJson(data, SysUserTo.class);
			
			logger.debug(userInfo.toString());	
			
			//1.3 Prepare Save Data

			String companyType = userInfo.getCompanyType();
			companyType = null != companyType ? companyType : "";
			if(companyType.equals("1") ){
				//Cista
				userInfo.setCompany(CistaUtil.CISTA_SITE);
			}else if(companyType.equals("2") ){
				//Customer or Vendor
				
			}
			//1.3.1 Password
			userInfo.setPassword(CistaUtil.encodePasswd(userInfo.getPassword()));
			
			//1.3.2 Active
			if(userInfo.getActive() == null){
				//Not Active
				userInfo.setActive(CistaUtil.USER_INACTIVE);
			}else{
				userInfo.setActive(CistaUtil.USER_ACTIVE);
			}

			//1.3.3 Set Creator , Create Date , Update By, Update Date
			userInfo.setCreateBy(curUser.getUserId());
			Calendar cal = Calendar.getInstance();
			userInfo.setCdt(cal.getTime());
			
			userInfo.setUpdateBy(curUser.getUserId());
			userInfo.setUdt(cal.getTime());
			
			String editStatus = userInfo.getEditStatus();
			editStatus = null != editStatus ? editStatus : "";
			
			logger.debug("editStatus " + editStatus);
			
			//1.4 Insert DB
			if(editStatus.equals("1")) {//Modify User
				//1.4.0 Update User
				int result = userDao.updateUser(userInfo);		
				if (result < 1){
					
					messageString = getText("System.createUser.message.fail.updateUserError");
					CistaUtil.ajaxResponse(response, messageString, CistaUtil.AJAX_RSEPONSE_ERROR);
					
					return NONE;
				}
				messageString = "Update Finish";
			}else{//Create New User
				
				//判斷 1.4.1 User 是否已經存在
				SysUserTo oldUser = userDao.getActiveCurUser(userInfo.getUserId());
				if( oldUser != null){
					
					messageString = getText("System.createUser.message.fail.userIdIsInDB");
					CistaUtil.ajaxResponse(response, messageString, CistaUtil.AJAX_RSEPONSE_ERROR);
	
					return NONE;
				}
				
				// 新增 1.4.2
				int result = userDao.insertUser(userInfo);		
				if (result < 1){
					
					messageString = getText("System.createUser.message.fail.insertUserError");
					CistaUtil.ajaxResponse(response, messageString, CistaUtil.AJAX_RSEPONSE_ERROR);
					
					return NONE;
				}
				messageString = "Save Finish";

			}
			// 1.5 Set AJAX response
			CistaUtil.ajaxResponse(response, messageString, CistaUtil.AJAX_RSEPONSE_FINISH);
						
			// send mail to new user		
			//String mailSubject = CistaUtil.getMessage("IE.email.createUser.subject", this.realName);				
			//CistaUtil.sendInitialUserMail(request , response , mailSubject, newUser,curUser.getEmail());

			//addActionMessage(getText("IE.createUser.message.success.insertUserInDB"));
		} catch (Exception e) {
			this.addActionMessage("ERROR");
			e.printStackTrace();
			logger.error(e.toString());
			addActionMessage(e.toString());
          	//AJAX Message
        	CistaUtil.ajaxResponse(response, e.toString(), CistaUtil.AJAX_RSEPONSE_ERROR);
		}
		
   		return NONE;
	}
	
	// for user profile	
	public String userProfilePre() throws Exception{		
	
		SysUserTo inUser = new SysUserTo();	
		UserDao userDAO = new UserDao();
		
		session = ServletActionContext.getRequest().getSession(true);
		inUser = (SysUserTo) session.getAttribute(CistaUtil.CUR_USERINFO); 
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
			//curUser.setLastTime(this.lastTime);
			curUser.setUpdateBy(this.userId);
			curUser.setPassword(CistaUtil.encodePasswd(password));
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
		session.removeAttribute(CistaUtil.CUR_USERINFO);		
		session.setAttribute(CistaUtil.CUR_USERINFO,curUser);
		
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
		
		if (this.userCompany.equals(CistaUtil.VENDOR_ROLE)){		
			companyList = (ArrayList) sapVendorDao.getAllVendor();
		}else if (this.userCompany.equals(CistaUtil.CUSTOMER_ROLE)){
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
		
		SysUserTo curUser = (SysUserTo) request.getSession().getAttribute(CistaUtil.CUR_USERINFO);
		
		if (newUser != null){
			String strCompany = newUser.getCompany()!=null?newUser.getCompany():"";
			newUser.setPhoneNum(this.phoneNum == null ? "": this.phoneNum);
			newUser.setRealName(this.realName);
			newUser.setDepartment(this.department);
			newUser.setPosition(this.position);
			newUser.setUpdateBy(this.updateBy);			
			
			//判斷user
			if (strCompany.equals(CistaUtil.CISTA_ROLE)){
				newUser.setEmail(this.email);
			}else{			
				newUser.setPassword(CistaUtil.encodePasswd(this.password));
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
				session.removeAttribute(CistaUtil.CUR_USERINFO);		
				session.setAttribute(CistaUtil.CUR_USERINFO,curUser);
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



}
