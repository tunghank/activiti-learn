<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="com.cista.system.util.CLTUtil"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.text.DateFormat"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import ="com.cista.system.to.SysRoleTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String contextPath = (String)request.getContextPath();
	List<SysRoleTo> data = (List<SysRoleTo>) request.getAttribute("roleList");
%>
<html>
<HEAD>
		<TITLE>System Modify Role Function</TITLE>
		<jsp:include page="/common/normalcheck.jsp" />
		<script language="javascript">
			function searchRole(){
				var forma = document.forms(0);
				forma.action = "SearchRole.action";
				forma.method = "post";
				forma.target = "result";
				forma.submit();
			}
		</script>
</HEAD>
<BODY bgColor=#ffffff leftMargin=0 topMargin=0 onload = "searchRole();">
	<table border="0" cellpadding="0" align="center" width="98%" height="98%">

	<tr>
		<td class="Himax-Title">
		<div align="left">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left" class="Himax-OrangeLine">
				<tr>
					<td width="361" class="Himax-Title">System Modify Role Function</td>
				</tr>
			</table>
		</div>
		</td>
	</tr>
	<tr></tr>

		<tr>
		<td valign="top" width="60%">
			<form name="loginForm" id="loginForm" method="post" >
				<table id="formdata" width="100%"  border=1 cellpadding=0 cellspacing=0 bordercolordark=#FFFFFF  class="Himax-TableContent" >
					<tr class= "portlet-title-bg3">

						<td align="left" class= "portlet-title-bgSearch1">Role Name</td>

						<td class= "portlet-title-bgSearch2">
							<select name="roleName" id = "roleName" class = "Himax-asl-col-smaill">
								<option value = "">All</option>
<%
							if (data != null && data.size() >0){
								for (SysRoleTo role : data){
									String roleName = (role.getRoleName() != null? role.getRoleName() :"");
%>
									<option value = "<%=roleName%>"><%=roleName%></option>
<%
								}
							}
%>
							</select>
						</td>

						<td align = "right" class= "portlet-title-bgSearch2">
							<input name = "saveBtn" id = "saveBtn" type="button" value=<s:text name="IE.maintain.button.filter"/>
							class="button" onclick = "searchRole();"/>&nbsp;&nbsp;
							<input name = "resetBtn" id ="resetBtn" type="reset" value=<s:text name="IE.maintain.button.reset"/>
							class="button" />
						</td>
					</tr>
				</table>
				</form>
		<tr height="100%">
			<td>
				<iframe id="resultList" name="result" frameborder="0" scrolling="auto" width="100%" height="100%"></iframe>
			</td>
		</tr>
	</table>
</BODY>
</HTML>