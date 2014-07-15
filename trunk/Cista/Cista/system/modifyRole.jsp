<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ page import ="com.clt.system.to.SysRoleTo"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
	SysRoleTo data = (SysRoleTo) request.getAttribute("data");
%>

<html>
<head>
	<TITLE>System Modify Role Function</TITLE>
	<jsp:include page="/common/normalcheck.jsp" />
	<script type="text/javascript">
			function modifyRole(){
				var forma = document.forms(0);
				forma.action = "ModifyRole.action";
				forma.method = "post";
				forma.target = "result";
				forma.submit();
			}

			function checkData(){
				var errorFlag = 0;
				var cnt = 0;
				msg.innerHTML = "Verify Data Error , please check your data is correct !!";

				if ($F('roleName') == ""){ 
					msg.innerHTML += "<br>" + (++cnt)+". " +" Role Name must be Input !!"; 
					errorFlag=1;
				}

				if(errorFlag !=0){
					return ;
				}else{
					msg.innerHTML = "Processing data , please wait a minutes...";
					modifyRole();
				}
			}
		</script>
</head>
<body bgcolor="#FFFFFF" leftMargin="0" rightMargin="0" topMargin="0" bottomMargin="0">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<font color="red"><div><s:actionmessage/></div></font>
				<font color="red"><b><div id="msg"></b></div></font>
			</td>
		</tr>
	</table>

	<form name="userSearchForm" id="userSearchForm" method="post">
<%
	if (data != null ){
		long strRoleId = data.getRoleId();
		String strRoleName = (data.getRoleName()!=null?data.getRoleName():"");
%>
		<table id="formdata" width="40%"  border=0 cellpadding=0 cellspacing=0 bordercolordark=#FFFFFF  class="Himax-TableContent" >

			<thead>
					<tr class= "portlet-title-bg3">
					<th colspan = 3>Role Detail</th>
				</tr>
			</thead>

			<tbody>
				<input type="hidden" id="selectRole" name="selectRole" value ="<%=strRoleId%>"/>

				<tr class = "portlet-title-bg2">
					<td>Role Name<BLINK class = "Himax-col-star"> *</BLINK></td>
					<td>
						<input type="text" id="roleName" name="roleName" class="Himax-asl-col-smaller" value ="<%=strRoleName%>"/>
					</td>
					<td>
						<input 
							name = "saveBtn" id = "saveBtn" 
							type="button" value=<s:text name="IE.maintain.button.save"/>
							class="button" onclick = "checkData();" />&nbsp;&nbsp;
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
