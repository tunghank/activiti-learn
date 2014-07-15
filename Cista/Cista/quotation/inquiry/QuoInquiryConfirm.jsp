<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.text.DecimalFormat"%>

<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ page import ="net.sf.json.JSONArray"%>
<%@ page import ="com.clt.system.to.SysFunParameterTo"%>
<%@ page import ="com.clt.system.to.SysUserTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogPaperVerTo"%>
<%@ page import ="com.clt.quotation.erp.to.ErpCurrencyTo"%>
<%@ page import ="com.clt.quotation.inquiry.to.InquiryConfirmTo"%>
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

	String paperVerUid = (String)request.getAttribute("paperVerUid");
	paperVerUid = null != paperVerUid ? paperVerUid : "";

	String inquiryNum = (String)request.getAttribute("inquiryNum");
	inquiryNum = null != inquiryNum ? inquiryNum : "";

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
		//String paperVerUid = catalogPaperVerTo.getPaperVerUid();
		String verStartDt	= catalogPaperVerTo.getVerStartDt();
		String verEndDt	= catalogPaperVerTo.getVerEndDt();
			
		//["格式A","格式A"]
		if( catalogPaperVerTo.getPaperVerUid().equals( paperVerUid ) )  {
			comboList =  "['" + paperVerUid +"','" + catalog + "-" +  paper + " Ver " + paperVer + "']";
		}
		/*if ( i != catalogPaperVerList.size() - 1 ){
			comboList = comboList + "['" + paperVerUid +"','" + catalog + "-" +  paper + " Ver " + paperVer + "'],";
		}else{
			comboList = comboList + "['" + paperVerUid +"','" + catalog + "-" +  paper + " Ver " + paperVer + "']";
		}*/
			
	}

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
			comboTermsTransaction = comboTermsTransaction + "['" + fieldValue +"','"  + fieldValue + " - " + fieldShowName + "'],";
		}else{
			comboTermsTransaction = comboTermsTransaction + "['" + fieldValue +"','"  + fieldValue + " - " + fieldShowName + "']";
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

	List<InquiryConfirmTo> inquiryConfirmList = (List<InquiryConfirmTo>)request.getAttribute("inquiryConfirmList");
	inquiryConfirmList = null != inquiryConfirmList ? inquiryConfirmList : new ArrayList<InquiryConfirmTo>();
	JSONArray jsonObject = JSONArray.fromObject(inquiryConfirmList);
	String jsonString = jsonObject.toString();
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
	function delSelectedRow(grid, store) {

	   Ext.Msg.confirm('訊息', '確定要刪除？', function(btn){
		   if (btn == 'yes') {
			   var record = grid.getSelectionModel().getSelected();
			   store.remove(record);
			   grid.getView().refresh();
			   //清Form Data
			   clearInquiryForm();
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
		   id:'inquiryKeyPartNum',
           header: "詢價料號",
		   tooltip:'詢價料號',
           dataIndex: 'inquiryKeyPartNum',
           width: 120
        },{
           id:'inquiryModelDesc',
           header: "機種料號",
		   tooltip:'機種料號',
           dataIndex: 'inquiryModelDesc',
		   hidden:true,
           width: 200
        },{
           id:'inquiryPartNumDiffer',
           header: "主/替",
		   tooltip:'主/替',
           dataIndex: 'inquiryPartNumDiffer',
           width: 40
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
           width: 150
        },{
           id:'inquirySupplierContact',
           header: "聯絡人",
		   tooltip:'聯絡人',
           dataIndex: 'inquirySupplierContact',
           width: 80
        },{
           id:'inquirySupplierEmail',
           header: "email",
		   tooltip:'email',
           dataIndex: 'inquirySupplierEmail',
           width: 150
        },{
		   id:'inquiryQty',
		   header: "數量",
		   tooltip:'數量',
		   dataIndex: 'inquiryQty',
		   width: 50
		},{
           id:'inquiryUnit',
           header: "單位",
		   tooltip:'單位',
           dataIndex: 'inquiryUnit',
           width: 35
        },{
           id:'inquiryCurrency',
           header: "幣別",
		   tooltip:'幣別',
           dataIndex: 'inquiryCurrency',
           width:50
        },{
		   id:'inquiryPaymentMethod',
		   header: "交易條件",
		   tooltip:'交易條件',
		   dataIndex: 'inquiryPaymentMethod',
		   width: 70
		},{
           id:'inquiryDeliveryLocationDesc',
           header: "送貨地點",
		   tooltip:'送貨地點',
           dataIndex: 'inquiryDeliveryLocationDesc',
           width: 150
        },{
           id:'inquiryDeliveryLocation',
           header: "送貨地點ID",
		   tooltip:'送貨地點ID',
           dataIndex: 'inquiryDeliveryLocation',
           width: 70
        },{
           id:'inquiryShippedBy',
           header: "運送方式",
		   tooltip:'運送方式',
           dataIndex: 'inquiryShippedBy',
           width: 70
        },{
           id:'inquirySupplierPartNum',
           header: "廠商料號",
		   tooltip:'廠商料號',
           dataIndex: 'inquirySupplierPartNum',
           width: 100
        },{
		   id:'quotationRecoverTime',
           header: "報價單回收時間",
		   tooltip:'報價單回收時間',
           dataIndex: 'quotationRecoverTime',
           width: 100
        },{
		   id:'inquiryRemark',
           header: "備註",
		   tooltip:'備註',
           dataIndex: 'inquiryRemark',
           width: 100
        },{
           id:'paperVerUid',
           header: "Version ID",
           dataIndex: 'paperVerUid',
           width: 60,
		   hidden: true
        },{
           id:'paperVerStartDt',
           header: "Version Start Date",
           dataIndex: 'paperVerStartDt',
           width: 60,
		   hidden: true
        },{
           id:'paperVerEndDt',
           header: "Version End Date",
           dataIndex: 'paperVerEndDt',
           width: 60,
		   hidden: true
        },{
		   id:'inquirySupplierCode',
           header: "廠商代碼",
           dataIndex: 'inquirySupplierCode',
           width: 80,
		   hidden: true
        },{
           id:'inquirySupplierSite',
           header: "Supplier Site",
           dataIndex: 'inquirySupplierSite',
           width: 180,
		   hidden: true
        },{
           id:'inquirySupplierSiteCode',
           header: "Supplier Site Code",
           dataIndex: 'inquirySupplierSiteCode',
           width: 180,
		   hidden: true
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
		   {name: 'inquiryKeyPartNum'},
           {name: 'inquiryModelDesc'},
		   {name: 'inquiryPartNumDiffer'},
		   {name: 'inquiryPartNum'},
		   {name: 'inquiryPartNumDesc'},
           {name: 'inquirySupplierName'},
           {name: 'inquirySupplierContact'},
		   {name: 'inquirySupplierEmail'},
		   {name: 'inquiryQty'},
           {name: 'inquiryUnit'},
		   {name: 'inquiryCurrency'},
		   {name: 'inquiryPaymentMethod'},
		   {name: 'inquiryDeliveryLocationDesc'},
		   {name: 'inquiryDeliveryLocation'},
           {name: 'inquiryShippedBy'},
           {name: 'inquirySupplierPartNum'},
		   {name: 'quotationRecoverTime'},
		   {name: 'inquiryRemark'},
		   {name: 'paperVerUid'},
		   {name: 'paperVerStartDt'},
           {name: 'paperVerEndDt'},
           {name: 'inquirySupplierCode'},
		   {name: 'inquirySupplierSite'},
		   {name: 'inquirySupplierSiteCode'}
			

		]),
		sortInfo:{ field: "inquirySupplierCode", direction: "ASC" }
	}) ;


	inquiryStore.load();

	 var toolbar = new Ext.Toolbar({
		 id:'toolbar',
		 items: [  
			'-',{text:'刪除',
				  iconCls: 'remove',
				  id:'inquiryGridTbBtnDel',
				  style:"background-color:#FF9900;",
				  pressed:true,
				  handler: function(){
						delSelectedRow(Ext.getCmp('inquiryGrid'), Ext.getCmp('inquiryGrid').getStore());
				  }

				},
			'-'
		 ]  
	 }); 

	// create the readonly grid
    var inquiryGrid = new Ext.grid.GridPanel({
		id: 'inquiryGrid',
        ds: inquiryStore,
        cm: cm,
		tbar: toolbar,
		sm: new Ext.grid.RowSelectionModel({
	                singleSelect: true,
	                listeners: {
	                    rowselect: function(sm, row, rec) {
							var index = inquiryGrid.getStore().indexOf(rec);
							//Get grid rec value
							var inquiryKeyPartNum = rec.data['inquiryKeyPartNum'];
							var inquiryModelDesc = rec.data['inquiryModelDesc'];
							var inquiryPartNumDiffer = rec.data['inquiryPartNumDiffer'];
							var inquiryPartNum = rec.data['inquiryPartNum'];
							var inquiryPartNumDesc = rec.data['inquiryPartNumDesc'];
							var inquirySupplierName = rec.data['inquirySupplierName'];
							var inquirySupplierContact = rec.data['inquirySupplierContact'];
							var inquirySupplierEmail = rec.data['inquirySupplierEmail'];
							var inquiryQty = rec.data['inquiryQty'];
							var inquiryUnit = rec.data['inquiryUnit'];
							var inquiryCurrency = rec.data['inquiryCurrency'];

							var inquiryPaymentMethod = rec.data['inquiryPaymentMethod'];
							var inquiryDeliveryLocation = rec.data['inquiryDeliveryLocation'];
							var inquiryDeliveryLocationDesc = rec.data['inquiryDeliveryLocationDesc'];
							var inquiryShippedBy = rec.data['inquiryShippedBy'];
							var inquirySupplierPartNum = rec.data['inquirySupplierPartNum'];
							var quotationRecoverTime = rec.data['quotationRecoverTime'];
							var inquiryRemark = rec.data['inquiryRemark'];
							var paperVerUid = rec.data['paperVerUid'];
							var paperVerStartDt = rec.data['paperVerStartDt'];
							var paperVerEndDt = rec.data['paperVerEndDt'];
							var inquirySupplierCode = rec.data['inquirySupplierCode'];
							var inquirySupplierSite = rec.data['inquirySupplierSite'];
							var inquirySupplierSiteCode = rec.data['inquirySupplierSiteCode'];
							//Set Form
							
							var t = Date.parse(quotationRecoverTime.substr(0,10));
							var recoverDate = new Date(t);
							var recoverTime = quotationRecoverTime.substr(11,5);

							Ext.getCmp('inquiryFormModelsPartNum').setValue(inquiryModelDesc);
							Ext.getCmp('inquiryFormPartNum').setValue(inquiryPartNum);
							Ext.getCmp('inquiryFormQty').setValue(inquiryQty);
							Ext.getCmp('inquiryFormPartNumDesc').setValue(inquiryPartNumDesc);
							Ext.getCmp('inquiryFormUnit').setValue(inquiryUnit);
							Ext.getCmp('inquiryFormSupplierCode').setValue(inquirySupplierCode);
							Ext.getCmp('inquiryFormSupplierName').setValue(inquirySupplierName);
							Ext.getCmp('inquiryFormSupplierSite').setValue(inquirySupplierSite);
							Ext.getCmp('inquiryFormSupplierSiteCode').setValue(inquirySupplierSiteCode);
							Ext.getCmp('inquiryFormContact').setValue(inquirySupplierContact);
							Ext.getCmp('inquiryFormContactEmail').setValue(inquirySupplierEmail);
							Ext.getCmp('inquiryFormSupplierPartNum').setValue(inquirySupplierPartNum);
							Ext.getCmp('inquiryFormWaers').setValue(inquiryCurrency);
							Ext.getCmp('inquiryFormShippedBy').setValue(inquiryShippedBy);
							Ext.getCmp('inquiryFormTermsTransaction').setValue(inquiryPaymentMethod);
							Ext.getCmp('inquiryFormDeliveryLocation').setValue(inquiryDeliveryLocation);
							Ext.getCmp('inquiryFormDeliveryLocationDesc').setValue(inquiryDeliveryLocationDesc);
							Ext.getCmp('inquiryFormQuotPaper').setValue(paperVerUid);
							Ext.getCmp('inquiryFormRecoverTime').setValue(recoverTime);
							Ext.getCmp('inquiryFormRecoverDate').setValue(recoverDate);

							Ext.getCmp('inquiryFormRemark').setValue(inquiryRemark);

							Ext.getCmp('inquiryGridInx').setValue(index);

							//Btn Diable
							Ext.getCmp('inquiryFormBtnEdit').setDisabled(false);
							Ext.getCmp('inquiryFormBtnAdd').setDisabled(true);
							
	                    }
	                }
	            }),
        region:'center',
        title:'詢價內容',
        frame:true,
		loadMask: true,
	    stripeRows: true,
		iconCls: 'icon-grid',
		bodyStyle:'width:100%',
		autoWidth:true,
		autoScroll:true
	}) ;

	//Form
	var inquiryForm = new Ext.form.FieldSet({
        id: 'inquiryForm',
		title: '資料編輯區',
		style: 'padding: 0 0 0 0',
		bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		labelAlign: 'left',
		height:305,
		region:'north',
        items: [{//第1-1行
			layout: "column",
			border: false,
			frame: true,
			items: [{
				columnWidth: .001,
				items: [{
					xtype: "label",
					hidden:true,
					text: '機種料號:'
				}]
			},{
				columnWidth: .001,
				items: [{
					xtype: "textfield",
					id:'inquiryFormModelsPartNum',
					name: 'inquiryFormModelsPartNum',
					readOnly:true,
					hidden:true,
					//width: 300,
					style:"background:#EEEEEE;color:#BC6618;"
				}]
			},{
				columnWidth: .01,
				height:30,
				items: [{}]
			},{
				columnWidth: .04,
				items: [{
					xtype: "label",
					text: '料號:'
				}]
			},{
				columnWidth: .15,
				items: [{
					xtype: "textfield",
					id:'inquiryFormPartNum',
					name: 'inquiryFormPartNum',
					width: 200
				}]
			},{
				columnWidth: .05,
				items: [{
					xtype: 'button',
					id:'inquiryFormBtnPartNum',
					text: '...',
					listeners:{
						"click":function(){
							document.getElementById("hiddenPartNumBtn").click();
						}
					}
				}]
			},{
				columnWidth: .05,
				items: [{
					xtype: "label",
					text: '數量:'
				}]
			},{
				columnWidth: .1,
				items: [{
					xtype : 'numberfield',
					id:'inquiryFormQty',
					name: 'inquiryFormQty',
					style:'direction:rtl',
					style: 'text-align:left',
					allowDecimals : false,//允許輸入小數 
					allowNegative :false//允許輸入負數  
				}]
			},{
				columnWidth: .58,
				height:30,
				items: [{}]
			},{//第1-2行
				columnWidth: .05,
				items: [{
					xtype: "label",
					text: '料號摘要:'
				}]
			},{
				columnWidth: .5,
				items: [{
					xtype: "textfield",
					id:'inquiryFormPartNumDesc',
					name: 'inquiryFormPartNumDesc',
					readOnly:true,
					width: 500,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{
				columnWidth: .05,
				items: [{
					xtype: "label",
					text: '單位:'
				}]
			},{
				columnWidth: .1,
				items: [{
					xtype : 'textfield',
					id:'inquiryFormUnit',
					name: 'inquiryFormUnit',
					readOnly:true,
					style:"background:#EEEEEE;color:#0000B7;"
				}]
			},{
				columnWidth: .3,
				height:30,
				items: [{}]
			},{//空白列
				columnWidth: 1,
				height:10,
				items: [{}]
			},{//第2-1行
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
					id:'inquiryFormSupplierCode',
					name: 'inquiryFormSupplierCode',
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
					id:'inquiryFormBtnSupplierCode',
					text: '...',
					listeners:{
						"click":function(){
							document.getElementById("hiddenVendorBtn").click();
						}
					}
				}]
			},{//空白行
				columnWidth: .05,
				height:30,
				items: [{}]
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
					id:'inquiryFormSupplierName',
					name: 'inquiryFormSupplierName',
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
					id:'inquiryFormSupplierSite',
					name: 'inquiryFormSupplierSite',
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
					id:'inquiryFormSupplierSiteCode',
					name: 'inquiryFormSupplierSiteCode',
					allowBlank:false,
					readOnly:true,
					style:"background:#EEEEEE;color:#BC6618;",
					hidden:true,
					anchor:'90%'
				}]
			},{//第2-2行
				columnWidth: .05,
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
					id:'inquiryFormContact',
					name: 'inquiryFormContact',
					allowBlank:false,
					width: 200,
					anchor:'90%'
				}]
			},{//空白行
				columnWidth: .05,
				height:30,
				items: [{}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: 'email:',
					anchor:'90%'
				}]
			},{
				columnWidth: .3,
				height:30,
				items: [{
					xtype: "textfield",
					id:'inquiryFormContactEmail',
					name: 'inquiryFormContactEmail',
					allowBlank:false,
					width: 300,
					anchor:'90%'
				}]
			},{
				columnWidth: .07,
				height:30,
				items: [{
					xtype: "label",
					text: '廠商料號:',
					anchor:'90%'
				}]
			},{
				columnWidth: .28,
				height:30,
				items: [{
					xtype: "textfield",
					id:'inquiryFormSupplierPartNum',
					name: 'inquiryFormSupplierPartNum',
					allowBlank:false,
					width: 300,
					anchor:'90%'
				}]
			},{//空白列
				columnWidth: 1,
				height:10,
				items: [{}]
			},{//第3-1行
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '幣別:',
					anchor:'90%'
				}]
			},{
				columnWidth: .15,
				height:30,
				items: [{
					xtype:"combo",
					id:'inquiryFormWaers',
					name: 'inquiryFormWaers',
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
					width: 150,
					anchor:'90%'
				}]
			},{//空白行
				columnWidth: .1,
				height:30,
				items: [{}]
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
					id:'inquiryFormShippedBy',
					name: 'inquiryFormShippedBy',
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
			},{//空白行
				columnWidth: .15,
				height:30,
				items: [{}]
			},{
				columnWidth: .07,
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
					id:'inquiryFormTermsTransaction',
					name: 'inquiryFormTermsTransaction',
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
			},{//空白行
				columnWidth: .12,
				height:30,
				items: [{}]
			},{//第3-2行
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '送貨地點:',
					anchor:'90%'
				}]
			},{
				columnWidth: 0.001,
				height:30,
				items: [{
					xtype: "textfield",
					id:'inquiryFormDeliveryLocation',
					name: 'inquiryFormDeliveryLocation',
					allowBlank:false,
					readOnly:true,
					width: 150,
					style:"background:#EEEEEE;color:#BC6618;",
					anchor:'90%',
					hidden:true
				}]
			},{
				columnWidth: .15,
				height:30,
				items: [{
					xtype: "textfield",
					id:'inquiryFormDeliveryLocationDesc',
					name: 'inquiryFormDeliveryLocationDesc',
					allowBlank:false,
					readOnly:true,
					width: 180,
					style:"background:#EEEEEE;color:#BC6618;",
					anchor:'90%'
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype: 'button',
					id:'inquiryFormBtnDeliveryLocation',
					text: '...',
					listeners:{
						"click":function(){
							document.getElementById("hiddenDeliveryLocationBtn").click();
						}
					}
				}]
			},{//空白行
				columnWidth: .04,
				height:30,
				items: [{}]
			},{
				columnWidth: .06,
				items: [{
					xtype: "label",
					text: '報價單格式:'
				}]
			},{
				columnWidth: .25,
				items: [{
					xtype:"combo",
					id:'inquiryFormQuotPaper',
					name: 'inquiryFormQuotPaper',
					store:new Ext.data.SimpleStore({
					fields: ['value', 'text'],
						data: [<%=comboList%>
						]
					}),
					displayField: 'text',
					valueField: 'value',
					mode: 'local',
					editable: false,
					triggerAction: 'all',
					width: 250
				}]
			},{//空白行
				columnWidth: .03,
				height:30,
				items: [{}]
			},{
				columnWidth: .09,
				height:30,
				items: [{
					xtype: "label",
					text: '報價單回收時間:',
					anchor:'90%'
				}]
			},{
				columnWidth: .09,
				height:30,
				items: [{
					xtype: "datefield",
					id:'inquiryFormRecoverDate',
					name: 'inquiryFormRecoverDate',
					format:'Y/m/d',
					readOnly : true,
					allowBlank:false,
					minValue : new Date(),
					anchor:'90%'
				}]
			},{
				columnWidth: .11,
				height:30,
				items: [{
					xtype: "timefield",
					id:'inquiryFormRecoverTime',
					name: 'inquiryFormRecoverTime',
					width : 100,
				    increment  : 30,
				    format     : 'H:i',
					editable :false,
					allowBlank:false,
					anchor:'90%'
				}]
			},{//空白行
				columnWidth: .08,
				height:30,
				items: [{ }]
			},{//第3-3行
				columnWidth: .05,
				items: [{
					xtype: "label",
					text: '備註:'
				}]
			},{
				columnWidth: .6,
				items: [{
					xtype:"textarea",
					id:'inquiryFormRemark',
					name: 'inquiryFormRemark',
					width : 600,
					height : 80,
					multiline: true
				}]
			},{//空白行
				columnWidth: .1,
				height:30,
				items: [{ }]
			},{
				columnWidth: .05,
				bodyStyle: 'padding-right:0px;padding-left:0px;padding-top:60px;padding-bottom:0px;',
				items: [{
					xtype: 'button',
					id:'inquiryFormBtnEdit',
					text: '修改',
					disabled :true,
					listeners:{
						"click":function(){
							saveModifyInquiryItem(Ext.getCmp('inquiryGrid'));
						}
					}
				}]
			},{//空白行
				columnWidth: .05,
				height:30,
				items: [{ }]
			},{
				columnWidth: .05,
				bodyStyle: 'padding-right:0px;padding-left:0px;padding-top:60px;padding-bottom:0px;',
				items: [{
					xtype: 'button',
					id:'inquiryFormBtnAdd',
					
					text: '新增',
					listeners:{
						"click":function(){
							var inquiryFormQuotPaper = Ext.getCmp('inquiryFormQuotPaper').getValue();
							var inquiryFormPartNum = Ext.getCmp('inquiryFormPartNum').getValue();

							Ext.lib.Ajax.request(
								'POST',
								'quotation/inquiry/AjaxInquiryConfirmAddQuery.action',
								{success: function(response){
									var res = response.responseText.split("|");
									var partNumDiffer = res[1];
									var verStartDt = res[2];
									var verEndDt = res[3];
									addInquiryItem(Ext.getCmp('inquiryGrid'), partNumDiffer, verStartDt, verEndDt);

								},failure: function(){
									Ext.Msg.alert("Error", "QuoInquiryPartNum Connection Error , Please Inform CLT IT");
								}},
								'partNum=' + inquiryFormPartNum + 
								'&paperVerUid=' + inquiryFormQuotPaper
							);
							
						}
					}
				}]
			},{//空白行
				columnWidth: .05,
				height:30,
				items: [{ }]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype : 'textfield',
					id:'inquiryGridInx',
					name: 'inquiryGridInx',
					readOnly:true,
					style:"background:#EEEEEE;color:#0000B7;",
					hidden: true
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
		labelAlign: 'left',
		height:35,
		region:'south',
        items: [{
            layout:'column',
            items: [{
                columnWidth:.5,
                items:[{
						xtype: "textarea",
						id:'inquiryGridData',
						name: 'inquiryGridData',
						allowBlank:false,
						readOnly:true,
						width : 600,
						height : 80,
						multiline: true,
						hidden: true
						//anchor:'90%'
				}]
            },{
                columnWidth:.12,
                items:[{
						xtype: "textfield",
						id:'inquiryStatus',
						name: 'inquiryStatus',
						allowBlank:false,
						readOnly:true,
						hidden: true
						//anchor:'90%'
				}]
            },{
                columnWidth:.13,
                items:[{
						xtype: "textfield",
						id:'inquiryNum',
						name: 'inquiryNum',
						allowBlank:false,
						readOnly:true,
						value:'<%=inquiryNum%>',
						hidden: true
						//anchor:'90%'
				}]
            },{
                columnWidth:.1,
                items:[{
						xtype: 'button',
						id:'nextFormTemporaryBtn',
						text: '暫存詢價單',
						listeners:{
							"click":function(){
									//Temporary
									Ext.getCmp('inquiryStatus').setValue("T");
									saveInquiry(Ext.getCmp('inquiryGrid'), Ext.getCmp('nextFormTemporaryBtn').text);
								}
						}
					}
                ]
            },{
                columnWidth:.1,
                items:[{
						xtype: 'button',
						id:'nextFormSaveBtn',
						text: '發出詢價單',
						listeners:{
							"click":function(){
									//Finish
									Ext.getCmp('inquiryStatus').setValue("F");
									saveInquiry(Ext.getCmp('inquiryGrid'), Ext.getCmp('nextFormSaveBtn').text);
								}
						}
					}
                ]
            },{
                columnWidth:.05,
                items:[{
					xtype: 'button',
					text: '取消'}
                ]
            }]
        }]
    });



	//Panel
	var mainPanel = new Ext.Panel({
		title:'確認詢價內容',
		layout: 'border',
		bodyStyle: "background-color:#FFFFFF; border-width: 0px 0px 0px 0px;",
		style: 'padding: 0 0 0 0',
		//管理grid
		items:[inquiryForm, inquiryGrid, nextForm]
	});
	

	/*********************************************************
	*					Layout
	*********************************************************/

	// Finally, build the main layout once all the pieces are ready.  This is also a good
	// example of putting together a full-screen BorderLayout within a Viewport.
    new Ext.Viewport({
		layout: 'fit',
		title: '確認詢價內容',
		forceFit: true,
		//defaults: {autoScroll: true}, 
		items: [mainPanel],
        renderTo: Ext.getBody()
    });

	//Data Load to Grid.
	var inquiryJsonData = Ext.util.JSON.decode('<%=jsonString%>');
	var inquiryStore = inquiryGrid.getStore();
	inquiryStore.removeAll();
	inquiryStore.loadData(inquiryJsonData);
	inquiryGrid.getView().refresh();

});


