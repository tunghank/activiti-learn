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

<TITLE>Foundry Wip Report</TITLE>
<jsp:include page="/common/normalcheck.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">  

<script type="text/javascript">

//下面兩行代碼必須要，不然會報404錯誤  
Ext.Loader.setConfig({enabled:true});  
//我的searchGrid和ext4在同一目錄下，所以引用時要到根目錄去"../"  
Ext.Loader.setPath('Ext.ux','<%=contextPath%>/js/extjs42/examples/ux');  
//預加載  
Ext.require(  
        [  
            'Ext.grid.*',  
            'Ext.toolbar.Paging',  
            'Ext.util.*',  
            'Ext.data.*',  
            //注意引用  
            'Ext.ux.form.SearchField'  
         ]  
           
);

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
	var queryForm = new  Ext.form.Panel({
        id: 'queryForm',
		title: 'Query Criteria',
		labelAlign: 'left',
		frame:true,
		height:380,
		width:400,
		renderTo: "queryForm",
		bodyPadding: 5,
		autoScroll:true,
		layout : {
			type : 'table',
			columns : 2
		},
		bodyStyle:'padding:5 5 5 5',//表單邊距
		defaults:{//統一設置表單字段默認屬性
			labelSeparator :'：',//分隔符
			width : 380,//字段寬度
			padding : 5,
			allowBlank : false,//是否允許為空
			labelAlign : 'left',//標籤對齊方式
			msgTarget :'side',   //在字段的右邊顯示一個提示信息
			frame:true,
			border: false
		},
		buttons :[
					{
						id:'queryFormSubmit',
						text : 'Submit',
						handler : submit
					},
					{
						id:'queryFormReset',
						text : 'Reset',
						handler : reset
					}
				],
		items : [
					{
						layout : 'anchor',
						border: false,
						items : [
									{
										xtype: 'radiogroup',
										id:'companyTypeGrp',
										name: 'companyTypeGrp',
										fieldLabel: 'Role',
										//arrange Radio Buttons into 2 columns
										columns: 3,
										itemId: 'userRole',
										items: [
											{
												// 1
												xtype: 'radiofield',
												boxLabel: 'Cista',
												name: 'companyType',
												checked: true,
												inputValue: '<%=CistaUtil.CISTA_ROLE%>'
											},
											{
												// 2
												xtype: 'radiofield',
												boxLabel: 'Customer',
												name: 'companyType',
												inputValue: '<%=CistaUtil.CUSTOMER_ROLE%>'
											},
											{
												// 3
												xtype: 'radiofield',
												boxLabel: 'Vendor',
												name: 'companyType',
												inputValue: '<%=CistaUtil.VENDOR_ROLE%>'
											}
										],            
										listeners: {
											change: function ( radio, newV, oldV, e ) {

												if( newV['companyType'] == "1" ){
													var company = Ext.getCmp('company');
													company.allowBlank = true;
													company.hide()

												}else if (newV['companyType'] == "2"){
													var company = Ext.getCmp('company');
													company.allowBlank = false;
													company.blankText = 'This should not be blank!'
													company.setFieldLabel("Customer");
													company.show()

												}
												else if (newV['companyType'] == "3"){
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
										blankText: 'This should not be blank!',
										anchor:'100%'
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
										regexText:'E-mail格式錯誤',//驗證錯誤之後的提示信息,
										anchor:'100%'
									},						
									{
										xtype: "textfield",
										id:'realName',
										name: 'realName',
										fieldLabel : 'Real Name',
										allowBlank : false,
										blankText: 'This should not be blank!',
										anchor:'80%'
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
										valueField: 'val',
										anchor:'60%'
									},						
									{
										xtype: "checkbox",
										id:'active',
										name: 'active',
										fieldLabel : 'Active'
									},						
									{
										xtype: "textfield",
										id:'editStatus',
										name: 'editStatus',
										fieldLabel : 'editStatus',
										allowBlank : true,
										value :0,
										hidden:true
									}

								]
					}
				]
		
    });

	function submit(){//提交表單
		//Check Form Data
		//1.0 List Form Items
		var queryFormItems = queryForm.items;
		var i = 0;
		//1.1 Check Must have value
		for(i = 0; i < queryFormItems.getAt(0).items.length; i++){


			if( queryFormItems.getAt(0).items.getAt(i).xtype == "textfield" &&
				queryFormItems.getAt(0).items.getAt(i).allowBlank == false && 
				( typeof(queryFormItems.getAt(0).items.getAt(i).value) == 'undefined' 
					|| queryFormItems.getAt(0).items.getAt(i).value == null
				    || queryFormItems.getAt(0).items.getAt(i).value == "" )
			   ){

				Ext.MessageBox.alert('Message', 'Message : '+ "'" + queryFormItems.getAt(0).items.getAt(i).fieldLabel + "'" + " Can't be blank" );
				return;
				//alert(queryFormItems.getAt(0).items.getAt(i).fieldLabel + " " + queryFormItems.getAt(0).items.getAt(i).value  + " " + queryFormItems.getAt(0).items.getAt(i).xtype);
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
		var queryFormSubmit =Ext.getCmp('queryFormSubmit'); 
		queryFormSubmit.disable();
		

		Ext.Ajax.timeout = 120000; // 120 seconds
		Ext.Ajax.request({  //ajax request test  
                    url : '<%=contextPath%>/UserSave.action',  
                    params : {  
                        data: Ext.encode(queryForm.getValues())
                    },
                    method : 'POST',
					scope:this,
                    success : function(response, options) {
						//parse Json data
						var freeback = Ext.JSON.decode(response.responseText);
						var message =  freeback.ajaxMessage;
						var status  =  freeback.ajaxStatus;
						queryFormSubmit.enable();
						if( status == '<%=CistaUtil.AJAX_RSEPONSE_ERROR%>' ){//ERROR
							Ext.MessageBox.alert('Success', 'ERROR : '+ message );
						}else{//FINISH
							Ext.MessageBox.alert('Success', 'FINISH : '+ message );
							
							//alert(queryForm.getForm().findField('userId').getValue());
							//Grid load data Ajax
							Ext.Ajax.request({  //ajax request test  
									url : '<%=contextPath%>/AjaxUserSearch.action',  
									params : {  
										query: queryForm.getForm().findField('userId').getValue(),
										start:'0',
										limit:'10'
									},
									method : 'POST',
									scope:this,
									success : function(response, options) {
										//parse Json data
										var freeback = Ext.JSON.decode(response.responseText);
										
										//Load Data in store
										grid.getStore().removeAll();
										grid.getStore().loadData(freeback['root']);
						

									},  
									failure : function(response, options) {  
										Ext.MessageBox.alert('Error', 'ERROR：' + response.status);  
									}  
								});
							
							 queryForm.form.reset();
							//Set Edit Status
							//queryForm.getForm().findField('editStatus').setValue('0');
							//User ID 設定為唯讀
							queryForm.getForm().findField('userId').setReadOnly (false); 
							//queryForm.getForm().findField('userId').setFieldStyle('color:#000000;background:#FFFFFF;');
							queryForm.getForm().findField('userId').removeCls('x-item-disabled');
						}
						

                    },  
                    failure : function(response, options) {  
                        Ext.MessageBox.alert('Error', 'ERROR：' + response.status);  
                    }  
                });

	}
	function reset(){//重置表單
		queryForm.form.reset();
	}


	/*************************
	* User Information Grid
	**************************/


	var isEdit = false;   
	//創建Model  
	Ext.define(  
			'User',  
			{  
				extend:'Ext.data.Model',  
				fields:[  
						{name:'vendorCode',mapping:'vendorCode'},  
						{name:'vendorSiteNum',mapping:'vendorSiteNum'},  
						{name:'process',mapping:'process'},  
					    {name:'cistaPo',mapping:'cistaPo'},  
						{name:'vendorProd',mapping:'vendorProd'},
						{name:'cistaPartNum',mapping:'cistaPartNum'},
						{name:'cistaProject',mapping:'cistaProject'},
						{name:'waferLotId',mapping:'waferLotId'},
						{name:'vendorLotId',mapping:'vendorLotId'},

						{name:'waferQty',mapping:'waferQty'},  
						{name:'lotType',mapping:'lotType'},  
						{name:'totalLayer',mapping:'totalLayer'},  
					    {name:'remainLayer',mapping:'remainLayer'},  
						{name:'lotStatus',mapping:'lotStatus'},
						{name:'currHoldDay',mapping:'currHoldDay'},
						{name:'holdCode',mapping:'holdCode'},
						{name:'holdReas',mapping:'holdReas'},
						{name:'priority',mapping:'priority'},

						{name:'waferStart',mapping:'waferStart',type:'date',dataFormat:'Y-m-d'},
						{name:'currStage',mapping:'currStage'},
					    {name:'stgInDate',mapping:'stgInDate',type:'date',dataFormat:'Y-m-d'},
						{name:'sod',mapping:'sod',type:'date',dataFormat:'Y-m-d'},
						{name:'rsod',mapping:'rsod',type:'date',dataFormat:'Y-m-d'},
						{name:'rptDate',mapping:'rptDate',type:'date',dataFormat:'Y-m-d'}
				]  
			}  
	)

	  
	//創建數據源  
	var store = Ext.create(  
			'Ext.data.Store',  
			{  
				model:'User',  
				//設置分頁大小  
				pageSize:10,  
				proxy: {  
					type: 'ajax',  
					url : '<%=contextPath%>/AjaxUserSearchLike.action',  
					reader: {  
						//數據格式為json  
						type: 'json',  
						root: 'root',  
						//獲取數據總數  
						totalProperty: 'total'  
					}  
				},
				sorters:[{property:"userId",direction:"ASC"}],//按qq倒序
				//autoLoad:{params:{start:0,limit:10}}//自動加載，每次加載一頁
				autoLoad:true  
			}  
	); 

	//創建grid  
	var grid = Ext.create('Ext.grid.Panel',{  
		  
			tbar:[ 
				{  
					xtype:'button',  
					text:'Save To Excel',
					border: 2,
					scale: 'small',
					iconCls: 'save'/*,
					handler:updateUser*/
				}
			],  
			  
			store:store,  
			columnLines: true,   
			loadMask: true,   
			//添加修改功能  
			columns:[  
					 {  
						id:'gUserId',  
						//表頭  
						header:'User ID',  
						width:100,  
						//內容  
						dataIndex:'userId',  
						sortable:true
					   
					 },{  
						 id:'gRealName',  
						 header:'Name',  
						 width:100,  
						 dataIndex:'realName',  
						 sortable:false
					  
						},{  
						 id:'gCompany',  
						 header:'Company',  
						 width:80,  
						 dataIndex:'company',  
						 sortable:false
					  
						},{
						 id:'gDepartment',  
						 header:'Department',  
						 width:80,  
						 dataIndex:'department',  
						 sortable:false
					  
						},{  
						 id:'gCompanyType',  
						 header:'Company Type',  
						 width:100,  
						 dataIndex:'companyType',  
						 sortable:false,
					     hidden:true
					  
						},{  
						 id:'gPosition',  
						 header:'Position',  
						 width:100,  
						 dataIndex:'position',  
						 sortable:false
					  
						},{  
						 id:'gEmail',  
						 header:'Email',  
						 width:200,  
						 dataIndex:'email',  
						 sortable:false
					  
						},{  
						 id:'gPhoneNum',  
						 header:'Phone',  
						 width:100,  
						 dataIndex:'phoneNum',  
						 sortable:false
					  
						},{  
						 id:'gCreateBy',  
						 header:'Creator',  
						 width:60,  
						 dataIndex:'createBy',  
						 sortable:false
					  
						},{  
							id:'gCdt',  
							header:'Create Date',  
							width:120,  
							dataIndex:'cdt',  
							//lazyRender: true,					  
							renderer: function(value){   
										return value ? Ext.Date.dateFormat(value, 'Y-m-d H:m:s') : '';   
									}
						},{  
						 id:'gUpdateBy',  
						 header:'Update By',  
						 width:60,  
						 dataIndex:'updateBy',  
						 sortable:false
					  
						},{  
							id:'gUdt',  
							header:'Update Date',  
							width:120,  
							dataIndex:'udt',  
							//lazyRender: true,  
														  
							renderer: function(value){   
										return value ? Ext.Date.dateFormat(value, 'Y-m-d H:m:s') : '';   
									}
						}  
			],  
			height:380,   
			width:700,   
			title: 'Foundry Wip',   
			renderTo: 'rptGrid',   
			 
			dockedItems:[  
						{   
							 dock: 'bottom',   
							 xtype: 'pagingtoolbar',   
							 store: store,   
							 displayInfo: true,   
							 displayMsg: '顯示 {0} - {1} 條，共計 {2} 條',   
							 emptyMsg: '沒有數據'   
						}
			]  
			  
		}  
	)  
	//store.loadPage(1);

	//Grid Function
	function updateUser(){
		//得到選中的行
		var record = grid.getSelectionModel().getSelection();
		if(record.length==0){  
			 Ext.MessageBox.show({   
				title:"提示",   
				msg:"請先選擇您要操作的行!"   
			 })  
			return;  
		}else{  
			/*var ids = "";   
			for(var i = 0; i < record.length; i++){   
				ids += record[i].get("userId")   
				if(i<record.length-1){   
					ids = ids + ",";   
				}   
			}  
			Ext.MessageBox.show({   
					title:"所選ID列表",   
					msg:ids   
				}  
			)*/
		} 



		queryForm.loadRecord(record[0]); 		
		//Set Edit Status
		queryForm.getForm().findField('editStatus').setValue('1');
		//User ID 設定為唯讀
		queryForm.getForm().findField('userId').setReadOnly (true); 
		//queryForm.getForm().findField('userId').setFieldStyle('color:#0000CC;background:#E1E1E1;');
		queryForm.getForm().findField('userId').addCls('x-item-disabled');
 
	}//End updateUser()


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
	#queryForm  {position:absolute; visibility:visible; z-index:2; top:25px; left:5px; }
	#rptGrid  {position:absolute; visibility:visible; z-index:3; top:53px; left:420px;}

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
					<td class="Title">Foundry Wip Report</td>
				</tr>
			</table>
		</div>
		</td>
	</tr>
</table>
</div>
<div id="queryForm" ></div>
<div id="rptGrid" ></div>
	
</body>
</html>