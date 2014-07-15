<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.text.DecimalFormat"%>

<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ page import ="com.clt.system.to.SysUserTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogPaperVerTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogPaperVerTo"%>
<%@ page import ="com.clt.quotation.erp.to.ErpCurrencyTo"%>
<%@ page import ="com.clt.system.to.SysFunParameterTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	long t1 = System.currentTimeMillis();
	String contextPath = (String)request.getContextPath();
	// Get Current User
	SysUserTo curUser =
            (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);

%>
<html>

<head>
	

<jsp:include page="/common/normalcheck.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    



<script type="text/javascript">    
function doInit(){



}


Ext.onReady(function(){

		var myMask = new Ext.LoadMask(Ext.getBody(), {  
			msg : "Please wait..."  
		});  


	//Form
	var fieldForm = new Ext.form.FormPanel({
        id: 'fieldForm',
		labelAlign: 'left',
		region:'center',
		bodyStyle  : 'padding: 1px; background-color: #DFE8F6',
		layout: "column",
		anchor: "0",
		frame: true,
		autoHeight: true,
		width:180,
        items: [{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "button",
					id:'fieldFormRatio',
					text:'Test',
					name: 'fieldFormRatio',
												listeners:{
								"click":function(){
									showLoadingMask("");
								}
							},
					//readOnly:true,
					//style:"background:#EEEEEE;color:#BC6618;",
					anchor:'90%'
				}]
			}],
        renderTo: Ext.getBody()
    });


function showLoadingMask(loadingMessage)
{
	if (Ext.isEmpty(loadingMessage))
	
	//Use the mask function on the Ext.getBody() element to mask the body element during Ajax calls
		Ext.lib.Ajax.request(
			'POST',
			'quotation/compare/ApplyPaperCompare.action',
			{success: function(response){	
				if( response.responseText != "ERROR" ) {

					Ext.Msg.alert('Message', 'Update OK', function(){
					});
				}else{
					Ext.Msg.alert("Error", "Save Error.");

				}
							
			},failure: function(){

				Ext.Msg.alert("Error", "Connection Error , Please Inform CLT IT");

			}},
			'data=111' +  
			'&inquiryGroupUid=11' + 
			'&paperVerUid=11'
		);//Ext.lib.Ajax.request(

	alert("pppppppppppppp");
	loadText = 'Loading... Please wait';
	Ext.Ajax.on('beforerequest',function(){Ext.getBody().mask(loadText, 'loading') }, Ext.getBody());
	Ext.Ajax.on('requestcomplete',Ext.getBody().unmask ,Ext.getBody());
	Ext.Ajax.on('requestexception', Ext.getBody().unmask , Ext.getBody());
}


});


</script>
<style type="text/css">

.ext-el-mask
{
color:gray;
cursor:default;
opacity:0.6;
background-color:grey;
}

.ext-el-mask-msg div {
background-color: #EEEEEE;
border-color: #A3BAD9;
color: #222222;
font: 1.2em tahoma,arial,helvetica,sans-serif;
}

.ext-el-mask-msg {

padding: 10px;

}
	.button_align_right {text-align:center; float:center}
	
</style>
</head>
<body onLoad="doInit()">


<!-- <div id="header"><h3>報價單</h3></div> -->
<div id="editVer-form"></div>


 </body>
</html>