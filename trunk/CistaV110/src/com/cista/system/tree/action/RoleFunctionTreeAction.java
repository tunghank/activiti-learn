
package com.cista.system.tree.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;


import com.cista.system.to.RoleFunctionTreeTo;
import com.cista.system.to.SysRoleFunctionTo;
import com.cista.system.to.SysUserTo;
import com.cista.system.tree.dao.RoleFunctionTreeDao;
import com.cista.system.util.BaseAction;
import com.cista.system.util.CistaUtil;
import com.sun.java_cup.internal.internal_error;

/**
 * @author 900730
 *
 */
public class RoleFunctionTreeAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private String menuString;
	private List<RoleFunctionTreeTo> trees;
	

	public String ShowRoleFunctionTree() throws Exception {

		RoleFunctionTreeDao treeDao = new RoleFunctionTreeDao();
		
		request= ServletActionContext.getRequest();
		SysUserTo curUser = (SysUserTo)request.getSession().getAttribute(CistaUtil.CUR_USERINFO);
		
		String roleUid = request.getParameter("roleUid"); 
		roleUid = null != roleUid ? roleUid : "";
		logger.debug("roleUid " + roleUid);
		//1.3 Check Role Function
		List<SysRoleFunctionTo> roleFunctionList =  treeDao.getRoleFunction(roleUid);
				
		if ( curUser == null ){
			addActionMessage(getText("System.error.access.nologin"));
			return ERROR;
		}else{
						
			List<RoleFunctionTreeTo> treeRootList = treeDao.getSubTreeList("1");
			//1.1 Set Menu Tree 
			trees = new ArrayList<RoleFunctionTreeTo>();
			//1.2 從1開始找

			getTreeNode(treeRootList, roleFunctionList);
			//logger.debug(trees.toString());
			

		
	        JSONArray jsonObject = JSONArray.fromObject(trees);
	        
	        try {
	            menuString = jsonObject.toString();
	        } catch (Exception e) {
	        	menuString = "";
	        	logger.error(e.toString());
	        }
		}
		// 1.3 Set AJAX response
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		
		logger.debug(menuString);
		out.println(menuString);
		out.close();

   		return NONE;
	}

	public void getTreeNode(List<RoleFunctionTreeTo> nextTreeList, List<SysRoleFunctionTo> roleFunctionList){
		RoleFunctionTreeDao treeDao = new RoleFunctionTreeDao();
		for(int i = 0; i < nextTreeList.size() ; i++){
			RoleFunctionTreeTo nextNode = (RoleFunctionTreeTo)nextTreeList.get(i);
			if(nextNode.getCls().equals("folder") && !nextNode.getId().equals("1")  ){
				List<RoleFunctionTreeTo> nextNextTreeList = treeDao.getSubTreeListNotRootByRole(String.valueOf(nextNode.getId()), roleFunctionList);
				if ( nextNextTreeList.size() > 0 ){
					nextNode.setChildren(nextNextTreeList);
					getTreeNode(nextNextTreeList, roleFunctionList);
				}
			}else if(nextNode.getCls().equals("folder") && nextNode.getId().equals("1") ){
				List<RoleFunctionTreeTo> nextNextTreeList = treeDao.getSubTreeListNotRootByRole(String.valueOf(nextNode.getId()), roleFunctionList);
				nextNode.setChildren(nextNextTreeList);
				getTreeNode(nextNextTreeList, roleFunctionList);
				trees.add(nextNode);

			}
		}
	}
	
	
	
    public String getMenuString() {
        return menuString;
    }

    public void setMenuString(String menuString) {
        this.menuString = menuString;
    }
    

}
