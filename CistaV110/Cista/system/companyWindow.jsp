<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List"%>
<%@ page import ="com.cista.system.util.CistaUtil"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<HTML>
	<HEAD>
		<TITLE>Choose Company </TITLE>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/common/normalcheck.jsp" />
		<script language="javascript">
			function submitData(){
				//window.opener.document.getElementById("userCompany").value = document.getElementById("selectCompany").value;
				//alert(window.opener.document.getElementById("userCompany").value);
				window.returnValue = document.getElementById("selectCompany").value ;
				self.close();
			}

			function setRadioValue(company){
				document.getElementById("selectCompany").value = company;
			}
		</script>
	</HEAD>
	<BODY bgcolor = "EBF5FF" leftMargin="0" rightMargin="0" topMargin="0" bottomMargin="0">
		<br>
		<form>
			<table align="center" width="90%" border="0" cellpadding="0" cellspacing="0" bordercolordark=#FFFFFF  class="Himax-TableContent" >
			<tr class="portlet-title-bg3">
				<font color ="#003366"><b><div>Choose your company role.</div></b></font>
			</tr>
			<tr class="portlet-title-bg1">
				<td>
					<input type="radio" name="selectCompany" value="<%=CistaUtil.VENDOR_ROLE%>" checked onclick = 'setRadioValue("<%=CistaUtil.VENDOR_ROLE%>");'><%=CistaUtil.VENDOR_ROLE%>
				</td>
			</tr>
			<tr class="portlet-title-bg2">
				<td>
					<input type="radio" name="selectCompany" value="<%=CistaUtil.CUSTOMER_ROLE%>" onclick = 'setRadioValue("<%=CistaUtil.CUSTOMER_ROLE%>");' ><%=CistaUtil.CUSTOMER_ROLE%>
				</td>
			</tr>
			<tr class="portlet-title-bg1">
				<td align = "center">
					<input type="button" value="OK" class="button" onclick ="submitData();"/>
				</td>
			</tr>
			</table>
		</form>
	</BODY>
</HTML>