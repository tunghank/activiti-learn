<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="com.cista.system.util.CLTUtil"%>
<%@ page import ="com.cista.system.to.SysUserTo"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String contextPath = (String)request.getContextPath();
	SysUserTo curUser =
            (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
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

        // NOTE: This is an example showing simple state management. During development,
        // it is generally best to disable state management as dynamically-generated ids
        // can change across page loads, leading to unpredictable results.  The developer
        // should ensure that stable state ids are set for stateful components in real apps.
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

		//EXTJS4.0
		/*var treeStore = new Ext.data.TreeStore ({
			expanded: true, 
			proxy: {
				type: 'ajax',
				url: '<%=contextPath%>/ShowTree.action'
			},
			sorters: [{
				property: 'leaf',
				direction: 'ASC'
			}, {
				property: 'text',
				direction: 'ASC'
			}]
		}); 

	   var tree =  new Ext.tree.TreePanel({
			id: 'tree-panel',
			title: '<s:text name="System.system.menu.function"/>',
			autoScroll: true,
			animate:true,
			enableDD:false,
			containerScroll: true,
			rootVisible: false,
			lines: false,
			singleExpand: false,
			useArrows: true,
			store: treeStore
		});
		
		setTimeout(function(){tree.expandAll();},0);
		tree.getRootNode().expand(true);
		*/

	   var tree =    new Ext.tree.TreePanel({
			id: 'tree-panel',
			title: '<s:text name="System.system.menu.function"/>',
			//split: true,
			//height: 300,
			//minSize: 150,
			autoScroll: true,
			animate:true,
			enableDD:false,
			containerScroll: true,
			// tree-specific configs:
			rootVisible: false,
			lines: false,
			singleExpand: false,
			useArrows: true,
			loader: new Ext.tree.TreeLoader({
				url:'<%=contextPath%>/ShowTree.action'
			}),
			root: new Ext.tree.AsyncTreeNode({
				
			})
		});

		

       var viewport = new Ext.Viewport({
            layout:'border',
            items:[{
                    region:'north',
					contentEl: 'north',
                    id:'north-panel',
                    //title:'North',
					height: 23,
                    layout:'accordion',
                    layoutConfig:{
                        animate:true
                    }
                },{
                    region:'west',
					contentEl: 'west',
                    id:'west-panel',
                    title:'Menu Bar',
                    split:true,
                    width: 150,
                    minSize: 150,
                    maxSize: 300,
                    collapsible: true,
                    layout:'accordion',
                    layoutConfig:{
                        animate:true
                    },
                    items:tree
                },{
                    region:'center',
                    contentEl: 'center',
					id:'center-panel',
                    split:true,
                    //title:'Center',
                    margins:'0 0 0 0'
                }
             ]
        });
		
		tree.expandAll();

		//Add Hader Button
		//var logoutButton = new Ext.Button('logoutButton', {text: 'Logout'});
		

    });
</script>
 
<script language="JavaScript" type="text/JavaScript">

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

</script>

</head>
<body>
  <div id="west">
  </div>
  <div id="north">
  	<div style="position:absolute;top:0px;left:30px;z-index:100">
		<img src="<%=contextPath%>/images/bg_top_blue.png" width="100%" height="110">
	</div>

	<div style="position:absolute;top:3px;left:15px;z-index:200">
		<img src="<%=contextPath%>/images/<s:text name='System.system.site'/>-Logo.png" width="65" height="17">
	</div>

	<div style="position:absolute;top:'10%';left:'30%';z-index:300">
		<TABLE  width="100%" border=0 cellPadding=0 cellSpacing=0 >
			<tr>
			 <td width="70%" valign="bottom" align="left"></td>
			  
			  <td width="10%" valign="top" ><a href="<%=contextPath%>/Logout.action" target="_parent" onMouseOver="MM_swapImage('Logout','','<%=contextPath%>/images/Logout.png',1)" onMouseOut="MM_swapImgRestore()"><img src="<%=contextPath%>/images/LogoutOver.png" alt="Logout" name="Logout"  width="60" height="20" border="0"></a></td>

			  <td width="10%" valign="top"><a href='mailto:<s:text name="System.system.contantus.email"/>' target="_parent" onMouseOver="MM_swapImage('ContactUs','','<%=contextPath%>/images/ContactUs.png',1)" onMouseOut="MM_swapImgRestore()"><img src="<%=contextPath%>/images/ContactUsOver.png" alt="Contact Us" name="ContactUs"  width="60" height="20" border="0"></a></td>

			  <td width="10%" valign="top"><!-- <a href="<%=contextPath%>/UserHome.action" target="mainFrame"  onMouseOver="MM_swapImage('Home','','<%=contextPath%>/images/Home.png',1)" onMouseOut="MM_swapImgRestore()">--><img src="<%=contextPath%>/images/HomeOver.png" alt="Home" name="Home"  width="60" height="20" border="0"></a> </td>
			  <!---Home-->
			  <td width="10%" valign="top"><a href="<%=contextPath%>/UserProfilePre.action" target="mainFrame" onMouseOver="MM_swapImage('myProfile','','<%=contextPath%>/images/myProfile.png',1)" onMouseOut="MM_swapImgRestore()"><img src="<%=contextPath%>/images/myProfileOver.png" alt="myProfile" name="myProfile"  width="60" height="20" border="0"></a></td>
			</tr>
		</TABLE>
	</div>

	<div style="position:absolute;top:'10%';left:'25%';z-index:200">
		<TABLE  width="100%" border=0 cellPadding=0 cellSpacing=0 >
			<tr>
			 <td width="100%" valign="bottom" align="left" ><span class="portlet_content"><b>Welcome :&nbsp;&nbsp;<%=curUser.getUserId()%> - <%=curUser.getRealName()%></b></span></td>
			</tr>
		</TABLE>
	</div>

  </div>
  <div id="center">
  	<iframe width="100%" height="100%" name="mainFrame" frameborder="0"  src='' scrolling="auto" noresize>
	
	</iframe>
  </div>


</body>

</html>