<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="com.cista.system.util.CLTUtil"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
  String contextPath = (String)request.getContextPath();
   
  String quoteHeaderUid = (String)request.getAttribute("quoteHeaderUid");
  quoteHeaderUid = null != quoteHeaderUid ? quoteHeaderUid : "" ;
%>
<HTML>
<HEAD>
<TITLE><s:text name="System.system.title" /></TITLE>
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
<script type="text/javascript" src="<%=contextPath%>/js/ext-all-debug.js"></script>
<script type="text/javascript">

Ext.onReady(function() {
	//Header
	var headerPage = new Ext.Panel({
		region:'north',
		contentEl: 'north',//id為center的div
		height:'5%',
		title:'<s:text name="System.system.welcome.message" />',
		bodyStyle: 'background-color: #BDE6FB'

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
		title:'<s:text name="System.system.title" />',
		bodyStyle: 'padding:5px;background-color: #BDE6FB',
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

</HEAD>
<BODY bgColor=#ffffff leftMargin=0 topMargin=0>
<div id="north"></div>
<div id="center">
	<table border="0" cellpadding="0" align="center" cellspacing="20" width="80%">
	  <tr>
		<td valign="top" width="60%" align="left"> <p><span class="LoginTitle"><font color="#FF9933"><b><font color="#FF6633" size='5'><s:text name="System.system.title" /></font></b></font></span><br>
			<br>
			<div valign="center" align="left"><img src="<%=contextPath%>/images/<s:text name='System.system.site'/>-Logo.png" width="380" height="140"></div>
			</p>
			<br>
			<br>
		<!--Ext JS Form -->
		<div class="x-box-blue"><div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div></div>
		<div class="x-box-blue"><div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">

		  <form name="loginForm" id="loginForm" action="Login.action" method="post">
			<table height="120" width="400" border="0" cellspacing="2" cellpadding="2" align="center">
			  <tr>
				<td align="right"><s:text name="System.login.message.userid" /></td>
				<td width="4">:</td>
				<td>
				<input type="text" name="userId" size='40' />
				</td>
			  </tr>
			  <tr>
				<td align="right"><s:text name="System.login.message.password" /></td>
				<td width="4">:</td>
				<td>
				<input type="password" name="password" />
				</td>
			  </tr>
			  <tr>
				<td align="left" colspan=3><p align="center"><font color="red">
				<s:actionmessage/>			
				</font></p></td>

			  </tr>
			  <tr>
				<td> <div align="right"></div></td>
				<td width="4">&nbsp;
				</td>
				<td>
				<input type="hidden" name="quoteHeaderUid" id="quoteHeaderUid" value="<%=quoteHeaderUid%>" />&nbsp;
				<input type="submit" value="Submit" class="button"/>&nbsp;&nbsp;<input type="reset" value="Reset" class="button" />
				</td>
			  </tr>
			</table>

		</div></div></div></div>
		<div class="x-box-blue"><div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div></div>

		  <br>
		  <div align="left"><a href="<%=request.getContextPath()%>/login/forgotpassword.jsp" class="OrangeLine">
				<s:text name="System.login.message.forgetpasswd" /></a></div>
		  </td>
		<td width="40%"> 
		<div valign="center" align="center"><img src="<%=contextPath%>/images/Logo.png" width="380" height="110"></div>
		</td>
	  </tr>
	  <tr>
	  <td>&nbsp;</td>
	  </tr>
	  <tr>
	  <td>&nbsp;</td>
	  </tr>
	  <tr>
	  <td>&nbsp;</td>
	  </tr>
	</table>
</div>
<div id="south">
	<jsp:include page="/layout/login-footer.jsp" />
</div>
</BODY>
</HTML>