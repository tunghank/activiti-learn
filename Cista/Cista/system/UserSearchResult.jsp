<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List"%>
<%@ page import ="com.cista.system.util.CistaUtil"%>
<%@ page import ="com.cista.system.to.SysUserTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String contextPath = (String)request.getContextPath();
	List<SysUserTo> result = (List<SysUserTo>)request.getAttribute("result");

	// for paging
	String pageSize = (String)request.getAttribute(CistaUtil.PAGE_SIZE);
    String resultSize = (String)request.getAttribute(CistaUtil.RESULT_SIZE);
    String pages = (String)request.getAttribute(CistaUtil.PAGES);
    String pageNo = (String)request.getAttribute(CistaUtil.PAGENO);

	//Criteria
    String userId = (String)request.getAttribute("userId");
	String roleId = (String)request.getAttribute("roleId");

%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="/common/normalcheck.jsp" />

	<script language="javascript">
	function doInit() {
<%
	if(result!=null && result.size()>0) {
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

	function nextPage() {
		document.forms(0).pageNo.value = Number(document.forms(0).pageNo.value) + 1;
		document.forms(0).action = "<%=contextPath%>/UserSearch.action";
		document.forms(0).submit();
	}
	function prevPage() {
		document.forms(0).pageNo.value = Number(document.forms(0).pageNo.value) - 1;
		document.forms(0).action = "<%=contextPath%>/UserSearch.action";
		document.forms(0).submit();
	}
	function goFirstPage() {
		document.forms(0).pageNo.value = 1;
		document.forms(0).action = "<%=contextPath%>/UserSearch.action";
		document.forms(0).submit();
	}
	function goLastPage() {
		document.forms(0).pageNo.value = <%=pages%>;
		document.forms(0).action = "<%=contextPath%>/UserSearch.action";
		document.forms(0).submit();
	}
	function selAll(){
		var forma = document.forms(0);
		if (forma.chkALL.checked==true){
			for (i=0 ; i<forma.chkUserList.length; i++){
				forma.chkUserList[i].checked = true;
			}
		}else{
			for (i=0 ; i<forma.chkUserList.length; i++){
				forma.chkUserList[i].checked = false;
			}
		}
	}

	function disableUser(){
		var forma = document.forms(0);
		for (i=0 ; i<forma.chkUserList.length; i++){
			if( forma.chkUserList[i].checked == false ){
				forma.hidUserChkList[i].value = "1";
			}else{
				forma.hidUserChkList[i].value = "0";
			}
		}
    	forma.action = "DisableUser.action";
    	forma.method = "post";
		forma.target = "result";
		forma.submit();
	} 



	/*
	function openWindowCenter(url, title, width, height){
		var x = screen.availWidth/2 - width/2;
		var y = screen.availHeight/2 - height/2;

		var features = 'width=' + width;
		features += ',height=' + height;
		features += ',top=' + y;
		features += ',left=' + x;
		features += ',toolbar=no,status=no,resizable=yes,scrollbars=no,modal=yes';
		//open_windows=window.open(url,title, features);
		open_windows=window.showModalDialog(url,title, features);
		open_windows.focus();
	}*/

	function modifyUser(userId,company){
		var strCompany ="";
		if(company != "<%=CistaUtil.CISTA_ROLE%>"){
			strCompany = window.showModalDialog('/system/companyWindow.jsp',null,"dialogWidth='195px';dialogHeight='140px';status='no';help=no");
		}

		// �ϥΪ̪������� �ȹw�] vendor
		if(strCompany == undefined ) {
			strCompany = "<%=CistaUtil.VENDOR_ROLE%>" ;
		}
		$('userCompany').value = strCompany;

		var forma = document.forms(0);
		$('selectUserId').value = userId;
		forma.action = "ModifyUserPre.action";
		forma.method = "post";
		forma.target = "result";
		forma.submit();
	}
</script>
</head>
<body bgcolor="#FFFFFF" onload="doInit()" leftMargin="0" rightMargin="0" topMargin="0" bottomMargin="0">
<s:actionmessage/>
<%
	if(result!=null && result.size()>0){
		int resultFrom = (Integer.parseInt(pageNo)-1)*Integer.parseInt(pageSize)+1;
		int resultTo = resultFrom + result.size()-1;
%>
	<form name="userSearchForm" id="userSearchForm" method="post">

	<!-- need modify start-->
	<input type="hidden" id="userId" name="userId" value="<%=userId%>"/>
	<input type="hidden" id="roleId" name="roleId" value="<%=roleId%>"/>
	<input type="hidden" id="pageNo" name="pageNo" value="<%=pageNo%>"/>
	<input type="hidden" id="selectUserId" name="selectUserId" />
	<input type="hidden" id="userCompany" name="userCompany" />
	<!-- need modify end-->
	<div align="left">
		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="left">
  			<tr>
	 			<td>
					<input type="button" value='<s:text name="System.maintain.button.update"/>' class="w100-button" onClick='disableUser();'/>
					
					<br>
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
						<table id="formdata" width="100%"  border=1 cellpadding=0 cellspacing=0 bordercolordark=#FFFFFF  class="TableContent" >
							<thead>
								<tr class="portlet-title-bg3">
									<th width="10%" align="left" colspan="2" >Disable? <input type="checkbox" name="chkALL" onClick='selAll()'></th>
									<th width="20%" align="left" nowrap >USER_ID</th>
									<!--<th width="10%" align="left" nowrap >Role</th>-->
									<th width="8%"  align="left" nowrap >PASSWORD</th>
									<th width="9%"  align="left" nowrap >COMPANY</th>
									<th width="9%"  align="left" nowrap >DEPARTMENT</th>
									<th width="20%" align="left" nowrap >EMAIL</th>
									<th width="9%"  align="left" nowrap >PHONE_NUM</th>
									<th width="8%"  align="left" nowrap >UPDATE_BY</th>
									<th width="9%"  align="left" nowrap >UDT</th>
								<tr>
							</thead>
							<tbody>
	<%
	int i=0;
	for(SysUserTo sysUser : result){
		userId = sysUser.getUserId()!=null?sysUser.getUserId():"";
		String password 	= sysUser.getPassword()!=null?sysUser.getPassword():"";
        String company 		= sysUser.getCompany()!=null?sysUser.getCompany():"";
        String department 	= sysUser.getDepartment()!=null?sysUser.getDepartment():"";
        String eMail 		= sysUser.getEmail()!=null?sysUser.getEmail():"";
        String phoneNum		= sysUser.getPhoneNum()!=null?sysUser.getPhoneNum():"";      
        String updateBy 	= sysUser.getUpdateBy()!=null?sysUser.getUpdateBy():"";     
        //String udt 			= sysUser.getUdt()!=null?sysUser.getUdt():"";
		String disable 		= sysUser.getActive()!=null?sysUser.getActive():"";
		//String roleName 	= sysUser.getRoleName()!=null?sysUser.getRoleName():"";
        String className = ((i++)%2==0 ? "portlet-title-bg1":"portlet-title-bg2");
	%>
						<tr class="<%=className%>">
							<td class= "asl-col-small"><%=i+resultFrom-1%></td>
							<td align="left" nowrap >
								<input type="checkbox" name="chkUserList"<%if (disable.equals("0")){%>checked<%}%>>
								<input type='hidden' name='hidUserList' value='<%=userId%>'/>
								<input type='hidden' name='hidUserChkList' value='0'/>
							</td>
							<td align="left" nowrap >&nbsp;
								<a href="javascript:modifyUser('<%=userId%>','<%=company%>')"><%=userId%></a>
							</td>
							<td align="left" nowrap >&nbsp;<%=password%></td>
							<td align="left" nowrap >&nbsp;<%=company%></td>
							<td align="left" nowrap >&nbsp;<%=department%></td>
							<td align="left" nowrap >&nbsp;<%=eMail%></td>
							<td align="left" nowrap >&nbsp;<%=phoneNum%></td>
							<td align="left" nowrap >&nbsp;<%=updateBy%></td>
							<td align="left" nowrap >&nbsp;<%=udt%></td>
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
<%
	}
%>

</body>
</html>