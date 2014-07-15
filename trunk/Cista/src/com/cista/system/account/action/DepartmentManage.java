package com.cista.system.account.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.cista.system.account.dao.DepartmentDao;
import com.cista.system.to.SysDepartmentTo;
import com.cista.system.util.BaseAction;
import com.cista.system.util.CistaUtil;

public class DepartmentManage extends BaseAction{
	
	private String company;
	private String departName;
	private String departDescription;
	private String cdt;
	private String[] chkDeleteList;
	private String[] chkModifyList;	
	private String selectDepartment;
	private int pageNo;

	public String departmentCreatePre() throws Exception {
		return SUCCESS;
	}	

	public String departmentSave() throws Exception{
		
		request= ServletActionContext.getRequest();
		DepartmentDao departmentDAO = new DepartmentDao();		
		SysDepartmentTo curDepartment;
		
		this.departDescription = (this.departDescription ==null?"":this.departDescription);
		
		// check exist
		curDepartment = departmentDAO.getDepartmentDetail(this.departName);
		
		if (curDepartment != null){
			addActionMessage(getText("IE.department.create.message.isExist.fail"));
			return INPUT;
		}		
		curDepartment = new SysDepartmentTo();
		curDepartment.setCompany(this.company);
		curDepartment.setDepartName(this.departName);
		curDepartment.setDepartDescription(this.departDescription);
		
		// add Department
		int dbResult = departmentDAO.insertDepartment(curDepartment);
		
		if (dbResult != 1){
			addActionMessage(getText("IE.department.create.message.insert.fail"));
			return INPUT;
		}
		addActionMessage(getText("IE.department.create.message.insert.success"));
        return SUCCESS;		
	}
	
	public String searchDepartmentPre() throws Exception{	
		
		request= ServletActionContext.getRequest();
		DepartmentDao departmentDAO = new DepartmentDao();	
		List  data = new ArrayList();		
		data = (ArrayList)departmentDAO.searchDepartmentList("");
		
		request.setAttribute("departmentList", data);	
		return SUCCESS;
	}	
	
	public String searchDepartment()throws Exception{
		
		this.departName = (this.departName != null?this.departName:"");
		this.pageNo = (0 == this.pageNo ? 1: this.pageNo);
		
		DepartmentDao departmentDAO = new DepartmentDao();		
		List  data = new ArrayList();		
		data = (ArrayList) departmentDAO.searchDepartmentList(this.departName);
		
		int pageSize = CistaUtil.REPORT_PAGE_SIZE;
	    int resultSize = -1;
	    int pages = -1;
		
		List result = new ArrayList();
		resultSize = null == data? 0 : data.size();		
		pages = CistaUtil.calcPages(resultSize, pageSize);
		result = CistaUtil.cutResult(data, this.pageNo, pageSize);
		if (result == null){ 
			addActionMessage("No data found.");	
			return INPUT;
		}	
		
		request = ServletActionContext.getRequest();
	
		request.setAttribute(CistaUtil.PAGE_SIZE, "" + pageSize);
	    request.setAttribute(CistaUtil.RESULT_SIZE, "" + resultSize);
	    request.setAttribute(CistaUtil.PAGES, "" + pages);
	    request.setAttribute(CistaUtil.PAGENO, "" + this.pageNo);
	    request.setAttribute("departName", "" + this.departName);

	    request.setAttribute("Data", result);
		
		return SUCCESS;
	}	

	public String deleteDepartment() throws Exception{
		
		DepartmentDao departmentDAO = new DepartmentDao();		
		int dbResult = 0;
		
		for (int i = 0 ; i < chkDeleteList.length ; i++){
			dbResult = departmentDAO.deleteDepartment(chkDeleteList[i]);
			
			if (dbResult != 1){
				addActionMessage(getText("IE.department.delete.message.delete.fail"));
				return INPUT;
			}
		}
		addActionMessage(getText("IE.department.delete.message.delete.success"));
		return INPUT;
	}	
	
	public String modifyDepartmentPre()	throws Exception{
		
		request= ServletActionContext.getRequest();
		departName = (selectDepartment==null) ? "" : selectDepartment;	
		
		DepartmentDao departmentDAO = new DepartmentDao();
		SysDepartmentTo curDepartment = new SysDepartmentTo();		
		curDepartment = departmentDAO.getDepartmentDetail(this.departName);		
		request.setAttribute("CurDepartment", curDepartment);
		
		return INPUT;
	}

	public String modifyDepartment() throws Exception{
		
		DepartmentDao departmentDAO = new DepartmentDao();
		SysDepartmentTo curDepartment = new SysDepartmentTo();		
		curDepartment = departmentDAO.getDepartmentDetail(this.selectDepartment);				
		
		this.departName = (this.departName == null?"":this.departName);
		this.departDescription = (this.departDescription == null?"":this.departDescription);
		
		// set value
		curDepartment.setDepartName(this.departName);
		curDepartment.setDepartDescription(this.departDescription);		
		int dbResult = departmentDAO.updateDepartment(curDepartment,this.selectDepartment) ;		
		
		if (dbResult != 1){
			addActionMessage(getText("IE.department.modify.message.modifyDepartment.fail"));
			return INPUT;
		}
		
		addActionMessage(getText("IE.department.modify.message.modifyDepartment.success"));
		return SUCCESS;
	}
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getDepartDescription() {
		return departDescription;
	}

	public void setDepartDescription(String departDescription) {
		this.departDescription = departDescription;
	}

	public String getCdt() {
		return cdt;
	}

	public void setCdt(String cdt) {
		this.cdt = cdt;
	}

	public String[] getChkDeleteList() {
		return chkDeleteList;
	}

	public void setChkDeleteList(String[] chkDeleteList) {
		this.chkDeleteList = chkDeleteList;
	}

	public String[] getChkModifyList() {
		return chkModifyList;
	}

	public void setChkModifyList(String[] chkModifyList) {
		this.chkModifyList = chkModifyList;
	}

	public String getSelectDepartment() {
		return selectDepartment;
	}

	public void setSelectDepartment(String selectDepartment) {
		this.selectDepartment = selectDepartment;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
}
