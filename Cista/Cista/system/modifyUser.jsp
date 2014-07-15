<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.text.DateFormat"%>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="javax.swing.JOptionPane"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import ="com.cista.system.to.SysUserTo"%>
<%@ page import ="com.cista.system.to.SysRoleTo"%>
<%@ page import ="com.cista.system.to.TSAPVendorTo"%>
<%@ page import ="com.cista.system.to.TSAPCustomerTo"%>
<%@ page import ="com.cista.system.to.SysUserRoleTo"%>
<%@ page import ="com.cista.system.util.CLTUtil"%>
<%@ page import ="com.cista.system.to.SysDepartmentTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
	SysUserTo curUser = (SysUserTo) request.getAttribute("CurUser");
	SysUserTo inUser = (SysUserTo) session.getAttribute(CLTUtil.CUR_USERINFO);

	// Data index
	String curUserCompany = (curUser != null)?curUser.getCompany():"";
	String curUserDepartment = (curUser != null)?curUser.getDepartment():"";
	String curUserPosition = (curUser != null)?curUser.getPosition():"";
	String userCompany = (String)request.getAttribute("userCompany");

	// Data List
	List<SysDepartmentTo> 	allDepartments = (List<SysDepartmentTo>) request.getAttribute("DepartmentList");
%>

<html>
<head>
	<TITLE>System User Modify Function</TITLE>
	<jsp:include page="/common/normalcheck.jsp" />
	
	<script type="text/javascript">

		var jsComany = "<%=curUserCompany%>" ;
		var flag = 0;

		function doInit(){
			if ( jsComany == "<%=CLTUtil.CLT_ROLE%>"){
				SelectItem_Combo2($('chkDepartmentList') , "<%=curUserDepartment%>");
				SelectItem_Combo2($('chkPositionList') , "<%=curUserPosition%>");
			}else{
				SelectItem_Combo_Value($('chkCompanyList') , "<%=curUserCompany%>");
				if ($F('chkCompanyList') == ""){
					alert(" Your company is not choosing , please check your Conpany Role is correct !!");
				}
			}
		}

		function checkData(){
			var cnt=0;
			msg.innerHTML = "Verify Data Error , please check your data is correct !!";
			var reg =/^[a-z_0-9]+@+[a-z_0-9]+([.][a-z_0-9]+)*$/;
			flag = 0;

			// �@�q�ʸ�ƽT�{
			if ($F('realName') == ""){ 
				//msg.innerHTML += "<br>" + (++cnt)+". " + " Real Name must be input";
				flag=1;
				alert("Real Name must be input");
				return;
			}

			// �̤��P����T�{
			if (jsComany == "<%=CLTUtil.CLT_ROLE%>"){
				if ($F('email') == ""){ 
					//msg.innerHTML += "<br>" + (++cnt)+". " + " Email must be input";
					flag=1;
					alert("Email must be input");
					return;
				}
				if ($F('chkDepartmentList') == ""){ 
					//msg.innerHTML += "<br>" + (++cnt)+". " + " Department must be select";
					flag=1;
					alert("Department must be select");
					return;
				}
				if ($F('chkPositionList') == ""){ 
					//msg.innerHTML += "<br>" + (++cnt)+". " + " Postition must be select";
					flag=1;
					alert("Postition must be select");
					return;
				}
				if($F('email') != "" && !reg.test($('email').value)){
					//msg.innerHTML += "<br>" + (++cnt)+". " + " Email format error";
					flag=1;
					alert("Email format error");
					return;
				};
			}
			// for subcon
			else{
				if ($F('chkCompanyList') == ""){
					//msg.innerHTML += "<br>" + (++cnt)+". " + " Company must be select";
					flag=1;
					alert("Company must be select");
					return;
				}
				if ($F('password') == ""){
					//msg.innerHTML += "<br>" + (++cnt)+". " + " Password must be input";
					flag=1;
					alert("Password must be input");
					return;
				}
			}
			if(flag == 0) msg.innerHTML = "";
		}
		
		// �]�w �Ǧ^�Ѽ�
		function setParameters(){
			checkData();
			if (flag==0){
				msg.innerHTML = "Processing data , please wait a minutes...";
				if (jsComany == "<%=CLTUtil.CLT_ROLE%>"){
					$('department').value = $('chkDepartmentList').value;
					$('position').value = $('chkPositionList').value;
				}else{
					$('company').value = $('chkCompanyList').value;
					$('department').value = $('subconDepartment').value;
					$('position').value = $('subconPostion').value;
				}
				userModify();
			}
		}

		function userModify(){
			var forma = document.forms(0);
			forma.action = "ModifyUser.action";
			forma.method = "post";
			forma.target = "result";
			forma.submit();
		}
	</script>
		
