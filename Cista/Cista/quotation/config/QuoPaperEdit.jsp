<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.text.DecimalFormat"%>

<%@ page import ="com.clt.system.util.CLTUtil"%>
<%@ page import ="com.clt.system.to.SysUserTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogTo"%>
<%@ page import ="com.clt.quotation.config.to.QuoCatalogPaperVerTo"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	long t1 = System.currentTimeMillis();
	String contextPath = (String)request.getContextPath();
	// Get Current User
	SysUserTo curUser =
            (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
	
	List<QuoCatalogTo> catalogList = (List<QuoCatalogTo>)request.getAttribute("catalogList");
	catalogList = null != catalogList ? catalogList : new ArrayList<QuoCatalogTo>();

%>
<html>

<head>
	

<jsp:include page="/common/normalcheck.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    

<!-- EXT JS Grid 分開 Function -->
<jsp:include page="/quotation/config/VersionGrid.jsp" />
<jsp:include page="/quotation/config/QuotationItemGrid.jsp" />

<script language="javascript">

function doInit(){
}

function resetCatalog(catalogValue, paperValue, paperUid) {
		$('catalog').value=catalogValue;
		retrievePapers();
		$('paper').value = paperValue;
		$('paperUid').value=paperUid;
}

function retrievePapers() {
	$('paper').value = "";
	$('paperUid').value = "";

	if( $F('catalog') != "" ){
		new Ajax.Request(
			'<%=contextPath%>/quotation/config/FetchPapers.action',
			{
				method: 'post',
				parameters: 'catalogUid='+ $F('catalog'),
				onComplete: retrievePapersComplete
			}
		);
	}else{
		removeOptions($('selPapers'));
	}
}

function retrievePapersComplete(r) {
	var returnValue = r.responseText.split("|");
		
	removeOptions($('selPapers'));
	addOption($('selPapers'),"---" , "");
	for(i = 0; i < returnValue.length ; i ++){
		var myObject = eval('(' + returnValue[i] + ')');
		//alert(myObject.uuid);
		var text =  myObject.paper;
		var value = myObject.paperUid;
		addOption($('selPapers'), text , value);
	}

}

function onClearGrid(){
}

function setPaper(){
	$('paper').value = $('selPapers').options[$('selPapers').selectedIndex].text;
	$('paperUid').value = $('selPapers').value;
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


	/** Version Grid Css for VersionGrid.jsp **/
	#version-grid  {position:absolute; visibility:visible; z-index:2; top:80px; left:5px;}
	#editVer-form  {position:absolute; visibility:visible; z-index:21; top:80px; left:585px;}
	#addVer-form  {position:absolute; visibility:visible; z-index:22; top:80px; left:585px;}
	#addQuot-form  {position:absolute; visibility:visible; z-index:23; top:80px; left:585px;}

	/** Quotation Item Grid Css for QuotationItemGrid.jsp **/
	#paper-tabs-button  {position:absolute; visibility:visible; z-index:999; top:292px; left:90px;} 
	#save-tabs-button  {position:absolute; visibility:visible; z-index:999; top:292px; left:220px;}
	#preview-button  {position:absolute; visibility:visible; z-index:999; top:292px; left:320px;} 
	#paperItem-panel  {position:absolute; visibility:visible; z-index:3; top:290px; left:5px;} 
	#paperItem-grid  {position:absolute; visibility:visible; z-index:3; top:500px; left:5px;} 

	.x-grid3-row{background-color:#CCFF66;}
	.x-grid3-row-alt{background-color:#FFFFCC;}
	
	/** Windows Color 
	.winHeaderClass .x-window-header{
				background-color : #FEFF7C;
			}
	.winHeaderClass .x-window-footer {
		background-color : #FEFF7C;
	}**/
</style>	

</head>
<body bgcolor="#FFFFFF" leftmargin="8" topmargin="16" onLoad="doInit()">


<form name="queryPaperForm" id="queryPaperForm" action="quotation/config/GetCatalogPaperVerList.action" method="post" target ="mainFrame" >
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" >
  <tr>
    <td class="Title">
		<div align="left">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left" class="OrangeLine">
			  <tr>
				<td width="361" class="Title"><s:text name="Quotation.config.quo.config.title"/></td>
				<td width="791" valign="bottom">
					 <div align="right">
					
					</div>
				</td>
				</tr>
			</table>
      </div>
	  </td>
	</tr>

  <tr>
    <td class="Title">
	<table width="90%" border="0" cellpadding="1" cellspacing="0" class="Alertword">
        <tr>
          <td><span class="redword"><p class="redword" id="errorMsg"><s:actionerror /><s:fielderror />&nbsp;</p></span></td>
        </tr>
    </table>

        <table width="70%" border="1" cellpadding="1" cellspacing=0 bordercolordark=#FFFFFF rules=rows class="portlet_content">
		   <tr>


		   <td width="15%" nowrap class="portlet-title-bg1" style="vertical-align: bottom;">
			<div align="left" > <span class="portlet-title-nobg"><s:text name="Quotation.config.quo.config.catalog"/>:</span>
			<select id="catalog" name="catalog" onChange="retrievePapers();onClearGrid()">
				<option value = ""> --- </option>
				<option value = "ALL"> ALL </option>
					<%   for(int i=0; i < catalogList.size(); i++ ){
							QuoCatalogTo catalogTo = (QuoCatalogTo)catalogList.get(i);
					%>
						<option value = '<%=catalogTo.getCatalogUid()%>'><%=catalogTo.getCatalog()%></option>
					<%}%>
			</select>
			</div>
			
		   </td>
		   <td width="35%" nowrap class="portlet-title-bg1" style="vertical-align: bottom;">
			<div align="left"> <span class="portlet-title-nobg"><s:text name="Quotation.config.quo.config.paper"/>:</span>
			<select id="selPapers" name="selPapers" onChange="setPaper();onClearGrid()">
			<option value = ""> --- </option>

			</select>
			&nbsp;&nbsp;<input type="text" id="paper" name="paper" size='20' onFocus="onClearGrid()"/>
			&nbsp;&nbsp;<input type="hidden" name="paperUid" id="paperUid" value="" />
				  			<img src="<%=contextPath%>/images/lov.gif" alt="LOV" id="paperQuerySBtn">
							<script type="text/javascript">
							SmartSearchQuo.setup({
                                cp:"<%=contextPath%>",
                                button:"paperQuerySBtn",
                                inputField:"paper",
                                table:"QUO_PAPER",
								keyColumn:"PAPER",
                                columns:"PAPER,PAPER_UID",
                                title:"Quotation",
								like:1,
                                mode:0,
                                autoSearch:false,
                                callbackHandle:"selectPaperCallback" 
							});	
							function selectPaperCallback(inputField, columns, value) {
								if ($(inputField) && value != null && value.length > 0) {
									var tempText = "";
									var tempValue = "";
									for(var i = 0; i < value.length; i++) {
										tempText = value[i][columns[0]];
										tempValue = value[i][columns[1]];
									}
							
									if(tempValue != "") {
										$(inputField).value = tempText;
										$('paperUid').value = tempValue;
									}
								}
							}	
							</script>
			</div>

		   </td>

		   <td width="15%" nowrap class="portlet-title-bg1" >
				<!--Extjs Button-->
				<div id="div-QueryBtn" align="right" ></div>	
		   </td>

		   <td width="5%" nowrap class="portlet-title-bg1" >
			<div align="left">
			</div>
			
		   </td>
        </table>

	 </td>

</table>
</form>
<!-- Version Grid -->
<div id="version-grid"></div>
<div id="editVer-form"></div>
<div id="addVer-form" style="display:none;"></div>
<div id="addQuot-form" style="display:none;"></div>
<!-- Paper Item Grid -->
<div id="paper-tabs" style="margin:15px 0;"></div>
<div id="paperItem-panel-button"></div>
<div id="paperItem-panel"></div>


<div id="paperItem-grid"></div>
<!-- End Form -->



<%
long t2 = System.currentTimeMillis();
System.out.println("T1 " + (t2 -t1) + " ms");
%>

</body>

</html>