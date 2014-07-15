<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.text.DecimalFormat"%>

<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ page import ="com.clt.system.to.SysUserTo"%>


<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	long t1 = System.currentTimeMillis();
	String contextPath = (String)request.getContextPath();
	// Get Current User
	SysUserTo curUser =
            (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
	
	String inquiryNum = (String)request.getAttribute("inquiryNum");
	inquiryNum = null != inquiryNum ? inquiryNum : "" ;

	String paperVerUid = (String)request.getAttribute("paperVerUid");
	paperVerUid = null != paperVerUid ? paperVerUid : "" ;

	StringBuilder returnJosnString = (StringBuilder)request.getAttribute("returnJosnString");
	returnJosnString = null != returnJosnString ? returnJosnString : new StringBuilder();
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

	//Form
	var notesForm = new Ext.form.FormPanel({
        id: 'notesForm',
		//style: 'padding: 0 0 0 0',
		//bodyStyle: 'padding-right:0px;padding-left:0px;padding-top:0px;padding-bottom:0px;',
		labelAlign: 'left',
		height:35,
		region:'north',
        items: [{
			layout: "column",
			anchor: "0",
			frame: true,
			items: [{
				columnWidth: .05,
				height:20,
				items: [{
					xtype: "label",
					text: '詢價單號:'
				}]
			},{
				columnWidth: .25,
				height:20,
				items: [{
					xtype: "textfield",
					id:'notesFormInquiryNum',
					name: 'notesFormInquiryNum',
					readOnly:true,
					value:'<%=inquiryNum%>',
					style:"background:#EEEEEE;color:#BC6618;"
				}]
			},{
				columnWidth: .05,
				height:20,
				items: [{
					xtype: "label",
					text: '機種料號:'
				}]
			},{
				columnWidth: .25,
				height:20,
				items: [{
					xtype: "textfield",
					id:'notesFormModelPartNum',
					name: 'queryInquiryModelFormPartNum',
					readOnly:true,
					width: 200,
					style:"background:#EEEEEE;color:#BC6618;"
				}]
			},{
				columnWidth: .08,
				height:20,
				items: [{
					xtype: "label",
					text: '比價單狀態:'
				}]
			},{
				columnWidth: .01,
				height:20,
				items: [{
					xtype: "textfield",
					id:'notesFormCompareStatus',
					name: 'notesFormCompareStatus',
					readOnly:true,
					hidden:true,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{
				columnWidth: .25,
				height:20,
				items: [{
					xtype: "textfield",
					id:'notesFormCompareStatusDesc',
					name: 'notesFormCompareStatusDesc',
					readOnly:true,
					style:"background:#EEEEEE;color:#FF0000;"
				}]
			},{//空白行
				columnWidth: .06,
				height:20,
				items: [{}]
			}]
		}]
    });

	//Form
	var labelForm = new Ext.form.FormPanel({
        id: 'labelForm',
		labelAlign: 'left',
		region:'center',
		layout: "column",
		frame: true,
		autoHeight: true,
		width:180,
        items: [{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: '報價單號:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: '報價狀態:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: '主/替料:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: '料號:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: '料號摘要:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: '廠商名稱:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: 'Supplier Site:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: '幣別:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: '運送方式:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: '交易條件:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: '送貨地點:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: '總計:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: '實際報價:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: 'TFT價:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: '稅率:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: '配比:'
				}]
			},{
				columnWidth: 1,
				height:30,
				items: [{
					xtype: "label",
					text: ''
				}]
			}]
    });
	

	//Add Label Form
	//1.0先找出 此張Quotation 有少Items
	Ext.lib.Ajax.request(
		'POST',
		'/PQS/quotation/config/AjaxPaperFildByPaperVer.action',
		{success: function(response){
				//parse Json data
				var groupListJsonData = Ext.util.JSON.decode(response.responseText);

				//alert(groupListJsonData.length);
				for (var i=groupListJsonData.length; i >=0  ; i--){
					var groupData = groupListJsonData[i];
					if( groupData ){
						//alert(groupData);
						var paperGroupName =  groupData.paperGroupName;
						var paperGroupUid  =  groupData.paperGroupUid;
						var paperGroupSeq  =  groupData.paperGroupSeq;
						//alert(paperGroupName);
						var fieldList =  groupData.fieldList;
						//動態列出總計的  Ltem
						var labelFormObj = new Ext.form.Label({
												id: "labelForm" + paperGroupUid,
												text: paperGroupName,
												name: "labelForm" + paperGroupUid,
												columnWidth: 1,
												height:20,
												style:"background:#FFFF99;"
											}); 

						labelForm.items.insert(11, labelFormObj);
						labelForm.render(); 
						labelForm.doLayout(true);

					}
				}
				
				//產生各Form
				var quoteJsonData = Ext.util.JSON.decode('<%=returnJosnString%>');
				if(quoteJsonData != ""){
					var layoutCenter = Ext.getCmp('layout-Center');
					layoutCenter.getLayout().columns = quoteJsonData.length + 1;
					layoutCenter.doLayout();

					for (var i=0; i < quoteJsonData.length ; i++){
							var quoteData = quoteJsonData[i];
							addFieldForm(i , layoutCenter, groupListJsonData, quoteData);
					}
					var inquiryData = quoteJsonData[0];
					
					Ext.getCmp('notesFormCompareStatus').setValue(inquiryData.inquiryHeaderTo.compareStatus);
					Ext.getCmp('notesFormCompareStatusDesc').setValue(inquiryData.inquiryHeaderTo.compareStatusDesc);
					//比價已經完成
					if(inquiryData.inquiryHeaderTo.compareStatus == 'F' ){
						Ext.getCmp('applyCompareBtn').disable();
					}
				}
		},failure: function(){
			Ext.Msg.alert("Error", "startQuotationItem Connection Error , Please Inform CLT IT");
		}},
		'paperVerUid=<%=paperVerUid%>'
	);


	var nextForm = new  Ext.form.FormPanel({
        id: 'nextForm',
        frame: true,
		//style: 'padding: 0 0 0 0',
		headers: {'Content-Type':'multipart/form-data; charset=UTF-8'},
		//style:'font-weight:bold;text-align:left;padding-right:0px;',
        //bodyStyle: 'padding:5px',
		//bodyStyle: 'padding-right:0px;padding-left:3px;padding-top:0px;padding-bottom:0px;',
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
				columnWidth: .3,
				items: [{
					xtype: "label",
					text: '同一料號、不同供應商加起來應配比100%',
					style:"color:#D90000;",
					anchor:'90%'
				}]
			},{
				columnWidth: .45,
				items: [{

				}]
			},{
				columnWidth: .1,
				items: [{
					id:'applyCompareBtn',
					xtype: 'button',
					text: '提出單價申請',
					listeners:{
						"click":function(){
							applyCompareQuotation();
						}
					}
				}]
			}]
        }]
    });
	

	/*********************************************************
	*					Layout
	*********************************************************/

	// Finally, build the main layout once all the pieces are ready.  This is also a good
	// example of putting together a full-screen BorderLayout within a Viewport.

    new Ext.Viewport({
		id:'viewport',
		layout: 'border',
		title: 'Ext Layout Browser',
		forceFit: true,
		//defaults: {autoScroll: true}, 
		items: [
			{id: 'layout-North',
			 region:'north',
			 //style: 'padding: 0 0 0 0',
			 height:35,
			 items: [notesForm]
			},
			{id: 'layout-Center',
	         region:'center',
	         border: false,
			 margins: '0 0 0 0',
	         minSize: 100,
	         maxSize: 500,
			 autoScroll: true,
			 bodyStyle: 'width:100%;',
			 layout:'table',
			 layoutConfig: {
				// The total column count must be specified here
				tableAttrs: { style: { width: '100%' } },
				columns: 1
			 },
			 items: [labelForm]
			},
			{id: 'layout-South',
			 region:'south',
			 //style: 'padding: 0 0 0 0',
			 height:35,
			 items: [nextForm]
			 //border: false*
			}
		],
        renderTo: Ext.getBody()
    });



	function addFieldForm(indexId, layoutCenter, groupListJsonData, quoteData){
		//Form
		var fieldForm = new Ext.form.FormPanel({
			id: 'fieldForm' + indexId,
			name: 'fieldForm' + indexId,
			labelAlign: 'left',
			region:'center',
			layout: "column",
			frame: true,
			autoHeight: true,
			width:180,
			items: [{
					columnWidth: 1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormQuoteNum',
						name: 'fieldFormQuoteNum',
					    value:quoteData.quoteHeaderTo.quoteNum,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormQuoteStatusDesc',
						name: 'fieldFormQuoteStatusDesc',
						value:quoteData.quoteHeaderTo.quoteStatusDesc,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormPartNumDiffer',
						name: 'fieldFormPartNumDiffer',
						value:quoteData.inquiryPartNumTo.inquiryPartNumDiffer,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormPartNum',
						name: 'fieldFormPartNum',
						value:quoteData.inquiryPartNumTo.inquiryPartNum,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormPartNumDesc',
						name: 'fieldFormPartNumDesc',
						value:quoteData.inquiryPartNumTo.inquiryPartNumDesc,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormSupplierName',
						name: 'fieldFormSupplierName',
						value:quoteData.inquirySupplierTo.inquirySupplierName,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormSupplierSite',
						name: 'fieldFormSupplierSite',
						value:quoteData.inquirySupplierTo.inquirySupplierSite,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormCurrency',
						name: 'fieldFormCurrency',
						value:quoteData.inquirySupplierTo.inquiryCurrency,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormShippedBy',
						name: 'fieldFormShippedBy',
						value:quoteData.inquirySupplierTo.inquiryShippedBy,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormPaymentMethod',
						name: 'fieldFormPaymentMethod',
						value:quoteData.inquirySupplierTo.inquiryPaymentMethod,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormDeliveryLocation',
						name: 'fieldFormDeliveryLocationDesc',
						value:quoteData.inquirySupplierTo.inquiryDeliveryLocationDesc,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormQuoteTotal',
						name: 'fieldFormQuoteTotal',
						value:quoteData.quoteHeaderTo.quoteTotal,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormQuoteRealTotal',
						name: 'fieldFormQuoteRealTotal',
						value:quoteData.quoteHeaderTo.quoteRealTotal,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormQuoteTftTotal',
						name: 'fieldFormQuoteTftTotal',
						value:quoteData.quoteHeaderTo.quoteTftTotal,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 0.7,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormTax',
						name: 'fieldFormTax',
						value:quoteData.quoteHeaderTo.quoteTax,
						readOnly:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 0.3,
					height:30,
					items: [{
						xtype: "label",
						//id:'fieldFormTaxLabel',
						name: 'fieldFormTaxLabel',
						text:'%'
					}]
				},{
					columnWidth: 0.7,
					height:30,
					items: [{
						xtype : 'numberfield',
						//id:'fieldFormRatio',
						name: 'fieldFormRatio',
						style:'direction:rtl',
						style: 'text-align:left',
						allowDecimals : false,//允許輸入小數 
						nanText :'請輸入有效的整數',//無效數字提示
						minText : '請輸入0 - 100',
						minValue : 0,
						maxText : '請輸入0 - 100',
						maxValue : 100,
						allowNegative :false//允許輸入負數  
					}]
				},{
					columnWidth: 0.3,
					height:30,
					items: [{
						xtype: "label",
						//id:'fieldFormRatioLabel',
						name: 'fieldFormRatioLabel',
						text:'%'
					}]
				},{
					columnWidth: 0.1,
					height:30,
					items: [{
						xtype: "checkbox",
						//id:'fieldFormApplyCompare',
						name: 'fieldFormApplyCompare'
					}]
				},{
					columnWidth: 0.4,
					height:30,
					items: [{
						xtype: "label",
						//id:'fieldFormApplyCompareLabel',
						name: 'fieldFormApplyCompareLabel',
						text:'申請單價'
					}]
				},{
					columnWidth: 0.1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormSupplierCode',
						name: 'fieldFormSupplierCode',
						value:quoteData.inquirySupplierTo.inquirySupplierCode,
						readOnly:true,
						hidden:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 0.1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormInquiryHeaderUid',
						name: 'fieldFormInquiryHeaderUid',
						value:quoteData.inquiryHeaderTo.inquiryHeaderUid,
						readOnly:true,
						hidden:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				},{
					columnWidth: 0.1,
					height:30,
					items: [{
						xtype: "textfield",
						//id:'fieldFormQuoteStatus',
						name: 'fieldFormQuoteStatus',
						value:quoteData.quoteHeaderTo.quoteStatus,
						readOnly:true,
						hidden:true,
						style:"background:#EEEEEE;color:#BC6618;"
					}]
				}]
		});
		//alert( quoteData.quoteRecordTotalList.length );

		for (var i=groupListJsonData.length; i >=0  ; i--){
			var groupData = groupListJsonData[i];
			if( groupData ){
				//alert(groupData);
				var paperGroupName =  groupData.paperGroupName;
				var paperGroupUid  =  groupData.paperGroupUid;
				var paperGroupSeq  =  groupData.paperGroupSeq;
				//alert(paperGroupName);
				var fieldList =  groupData.fieldList;

				var totalValue=0;
				for( var j=0; j < quoteData.quoteRecordTotalList.length; j++){
					if( paperGroupUid == quoteData.quoteRecordTotalList[j].paperGroupUid ){
						totalValue = quoteData.quoteRecordTotalList[j].recordTotal;
						break;
					}
					
				}
				//Add Item in Field Form
				var quoteTotal = 1;
				if( quoteData.quoteHeaderTo.quoteTotal != "" ){
					quoteTotal = quoteData.quoteHeaderTo.quoteTotal;
				}
				var cRatio = ( totalValue / quoteTotal ) * 100;
				var ratio = new Number(cRatio);
				ratio = ratio.toFixed(2);
				var fieldFormLabelObj = new Ext.form.Label({
										//id: "fieldFormLabel" + paperGroupUid,
										name: "fieldFormLabel" + paperGroupUid,
										columnWidth: 0.3,
										text:(ratio + "%"),
										readOnly:true,
										value:totalValue,
										style:"background:#FFEC9D;color:#BC6618;"
									}); 
				fieldForm.items.insert(11, fieldFormLabelObj);

				var fieldFormObj = new Ext.form.TextField({
										//id: "fieldForm" + paperGroupUid,
										name: "fieldForm" + paperGroupUid,
										columnWidth: 0.7,
										readOnly:true,
										value:totalValue,
										style:"background:#FFFF99;color:#BC6618;"
									}); 
				fieldForm.items.insert(11, fieldFormObj);



				//fieldForm.render(); 
				fieldForm.doLayout(true);			

			}
		}


		layoutCenter.add(fieldForm);
		layoutCenter.doLayout();
	}


	function applyCompareQuotation(){
		var myMask = new Ext.LoadMask(Ext.getBody(), {  
			msg : "Please wait..."  
		});
		
		//找出 各Form
		var quoteJsonData = Ext.util.JSON.decode('<%=returnJosnString%>');
		var layoutCenter = Ext.getCmp('layout-Center');
		
		layoutCenter.getLayout().columns = quoteJsonData.length + 1;
		layoutCenter.doLayout();
		
		var jsonString = "";
		jsonString = "[";
		var flag = 0;

		var totalRatio = 0;
		for (var i=0; i < quoteJsonData.length ; i++){
			var fieldForm = Ext.getCmp('fieldForm' + i).getForm();
			var applyCompare = fieldForm.findField('fieldFormApplyCompare').getValue();
			var ratio = fieldForm.findField('fieldFormRatio').getValue();
			var quoteNum = fieldForm.findField('fieldFormQuoteNum').getValue();
			var supplierCode = fieldForm.findField('fieldFormSupplierCode').getValue();
			var partNum = fieldForm.findField('fieldFormPartNum').getValue();
			var inquiryHeaderUid =  fieldForm.findField('fieldFormInquiryHeaderUid').getValue();

			if(applyCompare){
				flag = flag + 1;

				if( ratio == "" ){
					Ext.MessageBox.show({
						title:'MESSAGE',
						msg: quoteNum + " '配比' 必需有值 ",
						icon: Ext.MessageBox.ERROR
					});
					return false;
				}else{
					totalRatio = totalRatio * 1 + ratio * 1;

					if ( i != quoteJsonData.length - 1 ){
						jsonString = jsonString + '{'+
						'\"quoteNum\":' + '\"' + quoteNum + '\"' + 
						',\"inquiryPartNum\":' + '\"' + partNum + '\"' + 
						',\"inquiryHeaderUid\":' + '\"' + inquiryHeaderUid + '\"' + 
						',\"inquirySupplierCode\":' + '\"' + supplierCode + '\"' + 
						',\"paperVerUid\":' + '\"<%=paperVerUid%>\"' + 
						',\"inquiryNum\":' + '\"<%=inquiryNum%>\"' + 
						',\"ratio\":' + '\"' + ratio + '\"' + "},";
					}else{
						jsonString = jsonString + '{'+
						'\"quoteNum\":' + '\"' + quoteNum + '\"' + 
						',\"inquiryPartNum\":' + '\"' + partNum + '\"' + 
						',\"inquiryHeaderUid\":' + '\"' + inquiryHeaderUid + '\"' + 
						',\"inquirySupplierCode\":' + '\"' + supplierCode + '\"' + 
						',\"paperVerUid\":' + '\"<%=paperVerUid%>\"' + 
						',\"inquiryNum\":' + '\"<%=inquiryNum%>\"' + 
						',\"ratio\":' + '\"' + ratio + '\"' + "}";
					}
				}
			}
			
		}//for (var i=0; i < quoteJsonData.length ; i++){

		//Check Ratio  加總一定要等於 100%
		if( totalRatio > 100 ){
			Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '配比' 加總不能超過 100% ",
				icon: Ext.MessageBox.ERROR
			});
			return false;
		}
		if( totalRatio != 100 ){
			Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " '配比' 加總一定要等於 100% ",
				icon: Ext.MessageBox.ERROR
			});
			return false;
		}


		jsonString = jsonString + "]";

		if( flag == 0 ){
			Ext.MessageBox.show({
				title:'MESSAGE',
				msg: " 沒有勾選 任何的比價項目 ",
				icon: Ext.MessageBox.ERROR
			});
			return false;
		}

		//alert(jsonString);
		myMask.show();
		Ext.lib.Ajax.request(
			'POST',
			'quotation/compare/ApplyPaperCompare.action',
			{success: function(response){
				myMask.hide();
				if( response.responseText != "ERROR" ) {
					var rs = Ext.util.JSON.decode(response.responseText);
					var compareStatus = "";
					if( rs[0].compareStatus == 'F' ){
						compareStatus = "完成單價申請";
					}
					Ext.getCmp('notesFormCompareStatus').setValue(compareStatus);

					Ext.Msg.alert('Message', 'Update OK', function(){
						window.open ('<%=CLTUtil.getMessage("System.WorkFlow.URL")%>' + '?PQSNo=' + rs[0].compareNum + '&OpenForm=true',"blank");
					});
				}else{
					Ext.Msg.alert("Error", "Save Error.");

				}
							
			},failure: function(){
				myMask.hide();
				Ext.Msg.alert("Error", "Connection Error , Please Inform CLT IT");

			}},
			'data=' + encodeURIComponent(jsonString) + 
			'&inquiryNum=<%=inquiryNum%>' + 
			'&paperVerUid=<%=paperVerUid%>'
		);//Ext.lib.Ajax.request(
		myMask.destroy();
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
	
 </body>
</html>
