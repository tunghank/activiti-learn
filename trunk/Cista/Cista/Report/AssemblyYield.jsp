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

<TITLE>Assembly Yield Report</TITLE>
<jsp:include page="/common/normalcheck.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">  

<script type="text/javascript">
var project;
var edate;
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

	//創建Model  
	Ext.define(  
			'project',  
			{  
				extend:'Ext.data.Model',  
				fields:[  
						{name:'name',mapping:'project'},
						{name:'val',mapping:'project'}
				]  
			}  
	)

	  
	//創建數據源  
	var project = Ext.create(  
			'Ext.data.Store',  
			{  
				model:'project',  
				proxy: {  
					type: 'ajax',  
					url : '<%=contextPath%>/GetProjects.action',  
					reader: {  
						//數據格式為json  
						type: 'json',  
						root: 'root',
						//獲取數據總數  
						totalProperty: 'total'
					}  
				},
				sorters:[{property:"project",direction:"ASC"}],//按qq倒序
				autoLoad:false  
			}  
	); 

	project.load();


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
										maxValue: Ext.Date.add (Ext.Date.getFirstDateOfMonth(new Date() ), Ext.Date.DAY, -1),
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
		project = queryForm.getForm().findField('project').getValue();
		edate = queryForm.getForm().findField('edate').getValue();

		var query = 
		{
            query: {
                project:project,
                edate:edate
            }
        };

		Ext.Ajax.timeout = 120000; // 120 seconds
		Ext.Ajax.request({  //ajax request test  
				url : '<%=contextPath%>/AssemblyYieldReport.action',  
				params : {
					query:Ext.JSON.encode(query)
				},
				method : 'POST',
				scope:this,
				success : function(response, options) {
					//parse Json data
					var freeback = Ext.JSON.decode(response.responseText);
					
					//Load Data in store
					grid.getStore().removeAll();
					grid.getStore().loadData(freeback['root'], true);
					//alert(freeback['total']);
					//alert(grid.getStore().count());
			
					grid.getStore().totalCount = freeback['total'];
					grid.getStore().currentPage = 1;
					grid.getDockedComponent("botPagingtoolbar").onLoad();
					Ext.getCmp('btnSaveToExcel').enable();
				},  
				failure : function(response, options) {  
					Ext.MessageBox.alert('Error', 'ERROR：' + response.status);  
				}  
			});

		 queryForm.form.reset();

	}

	function reset(){//重置表單
		queryForm.form.reset();
	}


	/*************************
	* Assembly Yield Grid
	**************************/


	var isEdit = false;   
	//創建Model  
	Ext.define(  
			'AssemblyYield',  
			{  
				extend:'Ext.data.Model',  
				fields:[  
						{name:'product',mapping:'product'},
						{name:'grossDie',mapping:'grossDie'},  
						{name:'issueQty',mapping:'issueQty'},  
						{name:'unit',mapping:'unit'},  
					    {name:'issueDieQty',mapping:'issueDieQty'},  
						{name:'receiveDieQty',mapping:'receiveDieQty'},
						{name:'strAssemblyYield',mapping:'strAssemblyYield'}
				]  
			}  
	)

	  
	//創建數據源  
	var store = Ext.create(  
			'Ext.data.Store',  
			{  
				model:'AssemblyYield',  
				//設置分頁大小  
				pageSize:10,
				// allow the grid to interact with the paging scroller by buffering
				//buffered: true,
				// never purge any data, we prefetch all up front
				//purgePageCount: 0,
				proxy: {  
					type: 'ajax',  
					url : '<%=contextPath%>/AssemblyYieldReport.action',  
					reader: {  
						//數據格式為json  
						type: 'json',  
						root: 'root',  
						//獲取數據總數  
						totalProperty: 'total'  
					}  
				},
				sorters:[{property:"product",direction:"ASC"}],//按qq倒序
				//autoLoad:{params:{start:0,limit:10}}//自動加載，每次加載一頁
				autoLoad:false  
			}  
	); 

	//創建grid  
	var grid = Ext.create('Ext.grid.Panel',{  
		  
			tbar:[ 
				{  
					xtype:'button',
					id:'btnSaveToExcel',
					text:'Save To Excel',
					border: 2,
					scale: 'small',
					iconCls: 'save',
					disabled : true,
					handler: function(b, e) {
						downloadExcel()
						//b.up('grid').downloadExcelXml();
					}
				}
			],  
			  
			store:store,  
			columnLines: true,   
			loadMask: true,   
			//添加修改功能  
			columns:[  
						{  
						 id:'gProduct',  
						 header:'Product',  
						 width:60,  
						 dataIndex:'product',  
						 sortable:false
					  
						},{  
						 id:'gGrossDie',  
						 header:'Gross Die',  
						 width:60,  
						 dataIndex:'grossDie',  
						 sortable:false
					  
						},{  
						 id:'gIssueQty',  
						 header:'PO Issue Qty',  
						 width:80,  
						 dataIndex:'issueQty',  
						 sortable:false
					  
						},{
						 id:'gUnit',  
						 header:'Unit',  
						 width:50,  
						 dataIndex:'unit',  
						 sortable:false
					  
						},{  
						 id:'gIssueDieQty',  
						 header:'Issue Die Qty',  
						 width:100,  
						 dataIndex:'issueDieQty',  
						 sortable:false
					  
						},{  
						 id:'gReceiveDieQty',  
						 header:'Receive Die Qty',  
						 width:100,  
						 dataIndex:'receiveDieQty',  
						 sortable:false
					  
						},{  
						 id:'gStrAssemblyYield',  
						 header:'Assembly Yield',  
						 width:100,  
						 dataIndex:'strAssemblyYield',  
						 sortable:false
					  
						}
			],  
			height:350,   
			width:600,   
			title: 'Assembly Yield',   
			renderTo: 'rptGrid',
	
			dockedItems:[  
						{   
							 xtype: 'pagingtoolbar',
							 dock: 'bottom',
							 itemId: 'botPagingtoolbar',
							 pageSize: 10,
							 store: store,   
							 displayInfo: true,   
							 displayMsg: '顯示 {0} - {1} 條，共計 {2} 條',   
							 emptyMsg: 'No Data',
							 listeners: {

							 }
						}
			]  
			  
		}  
	)  


	var tempForm = new  Ext.form.Panel({
        id: 'tempForm',
		title: 'tempForm',
		items: [{
                    xtype: 'hiddenfield',
                    name: 'project'
                },{
                    xtype: 'hiddenfield',
                    name: 'edate'
                }]
		
    });



	//store.loadPage(1);
	//Grid Function
	function downloadExcel(){


		var title = "AssemblyYield";
		var fileName;
		fileName = title + "-" + Ext.Date.format(new Date(), 'Y-m-d Hi') + '.xls',
		
		tempForm.getForm().findField('project').setValue(project);
		tempForm.getForm().findField('edate').setValue(Ext.Date.format(edate, 'Y-m-d'));

		tempForm.submit({
			url: '<%=contextPath%>/AssemblyYieldExcel.action?filename=' + escape(fileName),
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

	}//End downloadExcel()

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
	#rptGrid  {position:absolute; visibility:visible; z-index:3; top:233px; left:8px;}
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
					<td class="Title">Assembly Yield  Report</td>
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