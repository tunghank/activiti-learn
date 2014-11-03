<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.text.DateFormat"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.List"%>
<%@ page import ="com.cista.system.util.CistaUtil"%>
<%@ page import ="com.cista.system.to.SysUserTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
  
    String contextPath = (String)request.getContextPath();
    String EXCEP_MSG_SESSION =CistaUtil.getMessage("System.error.access.nologin");

  	// get current user info
    SysUserTo curUserTo =
            (SysUserTo) request.getSession().getAttribute(CistaUtil.CUR_USERINFO);
    // check session
    if (null == curUserTo) {
        throw new Exception(EXCEP_MSG_SESSION);
   }
%>
<html>
<head>

<TITLE>System Assign Role's Function Function</TITLE>
<jsp:include page="/common/normalcheck.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">  

<script type="text/javascript">

//下面兩行代碼必須要，不然會報404錯誤  
Ext.Loader.setConfig({enabled:true});  
//我的searchGrid和ext4在同一目錄下，所以引用時要到根目錄去"../"  
Ext.Loader.setPath('Ext.ux','<%=contextPath%>/js/extjs42/examples/ux');  
//預加載  
Ext.require(  
        [  
            'Ext.grid.*',  
            'Ext.toolbar.Paging',  
            'Ext.util.*',  
            'Ext.data.*',  
            //注意引用  
            'Ext.ux.form.SearchField',
			'Ext.form.Panel',
			'Ext.ux.form.MultiSelect',
			'Ext.ux.form.ItemSelector'
         ]  
           
);


