package com.cista.system.account.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.cista.system.account.dao.FunctionDao;
import com.cista.system.account.dao.RoleDao;
import com.cista.system.account.dao.RoleFunctionDao;
import com.cista.system.account.dao.UserDao;
import com.cista.system.to.ExtJSGridTo;
import com.cista.system.to.SysFunctionTo;
import com.cista.system.to.SysRoleFunctionTo;
import com.cista.system.to.SysRoleTo;
import com.cista.system.to.SysUserTo;
import com.cista.system.util.BaseAction;
import com.cista.system.util.CistaUtil;
import com.google.gson.Gson;

public class RoleFunctionManager extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roleId;
	private String leaf;
	private String[] chkModifyList;
	private String functionId;
	
	public String SearchRoleFunctionPre() throws Exception{
	
		request= ServletActionContext.getRequest();
		
		return SUCCESS;
	}
	
	public String AjaxRoleSearchLike() throws Exception {
		
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
			
            
			String roleName = request.getParameter("query"); 
			roleName = null != roleName ? roleName : "";
			logger.debug("roleName " + roleName);
			
			RoleDao roleDao = new RoleDao();		
	
			List<SysRoleTo> roleList = new ArrayList<SysRoleTo>();
			
			if(roleName.equals("")){
				roleList = (ArrayList<SysRoleTo>)roleDao.getRoleList();
			}else{
				roleList = roleDao.searchRoleList(roleName);
			}
			
			//logger.debug("userList Size " + userList.size());
			//分頁
			total=roleList.size();
			int end=iStart+iLimit;
			if(end>total){//不能總數
				end=total;
			}		
			
			List<SysRoleTo> resultList = new ArrayList<SysRoleTo>();
			for(int i=iStart;i<end;i++){//只加載當前頁面數據
				//logger.debug(userList.get(i).toString());
				resultList.add(roleList.get(i));
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
	
	
	public String searchRoleFunction() throws Exception{
		
		request = ServletActionContext.getRequest();
		this.leaf = (null != this.leaf ? this.leaf : "");	
		
		// function list
		FunctionDao functionDao = new FunctionDao();
		List  functionData = new ArrayList();	
		functionData = (ArrayList) functionDao.getClsFunction(this.leaf);	
		request.setAttribute("functionList", functionData );
		
		// role function
		RoleFunctionDao roleFunctionDao = new RoleFunctionDao();		
		List  data = new ArrayList();		
		data = (ArrayList) roleFunctionDao.searchRoleFunctionList(this.leaf,this.roleId);
		if (data == null){
			addActionMessage(getText("Not setting role function mapping !!"));	
			return INPUT;
		}		
		request.setAttribute("Data", data);
		
		// for modify
		request.setAttribute("roleId", this.roleId);
		request.setAttribute("leaf", this.leaf);
	    
		return SUCCESS;
	}
	
	public String modifyRoleFunction() throws Exception {		
		
		List  data = new ArrayList();
		RoleFunctionDao roleFunctionDao = new RoleFunctionDao();	
		
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
		data = (ArrayList) roleFunctionDao.searchRoleFunctionList(this.leaf,this.roleId);
		List<SysRoleFunctionTo> roleFunctionList = (List<SysRoleFunctionTo>) data;
		if (roleFunctionList !=null && roleFunctionList.size()>0){
			for(SysRoleFunctionTo info : roleFunctionList){
				existFunction.add(info.getFunctionId());
				temp += info.getFunctionId() +",";
			}			
		}
		logger.debug(temp); temp ="";
		
		// 複製一份 (網頁上)
		ArrayList copySelect = new ArrayList();
		for (int i = 0 ; i < modifyFunction.size() ; i++ ){
			copySelect.add(modifyFunction.get(i));	
		}

		// need add (網 -DB)
		for (int i = 0 ; i < existFunction.size() ; i++ ){
			for (int j = 0 ; j < modifyFunction.size() ; j++){
				if (modifyFunction.get(j).toString().equals(existFunction.get(i).toString())){
					modifyFunction.remove(j);
				}
			}		
		}
		for (int i = 0 ; i < modifyFunction.size() ; i++ ){
			int dbResult = roleFunctionDao.insertFunctionRole(this.roleId, modifyFunction.get(i).toString());
			if (dbResult != 1){			
				addActionMessage(getText("IE.roleFunction.create.message.modify.fail"));
				return INPUT;
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
			int dbResult = roleFunctionDao.deleteFunctionRole(this.roleId, existFunction.get(i).toString());			
			if (dbResult != 1){			
				addActionMessage(getText("IE.roleFunction.create.message.modify.fail"));
				return INPUT;
			}
			logger.debug(existFunction.get(i));
		}
		
		addActionMessage(getText("IE.roleFunction.create.message.modify.success"));
		return SUCCESS;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String[] getChkModifyList() {
		return chkModifyList;
	}

	public void setChkModifyList(String[] chkModifyList) {
		this.chkModifyList = chkModifyList;
	}

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}
}
