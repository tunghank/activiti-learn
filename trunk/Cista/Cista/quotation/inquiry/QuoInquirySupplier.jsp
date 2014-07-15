<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.text.DecimalFormat"%>

<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ page import ="com.clt.system.to.SysFunParameterTo"%>
<%@ page import ="com.clt.system.to.SysUserTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogPaperVerTo"%>
<%@ page import ="com.clt.quotation.erp.to.ErpCurrencyTo"%>
<%@ page import ="com.clt.quotation.erp.to.ErpFreightTermsCodeTo"%>
<%@ page import ="com.clt.quotation.erp.to.ErpTradeTermsCodeTo"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	long t1 = System.currentTimeMillis();
	String contextPath = (String)request.getContextPath();
	// Get Current User
	SysUserTo curUser =
            (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
	
	String orgSiteId = CLTUtil.getMessage("System.ERP.Org");
	String orgOuId = CLTUtil.getMessage("System.ERP.OU");

	String partNumGrid = (String)request.getAttribute("partNumGrid");
	partNumGrid = null != partNumGrid ? partNumGrid : "";

	String paperVerUid = (String)request.getAttribute("paperVerUid");
	paperVerUid = null != paperVerUid ? paperVerUid : "";


	StringBuilder returnASL = (StringBuilder)request.getAttribute("returnASL");
	returnASL = null != returnASL ? returnASL : new StringBuilder();

	List<ErpCurrencyTo> waersList = (List<ErpCurrencyTo>)request.getAttribute("waersList");
	waersList = null != waersList ? waersList : new ArrayList<ErpCurrencyTo>();
	String comboWaers="";
	//準備 Select List Value
	for(int i=0;i < waersList.size(); i ++){
		ErpCurrencyTo waers = waersList.get(i);
		
		String fieldShowName = waers.getName();
		String fieldValue = waers.getCurrencyCode();
		//["格式A","格式A"]
		
		if ( i != waersList.size() - 1 ){
			comboWaers = comboWaers + "['" + fieldValue +"','" + fieldValue + " - " + fieldShowName + "'],";
		}else{
			comboWaers = comboWaers + "['" + fieldValue +"','" + fieldValue + " - " + fieldShowName + "']";
		}
			
	}

	List<ErpFreightTermsCodeTo> shippedByList = (List<ErpFreightTermsCodeTo>)request.getAttribute("shippedByList");
	shippedByList = null != shippedByList ? shippedByList : new ArrayList<ErpFreightTermsCodeTo>();
	String comboShippedBy="";
	//準備 Select List Value
	for(int i=0;i < shippedByList.size(); i ++){
		ErpFreightTermsCodeTo shippedBy = shippedByList.get(i);
		
		String meaning = shippedBy.getMeaning();
		String lookupCode = shippedBy.getLookupCode();
		//["格式A","格式A"]
		
		if ( i != shippedByList.size() - 1 ){
			comboShippedBy = comboShippedBy + "['" + lookupCode +"','"  + lookupCode + " - " + meaning + "'],";
		}else{
			comboShippedBy = comboShippedBy + "['" + lookupCode +"','"  + lookupCode + " - " + meaning + "']";
		}
			
	}

	List<ErpTradeTermsCodeTo> termsTransactionList = (List<ErpTradeTermsCodeTo>)request.getAttribute("termsTransactionList");
	termsTransactionList = null != termsTransactionList ? termsTransactionList : new ArrayList<ErpTradeTermsCodeTo>();
	String comboTermsTransaction="";
	//準備 Select List Value
	for(int i=0;i < termsTransactionList.size(); i ++){
		ErpTradeTermsCodeTo termsTransaction = termsTransactionList.get(i);
		
		String fieldShowName = termsTransaction.getDescription();
		String fieldValue = termsTransaction.getId();
		//["格式A","格式A"]
		
		if ( i != termsTransactionList.size() - 1 ){
			comboTermsTransaction = comboTermsTransaction + "['" + fieldValue +"','"  +  fieldShowName + "'],";
		}else{
			comboTermsTransaction = comboTermsTransaction + "['" + fieldValue +"','"  +  fieldShowName + "']";
		}
	}

	List<SysFunParameterTo> deliveryLocationList = (List<SysFunParameterTo>)request.getAttribute("deliveryLocationList");
	deliveryLocationList = null != deliveryLocationList ? deliveryLocationList : new ArrayList<SysFunParameterTo>();
	String comboDeliveryLocation="";
	//準備 Select List Value
	for(int i=0;i < deliveryLocationList.size(); i ++){
		SysFunParameterTo deliveryLocation = deliveryLocationList.get(i);
		
		String fieldShowName = deliveryLocation.getFieldShowName();
		String fieldValue = deliveryLocation.getFieldValue();
		//["格式A","格式A"]
		
		if ( i != deliveryLocationList.size() - 1 ){
			comboDeliveryLocation = comboDeliveryLocation + "['" + fieldValue +"','"  + fieldShowName + "'],";
		}else{
			comboDeliveryLocation = comboDeliveryLocation + "['" + fieldValue +"','"  + fieldShowName + "']";
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

	//詢價 供應商 Grid
	function delSelectedRow(grid, store) {

	   Ext.Msg.confirm('訊息', '確定要刪除？', function(btn){
		   if (btn == 'yes') {
			   var record = grid.getSelectionModel().getSelected();
			   store.remove(record);
			   grid.getView().refresh();

			   	//Disable Button
				Ext.getCmp('supplierFormBtnAdd').setDisabled(false);
				Ext.getCmp('supplierFormBtnSupplierCode').setDisabled(false);
				//Ext.getCmp('supplierGridTbBtnDel').setDisabled(false);
				//Disable Edit
				Ext.getCmp('supplierFormBtnEdit').setDisabled(true);
			   clearInquirySupplier();
		   }
	   });
	}

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
		new Ext.grid.RowNumberer(),
		{
		   id:'inquirySupplierCode',
           header: "廠商代碼",
           dataIndex: 'inquirySupplierCode',
           width: 70
        },{
           id:'inquirySupplierName',
           header: "廠商名稱",
           dataIndex: 'inquirySupplierName',
           width: 200
        },{
           id:'inquirySupplierSite',
           header: "Supplier Site",
           dataIndex: 'inquirySupplierSite',
           width: 150
        },{
           id:'inquirySupplierSiteCode',
           header: "Supplier Site Code",
           dataIndex: 'inquirySupplierSiteCode',
           width: 150,
		   hidden:true
        },{
           id:'inquirySupplierContact',
           header: "聯絡人",
           dataIndex: 'inquirySupplierContact',
           width: 80
        },{
           id:'inquirySupplierEmail',
           header: "email",
           dataIndex: 'inquirySupplierEmail',
           width: 200
        },{
           id:'inquiryCurrency',
           header: "幣別",
           dataIndex: 'inquiryCurrency',
           width:50
        },{
		   id:'inquiryPaymentMethod',
		   header: "交易條件",
		   dataIndex: 'inquiryPaymentMethod',
		   width: 60
		},{
           id:'inquiryDeliveryLocationDesc',
           header: "送貨地點",
           dataIndex: 'inquiryDeliveryLocationDesc',
           width: 170
        },{
           id:'inquiryDeliveryLocation',
           header: "送貨地點ID",
           dataIndex: 'inquiryDeliveryLocation',
           width: 80,
		   hidden: true
        },{
           id:'inquiryShippedBy',
           header: "運送方式",
           dataIndex: 'inquiryShippedBy',
           width: 70
        }
    ]);

    // by default columns are sortable
    cm.defaultSortable = true;

    // this could be inline, but we want to define the Plant record
    // type so we can add records dynamically
	var data = {

	};
	// create the Data Store
    var supplierStore = new Ext.data.GroupingStore({
		id:'supplierStore',
		proxy:new Ext.data.MemoryProxy(data),
		reader:new Ext.data.JsonReader({}, [
		   {name: 'inquirySupplierCode'},
           {name: 'inquirySupplierName'},
		   {name: 'inquirySupplierSite'},
		   {name: 'inquirySupplierSiteCode'},
		   {name: 'inquirySupplierContact'},
		   {name: 'inquirySupplierEmail'},
           {name: 'inquiryCurrency'},
           {name: 'inquiryPaymentMethod'},
		   {name: 'inquiryDeliveryLocationDesc'},
		   {name: 'inquiryDeliveryLocation'},
		   {name: 'inquiryShippedBy'}

		]),
		sortInfo:{ field: "inquirySupplierCode", direction: "ASC" }
	}) ;


	supplierStore.load();

	 var toolbar = new Ext.Toolbar({
		 id:'toolbar',
		 items: [  
			'-',{text:'刪除',
				  iconCls: 'remove',
				  id:'supplierGridTbBtnDel',
				  style:"background-color:#FF9900;",
				  pressed:true,
				  handler: function(){
						delSelectedRow(Ext.getCmp('supplierGrid'), Ext.getCmp('supplierGrid').getStore());
				  }

				},
			'-'
		 ]  
	 }); 

	// create the readonly grid
    var supplierGrid = new Ext.grid.GridPanel({
		id: 'supplierGrid',
        ds: supplierStore,
        cm: cm,
		tbar: toolbar,
		sm: new Ext.grid.RowSelectionModel({
	                singleSelect: true,
	                listeners: {
	                    rowselect: function(sm, row, rec) {
							clearInquirySupplier();

							//Disable Button
							Ext.getCmp('supplierFormBtnAdd').setDisabled(true);
							Ext.getCmp('supplierFormBtnSupplierCode').setDisabled(true);
							//Ext.getCmp('supplierGridTbBtnDel').setDisabled(true);
							//Disable Edit
							Ext.getCmp('supplierFormBtnEdit').setDisabled(false);
							
							//Set Form
							Ext.getCmp('supplierFormSupplierCode').setValue(rec.data['inquirySupplierCode']);
							Ext.getCmp('supplierFormSupplierName').setValue(rec.data['inquirySupplierName']);
							Ext.getCmp('supplierFormSupplierSite').setValue(rec.data['inquirySupplierSite']);
							Ext.getCmp('supplierFormSupplierSiteCode').setValue(rec.data['inquirySupplierSiteCode']);
							Ext.getCmp('supplierFormContact').setValue(rec.data['inquirySupplierContact']);
							Ext.getCmp('supplierFormContactEmail').setValue(rec.data['inquirySupplierEmail']);
							Ext.getCmp('supplierFormWaers').setValue(rec.data['inquiryCurrency']);
							Ext.getCmp('supplierFormTermsTransaction').setValue(rec.data['inquiryPaymentMethod']);
							Ext.getCmp('supplierFormDeliveryLocation').setValue(rec.data['inquiryDeliveryLocation']);
							Ext.getCmp('supplierFormDeliveryLocationDesc').setValue(rec.data['inquiryDeliveryLocationDesc']);
							Ext.getCmp('supplierFormShippedBy').setValue(rec.data['inquiryShippedBy']);
	                    }
	                }
	            }),
        region:'center',
        title:'詢價供應商',
        frame:true,
		loadMask: true,
	    stripeRows: true,
		iconCls: 'icon-grid',
        clicksToEdit:1
	}) ;

	//Load ASL Data
	var aslJsonData = Ext.util.JSON.decode('<%=returnASL%>');
	supplierStore.loadData(aslJsonData);


	//Form
	var supplierForm = new Ext.form.FieldSet({
        id: 'supplierForm',
		title: '資料編輯區',
		style: 'padding: 10 0 0 0',
		bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		labelAlign: 'left',
		height:160,
		region:'north',
        items: [{
			layout: "column",
			border: false,
			frame: true,
			items: [{
				columnWidth: .1,
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
					id:'supplierFormSupplierCode',
					name: 'supplierFormSupplierCode',
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
					id:'supplierFormBtnSupplierCode',
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
				columnWidth: .3,
				height:30,
				items: [{
					xtype: "textfield",
					id:'supplierFormSupplierName',
					name: 'supplierFormSupplierName',
					allowBlank:false,
					readOnly:true,
					width: 300,
					style:"background:#EEEEEE;color:#BC6618;",
					anchor:'90%'
				}]
			},{
				columnWidth: .07,
				height:30,
				items: [{
					xtype: "label",
					text: 'Supplier Site:',
					anchor:'90%'
				}]
			},{
				columnWidth: .27,
				height:30,
				items: [{
					xtype: "textfield",
					id:'supplierFormSupplierSite',
					name: 'supplierFormSupplierSite',
					allowBlank:false,
					readOnly:true,
					width: 300,
					style:"background:#EEEEEE;color:#BC6618;",
					anchor:'90%'
				}]
			},{
				columnWidth: .01,
				height:30,
				items: [{
					xtype: "textfield",
					id:'supplierFormSupplierSiteCode',
					name: 'supplierFormSupplierSiteCode',
					allowBlank:false,
					readOnly:true,
					hidden:true,
					style:"background:#EEEEEE;color:#BC6618;",
					anchor:'90%'
				}]
			},{//第二行
				columnWidth: .1,
				height:30,
				items: [{
					xtype: "label",
					text: '聯絡人:',
					anchor:'90%'
				}]
			},{
				columnWidth: .2,
				height:30,
				items: [{
					xtype: "textfield",
					id:'supplierFormContact',
					name: 'supplierFormContact',
					allowBlank:false,
					width: 200,
					anchor:'90%'
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: 'email:',
					anchor:'90%'
				}]
			},{
				columnWidth: .4,
				height:30,
				items: [{
					xtype: "textfield",
					id:'supplierFormContactEmail',
					name: 'supplierFormContactEmail',
					allowBlank:false,
					width: 350,
					anchor:'90%'
				}]
			},{
				columnWidth: .25,
				height:30,
				items: [{}]
			},{//第三行
				columnWidth: .1,
				height:30,
				items: [{
					xtype: "label",
					text: '幣別:',
					anchor:'90%'
				}]
			},{
				columnWidth: .2,
				height:30,
				items: [{
					xtype:"combo",
					id:'supplierFormWaers',
					name: 'supplierFormWaers',
					allowBlank:false,
					store:new Ext.data.SimpleStore({
					fields: ['value', 'text'],
						data: [<%=comboWaers%>
						]
					}),
					displayField: 'text',
					valueField: 'value',
					mode: 'local',
					editable: false,
					triggerAction: 'all',
					width: 200,
					anchor:'90%'
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '運送方式:',
					anchor:'90%'
				}]
			},{
				columnWidth: .15,
				height:30,
				items: [{
					xtype:"combo",
					id:'supplierFormShippedBy',
					name: 'supplierFormShippedBy',
					allowBlank:false,
					store:new Ext.data.SimpleStore({
					fields: ['value', 'text'],
						data: [<%=comboShippedBy%>
						]
					}),
					displayField: 'text',
					valueField: 'value',
					mode: 'local',
					editable: false,
					triggerAction: 'all',
					width: 150,
					anchor:'90%'
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '交易條件:',
					anchor:'90%'
				}]
			},{
				columnWidth: .15,
				height:30,
				items: [{
					xtype:"combo",
					id:'supplierFormTermsTransaction',
					name: 'supplierFormTermsTransaction',
					allowBlank:false,
					store:new Ext.data.SimpleStore({
					fields: ['value', 'text'],
						data: [<%=comboTermsTransaction%>
						]
					}),
					displayField: 'text',
					valueField: 'value',
					mode: 'local',
					editable: false,
					triggerAction: 'all',
					width: 150,
					anchor:'90%'
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '送貨地點:',
					anchor:'90%'
				}]
			},{
				columnWidth: .001,
				height:30,
				items: [{
					xtype: "textfield",
					id:'supplierFormDeliveryLocation',
					name: 'supplierFormDeliveryLocation',
					allowBlank:false,
					readOnly:true,
					style:"background:#EEEEEE;color:#BC6618;",
					anchor:'90%',
					hidden: true
				}]
			},{
				columnWidth: .15,
				height:30,
				items: [{
					xtype: "textfield",
					id:'supplierFormDeliveryLocationDesc',
					name: 'supplierFormDeliveryLocationDesc',
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
					id:'supplierFormBtnDeliveryLocation',
					text: '...',
					listeners:{
						"click":function(){
							document.getElementById("hiddenDeliveryLocationBtn").click();
						}
					}
				}]
			},{//第四行
				columnWidth: .3,
				height:30,
				items: [{}]
			},{
				columnWidth: .2,
				height:30,
				items: [{
					xtype: "label",
					text: '*** 非APL供應商請在下一步新增.',
					style:"color:#FF3300;",
					anchor:'90%'
				}]
			},{
				columnWidth: .3,
				height:30,
				items: [{}]
			},{
				columnWidth: .1,
				height:30,
				items: [{
					xtype:"button",
					text:"新增",
					id:'supplierFormBtnAdd',
					name: 'supplierFormBtnAdd',
					anchor:'90%',
					listeners:{
						"click":function(){
							addInquirySupplier();
						}
					}
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype:"button",
					text:"修改",
					id:'supplierFormBtnEdit',
					name: 'supplierFormBtnEdit',
					anchor:'90%',
					disabled :true,
					listeners:{
						"click":function(){
							editInquirySupplier();
						}
					}
				}]
			}]
		}]
    });


	var nextForm = new  Ext.form.FormPanel({
        id: 'nextForm',
        frame: true,
		//style: 'padding: 0 0 0 0',
		//style:'font-weight:bold;text-align:left;padding-right:0px;',
        //bodyStyle: 'padding:5px',
		//bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		headers: {'Content-Type':'multipart/form-data; charset=UTF-8'},
		labelAlign: 'left',
		height:35,
		region:'south',
        items: [{
            layout:'column',
            items: [{
                columnWidth:.1,
                items:[{
					xtype: "label",
					text: '報價單回收時間:',
					anchor:'90%'
					}
                ]
            },{
                columnWidth:.03,
                items:[{
					xtype: "label",
					text: 'Date:',
					anchor:'90%'
					}
                ]
            },{
                columnWidth:.1,
                items:[{
					xtype: "datefield",
					id:'nextFormRecoverDate',
					name: 'nextFormRecoverDate',
					format:'Y/m/d',
					readOnly : true,
					allowBlank:false,
					minValue : new Date(),
					anchor:'90%'}
                ]
            },{
                columnWidth:.03,
                items:[{
					xtype: "label",
					text: 'Time:',
					anchor:'90%'
					}
                ]
            },{
                columnWidth:.2,
                items:[{
					xtype: "timefield",
					id:'nextFormRecoverTime',
					name: 'nextFormRecoverTime',
					width : 100,
				    increment  : 30,
				    format     : 'H:i',
					editable :false,
					allowBlank:false,
					anchor:'90%'}
                ]
            },{
                columnWidth:.34,
                items:[{}
                ]
            },{
                columnWidth:.1,
                items:[{
						xtype: 'button',
						text: '下一步',
						listeners:{
							"click":function(){
									nextInquiryConfirm(Ext.getCmp('supplierGrid'));
								}
						}
					}
                ]
            },{
                columnWidth:.1,
                items:[{
					xtype: 'button',
					text: '取消'}
                ]
            },{
                columnWidth:.1,
                items:[{
						xtype: "textarea",
						id:'partNumGridData',
						name: 'partNumGridData',
						allowBlank:false,
						readOnly:true,
						width : 600,
						height : 80,
						multiline: true,
						value:'<%=partNumGrid%>',
						hidden: true
						//anchor:'90%'
					}
                ]
            },{
				columnWidth: .05,
				height:30,
				items: [{
					xtype : 'textfield',
					id:'paperVerUid',
					name: 'paperVerUid',
					readOnly:true,
					style:"background:#EEEEEE;color:#0000B7;",
					value:'<%=paperVerUid%>',
					hidden: true
				}]
			}]
        }]
    });

	//Panel
	var mainPanel = new Ext.Panel({
		title:'輸入詢價供應商',
		layout: 'border',
		bodyStyle: "background-color:#FFFFFF; border-width: 0px 2px 0px 0px;",
		//管理grid
		items:[supplierForm, supplierGrid, nextForm]
	});
	

	/*********************************************************
	*					Layout
	*********************************************************/

	// Finally, build the main layout once all the pieces are ready.  This is also a good
	// example of putting together a full-screen BorderLayout within a Viewport.
    new Ext.Viewport({
		layout: 'fit',
		title: '輸入詢價供應商',
		forceFit: true,
		//defaults: {autoScroll: true}, 
		items: [mainPanel],
        renderTo: Ext.getBody()
    });

	//Set 回收Date
	var today = new Date();
    var nextDay= new Date( today.setDate(today.getDate()+1) );
	Ext.getCmp('nextFormRecoverDate').setValue(nextDay);
	Ext.getCmp('nextFormRecoverTime').setValue('12:00');


});

