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
           dataIndex: 'inquiryKeyPartNum',
           width: 130
        },{
           id:'inquiryPartNumDiffer',
           header: "主/替",
           dataIndex: 'inquiryPartNumDiffer',
           width: 40
        },{
           id:'inquiryPartNum',
           header: "料號",
           dataIndex: 'inquiryPartNum',
           width: 120
        },{
           id:'inquiryModelDesc',
           header: "機種料號",
           dataIndex: 'inquiryModelDesc',
           width: 200
        },{
           id:'inquiryPartNumDesc',
           header: "料號摘要",
           dataIndex: 'inquiryPartNumDesc',
           width: 380
        },{
		   id:'inquiryQty',
		   header: "數量",
		   dataIndex: 'inquiryQty',
		   width: 70
		},{
           id:'inquiryUnit',
           header: "單位",
           dataIndex: 'inquiryUnit',
           width: 35
        }
    ]);

    // by default columns are sortable
    cm.defaultSortable = true;

    // this could be inline, but we want to define the Plant record
    // type so we can add records dynamically
	var data = {

	};
	// create the Data Store
    var partNumStore = new Ext.data.GroupingStore({
		id:'partNumStore',
		proxy:new Ext.data.MemoryProxy(data),
		reader:new Ext.data.JsonReader({}, [
		   {name: 'inquiryKeyPartNum'},
           {name: 'inquiryModelDesc'},
		   {name: 'inquiryPartNumDiffer'},
		   {name: 'inquiryPartNum'},
		   {name: 'inquiryPartNumDesc'},
           {name: 'inquiryQty'},
		   {name: 'inquiryUnit'}
		]),
		sortInfo:{ field: "inquiryPartNumDiffer", direction: "ASC" },
		groupField:'inquiryKeyPartNum'
	}) ;


	partNumStore.load();

	var quotPaperPaper = new  Ext.form.ComboBox({
		id:'quotPaperPaper',
		name: 'quotPaperPaper',
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
		width: 250,
		listeners:{
				change: function(f,newVal){
							Ext.getCmp('paperVerUidGrid').setValue(newVal);
						}
				}
	});

	var paperVerUidGrid = new Ext.form.TextField({
		id:'paperVerUidGrid',
		name : "paperVerUidGrid",
		readOnly:true,
		hidden:true,  
		hiddenLabel:true,
		allowBlank: true
	});


	 var toolbar = new Ext.Toolbar({
		 id:'toolbar',
		 items: [  
			'-',
			 {text:'刪除',
				  iconCls: 'remove',
				  id:'partNumGridTbBtnDel',
				  style:"background-color:#FF9900;",
				  pressed:true,
				  //hidden:true,
				  handler: function(){
						delSelectedRow(Ext.getCmp('partNumGrid'), Ext.getCmp('partNumGrid').getStore());
				  }

				},
			'-',{text:'修改',
				  iconCls: 'edit',
				  id:'partNumGridTbBtnEdit',
				  style:"background-color:#FF9900;",
				  pressed:true,
				  handler: function(){
						modifyInquiryItem(Ext.getCmp('partNumGrid'));
				  }

				}
			,'-',{text:'報價單格式:',
				  id:'partNumFormQuotPaperLabel'/*,
				  style:"background-color:#FF9900;"*/
				},quotPaperPaper
			,'-',paperVerUidGrid
		 ]  
	 }); 

	// create the readonly grid
    var partNumGrid = new Ext.grid.EditorGridPanel({
		id: 'partNumGrid',
        ds: partNumStore,
        cm: cm,
		tbar: toolbar,
		sm: new Ext.grid.RowSelectionModel({
	                singleSelect: true,
	                listeners: {
	                    rowselect: function(sm, row, rec) {

	                    }
	                }
	            }),
		view: new Ext.grid.GroupingView({
            forceFit:true,
            showGroupName: false,
            enableNoGroups:false, // REQUIRED!
            hideGroupedColumn: true
        }),
        region:'center',
        title:'詢價料號',
        frame:true,
		loadMask: true,
	    stripeRows: true,
        clicksToEdit:1

	}) ;

	//Form
	var partNumForm = new Ext.form.FieldSet({
        id: 'partNumForm',
		title: '資料編輯區',
		style: 'padding: 10 0 0 0',
		bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
		labelAlign: 'left',
		height:100,
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
					text: '料號:'
				}]
			},{
				columnWidth: .15,
				height:30,
				items: [{
					xtype: "textfield",
					id:'partNumFormPartNum',
					name: 'partNumFormPartNum',
					readOnly:true,
					width: 200,
					style:"background:#EEEEEE;color:#BC6618;"
				}]
			},{
				columnWidth: .03,
				height:30,
				items: [{
					xtype: 'button',
					id:'partNumFormBtnPartNum',
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
					xtype: 'button',
					id:'partNumFormBtnAddPartNum',
					text: '+',
					listeners:{
						"click":function(){
							document.getElementById("hiddenPartNumAddBtn").click();
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
				columnWidth: .67,
				height:30,
				items: [{
					xtype: "textfield",
					id:'partNumFormPartNumDesc',
					name: 'partNumFormPartNumDesc',
					readOnly:true,
					width: 700,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{//第二行
				columnWidth: .05,
				height:30,
				items: [{
					xtype: "label",
					text: '機種料號:'
				}]
			},{
				columnWidth: .3,
				height:30,
				items: [{
					xtype: "textfield",
					id:'partNumFormModelsPartNum',
					fieldLabel: '機種料號',
					name: 'partNumFormModelsPartNum',
					readOnly:true,
					width: 300,
					style:"background:#EEEEEE;color:#BC6618;"
				}]
			},{
				columnWidth: .04,
				height:30,
				items: [{
					xtype: "label",
					text: '數量:'
				}]
			},{
				columnWidth: .12,
				height:30,
				items: [{
					xtype : 'numberfield',
					id:'partNumFormQty',
					fieldLabel: '數量',
					name: 'partNumFormQty',
					style:'direction:rtl',
					style: 'text-align:left',
					allowDecimals : false,//允許輸入小數 
					allowNegative :false//允許輸入負數  
				}]
			},{
				columnWidth: .04,
				height:30,
				items: [{
					xtype: "label",
					text: '單位:'
				}]
			},{
				columnWidth: .12,
				height:30,
				items: [{
					xtype : 'textfield',
					id:'partNumFormUnit',
					name: 'partNumFormUnit',
					readOnly:true,
					style:"background:#EEEEEE;color:#0000B7;"
				}]
			},{//空白行
				columnWidth: .05,
				height:30,
				items: [{}]
			},{
				columnWidth: .05,
				height:30,
				bodyStyle: 'padding-right:0px;padding-left:0px;padding-top:5px;padding-bottom:0px;',
				items: [{
					xtype: 'button',
					id:'partNumFormBtnAdd',
					text: '新增',
					listeners:{
						"click":function(){
							addInquiryItemGroup();
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
				bodyStyle: 'padding-right:0px;padding-left:0px;padding-top:5px;padding-bottom:0px;',
				items: [{
					xtype: 'button',
					id:'partNumFormBtnEdit',
					text: '修改',
					disabled :true,
					listeners:{
						"click":function(){
							saveModifyInquiryItem(Ext.getCmp('partNumGrid'));
						}
					}
				}]
			},{
				columnWidth: .05,
				height:30,
				items: [{
					xtype : 'textfield',
					id:'partNumGridInx',
					name: 'partNumGridInx',
					readOnly:true,
					style:"background:#EEEEEE;color:#0000B7;",
					hidden: true
				}]
			}]
		}]
    });

	//詢價料號 Grid
	function delSelectedRow(grid, store) {

	   Ext.Msg.confirm('訊息', '確定要刪除？', function(btn){
		   if (btn == 'yes') {
			    var record = grid.getSelectionModel().getSelected();

				if (!record) {
					Ext.MessageBox.alert('No Record Selected', 'Please select a record before attemting this action.');
					return false;
				}

				var count = store.getCount();
				var delInquiryKeyPartNum = record.data['inquiryKeyPartNum'];
				for(var i=0; i < count; i ++ ){
					var rec = store.getAt( i );
					var inquiryKeyPartNum = rec.data['inquiryKeyPartNum'];
					if( delInquiryKeyPartNum == inquiryKeyPartNum ){
						store.remove(rec);
						grid.getView().refresh();
						count = store.getCount();
						i = -1;
					}
				}
			   grid.getView().refresh();
			   updateKeyPartNumRowColor(grid);
		   }
	   });

	}

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
                columnWidth:.3,
                layout: 'form',
                defaultType: 'textfield',
                items:[
                ]
            },{
                columnWidth:.3,
                layout: 'form',
                defaultType: 'textfield',
                items:[{
						xtype: "textfield",
						id:'paperVerUid',
						name: 'paperVerUid',
						allowBlank:false,
						readOnly:true,
						multiline: true,
						hidden: true
                }]
            },{
                columnWidth:.25,
                layout: 'form',
                items:[{
						xtype: "textarea",
						id:'partNumGridData',
						name: 'partNumGridData',
						allowBlank:false,
						readOnly:true,
						width : 600,
						height : 80,
						multiline: true,
						hidden: true
						//anchor:'90%'
                }]
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
									nextInquiryVendor(Ext.getCmp('partNumGrid'));
								}
							}
						}
					]
				},{
					columnWidth:.5,
					items:[{
							xtype: 'button',
							text: '取消'
						}
					]
				}]
			}]
        }]
    });

	//Panel
	var mainPanel = new Ext.Panel({
		title:'輸入詢價料號',
		layout: 'border',
		bodyStyle: "background-color:#FFFFFF; border-width: 0px 2px 0px 0px;",
		//管理grid
		items:[partNumForm , partNumGrid, nextForm]
	});
	

	/*********************************************************
	*					Layout
	*********************************************************/

	// Finally, build the main layout once all the pieces are ready.  This is also a good
	// example of putting together a full-screen BorderLayout within a Viewport.
    new Ext.Viewport({
		layout: 'fit',
		title: '輸入詢價料號',
		forceFit: true,
		//defaults: {autoScroll: true}, 
		items: [mainPanel],
        renderTo: Ext.getBody()
    });


	

});

