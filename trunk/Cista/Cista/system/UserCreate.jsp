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
  List<SysDepartmentTo> allDepartments = (List<SysDepartmentTo>) request.getAttribute("department");
  
  // current user
  SysUserTo curUser = (SysUserTo) session.getAttribute(CistaUtil.CUR_USERINFO);
  String curUserId = curUser.getUserId();  
%>
<html>
<head>

	<TITLE>System User Create Function</TITLE>
	<jsp:include page="/common/normalcheck.jsp" />

	<script type="text/javascript">
		var curUserRole;
		var imgErrorStart = "<img src='images/uncheck.gif' width='15px' height='15px' style='cursor: hand'";
		var imgAlt = "";
		var imgErrorEnd = ">";
		var errorFlag = 0; 			// 是否送出資料
		var pressSubmitFlag =0; // 是否按過 Submit Button
		var pressADFlag =0 ;    // 是否按過 AD button

		// loading to do
		function doInit(){
			userType("<%=CistaUtil.CISTA_ROLE%>")
		}

		// user 類型切換
		function userType(v){
			if (v == "<%=CistaUtil.CISTA_ROLE%>"){
				$('rowPassword').hide();
				$('rowConfirmPassword').hide();
				$('rowSubconPosition').hide();	$('rowHimaxPosition').show();
				$('rowHimaxEmail').show();		$('himaxEmailBox').show();
				$('rowSubconDepartment').hide();$('rowHimaxDepartment').show();
				$('fetchAD').show();
				$('rowVendorCompany').hide();
				$('rowCustomerCompany').hide();
			}else {
				if (v == "<%=CistaUtil.VENDOR_ROLE%>"){
					$('rowVendorCompany').show();
					$('rowCustomerCompany').hide();
				}
				else if (v == "<%=CistaUtil.CUSTOMER_ROLE%>"){
					$('rowVendorCompany').hide();
					$('rowCustomerCompany').show();
				}
				$('rowPassword').show();
				$('rowConfirmPassword').show();
				$('rowSubconPosition').show(); 	$('rowHimaxPosition').hide();
				$('rowSubconDepartment').show();$('rowHimaxDepartment').hide();
				$('himaxEmailBox').hide(); $('rowHimaxEmail').hide();
				$('fetchAD').hide();
			}
			curUserRole = v;
			// 清除資料
			cleanAllData();
			pressSubmitFlag = 0;
		}

		// 更改使用者資料時,變更相關AD欄位
		function clearUserADData(){
			if (pressADFlag != 1) return;
			$('realName').value = "";
			$('emailID').value = "";
			SelectItem_Combo_Value($('himaxEmailBox') , "");
		}

		//腳色切換時,清除填入資料
		function cleanAllData(){
			clearUserADData();
			$('userId').value = ""; 
			$('phoneNum').value = "";
			$('vendorCompany').value = "";
			$('rowCustomerCompany').value = "";
			$('password').value = "";
			$('confirmPassword').value = "";
			$('emailID').value = "";
			$('himaxEmailBox').value = "";
			$('phoneNum').value = "";
			$('himaxDepartment').value = "";
			$('himaxPosition').value = "";
			$('subconDepartment').value = "";
			$('subconPosition').value = "";
			msg.innerHTML = "";
		}
		
		// 前端資料驗證
		function checkData(){
			if (pressSubmitFlag !=1) return ;
			var reg =/^[a-z_0-9]+@+[a-z_0-9]+([.][a-z_0-9]+)*$/;
			//var cnt=0;
			msg.innerHTML = "Verify data error , please check your data is correct !!";
			errorFlag = 0;

			// 共通性資料確認
			if ($F('userId') == ""){
				errorFlag=1;
				alert("User Id must be input");
				return;
			}
			if ($F('realName') == ""){
				errorFlag=1;
				alert("Real Name must be input");
				return;
			}

			// 依不同角色確認
			// himax
			if (curUserRole == "<%=CistaUtil.CISTA_ROLE%>"){
				if ($F('userId') != "" && $F('userId').length != "6"){ 	// user ID不可小於或大於 6 個字
					errorFlag=1;
					alert("User id must in 6 characters");
					return;
				}
				if ($F('emailID') == ""){ 
					errorFlag=1;
					alert("Email must be input");
					return;
				}
				if ($F('himaxEmailBox') == ""){ 
					errorFlag=1;
					alert("Email MailBox must be select");
					return;
				}
				if ($F('himaxDepartment') == ""){ 
					errorFlag=1;
					alert("Department must be select");
					return;
				}
				if ($F('himaxPosition') == ""){
					errorFlag=1;
					alert("Postition must be select");
					return;
				}
			}
			// for subcon
			else{
				if($F('userId') != "" && !reg.test($('userId').value)){// subcon ID/mail 格式驗證
					errorFlag=1;
					alert("User ID format error");
					return;
				}
				if (curUserRole == "<%=CistaUtil.VENDOR_ROLE%>"){
					if ($F('vendorCompany') == ""){ 
						errorFlag=1;
						alert("Company must be select");
						return;
					}
				}
				else if (curUserRole == "<%=CistaUtil.CUSTOMER_ROLE%>"){
					if ($F('customerCompany') == ""){ 
						errorFlag=1;
						alert("Company must be select");
						return;
					}
				}
				if ($F('password') == ""){ 
					errorFlag=1;
					alert("Password must be input");
					return;
				}
				if ($F('confirmPassword') == ""){ 
					errorFlag=1;
					alert("Confirm Password must be input");
					return;
				}
				if ($F('confirmPassword') != "" &&　$F('password') != "" && $F('confirmPassword') != $F('password')){
					errorFlag=1;
					alert("Password must be equals");
					return;
				}
			}
		}

		// 設定上傳資料
		function setParameters(){
			pressSubmitFlag = 1;
			checkData();
			// 資料設定(設定傳回參數)
			if (errorFlag==0){
				msg.innerHTML = "Processing data , please wait a minutes...";
				if (curUserRole == "<%=CistaUtil.CISTA_ROLE%>"){
					$('email').value = $('emailID').value + "@" + $('himaxEmailBox').value;
					$('department').value = $('himaxDepartment').value;
					$('position').value = $('himaxPosition').value;
				}else{
					if (curUserRole == "<%=CistaUtil.VENDOR_ROLE%>"){
						$('company').value = $('vendorCompany').value;
					}
					else if (curUserRole == "<%=CistaUtil.CUSTOMER_ROLE%>"){
						$('company').value = $('customerCompany').value;
					}
					$('email').value = $('userId').value;
					$('department').value = $('subconDepartment').value;
					$('position').value = $('subconPosition').value;
				}
				userSave();
			}
		}

		// AD
		function fetchUser(){
			pressADFlag = 1;
			if ($F('userId') == "") {
				alert("Please help insert Himax Employe ID.");
				return;
			}

			new Ajax.Request(
				'<%=contextPath%>/FetchADUser.action',
				{
					method: 'post',
					parameters: 'userId='+ $F('userId'),
					onComplete: fetchUserComplete
				}
			);
		}

		// AD 填值
		function fetchUserComplete(r) {
			var userTxt = r.responseText;

			if ( userTxt != "ERROR") {
				var a1  = userTxt.split("|");
				var userID = a1[0];
				var mail_account = a1[1];
				var mail_domain = a1[2];
				var cname = a1[3];

				$('userId').value = userID;
				$('realName').value = cname;
				$('emailID').value = mail_account;
				SelectItem_Combo_Value($('himaxEmailBox') , mail_domain)
			}else{
				$('userId').value = "";
				alert('Can not find this account');
				return;
			}
		}

		// call action
		function userSave(){
			var forma = document.forms(0);
			forma.action = "UserSave.action";
			forma.method = "post";
			forma.target = "result";
			forma.submit();
		}
	</script>
	<style type="text/css">fieldset {width:90%;} </style>

