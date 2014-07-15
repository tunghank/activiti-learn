<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.text.DecimalFormat"%>

<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ page import ="com.clt.system.to.SysUserTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogPaperVerTo"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	long t1 = System.currentTimeMillis();
	String contextPath = (String)request.getContextPath();
	// Get Current User
	SysUserTo curUser =
            (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
	
	String orgSiteId = CLTUtil.getMessage("System.ERP.Org");
	String orgOuId = CLTUtil.getMessage("System.ERP.OU");

	List<QuoCatalogPaperVerTo> catalogPaperVerList = (List<QuoCatalogPaperVerTo>)request.getAttribute("catalogPaperVerList");
	catalogPaperVerList = null != catalogPaperVerList ? catalogPaperVerList : new ArrayList<QuoCatalogPaperVerTo>();
	String comboList="";
	//準備 Select List Value
	for(int i=0;i < catalogPaperVerList.size(); i ++){
		QuoCatalogPaperVerTo catalogPaperVerTo = catalogPaperVerList.get(i);
		
		String catalog = catalogPaperVerTo.getCatalog();
		String paper = catalogPaperVerTo.getPaper();
		String paperVer = catalogPaperVerTo.getPaperVer();
		String paperUid	= catalogPaperVerTo.getPaperUid();
		String paperVerUid = catalogPaperVerTo.getPaperVerUid();
		String verStartDt	= catalogPaperVerTo.getVerStartDt();
		String verEndDt	= catalogPaperVerTo.getVerEndDt();
			
		//["格式A","格式A"]
		
		if ( i != catalogPaperVerList.size() - 1 ){
			comboList = comboList + "['" + paperVerUid +"','" + catalog + "-" +  paper + " Ver " + paperVer + "'],";
		}else{
			comboList = comboList + "['" + paperVerUid +"','" + catalog + "-" +  paper + " Ver " + paperVer + "']";
		}
			
	}
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
    Ext.QuickTips.init();
	//詢價 內容 Grid
    function formatDate(value){
        return value ? value.dateFormat('Y/m/d') : '';
    };

	function columnColor(data, cell, record, rowIndex, columnIndex, store){ 
       cell.attr = "style=background-color:#CCFFFF"
       return data;
	}

	function columnColor1(data, cell, record, rowIndex, columnIndex, store){ 
       cell.attr = "style=background-color:#F6C400"
       return data;
	}

	function columnColor2(data, cell, record, rowIndex, columnIndex, store){ 
       cell.attr = "style=background-color:#FF99FF"
       return data;
	}

	    // the column model has information about grid columns
    // dataIndex maps the column to the specific data field in
    // the data store (created below)
	
	//checkbox選擇模型
	var sm = new Ext.grid.CheckboxSelectionModel({ checkOnly: true });
	// var sm:new Ext.grid.RowSelectionModel({singleSelection:true}) //選擇模型改為了行選擇模型
	//var sm = new Ext.grid.CheckboxSelectionModel();
	
    sm.handleMouseDown = Ext.emptyFn;//不響應MouseDown事件
    sm.on('rowselect',function(sm_,rowIndex,record){//行選中的時候
       
    }, this);
	
	sm.on('rowdeselect',function(sm_,rowIndex,record){//行未選中的時候
       
    }, this); 

    var cm = new Ext.grid.ColumnModel([
		{
           id:'inquiryNum',
           header: "詢價單號",
		   tooltip:'詢價單號',
           dataIndex: 'inquiryNum',
           width: 100
        },{
           id:'inquiryModel',
           header: "機種料號",
		   tooltip:'機種料號',
           dataIndex: 'inquiryModel',
           width: 120
        },{
           id:'inquiryPartNum',
           header: "料號",
		   tooltip:'料號',
           dataIndex: 'inquiryPartNum',
           width: 120
        },{
           id:'inquiryPartNumDesc',
           header: "料號摘要",
		   tooltip:'料號摘要',
           dataIndex: 'inquiryPartNumDesc',
           width: 300
        },{
           id:'cltInquiryUser',
           header: "Sourcer",
		   tooltip:'Sourcer',
           dataIndex: 'cltInquiryUser',
           width: 100
        },{
		   id:'inquiryCdt',
		   header: "詢價單發出時間",
		   tooltip:'詢價單發出時間',
		   dataIndex: 'inquiryCdt',
		   width: 100
		},{
           id:'quotationRecoverTime',
           header: "報價單回收時間",
		   tooltip:'報價單回收時間',
           dataIndex: 'quotationRecoverTime',
           width: 100
        },{
           id:'inquiryStatus',
           header: "詢價狀態",
		   tooltip:'詢價狀態',
           dataIndex: 'inquiryStatus',
		   hidden:true,
           width:80
        },{
           id:'inquiryStatusDesc',
           header: "詢價狀態",
		   tooltip:'詢價狀態',
           dataIndex: 'inquiryStatusDesc',
           width:80
        },{
           id:'compareStatus',
           header: "比價狀態",
		   tooltip:'比價狀態',
           dataIndex: 'compareStatus',
		   hidden:true,
           width:80
        },{
           id:'compareStatusDesc',
           header: "比價狀態",
		   tooltip:'比價狀態',
           dataIndex: 'compareStatusDesc',
           width:80
        },{
           id:'inquiryHeaderUid',
           header: "inquiryHeaderUid",
		   tooltip:'inquiryHeaderUid',
           dataIndex: 'inquiryHeaderUid',
		   hidden:true,
           width:50
        },{
           id:'inquiryPartNumUid',
           header: "inquiryPartNumUid",
		   tooltip:'inquiryPartNumUid',
           dataIndex: 'inquiryPartNumUid',
		   hidden:true,
           width:50
        },{
           id:'inquirySupplierUid',
           header: "inquirySupplierUid",
		   tooltip:'inquirySupplierUid',
           dataIndex: 'inquirySupplierUid',
		   hidden:true,
           width:50
        },{
           id:'paperVerUid',
           header: "paperVerUid",
		   tooltip:'paperVerUid',
           dataIndex: 'paperVerUid',
		   hidden:true,
           width:50
        }
    ]);

    // by default columns are sortable
    cm.defaultSortable = true;

    // this could be inline, but we want to define the Plant record
    // type so we can add records dynamically
	var data = {

	};
	// create the Data Store
    var inquiryStore = new Ext.data.GroupingStore({
		id:'inquiryStore',
		proxy:new Ext.data.MemoryProxy(data),
		reader:new Ext.data.JsonReader({}, [
		   {name: 'inquiryNum'},
		   {name: 'inquiryModel'},
		   {name: 'inquiryPartNum'},
		   {name: 'inquiryPartNumDesc'},
           {name: 'cltInquiryUser'},
		   {name: 'inquiryCdt'},
           {name: 'quotationRecoverTime'},
		   {name: 'inquiryStatus'},
		   {name: 'inquiryStatusDesc'},
		   {name: 'compareStatus'},
		   {name: 'compareStatusDesc'},
		   {name: 'inquiryHeaderUid'},
		   {name: 'inquiryPartNumUid'},
		   {name: 'inquirySupplierUid'},
		   {name: 'paperVerUid'}

		]),
		sortInfo:{ field: "inquiryNum", direction: "ASC" },
		groupField:'inquiryNum'
	}) ;


	inquiryStore.load();



	// create the readonly grid
    var inquiryGrid = new Ext.grid.GridPanel({
		id: 'inquiryGrid',
        ds: inquiryStore,
        cm: cm,
		height:180,
		sm: new Ext.grid.RowSelectionModel({
	                singleSelect: true,
	                listeners: {
	                    rowselect: function(sm, row, rec) {
							claerQueryQuoteForm();

							var quoteStore = quoteGrid.getStore();
							quoteStore.removeAll();
							quoteGrid.getView().refresh();
							//var index = inquiryGrid.getStore().indexOf(rec);
							//Get grid rec value
							var inquiryHeaderUid = rec.data['inquiryHeaderUid'];
							var inquiryPartNumUid = rec.data['inquiryPartNumUid'];
							var inquirySupplierUid = rec.data['inquirySupplierUid'];
							//alert(inquiryHeaderUid + " " + inquiryPartNumUid + " " +  inquirySupplierUid );

							Ext.lib.Ajax.request(
								'POST',
								'quotation/inquiry/AjaxQueryQuoteList.action',
								{success: function(response){
										//parse Json data
										var rs = Ext.util.JSON.decode(response.responseText);
										if(rs != ""){
											//Data Load to Grid.
											var quoteJsonData = Ext.util.JSON.decode(response.responseText);
											var quoteStore = quoteGrid.getStore();
											quoteStore.removeAll();
											quoteStore.loadData(quoteJsonData);
											quoteGrid.getView().refresh();
										}else{
											Ext.Msg.alert("Error", "No Data");
										}
								},failure: function(){
									Ext.Msg.alert("Error", "startQuotationItem Connection Error , Please Inform CLT IT");
								}},
								'inquiryHeaderUid=' + inquiryHeaderUid +
								'&inquiryPartNumUid=' + inquiryPartNumUid +
								'&inquirySupplierUid=' + inquirySupplierUid
									
							);//Ext.lib.Ajax.request(

							//claerQueryQuoteForm();
							

							Ext.getCmp('queryQuoteFormInquiryNum').setValue(rec.data['inquiryNum']);
							Ext.getCmp('queryQuoteFormInquiryModel').setValue();
							Ext.getCmp('queryQuoteFormInquiryPartNum').setValue(rec.data['inquiryPartNum']);
							Ext.getCmp('queryQuoteFormInquiryPartNumDesc').setValue(rec.data['inquiryPartNumDesc']);
							Ext.getCmp('queryQuoteFormInquiryCdt').setValue(rec.data['inquiryCdt']);
							Ext.getCmp('queryQuoteFormRecoverTime').setValue(rec.data['quotationRecoverTime']);

							Ext.getCmp('queryQuoteFormCltInquiryUser').setValue(rec.data['cltInquiryUser']);
							Ext.getCmp('queryQuoteFormInquiryStatus').setValue(rec.data['inquiryStatus']);
							Ext.getCmp('queryQuoteFormInquiryStatusDesc').setValue(rec.data['inquiryStatusDesc']);

							Ext.getCmp('queryQuoteFormCompareStatus').setValue(rec.data['compareStatus']);
							Ext.getCmp('queryQuoteFormCompareStatusDesc').setValue(rec.data['compareStatusDesc']);

							Ext.getCmp('queryQuoteFormInquiryHeaderUid').setValue(rec.data['inquiryHeaderUid']);
							Ext.getCmp('queryQuoteFormInquiryPartNumUid').setValue(rec.data['inquiryPartNumUid']);
							Ext.getCmp('queryQuoteFormInquirySupplierUid').setValue(rec.data['inquirySupplierUid']);
							//Ext.getCmp('queryQuoteFormQuoteHeaderUid').setValue(rec.data['quoteHeaderUid']);
							Ext.getCmp('queryQuoteFormPaperVerUid').setValue(rec.data['paperVerUid']);

	                    }
	                }
	            }),
		view: new Ext.grid.GroupingView({
            showGroupName: true,
            enableNoGroups:false, // REQUIRED!
            hideGroupedColumn: true
        }),
        region:'center',
        title:'查詢結果',
        frame:true,
		loadMask: true,
	    stripeRows: true,
		iconCls: 'icon-grid',
		bodyStyle:'width:100%',
		autoWidth:true,
		autoScroll:true
	}) ;




	//Form
	var queryInquiryForm = new Ext.form.FormPanel({
        id: 'queryInquiryForm',
		title: '詢價資料查詢',
		//style: 'padding: 0 0 0 0',
		//bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		labelAlign: 'left',
		height:120,
		region:'north',
        items: [{
			layout: "column",
			anchor: "0",
			frame: true,
			items: [{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '機種料號:'
				}]
			},{
				columnWidth: .12,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryInquiryFormInquiryModel',
					name: 'queryInquiryFormInquiryModel',
					readOnly:true,
					style:"background:#EEEEEE;color:#BC6618;"
				}]
			},{
				columnWidth: .07,
				height:30,
				items: [{
					xtype: "label",
					text: '報價發出日期:'
				}]
			},{
				columnWidth: .1,
				height:30,
				items: [{
					xtype: "datefield",
					id:'queryInquiryFormInquiryScdt',
					name: 'queryInquiryFormInquiryScdt',
					format:'Y/m/d',
					readOnly : true,
					anchor:'90%'
				}]
			},{
				columnWidth: .01,
				height:30,
				items: [{
					xtype: "label",
					text: '~'
				}]
			},{
				columnWidth: .1,
				height:30,
				items: [{
					xtype: "datefield",
					id:'queryInquiryFormInquiryEcdt',
					name: 'queryInquiryFormInquiryEcdt',
					format:'Y/m/d',
					readOnly : true,
					anchor:'90%'
				}]
			},{
				columnWidth: .07,
				height:30,
				items: [{
					xtype: "label",
					text: '報價回收日期:'
				}]
			},{
				columnWidth: .1,
				height:30,
				items: [{
					xtype: "datefield",
					id:'queryInquiryFormRecoverStime',
					name: 'queryInquiryFormRecoverStime',
					format:'Y/m/d',
					readOnly : true,
					anchor:'90%'
				}]
			},{
				columnWidth: .01,
				height:30,
				items: [{
					xtype: "label",
					text: '~'
				}]
			},{
				columnWidth: .1,
				height:30,
				items: [{
					xtype: "datefield",
					id:'queryInquiryFormRecoverEtime',
					name: 'queryInquiryFormRecoverEtime',
					format:'Y/m/d',
					readOnly : true,
					anchor:'90%'
				}]
			},{//空白
				columnWidth: .27,
				height:30,
				items: [{

				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '料號:'
				}]
			},{
				columnWidth: .15,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryInquiryFormInquiryPartNum',
					name: 'queryInquiryFormInquiryPartNum',
					readOnly:true,
					width: 200,
					style:"background:#EEEEEE;color:#BC6618;"
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: 'button',
					id:'queryInquiryFormBtnPartNum',
					text: '...',
					listeners:{
						"click":function(){
							document.getElementById("hiddenPartNumBtn").click();
						}
					}
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '料號摘要:'
				}]
			},{
				columnWidth: .45,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryInquiryFormInquiryPartNumDesc',
					name: 'queryInquiryFormPartInquiryNumDesc',
					readOnly:true,
					width: 550,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '詢價單號:'
				}]
			},{
				columnWidth: .12,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryInquiryFormInquiryNum',
					name: 'queryInquiryFormInquiryNum',
					style:"background:#FFFFFF;color:#BC6618;"
				}]
			},{//空白行
				columnWidth: .08,
				height:30,
				items: [{}]
			},{//第二列
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '廠商代碼:',
					anchor:'90%'
				}]
			},{
				columnWidth: .15,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryInquiryFormInquirySupplierCode',
					name: 'queryInquiryFormInquirySupplierCode',
					allowBlank:false,
					readOnly:true,
					width: 200,
					style:"background:#EEEEEE;color:#BC6618;",
					anchor:'90%'
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: 'button',
					id:'queryInquiryFormBtnSupplierCode',
					text: '...',
					listeners:{
						"click":function(){
							document.getElementById("hiddenVendorBtn").click();
						}
					}
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '廠商名稱:',
					anchor:'90%'
				}]
			},{
				columnWidth: .25,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryInquiryFormInquirySupplierName',
					name: 'queryInquiryFormInquirySupplierName',
					allowBlank:false,
					readOnly:true,
					width: 300,
					style:"background:#EEEEEE;color:#BC6618;",
					anchor:'90%'
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: 'Sourcer:'
				}]
			},{
				columnWidth: .1,
				height:30,
				items: [{
					xtype:"combo",
					id:'queryInquiryFormCltInquiryUser',
					name: 'queryInquiryFormCltInquiryUser',
					store:new Ext.data.SimpleStore({
					fields: ['value', 'text'],
						data: [
						]
					}),
					displayField: 'text',
					valueField: 'value',
					mode: 'local',
					editable: false,
					triggerAction: 'all'
				}]
			},{//空白行
				columnWidth: .15,
				height:30,
				items: [{}]
			},{
				columnWidth: .1,
				height:30,
				bodyStyle: 'padding-right:0px;padding-left:0px;padding-top:5px;padding-bottom:0px;',
				items: [{
					xtype: 'button',
					id:'queryInquiryFormQuery',
					text: 'Query',
					listeners:{
						"click":function(){
							queryInquiryList();
						}
					}
				}]
			},{
				columnWidth: .05,
				height:30,
				bodyStyle: 'padding-right:0px;padding-left:0px;padding-top:5px;padding-bottom:0px;',
				items: [{
					xtype: 'button',
					id:'queryInquiryFormReset',
					text: 'Reset',
					listeners:{
						"click":function(){
							clearAllData();
						}
					}
				}]
			}]
		}]
    });


	//Form
	var queryQuoteForm = new  Ext.form.FormPanel({
        id: 'queryQuoteForm',
		title: '詢價內容',
		//style: 'padding: 0 0 0 0',
		//bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		labelAlign: 'left',
		height:120,
		region:'south',
        items: [{
			layout: "column",
			anchor: "0",
			frame: true,
			items: [{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '詢價單號:'
				}]
			},{
				columnWidth: .12,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormInquiryNum',
					name: 'queryQuoteFormInquiryNum',
					readOnly:true,
					style:"background:#EEEEEE;color:#BC6618;"
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '機種料號:'
				}]
			},{
				columnWidth: .12,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormInquiryModel',
					name: 'queryQuoteFormInquiryModel',
					readOnly:true,
					style:"background:#EEEEEE;color:#BC6618;"
				}]
			},{
				columnWidth: .03,
				height:30,
				items: [{
					xtype: "label",
					text: '料號:'
				}]
			},{
				columnWidth: .12,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormInquiryPartNum',
					name: 'queryQuoteFormInquiryPartNum',
					readOnly:true,
					style:"background:#EEEEEE;color:#BC6618;"
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '料號摘要:'
				}]
			},{
				columnWidth: .45,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormInquiryPartNumDesc',
					name: 'queryQuoteFormPartInquiryNumDesc',
					readOnly:true,
					width: 550,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{//空白
				columnWidth: .01,
				height:30,
				items: [{

				}]
			},{//第二列
				columnWidth: .07,
				height:30,
				items: [{
					xtype: "label",
					text: '報價發出日期:'
				}]
			},{
				columnWidth: .13,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormInquiryCdt',
					name: 'queryQuoteFormInquiryCdt',
					readOnly:true,
					width: 130,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{
				columnWidth: .07,
				height:30,
				items: [{
					xtype: "label",
					text: '報價回收日期:'
				}]
			},{
				columnWidth: .12,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormRecoverTime',
					name: 'queryQuoteFormRecoverTime',
					readOnly:true,
					style:"background:#EEEEEE;color:#FF0000;"

				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: 'Sourcer:'
				}]
			},{
				columnWidth: .12,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormCltInquiryUser',
					name: 'queryQuoteFormCltInquiryUser',
					readOnly:true,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '詢價狀態:'
				}]
			},{
				columnWidth: .01,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormInquiryStatus',
					name: 'queryQuoteFormInquiryStatus',
					readOnly:true,
					hidden:true,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{
				columnWidth: .12,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormInquiryStatusDesc',
					name: 'queryQuoteFormInquiryStatusDesc',
					readOnly:true,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '比價狀態:'
				}]
			},{
				columnWidth: .01,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormCompareStatus',
					name: 'queryQuoteFormCompareStatus',
					readOnly:true,
					hidden:true,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{
				columnWidth: .11,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormCompareStatusDesc',
					name: 'queryQuoteFormCompareStatusDesc',
					readOnly:true,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{
				columnWidth: .01,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormInquiryHeaderUid',
					name: 'queryQuoteFormInquiryHeaderUid',
					readOnly:true,
					hidden:true,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{
				columnWidth: .01,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormInquiryPartNumUid',
					name: 'queryQuoteFormInquiryPartNumUid',
					readOnly:true,
					hidden:true,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{
				columnWidth: .01,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormInquirySupplierUid',
					name: 'queryQuoteFormInquirySupplierUid',
					readOnly:true,
					hidden:true,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{
				columnWidth: .01,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormQuoteHeaderUid',
					name: 'queryQuoteFormQuoteHeaderUid',
					readOnly:true,
					hidden:true,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{
				columnWidth: .01,
				height:30,
				items: [{
					xtype: "textfield",
					id:'queryQuoteFormPaperVerUid',
					name: 'queryQuoteFormPaperVerUid',
					readOnly:true,
					hidden:true,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{//空白行
				columnWidth: .03,
				height:30,
				items: [{}]
			},{//第三列
				columnWidth: .08,
				height:30,
				items: [{
					xtype: 'button',
					text: '全部結單',
					listeners:{
						"click":function(){
							//nextCompareQuotation(Ext.getCmp('partNumGrid'));
						}
					}
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: 'button',
					text: '作廢',
					listeners:{
						"click":function(){
							//nextCompareQuotation(Ext.getCmp('partNumGrid'));
						}
					}
				}]
			},{
				columnWidth: .08,
				height:30,
				items: [{
					xtype: 'button',
					text: '進行比價',
					listeners:{
						"click":function(){
							compareQuotation();
						}
					}
				}]
			},{
				columnWidth: .12,
				height:30,
				items: [{
					xtype: 'button',
					text: '編修未完成詢價單',
					listeners:{
						"click":function(){
							editInquiry();
						}
					}
				}]
			},{
				columnWidth: .08,
				height:30,
				items: [{
					xtype: 'button',
					text: '重新議價',
					listeners:{
						"click":function(){
							//nextCompareQuotation(Ext.getCmp('partNumGrid'));
						}
					}
				}]
			},{
				columnWidth: .09,
				height:30,
				items: [{
					xtype: 'button',
					text: '單價申請表',
					listeners:{
						"click":function(){
							//nextCompareQuotation(Ext.getCmp('partNumGrid'));
						}
					}
				}]
			}]
		}]
    });

	/********************************************************
	*		Quote Grid 
	********************************************************/
    var quoteCm = new Ext.grid.ColumnModel([
		{
           id:'quoteNum',
           header: "報價單號",
		   tooltip:'報價單號',
           dataIndex: 'quoteNum',
           width: 100
        },{
           id:'inquiryPartNumDiffer',
           header: "主/替料",
		   tooltip:'主/替料',
           dataIndex: 'inquiryPartNumDiffer',
           width: 50
        },{
           id:'inquiryPartNum',
           header: "料號",
		   tooltip:'料號',
           dataIndex: 'inquiryPartNum',
           width: 120
        },{
           id:'inquiryPartNumDesc',
           header: "料號摘要",
		   tooltip:'料號摘要',
           dataIndex: 'inquiryPartNumDesc',
           width: 300
        },{
           id:'inquirySupplierName',
           header: "廠商名稱",
		   tooltip:'廠商名稱',
           dataIndex: 'inquirySupplierName',
           width: 200
        },{
		   id:'inquirySupplierContact',
		   header: "聯絡人",
		   tooltip:'聯絡人',
		   dataIndex: 'inquirySupplierContact',
		   width: 100
		},{
           id:'inquirySupplierPartNum',
           header: "廠商料號",
		   tooltip:'廠商料號',
           dataIndex: 'inquirySupplierPartNum',
           width: 100
        },{
           id:'inquiryQty',
           header: "數量",
		   tooltip:'數量',
           dataIndex: 'inquiryQty',
           width: 70
        },{
           id:'inquiryUnit',
           header: "單位",
		   tooltip:'單位',
           dataIndex: 'inquiryUnit',
           width: 40
        },{
           id:'inquiryCurrency',
           header: "幣別",
		   tooltip:'幣別',
           dataIndex: 'inquiryCurrency',
           width: 40
        },{
           id:'inquiryPaymentMethod',
           header: "交易條件",
		   tooltip:'交易條件',
           dataIndex: 'inquiryPaymentMethod',
           width: 60
        },{
           id:'inquiryDeliveryLocationDesc',
           header: "送貨地點",
		   tooltip:'送貨地點',
           dataIndex: 'inquiryDeliveryLocationDesc',
           width: 100
        },{
           id:'inquiryShippedBy',
           header: "運送方式",
		   tooltip:'運送方式',
           dataIndex: 'inquiryShippedBy',
           width: 60
        },{
           id:'quotationRecoverTime',
           header: "回收時間",
		   tooltip:'回收時間',
           dataIndex: 'quotationRecoverTime',
           width: 100
        },{
           id:'quoteStatusDesc',
           header: "報價狀態",
		   tooltip:'報價狀態',
           dataIndex: 'quoteStatusDesc',
           width:80
        },{
           id:'quoteStatus',
           header: "報價狀態",
		   tooltip:'報價狀態',
           dataIndex: 'quoteStatus',
		   hidden:true,
           width:80
        },{
           id:'quoteNotes',
           header: "備註",
		   tooltip:'備註',
           dataIndex: 'quoteNotes',
           width:150
        },{
           id:'inquiryHeaderUid',
           header: "inquiryHeaderUid",
		   tooltip:'inquiryHeaderUid',
           dataIndex: 'inquiryHeaderUid',
		   hidden:true,
           width:50
        },{
           id:'inquiryPartNumUid',
           header: "inquiryPartNumUid",
		   tooltip:'inquiryPartNumUid',
           dataIndex: 'inquiryPartNumUid',
		   hidden:true,
           width:50
        },{
           id:'inquirySupplierUid',
           header: "inquirySupplierUid",
		   tooltip:'inquirySupplierUid',
           dataIndex: 'inquirySupplierUid',
		   hidden:true,
           width:50
        },{
           id:'quoteHeaderUid',
           header: "quoteHeaderUid",
		   tooltip:'quoteHeaderUid',
           dataIndex: 'quoteHeaderUid',
		   hidden:true,
           width:50
        },{
           id:'inquiryCdt',
           header: "inquiryCdt",
		   tooltip:'inquiryCdt',
           dataIndex: 'inquiryCdt',
		   hidden:true,
           width:50
        },{
           id:'inquiryNum',
           header: "inquiryNum",
		   tooltip:'inquiryNum',
           dataIndex: 'inquiryNum',
		   hidden:true,
           width:50
        },{
           id:'cltInquiryUser',
           header: "cltInquiryUser",
		   tooltip:'cltInquiryUser',
           dataIndex: 'cltInquiryUser',
		   hidden:true,
           width:50
        },{
           id:'inquiryStatus',
           header: "inquiryStatus",
		   tooltip:'inquiryStatus',
           dataIndex: 'inquiryStatus',
		   hidden:true,
           width:50
        },{
           id:'inquiryStatusDesc',
           header: "inquiryStatusDesc",
		   tooltip:'inquiryStatusDesc',
           dataIndex: 'inquiryStatusDesc',
		   hidden:true,
           width:50
        },{
           id:'paperVerUid',
           header: "paperVerUid",
		   tooltip:'paperVerUid',
           dataIndex: 'paperVerUid',
		   hidden:true,
           width:50
        }
    ]);

    // by default columns are sortable
    quoteCm.defaultSortable = true;

    // this could be inline, but we want to define the Plant record
    // type so we can add records dynamically
	var quoteData = {

	};
	// create the Data Store
    var quoteStore = new Ext.data.GroupingStore({
		id:'quoteStore',
		proxy:new Ext.data.MemoryProxy(quoteData),
		reader:new Ext.data.JsonReader({}, [
		   {name: 'quoteNum'},
		   {name: 'inquiryPartNumDiffer'},
		   {name: 'inquiryPartNum'},
		   {name: 'inquiryPartNumDesc'},
		   {name: 'inquirySupplierName'},
		   {name: 'inquirySupplierContact'},
		   {name: 'inquirySupplierPartNum'},
           {name: 'inquiryQty'},
		   {name: 'inquiryUnit'},
           {name: 'inquiryCurrency'},
           {name: 'inquiryPaymentMethod'},
		   {name: 'inquiryDeliveryLocationDesc'},
           {name: 'inquiryShippedBy'},
		   {name: 'quotationRecoverTime'},
		   {name: 'quoteStatusDesc'},
		   {name: 'quoteStatus'},
		   {name: 'quoteNotes'},
		   {name: 'inquiryHeaderUid'},
		   {name: 'inquiryPartNumUid'},
		   {name: 'inquirySupplierUid'},
		   {name: 'quoteHeaderUid'},
		   {name: 'inquiryCdt'},
		   {name: 'inquiryNum'},
		   {name: 'cltInquiryUser'},
		   {name: 'inquiryStatus'},
		   {name: 'inquiryStatusDesc'},
		   {name: 'paperVerUid'}
	
		]),
		sortInfo:{ field: "quoteNum", direction: "ASC" }
	}) ;


	quoteStore.load();

	 var quoteToolbar = new Ext.Toolbar({
		 id:'quoteToolbar',
		 items: [  
			//'-',
			 {text:'結單',
				  id:'quoteGridTbBtnClose',
				  style:"background-color:#FF9900;",
				  pressed:true,
				  //hidden:true,
				  handler: function(){
						delSelectedRow(Ext.getCmp('partNumGrid'), Ext.getCmp('partNumGrid').getStore());
				  }

				},
			'-',{text:'延長',
				  id:'quoteGridTbBtnPostpone',
				  style:"background-color:#FF9900;",
				  pressed:true,
				  handler: function(){
						modifyInquiryItem(Ext.getCmp('partNumGrid'));
				  }

				}

		 ]  
	 });


	// create the readonly grid
    var quoteGrid = new Ext.grid.GridPanel({
		id: 'quoteGrid',
        ds: quoteStore,
        cm: quoteCm,
		tbar: quoteToolbar,
		height:160,
		sm: new Ext.grid.RowSelectionModel({
	                singleSelect: true,
	                listeners: {
	                    rowselect: function(sm, row, rec) {

	                    }
	                }
	            }),
        region:'south',
        frame:true,
		loadMask: true,
	    stripeRows: true,
		iconCls: 'icon-grid',
		bodyStyle:'width:100%;',
		autoWidth:true,
		autoScroll:true
	}) ;


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



	/*********************************************************
	*					Layout
	*********************************************************/

	// Finally, build the main layout once all the pieces are ready.  This is also a good
	// example of putting together a full-screen BorderLayout within a Viewport.
    new Ext.Viewport({
		defaults: {autoScroll: true}, 
		items: [{
			layout: "column",
			anchor: "0",
			frame: true,
			items: [{
				columnWidth: 1,
				items: [queryInquiryForm]
			},{
				columnWidth: 1,
				items: [inquiryGrid]
			},{
				columnWidth: 1,
				items: [queryQuoteForm]
			},{
				columnWidth: 1,
				items: [quoteGrid]
			},{
				columnWidth: 1,
				items: [nextForm]
			}]
		}],
        renderTo: Ext.getBody(),
		autoScroll:true
    });

	function queryInquiryList(){
		var myMask = new Ext.LoadMask(Ext.getBody(), {  
			msg : "Please wait..."  
		});

		//清下面的 畫面
		claerQueryQuoteForm();
		var quoteStore = quoteGrid.getStore();
		quoteStore.removeAll();
		quoteGrid.getView().refresh();

		var inquiryStore = inquiryGrid.getStore();
		inquiryStore.removeAll();
		inquiryGrid.getView().refresh();
		
		myMask.show();
		Ext.lib.Ajax.request(
			'POST',
			'quotation/inquiry/AjaxQueryInquiryList.action',
			{success: function(response){
					myMask.hide();
					//parse Json data
					var rs = Ext.util.JSON.decode(response.responseText);
					if(rs != ""){
							//Data Load to Grid.
						var inquiryJsonData = Ext.util.JSON.decode(response.responseText);
						var inquiryStore = inquiryGrid.getStore();
						inquiryStore.removeAll();
						inquiryStore.loadData(inquiryJsonData);
						inquiryGrid.getView().refresh();
					}else{
						Ext.Msg.alert("Error", "No Data");
					}
			},failure: function(){
				myMask.hide();
				Ext.Msg.alert("Error", "startQuotationManage Connection Error , Please Inform CLT IT");
			}},
			'inquiryNum=' + Ext.getCmp("queryInquiryFormInquiryNum").getValue() +
			'&inquiryScdt=' + formatDate(Ext.getCmp("queryInquiryFormInquiryScdt").getValue()) +
			'&inquiryEcdt=' + formatDate(Ext.getCmp("queryInquiryFormInquiryEcdt").getValue()) +
			'&recoverStime=' + formatDate(Ext.getCmp("queryInquiryFormRecoverStime").getValue()) +
			'&recoverEtime=' + formatDate(Ext.getCmp("queryInquiryFormRecoverEtime").getValue()) +
			'&inquiryPartNum=' + Ext.getCmp("queryInquiryFormInquiryPartNum").getValue() +
			'&inquirySupplierCode=' + Ext.getCmp("queryInquiryFormInquirySupplierCode").getValue() +
			'&cltInquiryUser=' + Ext.getCmp("queryInquiryFormCltInquiryUser").getValue() 
				
		);
		myMask.destroy();
	}
	

	function claerQueryQuoteForm(){
		queryQuoteForm.form.reset();
	}



	function compareQuotation(){
		Ext.Msg.confirm("Waring", "確定 '進行比價?' ", function(button) {

			if (button == "yes") {
				
				 //Get Selected Row
				 var inquiryNum = Ext.getCmp('queryQuoteFormInquiryNum').getValue();
				 var paperVerUid = Ext.getCmp('queryQuoteFormPaperVerUid').getValue();
				 var inquiryStatus = Ext.getCmp('queryQuoteFormInquiryStatus').getValue();
				 var compareStatus = Ext.getCmp('queryQuoteFormCompareStatus').getValue();
				 if (inquiryNum=="") {  
					Ext.Msg.alert("提示信息", "沒有  詢價單號");
					return;
				 }
		
				 if( compareStatus == "F" ){
					Ext.Msg.alert("提示信息", "比價 已經完成");
					return;
				 }

				 if( inquiryStatus !='F' ) {
					Ext.Msg.alert("提示信息", "詢價狀態 尚未完成");
					return;
				 }
				 //var rows = quoteGrid.getSelectionModel().getSelections();
				 //if (rows.length == 0 || inquiryNum=="") {  

				 //只用指定TextField的id或者name屬性，服務器端Form中就能取到表單的數據
				 //如果同時指定了id和name，那麼name屬性將作為服務器端Form取表單數據的Key
				 var queryQuoteForm = Ext.getCmp('queryQuoteForm').getForm().getEl().dom;
				 queryQuoteForm.action = '/PQS/quotation/compare/QuoPaperCompare.action'
				 //指定為GET方式時,url中指定的參數將失效，表單項轉換成url中的key=value傳遞給服務端
				 //例如這裡指定為GET的話,url為:submit.aspx?param2=你輸入的值
				 queryQuoteForm.method = 'POST';//GET、POST
				 queryQuoteForm.submit();
				
			}else {
				return;
			}

		});
	}
	
	function editInquiry(){
		var inquiryNum = Ext.getCmp('queryQuoteFormInquiryNum').getValue();
		var inquiryStatus = Ext.getCmp('queryQuoteFormInquiryStatus').getValue();
		var paperVerUid = Ext.getCmp('queryQuoteFormPaperVerUid').getValue();
		Ext.Msg.confirm("Waring", "確定'編修詢價單' " + inquiryNum , function(button) {
			
			if (button == "yes") {
				 if( inquiryStatus =='F' ) {
					Ext.Msg.alert("提示信息", "詢價狀態 已經完成");
					return;
				 }
				Ext.getCmp('nextForm').getForm().setValues({
					inquiryNum: inquiryNum, 
					paperVerUid: paperVerUid 
				})


				 //只用指定TextField的id或者name屬性，服務器端Form中就能取到表單的數據
				 //如果同時指定了id和name，那麼name屬性將作為服務器端Form取表單數據的Key
				 var nextForm = Ext.getCmp('nextForm').getForm().getEl().dom;
				 nextForm.action = 'quotation/inquiry/UpdateInquiryPre.action'
				 //指定為GET方式時,url中指定的參數將失效，表單項轉換成url中的key=value傳遞給服務端
				 //例如這裡指定為GET的話,url為:submit.aspx?param2=你輸入的值
				 nextForm.method = 'POST';//GET、POST
				 nextForm.submit();

			}else {
				return;
			}

		});
	}


	function clearAllData(){
		queryQuoteForm.form.reset();
		queryInquiryForm.form.reset();

		var quoteStore = quoteGrid.getStore();
		quoteStore.removeAll();
		quoteGrid.getView().refresh();

		var inquiryStore = inquiryGrid.getStore();
		inquiryStore.removeAll();
		inquiryGrid.getView().refresh();
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
</style>

</head>
<body>
<div id="hidden-form" style="display:none;">
<input type="text" id = "hiddenVendor" name="hiddenVendor" value='張家港保稅區精工光電有限公司' />
<input type="button" id="hiddenVendorBtn" name="hiddenVendorBtn" >
<script type="text/javascript">
		SmartSearch.setup({
			cp:"<%=contextPath%>",
			button:"hiddenVendorBtn",
			inputField:"hiddenVendor",
			table:" PO_SUPPLIER_CONTACTS_VAL_V CONT, PO_VENDORS VENDOR, PO_VENDOR_SITES_ALL SITE ",
			keyColumn:"VENDOR.VENDOR_NAME",
			columns:" CONT.CONTACT CONTACT, CONT.EMAIL EMAIL, VENDOR.VENDOR_ID VENDOR_ID, " +
					" VENDOR.VENDOR_NAME VENDOR_NAME, VENDOR.PAYMENT_CURRENCY_CODE PAYMENT_CURRENCY, " + 
					" VENDOR.PAYMENT_METHOD_LOOKUP_CODE PAYMENT_METHOD, SITE.VENDOR_SITE_ID VENDOR_SITE_ID, SITE.VENDOR_SITE_CODE VENDOR_SITE ",
			whereCause:" VENDOR.VENDOR_ID = SITE.VENDOR_ID " +
					" AND SITE.VENDOR_SITE_ID = CONT.VENDOR_SITE_ID " +
					" AND SITE.ORG_ID ='<%=orgSiteId%>' " +
					" AND VENDOR.ENABLED_FLAG ='Y' AND SITE.INACTIVE_DATE IS NULL  " ,
			orderBy:"VENDOR.VENDOR_ID",
			title:"Vendor Lit",
			mode:0,
			autoSearch:true,
			like:1,
            callbackHandle:"completeGetSupplier"
		});	

		function completeGetSupplier(inputField, columns, value){
			Ext.getCmp('queryInquiryFormInquirySupplierCode').setValue(value[0]["VENDOR.VENDOR_ID VENDOR_ID"]);
			Ext.getCmp('queryInquiryFormInquirySupplierName').setValue(value[0]["VENDOR.VENDOR_NAME VENDOR_NAME"]);
		}

</script>


<input type="text" id = "hiddenPartNum" name="hiddenPartNum" value='11590750-B0' />
<input type="button" id="hiddenPartNumBtn" name="hiddenPartNumBtn" >
<script type="text/javascript">
		SmartSearch.setup({
			cp:"<%=contextPath%>",
			button:"hiddenPartNumBtn",
			inputField:"hiddenPartNum",
			table:"MTL_SYSTEM_ITEMS_B MSIB",
			keyColumn:"SEGMENT1",
			columns:"SEGMENT1,DESCRIPTION,PRIMARY_UOM_CODE",
			whereCause:"MSIB.PURCHASING_ITEM_FLAG = 'Y' " +
			" AND MSIB.ORGANIZATION_ID ='<%=orgOuId%>'",
			orderBy:"SEGMENT1",
			title:"MATERIAL LIST",
			mode:0,
			autoSearch:true,
			like:0,
            callbackHandle:"completeGetPartNum"
		});	

		function completeGetPartNum(inputField, columns, value){
			Ext.getCmp('queryInquiryFormInquiryPartNum').setValue(value[0]["SEGMENT1"]);
			Ext.getCmp('queryInquiryFormInquiryPartNumDesc').setValue(value[0]["DESCRIPTION"]);
			
		}
</script>
</div>
	
 </body>
</html>
