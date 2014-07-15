<%@ page isErrorPage="true" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String contextPath = (String)request.getContextPath();
    String errid = (String)request.getParameter("errid");
    errid = null!=errid? errid: "";
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

<!-- JS Config -->
<script type="text/javascript" src="<%=contextPath%>/js/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/ext-all.js"></script>
<script type="text/javascript">

Ext.onReady(function() {
	//Header
	var headerPage = new Ext.Panel({
		region:'north',
		contentEl: 'north',//id為center的div
		height:'5%',
		title:'Welcome to Himax'

	});

	//Main
	var mainPage = new Ext.Panel({
		region:'center',
		height:'85%',
		bodyStyle: 'padding:5%',
		contentEl: 'center'//id為center的div
	});

	//Footer
	var footerPage = new Ext.Panel({
		region:'south',
		height:'10%',
		title:'Himax HXIE System',
		bodyStyle: 'padding:5px',
		contentEl: 'south'//id為center的div

	});


	//Crate one viewport

	var viewport = new Ext.Viewport({
		layout:'border',
		defaults: {
			collapsible: false,
			split: false,
			autoHeight: false,
			border: false
		},
		items: [headerPage,mainPage,footerPage]
	});


});
	
</script>
</head>
<body leftmargin="0" topmargin="0" >
<div id="north"></div>
<div id="center">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="Himax-OrangeLine">
	<tr>
    	<td class="Himax-Title" >&nbsp;</td>
        <td valign="bottom">
                    <div align="right"></div>
                    <div align="right"></div>
        </td>
    </tr>
</table>

<br>
<div align="center">

<table width="95%"  border=0 cellpadding=0 cellspacing=0 height="300">
	<tr>
    	<td align="center">Error Message</td>
    </tr>
  <tr>
    <td height="1" ></td>
  </tr>
  <tr height="1" valign="top">
    <td align="center"><font color="red">
<%=exception.getMessage()%>

    </font>

<br><br><br>
<p><s:text name="System.system.title" />,  <a href="<%=request.getContextPath()%>/index.jsp" target="_top">Retry</a>? <br></p>
</td>

</tr>
</table>
</div>
</div>
<div id="south">
	<jsp:include page="/layout/login-footer.jsp" />
</div>
</body>
</html>
