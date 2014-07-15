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


<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	long t1 = System.currentTimeMillis();
	String contextPath = (String)request.getContextPath();
	// Get Current User
	SysUserTo curUser =
            (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
	
	String inquiryNum = (String)request.getAttribute("inquiryNum");
	inquiryNum = null != inquiryNum ? inquiryNum : "";


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
           width: 150,
		   hidden:true
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
		   {name: 'inquirySupplierSite'}
			

		]),
		sortInfo:{ field: "inquirySupplierCode", direction: "ASC" }
	}) ;


	inquiryStore.load();


	// create the readonly grid
    var inquiryGrid = new Ext.grid.GridPanel({
		id: 'inquiryGrid',
        ds: inquiryStore,
        cm: cm,
		sm: new Ext.grid.RowSelectionModel({
	                singleSelect: true,
	                listeners: {
	                    rowselect: function(sm, row, rec) {
							
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


	var messageForm = new  Ext.form.FormPanel({
        id: 'messageForm',
        frame: true,
		//style: 'padding: 0 0 0 0',
		//style:'font-weight:bold;text-align:left;padding-right:0px;',
        //bodyStyle: 'padding:5px',
		//bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		labelAlign: 'left',
		height:35,
		region:'north',
        items: [{
            layout:'column',
            items: [{
				columnWidth: .05,
				items: [{
					xtype: "label",
					text: '詢價單號:'
				}]
			},{
				columnWidth: .25,
				items: [{
					xtype: "textfield",
					id:'quotPaperDesc',
					name: 'quotPaperDesc',
					readOnly:true,
					width: 200,
					value:'<%=inquiryNum%>',
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{
                columnWidth:.02,
                items:[{}
                ]
            }]
        }]
    });


	//Panel
	var mainPanel = new Ext.Panel({
		title:'詢價儲存的內容',
		layout: 'border',
		bodyStyle: "background-color:#FFFFFF; border-width: 0px 0px 0px 0px;",
		style: 'padding: 0 0 0 0',
		//管理grid
		items:[messageForm, inquiryGrid]
	});
	

	/*********************************************************
	*					Layout
	*********************************************************/

	// Finally, build the main layout once all the pieces are ready.  This is also a good
	// example of putting together a full-screen BorderLayout within a Viewport.
    new Ext.Viewport({
		layout: 'fit',
		title: '詢價儲存的內容',
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

<script Language="javascript"> 


function document.oncontextmenu(){
	window.event.returnValue=false; //將滑鼠右鍵事件取消
}
</script>

</head>
<body >


<div id="header"><h3>詢價儲存的內容</h3></div>
	
 </body>
</html>
