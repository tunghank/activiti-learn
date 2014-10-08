package com.cista.system.account.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

import com.cista.system.account.dao.RoleDao;
import com.cista.system.account.dao.UserDao;
import com.cista.system.account.dao.UserRoleDao;

import com.cista.system.to.ExtJSGridTo;
import com.cista.system.to.SysRoleTo;
import com.cista.system.to.SysUserRoleTo;
import com.cista.system.util.BaseAction;
import com.cista.system.util.CistaUtil;
import com.google.gson.Gson;
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
			
			logger.debug("userRoleList " + userRoleList.toString());
			if( userRoleList != null ){
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
		
		request= ServletActionContext.getRequest();
		RoleDao roleDAO = new RoleDao();	
		List<SysRoleTo>  data = new ArrayList<SysRoleTo>();		
		data = roleDAO.searchRoleList("");
		request.setAttribute("roleList", data);		
		
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