function saveModifyInquiryItem(grid){
	//Check value
	if(!checkInquiryForm()){
		return;
	}
	

	var index = Ext.getCmp('inquiryGridInx').getValue();

	var rec = grid.store.getAt(index);

	var inquiryFormModelsPartNum = Ext.getCmp('inquiryFormModelsPartNum').getValue();
	var inquiryFormPartNum = Ext.getCmp('inquiryFormPartNum').getValue();
	var inquiryFormQty = Ext.getCmp('inquiryFormQty').getValue();
	var inquiryFormPartNumDesc = Ext.getCmp('inquiryFormPartNumDesc').getValue();
	var inquiryFormUnit = Ext.getCmp('inquiryFormUnit').getValue();
	var inquiryFormSupplierCode = Ext.getCmp('inquiryFormSupplierCode').getValue();
	var inquiryFormSupplierName = Ext.getCmp('inquiryFormSupplierName').getValue();
	var inquiryFormSupplierSite = Ext.getCmp('inquiryFormSupplierSite').getValue();
	var inquiryFormSupplierSiteCode = Ext.getCmp('inquiryFormSupplierSiteCode').getValue();
	var inquiryFormContact = Ext.getCmp('inquiryFormContact').getValue();
	var inquiryFormContactEmail = Ext.getCmp('inquiryFormContactEmail').getValue();
	var inquiryFormSupplierPartNum = Ext.getCmp('inquiryFormSupplierPartNum').getValue();
	var inquiryFormWaers = Ext.getCmp('inquiryFormWaers').getValue();
	var inquiryFormShippedBy = Ext.getCmp('inquiryFormShippedBy').getValue();
	var inquiryFormTermsTransaction = Ext.getCmp('inquiryFormTermsTransaction').getValue();
	var inquiryFormDeliveryLocation = Ext.getCmp('inquiryFormDeliveryLocation').getValue();
	var inquiryFormDeliveryLocationDesc = Ext.getCmp('inquiryFormDeliveryLocationDesc').getValue();
	var inquiryFormQuotPaper = Ext.getCmp('inquiryFormQuotPaper').getValue();
	var inquiryFormRecoverTime = Ext.getCmp('inquiryFormRecoverTime').getValue();
	var inquiryFormRecoverDate = Ext.getCmp('inquiryFormRecoverDate').getValue().format('yyyy/MM/dd');
	var inquiryFormRemark = Ext.getCmp('inquiryFormRemark').getValue();



	rec.data['inquiryKeyPartNum'] = inquiryFormPartNum;
	//rec.data['inquiryModelDesc'] = inquiryFormModelsPartNum;
	rec.data['inquiryPartNum'] = inquiryFormPartNum;
	rec.data['inquiryPartNumDesc']=inquiryFormPartNumDesc;
	rec.data['inquirySupplierName']=inquiryFormSupplierName;
	rec.data['inquirySupplierContact']=inquiryFormContact;
	rec.data['inquirySupplierEmail']=inquiryFormContactEmail;
	rec.data['inquiryQty']=inquiryFormQty;
	rec.data['inquiryUnit']=inquiryFormUnit;
	rec.data['inquiryCurrency']=inquiryFormWaers;

	rec.data['inquiryPaymentMethod']=inquiryFormTermsTransaction;
	rec.data['inquiryDeliveryLocation']=inquiryFormDeliveryLocation;
	rec.data['inquiryDeliveryLocationDesc']=inquiryFormDeliveryLocationDesc;
	rec.data['inquiryShippedBy']=inquiryFormShippedBy;
	rec.data['inquirySupplierPartNum']=inquiryFormSupplierPartNum;
	rec.data['quotationRecoverTime']=inquiryFormRecoverDate + " " + inquiryFormRecoverTime;
	rec.data['inquiryRemark'] = inquiryFormRemark;
	rec.data['paperVerUid'] = inquiryFormQuotPaper;

	rec.data['inquirySupplierCode']=inquiryFormSupplierCode;
	rec.data['inquirySupplierSite']=inquiryFormSupplierSite;
	rec.data['inquirySupplierSiteCode']=inquiryFormSupplierSiteCode;

	grid.getSelectionModel().clearSelections();
	grid.getView().refresh();
	clearInquiryForm();
	//Btn Diable
	Ext.getCmp('inquiryFormBtnEdit').setDisabled(true);
	Ext.getCmp('inquiryFormBtnAdd').setDisabled(false);
}

