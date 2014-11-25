<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.text.DateFormat"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import ="com.cista.system.util.CistaUtil"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<HTML>
	<HEAD>
		<TITLE><s:text name="SYSTEM.user.create.function.name"/></TITLE>
		<jsp:include page="/common/normalcheck.jsp" />
		<style type="text/css">fieldset {width:70%;} </style>

		<script type="text/javascript">

			function createDepartment(){
				var forma = document.forms(0);
				forma.action = "DepartmentSave.action";
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
					createDepartment();
				}
			}
		</script>
	</HEAD>

	<BODY leftmargin="8" topmargin="16" >
	<!--message-->
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
			<legend>System Create Department Function</legend>
			<table height="100%" width="100%" border="0" cellspacing="0" cellpadding="0" class="Himax-TableContent-white">
			<tr>
				<td align="left" class="portlet-title-bg1">Company</td>
				<td>
					<select name="company" id = "company" class = "Himax-col-width">
						<option value = "Himax">Himax</option>
					</select>
				</td>
			</tr>

			<tr>
				<td align="left" class="portlet-title-bg1">
					Department Name<BLINK class = "Himax-col-star"> *</BLINK>
				</td>
				<td>
					<input type="text" id = "departName" name="departName" class = "Himax-col-width"/>
				</td>
			</tr>

			<tr>
				<td align="left" class="portlet-title-bg1">Description</td>
				<td>
					<input type="text" id = "departDescription" name="departDescription" class = "Himax-col-width"/>
				</td>
			</tr>

			<!--button-->
			<tr class="portlet-title-bg3">
				<td></td>
				<td align ="right" class="portlet-title-bg3">
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