function editInquirySupplier(row){

	if( checkInquirySupplier()){
		var supplierGrid = Ext.getCmp('supplierGrid');

		var selRec = supplierGrid.getSelectionModel().getSelected();
		var index = supplierGrid.getStore().indexOf(selRec);
		var rec = supplierGrid.store.getAt(index);
		//Data
		rec.data['inquirySupplierCode'] = Ext.getCmp('supplierFormSupplierCode').getValue();
		rec.data['inquirySupplierName'] = Ext.getCmp('supplierFormSupplierName').getValue();
		rec.data['inquirySupplierSite'] = Ext.getCmp('supplierFormSupplierSite').getValue();
		rec.data['inquirySupplierSiteCode'] = Ext.getCmp('supplierFormSupplierSiteCode').getValue();
		rec.data['inquirySupplierContact'] = Ext.getCmp('supplierFormContact').getValue();
		rec.data['inquirySupplierEmail'] = Ext.getCmp('supplierFormContactEmail').getValue();
		rec.data['inquiryCurrency'] = Ext.getCmp('supplierFormWaers').getValue();
		rec.data['inquiryPaymentMethod'] = Ext.getCmp('supplierFormTermsTransaction').getValue();
		rec.data['inquiryDeliveryLocation'] = Ext.getCmp('supplierFormDeliveryLocation').getValue();
		rec.data['inquiryDeliveryLocationDesc'] = Ext.getCmp('supplierFormDeliveryLocationDesc').getValue();
		rec.data['inquiryShippedBy'] = Ext.getCmp('supplierFormShippedBy').getValue();
		supplierGrid.getView().refresh();
		
		//Disable Button
		Ext.getCmp('supplierFormBtnAdd').setDisabled(false);
		Ext.getCmp('supplierFormBtnSupplierCode').setDisabled(false);
		//Ext.getCmp('supplierGridTbBtnDel').setDisabled(false);
		//Disable Edit
		Ext.getCmp('supplierFormBtnEdit').setDisabled(true);

		clearInquirySupplier();
	}
}

