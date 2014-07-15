package com.clt.system.account.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import com.clt.system.account.dao.FunctionDao;
import com.clt.system.account.dao.RoleFunctionDao;
import com.clt.system.account.dao.RoleDao;
import com.clt.system.ldap.service.LDAPConfigService;
import com.clt.system.to.SysFunctionTo;
import com.clt.system.to.SysRoleFunctionTo;
import com.clt.system.to.SysRoleTo;
import com.clt.system.util.BaseAction;
import com.clt.system.util.CLTUtil;

public class FunctionManage extends BaseAction{	
	
	private String id;
	private String parentId;
	private String cls;
	private String leaf;
	private String url;
	private String hrefTarget;
	private String functionName;
	private int pageNo;
	private String[] chkDeleteList;
	private String chkRolesList;
	private String selectFunction;
	
	public String searchFunctionPre() throws Exception{
		
		FunctionDao functionDAO = new FunctionDao();
		// parent list
		ArrayList parentList = new ArrayList();
		parentList 	= (ArrayList) functionDAO.searchFunctionList("",CLTUtil.FUNCTION_FOLDER,"");		
		List<SysFunctionTo> result = (List<SysFunctionTo>) parentList;		
		request.setAttribute("Data", result);
		
		return SUCCESS;
	}
	
	public String searchFunction() throws Exception{
		
		this.parentId = (null != this.parentId ? this.parentId : "");		
		this.cls = (null != this.cls ? this.cls : "");	
		this.functionName = (null != this.functionName ? this.functionName:"");
		this.pageNo = (0 == this.pageNo ? 1: this.pageNo);
		
		FunctionDao functionDAO = new FunctionDao();
		
		request = ServletActionContext.getRequest();
		List  data = new ArrayList();		
		data = (ArrayList) functionDAO.searchFunctionList(this.parentId,this.cls,this.functionName);
		logger.debug(data == null ? "0":data.size());
		
		int pageSize = CLTUtil.REPORT_PAGE_SIZE;
        int resultSize = -1;
        int pages = -1;
		
		List result = new ArrayList();
		resultSize = (null == data? 0 : data.size());		
		pages = CLTUtil.calcPages(resultSize, pageSize);
		result = CLTUtil.cutResult(data, this.pageNo, pageSize);
		
		if (result == null){ 
			addActionMessage("No data found.");
			return INPUT;
		}
		
		request.setAttribute(CLTUtil.PAGE_SIZE, "" + pageSize);
        request.setAttribute(CLTUtil.RESULT_SIZE, "" + resultSize);
        request.setAttribute(CLTUtil.PAGES, "" + pages);
        request.setAttribute(CLTUtil.PAGENO, "" + this.pageNo);       
        request.setAttribute("parentId", "" + this.parentId);       
        request.setAttribute("cls", "" + this.cls);       
		request.setAttribute("data", result);
		
		return SUCCESS;
	}
	
	public String functionCreatePre() throws Exception{
	
		FunctionDao functionDAO = new FunctionDao();
		RoleDao roleDAO = new RoleDao();		
		request= ServletActionContext.getRequest();
		
		// get all folders
		ArrayList  allFolders = new ArrayList();
		allFolders = (ArrayList) functionDAO.getClsFunction("0");
		request.setAttribute("AllFolders", allFolders);	
		
		// get all role
		ArrayList allRoles = new ArrayList();
		allRoles = (ArrayList) roleDAO.searchRoleList("");
		request.setAttribute("AllRoles", allRoles);	
		
		return SUCCESS;
	}
		
	public String functionCreate() throws Exception{
		
		FunctionDao functionDAO = new FunctionDao();
		SysFunctionTo curFunction ;	
		RoleFunctionDao roleFunctionDAO = new RoleFunctionDao();	
		
		this.functionName = (this.functionName!=null ? this.functionName:"");
		this.leaf = (this.cls.equals("folder")?"0":"1");
		this.hrefTarget = (this.hrefTarget!=null ? this.hrefTarget:"");
		this.url = (this.url!=null ? this.url:"");		
		
		// exist ?
		if (!this.functionName.equals("")){		
			curFunction = functionDAO.getFunctionDetail(this.parentId, this.functionName);		
			if (curFunction != null ){
				addActionMessage(getText("IE.function.create.message.isExist.fail")); 
				return INPUT;
			}
		}
			
		// Set new Function detail
		curFunction = new SysFunctionTo();			
		curFunction.setParentId(this.parentId);
		curFunction.setTitle(this.functionName);
		curFunction.setCls(this.cls);
		curFunction.setLeaf(this.leaf);
		curFunction.setUrl(this.url);
		curFunction.setHrefTarget(this.hrefTarget);			
			
		// 2.insert data
		int dbResult = functionDAO.insertFunction(curFunction);
			
		if (dbResult != 1){
			addActionMessage(getText("IE.function.create.message.insert.fail"));
			return INPUT;
		}else{			
			// get new function id
			String functionId = functionDAO.getFunctionDetail(parentId, functionName).getId();
			dbResult = roleFunctionDAO.insertFunctionRole(chkRolesList,functionId);
			if (dbResult != 1){							
				addActionMessage(getText("IE.function.create.message.insertFunctionRole.fail"));
				return INPUT;
			}			
		}			
		addActionMessage(getText("IE.function.create.message.insert.success"));		
		return SUCCESS;
	}	
	
