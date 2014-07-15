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
	   fieldNecess:'YES',
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

/**********************************************
*				Add Tab Function
***********************************************/
// tab generation code
function addTabName(versionGrid){

	var selVersion = versionGrid.getSelectionModel();

	if (selVersion.hasSelection()) {
		Ext.MessageBox.prompt('詢價項目', '請打入詢價項目', instTabName);
	}else{
		Ext.Msg.alert("Error", "沒有選擇 要修改新增的 報價單版本 !!!");
	}
}
function instTabName(btn, text){
	text = text.trim();
	if(text == ""){
		Ext.Msg.alert("Error", "請打入 項目名稱!!");
		return;
	}
	addTab(text,'', '');

	Ext.Msg.alert('Message', '****請再點選Save Tabs 成為新的版本****', function(){
	});
};

var index = 0;

function addTab(tabName, paperGroupUidValue, fieldList){
	 var indexId = (++index);
	 var gridItemId = 'gridItem' + indexId;

	/*******************************************************
	*				Grid Config
	********************************************************/
	//fieldAttr
	var fieldAttrData = [
		['TEXT','TEXT'],
		['POSITIVE NUMBER','POSITIVE'],
		['BETWEEN NUMBER','BETWEEN']
		
	];

	var fieldAttrStore = new Ext.data.SimpleStore({
		fields: ['value', 'text'],
		data: fieldAttrData
	});

	//fieldNecess
	var fieldNecessData = [
		['NO','NO'],
		['YES','YES']
	];

	var fieldNecessStore = new Ext.data.SimpleStore({
		fields: ['value', 'text'],
		data: fieldNecessData
	});

	// shorthand alias
	var fmItem = Ext.form;
	fmItem.id =  'fmItem' + indexId;
	// the column model has information about grid columns
	// dataIndex maps the column to the specific data field in
	// the data store (created below)
	
	var smItem = new Ext.grid.RowSelectionModel({singleSelection:true}); //選擇模型改為了行選擇模型

	var cmItem = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{
		   id:'fieldSeq',
		   header: "Sequence",
		   dataIndex: 'fieldSeq',
		   width: 75,
		   align: 'right'
		  
		},{
		   id:'field',
		   header: "項目",
		   dataIndex: 'field',
		   width: 200,
		   editor: new fmItem.TextField({
			   allowBlank: true
		   })
		},{
		   id:'fieldAttr',
		   header: "屬性",
		   dataIndex: 'fieldAttr',
		   width: 60,
		   editor: new fmItem.ComboBox({
				width:200,
				store: fieldAttrStore,
				emptyText: 'Select One',
				mode: 'local',
				triggerAction: 'all',
				valueField: 'value',
				readOnly: true,
				resizable: true,
				displayField: 'text'
		   })
		},{
		   id:'fieldNecess',
		   header: "必須?",
		   dataIndex: 'fieldNecess',
		   width: 50,
			editor: new fmItem.ComboBox({
				width:200,
				store: fieldNecessStore,
				emptyText: 'Select One',
				mode: 'local',
				triggerAction: 'all',
				valueField: 'value',
				readOnly: true,
				resizable: true,
				displayField: 'text'
		   })
		},{
		   id:'fieldValue',
		   header: "預設值",
		   dataIndex: 'fieldValue',
		   width: 140,
		   editor: new fmItem.TextField({
			   allowBlank: true
		   })
		},{
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
		},{
		   id:'numberEnd',
		   header: "結束值",
		   dataIndex: 'numberEnd',
		   width: 80,
		   editor: new fmItem.NumberField({
			   style:'direction:rtl',
			   allowDecimals:false,
			   style: 'text-align:left',
			   maxValue: 999999
		   })
		}
	]);


	var paperGroupUid = new Ext.form.TextField({
				id:paperGroupUidValue,
				name : "paperGroupUid",
				maxLength:20,
				width:200,
				readOnly:true,
				hidden:true,  
				hiddenLabel:true,
				allowBlank: true
			});
	paperGroupUid.setValue(paperGroupUidValue);
	// by default columns are sortable
	cmItem.defaultSortable = true;

	// this could be inline, but we want to define the Plant record
	// type so we can add records dynamically
	var dataItem = {


	};
	// create the Data Store
	var storeItem = new Ext.data.Store({
		proxy:new Ext.data.MemoryProxy(dataItem),
		reader:new Ext.data.JsonReader({}, [
		   {name: 'fieldSeq'},
		   {name: 'field'},
		   {name: 'fieldAttr'},
		   {name: 'fieldNecess'},
		   {name: 'fieldValue'},
		   {name: 'numberStart'},
		   {name: 'numberEnd'}

		]),
		sortInfo:{ field: "fieldSeq", direction: "ASC" }
	}) ;
	// trigger the data store load
	storeItem.load();

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
		    '-',paperGroupUid
		 ]  
	 }); 

    /*主要 Add Tab Function*/
	
	var gridItem = new Ext.grid.EditorGridPanel({
		id: gridItemId,
		ds: storeItem,
		cm: cmItem,
		sm: smItem,
		tbar: itemToolbar,
		//renderTo: 'paperItem-grid',
		width:divWidth,
		height:divHeight,
		title:tabName,
		tabTip:tabName,
		frame:true,
		loadMask: true,
		stripeRows: true,
		enableColumnMove :false,
		iconCls: 'icon-grid',
		clicksToEdit:1,
		closable: true
	});

	var tabs = Ext.getCmp('paper-Tabs');
	tabs.add(gridItem).show();
	//Load Data in store
	gridItem.removeAll();
	gridItem.getStore().loadData(fieldList);

}//function addTab(tabName, paperGroupUidValue){


