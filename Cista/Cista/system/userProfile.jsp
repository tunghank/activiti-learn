<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.text.DateFormat"%>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ page import ="com.clt.system.to.SysUserTo"%>

<%@ taglib prefix="s" uri="/struts-tags" %>

<%  
  String contextPath = (String)request.getContextPath();   
  SysUserTo curUser = (SysUserTo) request.getAttribute("CurUser"); 
  DateFormat cdtFormat = new SimpleDateFormat("yyyyMMddhhmmss");
%>
<html>
<head>
	<TITLE>System My Profile Function</TITLE>
	<jsp:include page="/common/normalcheck.jsp" />
	<style type="text/css">fieldset {width:70%;} </style>

	<script type="text/javascript">

		function checkData(){
			var curUserRole = "<%=curUser.getCompany()%>";
			var errorFlag = 0;
			var cnt = 0;
			msg.innerHTML = "Verify data error , please check your data is correct !!";

			if (curUserRole != "<%=CLTUtil.CLT_ROLE%>"){
				if ($F('password') == ""){ 
					msg.innerHTML += "<br>" + (++cnt)+". " + " Password must be input"; 
					errorFlag=1;
				}
				if ($F('confirmPassword') == ""){ 
					msg.innerHTML += "<br>" + (++cnt)+". " + " Confirm Password must be input"; 
					errorFlag=1;
				}
				if ($F('confirmPassword') != "" && $F('password') != "" && $F('confirmPassword') != $F('password')){
					msg.innerHTML += "<br>" + (++cnt)+". " + " Password must be equals"; 
					errorFlag=1;
				}
			}

			if (errorFlag !=0){
				return ;
			}else{
				msg.innerHTML = "Processing data , please wait a minutes...";
				modifyProfile();
			}
		}

		function modifyProfile(){
			var forma = document.forms(0);
			forma.action = "UserProfile.action";
			forma.method = "post";
			forma.submit();
		}
	</script>
</head>
<body leftmargin="8" topmargin="16" >

	<table width="100%" border="0" cellspacing="0" cellpadding="0">	
		<tr>
			<!--message-->
			<td>
				<font color="red"><div><s:actionmessage/></div></font>
				<font color="red"><b><div id="msg"></b></div></font>
			</td>
		</tr>
	</table>

	<table border="0" cellpadding="0" cellspacing="20" width="80%">
		<tr><td valign="top" width="60%">

		<form name="loginForm" id="loginForm" method="post">
			<fieldset class="x-fieldset legend input">
			<legend>System My Profile Function</legend>
			<table height="100%" width="100%" border="0" cellspacing="0" cellpadding="0" class="Himax-TableContent-white">
				<tr><td align="left" class="portlet-title-bg1">User ID</td><td><%=curUser.getUserId()%></td></tr>
				<tr><td align="left" class="portlet-title-bg1">Real Name</td><td><%=curUser.getRealName()%></td></tr>
				<tr><td align="left" class="portlet-title-bg1">Company</td><td><%=curUser.getCompany()%></td></tr>
<%
	if (!curUser.getCompany().equals("Himax")){
%>
				<tr id = "rowPassword">
					<td align="left" class="portlet-title-bg1">Password<BLINK class = "Himax-col-star"> *</BLINK></td>
					<td><input 
							type="password" 
							id= "password" name="password" 
							class = "Himax-col-width"
							value = "<%=CLTUtil.decodePasswd(curUser.getPassword())%>"/></td> 
				</tr>

				<tr id = "rowConfirmPassword">
					<td align="left" class="portlet-title-bg1">Cofirm Password<BLINK class = "Himax-col-star"> *</BLINK></td>
					<td><input 
							type="password" 
							id = "confirmPassword" name="confirmPassword" 
							class = "Himax-col-width"
							value = "<%=CLTUtil.decodePasswd(curUser.getPassword())%>"/></td>
				</tr>
<%
	}
%>

			<tr>
				<td align="left" class="portlet-title-bg1">Department</td>
				<td><%=(curUser.getDepartment() == null ? " ":curUser.getDepartment())%></td>
			</tr>

			<tr>
				<td align="left" class="portlet-title-bg1">Position</td>
				<td><%=(curUser.getPosition() == null ? " ":curUser.getPosition())%></td>
			</tr>


			<tr>
				<td align="left" class="portlet-title-bg1">Email</td>
				<td><%=(curUser.getEmail() == null ? " ": curUser.getEmail())%></td>
			</tr>

			<tr>
				<td align="left" class="portlet-title-bg1">Phone Number</td><td><input type="text" 
						id = "phoneNum" name="phoneNum" 
						value = "<%=(curUser.getPhoneNum() == null ? " ": curUser.getPhoneNum())%>" 
						class = "Himax-col-width"/></td>
			</tr>

				<input type="hidden" id = "lastTime" name = "lastTime" value ="<%=cdtFormat.format(new Date())%>">
				<input type="hidden" id = "lastIp" name = "lastIp" value ="<%=request.getRemoteAddr()%>">
				<input type="hidden" id = "userID" name = "userId" value ="<%=curUser.getUserId()%>">
			  
				<!--button-->
				<tr class="portlet-title-bg3">
					<td></td>
					<td align ="right" class="portlet-title-bg3">
						<input 
							name = "saveBtn" id = "saveBtn" 
							type="button" value=<s:text name="IE.maintain.button.update"/> 
							class="button" onclick="checkData();"/>&nbsp;&nbsp;

						<input 
							name = "resetBtn" id ="resetBtn" 
							type="reset" value=<s:text name="IE.maintain.button.reset"/> 
							class="button" />
					</td>
				</tr>  
			</table>
			</fieldset>
		<form>
	</td></tr>
	</table>
</body>
</html>