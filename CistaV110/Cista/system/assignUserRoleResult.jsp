<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import ="com.cista.system.util.CistaUtil"%>
<%@ page import ="com.cista.system.to.SysUserRoleTo"%>
<%@ page import ="com.cista.system.to.SysUserTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String contextPath = (String)request.getContextPath();

	List<SysUserTo> userData = (List<SysUserTo>) request.getAttribute("userData"); // function �M��
	List<SysUserRoleTo> userRoledata = (List<SysUserRoleTo>) request.getAttribute("userRoledata"); // ��ڸ��
	int userSize = ( (userData != null && userData.size() > 0 )? userData.size() : 0 );
	String roleId = (String)  request.getAttribute("roleId");
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE>System Assign User Role Function</TITLE>
	<jsp:include page="/common/normalcheck.jsp" />
	
	<script language="javascript">

	function selAll(){
		var forma = document.forms(0);
		if (forma.chkALL.checked==true){
			for (i=0 ; i<forma.chkModifyList.length; i++){forma.chkModifyList[i].checked = true;}
		}else{
			for (i=0 ; i<forma.chkModifyList.length; i++){forma.chkModifyList[i].checked = false;}
		}
	}

	function modifyUserRole(){
		var forma = document.forms(0);
		for (i=0 ; i<forma.chkModifyList.length; i++){
			if( forma.chkModifyList[i].checked == false ){
				forma.chkModifyList[i].value = "-1";
			}
		}

		forma.action = "ModifyUserRole.action";
		forma.method = "post";
		forma.target = "result";
		forma.submit();
	}

	function radCheck(obj) {
		if(obj.checked) { 
			obj.parentNode.parentNode.style.background="#BFEFFF";
		}else{ 
			obj.parentNode.parentNode.style.background="#fcfcfc";
		}
	}
</script>

</script>
</head>
<body bgcolor="#FFFFFF" leftMargin="0" rightMargin="0" topMargin="0" bottomMargin="0">
<font color="red"><s:actionmessage/></font>
<%
	if(userSize>0){
%>
	<form name="roleFunctionForm" id="roleFunctionForm" method="post">
	<input type="hidden" id="roleId" name="roleId" value="<%=roleId%>"/>
	<div align="left">
		<table width="60%" border="0" cellspacing="0" cellpadding="0" align="left">
			<tr>
				<td>
					<input type="button" value=<s:text name="IE.maintain.button.save"/> class="button" onClick='modifyUserRole();'/>
					<input type="button" value=<s:text name="IE.maintain.button.reset"/> class="button" onClick='location.reload();'/>
				</td>
			</tr>
			<tr><td><hr color="#A60404" size="1"/></td></tr>
			<tr>
				<td>
					<div id="tb2-container">
						<table id="formdata" width="100%"  border=1 cellpadding=0 cellspacing=0 bordercolordark=#FFFFFF  class="Himax-TableContent" >
							<thead>
								<tr class= "portlet-title-bg3">
									<th class= "Himax-asl-col-middle" colspan="2">Check All
									<input type="checkbox" name="chkALL" onClick='selAll()'></th>
									<th class= "Himax-asl-col-middle">User ID</th>
									<th class= "Himax-asl-col-middle">Status</th>
									<th class= "Himax-asl-col-middle">User Name</th>
									<th class= "Himax-asl-col-middle">Role</th>
								<tr>
							</thead>
							<tbody>

<%
	int i=0;
	String className = "";
	
	for(SysUserTo info : userData ){
		className = ((i++)%2==0 ? "portlet-title-bg1":"portlet-title-bg2");
		String strUserID 	= info.getUserId() !=null? info.getUserId():"";
		String strUserName	= info.getRealName()!=null? info.getRealName():"";
		String strRoleName = info.getRoleName()!=null? info.getRoleName():"";
		String strUserActive=info.getActive().equals("0")?"Disable":"";
%>
		<tr class="<%=className%>">
			<td class= "Himax-asl-col-small"><%=i%></td>
			<td class= "Himax-asl-col-small"><input type="checkbox" name="chkModifyList" 
			<%
				if (userRoledata != null && userRoledata.size()>0){
					for (SysUserRoleTo roleInfo : userRoledata){
						String strRoleUserId = roleInfo.getUserId().toString();
						if (strUserID.equals(strRoleUserId)){	%>checked<%
							
						}
					}
				}
			%>
				onclick="radCheck(this);" value='<%=strUserID%>'
			></td>
			<td>&nbsp<%=strUserID%></td>
			<td>&nbsp<%=strUserActive%></td>
			<td>&nbsp<%=strUserName%></td>
			<td>&nbsp<%=strRoleName%></td>
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
<%
	}
%>

</body>
</html>