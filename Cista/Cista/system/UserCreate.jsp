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
    String EXCEP_MSG_SESSION =CistaUtil.getMessage("System.error.access.nologin");

  	// get current user info
    SysUserTo curUserTo =
            (SysUserTo) request.getSession().getAttribute(CistaUtil.CUR_USERINFO);
    // check session
    if (null == curUserTo) {
        throw new Exception(EXCEP_MSG_SESSION);
   }
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


	 /*
     * POSITION
     */
    var position = Ext.create('Ext.data.Store', {
    fields: ['name', 'val'],
    data : [
        {"name":"Engineer", "val":"Engineer"},
        {"name":"Leader", "val":"Leader"},
        {"name":"Manager", "val":"Manager"},
		{"name":"VP", "val":"VP"}

		]
	});

	//User Information
    function formatDate(value){
        return value ? value.dateFormat('Y/m/d') : '';
    };

	//Form
	var userForm = new  Ext.form.Panel({
        id: 'userForm',
		title: 'User Information',
		labelAlign: 'left',
		frame:true,
		height:360,
		width:450,
		renderTo: "userForm",
		bodyPadding: 5,
		autoScroll:true,
		layout : {
			type : 'table',
			columns : 2
		},
		bodyStyle:'padding:5 5 5 5',//表單邊距
		defaults:{//統一設置表單字段默認屬性
			labelSeparator :'：',//分隔符
			width : 400,//字段寬度
			padding : 5,
			allowBlank : false,//是否允許為空
			labelAlign : 'left',//標籤對齊方式
			msgTarget :'side',   //在字段的右邊顯示一個提示信息
			frame:true,
			border: false
		},
		buttons :[
					{
						text : 'Submit',
						handler : submit
					},
					{
						text : 'Reset',
						handler : reset
					}
				],
		items : [
					{
						layout : 'form',
						border: false,
						items : [
									{
										xtype: 'radiogroup',
										id:'userRole',
										name: 'userRole',
										fieldLabel: 'Role',
										//arrange Radio Buttons into 2 columns
										columns: 3,
										itemId: 'userRole',
										items: [
											{
												// 1
												xtype: 'radiofield',
												boxLabel: 'Cista',
												name: 'role',
												checked: true,
												inputValue: '<%=CistaUtil.CISTA_ROLE%>'
											},
											{
												// 3
												xtype: 'radiofield',
												boxLabel: 'Customer',
												name: 'role',
												inputValue: '<%=CistaUtil.CUSTOMER_ROLE%>'
											},
											{
												// 2
												xtype: 'radiofield',
												boxLabel: 'Vendor',
												name: 'role',
												inputValue: '<%=CistaUtil.VENDOR_ROLE%>'
											}
										],            
										listeners: {
											change: function ( radio, newV, oldV, e ) {

												if( newV['role'] == "1" ){
													var customer = Ext.getCmp('customer');
													customer.allowBlank = true;
													customer.hide()
													var vendor = Ext.getCmp('vendor');
													vendor.allowBlank = true;
													vendor.hide()

												}else if (newV['role'] == "3"){
													var customer = Ext.getCmp('customer');
													customer.allowBlank = false;
													customer.blankText = 'This should not be blank!'
													customer.show()
													var vendor = Ext.getCmp('vendor');
													vendor.allowBlank = true;
													vendor.hide()
												}
												else if (newV['role'] == "2"){
													var customer = Ext.getCmp('customer');
													customer.allowBlank = true;
													customer.hide()
													var vendor = Ext.getCmp('vendor');
													vendor.allowBlank = false;
													vendor.blankText = 'This should not be blank!'
													vendor.show()
												}
											},
											beforerender:function(me,eOpts){
													var customer = Ext.getCmp('customer');
													customer.allowBlank = true;
													customer.hide()
													var vendor = Ext.getCmp('vendor');
													vendor.allowBlank = true;
													vendor.hide()
											}
										}
									},						
									{
										xtype: "textfield",
										id:'userId',
										name: 'userId',
										fieldLabel : 'User ID',
										allowBlank : false,
										blankText: 'This should not be blank!'
									},						
									{
										xtype: "textfield",
										id:'email',
										name: 'email',
										fieldLabel : 'E-mail',
										allowBlank : false,
										blankText: 'This should not be blank!',
										//驗證電子郵件格式的正則表達式
										regex : /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/,
										vtype: 'email',
										regexText:'E-mail格式錯誤'//驗證錯誤之後的提示信息,
									},						
									{
										xtype: "textfield",
										id:'realName',
										name: 'realName',
										fieldLabel : 'Real Name',
										allowBlank : false,
										blankText: 'This should not be blank!'
									},						
									{
										xtype: "textfield",
										id:'vendor',
										name: 'vendor',
										fieldLabel : 'Vendor',
										allowBlank : true
									},						
									{
										xtype: "textfield",
										id:'customer',
										name: 'customer',
										fieldLabel : 'Customer',
										allowBlank : true
									},						
									{
										xtype: "textfield",
										id:'password',
										name: 'password',
										fieldLabel : 'Password',
										allowBlank : false,
										inputType: 'password',
										blankText: 'This should not be blank!'
									},						
									{
										xtype: "textfield",
										id:'confirmPassword',
										name: 'confirmPassword',
										fieldLabel : 'Confirm Password',
										allowBlank : false,
										inputType: 'password',
										blankText: 'This should not be blank!'
									},						
									{
										xtype: "textfield",
										id:'phoneNumber',
										name: 'phoneNumber',
										fieldLabel : 'Phone Number',
										allowBlank : true
									},						
									{
										xtype: "textfield",
										id:'department',
										name: 'department',
										fieldLabel : 'Department',
										allowBlank : true
									},						
									{
										xtype: "combobox",
										id:'position',
										name: 'position',
										fieldLabel : 'Position',
										allowBlank : true,
										store: position,
										queryMode: 'local',
										displayField: 'name',
										valueField: 'val'
									},						
									{
										xtype: "checkbox",
										id:'active',
										name: 'active',
										fieldLabel : 'Active'
									}

								]
					}
				]
		
    });

	function submit(){//提交表單
		Ext.Ajax.timeout = 120000; // 120 seconds
		Ext.Ajax.request({  //ajax request test  
                    url : '<%=contextPath%>/UserSave.action',  
                     /*  headers: { 
                           'userHeader': 'userMsg' 
                       },*/  
                    /*params : {  
                        name : 'yangxuan'  
                    },*/   
                    method : 'POST',
					scope:this,
                    success : function(response, options) {  
                        Ext.MessageBox.alert('Success', 'Message : '+ response.responseText);  
                    },  
                    failure : function(response, options) {  
                        Ext.MessageBox.alert('Error', 'ERROR：' + response.status);  
                    }  
                });


	}
	function reset(){//重置表單
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
	#userForm  {position:absolute; visibility:visible; z-index:2; top:25px; left:5px; }
	

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