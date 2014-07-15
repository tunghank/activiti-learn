<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import ="com.cista.system.util.CLTUtil"%>
<%@ page import ="com.cista.system.to.SysRoleFunctionTo"%>
<%@ page import ="com.cista.system.to.SysFunctionTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String contextPath = (String)request.getContextPath();

	List<SysFunctionTo> functionList = (List<SysFunctionTo>) request.getAttribute("functionList"); // function �M��
	List<SysRoleFunctionTo> data = (List<SysRoleFunctionTo>) request.getAttribute("Data"); // ��ڸ��
	int functionDataSize = ( (functionList != null && functionList.size() > 0 )? functionList.size() : 0 );
	String roleId = (String)  request.getAttribute("roleId");
	String leaf = (String)  request.getAttribute("leaf");
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE>System Role Search Function</TITLE>
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

	function modifyRoleFunction(){
		var forma = document.forms(0);
		for (i=0 ; i<forma.chkModifyList.length; i++){
			if( forma.chkModifyList[i].checked == false ){
				forma.chkModifyList[i].value = "-1";
			}
		}

		forma.action = "ModifyRoleFunction.action";
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
</head>
<body bgcolor="#FFFFFF" leftMargin="0" rightMargin="0" topMargin="0" bottomMargin="0">
<font color="red"><s:actionmessage/></font>
<%
	if(functionDataSize>0){
%>
	<form name="roleFunctionForm" id="roleFunctionForm" method="post">
	<input type="hidden" id="leaf" name="leaf" value="<%=leaf%>"/>
	<input type="hidden" id="roleId" name="roleId" value="<%=roleId%>"/>
	<div align="left">
		<table width="60%" border="0" cellspacing="0" cellpadding="0" align="left">
			<tr>
				<td>
					<input type="button" value=<s:text name="IE.maintain.button.save"/> class="button" onClick='modifyRoleFunction();'/>
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
									<th class= "Himax-asl-col-middle">Parent Name</th>
									<th class= "Himax-asl-col-middle">Function Name</th>
								<tr>
							</thead>
							<tbody>

<%
	int i=0;
	String className = "";
	//String strRoleId = "";

	for(SysFunctionTo info : functionList ){
		className = ((i++)%2==0 ? "portlet-title-bg1":"portlet-title-bg2");
		String strFunction 		= info.getTitle() !=null? info.getTitle():"";
		String strCls			= info.getCls();
		String strFunctionId	= info.getId() !=null?info.getId():"";
%>
		<tr class="<%=className%>">
			<td class= "Himax-asl-col-small"><%=i%></td>
			<td class= "Himax-asl-col-small"><input type="checkbox" name="chkModifyList"
			<%
				if (data != null && data.size()>0){
					for (SysRoleFunctionTo roleInfo : data){
						String strRoleFunctionId = roleInfo.getFunctionId().toString();
						//strRoleId 		= roleInfo.getRoleId().toString()!= null ? roleInfo.getRoleId().toString():"";
						if (strFunctionId.equals(strRoleFunctionId)){	%>checked<%
							
						}
					}
				}
			%>
				onclick="radCheck(this);"  value='<%=strFunctionId%>'
			></td>
			<td class= "Himax-asl-col-small">
				<%if(strCls.equals(CLTUtil.FUNCTION_FILE)){%> <font color = "blue">
				<%}else %> <font color = "green">
					<%=strCls%>
				</font>
			</td>
			<td>&nbsp<%=strFunction%></td>
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