function addInquiryItemGroup(){

	if( checkInquiryPartNum() ){
		var myMask = new Ext.LoadMask(Ext.getBody(), {  
			msg : "Please wait..."  
		});
		myMask.show();

		Ext.lib.Ajax.request(
			'POST',
			'quotation/inquiry/InstQuoInquiryPartNum.action',
			{success: function(response){
					//Clear Edit PartNum Form
					clearPartNumForm();

					//parse Json data
					var groupListJsonData = Ext.util.JSON.decode(response.responseText);
					var partNumGrid = Ext.getCmp('partNumGrid');
					partNumGrid.stopEditing();

					for (var i = 0; i < groupListJsonData.length; i++) {
							var count = partNumGrid.getStore().getCount();
							var rec = new Ext.data.Record(groupListJsonData[i]);
							partNumGrid.getStore().insert(count, rec);
					}
					partNumGrid.startEditing(count , 0);
					partNumGrid.getView().refresh();
				
					updateKeyPartNumRowColor(partNumGrid);
					//Ext.getCmp('partNumGrid').getStore().loadData(groupListJsonData);
					myMask.hide();
			},failure: function(){
				myMask.hide();
				Ext.Msg.alert("Error", "QuoInquiryPartNum Connection Error , Please Inform CLT IT");
			}},
			'inquiryKeyPartNum=' + Ext.getCmp('partNumFormPartNum').getValue() + 
			'&partNumFormModelsPartNum=' + Ext.getCmp('partNumFormModelsPartNum').getValue() + 
			'&partNumFormPartNumDesc=' + Ext.getCmp('partNumFormPartNumDesc').getValue() +
			'&partNumFormQty=' + Ext.getCmp('partNumFormQty').getValue() +
			'&partNumFormUnit=' + Ext.getCmp('partNumFormUnit').getValue()
		);
		myMask.destroy();
	}
}

