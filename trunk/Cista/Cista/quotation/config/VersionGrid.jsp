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
%>

<TITLE><s:text name="System.system.title"/></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<script language="javascript">
/*
 * 2011/07/27
 * Grid Basic Function
 * 
 * Hank Tang
 * 
 */

Ext.onReady(function(){
	Ext.QuickTips.init();

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

	function verApprove(value) {
        if (value == '1') {
            return "<span style='color:red;font-weight:bold;'>X</span>";
        } else {
            return "<span style='color:green;font-weight:bold;'></span>";
        }
    };

    function formatDate(value){
		if( value != "" ) {
			var year = value.substring(0,4);
			
			var month = value.substring(4,6);
			month = month - 1;
			
			var day = value.substring(6,8);
			
			value = new Date(year,month,day);

			return value ? value.dateFormat('Ymd') : '';
		}else{
			return '';
		}
    };

    function toDate(value){
		if( value != "" ) {
			var year = value.substring(0,4);
			
			var month = value.substring(4,6);
			month = month - 1;
			
			var day = value.substring(6,8);
			
			value = new Date(year,month,day);

			return value;
		}else{
			return '';
		}
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
           id:'catalog',
           header: "分類",
           dataIndex: 'catalog',
           width: 70
        },{
           id:'paper',
           header: "報價單",
           dataIndex: 'paper',
           width: 170
        },{
		   id:'paperVer',
           header: "版本",
           dataIndex: 'paperVer',
           width: 45
        },{
           id:'paperVerApprove',
           header: "生效",
           dataIndex: 'paperVerApprove',
           width: 45,
		   renderer:verApprove
        },{
           id:'verStartDt',
           header: "有效日",
           dataIndex: 'verStartDt',
		   renderer: formatDate,
           width: 60
        },{
           id:'verEndDt',
           header: "失效日",
           dataIndex: 'verEndDt',
		   renderer: formatDate,
           width: 60
        },{
           id:'udt',
           header: "最後更新",
           dataIndex: 'udt',
           width: 65
        },{
           id:'catalogUid',
           header: "Catalog ID",
           dataIndex: 'catalogUid',
           width: 60,
		   hidden: true
        },{
           id:'paperUid',
           header: "Quotation ID",
           dataIndex: 'paperUid',
           width: 60,
		   hidden: true
        },{
           id:'paperVerUid',
           header: "Version ID",
           dataIndex: 'paperVerUid',
           width: 60,
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
    var versionStore = new Ext.data.Store({
		id:'paperVerListStrore',
		proxy:new Ext.data.MemoryProxy(data),
		reader:new Ext.data.JsonReader({}, [
		   {name: 'catalog'},
           {name: 'paper'},
		   {name: 'paperVer'},
		   {name: 'paperVerApprove'},
           {name: 'verStartDt'},
           {name: 'verEndDt'},
		   {name: 'udt'},
		   {name: 'catalogUid'},
		   {name: 'paperUid'},
		   {name: 'paperVerUid'}

		]),
		sortInfo:{ field: "paperVer", direction: "DESC" }
	}) ;
	// trigger the data store load

	versionStore.load();

	var paperToolbar = new Ext.Toolbar({
	     items: [	
			'-',{text:'New Version',
				  style:"background-color:#FF9900;",
			      pressed:true,
			      handler: function(){
						//Clear  下面Item Grid 畫面
						clearQuotationItemGrid()

						if (versionStore.getCount() >0 ) {
							var rec = versionStore.getAt( 0 );
							var addVerFormPanel = Ext.getCmp('addVerForm');
							addVerForm = addVerFormPanel.getForm();
							addVerForm.loadRecord(rec);
							addVerForm.findField('verStartDt').setValue(''); 
							addVerForm.findField('verEndDt').setValue('');
							//Call 新增Function
							addVerFormUpdate();
						}else {
							Ext.Msg.alert("Error", "請Query Quotation");
						}

					}

				}
			,'-',{text:'New Quotation',
				style:"background-color:#FF9900;",
			    pressed:true,
			    handler: function(){
					//Clear Grid
					versionStore.removeAll();
					versionGrid.getView().refresh();
					//Clear Query Bar
					resetCatalog("","","");
					removeOptions($('selPapers'));
					//顯示addQuot-form
					$('addQuot-form').style.display = "block";
					//隱藏editVer-form
					$('editVer-form').style.display = "none";

					
				}
			},'-'
	     ]  
	 }); 

	// create the readonly grid
    var versionGrid = new Ext.grid.GridPanel({
		id: 'versionGrid',
        ds: versionStore,
        cm: cm,
		sm: new Ext.grid.RowSelectionModel({
	                singleSelect: true,
	                listeners: {
	                    rowselect: function(sm, row, rec) {
							//隱藏addQuot-form
							$('addQuot-form').style.display = "none";
							//顯示editVer-form
							$('editVer-form').style.display = "block";
	                        //Ext.getCmp("editVerForm").getForm().loadRecord(rec);

							Ext.getCmp('editVerFormCatalog').setValue(rec.data['catalog']);
							Ext.getCmp('editVerFormPaper').setValue(rec.data['paper']);
							Ext.getCmp('editVerFormPaperVer').setValue(rec.data['paperVer']);
							Ext.getCmp('editVerFormPaperVerApprove').setValue(rec.data['paperVerApprove']);
							if( rec.data['verStartDt'] != ""){
								Ext.getCmp('editVerFormVerStartDt').setValue(toDate(rec.data['verStartDt']) );
							}else{
								Ext.getCmp('editVerFormVerStartDt').setValue();
							}

							if( rec.data['verEndDt'] != ""){
								Ext.getCmp('editVerFormVerEndDt').setValue(toDate(rec.data['verEndDt']));
							}else{
								Ext.getCmp('editVerFormVerEndDt').setValue();
							}
							
							Ext.getCmp('editVerFormCatalogUid').setValue(rec.data['catalogUid']);
							Ext.getCmp('editVerFormPaperUid').setValue(rec.data['paperUid']);
							Ext.getCmp('editVerFormPaperVerUid').setValue(rec.data['paperVerUid']);
							Ext.getCmp('editVerFormOldPaperVerApprove').setValue(rec.data['inquiryCdt']);
							
							startQuotationItem(versionGrid);
	                    }
	                }
	            }),
		tbar: paperToolbar,
        renderTo: 'version-grid',
        width:575,
        height:divHeight * 0.5,
        title:'報價單各版本List',
        frame:true,
		loadMask: true,
	    stripeRows: true,
        clicksToEdit:1

	}) ;

	/********************
	* Edit Form Panel
	*********************/

    var editVerForm = new Ext.FormPanel({
        id: 'editVerForm',
        frame: true,
        labelAlign: 'left',
        title: 'Detail Update',
        bodyStyle:'padding:5px',
        width: 450,
        layout:'column',	// Specifies that the items will now be arranged in columns
        items: [{
        	columnWidth: 0.5,
            xtype: 'fieldset',
            labelWidth: 60,
            defaults: {width: 140},	// Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            border: false,

            items: [{
				id:'editVerFormCatalog',
                fieldLabel: '分類',
                name: 'catalog',
				readOnly:true,
				style:"background:#FFFFCC;"
            },{
				id:'editVerFormPaper',
                fieldLabel: '報價單',
                name: 'paper',
				readOnly:true,
				style:"background:#FFFFCC;"
            },{
				id:'editVerFormPaperVer',
                fieldLabel: '版本',
                name: 'paperVer',
				readOnly:true,
				style:"background:#FFFFCC;"
            },{
				id:'editVerFormPaperVerApprove',
				xtype:"combo",
                fieldLabel: '生效',
				store:new Ext.data.SimpleStore({
					fields: ['avalue', 'atext'],
					data: [["1","Yes"],["0","No"]]
				}),
				displayField: 'atext',
				valueField: 'avalue',
				mode: 'local',
				editable: false,
				triggerAction: 'all',
                name: 'paperVerApprove'
            }]
        },{
        	columnWidth: 0.5,
            xtype: 'fieldset',
            labelWidth: 60,
            defaults: {width: 140},	// Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            border: false,

            items: [{
				id:'editVerFormVerStartDt',
				xtype: 'datefield',
                fieldLabel: '有效日',
                name: 'verStartDt',
			    format: 'Y/m/d',
                minValue: '2011/01/01'
            },{
				id:'editVerFormVerEndDt',
				xtype: 'datefield',
                fieldLabel: '失效日',
                name: 'verEndDt',
			    format: 'Y/m/d',
                minValue: '2011/01/01'
            },{
				id:'editVerFormCatalogUid',
				xtype:"hidden",  
				hidden:true,  
				hiddenLabel:true,
                name: 'catalogUid'
            },{
				id:'editVerFormPaperUid',
				xtype:"hidden",  
				hidden:true,  
				hiddenLabel:true,
                name: 'paperUid'
            },{
				id:'editVerFormPaperVerUid',
				xtype:"hidden",  
				hidden:true,  
				hiddenLabel:true,
                name: 'paperVerUid'
            },{
				id:'editVerFormOldPaperVerApprove',
				xtype:"hidden",  
				hidden:true,  
				hiddenLabel:true,
                name: 'oldPaperVerApprove'
            }],
			buttons: [{
				text: 'Save',
				//handler:function(){
				onClick : function () {
					var editVerFormPanel = Ext.getCmp('editVerForm');
					editVerForm = editVerFormPanel.getForm();
						
					if(!editVerForm.findField('paperVerUid').getValue()){
						Ext.Msg.alert("Error", "請選擇'左邊'需要更新的資料..");
						return false;
					}

					if(editVerForm.findField('paperVerApprove').getValue() == 1 ){
						//Approve ="YES" - 生效日 & 失效日  都要有
						if( !editVerForm.findField('verStartDt').getValue() || 
							!editVerForm.findField('verEndDt').getValue() ){
							
							Ext.Msg.alert("Error", "請選擇 生效日 & 失效日");
							return false;
						}
						
						Ext.Msg.confirm("Waring", "要改變'生效版本'嗎?", function(button) {
							if (button == "yes") {
								editVerFormUpdate();
							}else{
							}
						});
					}
					//Update data
					editVerFormUpdate();
					
						
				}//handler:function()
			},{
				text: 'Cancel'
			}]
        }],
        renderTo: "editVer-form"
    });

	//Version From Update Function
	function editVerFormUpdate() {
		//Clear Paper Version List Grid Data
		versionStore.removeAll();
		//Update Data
		editVerForm.submit({  
		  waitMsg: '正在儲存,請稍候...',  
		  waitTitle: '提示',  
		  method: 'post',  
		  url: 'quotation/config/QuoPaperVerEdit.action',
		  successProperty:'success',
		  success: function(form,action) {

				reloadPaperVerGrid(Ext.getCmp('editVerFormPaperUid').value)
				Ext.MessageBox.alert("Message", "Update Success"); 
				editVerForm.reset();
		  },  
		  failure: function (form,action) {  
				Ext.MessageBox.alert("Error", "Update Failture"); 
		  }  
		});//editVerForm.submit
	}

	//Version From Update Function
	function addVerFormUpdate() {
		//Clear Paper Version List Grid Data
		versionStore.removeAll();
		//Update Data
		addVerForm.submit({  
		  waitMsg: '正在新增,請稍候...',  
		  waitTitle: '提示',  
		  method: 'post',  
		  url: 'quotation/config/QuoPaperVerAdd.action',
		  successProperty:'success',
		  success: function(form,action) {
				
				reloadPaperVerGrid(Ext.getCmp('addVerFormPaperUid').value)
				Ext.MessageBox.alert("Message", "新增 Version Success"); 
				addVerForm.reset();
		  },  
		  failure: function (form,action) {  
				Ext.MessageBox.alert("Error", "新增 Version Failture"); 
		  }  
		});//addVerForm.submit
	}
	//Reload Paper Version Grid

	function reloadPaperVerGrid(paperUid) {
		//Reload Paper Version List Grid Data
		Ext.lib.Ajax.request(
			'POST',
			'<%=contextPath%>/quotation/config/AjaxCatalogPaperVerList.action',
			{success: function(response){	
				if( response.responseText != "ERROR" ) {
					versionStore.removeAll();
					var json = Ext.decode(response.responseText);
					versionStore.loadData(json);
					versionGrid.getView().refresh();

				}else{
					Ext.Msg.alert("Error", "Get paper version list error.");

				}
			},failure: function(){

				Ext.Msg.alert("Error", "Connection Error , Please Inform CLT IT");
		
			}},
			'paperUid='+ paperUid
		);//Ext.lib.Ajax.request
	}

	/********************
	* Add New Form Panel
	*********************/

    var addVerForm = new Ext.FormPanel({
        id: 'addVerForm',
        frame: true,
        labelAlign: 'left',
        title: 'Add New Quotation Version',
        bodyStyle:'padding:5px',
        width: 450,
        layout:'column',	// Specifies that the items will now be arranged in columns
        items: [{
        	columnWidth: 0.5,
            xtype: 'fieldset',
            labelWidth: 60,
            defaults: {width: 140},	// Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            border: false,

            items: [{
				id:'addVerFormCatalog',
                fieldLabel: '分類',
                name: 'catalog',
				readOnly:true,
				style:"background:#FFFFCC;"
            },{
				id:'addVerFormPaper',
                fieldLabel: '報價單',
                name: 'paper',
				readOnly:true,
				style:"background:#FFFFCC;"
            },{
				id:'addVerFormPaperVerApprove',
				xtype:"combo",
                fieldLabel: '生效',
				store:new Ext.data.SimpleStore({
					fields: ['avalue', 'atext'],
					data: [["1","Yes"],["0","No"]]
				}),
				displayField: 'atext',
				valueField: 'avalue',
				mode: 'local',
				editable: false,
				triggerAction: 'all',
                name: 'paperVerApprove'
            }]
        },{
        	columnWidth: 0.5,
            xtype: 'fieldset',
            labelWidth: 60,
            defaults: {width: 140},	// Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            border: false,

            items: [{
				id:'addVerFormVerStartDt',
				xtype: 'datefield',
                fieldLabel: '有效日',
                name: 'verStartDt',
			    format: 'Y/m/d',
                minValue: '2011/01/01'
            },{
				id:'addVerFormVerEndDt',
				xtype: 'datefield',
                fieldLabel: '失效日',
                name: 'verEndDt',
			    format: 'Y/m/d',
                minValue: '2011/01/01'
            },{
				xtype:"hidden",  
				hidden:true,  
				hiddenLabel:true,
                name: 'catalogUid'
            },{
				id:'addVerFormPaperUid',
				xtype:"hidden",  
				hidden:true,  
				hiddenLabel:true,
                name: 'paperUid'
            },{
				id:'addVerFormPaperVerUid',
				xtype:"hidden",  
				hidden:true,  
				hiddenLabel:true,
                name: 'paperVerUid'
            },{
				id:'addVerFormOldPaperVerApprove',
				xtype:"hidden",  
				hidden:true,  
				hiddenLabel:true,
                name: 'oldPaperVerApprove'
            }]
        }],
        renderTo: "addVer-form"
    });

	/*****************************
	* Add New Quotation Form Panel
	******************************/

	var catalogStore = new Ext.data.SimpleStore({
        autoLoad: true,
        proxy: new Ext.data.HttpProxy({
        url: 'quotation/config/AjaxCatalogList.action',
            headers: { 'Content-type': 'application/json' },
            method: 'GET'
        }),  
		fields: ['value', 'text']
    });

    /*var catalogStore = new Ext.data.SimpleStore({
        fields: ['value', 'text'],
        data: catalogData
    });*/

    var addQuotForm = new Ext.FormPanel({
        id: 'addQuotForm',
        frame: true,
        labelAlign: 'left',
        title: 'Add New Quotation',
        bodyStyle:'padding:5px',
        width: 460,
        layout:'column',	// Specifies that the items will now be arranged in columns
        items: [{
        	columnWidth: 0.5,
            xtype: 'fieldset',
            labelWidth: 45,
            defaults: {width: 140},	// Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            border: false,

            items: [{
				id:'addQuotFormCatalog',
				xtype:"combo",
                fieldLabel: '分類',
                name: 'catalog',
				store: catalogStore,
				displayField: 'text',
				valueField: 'value',
				mode: 'local',
				editable: false,
				triggerAction: 'all',

				style:"background:#FFFFFF;"
            },{
				id:'addQuotFormPaper',
                fieldLabel: '報價單',
                name: 'paper',
				//readOnly:true,
				style:"background:#FFFFFF;"
            },{
				id:'addQuotFormPaperVerApprove',
				xtype:"combo",
                fieldLabel: '生效',
				store:new Ext.data.SimpleStore({
					fields: ['avalue', 'atext'],
					data: [["1","Yes"],["0","No"]]
				}),
				displayField: 'atext',
				valueField: 'avalue',
				mode: 'local',
				editable: false,
				triggerAction: 'all',
                name: 'paperVerApprove'
            }]
        },{
        	columnWidth: 0.5,
            xtype: 'fieldset',
            labelWidth: 45,
            defaults: {width: 140},	// Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            border: false,

            items: [{
				id:'addQuotFormVerStartDt',
				xtype: 'datefield',
                fieldLabel: '有效日',
                name: 'verStartDt',
			    format: 'Y/m/d',
                minValue: '2011/01/01'
            },{
				id:'addQuotFormVerEndDt',
				xtype: 'datefield',
                fieldLabel: '失效日',
                name: 'verEndDt',
			    format: 'Y/m/d',
                minValue: '2011/01/01'
            },{
				xtype:"hidden",  
				hidden:true,  
				hiddenLabel:true,
                name: 'catalogUid'
            },{
				id:'addQuotFormPaperUid',
				xtype:"hidden",  
				hidden:true,  
				hiddenLabel:true,
                name: 'paperUid'
            },{
				id:'addQuotFormPaperVerUid',
				xtype:"hidden",  
				hidden:true,  
				hiddenLabel:true,
                name: 'paperVerUid'
            },{
				id:'addQuotFormOldPaperVerApprove',
				xtype:"hidden",  
				hidden:true,  
				hiddenLabel:true,
                name: 'oldPaperVerApprove'
            }],
			buttons: [{
				text: 'Save',
				//handler:function(){
				onClick : function () {
					var addQuotFormPanel = Ext.getCmp('addQuotForm');
					addQuotForm = addQuotFormPanel.getForm();
						
					if(!addQuotForm.findField('catalog').getValue()){
						Ext.Msg.alert("Error", "請選擇 '分類' ..");
						return false;
					}

					if(!addQuotForm.findField('paper').getValue()){
						Ext.Msg.alert("Error", "請填入 新的'報價單名字' ..");
						return false;
					}
						
					if(!addQuotForm.findField('paperVerApprove').getValue()){
						Ext.Msg.alert("Error", "請選擇 '生失效'的資料..");
						return false;
					}

					if(addQuotForm.findField('paperVerApprove').getValue() == 1 ){
						//Approve ="YES" - 生效日 & 失效日  都要有
						if( !addQuotForm.findField('verStartDt').getValue() || 
							!addQuotForm.findField('verEndDt').getValue() ){
							
							Ext.Msg.alert("Error", "請選擇 生效日 & 失效日");
							return false;
						}
						
						/*Ext.Msg.confirm("Waring", "要改變'生效版本'嗎?", function(button) {
							if (button == "yes") {
								addQuotFormUpdate();
							}else{
							}
						});*/
					}
					//Save New data
					addQuotFormUpdate();
					
						
				}//handler:function()
			},{
				text: 'Cancel'
			}]
        }],
        renderTo: "addQuot-form"
    });

	//Version From Update Function
	function addQuotFormUpdate() {
		addQuotForm.findField('catalogUid').setValue(addQuotForm.findField('catalog').getValue());
		//Clear Paper Version List Grid Data
		versionStore.removeAll();
		//Update Data
		addQuotForm.submit({  
		  waitMsg: '正在新增Quotation,請稍候...',  
		  waitTitle: '提示',  
		  method: 'post',  
		  url: 'quotation/config/QuoPaperAdd.action',
		  successProperty:'success',
		  success: function(form,response) {
				//Ext.MessageBox.alert("Success", "新增 Quotation Success"); 
				reloadPaperVerGrid(response.result.data.paperUid);
				//Grid 自動Select 第一筆
				Ext.getCmp('versionGrid').store.on('load', function(){
					var grid = Ext.getCmp('versionGrid');
					grid.getSelectionModel().selectRow(0);
					grid.fireEvent('rowclick', grid, 0)
					}, this, {
					single: true
				});

				addQuotForm.reset();
				resetCatalog(response.result.data.catalogUid, response.result.data.paper, response.result.data.paperUid); 
				$('addQuot-form').style.display = "none";
				$('editVer-form').style.display = "block";
				
				alert("新增 Quotation Success"); 
				SelectItem_Combo_Value($('selPapers'),response.result.data.paperUid);
		  },  
		  failure: function (form,action) {  
				alert(action.responseText);
				Ext.MessageBox.alert("Error", "新增 Quotation Failture"); 
		  }
		});//addVerForm.submit
	}


	//監視Store數據是否變化, 進行一些其它處理
	/*store.on('datachanged', function(){
		if(grid.getSelectionModel().getSelections().length != pageCount){ //沒有全選的話
			//清空表格頭的checkBox
			grid.getEl().select('div.x-grid3-hd-checker').removeClass('x-grid3-hd-checker-on'); //x-grid3-hd-checker-on
		}
	}); */

	//Query Button Click
	new Ext.Button({
		text: 'Query',
		id: 'queryBtn',
		renderTo:'div-QueryBtn',
		onClick : function () {
				//Clear  下面Item Grid 畫面
				editVerFormReset();
				clearQuotationItemGrid()

				if( $F('paper') == "" ){
					Ext.MessageBox.alert("Error ","必須輸入 '報價單' ");
					return;
				}

				Ext.lib.Ajax.request(
					'POST',
					'<%=contextPath%>/quotation/config/AjaxCatalogPaperVerList.action',
					{success: function(response){	
						if( response.responseText != "ERROR" ) {
							versionStore.removeAll();
							var json = Ext.util.JSON.decode(response.responseText);
							versionStore.loadData(json);
	
						}else{
							Ext.Msg.alert("Error", "Get paper version list error.");

						}
					},failure: function(){

						Ext.Msg.alert("Error", "Connection Error , Please Inform CLT IT");
				
					}},
					'paperUid='+ $F('paperUid')
				);//Ext.lib.Ajax.request(
		}//onClick : function ()
	});
	//startQuotationItem(versionGrid);

	function editVerFormReset(){
		Ext.getCmp('editVerFormCatalog').reset();
		Ext.getCmp('editVerFormPaper').reset();
		Ext.getCmp('editVerFormPaperVer').reset();
		Ext.getCmp('editVerFormPaperVerApprove').reset();
		Ext.getCmp('editVerFormVerStartDt').reset();
		Ext.getCmp('editVerFormVerEndDt').reset();

		Ext.getCmp('editVerFormCatalogUid').reset();
		Ext.getCmp('editVerFormPaperUid').reset();
		Ext.getCmp('editVerFormPaperVerUid').reset();
		Ext.getCmp('editVerFormOldPaperVerApprove').reset();
	}
});
</script>