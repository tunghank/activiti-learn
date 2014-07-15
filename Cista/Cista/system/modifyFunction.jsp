<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.util.List"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="com.cista.system.util.CistaUtil"%>
<%@ page import ="com.cista.system.to.SysFunctionTo"%>

<%@ taglib prefix="s" uri="/struts-tags" %>

<%
	SysFunctionTo curFunction = (SysFunctionTo) request.getAttribute("curFunction");
	List<SysFunctionTo> allParents = (List<SysFunctionTo>) request.getAttribute("ParentList");
%>

<html>
<head>
	<TITLE>System Modify Function Function</TITLE>
	<jsp:include page="/common/normalcheck.jsp" />
	<script type="text/javascript">

		function doInit(){
			<%if (curFunction != null ){%>
				SelectItem_Combo_Value($('cls') , "<%=curFunction.getCls()%>");
				SelectItem_Combo_Value($('parentId') , "<%=curFunction.getParentId()%>");
			<%}%>
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

			if ($F('cls') == "<%=CistaUtil.FUNCTION_FILE%>" ){ 
				if($F('hrefTarget') == ""){
					msg.innerHTML += "<br>" + (++cnt)+". " +" Href Target must be select !!"; 
					errorFlag=1;
				}
				if ($F('url') == ""){ 
					msg.innerHTML += "<br>" + (++cnt)+". " +" URL must be inut !!"; 
					errorFlag=1;
				}
			}else {
				if($F('hrefTarget') != ""){
					msg.innerHTML += "<br>" + (++cnt)+". " +" Href Target must be Null!!"; 
					errorFlag=1;
				}
				if ($F('url') != ""){ 
					msg.innerHTML += "<br>" + (++cnt)+". " +" URL must be Null !!"; 
					errorFlag=1;
				}
			}

			if(errorFlag !=0){
					return ;
			}else{
				msg.innerHTML = "Processing data , please wait a minutes...";
				modifyFunction();
			}
		}

		function modifyFunction(){
			var forma = document.forms(0);
			forma.action = "ModifyFunction.action";
			forma.method = "post";
			forma.target = "result";
			forma.submit();
		}
	</script>
</head>
<body bgcolor="#FFFFFF" leftMargin="0" rightMargin="0" topMargin="0" bottomMargin="0" onload="doInit();">
<s:actionmessage/>
	<form name="userSearchForm" id="userSearchForm" method="post">
<%
	if (curFunction != null ){
%>
		<input type="hidden" id="id" name="id" value="<%=curFunction.getId()%>"/>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">	
			<tr>
				<td><font color="red"><b><div id="msg"></b></div></font></td>
			</tr>
		</table>

		<table id="formdata" width="40%"  border=1 cellpadding=0 cellspacing=0 bordercolordark=#FFFFFF  class="Himax-TableContent" >

			<thead>
				<tr class= "portlet-title-bg3">
					<th class = "Himax-col-width" colspan = "2">Function Data Detail</th>
				</tr>
			</thead>

			<tbody>
				<tr class = "portlet-title-bg1">
					<td>Parent Name</td>
					<td>
						<select id = "parentId" name = "parentId" class = "Himax-col-width" >
							<option value = ''>-----</option>
			<%
				if ( allParents != null && allParents.size() > 0){
					for(SysFunctionTo parent : allParents ){
			%>
							<option value = "<%=parent.getId()%>"><%=parent.getTitle()%>
			<% }} %>
						</select>
					</td>
				</tr>

				<tr class = "portlet-title-bg2">
					<td>Function Name</td>
					<td><input 	type="text" 
								id = "functionName" name="functionName" class = "Himax-col-width"
								value = "<%=curFunction.getTitle()%>"/></td>
				</tr>

				<tr class = "portlet-title-bg1">
					<td>Type</td>
					<td>
						<select type="text" class = "Himax-col-width" id = "cls" name="cls">
							<option value = ''>-----</option>
							<% for (int i = 0 ; i < CistaUtil.FUNCTION_CLS.length ; i ++) {%>
								<option value = "<%=CistaUtil.FUNCTION_CLS[i]%>"><%=CistaUtil.FUNCTION_CLS[i]%></option>
							<%}%>
						</select>
					</td>
				</tr>

				<tr class = "portlet-title-bg2">
					<td>URL</td>
					<td><input 	type="text" 
								id = "url" name="url" class = "Himax-col-width"
								value = "<%=curFunction.getUrl() == null ? "":curFunction.getUrl()%>"/></td>
				</tr>

				<tr class = "portlet-title-bg1">
					<td>Target</td>
					<td><input 	type="text" 
								id = "hrefTarget" name="hrefTarget" class = "Himax-col-width"
								value = "<%=curFunction.getHrefTarget() == null ? "":curFunction.getHrefTarget()%>"/></td>
				</tr>

				<!--button-->
				<tr class = "portlet-title-bg3">
					<td align = "right" colspan = "2">
						<input 
							name = "saveBtn" id = "saveBtn" 
							type="button" value=<s:text name="IE.maintain.button.save"/>
							class="button" onclick = "checkData();"/>&nbsp;&nbsp;
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
