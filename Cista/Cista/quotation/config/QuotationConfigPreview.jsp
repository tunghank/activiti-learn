<%@ page errorPage="/common/chkerror.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ page import ="com.clt.system.to.SysUserTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String contextPath = (String)request.getContextPath();
    String EXCEP_MSG_SESSION =CLTUtil.getMessage("System.error.access.nologin");

  	// get current user info
    SysUserTo curUserTo =
            (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
    // check session
    if (null == curUserTo) {
        throw new Exception(EXCEP_MSG_SESSION);
   }

   	String paperVerUid = (String)request.getAttribute("paperVerUid");
	paperVerUid = null != paperVerUid ? paperVerUid : "";

%>
<html>

<head>
<jsp:include page="/common/normalcheck.jsp" />
<script language="javascript">
/*
 * 2011/07/27
 * Quotation Config Preview Function
 * 
 * Hank Tang
 * 
 */

Ext.onReady(function(){
	startQuotationItem("<%=paperVerUid%>");



});

function startQuotationItem(paperVerUid){

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
		   fieldSeq:count,
		   field:'',
		   fieldAttr:'TEXT',
		   fieldNecess:'NO',
		   fieldValue:'',
		   numberStart:'',
		   numberEnd:''
		});
		grid.stopEditing();
		grid.getStore().insert(count, rec);
		grid.startEditing(count , 0);
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

	//1.0先找出 此張Quotation 有少Items
	Ext.lib.Ajax.request(
		'POST',
		'quotation/config/AjaxPaperFildByPaperVer.action',
		{success: function(response){
				//parse Json data
				var groupListJsonData = Ext.util.JSON.decode(response.responseText);
				//alert(groupListJsonData.length);
				var i = 0;
				for (i=0 ; i< groupListJsonData.length; i++){
					var groupData = groupListJsonData[i];
					if( groupData ){
						//alert(groupData);
						var paperGroupName =  groupData.paperGroupName;
						var paperGroupUid  =  groupData.paperGroupUid;
						//alert(paperGroupName);
						var fieldList =  groupData.fieldList;
						addTab(paperGroupName, paperGroupUid, fieldList);
					}
				}
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
		var dataItem = [


		] ;
		// create the Data Store
		var storeItem = new Ext.data.Store({
			proxy:new Ext.data.MemoryProxy(dataItem),
			reader:new Ext.data.ArrayReader({},[
			   {name: 'fieldSeq'}
			   /*,{name: 'field'},
			   {name: 'fieldAttr'},
			   {name: 'fieldNecess'},
			   {name: 'fieldValue'},
			   {name: 'numberStart'},
			   {name: 'numberEnd'}*/

			]),
			sortInfo:{ field: "fieldSeq", direction: "ASC" }
		}) ;
		// trigger the data store load
		storeItem.load();


		/*主要 Add Tab Function*/
		
		//readonly grid
		//var gridItem = new Ext.grid.EditorGridPanel({
		var gridItem = new Ext.grid.GridPanel({
			id: gridItemId,
			ds: storeItem,
			cm: cmItem,
			sm: smItem,
			//renderTo: 'paperItem-grid',
			width:divWidth,
			height:divHeight,
			title:tabName,
			frame:true,
			loadMask: true,
			stripeRows: true,
			enableColumnMove :false,
			iconCls: 'icon-grid',
			clicksToEdit:1
		});

		tabs.add(gridItem).show();
		
		//Add Column in Grid
		var j = 0;
		for (j=0 ; j< fieldList.length; j++){
			var fieldData = fieldList[j];
			if( fieldData ){
				var field =  fieldData.field;
				var fieldSeq =  fieldData.fieldSeq;
				/*,{
				   id:'numberStart',
				   header: "起始值",
				   dataIndex: 'numberStart',
				   width: 80,
					editor: new fmItem.NumberField({
					   style:'direction:rtl',
					   allowDecimals:false,
					   style: 'text-align:left',
					   maxValue: 999999
				   })
				}*/
				gridItem.addColumn({id:'item'+ fieldSeq , dataIndex:'item'+ fieldSeq, header:field, width:120}); 
			}
			gridItem.getView().refresh();
		}

	}//end function addTab

	/********************
	*	Paper Tabs
	*********************/
	var tabs = new Ext.TabPanel({
		id:'paper-Tabs',
		resizeTabs:true, // turn on tab resizing
		minTabWidth: 180,
		tabWidth:200,
		activeTab: 0,
		enableTabScroll:true,
		width:screen.width - 370,
		height:screen.height - 700,
		defaults: {autoScroll:true}
	});

	var tabsPanel = new Ext.Panel({
		width:screen.width - 370,
		title:'議價單項目',
		//管理grid
		items:[tabs]
	});

	/*********************************************************
	*					Layout
	*********************************************************/

	// Finally, build the main layout once all the pieces are ready.  This is also a good
	// example of putting together a full-screen BorderLayout within a Viewport.
    new Ext.Viewport({
		layout: 'fit',
		title: 'Ext Layout Browser',
		forceFit: true,
		items: [tabs],
        renderTo: Ext.getBody()
    });
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

	.x-grid3-row{background-color:#CCFF66;}
	.x-grid3-row-alt{background-color:#FFFFCC;}
	
</style>	

</head>
<body bgcolor="#FFFFFF">
</body>

</html>