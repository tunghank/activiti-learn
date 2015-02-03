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
			'Ext.ux.form.ItemSelector',
			'Ext.grid.plugin.CellEditing'
         ]  
           
);


Ext.onReady(function(){
    Ext.QuickTips.init();

    function formatDate(value){
        return value ? value.dateFormat('Y/m/d') : '';
    };

	//Tree

    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	//EXTJS4.0
	var treeStore = new Ext.data.TreeStore ({
		expanded: true, 
		proxy: {
			type: 'ajax',
			url: '<%=contextPath%>/ShowAllFunctionTree.action'
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


	var functionTree =  new Ext.tree.TreePanel({
		id: 'tree-panel',
		title: 'Function Tree',
		autoScroll: true,
		animate:true,
		enableDD:false,
		containerScroll: true,
		rootVisible: false,
		lines: false,
		singleExpand: false,
		useArrows: true,	
		height:570,   
		width:400,  
		renderTo: 'functionTreeList',
		store: treeStore,
		/*viewConfig: {  
            plugins: {  
                ptype: 'treeviewdragdrop'  
            },  
            listeners: {  
                drop: function(node, data, dropRec, dropPosition) {  
                    store.sync();  
                }  
            }  
        },*/
		listeners: {'cellcontextmenu' : tree_event}
      

	});
	
	setTimeout(function(){functionTree.expandAll();},0);
	functionTree.getRootNode().expand(true);
	functionTree.expandAll();

	//Node增刪
	//定義右擊功能表
	var rightClickMenu = Ext.create('Ext.menu.Menu' ,{
		items: [{
			id:'add',
	        text: '增加節點'
	    },{
	    	id: 'del',
	        text: '刪除節點'
	    }],
	    listeners: {'click': menuClick }
	});
	
	//保存點擊的節點
	var clickNode ;	
	
	//節點菜單事件
	 function menuClick (menu, item, e, eOpts ) {
	 	if(item.id == 'add'){
	 		Ext.MessageBox.prompt('', '輸入節點名:', function (btn,text){ 
		 		if(text == null || text == ''){
		 			return false;
		 		}
		 		var tmpNode = treeStore.getNodeById(clickNode.get('id'));
		 		//if(tmpNode.isLeaf()){ //如果是葉節點，需要改變leaf屬性，再添加節點
				if(tmpNode.data.cls == "folder"){ //如果是葉節點，需要改變leaf屬性，再添加節點
					alert('Leaf');
		 			changeNodeLeafStatus(tmpNode );
					var nowNode =  treeStore.getNodeById(clickNode.get('id'));
					alert("nowNode " + nowNode);
					//往該節點添加子節點
	 				nowNode.appendChild( {
	 					id: 1000,
						parentId:clickNode.get('id'),
						text:text,
						cls: 'file',
						hrefTarget: 'mainFrame',
	                    leaf: true
	                } ) ;
		 		}
		 		//如果不是葉節點，直接添加
				else{
					alert('No Leaf');
		 			tmpNode.appendChild( {
	 					//id: ++idSeq,
						id:1000,
	                    text: text,
	                    leaf:true
	                } ) ;
		 		}
	 		});
	 	}
	 	else if(item.id == 'del'){
	 		var parentNode = clickNode.parentNode;
	 		clickNode.remove();
	 		//刪除後沒有子節點了，那麼父節點就需要改變leaf屬性
	 		if(!parentNode.hasChildNodes()){ 
	 			changeNodeLeafStatus(parentNode);
	 		}
	 		
	 	}
	}
	
	//要改變狀態的節點，必須是葉節點 (leaf= true) 且沒有子節點
   	function changeNodeLeafStatus( node ){
   		if(node.isRoot() || node.hasChildNodes() ) {
   			return false;
   		}
   		var nodeText = node.get('text');
   		var isLeaf = node.isLeaf();
   		var parentNode = node.parentNode;
   		//取得點擊節點的相鄰下一個節點，以便刪除之後插入時做標識，知道從哪裡插入
 		var nextSibling = node.nextSibling;
   		//刪除節點
 		node.destroy();
   		//創建一個一樣的節點
 		parentNode.insertBefore({
			 id: ++idSeq,
			 text: nodeText,
     		 leaf: !isLeaf
		} , nextSibling);
   	}
	
	function tree_event( obj, td, cellIndex, record, tr, rowIndex, e, eOpts)
   	{
   		record.leaf = false;
		clickNode = record;
   		e.preventDefault();
   		rightClickMenu.showAt(e.getXY());
   	}

	//END Tree





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
	#functionTreeList  {position:absolute; visibility:visible; z-index:2; top:45px; left:10px;}
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
<div id="functionTreeList" ></div>	
</body>
</html>