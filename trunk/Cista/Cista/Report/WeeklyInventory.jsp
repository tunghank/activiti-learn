<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.text.DateFormat"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.List"%>
<%@ page import ="com.cista.system.util.CistaUtil"%>
<%@ page import ="com.cista.system.to.SysUserTo"%>
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

<TITLE>Weekly Inventory Report</TITLE>
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
            'Ext.ux.form.SearchField',
			'Ext.form.Panel',
			'Ext.ux.form.MultiSelect',
			'Ext.ux.form.ItemSelector'
         ]  
           
);


Ext.onReady(function(){
    Ext.QuickTips.init();
	//alert( Ext.Date.add (Ext.Date.getFirstDateOfMonth(new Date() ), Ext.Date.DAY, -1) );
	
    function formatDate(value){
        return value ? value.dateFormat('Y/m/d') : '';
    };

	/*
	* Project
	*/
    var project = Ext.create('Ext.data.Store', {
    fields: ['name', 'val'],
    data : [
			{"name":"S0201", "val":"S0201"}
		]
	});

	//User Information
	//Form
	var queryForm = new  Ext.form.Panel({
        id: 'queryForm',
		title: 'Query Criteria',
		labelAlign: 'left',
		frame:true,
		height:150,
		width:300,
		renderTo: "queryForm",
		bodyPadding: 1,
		autoScroll:true,
		layout : {
			type : 'table',
			columns : 1
		},
		bodyStyle:'padding:1 1 1 1',//表單邊距
		defaults:{//統一設置表單字段默認屬性
			labelSeparator :'：',//分隔符
			width : 250,//字段寬度
			padding : 1,
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
										xtype: "combobox",
										id:'project',
										name: 'project',
										fieldLabel : 'Project',
										labelWidth: 50,
										labelAlign: 'right',
										allowBlank : false,
										store: project,
										queryMode: 'local',
										displayField: 'name',
										valueField: 'val',
										anchor:'80%'
									},
									{
										xtype: 'datefield',
										id: 'edate',
										name: 'edate',
										fieldLabel: '日期',
										labelWidth: 50,
										labelAlign: 'right',
										emptyText: '請選擇日期',
										format: 'Ymd',
										allowBlank : false,
										maxValue: new Date(),
										//value:Ext.Date.getFirstDateOfMonth(Ext.Date.DAY),
										value: Ext.Date.add (Ext.Date.getFirstDateOfMonth(new Date() ), Ext.Date.DAY, -1),
										anchor:'80%'
									}

								]
					}
				]
		
    });


	function submit(){//提交表單
		//Check Form Data
				
		//Submit & Reset Button Disable
		//1.0 List Form Button
		var queryFormSubmit =Ext.getCmp('queryFormSubmit'); 
		//queryFormSubmit.disable();
		
		// 初始化 Excel導出 的按鈕  
        //window.location.href = '<%=contextPath%>/WeeklyInventory.action';

		var queryFormItems = queryForm.items;
		//1.1 Check Must have value
		for(var i = 0; i < queryFormItems.getAt(0).items.length; i++){

			if( 
				queryFormItems.getAt(0).items.getAt(i).allowBlank == false && 
				( typeof(queryFormItems.getAt(0).items.getAt(i).value) == 'undefined' 
					|| queryFormItems.getAt(0).items.getAt(i).value == null
				    || queryFormItems.getAt(0).items.getAt(i).value == "" )
			   ){

				Ext.MessageBox.alert('Message', 'Message : '+ "'" + queryFormItems.getAt(0).items.getAt(i).fieldLabel + "'" + " Can't be blank" );
				return;
				//alert(userFormItems.getAt(0).items.getAt(i).fieldLabel + " " + userFormItems.getAt(0).items.getAt(i).value  + " " + userFormItems.getAt(0).items.getAt(i).xtype);
			}
			
		}

		/*var ProgressBar = new Ext.ProgressBar({
			text:'working.......',
			width:300,//設定進度條的寬度
			renderTo:'ProgressBar'
		});

		ProgressBar.wait({
			//duration:10000,//進度條持續更新10秒鐘
			interval:1000,//每1秒鐘更新一次
			//increment:10,//進度條分10次更新完畢
			text: 'Loading...',//進度條上的文字
			scope:this,//回調函數的執行範圍
			fn:function(){
				//alert('更新完畢');
			}
		});*/

		/*Ext.MessageBox.show({
		  msg: 'Processing your data, please wait...',
		  progressText: 'Saving...',
		  width:300,
		  wait:true,
		  waitConfig: {interval:200}
		});*/

		queryForm.submit({
			url: '<%=contextPath%>/WeeklyInventory.action',
			//waitMsg: 'Loading...',
			method: 'POST',
			standardSubmit: true,
			success: function (form, action) {
				Ext.MessageBox.alert('SUCCESS', 'SUCCESS' + action.response.responseText);  
			},
			failure: function(form, action) {
				if (action.result.status == true) {
					console.log('success!');
				}
			}
		});

	}

	function reset(){//重置表單
		queryForm.form.reset();
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
	#queryForm  {position:absolute; visibility:visible; z-index:3; top:20px; left:5px;}
	#ProgressBar  {position:absolute; visibility:visible; z-index:3; top:320px; left:200px;}
</style>
<link rel="stylesheet" type="text/css" href="../js/extjs42/examples/ux/css/ItemSelector.css" />
</head>


<BODY bgColor=#FFFFFF leftMargin=0 topMargin=0 >

<div id="functionTitle" >
<table border="0" cellpadding="0" align="left"  width=300 >
	<tr>
		<td class="Title">
		<div align="left">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left" class="OrangeLine">
				<tr>
					<td class="Title">Weekly Inventory Report</td>
				</tr>
			</table>
		</div>
		</td>
	</tr>
</table>
</div>
<div id="queryForm" ></div>

<div id='ProgressBar'></div>

</body>
</html>