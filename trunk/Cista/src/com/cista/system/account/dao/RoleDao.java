package com.cista.system.account.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.system.to.SysRoleTo;
import com.cista.system.util.BaseDao;

public class RoleDao extends BaseDao{
	
	public SysRoleTo getRoleDetail(String roleName , String roleId) throws DataAccessException{
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		ParameterizedBeanPropertyRowMapper<SysRoleTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysRoleTo>();
    	rowMapper.setMappedClass(SysRoleTo.class);
		
		String sql ;
		sql = " SELECT A.ROLE_ID ,A.ROLE_NAME ,A.CDT "
			+ " FROM SYS_ROLE A "
			+ " WHERE 1 = 1 " ;
		
		// role name
		if (roleName.equals("")){ roleName = "1";	sql += " And 1 = ?"; }
		else{	
			
			roleName = roleName.replace('*', '%');		
			if (roleName.contains("%"))	sql += " And A.ROLE_NAME like ? ";
			else 						sql += " And A.ROLE_NAME = ? ";
		}
		
		// role id
		if (roleId.equals("")){ roleId = "1";	sql += " And 1 = ?"; }
		else{	
			
			roleId = roleId.replace('*', '%');		
			if (roleId.contains("%"))		sql += " And A.ROLE_ID like ? ";
			else 							sql += " And A.ROLE_ID = ? ";
		}
	
		List<SysRoleTo> result = sjt.query(sql,rowMapper, new Object[] {roleName , roleId} );
			
		if (result != null && result.size() > 0) {return result.get(0);} 
		else {return null;}
	}

	public int insertRole(String roleName) throws DataAccessException{
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		
		sql = " INSERT INTO SYS_Role (ROLE_ID,ROLE_NAME,CDT) "
			+ " VALUES ( SYS_ROLE_SEQ.NEXTVAL ,?,TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'))" ;
		
		int answer = sjt.update(sql, new Object[]{roleName});
		return answer ;
	}	
	
	public List<SysRoleTo> searchRoleList(String roleName) throws DataAccessException{
	
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		ParameterizedBeanPropertyRowMapper<SysRoleTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysRoleTo>();
    	rowMapper.setMappedClass(SysRoleTo.class);	
		
		sql = " SELECT "
		   	+ "	   A.ROLE_ID "
		   	+ "    ,A.ROLE_NAME "
		   	+ "    ,A.CDT "
		   	+ " FROM SYS_ROLE A "
		   	+ " WHERE "
		   	+ "    1 = 1 ";
		
			if (roleName.equals("")){ roleName = "1";	sql += " And 1 = ?"; }
			else 
			{	
				roleName = roleName.replace('*', '%');
			
				if (roleName.contains("%"))	sql += " And A.ROLE_NAME like ? ";
				else 							sql += " And A.ROLE_NAME = ? ";
			}
			
		sql+= " ORDER BY A.ROLE_ID";		
		
		List<SysRoleTo> result = sjt.query(sql,rowMapper, new Object[] {roleName} );
		
		if (result != null && result.size() > 0) {	
			return result;
		}else {	
			return null;
		}		
	}
	
	public int deleteRole(String roleId) throws DataAccessException	{
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		ParameterizedBeanPropertyRowMapper<SysRoleTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysRoleTo>();
    	rowMapper.setMappedClass(SysRoleTo.class);
    	
    	sql = " DELETE FROM SYS_ROLE A "
			+ " WHERE A.ROLE_ID = ? " ;    	
		int answer = sjt.update(sql	, new Object[]{roleId});
		
		return answer;		
	}
	
	public int updateRole(SysRoleTo role)throws DataAccessException{
	
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		ParameterizedBeanPropertyRowMapper<SysRoleTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysRoleTo>();
    	rowMapper.setMappedClass(SysRoleTo.class);
    	
    	sql = "  UPDATE SYS_ROLE A SET   A.ROLE_NAME = ? WHERE A.ROLE_ID = ? ";    	
    	    	
		int answer = sjt.update(sql	, new Object[]
		    {role.getRoleName(),role.getRoleId()});		
		return answer;		
	}
}