</head>
<body bgcolor="#FFFFFF" leftMargin="0" rightMargin="0" topMargin="0" bottomMargin="0" onload="doInit();">
	<form name="userModifyForm" id="userModifyForm" method="post">

		<table width="100%" border="0" cellspacing="0" cellpadding="0">	
			<tr>
			<!--message-->
			<td>
				<font color="red"><b><div id="msg"></b></div></font>
				<font color="red"><div><s:actionmessage/></div></font>
			</td>
			</tr>
		</table>
<%
	if (curUser != null ){
		String strUserId = curUser.getUserId() == null ? "":curUser.getUserId();
		String strRealName = curUser.getRealName() == null ? "" : curUser.getRealName();
		String strPhone = curUser.getPhoneNum() == null ? "" : curUser.getPhoneNum();
		String strEmail = curUser.getEmail() == null ? "" : curUser.getEmail();
		String strPosition = curUser.getPosition() == null ? "" : curUser.getPosition();
		String strDepartment = curUser.getDepartment() == null ? "" : curUser.getDepartment();
%>
		<table id="formdata" width="40%"  border=1 cellpadding=0 cellspacing=0 bordercolordark=#FFFFFF  class="Himax-TableContent" >

			<thead><tr class= "portlet-title-bg3"><th colspan="2" >Common Column</th></tr></thead>

			<tbody>
				<input type="hidden" id ="lastTime" name = "lastTime"/>  
				<input type="hidden" id ="lastIp" name = "lastIp"/>
				<input type="hidden" id ="updateBy" name = "updateBy" value = "<%=inUser.getUserId()%>" />
				<input type="hidden" id ="userId" name="userId" value="<%=curUser.getUserId()%>"/>
				<input type="hidden" id ="company" name="company" value="<%=curUser.getCompany()%>"/>
				<input type="hidden" id ="selectUserId" name="selectUserId" value="<%=curUser.getUserId()%>"/>
				<input type="hidden" id ="department" name="department" />
				<input type="hidden" id ="position" name="position" />

				<!--user id-->
				<tr class = "portlet-title-bg1">
					<td>User ID<BLINK class = "Himax-col-star"> *</BLINK></td>
					<td><%=strUserId%></td>
				</tr>

				<!--user name-->
				<tr class = "portlet-title-bg2">
					<td>Rele Name<BLINK class = "Himax-col-star"> *</BLINK></td>
					<td>
						<input 	type="text" id = "realName" name="realName" onChange = "checkData();"
								class = "Himax-col-width" value = "<%=strRealName%>"/>
					</td>
				</tr>

				<!--user Phone num-->
				<tr class = "portlet-title-bg1">
					<td>Phone Number</td>
					<td>
						<input 	type="text" id = "phoneNum" name="phoneNum"
								class = "Himax-col-width" value = "<%=strPhone%>"/>
					</td>
				</tr>
			</tbody>

			<!--subcon column--->
			<%if(!curUserCompany.equals(CLTUtil.CLT_ROLE)){%>

				<!--vendor/customer company-->
				<tr class = "portlet-title-bg1">
					<td>Company<BLINK class = "Himax-col-star"> *</BLINK></td>
					<td>
						<select name="chkCompanyList" id ="chkCompanyList" onChange = "checkData();"
								class = "Himax-col-width">
							<option value = "">-----</option>
					<%
						if(userCompany.equals(CLTUtil.VENDOR_ROLE)){
							List<TSAPVendorTo> allCompany = (List<TSAPVendorTo>) request.getAttribute("CompanyList");
							if (allCompany != null && allCompany.size() >0 ){
								for(TSAPVendorTo info : allCompany){
					%>
								<option value = <%=info.getVendorCode()%>><%=info.getVendorCode()%> - <%=info.getShortName()%></option>
					<%}
							}
						}else if (userCompany.equals(CLTUtil.CUSTOMER_ROLE)){
							List<TSAPCustomerTo> allCompany = (List<TSAPCustomerTo>) request.getAttribute("CompanyList");
							if (allCompany != null && allCompany.size() >0 ){
								for(TSAPCustomerTo info : allCompany){
					%>
								<option value = <%=info.getCustomerCode()%>><%=info.getCustomerCode()%> - <%=info.getShortName()%></option>
					<%
								}
							}
						}
					%>
						</select>
					</td>
				</tr>

				<!--password-->
				<tr class = "portlet-title-bg2">
					<td>Password<BLINK class = "Himax-col-star"> *</BLINK></td>
					<td><input 	type="text" 
								id = "password" name="password" class = "Himax-col-width" onChange = "checkData();"
								value = "<%=(curUser.getPassword() != "Decode Error" ? CLTUtil.decodePasswd(curUser.getPassword()) : curUser.getPassword())%>"/>
					</td>
				</tr>

				<!--department-->
				<tr class = "portlet-title-bg1">
					<td>Department</td>
					<td>
						<input 	type="text" id = "subconDepartment" name="subconDepartment" 
								class = "Himax-col-width" value = "<%=strDepartment%>"/>
					</td>
				</tr>

				<!--position-->
				<tr class = "portlet-title-bg2">
					<td>Position</td>
					<td>
						<input 	type="text" id = "subconPostion" name="subconPostion" 
								class = "Himax-col-width" value = "<%=strPosition%>"/>
					</td>
				</tr>
			<%
				}else{
			%>
				<!--email-->
				<tr class = "portlet-title-bg1">
					<td>Email<BLINK class = "Himax-col-star"> *</BLINK></td>
					<td>
						<input 	type="text" id = "email" name="email" onChange = "checkData();"
								class = "Himax-col-width" value = "<%=strEmail%>"/>
					</td>
				</tr>
				
				<!--department-->
				<tr class = "portlet-title-bg2">
					<td>Department<BLINK class = "Himax-col-star"> *</BLINK></td>
					<td>
					<%
						if ( allDepartments  != null && allDepartments.size() > 0){
					%>
						<select name="chkDepartmentList" id ="chkDepartmentList" onChange = "checkData();"
								class = "Himax-asl-col-middle">
							<option value = "">-----
							<%for (SysDepartmentTo depart : allDepartments){%>
								<option value = <%=depart.getDepartName()%>><%=depart.getDepartName()%>
							<%}%>
						</select>
					<%
						}
					%>
					</td>
				</tr>

				<!--position-->
				<tr class = "portlet-title-bg1">
					<td>Position<BLINK class = "Himax-col-star"> *</BLINK></td>
					<td>
						<select name="chkPositionList" id ="chkPositionList" onChange = "checkData();"
								class = "Himax-asl-col-middle">
							<option value = "">-----
							<%for (int i = 0 ; i<CLTUtil.POSITION.length ; i++){%>
								<option value = <%=CLTUtil.POSITION[i]%>><%=CLTUtil.POSITION[i]%>
							<%}%>
						</select>
					</td>
				</tr>
			<%
				}
			%>

				<!--button-->
				<tr class = "portlet-title-bg3">
					<td align="right" colspan ="3">
						<input 
							name = "saveBtn" id = "saveBtn"
							type="button" onclick = "setParameters();" value=<s:text name="IE.maintain.button.save"/>
							class="button" />&nbsp;&nbsp;
						<input 
							name = "resetBtn" id ="resetBtn" 
							type="reset" value=<s:text name="IE.maintain.button.reset"/> 
							class="button" />
					</td>
				</tr>
		</table>
<% }%>
	</form>

</body>
</html>