function modifyInquiryItem(grid){

	clearPartNumForm();
	var rec = grid.getSelectionModel().getSelected();
	var index = grid.getStore().indexOf(rec);

	var inquiryKeyPartNum = rec.data['inquiryKeyPartNum'];
	var differ = rec.data['inquiryPartNumDiffer'];
	var inquiryPartNum = rec.data['inquiryPartNum'];
	var modelsPartNum = rec.data['inquiryModelDesc'];
	var inquiryPartNumDesc = rec.data['inquiryPartNumDesc'];
	var quotPaperDesc = rec.data['quotPaperDesc'];
	var inquiryQty = rec.data['inquiryQty'];
	var inquiryUnit = rec.data['inquiryUnit'];

	if(inquiryUnit == ""){
		Ext.MessageBox.show({
			title:'MESSAGE',
			msg: " 必需選擇 詢價料號.. ",
			icon: Ext.MessageBox.ERROR
		});
		return false;
	}

	//Disable Button
	Ext.getCmp('partNumFormBtnAdd').setDisabled(true);
	Ext.getCmp('partNumFormBtnPartNum').setDisabled(true);
	Ext.getCmp('partNumGridTbBtnDel').setDisabled(true);
	//Disable Edit
	Ext.getCmp('partNumFormBtnEdit').setDisabled(false);

	Ext.getCmp('partNumFormPartNum').getEl().dom.readOnly = true;
	Ext.getCmp('partNumFormPartNum').getEl().addClass('valid-text');
	

	//Set value into PartNum Form
	Ext.getCmp('partNumFormPartNum').setValue(inquiryPartNum);
	Ext.getCmp('partNumFormModelsPartNum').setValue(modelsPartNum);
	Ext.getCmp('partNumFormPartNumDesc').setValue(inquiryPartNumDesc);
	Ext.getCmp('partNumFormQty').setValue(inquiryQty);
	Ext.getCmp('partNumFormUnit').setValue(inquiryUnit);
	Ext.getCmp('partNumGridInx').setValue(index)

	updateKeyPartNumRowColor(grid);
}

