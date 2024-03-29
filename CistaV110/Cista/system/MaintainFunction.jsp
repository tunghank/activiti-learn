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
	var roleListGrid = Ext.create('Ext.grid.Panel',{  
		  	id:"roleListGrid", 
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
						width:280,  
						//內容  
						dataIndex:'roleId',  
						sortable:true,
						hidden:true
					   
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
			width:370,   
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
					showRoleTree(record.get('roleId'), record.get('roleName'));      
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



	//Tree

    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	//EXTJS4.0
	var treeStore = new Ext.data.TreeStore ({
		expanded: true, 
		/*proxy: {
			type: 'ajax',
			url: '<%=contextPath%>/ShowRoleFunctionTree.action'
		},*/
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

	var roleFunctionTree =  new Ext.tree.TreePanel({
		id: 'tree-panel',
		title: 'Role Function Tree',
		autoScroll: true,
		animate:true,
		enableDD:false,
		containerScroll: true,
		rootVisible: false,
		lines: false,
		singleExpand: false,
		useArrows: true,	
		height:435,   
		width:400,  
		renderTo: 'roleFunctionTreeList',
		store: treeStore,
		listeners:{
            checkchange:function (node,checked,eOpts){
                //選中事件
                setChildChecked(node,checked);
                setParentChecked(node,checked);
            }
        },
        dockedItems: [{
			dock: 'bottom',
            xtype: 'toolbar',
            items: {
                text: 'Save checked function',
				border: 2,
                scale: 'small',
				iconCls: 'save',
                handler: function(){
					//得到Grid選中的行
					var gridRecord = roleListGrid.getSelectionModel().getSelection();
					if(gridRecord.length==0){  
						 Ext.MessageBox.show({   
							title:"提示",   
							msg:"請先選擇'Role' !!!!",
							icon: Ext.MessageBox.ERROR
						 })  
						return;  
					}

					var roleName = gridRecord[0].get('roleName');
					var roleUid = gridRecord[0].get('roleId');

					//alert(roleUid + " " + roleName );

                    var records = roleFunctionTree.getView().getChecked();
                    var names = [];

					var datar = new Array();
					for (var i = 0; i < records.length; i++) {
							datar.push(records[i].data);
					}
					var jsonDataEncode = Ext.encode(datar);

					//alert(jsonDataEncode );

					Ext.Ajax.timeout = 120000; // 120 seconds				
					Ext.Ajax.request({  //ajax request test  
								url : '<%=contextPath%>/AjaxSaveRoleFunctionList.action',
								params : {
									roleUid: roleUid,
									data: jsonDataEncode
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
										Ext.MessageBox.alert('Success', 'FINISH : '+ roleName + "'s Role Function " + message );										
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
	
	setTimeout(function(){roleFunctionTree.expandAll();},0);
	roleFunctionTree.getRootNode().expand(true);
	roleFunctionTree.expandAll();
	//END Tree

	/*****************************************
	* Tree checked
	******************************************/

	function setChildChecked(node,checked){

		node.expand();
		node.set({checked:checked});
		if(node.hasChildNodes()){
			node.eachChild(function(child) {
				setChildChecked(child,checked);
			});
		}
	}

	function setParentChecked(node,checked){
        node.set({checked:checked});
        var parentNode = node.parentNode;
        if(parentNode !=null){
            var flag = false;
            parentNode.eachChild(function(child) {
                if(child.data.checked == true){
                    flag = true;
                }
            });
            if(checked == false){
                if(!flag){
                    setParentChecked(parentNode,checked);
                }
            }else{
                if(flag){
                    setParentChecked(parentNode,checked);
                }
            }
         }
    }


	function showRoleTree(roleUid, roleName){

		roleFunctionTree.setTitle( "  " + roleName + "  Function List" );
		Ext.Ajax.timeout = 120000; // 120 seconds				
		Ext.Ajax.request({  //ajax request test  
					url : '<%=contextPath%>/ShowRoleFunctionTree.action',
					params : {
						roleUid: roleUid
					},
					method : 'POST',
					scope:this,
					success : function(response, options) {
						//parse Json data
						var freeback = Ext.JSON.decode(response.responseText);
						
						//Load Data in Tree store
						roleFunctionTree.getRootNode().removeAll();
						roleFunctionTree.getRootNode().appendChild(freeback);
						
						roleFunctionTree.getRootNode().expand(true);
						
						/*var message =  freeback.ajaxMessage;
						var status  =  freeback.ajaxStatus;

						if( status == '<%=CistaUtil.AJAX_RSEPONSE_ERROR%>' ){//ERROR
							Ext.MessageBox.alert('Success', 'ERROR : '+  message );
						}else{//FINISH
							Ext.MessageBox.alert('Success', 'FINISH : '+ roleName + "'s Role Function " + message );										
						}*/
						
					},  
					failure : function(response, options) {  
						Ext.MessageBox.alert('Error', 'ERROR：' + response.status);  
					}  
		});//End Ext.Ajax.request

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
	#roleFunctionTreeList  {position:absolute; visibility:visible; z-index:2; top:45px; left:420px;}
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
<div id="roleFunctionTreeList" ></div>	
</body>
</html>