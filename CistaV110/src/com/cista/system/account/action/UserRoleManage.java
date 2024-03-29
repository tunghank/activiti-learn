package com.cista.system.account.action;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import com.cista.system.account.dao.RoleDao;
import com.cista.system.account.dao.UserDao;
import com.cista.system.account.dao.UserRoleDao;

import com.cista.system.to.ExtJSGridTo;
import com.cista.system.to.SysRoleTo;
import com.cista.system.to.SysUserRoleTo;
import com.cista.system.to.SysUserTo;
import com.cista.system.util.BaseAction;
import com.cista.system.util.CistaUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.java_cup.internal.internal_error;

import org.apache.struts2.ServletActionContext;

public class UserRoleManage extends BaseAction{
	

	private static final long serialVersionUID = 1L;
	private String roleId;
	private String[] chkModifyList;
	
	public String AjaxRoleList() throws Exception {
		
		try {
			request= ServletActionContext.getRequest();

			RoleDao roleDao = new RoleDao();		
	
			List<SysRoleTo> roleList = roleDao.getRoleList();
						
			Gson gson = new Gson();
			String jsonData = gson.toJson(roleList);
			logger.debug(jsonData);
			
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
	
	public String AjaxUserRoleList() throws Exception {
		
		try {
			request= ServletActionContext.getRequest();

			UserRoleDao userRoleDao = new UserRoleDao();		
	
			String userId = request.getParameter("query"); 
			userId = null != userId ? userId : "";
			logger.debug("userId " + userId);
			
			List<SysUserRoleTo> userRoleList = userRoleDao.getUserRoleList(userId);
						
			if( userRoleList != null ){
				logger.debug("userRoleList " + userRoleList.toString());
				
				ExtJSGridTo extJSGridTo = new ExtJSGridTo();
				extJSGridTo.setRoot(userRoleList);
				extJSGridTo.setTotal(userRoleList.size());
				Gson gson = new Gson();
				String jsonData = gson.toJson(extJSGridTo);
				logger.debug(jsonData);
				
				// 1.5 Set AJAX response
				CistaUtil.ajaxResponseData(response, jsonData);	
			}

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

	public String AjaxSaveUserRoleList() throws Exception {
		
		try {
			request= ServletActionContext.getRequest();
			//1.0 Get Data
			String data = request.getParameter("data"); 
			data = null != data ? data : "";
			logger.debug("data " + data);
			
			String userId = request.getParameter("userId"); 
			userId = null != userId ? userId : "";
			logger.debug("userId " + userId);
			
			UserRoleDao userRoleDao = new UserRoleDao();
			String messageString = "";
			
			
			//1.1 Parser JSON Data
			Gson gson = new Gson();
			//String str = gson.fromJson(data, String.class);
			Map<String,String> map=new HashMap<String,String>();
			map=(Map<String,String>) gson.fromJson(data, map.getClass());
			
			
			//Map<String, Object> map = new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {
			//}.getType());
				
			//1.2 Prepare Insert DB Data
			String[] roleArray = map.get("roleSelector").split(",");
			logger.debug("roleArray " + roleArray.length);
			
			if (roleArray.length >=1 && ( !roleArray[0].equals("") )  ) {
					
				List<SysUserRoleTo> roleList = new ArrayList<SysUserRoleTo>();
				Calendar nowTime = Calendar.getInstance();
				UUID uuid;
				for(int i=0; i < roleArray.length; i ++ ){
					uuid = UUID.randomUUID();
					SysUserRoleTo sysUserRoleTo = new SysUserRoleTo();
					sysUserRoleTo.setId(uuid.toString().toUpperCase());
					sysUserRoleTo.setUserId(userId);
					sysUserRoleTo.setRoleId( roleArray[i]);
					sysUserRoleTo.setCdt(nowTime.getTime());
					roleList.add(sysUserRoleTo);
				}
				
				//Insert DB
				
				userRoleDao.deleteUserRole(userId);
				int result []  = userRoleDao.batchInsertUserRole(roleList);
				
				//logger.debug("roleSelector " + map.get("roleSelector").toString());
				//logger.debug("roleSelector " + map.get("roleSelector").length());
				//logger.debug("uuid " + uuid.toString().toUpperCase());
				//logger.debug("uuid " + uuid.toString().toUpperCase().length());
				//logger.debug("roleList.size " + roleList.size() );
		
				if (result.length < 1){
					
					messageString = getText("System.createUser.message.fail.insertUserError");
					CistaUtil.ajaxResponse(response, messageString, CistaUtil.AJAX_RSEPONSE_ERROR);
					
					return NONE;
				}
				messageString = "Save Finish";
				// 1.5 Set AJAX response
				CistaUtil.ajaxResponse(response, messageString, CistaUtil.AJAX_RSEPONSE_FINISH);
			}else{
				/********************************
				 * 清空ROLE LIST
				 ********************************/
				
				//Delete Role
				int result = userRoleDao.deleteUserRole(userId);
				if (result < 0){
					
					messageString = getText("System.createUser.message.fail.insertUserError");
					CistaUtil.ajaxResponse(response, messageString, CistaUtil.AJAX_RSEPONSE_ERROR);
					
					return NONE;
				}
				messageString = "Save Finish";
				// 1.5 Set AJAX response
				CistaUtil.ajaxResponse(response, messageString, CistaUtil.AJAX_RSEPONSE_FINISH);
			}

			
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
	
	public String SearchUserRolePre() throws Exception {
		
	
		return SUCCESS;
	}
	
	public String searchUserRole() throws Exception {
		
		request = ServletActionContext.getRequest();
		UserDao userDao = new UserDao();
		UserRoleDao userRoleDao = new UserRoleDao();
		
		// user list		
		List  userData = new ArrayList();	
		userData = (ArrayList) userDao.getUserList("","");	
		request.setAttribute("userData", userData );
		
		this.roleId = null != this.roleId ? this.roleId : "";
		
		// user role List		
		List  userRoledata = new ArrayList();		
		userRoledata = (ArrayList) userRoleDao.searchUserRoleList(this.roleId,"");
		if ( userRoledata == null){
			addActionMessage(getText("Not match User Role !!"));	
			return INPUT;
		}		
		request.setAttribute("userRoledata",  userRoledata);
		
		// for modify
		request.setAttribute("roleId", this.roleId);
	    
		return SUCCESS;
	}
	
	public String modifyUserRole() throws Exception {		
		
		List  data = new ArrayList();
		UserRoleDao userRoleDao = new UserRoleDao();		
		
		// 網頁上
		String temp = "";
		ArrayList modifyFunction = new ArrayList();
		for (int i = 0; i <chkModifyList.length ; i++){
			modifyFunction.add(chkModifyList[i]);
			temp += chkModifyList[i].toString()+",";			
		}
		logger.debug(temp); temp ="";
				
		// 目前有的
		ArrayList existFunction = new ArrayList();
		data = new ArrayList();
		data = (ArrayList) userRoleDao.searchUserRoleList(this.roleId,"");
		List<SysUserRoleTo> userRoleList = (List<SysUserRoleTo>) data;
		if (userRoleList !=null && userRoleList.size()>0){
			for(SysUserRoleTo info : userRoleList){
				existFunction.add(info.getUserId());
				temp += info.getUserId() +",";
			}			
		}
		logger.debug(temp); temp ="";		
		
		// 複製一份 (網頁上)
		ArrayList copySelect = new ArrayList();
		for (int i = 0 ; i < modifyFunction.size() ; i++ ){
			copySelect.add(modifyFunction.get(i));	
		}

		// need add (網 -DB)
		data = null;
		data = new ArrayList();
		
		for (int i = 0 ; i < existFunction.size() ; i++ ){
			for (int j = 0 ; j < modifyFunction.size() ; j++){
				if (modifyFunction.get(j).toString().equals(existFunction.get(i).toString())){
					modifyFunction.remove(j);
				}
			}		
		}
		for (int i = 0 ; i < modifyFunction.size() ; i++ ){
			// 已經存在			
			data = (ArrayList) userRoleDao.searchUserRoleList("",modifyFunction.get(i).toString());
			if(data != null && data.size()>0){
				int dbResult = userRoleDao.updateUserRole(this.roleId , modifyFunction.get(i).toString() );			
				if (dbResult != 1){	
					addActionMessage(getText("IE.userRole.create.message.modify.fail"));
					return INPUT;
				}
			}else{
			// 尚未存在
				int dbResult = userRoleDao.insertUserRole(modifyFunction.get(i).toString(), Integer.parseInt(this.roleId));			
				if (dbResult != 1){	
					addActionMessage(getText("IE.userRole.create.message.modify.fail"));
					return INPUT;
				}
			}
			logger.debug(modifyFunction.get(i));
		}
		
		// 需要移除 (DB-網)		
		for (int i = 0 ; i < copySelect.size() ; i++ ){
			for (int j = 0 ; j < existFunction.size() ; j++){
				if (copySelect.get(i).toString().equals(existFunction.get(j).toString())){
					existFunction.remove(j);
				}
			}		
		}
		for (int i = 0 ; i < existFunction.size() ; i++ ){
			int dbResult = userRoleDao.deleteUserRole(existFunction.get(i).toString(), Integer.parseInt(this.roleId));			
			if (dbResult != 1){			
				addActionMessage(getText("IE.userRole.delete.message.delete.fail"));
				return INPUT;
			}
			logger.debug(existFunction.get(i));
		}
		
		addActionMessage(getText("IE.userRole.create.message.modify.success"));
		return SUCCESS;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String[] getChkModifyList() {
		return chkModifyList;
	}

	public void setChkModifyList(String[] chkModifyList) {
		this.chkModifyList = chkModifyList;
	}

}
