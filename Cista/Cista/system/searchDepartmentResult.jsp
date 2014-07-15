<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="com.cista.system.util.CLTUtil"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import ="com.cista.system.to.SysDepartmentTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String contextPath = (String)request.getContextPath();

	// for paging
	String pageSize 	= (String)request.getAttribute(CLTUtil.PAGE_SIZE);
    String resultSize 	= (String)request.getAttribute(CLTUtil.RESULT_SIZE);
    String pages 		= (String)request.getAttribute(CLTUtil.PAGES);
    String pageNo 		= (String)request.getAttribute(CLTUtil.PAGENO);

	//Criteria
    String departName 	= (String)request.getAttribute("departName");

	List<SysDepartmentTo> Result = (List<SysDepartmentTo>) request.getAttribute("Data");
	int ResultSize = 0 ;
	if (Result != null && Result.size() > 0) ResultSize = Result.size();

%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE>System Search Department Function</TITLE>
	<jsp:include page="/common/normalcheck.jsp" />
<script language="javascript">

	function doInit(){
<%
	if(ResultSize>0) {
%>
		var form = document.forms(0);

		if(Number(form.pageNo.value)>=<%=pages%>) {
			nextButton.removeAttribute("href");
			lastButton.removeAttribute("href");
		}

		if(Number(form.pageNo.value)<=1) {
			prevButton.removeAttribute("href");
			firstButton.removeAttribute("href");
		}
<%
	}
%>
	}
	function nextPage(){
		document.forms(0).pageNo.value = Number(document.forms(0).pageNo.value) + 1;
		document.forms(0).action = "<%=contextPath%>/SearchDepartment.action";
		document.forms(0).submit();
	}

	function prevPage(){
		document.forms(0).pageNo.value = Number(document.forms(0).pageNo.value) - 1;
		document.forms(0).action = "<%=contextPath%>/SearchDepartment.action";
		document.forms(0).submit();
	}

	function goFirstPage(){
		document.forms(0).pageNo.value = 1;
		document.forms(0).action = "<%=contextPath%>/SearchDepartment.action";
		document.forms(0).submit();
	}

	function goLastPage(){
		document.forms(0).pageNo.value = <%=pages%>;
		document.forms(0).action = "<%=contextPath%>/SearchDepartment.action";
		document.forms(0).submit();
	}
	
	function selAll(){
		var forma = document.forms(0);
		if (forma.chkALL.checked==true)
		{
			for (i=0 ; i<forma.chkDeleteList.length; i++){
				forma.chkDeleteList[i].checked = true;
			}
		}else{
			for (i=0 ; i<forma.chkDeleteList.length; i++){
				forma.chkDeleteList[i].checked = false;
			}
		}
	}

	function deleteDepartment()
	{
		var count = 0;
		var forma = document.forms(0);
		
		if(forma.chkDeleteList.length > 1)
		{
			for (i=0 ; i<forma.chkDeleteList.length; i++)
			{
				if( forma.chkDeleteList[i].checked == false )
				{
					forma.chkDeleteList[i].value = "-1";				
				}
				else 
				{
					count = count+1;
				}
			}
		}
		else
		{
			if(forma.chkDeleteList.checked) { count = 1;}			
		}
		
		if(count == 0)
		{
			alert("please at least choosing one item !!");
			return ;
		}

    	forma.action = "DeleteDepartment.action";
    	forma.method = "post";
		forma.target = "result";
		forma.submit();
	}

	function modifyDepartment(department){
		var forma = document.forms(0);
		$('selectDepartment').value =department;
		forma.action = "ModifyDepartmentPre.action";
    	forma.method = "post";
		forma.target = "result";
		forma.submit();
	}

	function createDepartment(){
		var forma = document.forms(0);
    	forma.action = "DepartmentCreatePre.action";
    	forma.method = "post";
		forma.target = "result";
		forma.submit();
	}

</script>
</head>
<body bgcolor="#FFFFFF" onload="doInit()" leftMargin="0" rightMargin="0" topMargin="0" bottomMargin="0">
<p align="center"><font color="red"><s:actionmessage/></font></p>
<%
	if(ResultSize>0){
		int resultFrom = (Integer.parseInt(pageNo)-1)*Integer.parseInt(pageSize)+1;
		int resultTo = resultFrom + Result.size()-1;
%>
	<form>
	<!-- need modify start-->
	<input type="hidden" id="departName" name="departName" value="<%=departName%>"/>
	<input type="hidden" id="pageNo" name="pageNo" value="<%=pageNo%>"/>
	<input type="hidden" id="selectDepartment" name="selectDepartment"/>
	<!-- need modify end-->
	<div align="left">
		<table width="60%" border="0" cellspacing="0" cellpadding="0" align="left">
  			<tr>
	 			<td>
					<input type="button" value=<s:text name="IE.maintain.button.delete"/> class="button" onClick='deleteDepartment();'/>
					<input type="button" value=<s:text name="IE.maintain.button.create"/> class="button"  onClick='createDepartment();'/>
					<a href="javascript:goFirstPage()" class="portlet_content" id="firstButton">the first page </a> |
					<a href="javascript:prevPage()" class="portlet_content" id="prevButton">&lt;previous <%=pageSize%> </a> |
					<%=resultFrom%> - <%=resultTo%> of <%=resultSize%> |
					<a href="javascript:nextPage()" class="portlet_content" id="nextButton"> next <%=pageSize%> &gt;</a> |
					<a href="javascript:goLastPage()" class="portlet_content" id="lastButton">the last page </a> &nbsp;&nbsp;|
				</td>
			</tr>

			<tr>
				<td>
					<hr color="#A60404" size="1"/>
				</td>
			</tr>

			<tr>
				<td>
					<div id="tb2-container">
						<table id="formResult" width="100%"  border=1 cellpadding=0 cellspacing=0 bordercolordark=#FFFFFF  class="Himax-TableContent" >
							<thead>
								<tr class= "portlet-title-bg3">
									<th class= "Himax-asl-col-middle" colspan="2">Check All
									<input type="checkbox" name="chkALL" onClick='selAll()'></th>
									<th class= "Himax-asl-col-middle">Company</th>
									<th class= "Himax-asl-col-large">Department</th>
									<th class= "Himax-asl-col-large">Department Description</th>
								<tr>
							</thead>
							<tbody>

<%
	int i=0;
	String className = "";

	for(SysDepartmentTo department : Result ){
		className = ((i++)%2==0)?"portlet-title-bg1":"portlet-title-bg2";
		String strDepartName = (department.getDepartName() != null ? department.getDepartName():"");
		String strCompany = (department.getCompany().equals("")?"":department.getCompany());
		String strDescription = (department.getDepartDescription()== null? "":department.getDepartDescription());
%>
		<tr class="<%=className%>">
			<td class= "Himax-asl-col-small"><%=i+resultFrom-1%></td>
			<td class= "Himax-asl-col-small"><input type="checkbox" name="chkDeleteList" value='<%=strDepartName%>'></td>
			<td class= "Himax-asl-col-middle">&nbsp;<%=strCompany%></td>
			<td class= "Himax-asl-col-large">&nbsp;<a href="javascript:modifyDepartment('<%=strDepartName%>')"><%=strDepartName%></td>
			<td class= "Himax-asl-col-large">&nbsp;<%=strDescription%></td>
		</tr>
<%
	}
%>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
<%} %>


</body>
</html>