function addInquiryItem(grid, partNumDiffer, verStartDt, verEndDt){
	//Check value
	if(!checkInquiryForm()){
		return;
	}

	//Get Form Data
	var inquiryFormModelsPartNum = Ext.getCmp('inquiryFormModelsPartNum').getValue();
	var inquiryFormPartNum = Ext.getCmp('inquiryFormPartNum').getValue();
	var inquiryFormQty = Ext.getCmp('inquiryFormQty').getValue();
	var inquiryFormPartNumDesc = Ext.getCmp('inquiryFormPartNumDesc').getValue();
	var inquiryFormUnit = Ext.getCmp('inquiryFormUnit').getValue();
	var inquiryFormSupplierCode = Ext.getCmp('inquiryFormSupplierCode').getValue();
	var inquiryFormSupplierName = Ext.getCmp('inquiryFormSupplierName').getValue();
	var inquiryFormSupplierSite = Ext.getCmp('inquiryFormSupplierSite').getValue();
	var inquiryFormSupplierSiteCode = Ext.getCmp('inquiryFormSupplierSiteCode').getValue();
	var inquiryFormContact = Ext.getCmp('inquiryFormContact').getValue();
	var inquiryFormContactEmail = Ext.getCmp('inquiryFormContactEmail').getValue();
	var inquiryFormSupplierPartNum = Ext.getCmp('inquiryFormSupplierPartNum').getValue();
	var inquiryFormWaers = Ext.getCmp('inquiryFormWaers').getValue();
	var inquiryFormShippedBy = Ext.getCmp('inquiryFormShippedBy').getValue();
	var inquiryFormTermsTransaction = Ext.getCmp('inquiryFormTermsTransaction').getValue();
	var inquiryFormDeliveryLocation = Ext.getCmp('inquiryFormDeliveryLocation').getValue();
	var inquiryFormDeliveryLocationDesc = Ext.getCmp('inquiryFormDeliveryLocationDesc').getValue();
	var inquiryFormQuotPaper = Ext.getCmp('inquiryFormQuotPaper').getValue();
	var inquiryFormRecoverTime = Ext.getCmp('inquiryFormRecoverTime').getValue();
	var inquiryFormRecoverDate = Ext.getCmp('inquiryFormRecoverDate').getValue().format('yyyy/MM/dd');
	var inquiryFormRemark = Ext.getCmp('inquiryFormRemark').getValue();

	//Insert Grid Record
	var rec = new Ext.data.Record();

	rec.data['inquiryKeyPartNum'] = inquiryFormPartNum;
	//rec.data['inquiryModelDesc'] = inquiryFormModelsPartNum;
	rec.data['inquiryPartNum'] = inquiryFormPartNum;
	rec.data['inquiryPartNumDesc']=inquiryFormPartNumDesc;
	rec.data['inquirySupplierName']=inquiryFormSupplierName;
	rec.data['inquirySupplierContact']=inquiryFormContact;
	rec.data['inquirySupplierEmail']=inquiryFormContactEmail;
	rec.data['inquiryQty']=inquiryFormQty;
	rec.data['inquiryUnit']=inquiryFormUnit;
	rec.data['inquiryCurrency']=inquiryFormWaers;

	rec.data['inquiryPaymentMethod']=inquiryFormTermsTransaction;
	rec.data['inquiryDeliveryLocation']=inquiryFormDeliveryLocation;
	rec.data['inquiryDeliveryLocationDesc']=inquiryFormDeliveryLocationDesc;
	rec.data['inquiryShippedBy']=inquiryFormShippedBy;
	rec.data['inquirySupplierPartNum']=inquiryFormSupplierPartNum;
	rec.data['quotationRecoverTime']=inquiryFormRecoverDate + " " + inquiryFormRecoverTime;
	rec.data['inquiryRemark'] = inquiryFormRemark;
	rec.data['paperVerUid'] = inquiryFormQuotPaper;

	rec.data['inquirySupplierCode']=inquiryFormSupplierCode;
	rec.data['inquirySupplierSite']=inquiryFormSupplierSite;
	rec.data['inquirySupplierSiteCode']=inquiryFormSupplierSiteCode;

	rec.data['inquiryPartNumDiffer'] = partNumDiffer;
	rec.data['paperVerStartDt']=verStartDt;
	rec.data['paperVerEndDt']=verEndDt;

	grid.stopEditing();
	var count = grid.getStore().getCount();
						
	grid.getStore().insert(count, rec);

	grid.getSelectionModel().clearSelections();
	grid.getView().refresh();
	clearInquiryForm();
}

