package com.cista.system.account.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.cista.system.account.dao.FunctionDao;
import com.cista.system.account.dao.RoleDao;
import com.cista.system.account.dao.RoleFunctionDao;
import com.cista.system.to.SysFunctionTo;
import com.cista.system.to.SysRoleFunctionTo;
import com.cista.system.util.BaseAction;

public class RoleFunctionManager extends BaseAction{
	
	private String roleId;
	private String leaf;
	private String[] chkModifyList;
	private String functionId;
	
	public String searchRoleFunctionPre() throws Exception{
	
		request= ServletActionContext.getRequest();
		
		// rolelist
		RoleDao roleDAO = new RoleDao();	
		List  data = new ArrayList();		
		data = (ArrayList)roleDAO.searchRoleList("");
		request.setAttribute("roleList", data);
		
		return SUCCESS;
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
