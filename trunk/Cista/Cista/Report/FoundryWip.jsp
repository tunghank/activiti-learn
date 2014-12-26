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
var cistaProject;
var lot;
var limit=10;
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
		height:200,
		width:300,
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
			width : 280,//字段寬度
			padding : 3,
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
										xtype: "textfield",
										id:'lot',
										name: 'lot',
										fieldLabel : 'Lot',
										allowBlank : true,
										anchor:'90%'
									},				
									{
										xtype: "textfield",
										id:'cistaProject',
										name: 'cistaProject',
										fieldLabel : 'Cista Project',
										allowBlank : true,
										anchor:'90%'
									}

								]
					}
				]
		
    });

	function submit(){//提交表單

		
		grid.getStore().removeAll();
		//Submit & Reset Button Disable
		//1.0 List Form Button
		var queryFormSubmit =Ext.getCmp('queryFormSubmit'); 
		//queryFormSubmit.disable();
		
		lot = queryForm.getForm().findField('lot').getValue();
		cistaProject = queryForm.getForm().findField('cistaProject').getValue();

		var query = 
		{
            query: {
                start:'0',
                limit:limit,
                cistaProject:cistaProject,
                lot:lot
            }
        };

		//alert("queryS " + queryS.query.start );

		Ext.Ajax.timeout = 120000; // 120 seconds
		Ext.Ajax.request({  //ajax request test  
				url : '<%=contextPath%>/QueryFoundryWip.action',  
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
	* User Information Grid
	**************************/


	var isEdit = false;   
	//創建Model  
	Ext.define(  
			'FoundryWip',  
			{  
				extend:'Ext.data.Model',  
				fields:[  
						{name:'vendorCode',mapping:'vendorCode'},
						{name:'vendor',mapping:'vendor'},  
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
				model:'FoundryWip',  
				//設置分頁大小  
				pageSize:10,
				// allow the grid to interact with the paging scroller by buffering
				//buffered: true,
				// never purge any data, we prefetch all up front
				//purgePageCount: 0,
				proxy: {  
					type: 'ajax',  
					url : '<%=contextPath%>/QueryFoundryWip.action',  
					reader: {  
						//數據格式為json  
						type: 'json',  
						root: 'root',  
						//獲取數據總數  
						totalProperty: 'total'  
					}  
				},
				sorters:[{property:"cistaProject",direction:"ASC"}],//按qq倒序
				//autoLoad:{params:{start:0,limit:10}}//自動加載，每次加載一頁
				autoLoad:false  
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
						 id:'gVendor',  
						 header:'Vendor',  
						 width:50,  
						 dataIndex:'vendor',  
						 sortable:false
					  
						},{  
						 id:'gVendorSiteNum',  
						 header:'Site',  
						 width:60,  
						 dataIndex:'vendorSiteNum',  
						 sortable:false
					  
						},{  
						 id:'gProcess',  
						 header:'Process',  
						 width:50,  
						 dataIndex:'process',  
						 sortable:false
					  
						},{
						 id:'gCistaPo',  
						 header:'PO',  
						 width:130,  
						 dataIndex:'cistaPo',  
						 sortable:false
					  
						},{  
						 id:'gVendorProd',  
						 header:'Vendor Product',  
						 width:90,  
						 dataIndex:'vendorProd',  
						 sortable:false
					  
						},{  
						 id:'gCistaProject',  
						 header:'Cista Project',  
						 width:80,  
						 dataIndex:'cistaProject',  
						 sortable:false
					  
						},{  
						 id:'gWaferLotId',  
						 header:'Lot',  
						 width:80,  
						 dataIndex:'waferLotId',  
						 sortable:false
					  
						},{  
						 id:'gWaferQty',  
						 header:'Qty',  
						 width:40,  
						 dataIndex:'waferQty',  
						 sortable:false
					  
						},{  
						 id:'gLotType',  
						 header:'Lot Type',  
						 width:60,  
						 dataIndex:'lotType',  
						 sortable:false
					  
						},{  
						 id:'gTotalLayer',  
						 header:'Total Layer',  
						 width:70,  
						 dataIndex:'totalLayer',  
						 sortable:false
					  
						},{  
						 id:'gRemainLayer',  
						 header:'Remain Layer',  
						 width:80,  
						 dataIndex:'remainLayer',  
						 sortable:false
					  
						},{  
						 id:'gLotStatus',  
						 header:'Lot Status',  
						 width:65,  
						 dataIndex:'lotStatus',  
						 sortable:false
					  
						},{  
						 id:'gCurrStage',  
						 header:'Curr Stage',  
						 width:80,  
						 dataIndex:'currStage',  
						 sortable:false
					  
						},{  
						 id:'gCurrHoldDay',  
						 header:'Hold Days',  
						 width:60,  
						 dataIndex:'currHoldDay',  
						 sortable:false
					  
						},{  
						 id:'gCurrHoldDay',  
						 header:'Hold Days',  
						 width:60,  
						 dataIndex:'currHoldDay',  
						 sortable:false
					  
						},{  
							id:'gWaferStart',  
							header:'Wafer Start',  
							width:80,  
							dataIndex:'waferStart',  
							//lazyRender: true,					  
							renderer: function(value){   
										return value ? Ext.Date.dateFormat(value, 'Y-m-d') : '';   
									}
						},{  
							id:'gStgInDate',  
							header:'Stg In Date',  
							width:80,  
							dataIndex:'stgInDate',  
							//lazyRender: true,					  
							renderer: function(value){   
										return value ? Ext.Date.dateFormat(value, 'Y-m-d') : '';   
									}
						},{  
							id:'gSod',  
							header:'Sod',  
							width:80,  
							dataIndex:'sod',  
							//lazyRender: true,					  
							renderer: function(value){   
										return value ? Ext.Date.dateFormat(value, 'Y-m-d') : '';   
									}
						},{  
							id:'gRsod',  
							header:'Rsod',  
							width:80,  
							dataIndex:'rsod',  
							//lazyRender: true,					  
							renderer: function(value){   
										return value ? Ext.Date.dateFormat(value, 'Y-m-d') : '';   
									}
						},{  
							id:'gRptDate',  
							header:'Report Date',  
							width:120,  
							dataIndex:'rptDate',  
							//lazyRender: true,					  
							renderer: function(value){   
										return value ? Ext.Date.dateFormat(value, 'Y-m-d H:00:00') : '';   
									}
						}
			],  
			height:380,   
			width:1000,   
			title: 'Foundry Wip',   
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
							 moveFirst : function () {
								var me = this,
									total = me.getPageData().pageCount,
									next = 1;

									var query = 
									{
										query: {
											start:0,
											limit:limit,
											cistaProject:cistaProject,
											lot:lot
										}
									};
									me.store.getProxy().extraParams.query = Ext.JSON.encode(query);

									
								if (next <= total) {
									if (me.fireEvent('beforechange', me, next) !== false) {
										me.store.currentPage = next;
										me.store.load();
									}
								}

							 },
							 moveNext : function () {
								var me = this,
									total = me.getPageData().pageCount,
									next = me.store.currentPage + 1;

									var query = 
									{
										query: {
											start:( me.store.currentPage * 10 ),
											limit:limit,
											cistaProject:cistaProject,
											lot:lot
										}
									};
									me.store.getProxy().extraParams.query = Ext.JSON.encode(query);

									
								if (next <= total) {
									if (me.fireEvent('beforechange', me, next) !== false) {
										me.store.nextPage();
									}
								}

							 },
							 movePrevious : function () {
								var me = this,
									total = me.getPageData().pageCount,
									next = me.store.currentPage - 1;

									var query = 
									{
										query: {
											start:( (next - 1) * 10 ),
											limit:limit,
											cistaProject:cistaProject,
											lot:lot
										}
									};
									me.store.getProxy().extraParams.query = Ext.JSON.encode(query);

									
								if (next >= 0) {
									if (me.fireEvent('beforechange', me, next) !== false) {
										me.store.previousPage();
									}
								}

							 },
							 moveLast : function () {
								var me = this,
									total = me.getPageData().pageCount,
									next = total;
									var query = 
									{
										query: {
											start:( (total - 1) * 10 ),
											limit:limit,
											cistaProject:cistaProject,
											lot:lot
										}
									};
									me.store.getProxy().extraParams.query = Ext.JSON.encode(query);

									
								if (next >= 0) {
									if (me.fireEvent('beforechange', me, next) !== false) {
										me.store.currentPage = next;
										me.store.load();
									}
								}

							 },
							 doRefresh : function () {
								var me = this,
									total = me.getPageData().pageCount,
									next = 1;
									var query = 
									{
										query: {
											start:0,
											limit:limit,
											cistaProject:cistaProject,
											lot:lot
										}
									};
									me.store.getProxy().extraParams.query = Ext.JSON.encode(query);

									
								if (next >= 0) {
									if (me.fireEvent('beforechange', me, next) !== false) {
										me.store.currentPage = next;
										me.store.load();
									}
								}

							 },
							//Hank Modify
							onPagingKeyDown : function(j, h) {
								var d = this, b = h.getKey(), c = d.getPageData(), a = h.shiftKey
										? 10
										: 1, g;
								if (b == h.RETURN) {
									h.stopEvent();
									g = d.readPageFromInput(c);
									if (g !== false) {
										g = Math.min(Math.max(1, g), c.pageCount);
										if (d.fireEvent("beforechange", d, g) !== false) {
									
										//Hank Modify Start

										var total = d.getPageData().pageCount;
										var query = 
										{
											query: {
												start:( (g - 1) * 10 ),
												limit:limit,
												cistaProject:cistaProject,
												lot:lot
											}
										};
										d.store.getProxy().extraParams.query = Ext.JSON.encode(query);
										d.store.currentPage = g;
										d.store.load();

										//Hank Modify End
										//d.store.loadPage(g)

										}
									}
								} else {
									if (b == h.HOME || b == h.END) {
										h.stopEvent();
										g = b == h.HOME ? 1 : c.pageCount;
										j.setValue(g)
									} else {
										if (b == h.UP || b == h.PAGE_UP || b == h.DOWN
												|| b == h.PAGE_DOWN) {
											h.stopEvent();
											g = d.readPageFromInput(c);
											if (g) {
												if (b == h.DOWN || b == h.PAGE_DOWN) {
													a *= -1
												}
												g += a;
												if (g >= 1 && g <= c.pageCount) {
													j.setValue(g)
												}
											}
										}
									}
								}
							},
							 listeners: {

							 }
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
	#queryForm  {position:absolute; visibility:visible; z-index:2; top:20px; left:5px; }
	#rptGrid  {position:absolute; visibility:visible; z-index:3; top:253px; left:5px;}

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