function addInquirySupplier(){

	if( checkInquirySupplier()){
		var supplierGrid = Ext.getCmp('supplierGrid');
		supplierGrid.stopEditing();
		var count = supplierGrid.getStore().getCount();
		var rec = new Ext.data.Record();
		//Data
		rec.data['inquirySupplierCode'] = Ext.getCmp('supplierFormSupplierCode').getValue();
		rec.data['inquirySupplierName'] = Ext.getCmp('supplierFormSupplierName').getValue();
		rec.data['inquirySupplierSite'] = Ext.getCmp('supplierFormSupplierSite').getValue();
		rec.data['inquirySupplierSiteCode'] = Ext.getCmp('supplierFormSupplierSiteCode').getValue();
		rec.data['inquirySupplierContact'] = Ext.getCmp('supplierFormContact').getValue();
		rec.data['inquirySupplierEmail'] = Ext.getCmp('supplierFormContactEmail').getValue();
		rec.data['inquiryCurrency'] = Ext.getCmp('supplierFormWaers').getValue();
		rec.data['inquiryPaymentMethod'] = Ext.getCmp('supplierFormTermsTransaction').getValue();
		rec.data['inquiryDeliveryLocation'] = Ext.getCmp('supplierFormDeliveryLocation').getValue();
		rec.data['inquiryDeliveryLocationDesc'] = Ext.getCmp('supplierFormDeliveryLocationDesc').getValue();
		rec.data['inquiryShippedBy'] = Ext.getCmp('supplierFormShippedBy').getValue();

		supplierGrid.getStore().insert(count, rec);

		supplierGrid.getView().refresh();
		clearInquirySupplier();
	}
}

