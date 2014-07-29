<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.text.DateFormat"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.List"%>
<%@ page import ="com.cista.system.util.CistaUtil"%>
<%@ page import ="com.cista.system.to.SysUserTo"%>
<%@ page import ="com.cista.system.to.SysDepartmentTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
  String contextPath = (String)request.getContextPath();
  List<SysDepartmentTo> allDepartments = (List<SysDepartmentTo>) request.getAttribute("department");
  
  // current user
  SysUserTo curUser = (SysUserTo) session.getAttribute(CistaUtil.CUR_USERINFO);
  String curUserId = curUser.getUserId();  
%>
<html>
<head>

<TITLE>System User Create Function</TITLE>
<jsp:include page="/common/normalcheck.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">  

<script type="text/javascript">
Ext.onReady(function(){
    Ext.QuickTips.init();
	//詢價 內容 Grid
    function formatDate(value){
        return value ? value.dateFormat('Y/m/d') : '';
    };



	// the column model has information about grid columns
    // dataIndex maps the column to the specific data field in
    // the data store (created below)
	
	//checkbox選擇模型
	//var sm = new Ext.grid.CheckboxSelectionModel({ checkOnly: true });
	
	// var sm:new Ext.grid.RowSelectionModel({singleSelection:true}) //選擇模型改為了行選擇模型
	//var sm = new Ext.grid.CheckboxSelectionModel();
	
    //sm.handleMouseDown = Ext.emptyFn;//不響應MouseDown事件
    //sm.on('rowselect',function(sm_,rowIndex,record){//行選中的時候
       
    //}, this);
	
	//sm.on('rowdeselect',function(sm_,rowIndex,record){//行未選中的時候
       
    //}, this); 




	//Form
	var userForm = new  Ext.form.Panel({
        id: 'userForm',
		title: 'User Information',
		labelAlign: 'left',
		frame:true,
		width:600,
		renderTo: Ext.getBody(),
		layout: "column",
		bodyPadding: 10,
	    defaults: {
			border: false
		},
		fieldDefaults: {
			msgTarget: 'side',
			autoFitErrors: false,
			labelSeparator :'：',//分隔符
			labelWidth : 50,//標籤寬度
			msgTarget : 'side',
			width : 100			
		},
        items: [{	//Role
					xtype: "label",
					columnWidth: .2,
					anchor: "100",
					text: 'Role:'
				},{
					xtype: "textfield",
					width: .8,
					id:'userRole',
					name: 'userRole',
					readOnly:true
				},{//User ID
					xtype: "label",
					columnWidth: .2,
					text: 'User ID:'
				},{
					xtype: "textfield",
					columnWidth: .8,
					id:'userId',
					name: 'userId',
					readOnly:true
				},{//Real Name
					xtype: "label",
					columnWidth: .2,
					text: 'Real Name:'
				},{
					xtype: "textfield",
					columnWidth: .8,
					width:200,
					id:'realName',
					name: 'realName',
					readOnly:true
				},{//Company
					xtype: "label",
					columnWidth: .2,
					text: 'Company:'
				},{
					xtype: "textfield",
					columnWidth: .8,
					id:'company',
					name: 'company',
					readOnly:true
				}]
    });


	var nextForm = new  Ext.form.FormPanel({
        id: 'nextForm',
		hidden:true,
		//style: 'padding: 0 0 0 0',
		//style:'font-weight:bold;text-align:left;padding-right:0px;',
        //bodyStyle: 'padding:5px',
		//bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		headers: {'Content-Type':'multipart/form-data; charset=UTF-8'},
		labelAlign: 'left',
        items: [{
            layout:'column',
            items: [{
				columnWidth: .05,
				height:30,
				items: [{
					xtype : 'textfield',
					name: 'inquiryNum',
					readOnly:true,
					style:"background:#EEEEEE;color:#0000B7;",
					hidden: true
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype : 'textfield',
					name: 'paperVerUid',
					readOnly:true,
					style:"background:#EEEEEE;color:#0000B7;",
					hidden: true
				}]
			}]
        }]
    });

	function claerUserForm(){
		userForm.form.reset();
	}




});


</script>
<style type="text/css">

    .icon-grid {
        background-image:url(<%=contextPath%>/css/resources/images/icons/fam/grid.png) !important;
    }

	.down {
        background-image:url(<%=contextPath%>/css/resources/images/icons/fam/down.png) !important;
    }
	.up {
        background-image:url(<%=contextPath%>/css/resources/images/icons/fam/up.png) !important;
    }
    .edit {
        background-image:url(<%=contextPath%>/css/resources/images/icons/fam/table_edit.png) !important;
    }
    .add {
        background-image:url(<%=contextPath%>/css/resources/images/icons/fam/add.gif) !important;
    }
    .remove {
        background-image:url(<%=contextPath%>/css/resources/images/icons/fam/delete.gif) !important;
    }
    .save {
        background-image:url(<%=contextPath%>/css/resources/images/icons/fam/save.gif) !important;
    }
    .tabs {
        background-image:url(<%=contextPath%>/css/resources/images/icons/fam/tabs.gif) !important;
    }
    .preview {
        background-image:url(<%=contextPath%>/css/resources/images/icons/fam/application_form_magnify.png) !important;
    }
	.button_align_right {text-align:right; float:right}
	.x-grid3-row{background-color:#CCFF66;}
	.x-grid3-row-alt{background-color:#FFFFCC;}
	.valid-text{background:#EEEEEE;color:#BC6618;}

	/** HTML Layout **/
	#functionTitle  {position:absolute; visibility:visible; z-index:1; top:5px; left:5px;}
	#userForm  {position:absolute; visibility:visible; z-index:2; top:50px; left:5px; }
	

</style>

</head>


<BODY bgColor=#FFFFFF leftMargin=0 topMargin=0 >

<div id="functionTitle" >
<table border="0" cellpadding="0" align="left"  width=300 >
	<tr>
		<td class="Title">
		<div align="left">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left" class="OrangeLine">
				<tr>
					<td class="Title">System User Create Function</td>
				</tr>
			</table>
		</div>
		</td>
	</tr>
</table>
</div>
<div id="userForm" ></div>

	
</body>
</html>