function clearInquiryForm(){
	//Clear Form 參數
	Ext.getCmp('inquiryFormModelsPartNum').reset();
	Ext.getCmp('inquiryFormPartNum').reset();
	Ext.getCmp('inquiryFormQty').reset();
	Ext.getCmp('inquiryFormPartNumDesc').reset();
	Ext.getCmp('inquiryFormUnit').reset();
	Ext.getCmp('inquiryFormSupplierCode').reset();
	Ext.getCmp('inquiryFormSupplierName').reset();
	Ext.getCmp('inquiryFormSupplierSite').reset();
	Ext.getCmp('inquiryFormSupplierSiteCode').reset();
	Ext.getCmp('inquiryFormContact').reset();
	Ext.getCmp('inquiryFormContactEmail').reset();
	Ext.getCmp('inquiryFormSupplierPartNum').reset();
	Ext.getCmp('inquiryFormWaers').reset();
	Ext.getCmp('inquiryFormShippedBy').reset();
	Ext.getCmp('inquiryFormTermsTransaction').reset();
	Ext.getCmp('inquiryFormDeliveryLocation').reset();
	Ext.getCmp('inquiryFormDeliveryLocationDesc').reset();
	Ext.getCmp('inquiryFormQuotPaper').reset();
	Ext.getCmp('inquiryFormRecoverTime').reset();
	Ext.getCmp('inquiryFormRecoverDate').reset();
	Ext.getCmp('inquiryFormRemark').reset();

	Ext.getCmp('inquiryGridInx').reset();
}

