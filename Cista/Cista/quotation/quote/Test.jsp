<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.text.DecimalFormat"%>

<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ page import ="com.clt.system.to.SysUserTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogPaperVerTo"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	long t1 = System.currentTimeMillis();
	String contextPath = (String)request.getContextPath();
	// Get Current User
	SysUserTo curUser =
            (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
	
	List<QuoCatalogTo> catalogList = (List<QuoCatalogTo>)request.getAttribute("catalogList");
	catalogList = null != catalogList ? catalogList : new ArrayList<QuoCatalogTo>();

%>
<html>

<head>
	

<jsp:include page="/common/normalcheck.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    

<script type="text/javascript">    
Ext.onReady(function(){

	var noteForm = new Ext.form.FieldSet({
        id: 'noteForm',
        frame: true,
        labelAlign: 'left',
        title: '報價注意事項',
		region:'center',
		style: 'padding: 0 0 0 0',
		//style:'font-weight:bold;text-align:left;padding-right:0px;',
        //bodyStyle: 'padding:5px',
		bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		//bodyStyle: 'padding-top: 0px',
        labelWidth: 90,
		height :160,
		collapsible:true,
        defaults: {width: 120},	// Default config options for child items
        defaultType: 'textfield',
		columnWidth:.5,
		layout:'form',
        items: [{
			xtype: 'textarea',
			id:'noteFormNotes',
            fieldLabel: '備註',
            name: 'noteFormNotes',
			multiline: true,
			width : 280,
			height : 120,
			align: 'left',
			col:7,
			row:7
        }],

        renderTo: "editVer-form"
    });
});

</script>
</head>
<body>
<!-- <div id="header"><h3>報價單</h3></div> -->
<div id="editVer-form"></div>
 </body>
</html>