</head>

<body leftmargin="8" topmargin="16" onload="doInit();">

	<table width="100%" border="0" cellspacing="0" cellpadding="0">	
		<tr>
			<td>
				<font color="red"><div><s:actionmessage/></div></font>
				<font color="red"><b><div id="msg"></b></div></font>
			</td>
		</tr>
	</table>
	<table border="0" cellpadding="0" cellspacing="20" width="100%">
		<tr><td valign="top" width="80%">
		<form>
			<!---內層表格-->
			<fieldset class="x-fieldset legend input">
			<legend>System User Create Function</legend>
			<table height="100%" width="100%" border="0" cellspacing="0" cellpadding="0" class="Himax-TableContent-white">

				<!--user role -->
				<tr>
					<td align="left" class="portlet-title-bg3" width="15%">Company Type</td>
					<td class="portlet-title-bg3">
						<input type="radio"
							id = "userRole" name="userRole" value="<%=CistaUtil.CISTA_ROLE%>" 
							onclick = "userType('<%=CistaUtil.CISTA_ROLE%>');" checked><%=CistaUtil.CISTA_ROLE%>

						<input type="radio"
							id = "userRole" name="userRole" value="<%=CistaUtil.SUBCON_ROLE%>"
							onclick ="userType('<%=CistaUtil.VENDOR_ROLE%>');"><%=CistaUtil.VENDOR_ROLE%>

						<input type="radio"
							id = "userRole" name="userRole" value="<%=CistaUtil.SUBCON_ROLE%>"
							onclick ="userType('<%=CistaUtil.CUSTOMER_ROLE%>');"><%=CistaUtil.CUSTOMER_ROLE%>
					<td> 
				</tr>
 
				<!--user id-->
				<tr>
					<td align="left" class="portlet-title-bg1">
						User ID<BLINK class = "Himax-col-star"> *</BLINK>
					</td>
					<td>
						<input type="text" id = "userId" name="userId" class = "Himax-col-width" onChange = "clearUserADData();" />
						<input 	name = "fetchAD" id = "fetchAD" 
								type="button" class="button" 
								value = '<s:text name="IE.maintain.button.fetch"/>'  
								onclick ="fetchUser();"/>
					</td>
				</tr>
  
				<!--real name-->
				<tr>
					<td align="left" class="portlet-title-bg1">
						Real Name<BLINK class = "Himax-col-star"> *</BLINK>
					</td>
					<td>
						<input 	type="text" id = "realName" name="realName" 
								class = "Himax-col-width"/>
					</td>
				</tr> 

				<!--Vendor company-->
				<tr id = "rowVendorCompany"  style="display:none">
					<td align="left" class="portlet-title-bg1">
						Vendor Company<BLINK class = "Himax-col-star"> *</BLINK>
					</td>
					<td>
						<input 	type="text" id="vendorCompany" name="vendorCompany" disabled 
								title = "please use 'get Vendor code' to select your Vendor"
								class = "Himax-col-width"/>
						<img src="<%=contextPath%>/images/lov.gif" alt="Get Vender code" id="sapVendor" style="cursor: hand;">
							<script type="text/javascript">
									SmartSearch.setup({
										cp:"<%=contextPath%>",
										button:"sapVendor",
										inputField:"vendorCompany",
										table:"t_SAP_VENDOR",
										keyColumn:"vendor_code",
										columns:"vendor_code,short_name",
										title:"Vendor CODE",
										mode:0,
										autoSearch:false
									});
							</script>
					</td>
				</tr>

				<!--Customer company-->
				<tr id = "rowCustomerCompany"  style="display:none">
					<td align="left" class="portlet-title-bg1">
						Customer Company<BLINK class = "Himax-col-star"> *</BLINK>
					</td>
					<td>
						<input 	type="text" id="customerCompany" name="customerCompany" disabled 
								title = "please use 'get Vendor code' to select your Vendor"
								class = "Himax-col-width" />
						<img src="<%=contextPath%>/images/lov.gif" alt="Get Customer code" id="sapCustomer" style="cursor: hand;">
							<script type="text/javascript">
									SmartSearch.setup({
										cp:"<%=contextPath%>",
										button:"sapCustomer",
										inputField:"customerCompany",
										table:"t_SAP_CUSTOMER",
										keyColumn:"customer_code",
										columns:"customer_code,short_name",
										title:"Customer CODE",
										mode:0,
										autoSearch:false
									});
							</script>
					</td>
				</tr>

				<!--password-->
				<tr id = "rowPassword"  style="display:none" >
					<td align="left" class="portlet-title-bg1">
						Password<BLINK class = "Himax-col-star"> *</BLINK>
					</td>
					<td>
						<input 	type="password" id= "password" name="password" class = "Himax-col-width" />
					</td>
				</tr>

				<!--confirm password -->
				<tr id = "rowConfirmPassword" style="display:none" >
					<td align="left" class="portlet-title-bg1">
						Cofirm Password<BLINK class = "Himax-col-star"> *</BLINK>
					</td>
					<td>
						<input  type="password" id = "confirmPassword" name="confirmPassword" 
								class = "Himax-col-width" />
					</td>
				</tr>
				
				<!--email ===> 三合1 -->  
				<tr id = "rowHimaxEmail" style="display:none">
					<td align="left" class="portlet-title-bg1">
						Email<BLINK class = "Himax-col-star"> *</BLINK>
					</td>
					<td>
						<!--main ID-->
						<input name="emailID" id="emailID" type="text" size ="9">
						@
						<!--himax mailbox-->
						<select name="himaxEmailBox" id ="himaxEmailBox">
							<option value = ''>-----</option>
							<%	for (int i = 0 ; i < CistaUtil.MAIL_BOX.length ;i++){%>
								<option value = <%=CistaUtil.MAIL_BOX[i][0]%>><%=CistaUtil.MAIL_BOX[i][1]%>
							<%	}%>
						</select>
					</td>
				</tr>
  
				<!--phoneNum-->
				<tr>
					<td align="left" class="portlet-title-bg1">Phone Number</td>
					<td>
						<input type="text" id = "phoneNum" name="phoneNum" class = "Himax-col-width"/>
					</td>
				</tr>

				<!--department - himax -->
				<tr id = "rowHimaxDepartment">
					<td align="left" class="portlet-title-bg1">
						Department<BLINK class = "Himax-col-star"> *</BLINK>
					</td>
					<td>
						<select id = "himaxDepartment" name = "himaxDepartment" 
								class = "Himax-asl-col-middle" >
							<option value = ''>-----</option>
						<%
							if (allDepartments  != null && allDepartments .size() > 0){
								for(SysDepartmentTo himax : allDepartments ){
						%>
							<option value = '<%=himax.getDepartName()%>'><%=himax.getDepartName()%></option>
						<%
							}}
						%>
						</select>
					</td>
				</tr>

				<!--position - himax-->
				<tr id = "rowHimaxPosition">
					<td align="left" class="portlet-title-bg1">
						Position<BLINK class = "Himax-col-star"> *</BLINK>
					</td>
					<td>
						<select id = "himaxPosition" name="himaxPosition" 
								class = "Himax-asl-col-middle" >
							<option value = ''>-----</option>
							<% for (int i = 0 ; i < CistaUtil.POSITION.length ; i++){%>
								<option value = '<%=CistaUtil.POSITION[i]%>' ><%=CistaUtil.POSITION[i]%></option>
							<%}%>
						</select>
					</td>
				</tr>

			  	<!--department - subcon-->
				<tr id = "rowSubconDepartment" style="display:none" >
					<td align="left" class="portlet-title-bg1">Department</td>
					<td>
						<input type="text" id = "subconDepartment" name="subconDepartment" class = "Himax-col-width"/>
					</td>
				</tr>

				<!--position - subcon-->
				<tr id = "rowSubconPosition" style="display:none" >
					<td align="left" class="portlet-title-bg1">Position</td>
					<td>
						<input type="text" id = "subconPosition" name="subconPosition" class = "Himax-col-width"/>
					</td>
				</tr>

				<!--form hide field-->
				<input type="hidden" id = "department" 	name = "department"/>
				<input type="hidden" id = "company" 	name = "company"/>
				<input type="hidden" id = "position" 	name = "position"/>
				<input type="hidden" id = "email" 		name = "email"/>

				<!--button-->
				<tr class="portlet-title-bg3" ></tr>
				<tr class="portlet-title-bg3">
					<td></td>
					<td align = "right">
						<input 
							name = "saveBtn" id = "saveBtn" 
							type="button" value='<s:text name="IE.maintain.button.save"/>' 
							class="button" onclick="setParameters();"/>&nbsp;&nbsp;
						
						<input 
							name = "resetBtn" id ="resetBtn" 
							type="reset" value='<s:text name="IE.maintain.button.reset"/>' 
							class="button" />
					</td>
				</tr> 
			</table>
			</fieldset>
		<form>

	</td></tr>
	</table>
	
</body>
</html>