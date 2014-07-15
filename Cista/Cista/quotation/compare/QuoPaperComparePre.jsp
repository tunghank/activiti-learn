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
		   width: 50
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
           width: 150
        },{
           id:'inquiryShippedBy',
           header: "運送方式",
		   tooltip:'運送方式',
           dataIndex: 'inquiryShippedBy',
           width: 50
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
		   {name: 'inquiryNum'},
		   {name: 'inquiryPartNumDiffer'},
		   {name: 'inquiryPartNum'},
		   {name: 'inquiryPartNumDesc'},
           {name: 'inquirySupplierName'},
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
		sortInfo:{ field: "inquiryNum", direction: "ASC" },
		groupField:'inquiryNum'
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
		view: new Ext.grid.GroupingView({
            showGroupName: false,
            enableNoGroups:false, // REQUIRED!
            hideGroupedColumn: true
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
	var queryInquiryForm = new Ext.form.FieldSet({
        id: 'queryInquiryForm',
		title: '詢價資料查詢',
		style: 'padding: 10 0 0 0',
		bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		labelAlign: 'left',
		height:70,
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
				columnWidth: .05,
				height:30,
				items: [{}]
			},{
				columnWidth: .05,
				height:30,
				bodyStyle: 'padding-right:0px;padding-left:0px;padding-top:1px;padding-bottom:0px;',
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
			}]
		}]
    });


	var nextForm = new  Ext.form.FormPanel({
        id: 'nextForm',
        frame: true,
		style: 'padding: 0 0 0 0',
		headers: {'Content-Type':'multipart/form-data; charset=UTF-8'},
		//style:'font-weight:bold;text-align:left;padding-right:0px;',
        //bodyStyle: 'padding:5px',
		bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		labelAlign: 'left',
		height:35,
		region:'south',
        items: [{
            layout:'column',
            items: [{
                columnWidth:.15,
                layout: 'form',
                items:[{
						xtype: "textfield",
						id:'nextFormInquiryNum',
						name: 'nextFormInquiryNum',
						hideLabel : true,
						readOnly:true,
						hidden: true
                }]
            },{
                columnWidth:.1,
                layout: 'form',
                items:[{
						xtype: "textfield",
						id:'nextFormPaperVerUid',
						name: 'nextFormPaperVerUid',
						hideLabel : true,
						readOnly:true,
						hidden: true
                }]
            },{
                columnWidth:.3,
                layout: 'form',
                defaultType: 'textfield',
                items:[
                ]
            },{
                columnWidth:.3,
                layout: 'form',
                defaultType: 'textfield',
                items:[
                ]
            },{
                columnWidth:.15,
                layout: 'column',
				bodyStyle: 'padding-top: 0px',
				items: [{
					columnWidth:.5,
					items:[{
							xtype: 'button',
							text: '下一步',
							listeners:{
								"click":function(){
									nextCompareQuotation(Ext.getCmp('partNumGrid'));
								}
							}
						}
					]
				}]
			}]
        }]
    });

	//Panel
	var mainPanel = new Ext.Panel({
		title:'Query 詢價資料',
		layout: 'border',
		bodyStyle: "background-color:#FFFFFF; border-width: 0px 0px 0px 0px;",
		style: 'padding: 0 0 0 0',
		//管理grid
		items:[queryInquiryForm , inquiryGrid, nextForm]
	});
	

	/*********************************************************
	*					Layout
	*********************************************************/

	// Finally, build the main layout once all the pieces are ready.  This is also a good
	// example of putting together a full-screen BorderLayout within a Viewport.
    new Ext.Viewport({
		layout: 'fit',
		title: '比價表',
		forceFit: true,
		//defaults: {autoScroll: true}, 
		items: [mainPanel],
        renderTo: Ext.getBody()
    });

	function queryInquiryList(){
		Ext.lib.Ajax.request(
			'POST',
			'quotation/compare/AjaxQueryInquiryList.action',
			{success: function(response){
					//parse Json data
					var rs = Ext.util.JSON.decode(response.responseText);
						//Data Load to Grid.
					var inquiryJsonData = Ext.util.JSON.decode(response.responseText);
					var inquiryStore = inquiryGrid.getStore();
					inquiryStore.removeAll();
					inquiryStore.loadData(inquiryJsonData);
					inquiryGrid.getView().refresh();
			},failure: function(){
				Ext.Msg.alert("Error", "startQuotationItem Connection Error , Please Inform CLT IT");
			}},
			'inquiryNum=' + Ext.getCmp("queryInquiryFormInquiryNum").getValue() 
		);
	}
	
	function nextCompareQuotation(){
		Ext.Msg.confirm("Waring", "確定往 '下一步' ", function(button) {

			if (button == "yes") {
				 //Get Selected Row
				 var rows = inquiryGrid.getSelectionModel().getSelections();
				 if (rows.length == 0) {  
					Ext.Msg.alert("提示信息", "您沒有選中行!");
					return;
				 }else{
					var record = inquiryGrid.getSelectionModel().getSelected();
					var inquiryNum = record.get('inquiryNum');
					var paperVerUid = record.get('paperVerUid');

					Ext.getCmp('nextFormInquiryNum').setValue(inquiryNum);
					Ext.getCmp('nextFormPaperVerUid').setValue(paperVerUid);
				 }
				 
				 //只用指定TextField的id或者name屬性，服務器端Form中就能取到表單的數據
				 //如果同時指定了id和name，那麼name屬性將作為服務器端Form取表單數據的Key
				 var nextForm = Ext.getCmp('nextForm').getForm().getEl().dom;
				 nextForm.action = 'quotation/compare/QuoPaperCompare.action'
				 //指定為GET方式時,url中指定的參數將失效，表單項轉換成url中的key=value傳遞給服務端
				 //例如這裡指定為GET的話,url為:submit.aspx?param2=你輸入的值
				 nextForm.method = 'POST';//GET、POST
				 nextForm.submit();
				
			}else {
				return;
			}

		});
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
					" VENDOR.PAYMENT_METHOD_LOOKUP_CODE PAYMENT_METHOD, SITE.VENDOR_SITE_CODE VENDOR_SITE ",
			whereCause:" VENDOR.VENDOR_ID = SITE.VENDOR_ID " +
					" AND SITE.VENDOR_SITE_ID = CONT.VENDOR_SITE_ID " +
					" AND VENDOR.ENABLED_FLAG ='Y' AND SITE.INACTIVE_DATE IS NULL  " ,
			orderBy:"VENDOR.VENDOR_ID",
			title:"Vendor Lit",
			mode:0,
			autoSearch:true,
			like:1,
            callbackHandle:"completeGetSupplier"
		});	

		function completeGetSupplier(inputField, columns, value){
			Ext.getCmp('queryInquiryFormSupplierCode').setValue(value[0]["VENDOR.VENDOR_ID VENDOR_ID"]);
			Ext.getCmp('queryInquiryFormSupplierName').setValue(value[0]["VENDOR.VENDOR_NAME VENDOR_NAME"]);
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
			Ext.getCmp('queryInquiryFormPartNum').setValue(value[0]["SEGMENT1"]);
			Ext.getCmp('queryInquiryFormPartNumDesc').setValue(value[0]["DESCRIPTION"]);
			
		}
</script>
</div>
<div id="header"><h3>比價單</h3></div>
	
 </body>
</html>
