package com.cista.system.account.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.system.to.SysRoleTo;
import com.cista.system.to.SysUserTo;
import com.cista.system.util.BaseDao;
import com.cista.system.util.CistaUtil;


public class UserDao extends BaseDao{

	public List showAllUsers() throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT " 
					+ 		" A.USER_ID " 
					+		" ,A.REAL_NAME " 
					+		" ,A.PASSWORD " 
					+		" ,A.COMPANY " 
					+		" ,A.DEPARTMENT " 
					+		" ,A.POSITION " 
					+		" ,A.EMAIL " 
					+		" ,A.PHONE_NUM " 
					+		" ,A.ACTIVE " 
					+		" ,A.LAST_TIME " 
					+		" ,A.LAST_IP " 
					+		" ,A.CDT " 
					+		" ,A.CREATE_BY " 
					+		" ,A.UPDATE_BY " 
					+		" ,A.UDT " 

					+ " FROM SYS_USER A ";
		
    	ParameterizedBeanPropertyRowMapper<SysUserTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysUserTo>();
    	rowMapper.setMappedClass(SysUserTo.class);

    	List<SysUserTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}

	public boolean validate(String username, String password)throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql 	= " SELECT A.USER_ID, A.REAL_NAME, A.PASSWORD, A.COMPANY, " +
				" A.COMPANY_SHORT_NAME, A.DEPARTMENT, A.POSITION, A.EMAIL, " +
				" A.PHONE_NUM, A.ACTIVE, A.LAST_TIME, A.LAST_IP, A.CDT, " +
				" A.CREATE_BY, A.UPDATE_BY, A.UDT " +
				" FROM SYS_USER A " +
				" Where USER_ID=? and PASSWORD=? ";
		
		
    	ParameterizedBeanPropertyRowMapper<SysUserTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysUserTo>();
    	rowMapper.setMappedClass(SysUserTo.class);
    	
    	logger.debug(sql);

    	List<SysUserTo> result = sjt.query(sql,rowMapper, new Object[] {username,password} );		
		if (result != null && result.size() > 0) {	return true;} else {return false;}
	}
	
	public int saveLastLoginInfo(String userId,String lastIP) throws DataAccessException{
		
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		sql = " UPDATE SYS_USER "
			+ "	SET LAST_IP 	= ? , "
			+ "		LAST_TIME 	= SYSDATE "
			+ "	WHERE  USER_ID 	= ? ";
			
		logger.debug(sql);
		int answer = sjt.update( sql, new Object[]{lastIP,userId});		
		return answer;		
	}
	
	public SysUserTo getUserDetail(String userId)throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql = " SELECT "
					+ 		" A.USER_ID " 
					+		" ,A.REAL_NAME " 
					+		" ,A.PASSWORD " 
					+		" ,A.COMPANY " 
					+		" ,A.DEPARTMENT " 
					+		" ,A.POSITION " 
					+		" ,A.EMAIL " 
					+		" ,A.PHONE_NUM " 
					+		" ,A.ACTIVE " 
					+		" ,A.LAST_TIME " 
					+		" ,A.LAST_IP " 
					+		" ,A.CDT " 
					+		" ,A.CREATE_BY " 
					+		" ,A.UPDATE_BY " 
					+		" ,A.UDT " 

					+ " FROM SYS_USER A "
					+ " Where USER_ID=? ";
		
    	ParameterizedBeanPropertyRowMapper<SysUserTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysUserTo>();
    	rowMapper.setMappedClass(SysUserTo.class);

    	List<SysUserTo> result = sjt.query(sql,rowMapper, new Object[] {userId} );		
		if (result != null && result.size() > 0){
			return result.get(0);
		} else {
			return null;
		}
	}
	
	public SysUserTo getActiveCurUser(String userId)throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql 	= "SELECT " 
					+ 		" A.USER_ID " 
					+		" ,A.REAL_NAME " 
					+		" ,A.PASSWORD " 
					+		" ,A.COMPANY " 
					+		" ,A.DEPARTMENT " 
					+		" ,A.POSITION " 
					+		" ,A.EMAIL " 
					+		" ,A.PHONE_NUM " 
					+		" ,A.ACTIVE " 
					+		" ,A.LAST_TIME " 
					+		" ,A.LAST_IP " 
					+		" ,A.CDT " 
					+		" ,A.CREATE_BY " 
					+		" ,A.UPDATE_BY " 
					+		" ,A.UDT " 

					+ " FROM SYS_USER A "
					+ " Where 1=1 " 
					+		" AND ACTIVE = '1' AND USER_ID=? ";

		ParameterizedBeanPropertyRowMapper<SysUserTo> rowMapper 
			= new ParameterizedBeanPropertyRowMapper<SysUserTo>();
		rowMapper.setMappedClass(SysUserTo.class);

		List<SysUserTo> result = sjt.query(sql, rowMapper,new Object[] { userId });
		if (result != null && result.size() > 0) {return result.get(0);} else {return null;}
	}
	
	public List getUserList(String userId, String roleId)throws DataAccessException{	
	
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		List<SysUserTo> result;
		List<SysUserTo> sysUsers;
		String sql = "";
		sql = " SELECT"
			+ " 	A.USER_ID,"
			+ "     C.ROLE_NAME,"
			+ " 	A.REAL_NAME,"			
			+ "		A.PASSWORD,"
			+ " 	A.COMPANY,"
			+ " 	A.DEPARTMENT,"
			+ " 	A.POSITION,"
			+ " 	A.EMAIL,"
			+ " 	A.PHONE_NUM,"
			+ " 	A.ACTIVE,"
			+ " 	A.LAST_TIME,"
			+ " 	A.LAST_IP,"
			+ " 	A.CDT,"
			+ " 	A.CREATE_BY,"
			+ " 	A.UPDATE_BY,"
			+ " 	TO_CHAR(A.UDT,'YYYY/MM/DD HH24:MI:SS') AS UDT"
			+ " FROM"
			+ " 	SYS_USER A,"
			+ " 	SYS_USER_ROLE B,"
			+ "		SYS_ROLE C "
			+ " WHERE"
			+ " 	A.USER_ID = B.USER_ID (+)"
			+ "		AND B.ROLD_ID = C.ROLE_ID (+)" ;
	
			if (roleId != null && !roleId.equals("")) {
				sql +=  "	AND B.ROLD_ID = ?";
			}
			if(userId != null && !userId.equals("")){
				userId = userId.replace('*', '%');
				if( userId.contains("%")) {
					sql += "	AND B.USER_ID LIKE ?";
				}
				else {
					sql += "	AND B.USER_ID = ?";
				}
			}
			sql += " ORDER BY 1";
		
    	ParameterizedBeanPropertyRowMapper<SysUserTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysUserTo>();
    	rowMapper.setMappedClass(SysUserTo.class);

    	logger.debug(sql);
    	
    	if(!userId.equals("") && !roleId.equals("")) {
    		logger.debug("User Id : " + userId + ", Role Id : " + roleId);
    		sysUsers = sjt.query(sql,rowMapper, new Object[] {roleId, userId} );
    	} else {
    		if(userId.equals("") && !roleId.equals("")) {
    			logger.debug(" Role Id : " + roleId);
    			sysUsers = sjt.query(sql,rowMapper, new Object[] {roleId} );
    		} else {
    			if(!userId.equals("") && roleId.equals("")) {
    				logger.debug("User Id : " + userId );
    				sysUsers = sjt.query(sql,rowMapper, new Object[] {userId} );
    			}
    			else {
    				sysUsers = sjt.query(sql,rowMapper, new Object[] {} );
    			}
    		}
    	}
    	result = new ArrayList<SysUserTo>();
		if (sysUsers != null && sysUsers.size() > 0) {
			for (int i =0; i < sysUsers.size(); i ++){
				SysUserTo sysUser = (SysUserTo) sysUsers.get(i);
				
				// Decode the field of Password.
				String erroorPas = "Decode Error";
				
				// check password
				String newPassword = "";				
				
				if (sysUser.getPassword()== null || sysUser.getPassword().equals("")){
					newPassword = erroorPas;
				}
				
				// subcon : but password is not error format
				if (!sysUser.getCompany().equals(CistaUtil.CISTA_ROLE)){
					try	{
						newPassword = CistaUtil.decodePasswd(sysUser.getPassword());
					}catch(Exception e)	{
						newPassword = erroorPas;
					}					
					
					if (newPassword==null|| newPassword.equals("")){
						newPassword = erroorPas;
					}
				}
				sysUser.setPassword(newPassword);
				result.add(sysUser);
			}
			return result;
		} else {
			return null;
		}
	}
	
	public List<SysRoleTo> getAllRoles() throws DataAccessException {
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.ROLE_ID ,A.ROLE_NAME "
					+ " FROM SYS_ROLE A " 
					+ " ORDER BY A.ROLE_ID ";
		
    	ParameterizedBeanPropertyRowMapper<SysRoleTo> rowMapper = new ParameterizedBeanPropertyRowMapper<SysRoleTo>();
		rowMapper.setMappedClass(SysRoleTo.class);

    	List<SysRoleTo> result = sjt.query(sql,rowMapper, new Object[] {} );		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}
	}
	
	public int deleteUser(String userId) throws DataAccessException{
		
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		sql = " DELETE FROM SYS_USER A "
			+ " WHERE a.USER_ID= ? " ;
		
		int answer = sjt.update(sql	, new Object[]{userId});
		return answer ;
	}
	
	public int disableUser(String userId, String updateBy)throws DataAccessException{
		
		int result =0;
		String sql ="UPDATE SYS_USER SET ACTIVE='0',UPDATE_BY=?,UDT=SYSDATE WHERE USER_ID = ?";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		try {
			result = sjt.update(sql, new Object[] {updateBy,userId});
		}catch(Exception ex) {
			logger.debug("error occurred@UserDAO.disableUser" + ex.getMessage());
			result = 0;
		}
		return result;
	}
	
	public int updateUserActive(String userId, String updateBy , String active)throws DataAccessException{
		
		int result =0;
		String sql ="UPDATE SYS_USER SET ACTIVE=?,UPDATE_BY=?,UDT=SYSDATE WHERE USER_ID = ?";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		try {
			result = sjt.update(sql, new Object[] {active,updateBy,userId});
		}catch(Exception ex) {
			logger.debug("error occurred@UserDAO.disableUser" + ex.getMessage());
			result = 0;
		}
		return result;
	}
	
	public int updateUser(SysUserTo user)throws DataAccessException{	
		
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		sql = " UPDATE SYS_USER A "
			+ "	SET "
			+ "		A.REAl_NAME 	= ? "			
			+ "		,A.PASSWORD 	= ? "
			+ "		,A.COMPANY 		= ? "
			+ "     ,A.DEPARTMENT 	= ? "
			+ "     ,A.POSITION 	= ? "
			+ "     ,A.EMAIL 		= ? "
			+ "		,A.PHONE_NUM 	= ? "
			+ "     ,A.UPDATE_BY 	= ? "	
			+ "     ,A.UDT = SYSDATE "
			+ "	WHERE  A.USER_ID 	= ? ";		
		
		int answer = sjt.update(
				sql	, new Object[]{	
						user.getRealName()
						,user.getPassword()
						,user.getCompany()
						,user.getDepartment()
						,user.getPosition()
						,user.getEmail()
						,user.getPhoneNum()
						,user.getUpdateBy()					
						,user.getUserId()
				}
		);
		
		return answer;	
	}
	
	public int insertUser(SysUserTo newUser) throws DataAccessException {
		
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		sql = " INSERT INTO SYS_USER ( "
			+ " 	USER_ID "
			+ " 	,REAL_NAME "
			+ " 	,PASSWORD "
			+ " 	,COMPANY "
			+ " 	,DEPARTMENT "
			+ " 	,POSITION "
			+ " 	,EMAIL "
			+ " 	,PHONE_NUM "
			+ " 	,ACTIVE "
			+ " 	,CREATE_BY "
			+ " )VALUES (?,?,?,?,?,?,?,?,?,?) " ;			
				
		int result = sjt.update(sql, new Object[] { newUser.getUserId(),
				newUser.getRealName(), newUser.getPassword(),
				newUser.getCompany(), newUser.getDepartment(),
				newUser.getPosition(), newUser.getEmail(),
				newUser.getPhoneNum(), newUser.getActive(),
				newUser.getCreateBy() });
		
		return result;	
	}
}
