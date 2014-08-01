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

    function formatDate(value){
        return value ? value.dateFormat('Y/m/d') : '';
    };

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
						id:'userFormSubmit',
						text : 'Submit',
						handler : submit
					},
					{
						id:'userFormReset',
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
												// 2
												xtype: 'radiofield',
												boxLabel: 'Customer',
												name: 'role',
												inputValue: '<%=CistaUtil.CUSTOMER_ROLE%>'
											},
											{
												// 3
												xtype: 'radiofield',
												boxLabel: 'Vendor',
												name: 'role',
												inputValue: '<%=CistaUtil.VENDOR_ROLE%>'
											}
										],            
										listeners: {
											change: function ( radio, newV, oldV, e ) {

												if( newV['role'] == "1" ){
													var company = Ext.getCmp('company');
													company.allowBlank = true;
													company.hide()

												}else if (newV['role'] == "2"){
													var company = Ext.getCmp('company');
													company.allowBlank = false;
													company.blankText = 'This should not be blank!'
													company.setFieldLabel("Customer");
													company.show()

												}
												else if (newV['role'] == "3"){
													var company = Ext.getCmp('company');
													company.allowBlank = false;
													company.blankText = 'This should not be blank!'
													company.setFieldLabel("Vendor");
													company.show()
												}
											},
											beforerender:function(me,eOpts){
													var company = Ext.getCmp('company');
													company.allowBlank = true;
													company.hide()
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
										id:'company',
										name: 'company',
										fieldLabel : 'Vendor',
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
										id:'phoneNum',
										name: 'phoneNum',
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
		//Check Form Data
		//1.0 List Form Items
		var userFormItems = userForm.items;
		var i = 0;
		//1.1 Check Must have value
		for(i = 0; i < userFormItems.getAt(0).items.length; i++){


			if( userFormItems.getAt(0).items.getAt(i).xtype == "textfield" &&
				userFormItems.getAt(0).items.getAt(i).allowBlank == false && 
				( typeof(userFormItems.getAt(0).items.getAt(i).value) == 'undefined' 
					|| userFormItems.getAt(0).items.getAt(i).value == null
				    || userFormItems.getAt(0).items.getAt(i).value == "" )
			   ){

				Ext.MessageBox.alert('Message', 'Message : '+ "'" + userFormItems.getAt(0).items.getAt(i).fieldLabel + "'" + " Can't be blank" );
				return;
				//alert(userFormItems.getAt(0).items.getAt(i).fieldLabel + " " + userFormItems.getAt(0).items.getAt(i).value  + " " + userFormItems.getAt(0).items.getAt(i).xtype);
			}
			
		}
		//1.2 CHECK MAIL FORMAT
		var email = Ext.getCmp('email');
		if( !verifyAddress(email) ){
			Ext.MessageBox.alert('Message', 'Message : '+ "'" + email.fieldLabel + "'" + " Wrong e-mail format!" );
			return;
		}
		//1.3 CHECK Password
		var password = Ext.getCmp('password');
		var confirmPassword = Ext.getCmp('confirmPassword');

		if( password.value.length < 6 ){
			Ext.MessageBox.alert('Message', 'Message : '+ "'" + password.fieldLabel + "' length must >= 6 " );
			return;
		}else if (password.value != confirmPassword.value){
			Ext.MessageBox.alert('Message', 'Message : '+ "'" + password.fieldLabel + "' must be same '" + confirmPassword.fieldLabel + "'" );
			return;
		}
		
	
		//Submit & Reset Button Disable
		//1.0 List Form Button
		var userFormSubmit =Ext.getCmp('userFormSubmit'); 
		userFormSubmit.disable();

		Ext.Ajax.timeout = 120000; // 120 seconds
		Ext.Ajax.request({  //ajax request test  
                    url : '<%=contextPath%>/UserSave.action',  
                    params : {  
                        data: Ext.encode(userForm.getValues())
                    },
                    method : 'POST',
					scope:this,
                    success : function(response, options) {
						//parse Json data
						var freeback = Ext.JSON.decode(response.responseText);
						var message =  freeback.ajaxMessage;
						var status  =  freeback.ajaxStatus;
						userFormSubmit.enable();
						if( status == '<%=CistaUtil.AJAX_RSEPONSE_ERROR%>' ){//ERROR
							Ext.MessageBox.alert('Success', 'ERROR : '+ message );
						}else{//FINISH
							Ext.MessageBox.alert('Success', 'FINISH : '+ message );
							userForm.form.reset();
						}
						

                    },  
                    failure : function(response, options) {  
                        Ext.MessageBox.alert('Error', 'ERROR：' + response.status);  
                    }  
                });

	}
	function reset(){//重置表單
		userForm.form.reset();
	}

	//Verify Mail Address
	function verifyAddress(obj){
		// obtain form value into variable
		var email = obj.value;
		//alert ('email' + email);
		// define regex
		var pattern = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

		// test for pattern
		flag = pattern.test(email);

		if(flag){
			//alert("Right e-mail format!");
			return true;
		}else{
			//alert("Wrong e-mail format!");
			return false;
		}
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