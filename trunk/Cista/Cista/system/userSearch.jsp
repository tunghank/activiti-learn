<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import ="com.cista.system.util.CLTUtil"%>
<%@ page import ="com.cista.system.to.SysRoleTo"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String contextPath = (String)request.getContextPath();
	List<SysRoleTo> roleList = (List<SysRoleTo>)request.getAttribute("roleList");
	roleList =null!=roleList?roleList:new ArrayList<SysRoleTo>();
%>
<html>
<head>
	<jsp:include page="/common/normalcheck.jsp" flush="true" />
	<script language="javascript">
		function searchUser(){
			var forma = document.forms(0);
			forma.action = "UserSearch.action";
			forma.method = "post";
			forma.target = "result";
			forma.submit();
		}

		function createUser(){
			var forma = document.forms(0);
			forma.action = "UserCreatePre.action";
			forma.method = "post";
			forma.target = "result";
			forma.submit();
		}
	</script>
</head>
<BODY bgColor=#ffffff leftMargin=0 topMargin=0 >
<table border="0" cellpadding="0" align="left" STYLE="margin-left:10px; width="90%" height="90%" >
	<tr>
		<td class="Title">
		<div align="left">
			<table width="30%" border="0" cellspacing="0" cellpadding="0" align="left" class="OrangeLine">
				<tr>
					<td class="Title">System User Maintain Function</td>
				</tr>
			</table>
		</div>
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td valign="top" width="60%">
		<form name="userSearchForm" id="userSearchForm" method="post" target ="result">
			<table id="formdata" width="100%"  border=1 cellpadding=0 cellspacing=0 bordercolordark=#FFFFFF  class="TableContent-Border-Color" >

				<tr>
					<td align="left" class= "portlet-title-bgSearch1">User ID:</td>
					<td class= "portlet-title-bgSearch2"><input type="text" name="userId" id="userId" class='asl-col-large'/></td>
					
					<td align="left" class= "portlet-title-bgSearch1">User Role:</td>
					<td class= "portlet-title-bgSearch2">
							<select name="roleId" id = "roleId" class = "asl-col-smaill">
								<option value = "">All</option>
<%
							if (roleList != null && roleList.size() >0){
								for (SysRoleTo role : roleList){
									String roleName = (role.getRoleName() != null? role.getRoleName() :"");
%>
								<option value = "<%=role.getRoleId()%>"><%=roleName%></option>
<%
								}
							}
%>
							</select>
						</td>
					
					<!--button-->
					<td align = "center" class= "portlet-title-bgSearch2">
							<input name = "saveBtn" id = "saveBtn" type="button" value='<s:text name="System.maintain.button.filter"/>'
							class="button" onclick = "searchUser();" />&nbsp;&nbsp;
							<input name = "resetBtn" id ="resetBtn" type="reset" value='<s:text name="System.maintain.button.reset"/>'
							class="button" />
							<input type="button" value='<s:text name="System.maintain.button.create"/>' class="button"  onClick='createUser();'/>
					</td>
				</tr>

			</table>
			</form>
			<!-- message -->
			<tr>
				<td align="right"><p align="center"><font color="red"><s:actionmessage/></font></p></td>
			</tr>

	<tr height="100%">
			<td>
				<iframe id="resultList" name="result" frameborder="0" scrolling="auto" width="100%" height="100%"></iframe>
			</td>
	</tr>
</table>
</BODY>
</HTML>