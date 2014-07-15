<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.text.DecimalFormat"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import ="java.util.Calendar"%>
<%@ page import ="java.util.Date"%>

<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ page import ="com.clt.system.to.SysUserTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogPaperVerTo"%>
<%@ page import ="com.clt.quotation.quote.to.QuoteHeaderTo"%>
<%@ page import ="com.clt.quotation.inquiry.to.InquirySupplierTo"%>
<%@ page import ="com.clt.quotation.inquiry.to.InquiryHeaderTo"%>
<%@ page import ="com.clt.system.to.SysUserTo"%>
<%@ page import ="com.clt.quotation.erp.to.ErpTaxTo"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	long t1 = System.currentTimeMillis();
	String contextPath = (String)request.getContextPath();
	// Get Current User
	SysUserTo curUser =
            (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
	
	List<QuoCatalogTo> catalogList = (List<QuoCatalogTo>)request.getAttribute("catalogList");
	catalogList = null != catalogList ? catalogList : new ArrayList<QuoCatalogTo>();

	String quoteNum = (String)request.getAttribute("quoteNum");
	quoteNum = null != quoteNum ? quoteNum : "" ;

	String quoteHeaderUid = (String)request.getAttribute("quoteHeaderUid");
	quoteHeaderUid = null != quoteHeaderUid ? quoteHeaderUid : "" ;

	InquiryHeaderTo inquiryHeaderTo = (InquiryHeaderTo)request.getAttribute("inquiryHeaderTo");
	inquiryHeaderTo = null != inquiryHeaderTo ? inquiryHeaderTo : new InquiryHeaderTo();

	InquirySupplierTo inquirySupplierTo = (InquirySupplierTo)request.getAttribute("inquirySupplierTo");
	inquirySupplierTo = null != inquirySupplierTo ? inquirySupplierTo : new InquirySupplierTo();

	QuoteHeaderTo quoteHeaderTo = (QuoteHeaderTo)request.getAttribute("quoteHeaderTo");
	quoteHeaderTo = null != quoteHeaderTo ? quoteHeaderTo : new QuoteHeaderTo();
	
	double totalFormQuoRealTotal = 0;
	if( quoteHeaderTo.getQuoteRealTotal() == null ){
		totalFormQuoRealTotal = 0;
	}else{
		totalFormQuoRealTotal = quoteHeaderTo.getQuoteRealTotal();
	}
	
	double totalFormQuoTFTTotal = 0;
	if( quoteHeaderTo.getQuoteRealTotal() == null ){
		totalFormQuoTFTTotal = 0;
	}else{
		totalFormQuoTFTTotal = quoteHeaderTo.getQuoteTftTotal();
	}


	String totalFormQuoTax = quoteHeaderTo.getQuoteTax();
	totalFormQuoTax = null != totalFormQuoTax ? totalFormQuoTax : "" ;

	String totalFormQuoNote = quoteHeaderTo.getQuoteNotes();
	totalFormQuoNote = null != totalFormQuoNote ? totalFormQuoNote : "" ;

	SysUserTo cltContactUser = (SysUserTo)request.getAttribute("cltContactUser");
	cltContactUser = null != cltContactUser ? cltContactUser : new SysUserTo();
	
	StringBuilder returnJosnString = (StringBuilder)request.getAttribute("returnJosnString");
	returnJosnString = null != returnJosnString ? returnJosnString : new StringBuilder();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	Calendar today = Calendar.getInstance();
    String quoteDate = sdf.format(today.getTime());
    Date quoteDate2 = sdf2.parse( sdf2.format(today.getTime()) ) ;
    System.out.println("*********************************** " +quoteDate2);
    
	Date quotationRecoverTime = inquirySupplierTo.getQuotationRecoverTime();
	long dateDiff = quotationRecoverTime.getTime() - quoteDate2.getTime();
	System.out.println("*********************************** " +quotationRecoverTime);
	System.out.println("*********************************** " + dateDiff);
	
	List<ErpTaxTo> erpTaxRateList = (List<ErpTaxTo>)request.getAttribute("erpTaxRateList");
	erpTaxRateList = null != erpTaxRateList ? erpTaxRateList : new ArrayList<ErpTaxTo>();
	String comboTaxRate="";
	//準備 Select List Value
	for(int i=0;i < erpTaxRateList.size(); i ++){
		ErpTaxTo erpTaxTo = erpTaxRateList.get(i);
		
		String taxRate = erpTaxTo.getTaxRate();
		//["格式A","格式A"]
		
		if ( i != erpTaxRateList.size() - 1 ){
			comboTaxRate = comboTaxRate + "['" + taxRate +"','"  + taxRate + "%'],";
		}else{
			comboTaxRate = comboTaxRate + "['" + taxRate +"','"  + taxRate + "%']";
		}
			
	}

	String inquiryRemark ="";
	/*String inquiryRemark = inquiryHeaderTo.getInquiryRemark();
	inquiryRemark = null != inquiryRemark ? inquiryRemark : "" ;
	inquiryRemark = inquiryRemark.replaceAll("(\\r\\n|\\n)", "");*/
	
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
	var paperVerUid = '<%=inquiryHeaderTo.getPaperVerUid()%>';

	/********************
	*	Form Object
	*********************/
	var askPriceForm = new Ext.form.FieldSet({
        id: 'askPriceForm',
        frame: true,
        labelAlign: 'left',
        title: '詢價資訊',
		region:'center',
		style: 'padding: 0 0 0 0',
		//style:'font-weight:bold;text-align:left;padding-right:0px;',
        //bodyStyle: 'padding:5px',
		bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		//bodyStyle: 'padding-top: 0px',
        labelWidth: 90,
		height :160,
		collapsible:true,
        defaults: {width: 120},	// Default config options for child items
        defaultType: 'textfield',
		columnWidth:.5,
		layout:'form',
        items: [{
			id:'askPriceFormCurrency',
            fieldLabel: '幣別',
            name: 'currency',
			readOnly:true,
			style:"background:#EEEEEE;",
			value:'<%=inquirySupplierTo.getInquiryCurrency()%>'
        },{
			id:'askPriceFormTransportWay',
            fieldLabel: '運送方式',
            name: 'transportWay',
			readOnly:true,
			style:"background:#EEEEEE;",
			value:'<%=inquirySupplierTo.getInquiryShippedBy()%>'
        },{
			id:'askPriceFormTermsTransaction',
            fieldLabel: '交易條件',
            name: 'termsTransaction',
			readOnly:true,
			style:"background:#EEEEEE;",
			value:'<%=inquirySupplierTo.getInquiryPaymentMethod()%>'
        },{
			id:'askPriceFormDeliveryLocations',
            fieldLabel: '送貨地點',
            name: 'deliveryLocations',
			readOnly:true,
			style:"background:#EEEEEE;",
			value:'<%=inquirySupplierTo.getInquiryDeliveryLocationDesc()%>'
        },{
			id:'askPriceFormQuoReturnDeadline',
            fieldLabel: '報價單回收期限',
            name: 'quoReturnDeadline',
			readOnly:true,
			style:"background:#EEEEEE;",
			width: 125,
			value:'<%=inquirySupplierTo.getQuotationRecoverTime()%>'
        }]
    });

	var totalForm = new Ext.form.FieldSet({
        id: 'totalForm',
        frame: true,
        labelAlign: 'left',
        title: '總計',
		region:'center',
		style: 'padding: 0 0 0 0',
		bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
        labelWidth: 140,
		autoScroll : true,
		height :390,
        defaults: {width: 140},	// Default config options for child items
        defaultType: 'textfield',
		collapsible:true,
		columnWidth:.5,
		layout:'form',
        items: [{
			id:'totalFormQuoTotal',
            fieldLabel: '總計',
            name: 'totalFormQuoTotal',
			readOnly:true,
			style:"background:#EEEEEE;"
        },{
			xtype : 'numberfield',
			id:'totalFormQuoRealTotal',
            fieldLabel: '實際報價',
            name: 'totalFormQuoRealTotal',
			style:'direction:rtl',
			style: 'text-align:left',
            decimalPrecision : 4,//精確到小數點後兩位 
            allowDecimals : true,//允許輸入小數 
            nanText :'請輸入有效的小數 2 位',//無效數字提示 
            allowNegative :false//允許輸入負數  
			
        },{
			xtype : 'numberfield',
			id:'totalFormQuoTFTTotal',
            fieldLabel: 'TFT(客戶指定用料必填)',
            name: 'totalFormQuoTFTTotal',
			style:'direction:rtl',
			style: 'text-align:left',
            decimalPrecision : 4,//精確到小數點後兩位 
            allowDecimals : true,//允許輸入小數 
            nanText :'請輸入有效的小數 2 位',//無效數字提示 
            allowNegative :false//允許輸入負數  
        },{
			xtype:"combo",
			id:'totalFormQuoTax',
            fieldLabel: '稅率',
            name: 'totalFormQuoTax',
			store:new Ext.data.SimpleStore({
				fields: ['value', 'text'],
				data: [<%=comboTaxRate%>
					]
			}),
			displayField: 'text',
			valueField: 'value',
			mode: 'local',
			editable: false,
			triggerAction: 'all'
        },{
			xtype: 'textarea',
			id:'totalFormQuoNote',
            fieldLabel: '備註',
            name: 'totalFormQuoNote',
			multiline: true
        }]

    });

    var saveButton = new Ext.Button({
        	id:'saveButton',
			cls:		"button_align_right",
			text: '儲存',
			handler: saveQuote
    })

	/********************
	*	Form Object 2
	*********************/
	var quoInfoForm = new Ext.form.FieldSet({
        id: 'quoInfoForm',
        labelAlign: 'left',
		layout: 'column',
		height :24,
		style: 'padding: 0 0 0 0',
		bodyStyle: 'padding-right:0px;padding-left:10px;padding-top:2px;padding-bottom:0px;',
		border: false,
		items: [{
			columnWidth:.08,
			border:false,
			items: [{
				xtype: "label",
				text: '報價單號:'
			}]
		},{
			columnWidth:.2,
			border:false,
			items: [{
				xtype:'textfield',
				id:'quoInfoFormQuoteNum',
				name: 'quoInfoFormQuoteNum',
				readOnly:true,
				width: 150,
				value:'<%=quoteNum%>',
				style:"background:#EEEEEE;color:#BC6618;"
			}]
		},{
			columnWidth:.1,
			border:false,
			items: [{
				xtype: "label",
				text: '報價單格式:'
			}]
		},{
			columnWidth:.34,
			border:false,
			items: [{
				xtype:'textfield',
				id:'quoInfoFormQuotation',
				name: 'quoInfoFormQuotation',
				readOnly:true,
				width: 250,
				value:'<%=inquiryHeaderTo.getQuotPaperDesc()%>',
				style:"background:#EEEEEE;color:#BC6618;"
			}]
		},{
			columnWidth:.08,
			border:false,
			items: [{
				xtype: "label",
				text: '報價日期:'
			}]
		},{
			columnWidth:.2,
			border:false,
			items: [{
				xtype:'textfield',
				id:'quoInfoFormQuotationDate',
				name: 'quoInfoFormQuotationDate',
				readOnly:true,
				value:'<%=quoteDate%>',
				style:"background:#EEEEEE;color:#BC6618;"
			}]
		}]
    });

	//供應商資料
	var supplierForm = new Ext.form.FieldSet({
        id: 'supplierForm',
        frame: true,
        labelAlign: 'left',
        title: '供應商資料',
		region:'center',
		style: 'padding: 0 0 0 0',
		//style:'font-weight:bold;text-align:left;padding-right:0px;',
        //bodyStyle: 'padding:5px',
		bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		//bodyStyle: 'padding-top: 0px',
        labelWidth: 60,
		autoHeight:true,
		//collapsible:true,
        defaults: {width: 120},	// Default config options for child items
        defaultType: 'textfield',
		columnWidth:1,
		layout:'form',
        items: [{
			id:'supplierFormSupplier',
            fieldLabel: '供應商',
            name: 'supplierFormSupplier',
			readOnly:true,
			style:"background:#EEEEEE;",
			align:'top',
			width: 250,
			value:'<%=inquirySupplierTo.getInquirySupplierName()%>'
        },{
			id:'supplierFormOfferPeople',
            fieldLabel: '報價人',
            name: 'supplierFormOfferPeople',
			readOnly:true,
			style:"background:#EEEEEE;",
			value:'<%=curUser.getRealName()%>'
        },{
			id:'supplierFormCellPhone',
            fieldLabel: '行動電話',
            name: 'supplierFormCellPhone',
			readOnly:true,
			style:"background:#EEEEEE;",
			value:'<%=curUser.getPhoneNum()%>'
        },{
			id:'supplierFormAddress',
            fieldLabel: '送貨地點',
            name: 'supplierFormAddress',
			readOnly:true,
			style:"background:#EEEEEE;",
			width: 250,
			value:'<%=inquirySupplierTo.getInquiryDeliveryLocationDesc()%>'
        }]
    });

	//機種
	var modelsForm = new Ext.form.FieldSet({
        id: 'modelsForm',
        frame: true,
        labelAlign: 'left',
        title: '機種',
		region:'center',
		style: 'padding: 0 0 0 0',
		//style:'font-weight:bold;text-align:left;padding-right:0px;',
        //bodyStyle: 'padding:5px',
		bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		//bodyStyle: 'padding-top: 0px',
        labelWidth: 60,
		autoHeight:true,
		//collapsible:true,
        defaults: {width: 120},	// Default config options for child items
        defaultType: 'textfield',
		columnWidth:1,
		layout:'form',
        items: [{
			id:'modelsFormModels',
            fieldLabel: '機種',
            name: 'modelsFormModels',
			readOnly:true,
			style:"background:#EEEEEE;"
        },{
			id:'modelsFormSpec',
            fieldLabel: '品名/規格',
            name: 'modelsFormSpec',
			readOnly:true,
			style:"background:#EEEEEE;",
			width:300,
			value:'<%=quoteHeaderTo.getQuotePartNumDesc()%>'
        },{
			id:'modelsFormPartNum',
            fieldLabel: '料號',
            name: 'modelsFormPartNum',
			readOnly:true,
			style:"background:#EEEEEE;",
			value:'<%=quoteHeaderTo.getQuotePartNum()%>'
        },{
			id:'modelsFormDrawingNo',
            fieldLabel: '圖號',
            name: 'modelsFormDrawingNo',
			readOnly:true,
			style:"background:#EEEEEE;"
        }]
    });

	//奇菱光電聯絡人

	var contactForm = new Ext.form.FieldSet({
        id: 'contactForm',
        frame: true,
        labelAlign: 'left',
        title: '奇菱光電聯絡人',
		region:'center',
		style: 'padding: 0 0 0 0',
		//style:'font-weight:bold;text-align:left;padding-right:0px;',
        //bodyStyle: 'padding:5px',
		bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		//bodyStyle: 'padding-top: 0px',
        labelWidth: 40,
		height : 111,
		//collapsible:true,
        defaults: {width: 120},	// Default config options for child items
        defaultType: 'textfield',
		columnWidth:1,
		layout:'form',
        items: [{
			id:'contactFormName',
            fieldLabel: '姓名',
            name: 'contactFormName',
			readOnly:true,
			style:"background:#EEEEEE;",
			value:'<%=cltContactUser.getRealName()%>'
        },{
			id:'contactFormDept',
            fieldLabel: '單位',
            name: 'contactFormDept',
			readOnly:true,
			style:"background:#EEEEEE;",
			value:'<%=cltContactUser.getDepartment()%>'
        },{
			id:'contactFormPhone',
            fieldLabel: '電話',
            name: 'contactFormPhone',
			readOnly:true,
			style:"background:#EEEEEE;",
			value:'<%=cltContactUser.getPhoneNum()%>'
        }]
    });

	//報價注意事項
	var noteForm = new Ext.form.FieldSet({
        id: 'noteForm',
        
        labelAlign: 'left',
        title: '報價注意事項',
		region:'center',
		style: 'padding: 0 0 0 0',
		//style:'font-weight:bold;text-align:left;padding-right:0px;',
        //bodyStyle: 'padding:5px',
		bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		//bodyStyle: 'padding-top: 0px',
        labelWidth: 40,
		autoHeight:true,
		//collapsible:true,
        defaults: {width: 120},	// Default config options for child items
        defaultType: 'textfield',
		columnWidth:1,
		layout:'form',
        items: [{
			xtype: 'textarea',
			id:'noteFormNotes',
            fieldLabel: '備註',
            name: 'noteFormNotes',
			multiline: true,
			width : 280,
			height : 80,
			style: 'padding: 0 0 0 0',
			bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
			align: 'left',
			col:6,
			row:6
        }]

    });
	Ext.getCmp('noteFormNotes').setValue('<%=inquiryRemark%>');
    
	/********************
	*	Paper Tabs Grid
	*********************/
	//Panel
	var resolution = screen.width+' x '+screen.height;
	var divWidth;
	var divHeight;
	var scale = divWidth/divHeight;

	if (scale == 0.75){
		//1024*768
		divWidth = screen.width * 0.95;
		divHeight = screen.height * 0.5;

		//e=document.getElementById("ecoa-grid");
		//e.style.top = '290px';
	}else if(scale > 0.75){
		//1280 x 1024
		divWidth = screen.width * 0.95;
		divHeight = screen.height * 0.5;
	}else {
		//1280 x 800
		divWidth = screen.width * 0.95;
		divHeight = screen.height * 0.5;
	}

	
	function formatDate(value){
		return value ? value.dateFormat('Ymd') : '';
	};

	function formatDateTime(value){
		return value ? value.dateFormat('YmdHis') : '';
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
	//Grid Function
	function moveSelectedRow(grid, direction) {

		var record = grid.getSelectionModel().getSelected();
		if (!record) {
			return;
		}
		var index = grid.getStore().indexOf(record);
		if (direction < 0) {
			index--;
			if (index < 0) {
				return;
			}
		} else {
			index++;
			if (index >= grid.getStore().getCount()) {
				return;
			}
		}
		grid.getStore().remove(record);
		grid.getStore().insert(index, record);
		grid.getSelectionModel().selectRow(index, true);
		grid.getView().refresh();
	};

	function delSelectedRow(grid, store) {

	   Ext.Msg.confirm('訊息', '確定要刪除？', function(btn){
		   if (btn == 'yes') {
			   var record = grid.getSelectionModel().getSelected();
			   store.remove(record);
			   grid.getView().refresh(); 
		   }
	   });
	}

	function addRow(grid) {
		var count = grid.getStore().getCount();
		var rec = new Ext.data.Record({

		});
		grid.stopEditing();
		grid.getStore().insert(count, rec);
		//grid.startEditing(count , 0);
		grid.getView().refresh();
		grid.getSelectionModel().selectRow(count); 
		
	};

	Ext.override(Ext.data.Store,{
		addField: function(field){
			field = new Ext.data.Field(field);
			this.recordType.prototype.fields.replace(field);
			if(typeof field.defaultValue != 'undefined'){
				this.each(function(r){
					if(typeof r.data[field.name] == 'undefined'){
						r.data[field.name] = field.defaultValue;
					}
				});
			}
		},
		removeField: function(name){
			this.recordType.prototype.fields.removeKey(name);
			this.each(function(r){
				delete r.data[name];
				if(r.modified){
					delete r.modified[name];
				}
			});
		}
	});
	Ext.override(Ext.grid.ColumnModel,{
		addColumn: function(column, colIndex){
			if(typeof column == 'string'){
				column = {header: column, dataIndex: column};
			}
			var config = this.config;
			this.config = [];
			if(typeof colIndex == 'number'){
				config.splice(colIndex, 0, column);
			}else{
				colIndex = config.push(column);
			}
			this.setConfig(config);
			return colIndex;
		},
		removeColumn: function(colIndex){
			var config = this.config;
			this.config = [config[colIndex]];
			config.splice(colIndex, 1);
			this.setConfig(config);
		}
	});
	Ext.override(Ext.grid.GridPanel,{
		addColumn: function(field, column, colIndex){
			if(!column){
				if(field.dataIndex){
					column = field;
					field = field.dataIndex;
				} else{
					column = field.name || field;
				}
			}
			this.store.addField(field);
			return this.colModel.addColumn(column, colIndex);
		},
		removeColumn: function(name, colIndex){
			this.store.removeField(name);
			if(typeof colIndex != 'number'){
				colIndex = this.colModel.findColumnIndex(name);
			}
			if(colIndex >= 0){
				this.colModel.removeColumn(colIndex);
			}
		}
	});



	/**********************************************
	*		Load Tab Form Database Function
	***********************************************/
	/************動態總計*********/

	//1.0先找出 此張Quotation 有少Items
	Ext.lib.Ajax.request(
		'POST',
		'quotation/config/AjaxPaperFildByPaperVer.action',
		{success: function(response){
				//parse Json data
				var groupListJsonData = Ext.util.JSON.decode(response.responseText);
				//alert(groupListJsonData.length);
				for (var i=groupListJsonData.length; i >= 0 ; i--){
					var groupData = groupListJsonData[i];
					if( groupData ){
						//alert(groupData);
						var paperGroupName =  groupData.paperGroupName;
						var paperGroupUid  =  groupData.paperGroupUid;
						var paperGroupSeq  =  groupData.paperGroupSeq;
						//alert(paperGroupName);
						var fieldList =  groupData.fieldList;
						//動態列出總計的  Ltem
						var totalFormObj = new Ext.form.TextField({
												id: "totalForm" + paperGroupUid,
												fieldLabel: paperGroupName,
												name: "totalForm" + paperGroupUid,
												readOnly:true,
												style:"background:#EEEEEE;"
											}); 
						//totalForm.items.add(totalFormObj);
						totalForm.items.insert(0, totalFormObj);
						totalForm.render(); 
						totalForm.doLayout(true);

					}
				}
				//產生Tabs
				for (var i=0; i < groupListJsonData.length ; i++){
					var groupData = groupListJsonData[i];
					if( groupData ){
						//alert(groupData);
						var paperGroupName =  groupData.paperGroupName;
						var paperGroupUid  =  groupData.paperGroupUid;
						var paperGroupSeq  =  groupData.paperGroupSeq;
						//alert(paperGroupName);
						var fieldList =  groupData.fieldList;
						//動態列出總計的  Ltem

						addTab(paperGroupName, paperGroupUid, fieldList);

					}
				}
				//將Tab定位回第一個Tab Item
				var paperTabs = Ext.getCmp('paper-Tabs');
				paperTabs.getItem(0).show();

				//將先前報價資料秀出
				var tabs1 = paperTabs.items.length;
				var quoteJsonData1 = Ext.util.JSON.decode('<%=returnJosnString%>');
				var totalFormQuoTotal = 0 * 1;

				for (var i=0 ; i<tabs1; i++){
					var tabGrid1 = paperTabs.getItem(i);
					var tabGridStore1 = tabGrid1.getStore();
					tabGridStore1.removeAll();
					tabGrid1.getTopToolbar().items.item(10).reset();
					var paperGroupUidValue1 = tabGrid1.getTopToolbar().items.item(9).getValue();
					//Clear Total Form Data
					var totalFormObj1 = "totalForm" + paperGroupUidValue1;
					Ext.getCmp(totalFormObj1).reset();
					if( quoteJsonData1 ) {
						for(var j=0; j < quoteJsonData1.length ; j++){
							
							if(paperGroupUidValue1 == quoteJsonData1[j].paperGroupUid ){
								var fieldValueList1 = quoteJsonData1[j].fieldValueList;								
								tabGridStore1.loadData(fieldValueList1);
								tabGrid1.getView().refresh();
								tabGrid1.getTopToolbar().items.item(10).setValue(quoteJsonData1[j].paperRecordTotal);
								Ext.getCmp(totalFormObj1).setValue(quoteJsonData1[j].paperRecordTotal);

								totalFormQuoTotal = totalFormQuoTotal * 1 + quoteJsonData1[j].paperRecordTotal * 1;
							}
						}
						
					}
					
				}//for (var i=0 ; i<tabs1; i++){
				Ext.getCmp('totalFormQuoTotal').setValue(totalFormQuoTotal);

				Ext.getCmp('totalFormQuoRealTotal').setValue(<%=totalFormQuoRealTotal%>);
				Ext.getCmp('totalFormQuoTFTTotal').setValue(<%=totalFormQuoTFTTotal%>);
				Ext.getCmp('totalFormQuoTax').setValue('<%=totalFormQuoTax%>');
				Ext.getCmp('totalFormQuoNote').setValue('<%=totalFormQuoNote%>');



		},failure: function(){
			Ext.Msg.alert("Error", "startQuotationItem Connection Error , Please Inform CLT IT");
		}},
		'paperVerUid=' + paperVerUid 
	);



	/**********************************************
	*				Add Tab Function
	***********************************************/

	var index = 0;

	function addTab(tabName, paperGroupUidValue, fieldList){

		 var indexId = (++index);
		 var gridItemId = 'gridItem' + indexId;

		/*******************************************************
		*				Grid Config
		********************************************************/


		// shorthand alias
		var fmItem = Ext.form;
		fmItem.id =  'fmItem' + indexId;
		// the column model has information about grid columns
		// dataIndex maps the column to the specific data field in
		// the data store (created below)
		
		var smItem = new Ext.grid.RowSelectionModel({singleSelection:true}); //選擇模型改為了行選擇模型

		var cmItem = new Ext.grid.ColumnModel([

		]);


		// by default columns are sortable
		cmItem.defaultSortable = true;

		// this could be inline, but we want to define the Plant record
		// type so we can add records dynamically
		var dataItem = {
		};
				
		/*var dataItem = [
		] ;*/

		var storeFields = new Array();
		for (var i=0 ; i< fieldList.length; i++){
			var fieldData = fieldList[i];
			if( fieldData ){
				var field =  fieldData.field;
				var fieldSeq =  fieldData.fieldSeq;

				storeFields[i] = 'item' + fieldSeq;

			}
		}


		// create the Data Store
		var storeItem = new Ext.data.Store({
			proxy:new Ext.data.MemoryProxy(dataItem),
			//reader:new Ext.data.ArrayReader({},[
			reader:new Ext.data.JsonReader({fields: storeFields})
		}) ;
		// trigger the data store load
		storeItem.load();

		var paperGroupUid = new Ext.form.TextField({
					id:paperGroupUidValue,
					name : "paperGroupUid",
					width:200,
					readOnly:true,
					hidden:true,  
					hiddenLabel:true,
					allowBlank: true
				});
		paperGroupUid.setValue(paperGroupUidValue);

		var paperRecordTotal = new Ext.form.NumberField({
					id:'total'+ paperGroupUidValue,
					name : "paperRecordTotal",
					style:'direction:rtl',
					style: 'text-align:right',
					width:200,
					decimalPrecision : 4,//精確到小數點後兩位 
					allowDecimals : true,//允許輸入小數 
					nanText :'請輸入有效的小數 2 位',//無效數字提示 
					allowNegative :false,//允許輸入負數
					readOnly:true,
					hidden:true,  
					hiddenLabel:true,
					allowBlank: true
				});

		//Grid Tool Bar
		var itemToolbar = new Ext.Toolbar({
			 id:'itemToolbar' + indexId,
			 items: [  
			   '-',{text:'Up',
					  iconCls: 'up',
					  style:"background-color:#FF9900;",
					  pressed:true,
					  handler: function(){
							moveSelectedRow(Ext.getCmp(gridItemId), -1);
					  }

					},
			   '-',{text:'Down',
					  iconCls: 'down',
					  style:"background-color:#FF9900;",
					  pressed:true,
					  handler: function(){
							moveSelectedRow(Ext.getCmp(gridItemId), 1);
					  }

					},
				'-',{text:'Delete',
					  iconCls: 'remove',
					  style:"background-color:#FF9900;",
					  pressed:true,
					  handler: function(){
							delSelectedRow(Ext.getCmp(gridItemId), Ext.getCmp(gridItemId).getStore());
					  }

					},
				'-',{text:'Add New',
					  iconCls: 'add',
					  style:"background-color:#FF9900;",
					  pressed:true,
					  handler: function(){
							addRow(Ext.getCmp(gridItemId))
					  }

					},
			   '-',paperGroupUid, paperRecordTotal
			 ]  
		 }); 
		/*主要 Add Tab Function*/
		
		//readonly grid
		var gridItem = new Ext.grid.EditorGridPanel({
			id: gridItemId,
			ds: storeItem,
			cm: cmItem,
			sm: smItem,
			tbar: itemToolbar,
			title:tabName,
			frame:true,
			loadMask: true,
			stripeRows: true,
			enableColumnMove :false,
			iconCls: 'icon-grid',
			clicksToEdit:1
		});
		
		//Check Grid 填入值 的一些相關確認
		gridItem.addListener("afteredit", function(grid){

				//1.1 Summary Total
				var count = gridItem.getColumnModel().getColumnCount();
				var rec = grid.record;//獲取被修改的列
				var field = grid.field;//獲取被修改的欄
				var indexColumn = gridItem.getColumnModel().findColumnIndex(field);
				var value = rec.get(field);
				var totalFormSize = totalForm.items.length;

				//抓出最後一行, 做加總.
				var storeItem = gridItem.getStore();
				for( var j=0; j < storeItem.getCount(); j++ ){
					var record = storeItem.getAt( j );
					var fieldName = gridItem.getColumnModel().getDataIndex(indexColumn);
					var total = record.get(fieldName)*1;
				}

				if( ( indexColumn + 1)  == count ){

					for( var i = 0; i < totalFormSize ; i ++){
							
						if( totalForm.items.items[i].id == ("totalForm" + gridItem.getTopToolbar().items.item(9).getValue()) ){
						
							var total=0 * 1;
							var storeItem = gridItem.getStore();
							for( var j=0; j < storeItem.getCount(); j++ ){
								var record = storeItem.getAt( j );
								var fieldName = gridItem.getColumnModel().getDataIndex(indexColumn);
								total = total + record.get(fieldName) * 1;
							}
							totalForm.items.items[i].setValue(total);
							gridItem.getTopToolbar().items.item(10).setValue(total);


						}
					}
				}

				//加總 總計
				
				//1.0先找出 此張Quotation 有少Items
				Ext.lib.Ajax.request(
					'POST',
					'quotation/config/AjaxPaperFildByPaperVer.action',
					{success: function(response){
							//parse Json data
							var groupListJsonData = Ext.util.JSON.decode(response.responseText);
							//alert(groupListJsonData.length);
							var totalFormQuoTotal = 0 * 1;
							for (var i=0; i < groupListJsonData.length ; i++){
								var groupData = groupListJsonData[i];
								if( groupData ){
									var paperGroupName =  groupData.paperGroupName;
									var paperGroupUid  =  groupData.paperGroupUid;
									var paperGroupSeq  =  groupData.paperGroupSeq;

									totalFormQuoTotal = totalFormQuoTotal * 1 + Ext.getCmp('totalForm' + paperGroupUid).getValue() * 1;
				
								}
							}
							Ext.getCmp('totalFormQuoTotal').setValue(totalFormQuoTotal);
					},failure: function(){
						Ext.Msg.alert("Error", "startQuotationItem Connection Error , Please Inform CLT IT");
					}},
					'paperVerUid=' + paperVerUid 
				);
		});

		tabs.add(gridItem).show();
		//Add Column in Grid
		var j = 0;

		for (j=0 ; j< fieldList.length; j++){
			var fieldData = fieldList[j];
			if( fieldData ){
				var field =  fieldData.field;
				var fieldSeq =  fieldData.fieldSeq;
				var fieldAttr = fieldData.fieldAttr;
				var fieldNecess = fieldData.fieldNecess;
				var fieldName = field + "|" + 'item'+ fieldSeq;
				var necessField = "*" + field;

				if(fieldAttr == "POSITIVE NUMBER" ){
					//必填
					if( fieldNecess == "YES" ){
						gridItem.addColumn({id:'item'+ fieldSeq , name:'item'+ fieldSeq, dataIndex:'item'+ fieldSeq, header:necessField, width:120, 
							editor: new fmItem.NumberField({
								   style:'direction:rtl',
								   style: 'text-align:left',
								   decimalPrecision : 4,//精確到小數點後兩位 
								   allowDecimals : true,//允許輸入小數
								   allowNegative :false,//允許輸入負數
								   allowBlank: false,
								   name : fieldName,
								   listeners: {
										blur: function(f){  
											var newValue = f.getValue();
											
											if(newValue == ""){
												var keyText = f.getName().split("|");
												Ext.Msg.alert('', "'" + keyText[0] + "'" + " " + "This field must be" , callBack(f));
												function callBack(f) {
													var keyText = f.getName().split("|");
													var record = gridItem.getSelectionModel().getSelected();//獲取被修改的行
													var indexRow = gridItem.getStore().indexOf(record);

													var indexColumn = gridItem.getColumnModel().findColumnIndex(keyText[1]);
													//gridItem.stopEditing();
													//gridItem.startEditing(indexRow , indexColumn);
												}
											}
										}//blur: function(f){
									}

							   })
							});
					}else{
						gridItem.addColumn({id:'item'+ fieldSeq , dataIndex:'item'+ fieldSeq, header:field, width:120, 
							editor: new fmItem.NumberField({
								   style:'direction:rtl',
								   style: 'text-align:left',
								   decimalPrecision : 4,//精確到小數點後兩位 
								   allowDecimals : true,//允許輸入小數
								   allowNegative :false,//允許輸入負數
								   allowBlank: true
							   })
							});
					}
				}else{
					//必填
					if( fieldNecess == "YES" ){
						gridItem.addColumn({id:'item'+ fieldSeq , dataIndex:'item'+ fieldSeq, header:necessField, width:120, 
							editor: new fmItem.TextField({
									allowBlank: false,
									name : fieldName,
									listeners: {
										blur: function(f){  
											var newValue = f.getValue();
											newValue = newValue.trim();
											if(newValue == ""){
												var keyText = f.getName().split("|");
												Ext.Msg.alert('', "'" + keyText[0] + "'" + " " + "This field must be" , callBack(f));
												function callBack(f) {
													var keyText = f.getName().split("|");
													var record = gridItem.getSelectionModel().getSelected();//獲取被修改的行
													var indexRow = gridItem.getStore().indexOf(record);

													var indexColumn = gridItem.getColumnModel().findColumnIndex(keyText[1]);
													//gridItem.stopEditing();
													//gridItem.startEditing(indexRow , indexColumn);
												}
											}
										}//blur: function(f){
									}
								})	
							});
					}else{
						gridItem.addColumn({id:'item'+ fieldSeq , dataIndex:'item'+ fieldSeq, header:field, width:120, 
							editor: new fmItem.TextField({
									allowBlank: true
								})	
							});
					}
				}//if(fieldAttr == "POSITIVE NUMBER" ){

			}//if( fieldData ){
			gridItem.getView().refresh();
		}//for (j=0 ; j< fieldList.length; j++){

	}//end function addTab

	/********************
	*	Paper Tabs
	*********************/
	var tabs = new Ext.TabPanel({
		id:'paper-Tabs',
		plain:true,
        activeTab: 0,
		resizeTabs:true, // turn on tab resizing
		minTabWidth: 180,
		tabWidth:200,
		enableTabScroll:true,
		height:screen.height - 535,
		defaults: {
			autoScroll:true
			}
	});


	/*********************************************************
	*					Layout
	*********************************************************/

	// Finally, build the main layout once all the pieces are ready.  This is also a good
	// example of putting together a full-screen BorderLayout within a Viewport.
    new Ext.Viewport({
		layout: 'border',
		title: 'Ext Layout Browser',
		forceFit: true,
		//defaults: {autoScroll: true}, 
		items: [/*{
			xtype: 'box',
			region: 'north',
			applyTo: 'header',
			height: 20
			},*/
			{layout: 'border',
	    	 id: 'layout-browser',
	         region:'center',
	         border: false,
	         split:true,
			 margins: '2 0 5 5',
	         width: 275,
	         minSize: 100,
	         maxSize: 500,
			 items: [{
					layout:'column',
					border:false,
					region:'center',
						items: [{
							columnWidth:1,
							layout: 'form',
							border:false,
							items: [quoInfoForm]
						},{
							columnWidth:.5,
							layout: 'form',
							border:false,
							items: [supplierForm,contactForm]
						},{
							columnWidth:.5,
							layout: 'form',
							border:false,
							items: [modelsForm,noteForm]
						},{
							columnWidth:1,
							layout: 'form',
							border:false,
							items: [tabs]
						}]
					
					}
				]
			},
			{id: 'layout-mainForm2',
			 region:'east',
			 width: '28%',
			 style: 'padding: 2 0 0 0',
			 items: [askPriceForm, totalForm, saveButton]
			 //border: false*
			}
		],
        renderTo: Ext.getBody()
    });

	/************************************************
	*			Save Quote Function
	************************************************/
	function saveQuote(){

		if(!checkTotalvalue()){
			return;
		}

		Ext.Msg.confirm("Waring", "確定 '儲存 ? ' ", function(button) {

			if (button == "yes") {
				saveAllTabData();
			}else {
				return;
			}

		});
	}

	function checkTotalvalue(){
		var totalFormQuoTotal = Ext.getCmp('totalFormQuoTotal').getValue() * 1;
		var totalFormQuoRealTotal = Ext.getCmp('totalFormQuoRealTotal').getValue() * 1;
		var totalFormQuoTax = Ext.getCmp('totalFormQuoTax').getValue();

		if( totalFormQuoTotal > 0 ) {
			if( totalFormQuoRealTotal <= 0){
				Ext.Msg.alert("Error", "'實際報價'  必須給值");
				return false;
			}
			if( totalFormQuoTax == "" ){
				Ext.Msg.alert("Error", "'稅率'  必須給值");
				return false;
			}
		}
		return true;
	}
	function saveAllTabData(){
		var myMask = new Ext.LoadMask(Ext.getBody(), {  
			msg : "Please wait..."  
		});
		myMask.show();

		var tabList = Ext.getCmp('paper-Tabs');
		var tabs = tabList.items.length;

		var jsonString = "";
		jsonString = "[";
		for (var i=0 ; i<tabs; i++){
			var tabGrid = tabList.getItem(i);
			
			var paperGroupSeq = i;
			var paperGroupName = tabGrid.title;
			var paperGroupUidValue = tabGrid.getTopToolbar().items.item(9).getValue();
			var paperRecordTotal = tabGrid.getTopToolbar().items.item(10).getValue();

			var jsonData = [];
			var j = 0;
			for( j=0; j < tabGrid.getStore().getCount(); j++ ){
				 var rec = tabGrid.getStore().getAt(j);
				 jsonData[j] = rec;
			}

			var jsonArray = [];
			Ext.each(jsonData, function(item) {
				jsonArray.push(item.data);
			});
			
			if ( i != tabs - 1 ){
				jsonString = jsonString + '{'+
				'\"paperGroupUid\":' + '\"' + paperGroupUidValue + '\"' + 
				',\"paperGroupSeq\":' + '\"' + paperGroupSeq + '\"' + 
				',\"paperGroupName\":' + '\"' + paperGroupName + '\"' + 
				',\"paperRecordTotal\":' + '\"' + paperRecordTotal + '\"' + 
				',\"fieldValueList\":' + Ext.encode(jsonArray)+"},";
			}else{
				jsonString = jsonString + '{'+
				'\"paperGroupUid\":' + '\"' + paperGroupUidValue + '\"' + 
				',\"paperGroupSeq\":' + '\"' + paperGroupSeq + '\"' + 
				',\"paperGroupName\":' + '\"' + paperGroupName + '\"' + 
				',\"paperRecordTotal\":' + '\"' + paperRecordTotal + '\"' + 
				',\"fieldValueList\":' + Ext.encode(jsonArray)+"}";
			}
			
		}
		jsonString = jsonString + "]";


		//alert(jsonString);
		Ext.lib.Ajax.request(
			'POST',
			'quotation/quote/QuoPaperQuoteSave.action',
			{success: function(response){
				if( response.responseText != "ERROR" ) {
					//清空 Total Form
					clearTotalFormData();
					//接收回傳值
					//清空 All Tabs Grid
					var tabList = Ext.getCmp('paper-Tabs');
					var tabs = tabList.items.length;
					var rs = Ext.util.JSON.decode(response.responseText);
					var quoteJsonData = rs[0].quoPaperGroupList;
					
					var totalFormQuoTotal = 0 * 1;

					for (var i=0 ; i<tabs; i++){
						var tabGrid = tabList.getItem(i);
						var tabGridStore = tabGrid.getStore();
						tabGridStore.removeAll();
						tabGrid.getTopToolbar().items.item(10).reset();
						var paperGroupUidValue = tabGrid.getTopToolbar().items.item(9).getValue();
						//Clear Total Form Data
						var totalFormObj = "totalForm" + paperGroupUidValue;
						Ext.getCmp(totalFormObj).reset();
						if( quoteJsonData ) {
							for(var j=0; j < quoteJsonData.length ; j++){
								if(paperGroupUidValue == quoteJsonData[j].paperGroupUid ){
									var fieldValueList = quoteJsonData[j].fieldValueList;								
									tabGridStore.loadData(fieldValueList);
									tabGrid.getView().refresh();
									tabGrid.getTopToolbar().items.item(10).setValue(quoteJsonData[j].paperRecordTotal);
									Ext.getCmp(totalFormObj).setValue(quoteJsonData[j].paperRecordTotal);

									totalFormQuoTotal = totalFormQuoTotal * 1 + quoteJsonData[j].paperRecordTotal * 1;
								}
							}
							
						}
						
					}//for (var i=0 ; i<tabs; i++){
					Ext.getCmp('totalFormQuoTotal').setValue(totalFormQuoTotal);

					Ext.getCmp('totalFormQuoRealTotal').setValue(rs[0].quoteRealTotal);
					Ext.getCmp('totalFormQuoTFTTotal').setValue(rs[0].quoteTftTotal);
					Ext.getCmp('totalFormQuoTax').setValue(rs[0].quoteTax);
					Ext.getCmp('totalFormQuoNote').setValue(rs[0].quoteNotes);

					myMask.hide();
					Ext.Msg.alert('Message', 'Update OK', function(){
					});
				}else{
					myMask.hide();
					Ext.Msg.alert("Error", "Save Error.");

				}
							
			},failure: function(){
				myMask.hide();
				Ext.Msg.alert("Error", "Connection Error , Please Inform CLT IT");

			}},
			'data=' + encodeURIComponent(jsonString) + 
			'&paperVerUid=' + paperVerUid +
			'&quoteHeaderUid=<%=quoteHeaderUid%>' + 
			'&totalFormQuoTotal=' + Ext.getCmp('totalFormQuoTotal').getValue() +
			'&totalFormQuoRealTotal=' + Ext.getCmp('totalFormQuoRealTotal').getValue() +
			'&totalFormQuoTFTTotal=' + Ext.getCmp('totalFormQuoTFTTotal').getValue()  +
			'&totalFormQuoTax=' + Ext.getCmp('totalFormQuoTax').getValue()  +
			'&totalFormQuoNote=' + Ext.getCmp('totalFormQuoNote').getValue() 
		);//Ext.lib.Ajax.request(
		myMask.destroy();
	}

	function clearTotalFormData(){

		Ext.getCmp('totalFormQuoTotal').reset();
		Ext.getCmp('totalFormQuoRealTotal').reset();
		Ext.getCmp('totalFormQuoTFTTotal').reset();
		Ext.getCmp('totalFormQuoTax').reset();
		Ext.getCmp('totalFormQuoNote').reset();

		
	}
	<%if (dateDiff < 0 ) {%>
		saveButton.disable();
		Ext.Msg.alert('Message', "超過報價單回收時間,已無法報價 ", function(){
		});
	<%}%>

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
	
</style>

</head>
<body>
<!-- <div id="header"><h3>報價單</h3></div> -->
<div id="paperItem-panel"></div>
 </body>
</html>
