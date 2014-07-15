<%@ page isErrorPage="true" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="com.cista.system.util.CLTUtil"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String contextPath = (String)request.getContextPath();
	String error = (String)request.getAttribute("error");
	error =null!=error?error:"";
%>
<html>




<head>
<TITLE><s:text name="System.error.page" /></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
 
<!-- CSS Config -->
<link href="<%=contextPath%>/css/resources/css/ext-all.css" rel="stylesheet" type="text/css"/>
<link href="<%=contextPath%>/css/clt_quo.css" rel="stylesheet" type="text/css">
<%
	//disable browser client cache.
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%>

</head>
<body leftmargin="0" topmargin="0" >


<table width="100%" border="0" cellspacing="0" cellpadding="0" class="Himax-OrangeLine">
	<tr>
    	<td class="Himax-Title" >Error Message</td>
        <td valign="bottom">
                    <div align="right"></div>
                    <div align="right"></div>
        </td>
    </tr>
</table>

<br>
<div align="center">

<table width="95%"  border=0 cellpadding=0 cellspacing=0 height="300">
  <tr height="5">
    <td height="10" ></td>
  </tr>
  <tr height="1" valign="top">
    <td align="center"><font color="red">
	<%=error%><s:actionerror /><s:actionmessage/>
    </font>

<br><br><br>
<p><s:text name="System.system.title" />,  <a href="<%=request.getContextPath()%>/index.jsp" target="_top">Retry</a>? <br></p>
</td>

</tr>
</table>
</div>


</body>
</html>
