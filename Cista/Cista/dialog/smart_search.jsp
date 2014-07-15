<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.cista.system.ajax.to.SmartSearchTo"%>
<%
	String contextPath = (String)request.getContextPath();
	List<Map<String, Object>> resultList = (List<Map<String, Object>>)request.getAttribute("result");
	SmartSearchTo ss = (SmartSearchTo) request.getAttribute("SmartSearch");
	ss =null!=ss?ss:new SmartSearchTo();
	String inputField = (String) request.getAttribute("inputField");
	String inputFieldValue = (String) request.getAttribute("inputFieldValue");
	String callbackHandle = (String) request.getAttribute("callbackHandle");
	String columns = "";
	for(String column : ss.getColumns()) {
		columns += "," + column;
	}
	if (columns.length() > 0) {
		columns = columns.substring(1);
	}
	String whereCause = ss.getWhereCause();
	whereCause =null!=whereCause?whereCause:"";
%>
<html>
<head>
<!-- This is header -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<jsp:include page="/common/normalcheck.jsp" />

<script type="text/javascript">
	var vHandle = "<%=callbackHandle%>";
	var vInputField = "<%=inputField%>";
	var vColumns = new Array();
	<%
		for(String column : ss.getColumns()) {
			%>
	vColumns.push('<%=column%>');
			<%
		}
	%>
	function init() {
		autoFitBottomArea('resultPanel', 80, 400);
	}
	window.onload = init;
</script>
</head>
<body>
<form id="selectForm" name="selectForm" action="SmartSearch.action" method="post">
<input type="hidden" name="name" value="<%=ss.getName()%>">

<input type="hidden" name="table" value="<%=ss.getTable()%>">
<input type="hidden" name="keyColumn" value="<%=ss.getKeyColumn()%>">
<input type="hidden" name="columns" value="<%=columns %>">

<input type="hidden" name="title" value="<%=ss.getTitle()%>">
<input type="hidden" name="mode" value="<%=ss.getMode()%>">
<input type="hidden" name="whereCause" value="<%=whereCause%>">
<input type="hidden" name="orderBy" value="<%=ss.getOrderBy()%>">
<input type="hidden" name="inputField" value="<%=inputField %>">
<input type="hidden" name="callbackHandle" value="<%=callbackHandle %>">
<input type="hidden" name="like" value="<%=ss.getLike()%>">
<table width="99%" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<td valign="top" bgcolor="#FFFFFF"><!--Html Start-->
			<!-- Content start -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="portlet_content">
				<tr>
					<td nowrap class="portlet-title-bg1"><span class="portlet-title-nobg">Smart Search :: <%=new String(ss.getTitle().getBytes("iso8859-1"), "utf-8") %></span></td>
				</tr>
			</table>
			<div class="content">
			<table class="portlet_content">
				<tr>
					<td>
					<div class="CLT-redword" id="error"><s:actionerror /><!--ErrorMessage--><%=resultList==null || resultList.size()==0?"No result found.":"" %>&nbsp;</div>
					</td>
				</tr>
				<tr>
					<td>
					<input type="text" class="text" name="inputFieldValue" value="<%=inputFieldValue!=null?inputFieldValue:"" %>">
					<input type="submit" class="button" value="Search">
					</td>
				</tr>
			</table>

<%
	if(resultList != null && resultList.size() > 0) {
%>
<table border=0 cellpadding=0 cellspacing=0 width="100%" class="TableContent02">
<tr>
<td>
<div id="resultPanel" style="overflow:auto;width:100px;height:100px">
			<table class="portlet_content" border="0" cellpadding="1" cellspacing="1">
				<tbody>
				<tr>
					<th width="1%" class="portlet-title-bg5">&nbsp;</th>
					<%
						if (ss != null) {
							for(String column : ss.getColumns()) {
								column = column.trim();
								column = column.substring(column.indexOf(" ")+ 1, column.length());
							%>
					<th class="portlet-title-bg5"><span ><%= column%></span></th>
							<%
							}
						}
					%>
				</tr>
				<%
					int idx = 0;
					for(Map item : resultList) {
						idx ++;
						String tdcss = "class='portlet-title-bg2'";
								
						if(idx%2==0) {
							tdcss = "class='portlet-title-bg3'";
						}

						%>
				<tr>
					<td <%=tdcss %>>
						<%
							if ((ss != null && ss.getMode() == 0)) {
								%>
						<input type="radio" value="<%=item.get(ss.getKeyColumn()) %>" name="item">
								<%
							} else {
								%>
						<input type="checkbox" value="<%=item.get(ss.getKeyColumn()) %>" name="item">
								<%
							}
						%>
					</td>
					<%
						for(String column : ss.getColumns()) {
							column = column.trim();
							column = column.substring(column.indexOf(" ")+ 1, column.length());

							%>
					<td <%=tdcss %>><%=item.get(column)!=null?item.get(column):"&nbsp;" %></td>
							<%
						}
					%>
				</tr>
						<%
					}
				%>
				</tbody>
			</table>
</div>
	</td>
</tr>
</table>
<%
	}
%>
			<table class="portlet_content">
				<tr>
					<td>
					<div align="right">
					  <input
						name="okBtn" type="button" class="button" id="okBtn"
						value="OK" onclick="onSSSelect()">
					  <input
						name="cancelBtn" type="button" class="button" id="cancelBtn"
						value="Cancel" onclick="window.close()">
					</div>
					</td>
				</tr>
			</table>
			</div>
			<!-- Content end --></td>
			<td width="5" valign="bottom"
				background="<%=contextPath %>/images/shadow-1.gif">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				background="<%=contextPath %>/images/bgs.gif">
				<tr>
					<td height="15"><img src="<%=contextPath %>/images/spacer.gif"
						width="1" height="1" alt=""></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="2"><img height="2" alt=""
				src="<%=contextPath %>/images/shadow-2.gif" width="585" border="0"></td>
		</tr>
	</tbody>
</table>
</form>
<!-- This is footer -->

</body>
</html>