function clearQuotationItemGrid(){
	//Clear  下面Item Grid 畫面
	$('paperItem-panel').innerHTML ="";
	$('paperItem-panel').insert("<div id='paperItem-InnerPanel'></div>");

	$('paperItem-panel-button').innerHTML ="";
	$('paperItem-panel-button').insert("<div id='paper-tabs-button'></div><div id='save-tabs-button'></div><div id='preview-button'></div>");

}

function startQuotationItem(versionGrid){
	//Clear  下面Item Grid 畫面
	clearQuotationItemGrid()

	var verPaperVerRecord =  versionGrid.getSelectionModel().getSelected();//得到被選擇的行
	var verPaperVerUid = verPaperVerRecord.get('paperVerUid');


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
				if( groupListJsonData.length > 0 ) {
					for (var i=0 ; i< groupListJsonData.length; i++){
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
					//將Tab定位回第一個Tab Item
					var paperTabs = Ext.getCmp('paper-Tabs');
					paperTabs.getItem(0).show();
				}
		},failure: function(){
			Ext.Msg.alert("Error", "startQuotationItem Connection Error , Please Inform CLT IT");
		}},
		'paperVerUid=' + verPaperVerUid 
	);

	
	/********************
	*	Paper Tabs
	*********************/
	var tabs = new Ext.TabPanel({
		id:'paper-Tabs',
        renderTo:'paper-tabs',
        resizeTabs:true, // turn on tab resizing
        minTabWidth: 180,
        tabWidth:200,
        enableTabScroll:true,
        width:divWidth * 0.85,
        height:240,
        defaults: {autoScroll:true},
		listeners: { beforeremove: removeTabEvent },
		/** 
		 * 返回指定index對應的Panel 
		 * @param {number} index 
		 * @return {Panel}  
		 */  
		getTabByIndex:function(index){  
		  return this.items.itemAt(index);  
		},  
		/** 
		 * 返回指定index對應的標籤頁的Element對象 
		 * @param {number} index 
		 * @return {Ext.Element} 
		 */  
		getStripByIndex:function(index){  
		  return Ext.get(this.getTabEl(this.getTabByIndex(index)));  
		},  
		/** 
		 * 返回指定的panel在tabpanel裡面的index 
		 * @param {Ext.Panel} p 
		 * @return {number} 
		 */  
		getTabIndex:function(tab){  
		  return this.items.indexOf(tab);  
		},  
		/** 
		 * 把某個Tab向左或向右移動 
		 * @param {string/panel} tab 要移動的tab的panel對象或者它的index 
		 * @param {number} dir 方向,負數為向左,正數為向右 
		 */  
		moveTab:function(tab,dir){  
		  var srcIndex = this.items.indexOf(tab);  
		  var targetIndex = srcIndex + dir;  
		  if(targetIndex>=0 && targetIndex<this.items.getCount()){  
			var targetTab = this.items.itemAt(targetIndex);  
			var targetStrip = Ext.get(this.getTabEl(targetTab));  
			var srcTab = this.items.itemAt(srcIndex);  
			var srcStrip = Ext.get(this.getTabEl(srcTab));  
			//互換item的位置  
			this.items.items[srcIndex] = targetTab;  
			this.items.items[targetIndex] = srcTab;  
			//互換Strip的位置  
			dir<0 ? srcStrip.insertBefore(targetStrip): srcStrip.insertAfter(targetStrip);  
			//清理  
			targetTab=null;  
			targetStrip=null;  
			srcTab=null;  
			srcStrip=null;  
			tab=null;  
		  }  
		}  
    });


    //Tabs beforeremove事件響應函數
	function removeTabEvent(tabpanel, tab) {
        Ext.MessageBox.show({
            title: '移除' + tab.title + '確認'
            , msg: '是否移除此報價項目'
            , buttons: Ext.Msg.YESNO
            , icon: Ext.Msg.QUESTION
            , fn: function(btn,text) {
                if (btn == 'yes') {
　　　　　　　　　　//移除beforeremove事件，為了防止tabpanel.remove(tab)時進入死循環
                    tabpanel.un('beforeremove', removeTabEvent);
					//後端 移除
					var paperGroupUidVal = tab.getTopToolbar().items.item(9).getValue();
					Ext.Msg.alert('Message', 'Delete OK, ****請再點選Save Tabs 成為新的版本****', function(){
					});

					/*Ext.lib.Ajax.request(
						'POST',
						'quotation/config/QuoPaperItemDelete.action',
						{success: function(response){	
							if( response.responseText != "ERROR" ) {

								Ext.Msg.alert('Message', 'Delete OK, ****請再點選Save Tabs 成為新的版本****', function(){
								});

							}else{
								Ext.Msg.alert("Error", "Delete Error.");

							}
										
						},failure: function(){

							Ext.Msg.alert("Error", "Connection Error , Please Inform CLT IT");

						}},
						'paperGroupUid=' + paperGroupUidVal
					);*/

　　　　　　　　　　//移除tab
                    tabpanel.remove(tab);
　　　　　　　　　　//增加beforeremove事件
                    tabpanel.addListener('beforeremove', removeTabEvent, tabpanel);

                }
            }
        });
　　　　//這一句很關鍵
        return false;
    }//function removeTabEvent(tabpanel, tab) {


    var addTabsButton = new Ext.Button({
        text: '加入議價項目',
        listeners:{
            "click":function(){
				addTabName(versionGrid);
            }
        },
        iconCls:'add',
		renderTo:'paper-tabs-button'
    })

    var saveAllButton = new Ext.Button({
        text: 'Save Tabs',
        listeners:{
            "click":function(){
				saveAllTabData(versionGrid);
            }
        },
        iconCls:'save',
		renderTo:'save-tabs-button'
    })


    var previewButton = new Ext.Button({
        text: 'Preview 報價單',
        listeners:{
            "click":function(){
				previewQuoData(verPaperVerUid);
            }
        },
        iconCls:'preview',
		renderTo:'preview-button'
    })


	var f = new Ext.form.NumberField({value:'1',width:30});  
	var tabsPanel = new Ext.Panel({
		renderTo: 'paperItem-InnerPanel',
		width:divWidth * 0.85,
		title:'議價單項目',
		//管理grid
		items:[tabs]/*, 
		//Tool Bars
		tbar:[   
			//'移動距離:',f,'-',
			{   
			text:'左移',   
			handler:function(){   
			  tabs.moveTab(tabs.getActiveTab(),-1*parseInt(f.getValue()));   
			}   
		   },{   
			text:'右移',   
			handler:function(){   
			  tabs.moveTab(tabs.getActiveTab(),parseInt(f.getValue()));   
			}   
		   }   
		] */
	});

	function saveAllTabData(versionGrid){
		var tabList = Ext.getCmp('paper-Tabs');
		var tabs = tabList.items.length;
		var verRecords =  versionGrid.getSelectionModel().getSelected();;//得到被選擇的行
		var paperVerUid = verRecords.get('paperVerUid');

		
		if( !checkTabData(versionGrid, tabList) ){
			return;
		}

		var i=0 ;

		var jsonString = "";
		jsonString = "[";
		for (i=0 ; i<tabs; i++){
			var tabGrid = tabList.getItem(i);
			
			var paperGroupSeq = i;
			var paperGroupName = tabGrid.title;
			var paperGroupUidValue = tabGrid.getTopToolbar().items.item(9).getValue();
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
				',\"fieldList\":' + Ext.encode(jsonArray)+"},";
			}else{
				jsonString = jsonString + '{'+
				'\"paperGroupUid\":' + '\"' + paperGroupUidValue + '\"' + 
				',\"paperGroupSeq\":' + '\"' + paperGroupSeq + '\"' + 
				',\"paperGroupName\":' + '\"' + paperGroupName + '\"' + 
				',\"fieldList\":' + Ext.encode(jsonArray)+"}";
			}
		}
		jsonString = jsonString + "]";

		Ext.lib.Ajax.request(
			'POST',
			'quotation/config/QuoPaperItemsSave.action',
			{success: function(response){	
				if( response.responseText != "ERROR" ) {
					var quoJsonData = Ext.util.JSON.decode(response.responseText);
					if( quoJsonData ) {
						var versionList =  quoJsonData[0].versionList;
						var versionStore = versionGrid.getStore();
						versionStore.removeAll();
						versionStore.loadData(versionList);
						versionGrid.getView().refresh();
						//自動選到那一行
						versionGrid.getSelectionModel().selectRow(0);
			
						Ext.Msg.alert('Message', 'Update OK', function(){
						});
					}else{
						Ext.Msg.alert("Error", "Save Error.");
					}

				}else{
					Ext.Msg.alert("Error", "Save Error.");

				}
							
			},failure: function(){

				Ext.Msg.alert("Error", "Connection Error , Please Inform CLT IT");

			}},
			'data=' + encodeURIComponent(jsonString) + 
			'&paperVerUid=' + paperVerUid
		);//Ext.lib.Ajax.request(
		
	}
}