function saveModifyInquiryItem(grid){
	if( checkInquiryPartNum() ){

		Ext.getCmp('partNumFormBtnAdd').setDisabled(false);
		Ext.getCmp('partNumFormBtnPartNum').setDisabled(false);
		Ext.getCmp('partNumGridTbBtnDel').setDisabled(false);

		Ext.getCmp('partNumFormPartNum').getEl().dom.readOnly = false;
		Ext.getCmp('partNumFormPartNum').getEl().removeClass('valid-text');
		var partNumFormQty = Ext.getCmp('partNumFormQty').getValue();
		clearPartNumForm();
		var index = Ext.getCmp('partNumGridInx').getValue();
		partNumFormQty = partNumFormQty * 1;

		var rec = grid.store.getAt(index);
		rec.data['inquiryQty'] = partNumFormQty;
		
		grid.getView().refresh();
		grid.getSelectionModel().selectRow(index); 
		//Disable Edit
		Ext.getCmp('partNumGridInx').setValue("");
		Ext.getCmp('partNumFormBtnEdit').setDisabled(true);
		
		updateKeyPartNumRowColor(grid);
	}
}

function nextInquiryVendor(grid){
	var partNumGridStore = grid.getStore();

	if(partNumGridStore.getCount() == 0 ) {
		Ext.Msg.alert("Waring", "No Any Data");
		return;
	}
	var jsonData = [];
	 
	for( i=0; i < partNumGridStore.getCount(); i ++ ){
		var rec = partNumGridStore.getAt( i );
		jsonData[i] = rec;
	}


	var jsonArray = [];
	Ext.each(jsonData, function(item) {
		jsonArray.push(item.data);
	});

	Ext.Msg.confirm("Waring", "確定往 '下一步' ", function(button) {

		if (button == "yes") {
			 
			//報價單格式
			var quotPaperPaper = Ext.getCmp('quotPaperPaper').getValue() + "";
			if( quotPaperPaper != "" ){

			}else{
				Ext.MessageBox.show({
						title:'MESSAGE',
						msg: " '報價單格式' 必需選擇!",
						icon: Ext.MessageBox.ERROR
					});
					return false;
			}

			var paperVerUidGrid = Ext.getCmp('paperVerUidGrid').getValue() + "";
			if( paperVerUidGrid != "" ){

			}else{
				Ext.MessageBox.show({
						title:'MESSAGE',
						msg: " '報價單格式' 必需選擇!",
						icon: Ext.MessageBox.ERROR
					});
					return false;
			}


			 Ext.getCmp('partNumGridData').setValue(Ext.encode(jsonArray));
			 Ext.getCmp('paperVerUid').setValue(paperVerUidGrid);

			 //只用指定TextField的id或者name屬性，服務器端Form中就能取到表單的數據
			 //如果同時指定了id和name，那麼name屬性將作為服務器端Form取表單數據的Key
			 var nextForm = Ext.getCmp('nextForm').getForm().getEl().dom;
			 nextForm.action = 'quotation/inquiry/SaveInquiryPartNum.action'
			 //指定為GET方式時,url中指定的參數將失效，表單項轉換成url中的key=value傳遞給服務端
			 //例如這裡指定為GET的話,url為:submit.aspx?param2=你輸入的值
			 nextForm.method = 'POST';//GET、POST
			 nextForm.submit();

		}else {
			return;
		}

	});
}

