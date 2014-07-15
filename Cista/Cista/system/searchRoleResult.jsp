<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="com.cista.system.util.CLTUtil"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import ="com.cista.system.to.SysRoleTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String contextPath = (String)request.getContextPath();
	String pageSize 	= (String)request.getAttribute(CLTUtil.PAGE_SIZE);
    String resultSize 	= (String)request.getAttribute(CLTUtil.RESULT_SIZE);
    String pages 		= (String)request.getAttribute(CLTUtil.PAGES);
    String pageNo 		= (String)request.getAttribute(CLTUtil.PAGENO);
    String roleName 	= (String)request.getAttribute("roleName");

	List<SysRoleTo> data = (List<SysRoleTo>) request.getAttribute("Data");
	int dataSize = 0 ;
	if (data != null && data.size() > 0) dataSize = data.size();
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE><s:text name="SYSTEM.user.create.function.name"/></TITLE>
	<jsp:include page="/common/normalcheck.jsp" />
<script language="javascript">
	function doInit() {
<%
	if(dataSize>0) {
%>
		var form = document.forms(0);

		if(Number(form.pageNo.value)>=<%=pages%>) {
			nextButton.removeAttribute("href");
			lastButton.removeAttribute("href");
		}

		if(Number(form.pageNo.value)<=1) {
			prevButton.removeAttribute("href");
			firstButton.removeAttribute("href");
		}
<%
	}
%>
	}

	function nextPage(){
		document.forms(0).pageNo.value = Number(document.forms(0).pageNo.value) + 1;
		document.forms(0).action = "<%=contextPath%>/SearchRole.action";
		document.forms(0).submit();
	}

	function prevPage(){
		document.forms(0).pageNo.value = Number(document.forms(0).pageNo.value) - 1;
		document.forms(0).action = "<%=contextPath%>/SearchRole.action";
		document.forms(0).submit();
	}

	function goFirstPage(){
		document.forms(0).pageNo.value = 1;
		document.forms(0).action = "<%=contextPath%>/SearchRole.action";
		document.forms(0).submit();
	}

	function goLastPage(){
		document.forms(0).pageNo.value = <%=pages%>;
		document.forms(0).action = "<%=contextPath%>/SearchRole.action";
		document.forms(0).submit();
	}

	function selAll()
	{
		var forma = document.forms(0);
		if (forma.chkALL.checked==true){
			for (i=0 ; i<forma.chkDeleteList.length; i++){
				forma.chkDeleteList[i].checked = true;
			}
		}else{
			for (i=0 ; i<forma.chkDeleteList.length; i++){
				forma.chkDeleteList[i].checked = false;
			}
		}
	}

	function deleteRole()
	{
		var count = 0;
		var forma = document.forms(0);
		
		if(forma.chkDeleteList.length > 1)
		{
			for (i=0 ; i<forma.chkDeleteList.length; i++)
			{
				if( forma.chkDeleteList[i].checked == false )
				{
					forma.chkDeleteList[i].value = "-1";				
				}
				else 
				{
					count = count+1;
				}
			}
		}
		else
		{
			if(forma.chkDeleteList.checked) { count = 1;}			
		}
		
		if(count == 0)
		{
			alert("please at least choosing one item !!");
			return ;
		}

		forma.action = "DeleteRole.action";
    	forma.method = "post";
		forma.target = "result";
		forma.submit();
	}
	
	function modifyRole(roleId){
		var forma = document.forms(0);
		$('selectRole').value =roleId;
		forma.action = "ModifyRolePre.action";
    	forma.method = "post";
		forma.target = "result";
		forma.submit();
	}

	function createRole(){
		var forma = document.forms(0);
    	forma.action = "RoleCreatePre.action";
    	forma.method = "post";
		forma.target = "result";
		forma.submit();
	}

</script>
</head>
<body bgcolor="#FFFFFF" onload="doInit()" leftMargin="0" rightMargin="0" topMargin="0" bottomMargin="0">
<font color="red"><s:actionmessage/></font>
<%
	if(dataSize>0){
		int resultFrom = (Integer.parseInt(pageNo)-1)*Integer.parseInt(pageSize)+1;
		int resultTo = resultFrom + data.size()-1;
%>
	<form>
	<!-- need modify start-->
	<input type="hidden" id="roleName" name="roleName" value="<%=roleName%>"/>
	<input type="hidden" id="pageNo" name="pageNo" value="<%=pageNo%>"/>
	<input type="hidden" id="selectRole" name="selectRole"/>
	<!-- need modify end-->
	<div align="left">
		<table width="55%" border="0" cellspacing="0" cellpadding="0" align="left">
  			<tr>
	 			<td>
					<input type="button" value=<s:text name="IE.maintain.button.delete"/> class="button" onClick='deleteRole();'/>
					<input type="button" value=<s:text name="IE.maintain.button.create"/> class="button"  onClick='createRole();'/>
					<a href="javascript:goFirstPage()" class="portlet_content" id="firstButton">the first page </a> |
					<a href="javascript:prevPage()" class="portlet_content" id="prevButton">&lt;previous <%=pageSize%> </a> |
					<%=resultFrom%> - <%=resultTo%> of <%=resultSize%> |
					<a href="javascript:nextPage()" class="portlet_content" id="nextButton"> next <%=pageSize%> &gt;</a> |
					<a href="javascript:goLastPage()" class="portlet_content" id="lastButton">the last page </a> &nbsp;&nbsp;|
				</td>
			</tr>
			<tr>
				<td>
					<hr color="#A60404" size="1"/>
				</td>
			</tr>
			<tr>
				<td>
					<div id="tb2-container">
						<table id="formdata" width="100%"  border=1 cellpadding=0 cellspacing=0 bordercolordark=#FFFFFF  class="Himax-TableContent" >
							<thead>
								<tr class= "portlet-title-bg3">
									<th class= "Himax-asl-col-middle" colspan="2">Check All<input type="checkbox" name="chkALL" onClick='selAll()'></th>
									<th class= "Himax-asl-col-large">Role Name</th>
								<tr>
							</thead>
							<tbody>

<%
	int i=0;
	String className = "";

	for(SysRoleTo role : data ){
		className = ((i++)%2==0?"portlet-title-bg1":"portlet-title-bg2");
		String strRoleId = role.getRoleId().toString().equals("")?" ":role.getRoleId().toString();
		String strRoleName = role.getRoleName().equals("")?" ":role.getRoleName();
%>
		<tr class="<%=className%>">
			<td class= "Himax-asl-col-small"><%=i+resultFrom-1%></td>
			<td class= "Himax-asl-col-small"><input type="checkbox" name="chkDeleteList" value='<%=strRoleId%>'></td>
			<td class= "Himax-asl-col-large"><a href="javascript:modifyRole(<%=strRoleId%>)"><%=strRoleName%></td>
		</tr>
	<%}%>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
<%}%>
</body>
</html>