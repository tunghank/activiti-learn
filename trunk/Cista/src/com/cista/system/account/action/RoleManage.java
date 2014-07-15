package com.cista.system.account.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.cista.system.account.dao.FunctionDao;
import com.cista.system.account.dao.RoleDao;
import com.cista.system.account.dao.RoleFunctionDao;
import com.cista.system.ldap.service.LDAPConfigService;
import com.cista.system.to.SysRoleFunctionTo;
import com.cista.system.to.SysRoleTo;
import com.cista.system.util.BaseAction;
import com.cista.system.util.CLTUtil;

public class RoleManage extends BaseAction{	
	
	private String roleId;
	private String roleName;	
	private int pageNo;		
	private String[] chkDeleteList;
	private String selectRole;	
	
	public String roleCreatePre() throws Exception {
		return SUCCESS;
	}	
	
	public String roleCreate() throws Exception{
		
		request= ServletActionContext.getRequest();	
		RoleDao roleDAO = new RoleDao();
		SysRoleTo curRole = roleDAO.getRoleDetail(this.roleName,"");
		
		if (curRole != null){
			addActionMessage(getText("IE.role.create.message.isExist.fail"));
			return INPUT;
		}		
	
		int dbResult = roleDAO.insertRole(this.roleName);		
		if (dbResult != 1){
			addActionMessage(getText("IE.role.create.message.insert.fail"));
			return INPUT;
		}
		
		addActionMessage(getText("IE.role.create.message.insert.success"));
        return SUCCESS;		
	}
	
	public String searchRolePre()throws Exception{
		
		request= ServletActionContext.getRequest();
		RoleDao roleDAO = new RoleDao();	
		List  data = new ArrayList();		
		data = (ArrayList)roleDAO.searchRoleList("");
		
		request.setAttribute("roleList", data);	
		
		return SUCCESS;
	}
	
	public String searchRole()throws Exception{
		
		request = ServletActionContext.getRequest();
		this.roleName = (this.roleName != null ? this.roleName :"");
		this.pageNo = (0 == this.pageNo ? 1: this.pageNo);
		
		RoleDao roleDAO = new RoleDao();		
		List  data = new ArrayList();		
		data = (ArrayList) roleDAO.searchRoleList(this.roleName);
		
		//for paging
		int pageSize = CLTUtil.REPORT_PAGE_SIZE;
	    int resultSize = -1;
	    int pages = -1;
		
		List result = new ArrayList();
		resultSize = null == data? 0 : data.size();		
		pages = CLTUtil.calcPages(resultSize, pageSize);
		result = CLTUtil.cutResult(data, this.pageNo, pageSize);
		if (result == null){ 
			addActionMessage("No data found.");
			return INPUT;
		}
		
		// paging setting		
		request.setAttribute(CLTUtil.PAGE_SIZE, "" + pageSize);
	    request.setAttribute(CLTUtil.RESULT_SIZE, "" + resultSize);
	    request.setAttribute(CLTUtil.PAGES, "" + pages);
	    request.setAttribute(CLTUtil.PAGENO, "" + this.pageNo);
	    
	    // Criteria setting
	    request.setAttribute("roleName", "" + this.roleName);
	    
	    // result
	    request.setAttribute("Data", result);
		return SUCCESS;
	}

	public String deleteRole() throws Exception{
		
		RoleDao roleDAO = new RoleDao();
		RoleFunctionDao roleFunctionDAO = new RoleFunctionDao();
		
		for (int i = 0 ; i < chkDeleteList.length ; i++){
			
			List data =  new ArrayList();
			data = (ArrayList)roleFunctionDAO.searchRoleFunctionList("",chkDeleteList[i]);
			List<SysRoleFunctionTo> roleFunction = (List<SysRoleFunctionTo>) data ;			
			
			int dbResult = 0;
			// 刪除子 function role
			if (roleFunction != null){				
				for (SysRoleFunctionTo info : roleFunction){
					
					dbResult = roleFunctionDAO.deleteFunctionRole(info.getId().toString(), String.valueOf(info.getFunctionId()));
					if (dbResult != 1){
						addActionMessage(getText("IE.role.delete.message.deleteFunctionRole.fail"));						
						return INPUT;
					}				
				}
			}
			
			// 刪除 role
			dbResult = roleDAO.deleteRole(chkDeleteList[i]);			
			
			if (dbResult != 1){
				addActionMessage(getText("IE.role.delete.message.delete.fail"));				
				return INPUT;
			}
		}
		
		addActionMessage(getText("IE.role.delete.message.delete.success"));
		return SUCCESS;
	}
		
	public String modifyRolePre()throws Exception{
		
		request= ServletActionContext.getRequest();
		roleId = (selectRole==null) ? "" : selectRole;	
		
		RoleDao roleDAO = new RoleDao();
		SysRoleTo data = new SysRoleTo();		
		data = roleDAO.getRoleDetail("",roleId);	
		request.setAttribute("data", data);
		
		return INPUT;
	}	
	
	public String modifyRole() throws Exception{
		
		RoleDao roleDAO = new RoleDao();
		SysRoleTo curRole = new SysRoleTo();	
		this.roleId = (this.selectRole==null) ? "" : this.selectRole;
		
		// exist?
		curRole = roleDAO.getRoleDetail(this.roleName,"");		
		if (curRole != null){
			addActionMessage(getText("IE.role.create.message.isExist.fail"));
			return INPUT;
		}
		
		// modify
		curRole = roleDAO.getRoleDetail("",this.roleId);	
		curRole.setRoleName(this.roleName);
		int dbResult = roleDAO.updateRole(curRole);		
		if (dbResult != 1){
			addActionMessage(getText("IE.role.modify.message.modify.fail"));
			return INPUT;
		}		
		
		addActionMessage(getText("IE.role.modify.message.modify.success"));
		return SUCCESS;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String[] getChkDeleteList() {
		return chkDeleteList;
	}

	public void setChkDeleteList(String[] chkDeleteList) {
		this.chkDeleteList = chkDeleteList;
	}

	public String getSelectRole() {
		return selectRole;
	}

	public void setSelectRole(String selectRole) {
		this.selectRole = selectRole;
	}
}
