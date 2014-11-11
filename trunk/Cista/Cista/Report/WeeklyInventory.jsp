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

<TITLE>Weekly Inventory Report</TITLE>
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


	//User Information
	//Form
	var userForm = new  Ext.form.Panel({
        id: 'queryForm',
		title: 'Query Criteria',
		labelAlign: 'left',
		frame:true,
		height:150,
		width:300,
		renderTo: "queryForm",
		bodyPadding: 1,
		autoScroll:true,
		layout : {
			type : 'table',
			columns : 1
		},
		bodyStyle:'padding:1 1 1 1',//表單邊距
		defaults:{//統一設置表單字段默認屬性
			labelSeparator :'：',//分隔符
			width : 250,//字段寬度
			padding : 1,
			allowBlank : false,//是否允許為空
			labelAlign : 'left',//標籤對齊方式
			msgTarget :'side',   //在字段的右邊顯示一個提示信息
			frame:true,
			border: false
		},
		buttons :[
					{
						id:'queryFormSubmit',
						text : 'Submit',
						handler : submit
					},
					{
						id:'queryFormReset',
						text : 'Reset',
						handler : reset
					}
				],
		items : [
					{
						layout : 'anchor',
						border: false,
						items : [
								
									{
										xtype: 'datefield',
										id: 'sdate',
										fieldLabel: '開始時間',
										labelWidth: 80,
										labelAlign: 'right',
										emptyText: '請選擇日期',
										format: 'Y-m-d',
										maxValue: new Date(),
										value: Ext.Date.add(new Date(), Ext.Date.DAY, -2)
									}

								]
					}
				]
		
    });


	function submit(){//提交表單
		//Check Form Data
		
		
		//Submit & Reset Button Disable
		//1.0 List Form Button
		var queryFormSubmit =Ext.getCmp('queryFormSubmit'); 
		//queryFormSubmit.disable();
		

		// 初始化 Excel導出 的按鈕  

         window.location.href = '<%=contextPath%>/WeeklyInventory.action';  


		/*Ext.Ajax.timeout = 120000; // 120 seconds
		Ext.Ajax.request({  //ajax request test  
                    url : '<%=contextPath%>/WeeklyInventory.action',  
                    params : {  
                        //data: Ext.encode(queryForm.getValues())
                    },
                    method : 'POST',
					scope:this,
                    success : function(response, options) {


						alert('0000');
						//parse Json data
						var freeback = Ext.JSON.decode(response.responseText);
						var message =  freeback.ajaxMessage;
						var status  =  freeback.ajaxStatus;
						//queryFormSubmit.enable();
						if( status == '<%=CistaUtil.AJAX_RSEPONSE_ERROR%>' ){//ERROR
							Ext.MessageBox.alert('Success', 'ERROR : '+ message );
						}else{//FINISH
							Ext.MessageBox.alert('Success', 'FINISH : '+ message );
												
							 queryForm.form.reset();
						}
						

                    },  
                    failure : function(response, options) {  
                        Ext.MessageBox.alert('Error', 'ERROR：' + response.status);  
                    }  
                });*/

	}
	function reset(){//重置表單
		queryForm.form.reset();
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

	/** HTML Layout **/
	#functionTitle  {position:absolute; visibility:visible; z-index:1; top:5px; left:5px;}
	#queryForm  {position:absolute; visibility:visible; z-index:3; top:20px; left:5px;}
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
					<td class="Title">Weekly Inventory Report</td>
				</tr>
			</table>
		</div>
		</td>
	</tr>
</table>
</div>
<div id="queryForm" ></div>
<div id="roleFunctionTreeList" ></div>	
</body>
</html>