function saveInquiry(grid, btnText){

	var inquiryGridStore = grid.getStore();

	if(inquiryGridStore.getCount() == 0 ) {
		Ext.Msg.alert("Waring", "No Any Data");
		return;
	}
	var jsonData = [];
	 
	for( i=0; i < inquiryGridStore.getCount(); i ++ ){
		var rec = inquiryGridStore.getAt( i );
		jsonData[i] = rec;
	}


	var jsonArray = [];
	Ext.each(jsonData, function(item) {
		jsonArray.push(item.data);
	});

	Ext.Msg.confirm("Waring", "確定 '" + btnText + "' 嗎? ", function(button) {

		if (button == "yes") {
			 
			 Ext.getCmp('inquiryGridData').setValue(Ext.encode(jsonArray));

			 //只用指定TextField的id或者name屬性，服務器端Form中就能取到表單的數據
			 //如果同時指定了id和name，那麼name屬性將作為服務器端Form取表單數據的Key
			 var nextForm = Ext.getCmp('nextForm').getForm().getEl().dom;
			 nextForm.action = 'quotation/inquiry/SaveInquiry.action'
			 //指定為GET方式時,url中指定的參數將失效，表單項轉換成url中的key=value傳遞給服務端
			 //例如這裡指定為GET的話,url為:submit.aspx?param2=你輸入的值
			 nextForm.method = 'POST';//GET、POST
			 nextForm.submit();

		}else {
			return;
		}

	});
}

