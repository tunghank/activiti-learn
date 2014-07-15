<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.text.DateFormat"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import ="com.cista.system.util.CLTUtil"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<HTML>
	<HEAD>
		<TITLE>System Create Role Function</TITLE>
		<jsp:include page="/common/normalcheck.jsp" />
		<style type="text/css">fieldset {width:68%;} </style>

		<script type="text/javascript">
			function createRole(){
				var forma = document.forms(0);
				forma.action = "RoleCreate.action";
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
					createRole();
				}
			}
		</script>
	</HEAD>

	<BODY leftmargin="8" topmargin="16" >
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
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
			<legend>System Create Role Function</legend>
			<table height="100%" width="100%" border="0" cellspacing="0" cellpadding="0" class="Himax-TableContent-white">

			<tr class="portlet-title-bg1">
				<td align="left" class="portlet-title-bg1">Role Name<BLINK class = "Himax-col-star"> *</BLINK></td>
				<td class="portlet-title-bg3"><input type="text" id = "roleName" name="roleName" class = "Himax-asl-col-middle"/></td>

				<td align = "right" class="portlet-title-bg3">
						<input 
							name = "saveBtn" id = "saveBtn" 
							type="button" value=<s:text name="IE.maintain.button.save"/> 
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
	</BODY>
</HTML>