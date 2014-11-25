<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="com.cista.system.util.CistaUtil"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%
  String contextPath = (String)request.getContextPath();
%>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="copyright">
  <tr>
    <td valign="top" align="center"><font color="red"><s:text name="System.system.title"/>:&nbsp;&nbsp;</font><a href=mailto:<s:text name="System.system.contantus.email"/> ><s:text name="System.system.contantus.email"/></a>
	<br>
	<font color="#CC0000"><s:text name="System.layout.support.browser"/></font>
	<br>
	<s:text name="System.layout.message.footer"/>
	</td>
  </tr>

</table>