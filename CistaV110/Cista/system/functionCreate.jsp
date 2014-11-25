<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.text.DateFormat"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import ="com.cista.system.util.CistaUtil"%>
<%@ page import ="com.cista.system.to.SysFunctionTo"%>
<%@ page import ="com.cista.system.to.SysRoleTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
  String contextPath = (String)request.getContextPath();
  List<SysFunctionTo> allFunctions = (List<SysFunctionTo>) request.getAttribute("AllFolders"); 
  List<SysRoleTo> allRoles = (List<SysRoleTo>) request.getAttribute("AllRoles");  
%>

<HTML>
	<HEAD>
		<TITLE>System Function Create Function</TITLE>
		<jsp:include page="/common/normalcheck.jsp" />
		<style type="text/css">fieldset {width:70%;} </style>

		<script type="text/javascript">

			function createFunction(){
				var forma = document.forms(0);
				forma.action = "FunctionCreate.action";
				forma.method = "post";
				forma.target = "result";
				forma.submit();
			}

			function checkData(){
				var errorFlag = 0;
				var cnt = 0;
				msg.innerHTML = "Verify Data Error , please check your data is correct !!";

				if ($F('parentId') == ""){ 
					msg.innerHTML += "<br>" + (++cnt)+". " +" Parent Name must be select !!"; 
					errorFlag=1;
				}

				if ($F('functionName') == ""){ 
					msg.innerHTML += "<br>" + (++cnt)+". " +" Function Name must be input !!"; 
					errorFlag=1;
				}

				if ($F('cls') == ""){ 
					msg.innerHTML += "<br>" + (++cnt)+". " +" Function Type must be select !!"; 
					errorFlag=1;
				}
				
				// ��Ƨ�����
				if ($F('cls') == "folder" && $F('hrefTarget') != ""){ 
					msg.innerHTML += "<br>" + (++cnt)+". " +" Href Target can't be select !!"; 
					errorFlag=1;
				}

				// ��Ƨ�����
				if ($F('cls') == "folder" && $F('url') != ""){ 
					msg.innerHTML += "<br>" + (++cnt)+". " +" URL can't be inut !!"; 
					errorFlag=1;
				}

				// ��������
				if ($F('cls') == "file" && $F('hrefTarget') == ""){ 
					msg.innerHTML += "<br>" + (++cnt)+". " +" Href Target must be select !!"; 
					errorFlag=1;
				}

				// ��������
				if ($F('cls') == "file" && $F('url') == ""){ 
					msg.innerHTML += "<br>" + (++cnt)+". " +" URL must be inut !!"; 
					errorFlag=1;
				}

				if ($F('chkRolesList') == ""){ 
					msg.innerHTML += "<br>" + (++cnt)+". " +" Use Role must be select !!";
					errorFlag=1;
				}

				if(errorFlag !=0){
					return ;
				}else{
					msg.innerHTML = "Processing data , please wait a minutes...";
					createFunction();
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
			<legend>System Function Create Function</legend>
			<table height="100%" width="100%" border="0" cellspacing="0" cellpadding="0" class="Himax-TableContent-white">
			
			<!--function name -->
			<tr>
				<td align="left" class="portlet-title-bg1">Parent Name<BLINK class = "Himax-col-star"> *</BLINK></td>
				<td>
					<select id = "parentId" name = "parentId" class = "Himax-col-width">
						<option value = ''>-----</option>
<%
					if (allFunctions  != null && allFunctions.size() > 0){
						for(SysFunctionTo functions : allFunctions ){
%>
							<option value = "<%=functions.getId()%>"><%=functions.getTitle()%>
<%						}
					}
%>
						</select>
				</td>
			</tr>

				<tr>
					<td align="left" class="portlet-title-bg1">Function Name<BLINK class = "Himax-col-star"> *</BLINK>
					</td>
					<td><input type="text" id = "functionName" name="functionName" class = "Himax-col-width"/></td>
				</tr>

			<tr>
					<td align="left" class="portlet-title-bg1">Type<BLINK class = "Himax-col-star"> *</BLINK></td>
					<td>
						<select type="text" class = "Himax-col-width" id = "cls" name="cls">
							<option value = ''>-----</option>
							<% for (int i = 0 ; i < CistaUtil.FUNCTION_CLS.length ; i ++) {%>
								<option value = "<%=CistaUtil.FUNCTION_CLS[i]%>"><%=CistaUtil.FUNCTION_CLS[i]%></option>
							<%}%>
						</select>
					</td>
			</tr>

			<tr>
				<td align="left" class="portlet-title-bg1">URL</td>
				<td><input type="text" id = "url" name="url" class = "Himax-col-width"/></td>
			</tr>

			<tr>
					<td align="left" class="portlet-title-bg1">Href Target</td>
					<td>
						<select name="hrefTarget" id = "hrefTarget" class = "Himax-col-width">
							<option value = "">-----</option>
							<option value = "mainFrame">mainFrame</option>
						</select>
					</td>
			</tr>

							<tr class = "portlet-title-bg2">
					<td>User Role<BLINK class = "Himax-col-star"> *</BLINK></td>
					<td>
					<%	if ( allRoles  != null && allRoles.size() > 0){%>
						<select name="chkRolesList" id ="chkRolesList" class = "Himax-asl-col-middle">
							<option value = "">-----
							<%for (SysRoleTo role : allRoles){%>
								<option value = <%=role.getRoleId()%>><%=role.getRoleName()%>
							<%}%>
						</select>
					<% } %>
					</td>
				</tr>

			<tr></tr>

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