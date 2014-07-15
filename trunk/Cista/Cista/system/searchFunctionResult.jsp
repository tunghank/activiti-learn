<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import ="com.cista.system.util.CLTUtil"%>
<%@ page import ="com.cista.system.to.SysFunctionTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
	String contextPath = (String)request.getContextPath();

	// for paging
	String pageSize = (String)request.getAttribute(CLTUtil.PAGE_SIZE);
    String resultSize = (String)request.getAttribute(CLTUtil.RESULT_SIZE);
    String pages = (String)request.getAttribute(CLTUtil.PAGES);
    String pageNo = (String)request.getAttribute(CLTUtil.PAGENO);

	//Criteria
	String parentId = (String)request.getAttribute("parentId");
	String cls = (String)request.getAttribute("cls");

	List<SysFunctionTo> data = (List<SysFunctionTo>) request.getAttribute("data");
	int dataSize = 0 ;
	if (data != null) dataSize = data.size();

%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE>System Function Search Function</TITLE>
	<jsp:include page="/common/normalcheck.jsp" />

	<script language="javascript">
		function doInit(){
<%
			if(data != null && dataSize>0){
%>
				var form = document.forms(0);
				if (Number(form.pageNo.value)>=<%=pages%>){
					nextButton.removeAttribute("href");
					lastButton.removeAttribute("href");
				}
				if (Number(form.pageNo.value)<=1){
					prevButton.removeAttribute("href");
					firstButton.removeAttribute("href");
				}
<%
			}
%>
		}

		function nextPage(){
			document.forms(0).pageNo.value = Number(document.forms(0).pageNo.value) + 1;
			document.forms(0).action = "<%=contextPath%>/SearchFunction.action";
			document.forms(0).submit();
		}

		function prevPage(){
			document.forms(0).pageNo.value = Number(document.forms(0).pageNo.value) - 1;
			document.forms(0).action = "<%=contextPath%>/SearchFunction.action";
			document.forms(0).submit();
		}

		function goFirstPage(){
			document.forms(0).pageNo.value = 1;
			document.forms(0).action = "<%=contextPath%>/SearchFunction.action";
			document.forms(0).submit();
		}

		function goLastPage(){
			document.forms(0).pageNo.value = <%=pages%>;
			document.forms(0).action = "<%=contextPath%>/SearchFunction.action";
			document.forms(0).submit();
		}

		function selAll(){
			var forma = document.forms(0);
			if (forma.chkALL.checked==true){
				for (i=0 ; i<forma.chkDeleteList.length; i++){
					forma.chkDeleteList[i].checked = true;
				}
			}else{
				for (i=0 ; i<forma.chkDeleteList.length; i++){
					forma.chkDeleteList[i].checked = false;
				}
			}
		}
		
		// delete function
		function deleteFunction()
		{
			var count = 0;
			var forma = document.forms(0);
		
			if(forma.chkDeleteList.length > 1)
			{
				for (i=0 ; i<forma.chkDeleteList.length; i++)
				{
					if( forma.chkDeleteList[i].checked == false )
					{
						forma.chkDeleteList[i].value = "-1";				
					}
					else 
					{
						count = count+1;
					}
				}
			}
			else
			{
				if(forma.chkDeleteList.checked) { count = 1;}			
			}
			
			if(count == 0)
			{
				alert("please at least choosing one item !!");
				return ;
			}
			
			var forma = document.forms(0);
			forma.action = "DeleteFunction.action";
			forma.method = "post";
			forma.target = "result";
			forma.submit();
		}

		function modifySubcon(inFunctionID){
			var forma = document.forms(0);
			$('selectFunction').value =inFunctionID;
			forma.action = "ModifyFunctionPre.action";
			forma.method = "post";
			forma.target = "result";
			forma.submit();
		}

		function createFunction(){
			var forma = document.forms(0);
			forma.action = "FunctionCreatePre.action";
			forma.method = "post";
			forma.target = "result";
			forma.submit();
		}
	</script>
</head>

