<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import ="java.text.DateFormat"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import ="com.cista.system.util.CLTUtil"%>
<%@ page import ="com.cista.system.to.SysFunctionTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
	List<SysFunctionTo> data = (List<SysFunctionTo>) request.getAttribute("Data");
%>
<html>
<HEAD>
		<TITLE>System Function Search Function</TITLE>
		<jsp:include page="/common/normalcheck.jsp" />
		<script language="javascript">
		function searchFunction(){
			var forma = document.forms(0);
			forma.action = "SearchFunction.action";
			forma.method = "post";
			forma.target = "result";
			forma.submit();
		}
		</script>
</HEAD>
<BODY bgColor=#ffffff leftMargin=0 topMargin=0 onload = "searchFunction();">
	<table border="0" cellpadding="0" align="center" width="98%" height="90%">

	<tr>
		<td class="Himax-Title">
		<div align="left">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left" class="Himax-OrangeLine">
				<tr>
					<td width="361" class="Himax-Title">System Search User Function</td>
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
						<!--parentName-->
						<td align="left" class= "portlet-title-bgSearch1">Parent Name</td>
						<td class= "portlet-title-bgSearch2">
							<select id = "parentId" name = "parentId" class = "Himax-col-width" >
							<option value = "">All</option>
<%
			if ( data != null && data.size() > 0){
				for(SysFunctionTo parent : data ){
%>
							<option value = "<%=parent.getId()%>"><%=parent.getTitle()%></option>
<%				}
			}
%>
						</select>
						</td>

						<!--cls-->
						<td align="left" class= "portlet-title-bgSearch1">Type</td>
						<td class= "portlet-title-bgSearch2">
							<select name="cls" id = "cls" class = "Himax-asl-col-smaill">
								<option value = "">All</option>
								<option value = "file">file</option>
								<option value = "folder">folder</option>
							</select>
						</td>

						<!--button-->
						<td align = "right" class= "portlet-title-bgSearch2">
							<input name = "saveBtn" id = "saveBtn" type="button" value=<s:text name="IE.maintain.button.filter"/>
							class="button" onclick = "searchFunction();" />&nbsp;&nbsp;
							<input name = "resetBtn" id ="resetBtn" type="reset" value=<s:text name="IE.maintain.button.reset"/>
							class="button" />
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