	public String deleteFunction() throws Exception{
		
		FunctionDao functionDAO = new FunctionDao();
		RoleFunctionDao roleFunctionDao = new RoleFunctionDao();
		ArrayList roleFunctionList = new ArrayList();
		
		for (int i = 0 ; i < this.chkDeleteList.length ; i++){			
			List data = new ArrayList();			
			data = (ArrayList) functionDAO.getAllChild(this.chkDeleteList[i]);
			List<SysFunctionTo> function = (List<SysFunctionTo>) data; 
			
			for (SysFunctionTo info : function ){
				int dbResult = functionDAO.deleteFunction(info.getId());
				if(dbResult != 1){					
					addActionMessage(getText("IE.function.delete.message.deleteFunction.fail",info.getTitle()));
					return INPUT;
				}else {
					// function role
					roleFunctionList = (ArrayList) roleFunctionDao.getRoleFunctionList(this.chkDeleteList[i]);				
					List<SysRoleFunctionTo> allFunctions = (List<SysRoleFunctionTo>) roleFunctionList;
					
					if (allFunctions != null){
						for (int j=0 ; j < allFunctions.size();j++){
							dbResult = roleFunctionDao.deleteFunctionRole(allFunctions.get(j).getId().toString(),this.chkDeleteList[i]);
											
							if (dbResult != 1){
								addActionMessage(getText("IE.function.delete.message.deleteFunctionRole.fail",info.getTitle()));	
								return INPUT;
							}
						}
					}					
				}
			}
		}
		addActionMessage(getText("IE.function.delete.message.deleteFunction.success"));
		return SUCCESS;
	}
	
	public String modifyFunctionPre() throws Exception{
		
		FunctionDao functionDAO = new FunctionDao();		
		request= ServletActionContext.getRequest();	
		
		String curFunctionId= (this.selectFunction==null) ? "" : this.selectFunction;
		
		// function data
		SysFunctionTo curFunction = new SysFunctionTo();		
		curFunction = functionDAO.getFunctionDetail(curFunctionId);		
		request.setAttribute("curFunction", curFunction);
		
		// parent list
		ArrayList parentList = new ArrayList();
		parentList 	= (ArrayList) functionDAO.searchFunctionList("",CLTUtil.FUNCTION_FOLDER,"");
		request.setAttribute("ParentList", parentList);
		
		return SUCCESS;
	}

	public String modifyFunction() throws Exception{
		
		FunctionDao functionDAO = new FunctionDao();		
		SysFunctionTo curFunction = functionDAO.getFunctionDetail(this.id);
		this.leaf = (this.cls.equals("folder")?"0":"1");
				
		curFunction.setTitle(this.functionName);
		curFunction.setParentId(this.parentId);
		curFunction.setCls(this.cls);		
		curFunction.setUrl(this.url);		
		curFunction.setHrefTarget(this.hrefTarget);
		curFunction.setLeaf(this.leaf);
			
		int dbResult = functionDAO.updateFunction(curFunction);
			
		if (dbResult != 1){
			addActionMessage(getText("IE.function.modify.message.modifyFunction.fail"));
			return INPUT;
		}
		addActionMessage(getText("IE.function.modify.message.modifyFunction.success"));		
		return SUCCESS;	
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHrefTarget() {
		return hrefTarget;
	}

	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
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

	public String getChkRolesList() {
		return chkRolesList;
	}

	public void setChkRolesList(String chkRolesList) {
		this.chkRolesList = chkRolesList;
	}

	public String getSelectFunction() {
		return selectFunction;
	}

	public void setSelectFunction(String selectFunction) {
		this.selectFunction = selectFunction;
	}
	
}