function checkInquiryForm(){

	//Check 料號 value
	var inquiryFormPartNum = Ext.getCmp('inquiryFormPartNum').getValue()
	if(inquiryFormPartNum == ""){
		Ext.MessageBox.show({
			title:'MESSAGE',
			msg: " 必需選擇 詢價料號.. ",
			icon: Ext.MessageBox.ERROR
		});
		return false;
	}

	//Check QTY value
	/*var inquiryFormQty = Ext.getCmp('inquiryFormQty').getValue() + "";
	if( inquiryFormQty != "" ){
		if ( inquiryFormQty.isNumber() ){
			if(!inquiryFormQty.isPositiveDecimal(8,8) ){
				Ext.MessageBox.show({
					title:'MESSAGE',
					msg: "數量 必需是正數 ",
					icon: Ext.MessageBox.ERROR
				});
				return false;
			}
		}else{
			Ext.MessageBox.show({
				title:'MESSAGE',
				msg: "數量 必需是正數!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
		}
	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '數量' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}*/

	//報價單格式
	var inquiryFormQuotPaper = Ext.getCmp('inquiryFormQuotPaper').getValue() + "";
	if( inquiryFormQuotPaper != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '報價單格式' 必需選擇!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}



	//Check 廠商代碼 value
	var inquiryFormSupplierCode = Ext.getCmp('inquiryFormSupplierCode').getValue();
	if( inquiryFormSupplierCode != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '廠商代碼' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check 聯絡人 value
	var inquiryFormContact = Ext.getCmp('inquiryFormContact').getValue();
	if( inquiryFormContact != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '聯絡人' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check email value
	var inquiryFormContactEmail = Ext.getCmp('inquiryFormContactEmail').getValue();
	if( inquiryFormContactEmail != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " 'email' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check 幣別 value
	var inquiryFormWaers = Ext.getCmp('inquiryFormWaers').getValue();
	if( inquiryFormWaers != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '幣別' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check 運送方式 value
	var inquiryFormShippedBy = Ext.getCmp('inquiryFormShippedBy').getValue();
	if( inquiryFormShippedBy != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '運送方式' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check 交易條件 value
	var inquiryFormTermsTransaction = Ext.getCmp('inquiryFormTermsTransaction').getValue();
	if( inquiryFormTermsTransaction != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '交易條件' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check 送貨地點 value
	var inquiryFormDeliveryLocation = Ext.getCmp('inquiryFormDeliveryLocation').getValue();
	if( inquiryFormDeliveryLocation != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '送貨地點' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check email value
	var inquiryFormContactEmail = Ext.getCmp('inquiryFormContactEmail').getValue();
	inquiryFormContactEmail = inquiryFormContactEmail.trim();
	if( inquiryFormContactEmail != "" ){
		if ( !inquiryFormContactEmail.isValidEmail() ){
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

	//Check 報價單回收時間 value
	var inquiryFormRecoverDate = Ext.getCmp('inquiryFormRecoverDate').getValue();
	if( inquiryFormRecoverDate != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '報價單回收時間 Date' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check 報價單回收時間 value
	var inquiryFormRecoverTime = Ext.getCmp('inquiryFormRecoverTime').getValue();
	if( inquiryFormRecoverTime != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '報價單回收時間 Time' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	return true;
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
<script>
if(window.history.forward(1) != null)
	window.history.forward(1);
</script>
</head>
<body onunload="if(history.length>0)history.go(+1)">

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
			Ext.getCmp('inquiryFormSupplierCode').setValue(value[0]["VENDOR.VENDOR_ID VENDOR_ID"]);
			Ext.getCmp('inquiryFormSupplierName').setValue(value[0]["VENDOR.VENDOR_NAME VENDOR_NAME"]);
			Ext.getCmp('inquiryFormSupplierSite').setValue(value[0]["SITE.VENDOR_SITE_CODE VENDOR_SITE"]);
			Ext.getCmp('inquiryFormSupplierSiteCode').setValue(value[0]["SITE.VENDOR_SITE_ID VENDOR_SITE_ID"]);
			Ext.getCmp('inquiryFormContact').setValue(value[0]["CONT.CONTACT CONTACT"]);
			
			Ext.getCmp('inquiryFormContactEmail').setValue(value[0]["CONT.EMAIL EMAIL"]);
			
			Ext.getCmp('inquiryFormWaers').setValue(value[0]["VENDOR.PAYMENT_CURRENCY_CODE PAYMENT_CURRENCY"]);
			//Ext.getCmp('inquiryFormTermsTransaction').setValue(value[0]["VENDOR.PAYMENT_METHOD_LOOKUP_CODE PAYMENT_METHOD"]);
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
			Ext.getCmp('inquiryFormPartNum').setValue(value[0]["SEGMENT1"]);
			Ext.getCmp('inquiryFormPartNumDesc').setValue(value[0]["DESCRIPTION"]);
			//Ext.getCmp('inquiryFormModelsPartNum').setValue(value[0]["ELEMENT_VALUE"]);
			Ext.getCmp('inquiryFormUnit').setValue(value[0]["PRIMARY_UOM_CODE"]);
			
		}
</script>

<input type="text" id = "hiddenDeliveryLocation" name="hiddenDeliveryLocation" value='' />
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
			Ext.getCmp('inquiryFormDeliveryLocation').setValue(value[0]["LOCATION_ID"]);
			Ext.getCmp('inquiryFormDeliveryLocationDesc').setValue(value[0]["LOCATION_CODE"]);

		}



		
</script>

</div>
<div id="header"><h3>確認詢價內容</h3></div>
	
 </body>
</html>
