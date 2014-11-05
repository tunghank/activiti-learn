package com.cista.system.account.dao;

import java.util.ArrayList;
import java.util.List;

import com.cista.system.to.SysRoleFunctionTo;
import com.cista.system.to.SysUserRoleTo;
import com.cista.system.util.BaseDao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;


public class RoleFunctionDao extends BaseDao {

	public List searchRoleFunctionList(String leaf ,String roleId) throws DataAccessException {

		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		ParameterizedBeanPropertyRowMapper<SysRoleFunctionTo> rowMapper = new ParameterizedBeanPropertyRowMapper<SysRoleFunctionTo>();
		rowMapper.setMappedClass(SysRoleFunctionTo.class);

		String sql = "";

		sql = " SELECT " 
			+ "    A.ID " 
			+ "    ,A.ROLE_ID "
			+ "    ,c.ROLE_NAME " 
			+ "    ,A.FUNCTION_ID "
			+ "    ,B.TITLE AS FUNCTION_NAME " 
			+ "    ,A.CDT " 
			+ "	   ,B.CLS "
			+ " FROM "
			+ "    SYS_ROLE_FUNCTION A " 
			+ "    ,SYS_FUNCTION  B "
			+ "    ,sys_role c " 
			+ " WHERE " 
			+ "    A.FUNCTION_ID = B.ID "
			+ "    AND a.ROLE_ID = c.role_id " ;
		
		// leaf
		if (leaf.equals("")){ 
			leaf = "1";	sql += " And 1 = ?";
		}else  sql += " And B.LEAF = ? ";

		// role id
		if (roleId.equals("")) {
			roleId = "1";
			sql += " And 1 = ?";
		} else {
			roleId = roleId.replace('*', '%');

			if (roleId.contains("%"))
				sql += " And A.ROLE_ID like ? ";
			else
				sql += " And A.ROLE_ID = ? ";
		}
		sql += " ORDER BY A.ROLE_ID ,B.TITLE ";

		List<SysRoleFunctionTo> result = sjt.query(sql, rowMapper,new Object[] { leaf,roleId });
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}
	}
	
	public List getRoleFunctionList(String functionID) throws DataAccessException{
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		ParameterizedBeanPropertyRowMapper<SysRoleFunctionTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysRoleFunctionTo>();
    	rowMapper.setMappedClass(SysRoleFunctionTo.class);
		
		String sql ="";
		sql = " SELECT "
			+ "		   A.ID "
			+ "		   ,A.ROLE_ID "
			+ "		   ,A.FUNCTION_ID "
		    + "		   ,C.ROLE_NAME AS ROLENAME "
		    + "        ,B.TITLE AS FUNCTIONNAME "
		    + " 	   ,A.CDT "
			+ "	FROM " 
			+ "			SYS_ROLE_FUNCTION A "
			+ "			,SYS_FUNCTION B "
			+ "			,SYS_ROLE C "	
			+ "	WHERE "
			+ "		    A.FUNCTION_ID = B.ID (+) "
			+ "			AND C.ROLE_ID = A.ROLE_ID ";		
		
		// id
		if (functionID.equals("")){ functionID = "1";	sql += " And 1 = ?";}			
		else  
		{
			functionID = functionID.replace('*','%');
			
			if (functionID.contains("%"))	sql += " And A.FUNCTION_ID like ? ";	
			else 							sql += " And A.FUNCTION_ID = ? ";	
		}
   
    	List<SysRoleFunctionTo> result = sjt.query(sql,rowMapper, new Object[] {functionID} );
		
		if (result != null && result.size() > 0){return result ;} 
		else {	return null;}				
	}
	
	public int insertFunctionRole(String roleId,String functionId) throws DataAccessException{
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		
		sql = " INSERT INTO SYS_Role_Function (ID,ROLE_ID,FUNCTION_ID) "
			+ "	VALUES (SYS_ROLE_FUNCTION_SEQ.NEXTVAL,?,?)" ;
		logger.debug(sql);
		
		int answer = sjt.update(sql, new Object[] { roleId ,functionId });
		return answer ;			
	}
	
	//Add 2014/11/05
	public int deleteRoleFunctionByRole(String roleId) throws DataAccessException{
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		
		sql = " DELETE FROM SYS_Role_FUNCTION A "
			+ " WHERE A.ROLE_ID = ? ";			
		
		int answer = sjt.update(sql, new Object[] {roleId });
		return answer ;	
	}
	
	public int[] batchInsertRoleFunction(List<SysRoleFunctionTo> roleFunctionList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into SYS_ROLE_FUNCTION ( ID, ROLE_ID, FUNCTION_ID, CDT ) " +
		" Values ( ?, ?, ?, ? )";
		
		List<Object[]> batch = new ArrayList<Object[]>();
		for (SysRoleFunctionTo roleFunctionTo : roleFunctionList) {
		    Object[] values = new Object[] {
		    		roleFunctionTo.getId(),
		    		roleFunctionTo.getRoleId(),
		    		roleFunctionTo.getFunctionId(),
		    		roleFunctionTo.getCdt()
					};
		    batch.add(values);
		}
	
		logger.debug(sql);
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	
	
	public int deleteFunctionRole(String roleId,String functionId) throws DataAccessException{
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		
		sql = " DELETE FROM SYS_Role_FUNCTION A "
			+ " WHERE a.ROLE_ID = ? AND a.FUNCTION_ID= ? ";			
		
		int answer = sjt.update(sql, new Object[] {roleId,functionId });
		return answer ;	
	}

	public SysRoleFunctionTo getRoleFunctionDetail(String roleId, String functionId)
			throws DataAccessException {
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		ParameterizedBeanPropertyRowMapper<SysRoleFunctionTo> rowMapper = new ParameterizedBeanPropertyRowMapper<SysRoleFunctionTo>();
		rowMapper.setMappedClass(SysRoleFunctionTo.class);

		String sql = "";

		sql = " SELECT A.ID  ,A.ROLE_ID  ,A.FUNCTION_ID  ,A.CDT FROM SYS_ROLE_FUNCTION A WHERE 1=1";

		if (roleId.equals("")) {
			roleId = "1";
			sql += " And 1 = ?";
		} else {
			roleId = roleId.replace('*', '%');

			if (roleId.contains("%"))
				sql += " And A.ROLE_ID like ? ";
			else
				sql += " And A.ROLE_ID = ? ";
		}

		if (functionId.equals("")) {
			functionId = "1";
			sql += " And 1 = ?";
		} else {
			functionId = functionId.replace('*', '%');

			if (functionId.contains("%"))
				sql += " And A.FUNCTION_ID like ? ";
			else
				sql += " And A.FUNCTION_ID = ? ";
		}

		List<SysRoleFunctionTo> result = sjt.query(sql, rowMapper, new Object[] { roleId, functionId });

		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}
}
