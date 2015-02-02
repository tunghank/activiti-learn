package com.cista.system.tree.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.system.to.FunctionTreeTo;
import com.cista.system.to.SysFunctionTo;
import com.cista.system.to.SysRoleFunctionTo;
import com.cista.system.util.BaseDao;


/**
 * @author 900730
 *
 */
public class AllFunctionTreeDao extends BaseDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.clt.system.dao.ITreeDao#getSubTreeList(java.lang.String)
	 */
	public List<FunctionTreeTo> getSubTreeList(String parentId)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql = "SELECT A.ID, A.PARENT_ID, A.TITLE, A.CLS, A.LEAF, A.URL, A.HREF_TARGET " +
					" FROM SYS_FUNCTION A where PARENT_ID = ? order by ID" ;
		
    	ParameterizedBeanPropertyRowMapper<SysFunctionTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysFunctionTo>();
    	rowMapper.setMappedClass(SysFunctionTo.class);

    	logger.debug("parentId " + parentId);
    	logger.debug(sql);
    	List<SysFunctionTo> funTreeList = sjt.query(sql,rowMapper, new Object[] { parentId } );

		List<FunctionTreeTo> menuList = new ArrayList<FunctionTreeTo>(); 
		for (int i =0; i < funTreeList.size(); i ++){
			SysFunctionTo node = (SysFunctionTo)funTreeList.get(i);
			FunctionTreeTo menuNode = new FunctionTreeTo();
			menuNode.setCls(node.getCls());
			menuNode.setId(node.getId());
			menuNode.setLeaf(Integer.parseInt(node.getLeaf()) >= 1);
			menuNode.setText(node.getTitle());
			menuNode.setHref(node.getUrl());
			menuNode.setHrefTarget(node.getHrefTarget());
			menuList.add(menuNode);
		}
		
		return menuList;
	}


	/* (non-Javadoc)
	 * @see com.clt.system.dao.ITreeDao#getSubTreeListNotRoot(java.lang.String)
	 */
	public List<FunctionTreeTo> getSubTreeListNotRoot(String parentId)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSubTreeListNotRoot parentId " +  parentId);
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql = "SELECT A.ID, A.PARENT_ID, A.TITLE, A.CLS, A.LEAF, A.URL, A.HREF_TARGET " +
					" FROM SYS_FUNCTION A where PARENT_ID = ? and (ID <> 1 ) order by ID" ;
		
    	ParameterizedBeanPropertyRowMapper<SysFunctionTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysFunctionTo>();
    	rowMapper.setMappedClass(SysFunctionTo.class);

    	logger.debug(sql);
    	List<SysFunctionTo> funTreeList = sjt.query(sql,rowMapper, new Object[] { parentId } );
		
		List<FunctionTreeTo> menuList = new ArrayList<FunctionTreeTo>(); 
		for (int i =0; i < funTreeList.size(); i ++){
			SysFunctionTo node = (SysFunctionTo)funTreeList.get(i);
			FunctionTreeTo menuNode = new FunctionTreeTo();
			menuNode.setCls(node.getCls());
			menuNode.setId(node.getId());
			menuNode.setLeaf(Integer.parseInt(node.getLeaf()) >= 1);
			menuNode.setText(node.getTitle());
			menuNode.setHref(node.getUrl());
			menuNode.setHrefTarget(node.getHrefTarget());
			menuList.add(menuNode);
		}
		
		return menuList;
	}

	public List<FunctionTreeTo> getSubTreeListNotRootByRole(String parentId, List<SysRoleFunctionTo> roleFunctionList)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		logger.debug("getSubTreeListNotRootByUser parentId " +  parentId);
		String sql = " SELECT A.ID, A.PARENT_ID, A.TITLE, A.CLS, A.LEAF, A.URL, A.HREF_TARGET " + 
				" FROM SYS_FUNCTION A Where A.PARENT_ID = ? and (A.ID <> 1 )  " +
				" AND A.LEAF = '0' " +			
				" UNION " +
				" SELECT A.ID, A.PARENT_ID, A.TITLE, A.CLS, A.LEAF, A.URL, A.HREF_TARGET " + 
				" FROM SYS_FUNCTION A Where A.PARENT_ID = ? " +
				" AND A.LEAF = '1' " +
				" Order by 1 ";

		ParameterizedBeanPropertyRowMapper<SysFunctionTo> rowMapper = 
			new ParameterizedBeanPropertyRowMapper<SysFunctionTo>();
		rowMapper.setMappedClass(SysFunctionTo.class);

		logger.debug(sql);
		List<SysFunctionTo> funTreeList = sjt.query(sql, rowMapper,
				new Object[] { parentId, parentId });

		List<FunctionTreeTo> menuList = new ArrayList<FunctionTreeTo>();
		
		for (int i = 0; i < funTreeList.size(); i++) {
			SysFunctionTo node = (SysFunctionTo) funTreeList.get(i);
			FunctionTreeTo menuNode = new FunctionTreeTo();
			
			//logger.debug("roleFunctionList.size() " + roleFunctionList.size());
			
			menuNode.setCls(node.getCls());
			menuNode.setId(node.getId());
			menuNode.setLeaf(Integer.parseInt(node.getLeaf()) >= 1);
			menuNode.setText(node.getTitle());
			menuNode.setHref(node.getUrl());
			menuNode.setHrefTarget(node.getHrefTarget());
			menuList.add(menuNode);
		}

		return menuList;
	}

	public List<SysRoleFunctionTo> getRoleFunction(String roleId)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		logger.debug("roleId" +  roleId);
		String sql = " SELECT A.ID, A.ROLE_ID, A.FUNCTION_ID, A.CDT " +
					" FROM SYS_ROLE_FUNCTION A " +
					" WHERE A.ROLE_ID = ? ";

		ParameterizedBeanPropertyRowMapper<SysRoleFunctionTo> rowMapper = 
			new ParameterizedBeanPropertyRowMapper<SysRoleFunctionTo>();
		rowMapper.setMappedClass(SysRoleFunctionTo.class);

    	List<SysRoleFunctionTo> result = sjt.query(sql,rowMapper, new Object[] {roleId} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}
	}

}
