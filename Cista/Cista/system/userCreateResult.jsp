<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import ="com.cista.system.util.CLTUtil"%>
<%@ page import ="com.cista.system.to.SysUserTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String contextPath = (String)request.getContextPath();
	SysUserTo ADUser = (SysUserTo) request.getAttribute("ADUser");
	if (ADUser != null)
	{
		out.println(ADUser.getRealName());
		session.removeAttribute("ADUSER");
	}
	
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="/common/normalcheck.jsp" flush="true" />
</head>

<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">	
		<tr>
			<!--message-->
			<td>
				<font color="red"><div><s:actionmessage/></div></font>			
			</td>
		</tr>	
	</table>
</body>
</html>