Ext.onReady(function(){
    Ext.QuickTips.init();

    function formatDate(value){
        return value ? value.dateFormat('Y/m/d') : '';
    };


	/*************************
	*User Information Grid
	**************************/

	var isEdit = false;   
	//創建Model  
	Ext.define(  
			'Role',  
			{  
				extend:'Ext.data.Model',  
				fields:[  
						{name:'roleId',mapping:'roleId'},  
						{name:'roleName',mapping:'roleName'},  
						{name:'cdt',mapping:'cdt',type:'date',dataFormat:'Y-m-d'}
				]  
			}  
	)
	  
	//創建數據源  
	var store = Ext.create(  
			'Ext.data.Store',  
			{  
				model:'Role',  
				//設置分頁大小  
				pageSize:10,  
				proxy: {  
					type: 'ajax',  
					url : '<%=contextPath%>/AjaxRoleSearchLike.action',  
					reader: {  
						//數據格式為json  
						type: 'json',  
						root: 'root',  
						//獲取數據總數  
						totalProperty: 'total'  
					}  
				},
				sorters:[{property:"userId",direction:"ASC"}],//按qq倒序
				//autoLoad:{params:{start:0,limit:10}}//自動加載，每次加載一頁
				autoLoad:true  
			}  
	); 

	//創建多選框  
	var checkBox = Ext.create('Ext.selection.CheckboxModel');   
	var cellEditing = Ext.create('Ext.grid.plugin.CellEditing',  
			{  
				//表示「雙擊」才可以修改內容（取值只能為「1」或「2」）  
				clicksToEdit:2  
			}  
	  
	); 

	//創建grid  
	var grid = Ext.create('Ext.grid.Panel',{  
		  		  
			store:store,  
			//添加到grid  
			/*selModel: { selType: 'checkboxmodel' ,
						mode: "single",//multi,simple,single；默认为多选multi
						},   //選擇框*/
			//表示可以選擇行  
			disableSelection: false,  
			columnLines: true,   
			loadMask: true,   
			//添加修改功能  
			plugins: [cellEditing] ,  
			columns:[  
					 {  
						id:'gRoleId',  
						//表頭  
						header:'Role ID',  
						width:100,  
						//內容  
						dataIndex:'roleId',  
						sortable:true
					   
					 },{  
						 id:'gRoleName',  
						 header:'Role Name',  
						 width:100,  
						 dataIndex:'roleName',  
						 sortable:false
					  
						},{  
							id:'gCdt',  
							header:'Create Date',  
							width:120,  
							dataIndex:'cdt',  
							//lazyRender: true,					  
							renderer: function(value){   
										return value ? Ext.Date.dateFormat(value, 'Y-m-d H:m:s') : '';   
									}
						}
			],  
			height:335,   
			width:400,   
			title: 'Role List',   
			renderTo: 'roleGrid',
			dockedItems:[  					   
						   
						 //添加搜索控件  
						 {  
							 dock: 'top',   
							 xtype: 'toolbar',   
							 items: {   
								 width: 250,   
								 fieldLabel: 'Search Role:',   
								 labelWidth: 80,   
								 xtype: 'searchfield',   
								 store: store   
							}  
						 },{   
							 dock: 'bottom',   
							 xtype: 'pagingtoolbar',   
							 store: store,   
							 displayInfo: true,   
							 displayMsg: '顯示 {0} - {1} 條，共計 {2} 條',   
							 emptyMsg: '沒有數據'   
						}
			],
			listeners: {
				itemclick: function(dv, record, item, index, e) {
					showRole(record.get('roleName'));      
				}
			}
			  
		}  
	)  
	store.loadPage(1);

    /*
     * Ext.ux.form.ItemSelector Example Code
     */

	//創建Model  
	Ext.define(  
			'Role',  
			{  
				extend:'Ext.data.Model',  
				fields:[  
						{name:'roleId',mapping:'roleId'},  
						{name:'roleName',mapping:'roleName'}
				]  
			}  
	)

	//創建數據源  
	var roleDs = Ext.create(  
			'Ext.data.Store',  
			{  
				model:'Role',  
				proxy: {  
					type: 'ajax',  
					url : '<%=contextPath%>/AjaxRoleList.action',  
					reader: {  
						//數據格式為json  
						type: 'json' 
					}  
				},
				sorters:[{property:"roleId",direction:"ASC"}],
				autoLoad:true  
			}  
	); 

    var isForm = Ext.widget('form', {
        title: 'Role List',
        width: 400,
        bodyPadding: 10,
        renderTo: 'roleList',

        /*tbar:[{
            text: 'Options',
            menu: [{
                text: 'Set value (2,3)',
                handler: function(){
                    isForm.getForm().findField('roleSelector').setValue(['2', '3']);
                }
            },{
                text: 'Toggle enabled',
                handler: function(){
                    var m = isForm.getForm().findField('roleSelector');
                    if (!m.disabled) {
                        m.disable();
                    } else {
                        m.enable();
                    }
                }
            },{
                text: 'Toggle delimiter',
                handler: function() {
                    var m = isForm.getForm().findField('roleSelector');
                    if (m.delimiter) {
                        m.delimiter = null;
                        Ext.Msg.alert('Delimiter Changed', 'The delimiter is now set to <b>null</b>. Click Save to ' +
                                      'see that values are now submitted as separate parameters.');
                    } else {
                        m.delimiter = ',';
                        Ext.Msg.alert('Delimiter Changed', 'The delimiter is now set to <b>","</b>. Click Save to ' +
                                      'see that values are now submitted as a single parameter separated by the delimiter.');
                    }
                }
            }]
        }],*/

        items:[{
            xtype: 'itemselector',
            name: 'roleSelector',
            anchor: '100%',
            fieldLabel: 'Role',
            imagePath: '../js/extjs42/examples/ux/css/images/',

            store: roleDs,
            displayField: 'roleName',
            valueField: 'roleId',
            allowBlank: true,
            msgTarget: 'side'
        }],

        buttons: [{
            text: 'Clear',
            handler: function(){
                var field = isForm.getForm().findField('roleSelector');
                if (!field.readOnly && !field.disabled) {
                    field.clearValue();
                }
            }
        }, {
            text: 'Reset',
            handler: function() {
                isForm.getForm().reset();
            }
        }, {
            text: 'Save',
            handler: function(){
                if(isForm.getForm().isValid()){
					/*alert(Ext.encode(isForm.getForm().getValues()));
                    Ext.Msg.alert('Submitted Values', 'The following will be sent to the server: <br />'+
                        isForm.getForm().getValues());*/

					//得到選中的行
					var record = grid.getSelectionModel().getSelection();
					var userId = record[0].get('userId');
					//alert(userId);

					Ext.Ajax.timeout = 120000; // 120 seconds
					Ext.Ajax.request({  //ajax request test  
								url : '<%=contextPath%>/AjaxSaveUserRoleList.action',  
								params : {  
									data: Ext.encode(isForm.getForm().getValues()),
									userId: userId
								},
								method : 'POST',
								scope:this,
								success : function(response, options) {
									//parse Json data
									var freeback = Ext.JSON.decode(response.responseText);
									var message =  freeback.ajaxMessage;
									var status  =  freeback.ajaxStatus;
									
									if( status == '<%=CistaUtil.AJAX_RSEPONSE_ERROR%>' ){//ERROR
										Ext.MessageBox.alert('Success', 'ERROR : '+  message );
									}else{//FINISH
										Ext.MessageBox.alert('Success', 'FINISH : '+ userId + "'s Role " + message );
										

									}
									
								},  
								failure : function(response, options) {  
									Ext.MessageBox.alert('Error', 'ERROR：' + response.status);  
								}  
							});//End Ext.Ajax.request
                }
            }
        }]
    });


	//Tree

        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

		//EXTJS4.0
		var treeStore = new Ext.data.TreeStore ({
			expanded: true, 
			proxy: {
				type: 'ajax',
				url: '<%=contextPath%>/ShowTree.action'
			},
			sorters: [{
				property: 'id',
				direction: 'ASC'
			},{
				property: 'leaf',
				direction: 'ASC'
			}, {
				property: 'text',
				direction: 'ASC'
			}]
		}); 

	   var tree =  new Ext.tree.Panel({
			id: 'tree-panel',
			autoScroll: true,
			animate:true,
			enableDD:false,
			containerScroll: true,
			rootVisible: false,
			lines: false,
			singleExpand: false,
			useArrows: true,
			cls: 'my-tree',
			store: treeStore
		});
		
		setTimeout(function(){tree.expandAll();},0);
		tree.getRootNode().expand(true);

       var viewport = new Ext.Viewport({
            layout:'border',
            items:[{
                    region:'north',
					contentEl: 'north',
                    id:'north-panel',
                    //title:'North',
					height: 25,
                    layout:'accordion',
                    layoutConfig:{
                        animate:true
                    }
                },{
                    region:'west',
					contentEl: 'west',
                    id:'west-panel',
                    title:'<s:text name="System.system.menu.function"/>',
                    split:true,
                    width: 200,
                    minSize: 150,
                    maxSize: 300,
                    collapsible: true,
                    layout:'accordion',
                    layoutConfig:{
                        animate:true
                    },
                    items:tree
                },{
                    region:'center',
                    contentEl: 'center',
					id:'center-panel',
                    split:true,
                    //title:'Center',
                    margins:'0 0 0 0'
                }
             ]
        });
		
		tree.expandAll();
	//END Tree






	function showRole(roleName){

		var roleSelector = isForm.getForm().findField('roleSelector');
		if (!roleSelector.readOnly && !roleSelector.disabled) {
			roleSelector.clearValue();
		}

		//alert(userId);
		//Change Title
		isForm.setTitle( "  " + roleName + "  Function List" );
		Ext.Ajax.request({  //ajax request test  
				url : '<%=contextPath%>/AjaxUserRoleList.action',  
				params : {  
					query: userId,
				},
				method : 'POST',
				scope:this,
				success : function(response, options) {
					//parse Json data
					var freeback = Ext.JSON.decode(response.responseText);
					var toatl = freeback.total;
					var roles = []; // 空陣列
					for( var i=0; i < toatl; i++ ){
						//alert(freeback['root'][i].roleId);
						roles[i]=freeback['root'][i].roleId;
					}
					isForm.getForm().findField('roleSelector').setValue(roles);
				},  
				failure : function(response, options) {  
					Ext.MessageBox.alert('Error', 'ERROR：' + response.status);  
				}  
		});
		//var role2s = ['-1','-2'];
		//isForm.getForm().findField('roleSelector').setValue(role2s);
	}// End showRole()

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

	/** HTML Layout **/
	#functionTitle  {position:absolute; visibility:visible; z-index:1; top:5px; left:5px;}
	#roleGrid  {position:absolute; visibility:visible; z-index:3; top:45px; left:5px;}
	#roleList  {position:absolute; visibility:visible; z-index:2; top:45px; left:425px;}
</style>
<link rel="stylesheet" type="text/css" href="../js/extjs42/examples/ux/css/ItemSelector.css" />
</head>


<BODY bgColor=#FFFFFF leftMargin=0 topMargin=0 >

<div id="functionTitle" >
<table border="0" cellpadding="0" align="left"  width=400 >
	<tr>
		<td class="Title">
		<div align="left">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left" class="OrangeLine">
				<tr>
					<td class="Title">System Assign Role's Function Function</td>
				</tr>
			</table>
		</div>
		</td>
	</tr>
</table>
</div>
<div id="roleGrid" ></div>
<div id="roleList" ></div>	
</body>
</html>