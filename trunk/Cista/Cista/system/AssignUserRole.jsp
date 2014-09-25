<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.text.DateFormat"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.List"%>
<%@ page import ="com.cista.system.util.CistaUtil"%>
<%@ page import ="com.cista.system.to.SysUserTo"%>
<%@ page import ="com.cista.system.to.SysDepartmentTo"%>
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

<TITLE>System Assign User Role Function</TITLE>
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
			'User',  
			{  
				extend:'Ext.data.Model',  
				fields:[  
						{name:'userId',mapping:'userId'},  
						{name:'realName',mapping:'realName'},  
						{name:'company',mapping:'company'},  
					    {name:'companyType',mapping:'companyType'},  
						{name:'position',mapping:'position'},
						{name:'email',mapping:'email'},
						{name:'phoneNum',mapping:'phoneNum'},
						{name:'active',mapping:'active'},
						{name:'createBy',mapping:'createBy'},
						{name:'cdt',mapping:'cdt',type:'date',dataFormat:'Y-m-d'},
						{name:'updateBy',mapping:'updateBy'},
					    {name:'udt',mapping:'udt',type:'date',dataFormat:'Y-m-d'}
				]  
			}  
	)

	//創建本地數據源  
	var activeStore = Ext.create(  
			'Ext.data.Store',  
			{  
				fields:['id','name'],  
				data:[  
					  {"id":"1","name":"Active"},  
					  {"id":"0","name":"No Active"}  
				]  
			}  
	);  
	  
	//創建數據源  
	var store = Ext.create(  
			'Ext.data.Store',  
			{  
				model:'User',  
				//設置分頁大小  
				pageSize:10,  
				proxy: {  
					type: 'ajax',  
					url : '<%=contextPath%>/AjaxUserSearchLike.action',  
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
						id:'gUserId',  
						//表頭  
						header:'User ID',  
						width:100,  
						//內容  
						dataIndex:'userId',  
						sortable:true
					   
					 },{  
						 id:'gRealName',  
						 header:'Name',  
						 width:100,  
						 dataIndex:'realName',  
						 sortable:false
					  
						},{  
						 id:'gCompany',  
						 header:'Company',  
						 width:80,  
						 dataIndex:'company',  
						 sortable:false
					  
						},{
						 id:'gDepartment',  
						 header:'Department',  
						 width:80,  
						 dataIndex:'department',  
						 sortable:false
					  
						},{  
						 id:'gCompanyType',  
						 header:'Company Type',  
						 width:100,  
						 dataIndex:'companyType',  
						 sortable:false,
					     hidden:true
					  
						},{  
						 id:'gPosition',  
						 header:'Position',  
						 width:100,  
						 dataIndex:'position',  
						 sortable:false
					  
						},{  
						 id:'gEmail',  
						 header:'Email',  
						 width:200,  
						 dataIndex:'email',  
						 sortable:false
					  
						},{  
						 id:'gPhoneNum',  
						 header:'Phone',  
						 width:100,  
						 dataIndex:'phoneNum',  
						 sortable:false
					  
						},{  
							id:'gActive',  
							header:'Active',  
							width:60,  
							dataIndex:'active',  
							editor:{  
								xtype:'combobox',  
								store:activeStore,  
								displayField:'name',  
								valueField:'id',
								readOnly :true/*,
								listeners:{       
									select : function(combo, record,index){   
										isEdit = true;   
									}   
								}*/
							},
							renderer: function(value) {
								var rec = activeStore.getById(value);
								
								if (rec)
								{
									return rec.get('name');
								}
								
								return '&mdash;';
							}
						},{  
						 id:'gCreateBy',  
						 header:'Creator',  
						 width:60,  
						 dataIndex:'createBy',  
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
						},{  
						 id:'gUpdateBy',  
						 header:'Update By',  
						 width:60,  
						 dataIndex:'updateBy',  
						 sortable:false
					  
						},{  
							id:'gUdt',  
							header:'Update Date',  
							width:120,  
							dataIndex:'udt',  
							//lazyRender: true,  
														  
							renderer: function(value){   
										return value ? Ext.Date.dateFormat(value, 'Y-m-d H:m:s') : '';   
									}
						}  
			],  
			height:335,   
			width:900,   
			title: 'User List',   
			renderTo: 'userGrid',
			dockedItems:[  					   
						   
						 //添加搜索控件  
						 {  
							 dock: 'top',   
							 xtype: 'toolbar',   
							 items: {   
								 width: 400,   
								 fieldLabel: 'Search User ID:',   
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
					showRole(record.get('userId'));      
				}
			}
			  
		}  
	)  
	store.loadPage(1);

    /*
     * Ext.ux.form.ItemSelector Example Code
     */

	var ds = Ext.create('Ext.data.ArrayStore', {
        data: [[123,'One Hundred Twenty Three'],
            ['1', 'One'], ['2', 'Two'], ['3', 'Three'], ['4', 'Four'], ['5', 'Five'],
            ['6', 'Six'], ['7', 'Seven'], ['8', 'Eight'], ['9', 'Nine']],
        fields: ['value','text'],
        sortInfo: {
            field: 'value',
            direction: 'ASC'
        }
    });


    var isForm = Ext.widget('form', {
        title: 'ItemSelector Test',
        width: 700,
        bodyPadding: 10,
        renderTo: 'itemselector',

        tbar:[{
            text: 'Options',
            menu: [{
                text: 'Set value (2,3)',
                handler: function(){
                    isForm.getForm().findField('itemselector').setValue(['2', '3']);
                }
            },{
                text: 'Toggle enabled',
                handler: function(){
                    var m = isForm.getForm().findField('itemselector');
                    if (!m.disabled) {
                        m.disable();
                    } else {
                        m.enable();
                    }
                }
            },{
                text: 'Toggle delimiter',
                handler: function() {
                    var m = isForm.getForm().findField('itemselector');
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
        }],

        items:[{
            xtype: 'itemselector',
            name: 'itemselector',
            anchor: '100%',
            fieldLabel: 'ItemSelector',
            imagePath: '../js/extjs42/examples/ux/css/images/',

            store: ds,
            displayField: 'text',
            valueField: 'value',
            value: ['3', '4', '6'],

            allowBlank: false,
            // minSelections: 2,
            // maxSelections: 3,
            msgTarget: 'side'
        }],

        buttons: [{
            text: 'Clear',
            handler: function(){
                var field = isForm.getForm().findField('itemselector');
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
                    Ext.Msg.alert('Submitted Values', 'The following will be sent to the server: <br />'+
                        isForm.getForm().getValues(true));
                }
            }
        }]
    });



	function showRole(userId){

			alert(userId);
		
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
	#userGrid  {position:absolute; visibility:visible; z-index:3; top:53px; left:5px;}
	#itemselector  {position:absolute; visibility:visible; z-index:2; top:400px; left:5px;}
</style>
<link rel="stylesheet" type="text/css" href="../js/extjs42/examples/ux/css/ItemSelector.css" />
</head>


<BODY bgColor=#FFFFFF leftMargin=0 topMargin=0 >

<div id="functionTitle" >
<table border="0" cellpadding="0" align="left"  width=300 >
	<tr>
		<td class="Title">
		<div align="left">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left" class="OrangeLine">
				<tr>
					<td class="Title">System Assign User Role Function</td>
				</tr>
			</table>
		</div>
		</td>
	</tr>
</table>
</div>
<div id="userGrid" ></div>
<div id="itemselector" ></div>	
</body>
</html>