<body bgcolor="#FFFFFF" onload="doInit()" leftMargin="0" rightMargin="0" topMargin="0" bottomMargin="0">
<s:actionmessage/>
<%
	if(data != null && dataSize>0){
		int resultFrom = (Integer.parseInt(pageNo)-1)*Integer.parseInt(pageSize)+1;
		int resultTo = resultFrom + data.size()-1;
%>
	<form>

	<!-- need modify start-->
	<input type="hidden" id="parentId" name="parentId" value="<%=parentId%>"/>
	<input type="hidden" id="cls" name="cls" value="<%=cls%>"/>	
	<input type="hidden" id="pageNo" name="pageNo" value="<%=pageNo%>"/>
	<input type="hidden" id="selectFunction" name="selectFunction"/>

	<!-- need modify end-->
	<div align="left">
		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="left">
  			<tr>
	 			<td>
					<input type="button" value=<s:text name="IE.maintain.button.delete"/> class="button" onClick='deleteFunction();'/>
					<input type="button" value=<s:text name="IE.maintain.button.create"/> class="button"  onClick='createFunction();'/>
					<a href="javascript:goFirstPage()" class="portlet_content" id="firstButton">the first page </a> |
					<a href="javascript:prevPage()" class="portlet_content" id="prevButton">&lt;previous <%=pageSize%> </a> |
					<%=resultFrom%> - <%=resultTo%> of <%=resultSize%> |
					<a href="javascript:nextPage()" class="portlet_content" id="nextButton"> next <%=pageSize%> &gt;</a> |
					<a href="javascript:goLastPage()" class="portlet_content" id="lastButton">the last page </a> &nbsp;&nbsp;|
				</td>
			</tr>
			<tr>
				<td><hr color="#A60404" size="1"/></td>
			</tr>
			<tr>
				<td>
				<div id="tb2-container">
				<table id="formdata" width="100%"  border=1 cellpadding=0 cellspacing=0 bordercolordark=#FFFFFF  class="Himax-TableContent" >
					<thead>
						<tr class= "portlet-title-bg3">
							<th class= "Himax-asl-col-middle" colspan="2">Check All
								<input type="checkbox" name="chkALL" onClick='selAll()'>
							</th>
							<th class= "Himax-asl-col-middle">Parent Name</th>
							<th class= "Himax-asl-col-large">Function Name</th>
							<th class= "Himax-asl-col-large">Type</th>
							<th class= "Himax-asl-col-large">URL</th>
							<th class= "Himax-asl-col-large">Target</th>
						<tr>
					</thead>

					<tbody>
<%
		int i=0;
		String className = "";

		for(SysFunctionTo functions : data ){
			className = ((i++)%2==0 ? "portlet-title-bg1":"portlet-title-bg2");
			String strFunctionId = functions.getId();
			String strParentName = functions.getParentName().equals("")?" ":functions.getParentName();
			String strFunctionName = functions.getTitle().equals("")?" ":functions.getTitle();
			String strType = functions.getCls().equals("")?" ":functions.getCls();
			String strUrl = functions.getUrl()==null?" ":functions.getUrl();
			String strTarget = functions.getHrefTarget()==null?" ":functions.getHrefTarget();
%>
				<tr class="<%=className%>">
					<td class= "Himax-asl-col-small"><%=i+resultFrom-1%></td>
					<td class= "Himax-asl-col-smaill">
						<input type="checkbox" name="chkDeleteList" value='<%=strFunctionId%>'>
					</td>
					<td class= "Himax-asl-col-large">&nbsp;<%=strParentName%></td>
					<td class= "Himax-asl-col-large">
						<a href="javascript:modifySubcon('<%=strFunctionId%>')"><%=strFunctionName%>
					</td>
					<td class= "Himax-asl-col-small">&nbsp;<%=strType%></td>
					<td class= "Himax-asl-col-large">&nbsp;<%=strUrl%></td>
					<td class= "Himax-asl-col-middle">&nbsp;<%=strTarget%></td>
				</tr>
<%		}	%>
				</tbody>
				</table>
				</div>
				</td>
			</tr>
		</table>
	</div>
	</form>

<% } %>

</body>
</html>