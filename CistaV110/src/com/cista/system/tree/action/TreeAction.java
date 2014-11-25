
package com.cista.system.tree.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import com.cista.system.to.MenuTo;
import com.cista.system.to.SysUserTo;
import com.cista.system.tree.dao.TreeDao;
import com.cista.system.util.BaseAction;
import com.cista.system.util.CistaUtil;

/**
 * @author 900730
 *
 */
public class TreeAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private String menuString;
	private List<MenuTo> trees;
	

	public String showTree() throws Exception {

		TreeDao treeDao = new TreeDao();
		
		request= ServletActionContext.getRequest();
		SysUserTo curUser = (SysUserTo)request.getSession().getAttribute(CistaUtil.CUR_USERINFO);
		
		if ( curUser == null ){
			addActionMessage(getText("System.error.access.nologin"));
			return ERROR;
		}else{
						
			List<MenuTo> treeRootList = treeDao.getSubTreeList("1");
			//1.1 Set Menu Tree 
			trees = new ArrayList<MenuTo>();
			//1.2 從1開始找
			getTreeNode(treeRootList, curUser.getUserId());
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

	public void getTreeNode(List<MenuTo> nextTreeList, String curUser){
		TreeDao treeDao = new TreeDao();
		for(int i = 0; i < nextTreeList.size() ; i++){
			MenuTo nextNode = (MenuTo)nextTreeList.get(i);
			if(nextNode.getCls().equals("folder") && nextNode.getId() != 1 ){
				List<MenuTo> nextNextTreeList = treeDao.getSubTreeListNotRootByUser(String.valueOf(nextNode.getId()), curUser);
				if ( nextNextTreeList.size() > 0 ){
					nextNode.setChildren(nextNextTreeList);
					getTreeNode(nextNextTreeList, curUser);
				}
			}else if(nextNode.getCls().equals("folder") && nextNode.getId() == 1 ){
				List<MenuTo> nextNextTreeList = treeDao.getSubTreeListNotRootByUser(String.valueOf(nextNode.getId()), curUser);
				nextNode.setChildren(nextNextTreeList);
				getTreeNode(nextNextTreeList, curUser);
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
