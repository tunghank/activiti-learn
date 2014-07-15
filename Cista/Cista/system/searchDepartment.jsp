<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import ="java.text.DateFormat"%>
<%@ page import ="com.clt.system.to.SysDepartmentTo"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String contextPath = (String)request.getContextPath();
	List<SysDepartmentTo> data = (List<SysDepartmentTo>) request.getAttribute("departmentList");
%>
<html>
<HEAD>
		<TITLE>System Search Department Function</TITLE>
		<jsp:include page="/common/normalcheck.jsp" />
		<script language="javascript">
		function searchDepartment(){
			var forma = document.forms(0);
			forma.action = "SearchDepartment.action";
			forma.method = "post";
			forma.target = "result";
			forma.submit();
		}
	</script>
</HEAD>
<BODY bgColor=#ffffff leftMargin=0 topMargin=0 onload = "searchDepartment();">
	<table border="0" cellpadding="0" align="center" width="98%" height="98%">

	<tr>
		<td class="Himax-Title">
		<div align="left">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left" class="Himax-OrangeLine">
				<tr>
					<td width="361" class="Himax-Title">System Search Department Function</td>
				</tr>
			</table>
		</div>
		</td>
	</tr>
	<tr></tr>

		<tr>
			<td valign="top" width="60%">
				<form name="loginForm" id="loginForm" method="post">
					<table id="formdata" width="100%"  border=1 cellpadding=0 cellspacing=0 bordercolordark=#FFFFFF  class="Himax-TableContent" >
						<tr>
							<td align="left" class= "portlet-title-bgSearch1">Department</td>
							<td class= "portlet-title-bgSearch2">
							<select name="departName" id = "departName" class = "Himax-asl-col-smaill">
								<option value = "">All</option>
<%
							if (data != null && data.size() >0){
								for (SysDepartmentTo department : data){
									String departmentName = (department.getDepartName() != null? department.getDepartName() :"");
%>
									<option value = "<%=departmentName%>"><%=departmentName%></option>
<%
								}
							}
%>
							</select>
						</td>
							
							
							
							<!-- button -->
							<td align = "right" class= "portlet-title-bgSearch2">
								<input 	name = "saveBtn" id = "saveBtn" type="button" 
										value=<s:text name="IE.maintain.button.filter"/>
										class="button" onclick = "searchDepartment();" />&nbsp;&nbsp;
								<input 	name = "resetBtn" id ="resetBtn" type="reset" 
										value=<s:text name="IE.maintain.button.reset"/>
										class="button" />
							</td>
						</tr>
					</table>
				</form>

				<!-- message -->
				<tr>
					<td align="right">
						<font color="red"><s:actionmessage/></font>
					</td>
				</tr>
			</td>
		</tr>

		<tr height="100%">
			<td>
				<iframe id="resultList" name="result" frameborder="0" scrolling="auto" width="100%" height="100%">
				</iframe>
			</td>
		</tr>
	</table>
</BODY>
</HTML>