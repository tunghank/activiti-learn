<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ page import ="com.clt.system.to.SysDepartmentTo"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
SysDepartmentTo curDepartment = (SysDepartmentTo) request.getAttribute("CurDepartment");
%>

<html>
<head>
	<TITLE>System Modify Department Function</TITLE>
	<jsp:include page="/common/normalcheck.jsp" />

	<script type="text/javascript">

			function modifyDepartment(){
				var forma = document.forms(0);
				forma.action = "ModifyDepartment.action";
				forma.method = "post";
				forma.target = "result";
				forma.submit();
			}

			function checkData(){
				var errorFlag = 0;
				//var cnt = 0;
				msg.innerHTML = "Verify Data Error , please check your data is correct !!";

				if ($F('departName') == ""){ 
					msg.innerHTML += "<br>" + " Depart Name must be Input!!"; 
					//msg.innerHTML += "<br>" + (++cnt)+". " +" Depart Name must be Input!!"; 
					errorFlag=1;
				}

				if(errorFlag !=0){
					return ;
				}else{
					msg.innerHTML = "Processing data , please wait a minutes...";
					modifyDepartment();
				}
			}
		</script>
</head>
<body bgcolor="#FFFFFF" leftMargin="0" rightMargin="0" topMargin="0" bottomMargin="0">

	<form name="userSearchForm" id="userSearchForm" method="post">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">	
			<tr>
				<td>
					<font color="red"><div><s:actionmessage/></div></font>
					<font color="red"><b><div id="msg"></b></div></font>
				</td>
			</tr>
		</table>
	<br>
<%
	if (curDepartment != null ){
		String strDepartment = (curDepartment.getDepartName()!=null ? curDepartment.getDepartName():"");
		String strCompany = (curDepartment.getCompany()!=null ? curDepartment.getCompany():"");
		String strDescription = (curDepartment.getDepartDescription() ==null ? " ": curDepartment.getDepartDescription());
%>
		<table id="formdata" width="40%"  border=1 cellpadding=0 cellspacing=0 bordercolordark=#FFFFFF  class="Himax-TableContent" >
			<input type="hidden" id="selectDepartment" name="selectDepartment" value = "<%=strDepartment%>"/>

			<thead>
				<tr class= "portlet-title-bg3"><th colspan = "2">Department Detail</th></tr>
			</thead>

			<tbody>
				<tr class = "portlet-title-bg1"><td>Company</td><td><%=strCompany%></td></tr>
				<tr class = "portlet-title-bg2"><td>Department Name<BLINK class = "Himax-col-star"> *</BLINK></td>
					<td>
						<input 	type="text" id = "departName" name="departName" class = "Himax-col-width" value = "<%=strDepartment%>"/>
					</td>
				</tr>

				<tr class = "portlet-title-bg1"><td>Description</td>
					<td>
						<input 	type="text" id = "departDescription" name="departDescription" class = "Himax-col-width" value = "<%=strDescription%>"/>
					</td>
				</tr>

				<tr class = "portlet-title-bg3">
					<td colspan = "2" align="right">
						<input 
							name = "saveBtn" id = "saveBtn" 
							type="button" value=<s:text name="IE.maintain.button.save"/>
							class="button" onclick = "checkData();"/>&nbsp;&nbsp;
						<input 
							name = "resetBtn" id ="resetBtn" 
							type="reset" value=<s:text name="IE.maintain.button.reset"/> 
							class="button" />
					</td>
				</tr>

			</tbody>
		</table>
<%
	}
%>

	</form>
</body>
</html>