function checkInquiryPartNum(){

	//Check 料號 value
	var partNumFormPartNum = Ext.getCmp('partNumFormPartNum').getValue();
	if( partNumFormPartNum != "" ){

	}else{
		Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '料號' 必需填入值!",
				icon: Ext.MessageBox.ERROR
			});
			return false;
	}

	//Check QTY value
	/*var partNumFormQty = Ext.getCmp('partNumFormQty').getValue() + "";
	if( partNumFormQty != "" ){
		if ( partNumFormQty.isNumber() ){
			if(!partNumFormQty.isPositiveDecimal(8,8) ){
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
	return true;
}

function clearPartNumForm(){
	//Clear Edit PartNum Form
	Ext.getCmp('partNumFormPartNum').reset();
	Ext.getCmp('partNumFormModelsPartNum').reset();
	Ext.getCmp('partNumFormPartNumDesc').reset();
	Ext.getCmp('partNumFormQty').reset();
	Ext.getCmp('partNumFormUnit').reset();
}

function updateKeyPartNumRowColor(grid){
	for (var i = 0; i < grid.getStore().getCount(); i++) {
		var record = grid.getStore().getAt(i);
		if(record.data['inquiryKeyPartNum'] == record.data['inquiryPartNum'] ){
			grid.getView().getRow(i).style.backgroundColor = '#FF99FF'
		}
	}
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
<input type="text" id = "hiddenPartNum" name="hiddenPartNum" value='11590750-B0' />
<input type="button" id="hiddenPartNumBtn" name="hiddenPartNumBtn" >
<input type="button" id="hiddenPartNumAddBtn" name="hiddenPartNumAddBtn" onClick="selectPartNum(this)">
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
			Ext.getCmp('partNumFormPartNum').setValue(value[0]["SEGMENT1"]);
			Ext.getCmp('partNumFormPartNumDesc').setValue(value[0]["DESCRIPTION"]);
			//Ext.getCmp('partNumFormModelsPartNum').setValue(value[0]["ELEMENT_VALUE"]);
			Ext.getCmp('partNumFormUnit').setValue(value[0]["PRIMARY_UOM_CODE"]);
			
		}

		function selectPartNum(objAdd, inputField){
			
			var btnName = objAdd.name;
			var button = Ext.get(btnName);
			var initSelects = "";
			var initValue = Ext.getCmp('partNumFormPartNum').getValue();

			var initSelected = new Array();
			initSelected = initValue.split(",");
			if (initSelected && initSelected.length>0) {
				
				for(var i=0; i<initSelected.length; i++) {
					initSelects +=  initSelected[i] + "\n";
				}

			}
			clearPartNumForm();

			var dialog = Ext.MessageBox;
			dialog.show({
					title: '多行輸入框',
					msg: '你可以輸入好幾行',
					width:300,
					buttons: Ext.MessageBox.OKCANCEL,
					multiline: true,
					prompt  : true,
					value : initSelects,
					fn: function(btn, text) {
						var selected = new Array();
						var textValue = text;
						selected = textValue.split("\n");
						if (selected && selected.length>0) {
							var selects = "";
							for(var i=0; i<selected.length; i++) {
								selects += "," + selected[i];
							}
							if (selects.length > 0) {
							  var value = selects.substring(1);
								if(value.substring(value.length-1)==","){
								   value = value.substring(0,value.length-2);
								}
								Ext.getCmp('partNumFormPartNum').setValue(value);
							}
						}
					}
			});
		};

</script>
</div>
<div id="header"><h3>詢價單</h3></div>
	
 </body>
</html>
