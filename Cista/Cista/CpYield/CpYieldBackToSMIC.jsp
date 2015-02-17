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

<TITLE>CP Yield Back To SMIC</TITLE>
<jsp:include page="/common/normalcheck.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="<%=contextPath%>/js/extjs42/exporterExcel.js"></script>
<script type="text/javascript">
//var cistaProject;
var lot;
var sCdt;
var ftpFlag;
var sFtpSendTime;
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
            'Ext.ux.form.SearchField',
			'Ext.form.action.StandardSubmit'
         ]  
           
);


Ext.onReady(function(){
    Ext.QuickTips.init();

    function formatDate(value){
        return value ? value.dateFormat('Y/m/d') : '';
    };

	/*
	* FTP FLAG
	*/
    var ftpFlagStore = Ext.create('Ext.data.Store', {
    fields: ['name', 'val'],
    data : [
        {"name":"Not Send", "val":"N"},
        {"name":"Success", "val":"S"},
        {"name":"Fail", "val":"F"}
		]
	});

	//CP Yield Information
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
										labelWidth: 80,
										labelAlign: 'right',
										allowBlank : true,
										anchor:'90%'
									},
									{
										xtype: 'datefield',
										id: 'sCdt',
										name: 'sCdt',
										fieldLabel: '日期',
										labelWidth: 80,
										labelAlign: 'right',
										emptyText: '請選擇日期',
										format: 'Ymd',
										allowBlank : false,
										maxValue: new Date(),
										//value:Ext.Date.getFirstDateOfMonth(Ext.Date.DAY),
										value: new Date(),
										anchor:'80%'
									},
									{
										xtype: 'datefield',
										id: 'sFtpSendTime',
										name: 'sFtpSendTime',
										fieldLabel: 'FTP Send日期',
										labelWidth: 80,
										labelAlign: 'right',
										emptyText: '請選擇日期',
										format: 'Ymd',
										allowBlank : true,
										maxValue: new Date(),
										//value:Ext.Date.getFirstDateOfMonth(Ext.Date.DAY),
										//value: new Date(),
										anchor:'80%'
									},
									{
										xtype: "combobox",
										id:'ftpFlag',
										name: 'ftpFlag',
										fieldLabel : 'Ftp Flag',
										labelWidth: 80,
										labelAlign: 'right',
										allowBlank : true,
										store: ftpFlagStore,
										queryMode: 'local',
										displayField: 'name',
										valueField: 'val',
										value:'N',
										anchor:'90%'
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

		grid.getStore().removeAll();
		//Submit & Reset Button Disable
		//1.0 List Form Button
		var queryFormSubmit =Ext.getCmp('queryFormSubmit'); 
		//queryFormSubmit.disable();
		
		//Set Query Value
		lot = queryForm.getForm().findField('lot').getValue();
		sCdt = queryForm.getForm().findField('sCdt').getValue();
		ftpFlag = queryForm.getForm().findField('ftpFlag').getValue();
		sFtpSendTime = queryForm.getForm().findField('sFtpSendTime').getValue();

		//cistaProject = queryForm.getForm().findField('cistaProject').getValue();

		var query = 
		{
            query: {
                start:'0',
                limit:limit,
                sCdt:sCdt,
				ftpFlag:ftpFlag,
				sFtpSendTime:sFtpSendTime,
                lot:lot
            }
        };

		//alert("queryS " + queryS.query.start );

		Ext.Ajax.timeout = 120000; // 120 seconds
		Ext.Ajax.request({  //ajax request test  
				url : '<%=contextPath%>/QueryCpYieldBackToSMIC.action',  
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
					Ext.getCmp('btnSaveToSend').enable();
				},  
				failure : function(response, options) {  
					Ext.MessageBox.alert('Error', 'ERROR：' + response.status);  
				}  
			});

		 //queryForm.form.reset();

	}




	function reset(){//重置表單
		queryForm.form.reset();
	}


	/*************************
	* Foundry Wip Grid
	**************************/


	var isEdit = false;   
	//創建Model  
	Ext.define(  
			'CpYieldBin',  
			{  
				extend:'Ext.data.Model',  
				fields:[
						{name:'cpYieldUuid',mapping:'cpYieldUuid'},
						{name:'cpLot',mapping:'cpLot'},
						{name:'waferId',mapping:'waferId'},  
						{name:'machineId',mapping:'machineId'},  
						{name:'cpTestTimes',mapping:'cpTestTimes'},  
					    {name:'xMaxCoor',mapping:'xMaxCoor'},  
						{name:'yMaxCoor',mapping:'yMaxCoor'},
						{name:'flat',mapping:'flat'},
						{name:'passDie',mapping:'passDie'},
						{name:'failDie',mapping:'failDie'},
						{name:'totelDie',mapping:'totelDie'},
						{name:'cdt',mapping:'cdt',type:'date',dataFormat:'Y-m-d'},
						{name:'fileName',mapping:'fileName'},  
						{name:'ftpFlag',mapping:'ftpFlag'},  
						{name:'bin0',mapping:'bin0'},  
					    {name:'bin3',mapping:'bin3'},  
						{name:'bin5',mapping:'bin5'},
						{name:'bin10',mapping:'bin10'},
						{name:'bin88',mapping:'bin88'},
						{name:'bin100',mapping:'bin100'},
						{name:'bin101',mapping:'bin101'},
						{name:'bin121',mapping:'bin121'},
						{name:'bin211',mapping:'bin211'}
				]  
			}  
	)

	  
	//創建數據源  
	var store = Ext.create(  
			'Ext.data.Store',  
			{  
				model:'CpYieldBin',  
				//設置分頁大小  
				pageSize:10,
				// allow the grid to interact with the paging scroller by buffering
				//buffered: true,
				// never purge any data, we prefetch all up front
				//purgePageCount: 0,
				proxy: {  
					type: 'ajax',  
					url : '<%=contextPath%>/QueryCpYieldBackToSMIC.action',  
					reader: {  
						//數據格式為json  
						type: 'json',  
						root: 'root',  
						//獲取數據總數  
						totalProperty: 'total'  
					}  
				},
				sorters:[{property:"cpLot",direction:"ASC"}],//按qq倒序
				//autoLoad:{params:{start:0,limit:10}}//自動加載，每次加載一頁
				autoLoad:false  
			}  
	); 

	//創建grid  
	var grid = Ext.create('Ext.grid.Panel',{  
		  
			tbar:[ 
				{  
					xtype:'button',
					id:'btnSaveToSend',
					text:'Send To SMIC',
					border: 2,
					scale: 'small',
					iconCls: 'save',
					disabled : true,
					handler: function(b, e) {
						saveToSend()
						//b.up('grid').downloadExcelXml();
					}
				}
			],  
			  
			store:store,			//添加到grid  
			selModel: { selType: 'checkboxmodel' ,
						mode: "multi",//multi,simple,single；默认为多选multi
						},   //選擇框
			//表示可以選擇行  
			disableSelection: false,
			columnLines: true,   
			loadMask: true,   
			//添加修改功能  
			columns:[  
						{  
						 id:'gCpYieldUuid',
						 header:'CpYieldUuid',  
						 width:50,  
						 dataIndex:'cpYieldUuid',  
						 sortable:false,
						 hidden:true
					  
						},{  
						 id:'gCpLot',  
						 header:'Lot',  
						 width:70,  
						 dataIndex:'cpLot',  
						 sortable:false
					  
						},{  
						 id:'gWaferId',  
						 header:'Wafer Id',  
						 width:53,  
						 dataIndex:'waferId',  
						 sortable:false
					  
						},{
						 id:'gMachineId',  
						 header:'Machine Id',  
						 width:63,  
						 dataIndex:'machineId',  
						 sortable:false
					  
						},{  
						 id:'gCpTestTimes',  
						 header:'Times',  
						 width:38,  
						 dataIndex:'cpTestTimes',  
						 sortable:false
					  
						},{  
						 id:'gXMaxCoor',  
						 header:'X Max Coor',  
						 width:62,  
						 dataIndex:'xMaxCoor',  
						 sortable:false
					  
						},{  
						 id:'gYMaxCoor',  
						 header:'Y Max Coor',  
						 width:62,  
						 dataIndex:'yMaxCoor',  
						 sortable:false
					  
						},{  
						 id:'gFlat',  
						 header:'Flat',  
						 width:50,  
						 dataIndex:'flat',  
						 sortable:false
					  
						},{  
						 id:'gPassDie',  
						 header:'Pass Die',  
						 width:50,  
						 dataIndex:'passDie',  
						 sortable:false
					  
						},{  
						 id:'gFailDie',  
						 header:'Fail Die',  
						 width:45,  
						 dataIndex:'failDie',  
						 sortable:false
					  
						},{  
						 id:'gTotelDie',  
						 header:'Totel Die',  
						 width:52,  
						 dataIndex:'totelDie',  
						 sortable:false
					  
						},{  
						 id:'gBin0',  
						 header:'Bin0',  
						 width:45,  
						 dataIndex:'bin0',  
						 sortable:false
					  
						},{  
						 id:'gBin3',  
						 header:'Bin3',  
						 width:45,  
						 dataIndex:'bin3',  
						 sortable:false
					  
						},{  
						 id:'gBin5',  
						 header:'Bin5',  
						 width:45,  
						 dataIndex:'bin5',  
						 sortable:false
					  
						},{  
						 id:'gBin10',  
						 header:'Bin10',  
						 width:45,  
						 dataIndex:'bin10',  
						 sortable:false
					  
						},{  
						 id:'gBin88',  
						 header:'Bin88',  
						 width:45,  
						 dataIndex:'bin88',  
						 sortable:false
					  
						},{  
						 id:'gBin100',  
						 header:'Bin100',  
						 width:48,  
						 dataIndex:'bin100',  
						 sortable:false
					  
						},{  
						 id:'gBin101',  
						 header:'Bin101',  
						 width:48,  
						 dataIndex:'bin101',  
						 sortable:false
					  
						},{  
						 id:'gBin121',  
						 header:'Bin121',  
						 width:48,  
						 dataIndex:'bin121',  
						 sortable:false
					  
						},{  
						 id:'gBin211',  
						 header:'Bin211',  
						 width:48,  
						 dataIndex:'bin0',  
						 sortable:false
					  
						},{  
							id:'gCdt',  
							header:'Create Date',  
							width:80,  
							dataIndex:'cdt',  
							//lazyRender: true,
							hidden:true,
							renderer: function(value){   
										return value ? Ext.Date.dateFormat(value, 'Y-m-d') : '';   
									}
						},{  
						 id:'gFileName',  
						 header:'File Name',  
						 width:85,  
						 dataIndex:'fileName',  
						 sortable:false
					  
						},{  
						 id:'gFtpFlag',  
						 header:'Ftp Flag',  
						 width:50,  
						 dataIndex:'ftpFlag',  
						 sortable:false
					  
						}
			],  
			height:380,   
			width:1100,   
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


	var tempForm = new  Ext.form.Panel({
        id: 'tempForm',
		title: 'tempForm',
		items: [{
                    xtype: 'hiddenfield',
                    name: 'cistaProject'
                },{
                    xtype: 'hiddenfield',
                    name: 'lot'
                }]
		
    });



	//store.loadPage(1);
	//Grid Function
	function saveToSend(){
		//alert("ppppp");
		var record = grid.getSelectionModel().getSelection();
		if(record.length==0){  
			 Ext.MessageBox.show({   
				title:"提示",   
				msg:"請先選擇您要操作的行!"   
			 })  
			return;  
		}else{  
			var cpYieldUuid = "";   
			for(var i = 0; i < record.length; i++){   
				cpYieldUuid += record[i].get("cpYieldUuid")   
				if(i<record.length-1){   
					cpYieldUuid = cpYieldUuid + ",";   
				}   
			}  
			/*Ext.MessageBox.show({   
					title:"所選ID列表",   
					msg:cpYieldUuid   
				}  
			)*/

			var query = 
			{
				query: {
					start:'0',
					limit:limit,
					sCdt:sCdt,
					ftpFlag:ftpFlag,
					sFtpSendTime:sFtpSendTime,
					lot:lot,
					cpYieldUuid:cpYieldUuid
				}
			};

			//Grid load data Ajax
			Ext.Ajax.timeout = 120000; // 120 seconds
			Ext.Ajax.request({  //ajax request test  
					url : '<%=contextPath%>/UpdateCpYieldBackToSmicFlag.action',  
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
						//Ext.getCmp('btnSaveToSend').enable();
					},  
					failure : function(response, options) {  
						Ext.MessageBox.alert('Error', 'ERROR：' + response.status);  
					}  
				});
		} 
		//alert("record.length " + record.length );
		//alert("record.length " + record.length );
		//if(record.length==0){  
		
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
					<td class="Title">CP Yield Back To SMIC</td>
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