function checkTabData(versionGrid, tabList){

	//Get Version Grid Data
	
	var verRecords =  versionGrid.getSelectionModel().getSelected();;//得到被選擇的行
	var paperVerUid = verRecords.get('paperVerUid');

	var tabs = tabList.items.length;
	var j = 0;
	for (j=0 ; j<tabs; j++){
		var gridItem = tabList.getItem(j);
		var storeItem = gridItem.getStore();
		var paperGroupName = gridItem.title;

		if(storeItem.getCount() == 0 ) {
			Ext.Msg.alert("Waring", "頁面: '" + paperGroupName +  " 需填入資料..");
			return false;
		}

		var jsonData = [];
		var i = 0;
		for( i=0; i < storeItem.getCount(); i ++ ){
			 var rec = storeItem.getAt( i );
			 jsonData[i] = rec;
			 var fieldVal = rec.data['field'];
			 var fieldAttrVal = rec.data['fieldAttr'];
			 var fieldNecessVal = rec.data['fieldNecess'];
			 var fieldValue = rec.data['fieldValue'];
			 var numberStartVal = rec.data['numberStart'];
			 var numberEndVal = rec.data['numberEnd'];

			//正數確認
			if( fieldAttrVal == "POSITIVE NUMBER" ){
				if( fieldValue != "" ){
					if ( fieldValue.isNumber() ){
						if(!fieldValue.isPositiveDecimal(8,8) ){
							Ext.MessageBox.show({
								title:'MESSAGE',
								msg: "TAB: '" + paperGroupName + "' " + "第 '" + (i+1) + "' 列 - " + "'預設值' 必需是正數 ",
								icon: Ext.MessageBox.ERROR
							});
							return false;
						}
					}else{
						Ext.MessageBox.show({
							title:'MESSAGE',
							msg: "TAB: '" + paperGroupName + "' " + "第 '" + (i+1) + "' 列 - " + "'預設值' 必需是正數!",
							icon: Ext.MessageBox.ERROR
						});
						return false;
					}
				}

			}

			//區間數確認
			if( fieldAttrVal == "BETWEEN NUMBER" ){
				if( numberStartVal == "" || numberEndVal == "" ){
					Ext.MessageBox.show({
					   title:'MESSAGE',
					   msg: "TAB: '" + paperGroupName + "' " + "第 '" + (i+1) + "' 列 - " + "因 'BETWEEN NUMBER' 所以 起始值 & 結束值 必需填入",
					   icon: Ext.MessageBox.ERROR
				   });
				   return false;
				}else{
					
					if(numberEndVal < numberStartVal){
						Ext.MessageBox.show({
							title:'MESSAGE',
							msg: "TAB: '" + paperGroupName + "' " + "第 '" + (i+1) + "' 列 - " + "因 'BETWEEN NUMBER' 所以 結束值 需 大於等於 起始值 ",
							icon: Ext.MessageBox.ERROR
						});
						return false;
					}

				}
				if( fieldValue != "" ){									
					if ( fieldValue.isNumber() ){
						fieldValue = ( fieldValue * 1 ) + 0;
						numberEndVal = ( numberEndVal * 1 ) + 0;
						numberStartVal = ( numberStartVal * 1 ) + 0;
						if( fieldValue > numberEndVal || fieldValue < numberStartVal ){
							Ext.MessageBox.show({
								title:'MESSAGE',
								msg: "TAB: '" + paperGroupName + "' " + "第 '" + (i+1) + "' 列 - " + "'預設值' 需在 起始值 & 結束值 區間內 ",
								icon: Ext.MessageBox.ERROR
							});
							return false;
						}

					}else{
						Ext.MessageBox.show({
							title:'MESSAGE',
							msg: "TAB: '" + paperGroupName + "' " + "第 '" + (i+1) + "' 列 - " + "'預設值' 必需是數字!",
							icon: Ext.MessageBox.ERROR
						});
						return false;
					}
				}

			}


			if( fieldVal == "" || fieldAttrVal =="" || fieldNecessVal =="" ){
				Ext.MessageBox.show({
				   title:'MESSAGE',
				   msg: "TAB: '" + paperGroupName + "' " + "第 '" + (i+1) + "' 列 - " + "項目 & 屬性 必需有值",
				   icon: Ext.MessageBox.ERROR
			   });
			   return false;
			}
		 }//for( i=0; i < storeItem.getCount(); i ++ ){

	}//for (j=0 ; j<tabs; j++){
	 return true;
}//checkTabData(versionGrid, tabList){

function previewQuoData(verPaperVerUid){
	var win;
	if(!win){
		win = new Ext.Window({
			title:"Quotation Preview",
			renderTo:Ext.getBody(),
			layout      : 'fit',
			width       : screen.width - 350,
			height      : screen.height - 560,
			closeAction :'hide',
			plain       : true,
			cls:'winHeaderClass',
			html		:'<iframe width="' + (screen.width - 350)+ '" height="' + (screen.height - 560) +'" frameborder="0"' +  ' src=<%=contextPath%>/quotation/config/QuoPaperItemPreview.action?paperVerUid=' + verPaperVerUid +' scrolling="auto" ></iframe>',
			buttons: [{
				text     : 'Close Window',
				handler  : function(){
					win.hide();
				}
			}]
		});
		win.show();

	}

 
}//previewQuoData(verPaperVerUid){


</script> 