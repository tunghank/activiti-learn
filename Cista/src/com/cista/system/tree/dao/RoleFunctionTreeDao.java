package com.cista.system.tree.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.system.to.MenuCheckedTo;
import com.cista.system.to.SysFunctionTo;
import com.cista.system.util.BaseDao;

/**
 * @author 900730
 *
 */
public class CheckedTreeDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.clt.system.dao.ITreeDao#getSubTreeList(java.lang.String)
	 */
	public List<MenuCheckedTo> getSubTreeList(String parentId)
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

		List<MenuCheckedTo> menuList = new ArrayList<MenuCheckedTo>(); 
		for (int i =0; i < funTreeList.size(); i ++){
			SysFunctionTo node = (SysFunctionTo)funTreeList.get(i);
			MenuCheckedTo menuNode = new MenuCheckedTo();
			menuNode.setCls(node.getCls());
			menuNode.setId(Integer.parseInt(node.getId()));
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
	public List<MenuCheckedTo> getSubTreeListNotRoot(String parentId)
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
		
		List<MenuCheckedTo> menuList = new ArrayList<MenuCheckedTo>(); 
		for (int i =0; i < funTreeList.size(); i ++){
			SysFunctionTo node = (SysFunctionTo)funTreeList.get(i);
			MenuCheckedTo menuNode = new MenuCheckedTo();
			menuNode.setCls(node.getCls());
			menuNode.setId(Integer.parseInt(node.getId()));
			menuNode.setLeaf(Integer.parseInt(node.getLeaf()) >= 1);
			menuNode.setText(node.getTitle());
			menuNode.setHref(node.getUrl());
			menuNode.setHrefTarget(node.getHrefTarget());
			menuList.add(menuNode);
		}
		
		return menuList;
	}

	public List<MenuCheckedTo> getSubTreeListNotRootByUser(String parentId, String curUser)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		logger.debug("getSubTreeListNotRootByUser parentId " +  parentId);
		String sql = " SELECT A.ID, A.PARENT_ID, A.TITLE, A.CLS, A.LEAF, A.URL, A.HREF_TARGET " + 
				" FROM SYS_FUNCTION A Where A.PARENT_ID = ? and (A.ID <> 1 )  " +
				" AND A.LEAF = '0' " +			
				" UNION " +
				" SELECT A.ID, A.PARENT_ID, A.TITLE, A.CLS, A.LEAF, A.URL, A.HREF_TARGET " + 
				" FROM SYS_FUNCTION A Where A.PARENT_ID = ? AND A.ID IN ( SELECT D.FUNCTION_ID " +
				" FROM SYS_USER_ROLE B, SYS_ROLE C,  SYS_ROLE_FUNCTION D " +
				" WHERE B.ROLD_ID = C.ROLE_ID AND C.ROLE_ID = D.ROLE_ID " +
				" AND B.USER_ID = ? )" +
				" AND A.LEAF = '1' " +
				" Order by 1 ";

		ParameterizedBeanPropertyRowMapper<SysFunctionTo> rowMapper = 
			new ParameterizedBeanPropertyRowMapper<SysFunctionTo>();
		rowMapper.setMappedClass(SysFunctionTo.class);

		logger.debug(sql);
		List<SysFunctionTo> funTreeList = sjt.query(sql, rowMapper,
				new Object[] { parentId, parentId, curUser });

		List<MenuCheckedTo> menuList = new ArrayList<MenuCheckedTo>();
		for (int i = 0; i < funTreeList.size(); i++) {
			SysFunctionTo node = (SysFunctionTo) funTreeList.get(i);
			MenuCheckedTo menuNode = new MenuCheckedTo();
			menuNode.setCls(node.getCls());
			menuNode.setId(Integer.parseInt(node.getId()));
			menuNode.setLeaf(Integer.parseInt(node.getLeaf()) >= 1);
			menuNode.setText(node.getTitle());
			menuNode.setHref(node.getUrl());
			menuNode.setHrefTarget(node.getHrefTarget());
			menuList.add(menuNode);
		}

		return menuList;
	}


}
