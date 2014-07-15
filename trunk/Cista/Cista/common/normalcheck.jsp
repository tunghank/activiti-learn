<%@ page errorPage="/common/chkerror.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="com.cista.system.util.CLTUtil"%>
<%@ page import ="com.cista.system.to.SysUserTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String contextPath = (String)request.getContextPath();
    String EXCEP_MSG_SESSION =CLTUtil.getMessage("System.error.access.nologin");

  	// get current user info
    SysUserTo curUserTo =
            (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
    // check session
    if (null == curUserTo) {
        throw new Exception(EXCEP_MSG_SESSION);
   }
%>

<TITLE><s:text name="System.system.title"/></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<!-- CSS Config -->
<link href="<%=contextPath%>/css/resources/css/ext-all.css" rel="stylesheet" type="text/css"/>
<link href="<%=contextPath%>/css/clt_quo.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="<%=contextPath%>/js/jscalendar-1.0/calendar-blue2.css" title="aqua" />
<%
	//disable browser client cache.
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%>

<!-- import the calendar script -->
<script type="text/javascript" src="<%=contextPath%>/js/jscalendar-1.0/calendar.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/jscalendar-1.0/calendar-setup.js"></script>
<!-- import the language module -->
<script type="text/javascript" src="<%=contextPath%>/js/jscalendar-1.0/lang/calendar-en.js"></script>

<!-- JS Config -->
<script type="text/javascript" src="<%=contextPath%>/js/prototype1603.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/ext-all-debug.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/StringEnhance.js"></script>
<!-- import Smart Search Script -->
<script type="text/javascript" src="<%=contextPath%>/js/dynamicTable.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/form.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/ssQuo.js"></script>


<!-- import the Time Picker -->
<script type="text/javascript" src="<%=contextPath%>/js/timepicker/anytime.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/js/timepicker/anytime.css" />