function checkInquirySupplier(){
		//Check 廠商代碼 value
	var supplierFormSupplierCode = Ext.getCmp('supplierFormSupplierCode').getValue();
	if( supplierFormSupplierCode != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '廠商代碼' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check 聯絡人 value
	var supplierFormContact = Ext.getCmp('supplierFormContact').getValue();
	if( supplierFormContact != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '聯絡人' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check email value
	var supplierFormContactEmail = Ext.getCmp('supplierFormContactEmail').getValue();
	if( supplierFormContactEmail != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " 'email' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check 幣別 value
	var supplierFormWaers = Ext.getCmp('supplierFormWaers').getValue();
	if( supplierFormWaers != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '幣別' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check 運送方式 value
	var supplierFormShippedBy = Ext.getCmp('supplierFormShippedBy').getValue();
	if( supplierFormShippedBy != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '運送方式' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check 交易條件 value
	var supplierFormTermsTransaction = Ext.getCmp('supplierFormTermsTransaction').getValue();
	if( supplierFormTermsTransaction != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '交易條件' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check 送貨地點 value
	var supplierFormDeliveryLocation = Ext.getCmp('supplierFormDeliveryLocation').getValue();
	if( supplierFormDeliveryLocation != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '送貨地點' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check email value
	var supplierFormContactEmail = Ext.getCmp('supplierFormContactEmail').getValue();
	supplierFormContactEmail = supplierFormContactEmail.trim();
	if( supplierFormContactEmail != "" ){
		if ( !supplierFormContactEmail.isValidEmail() ){
			Ext.MessageBox.show({
					title:'MESSAGE',
					msg: " 'email' 必需符合格式!",
					icon: Ext.MessageBox.ERROR
				});
				return false;
		}
	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " 'email' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}
	return true;
}

function nextInquiryConfirm(grid){


	//Check 報價單回收時間 value
	var nextFormRecoverDate = Ext.getCmp('nextFormRecoverDate').getValue();
	if( nextFormRecoverDate != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '報價單回收時間 Date' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check 報價單回收時間 value
	var nextFormRecoverTime = Ext.getCmp('nextFormRecoverTime').getValue();
	if( nextFormRecoverTime != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '報價單回收時間 Time' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check Supplier Grid value
	var supplierGridStore = grid.getStore();

	if(supplierGridStore.getCount() == 0 ) {
		Ext.Msg.alert("Waring", "No Any Data");
		return;
	}
	var jsonData = [];
	 
	for(var i=0; i < supplierGridStore.getCount(); i ++ ){
		var rec = supplierGridStore.getAt( i );

		var supplierName = rec.get('inquirySupplierName');
		
		var currency = rec.get('inquiryCurrency');
		var paymentMethod = rec.get('inquiryPaymentMethod');
		var shippedBy = rec.get('inquiryShippedBy');
		var deliveryLocation = rec.get('inquiryDeliveryLocation');

		if( currency == "" || paymentMethod == "" || shippedBy == "" || deliveryLocation == "" ){
				Ext.Msg.alert("Waring", "第 " + (i+1) + "列 '幣別 , 交易條件, 送貨地點, 運送方式' 都必須有值.");
				return;
		}
		jsonData[i] = rec;
	}


	var jsonArray = [];
	Ext.each(jsonData, function(item) {
		jsonArray.push(item.data);
	});

	Ext.Msg.confirm("Waring", "確定往 '下一步' ", function(button) {
		
		if (button == "yes") {
			 //Ext.getCmp('partNumGrid').setValue('<%=partNumGrid%>');
				
			 //只用指定TextField的id或者name屬性，服務器端Form中就能取到表單的數據
			 //如果同時指定了id和name，那麼name屬性將作為服務器端Form取表單數據的Key
			 var nextForm = Ext.getCmp('nextForm').getForm().getEl().dom;
			 nextForm.action = 'quotation/inquiry/SaveInquirySupplier.action?supplierGrid=' + 
					Ext.encode(jsonArray) + 
					'&recoverDate=' + formatDate(Ext.getCmp('nextFormRecoverDate').getValue()) +
					'&recoverTime=' + Ext.getCmp('nextFormRecoverTime').getValue();
			 //指定為GET方式時,url中指定的參數將失效，表單項轉換成url中的key=value傳遞給服務端
			 //例如這裡指定為GET的話,url為:submit.aspx?param2=你輸入的值
			 nextForm.method = 'POST';//GET、POST
			 nextForm.submit();

		}else {
			return;
		}

	});
}

function formatDate(value){
    return value ? value.dateFormat('Y/m/d') : '';
};

function clearInquirySupplier(){
	Ext.getCmp('supplierFormSupplierCode').reset();
	Ext.getCmp('supplierFormSupplierName').reset();
	Ext.getCmp('supplierFormSupplierSite').reset();
	Ext.getCmp('supplierFormSupplierSiteCode').reset();
	Ext.getCmp('supplierFormContact').reset();
	Ext.getCmp('supplierFormContactEmail').reset();
	Ext.getCmp('supplierFormWaers').reset();
	Ext.getCmp('supplierFormTermsTransaction').reset();
	Ext.getCmp('supplierFormDeliveryLocation').reset();
	Ext.getCmp('supplierFormDeliveryLocationDesc').reset();
	Ext.getCmp('supplierFormShippedBy').reset();
}

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
					" VENDOR.PAY_DATE_BASIS_LOOKUP_CODE PAYMENT_METHOD, SITE.VENDOR_SITE_ID VENDOR_SITE_ID, SITE.VENDOR_SITE_CODE VENDOR_SITE ",
			whereCause:" VENDOR.VENDOR_ID = SITE.VENDOR_ID " +
					" AND SITE.VENDOR_SITE_ID = CONT.VENDOR_SITE_ID " +
					" AND SITE.ORG_ID ='<%=orgSiteId%>' " +
					" AND VENDOR.ENABLED_FLAG ='Y' AND SITE.INACTIVE_DATE IS NULL " ,
			orderBy:"VENDOR.VENDOR_ID",
			title:"Vendor Lit",
			mode:0,
			autoSearch:true,
			like:1,
            callbackHandle:"completeGetSupplier"
		});	

		function completeGetSupplier(inputField, columns, value){
			Ext.getCmp('supplierFormSupplierCode').setValue(value[0]["VENDOR.VENDOR_ID VENDOR_ID"]);
			Ext.getCmp('supplierFormSupplierName').setValue(value[0]["VENDOR.VENDOR_NAME VENDOR_NAME"]);
			Ext.getCmp('supplierFormSupplierSite').setValue(value[0]["SITE.VENDOR_SITE_CODE VENDOR_SITE"]);
			Ext.getCmp('supplierFormSupplierSiteCode').setValue(value[0]["SITE.VENDOR_SITE_ID VENDOR_SITE_ID"]);
			Ext.getCmp('supplierFormContact').setValue(value[0]["CONT.CONTACT CONTACT"]);
			
			Ext.getCmp('supplierFormContactEmail').setValue(value[0]["CONT.EMAIL EMAIL"].trim());
			
			Ext.getCmp('supplierFormWaers').setValue(value[0]["VENDOR.PAYMENT_CURRENCY_CODE PAYMENT_CURRENCY"]);
			//Ext.getCmp('supplierFormTermsTransaction').setValue(value[0]["VENDOR.PAY_DATE_BASIS_LOOKUP_CODE PAYMENT_METHOD"]);
		}

</script>


<input type="text" id = "hiddenDeliveryLocation" name="hiddenDeliveryLocation" value='奇菱' />
<input type="button" id="hiddenDeliveryLocationBtn" name="hiddenDeliveryLocationBtn" >
<script type="text/javascript">
		SmartSearch.setup({
			cp:"<%=contextPath%>",
			button:"hiddenDeliveryLocationBtn",
			inputField:"hiddenDeliveryLocation",
			table:" HR_LOCATIONS_ALL_V ",
			keyColumn:"LOCATION_CODE",
			columns:" LOCATION_ID, LOCATION_CODE ",
			whereCause:" 1=1 AND ( INVENTORY_ORGANIZATION_ID ='<%=orgOuId%>' or INVENTORY_ORGANIZATION_ID IS NULL ) AND INACTIVE_DATE IS NULL " ,
			orderBy:"LOCATION_CODE",
			title:"送貨地點",
			mode:0,
			autoSearch:true,
			like:1,
            callbackHandle:"completeDeliveryLocation"
		});	

		function completeDeliveryLocation(inputField, columns, value){
			Ext.getCmp('supplierFormDeliveryLocation').setValue(value[0]["LOCATION_ID"]);
			Ext.getCmp('supplierFormDeliveryLocationDesc').setValue(value[0]["LOCATION_CODE"]);

		}



		
</script>

</div>
<div id="header"><h3>詢價單-Vendor</h3></div>
	
 </body>
</html>
