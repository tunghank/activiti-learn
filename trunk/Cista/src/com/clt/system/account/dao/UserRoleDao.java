package com.clt.system.account.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.system.to.SysUserRoleTo;
import com.clt.system.util.BaseDao;

public class UserRoleDao extends BaseDao{	
	
	
	public List searchUserRoleList(String roleId , String userId) throws DataAccessException {

		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		ParameterizedBeanPropertyRowMapper<SysUserRoleTo> rowMapper = new ParameterizedBeanPropertyRowMapper<SysUserRoleTo>();
		rowMapper.setMappedClass(SysUserRoleTo.class);

		String sql = "";

		sql = " SELECT "
			+ "	   A.USER_ID "
			+ "    ,B.ROLD_ID  "
			+ " FROM "
			+ "    SYS_USER A "
			+ "    , SYS_USER_ROLE B "
			+ "    ,SYS_ROLE C "
			+ " WHERE "
			+ "    A.USER_ID = B.USER_ID "
			+ "    AND B.ROLD_ID = C.ROLE_ID ";
		
		// role id
		if (roleId.equals("")) {
			roleId = "1";
			sql += " And 1 = ?";
		} else {
			roleId = roleId.replace('*', '%');

			if (roleId.contains("%"))
				sql += " And C.ROLE_ID like ? ";
			else
				sql += " And C.ROLE_ID = ? ";
		}
		
		// user id
		if (userId.equals("")) {
			userId = "1";
			sql += " And 1 = ?";
		} else {
			userId = userId.replace('*', '%');

			if (userId.contains("%"))
				sql += " And A.USER_ID like ? ";
			else
				sql += " And A.USER_ID = ? ";
		}
		sql += " ORDER BY A.USER_ID ";

		List<SysUserRoleTo> result = sjt.query(sql, rowMapper,new Object[] { roleId , userId });
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}
	}
	
	public int updateUserRole(String roleId , String userId) throws DataAccessException{
		logger.debug(roleId);
		logger.debug(userId);
		
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		sql = " UPDATE SYS_USER_ROLE A "
			+ "	SET A.ROLD_ID 	= ? "
			+ "	WHERE   A.USER_ID= ? ";
		
		int answer = sjt.update( sql, new Object[]{roleId,userId});		
		return answer;		
	}
	
	public int insertUserRole(String userId , int roleId) throws DataAccessException{
		
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		sql = "  INSERT INTO SYS_USER_ROLE (ID,USER_ID,ROLD_ID,CDT) "
			+ "	 VALUES "
			+	"(" 
			+		"SYS_USER_ROLE_SEQ.NEXTVAL"
			+		",?" 
			+		",?" 
			+		",TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')" 
			+	")";		
		int answer = sjt.update(sql	, new Object[]{userId,roleId});
		return answer ;
	}
	
	public int deleteUserRole(String userId , int roleId) throws DataAccessException{
		
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		sql = "  DELETE FROM SYS_USER_ROLE A"
			+ "	 WHERE A.USER_ID = ? AND A.ROLD_ID = ?";
		int answer = sjt.update(sql	, new Object[]{userId,roleId});
		return answer ;
	}
	
	public SysUserRoleTo getUserRoleDetail(String userId) throws DataAccessException{
		
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		ParameterizedBeanPropertyRowMapper<SysUserRoleTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysUserRoleTo>();
    	rowMapper.setMappedClass(SysUserRoleTo.class);
		
		sql = " SELECT "
			+ "  		A.ID "
			+ "		   ,A.USER_ID "
			+ "		   ,A.ROLD_ID "
			+ "		   ,A.CDT "
			+ "	FROM SYS_USER_ROLE A "
			+ " WHERE   A.USER_ID = ? " ;
		
		List<SysUserRoleTo> result = sjt.query(sql,rowMapper, new Object[] {userId} );		
		if (result != null && result.size() > 0){return result.get(0);} else {return null;}
	}

}
