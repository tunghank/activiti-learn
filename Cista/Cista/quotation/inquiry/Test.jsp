<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.text.DecimalFormat"%>

<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ page import ="com.clt.system.to.SysUserTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogPaperVerTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogPaperVerTo"%>
<%@ page import ="com.clt.quotation.erp.to.ErpCurrencyTo"%>
<%@ page import ="com.clt.system.to.SysFunParameterTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	long t1 = System.currentTimeMillis();
	String contextPath = (String)request.getContextPath();
	// Get Current User
	SysUserTo curUser =
            (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
	
	List<QuoCatalogTo> catalogList = (List<QuoCatalogTo>)request.getAttribute("catalogList");
	catalogList = null != catalogList ? catalogList : new ArrayList<QuoCatalogTo>();

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
	//--------------------------------------------------------------------
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

	List<SysFunParameterTo> shippedByList = (List<SysFunParameterTo>)request.getAttribute("shippedByList");
	shippedByList = null != shippedByList ? shippedByList : new ArrayList<SysFunParameterTo>();
	String comboShippedBy="";
	//準備 Select List Value
	for(int i=0;i < shippedByList.size(); i ++){
		SysFunParameterTo shippedBy = shippedByList.get(i);
		
		String fieldShowName = shippedBy.getFieldShowName();
		String fieldValue = shippedBy.getFieldValue();
		//["格式A","格式A"]
		
		if ( i != shippedByList.size() - 1 ){
			comboShippedBy = comboShippedBy + "['" + fieldValue +"','"  + fieldShowName + "'],";
		}else{
			comboShippedBy = comboShippedBy + "['" + fieldValue +"','"  + fieldShowName + "']";
		}
			
	}

	List<SysFunParameterTo> termsTransactionList = (List<SysFunParameterTo>)request.getAttribute("termsTransactionList");
	termsTransactionList = null != termsTransactionList ? termsTransactionList : new ArrayList<SysFunParameterTo>();
	String comboTermsTransaction="";
	//準備 Select List Value
	for(int i=0;i < termsTransactionList.size(); i ++){
		SysFunParameterTo termsTransaction = termsTransactionList.get(i);
		
		String fieldShowName = termsTransaction.getFieldShowName();
		String fieldValue = termsTransaction.getFieldValue();
		//["格式A","格式A"]
		
		if ( i != termsTransactionList.size() - 1 ){
			comboTermsTransaction = comboTermsTransaction + "['" + fieldValue +"','"  + fieldShowName + "'],";
		}else{
			comboTermsTransaction = comboTermsTransaction + "['" + fieldValue +"','"  + fieldShowName + "']";
		}
		System.out.println("comboTermsTransaction " + comboTermsTransaction);
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
function doInit(){



}


Ext.onReady(function(){

    Ext.QuickTips.init();

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
           width: 100
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
           width: 100
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
           width: 100
        },{
           id:'inquiryUnit',
           header: "單位",
		   tooltip:'單位',
           dataIndex: 'inquiryUnit',
           width: 100
        },{
           id:'inquiryCurrency',
           header: "幣別",
		   tooltip:'幣別',
           dataIndex: 'inquiryCurrency',
           width: 100
        },{
           id:'inquiryPaymentMethod',
           header: "交易條件",
		   tooltip:'交易條件',
           dataIndex: 'inquiryPaymentMethod',
           width: 100
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
           width: 100
        },{
           id:'quotationRecoverTime',
           header: "回收時間",
		   tooltip:'回收時間',
           dataIndex: 'quotationRecoverTime',
           width: 100
        },{
           id:'quoteStatus',
           header: "狀態",
		   tooltip:'狀態',
           dataIndex: 'quoteStatus',
           width:50
        },{
           id:'quoteNotes',
           header: "備註",
		   tooltip:'備註',
           dataIndex: 'quoteNotes',
           width:150
        }
    ]);

    // by default columns are sortable
    quoteCm.defaultSortable = true;

    // this could be inline, but we want to define the Plant record
    // type so we can add records dynamically
	var quoteData = {

	};
	// create the Data Store
    var quoteStore = new Ext.data.Store({
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
		   {name: 'quoteStatus'},
		   {name: 'quoteNotes'}

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
		sm: new Ext.grid.RowSelectionModel({
	                singleSelect: true,
	                listeners: {
	                    rowselect: function(sm, row, rec) {
							
	                    }
	                }
	            }),
        region:'center',
        frame:true,
		loadMask: true,
	    stripeRows: true,
		iconCls: 'icon-grid',
		bodyStyle:'width:100%',
		autoWidth:true,
		autoScroll:true,

        renderTo: "editVer-form"
	}) ;



  


});


</script>
<style type="text/css">


	.button_align_right {text-align:center; float:center}
	
</style>
</head>
<body onLoad="doInit()">


<!-- <div id="header"><h3>報價單</h3></div> -->
<div id="editVer-form"></div>


 </body>
</html>