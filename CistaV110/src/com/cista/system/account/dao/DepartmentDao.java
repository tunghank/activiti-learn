package com.cista.system.account.dao;
import java.util.List;

import com.cista.system.to.SysDepartmentTo;
import com.cista.system.util.BaseDao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;


public class DepartmentDao extends BaseDao{	
	
	public SysDepartmentTo getDepartmentDetail(String departName)throws DataAccessException{			
		String sql = "";	
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		ParameterizedBeanPropertyRowMapper<SysDepartmentTo> rowMapper = 
	    		new ParameterizedBeanPropertyRowMapper<SysDepartmentTo>();
	    	rowMapper.setMappedClass(SysDepartmentTo.class);

		sql = " SELECT "
			+ "	   A.COMPANY "
			+ "    ,A.DEPART_NAME "
			+ "    ,A.DEPART_DESCRIPTION "
			+ "    ,A.CDT "
			+ " FROM SYS_DEPARTMENT A "
			+ " WHERE "
			+ "    1 = 1 "
			+ "    AND A.DEPART_NAME = ? " ;	   
			
	    List<SysDepartmentTo> result = sjt.query(sql,rowMapper, new Object[] {departName} );
			
		if (result != null && result.size() > 0) {	
			return result.get(0);
		} else {									
			return null;
		}
	}	
	
	public int insertDepartment(SysDepartmentTo newDepartment)throws DataAccessException{
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		
		sql = " INSERT INTO SYS_DEPARTMENT (COMPANY,DEPART_NAME,DEPART_DESCRIPTION,CDT) "
			+ " VALUES ( ?,?,?,TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'))" ;
		
		int answer = sjt.update(sql, new Object[]
		   {newDepartment.getCompany(),newDepartment.getDepartName(),newDepartment.getDepartDescription()}
		);
		return answer ;
	}
	
	public int updateDepartment(SysDepartmentTo curDepartment ,String oldDepartmentName)throws DataAccessException{
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		ParameterizedBeanPropertyRowMapper<SysDepartmentTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysDepartmentTo>();
    	rowMapper.setMappedClass(SysDepartmentTo.class);
    	
    	sql = "  UPDATE SYS_DEPARTMENT A "
    		+ "  SET   A.DEPART_DESCRIPTION = ? , A.DEPART_NAME = ?"
    		+ "  WHERE A.DEPART_NAME = ? ";    	
    	   	
		int answer = sjt.update(sql	
				, new Object[]{curDepartment.getDepartDescription()
				,curDepartment.getDepartName(),oldDepartmentName});		
		return answer;		
	}

	public int deleteDepartment(String department)throws DataAccessException{
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		ParameterizedBeanPropertyRowMapper<SysDepartmentTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysDepartmentTo>();
    	rowMapper.setMappedClass(SysDepartmentTo.class);
    	
    	sql = " DELETE FROM SYS_DEPARTMENT A WHERE A.DEPART_NAME = ? " ;
		int answer = sjt.update(sql	, new Object[]{department});
		
		return answer;		
	}

	public List searchDepartmentList(String departName)throws DataAccessException{
	
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		ParameterizedBeanPropertyRowMapper<SysDepartmentTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysDepartmentTo>();
    	rowMapper.setMappedClass(SysDepartmentTo.class);	
		
		sql = " SELECT "
			+ "		A.COMPANY "
			+ "     ,A.DEPART_NAME "
			+ "     ,A.DEPART_DESCRIPTION "
			+ "     ,A.CDT "
			+ "	FROM SYS_DEPARTMENT A "
			+ " WHERE 1= 1 " ;
		
			if (departName.equals("")){ departName = "1";	sql += " And 1 = ?"; }
			else 
			{	
				departName = departName.replace('*', '%');
			
				if (departName.contains("%"))	sql += " And A.DEPART_NAME like ? ";
				else 							sql += " And A.DEPART_NAME = ? ";
			}
			
		sql+= " ORDER BY A.DEPART_NAME";
		
		List<SysDepartmentTo> result = sjt.query(sql,rowMapper, new Object[] {departName} );
		
		if (result != null && result.size() > 0) {	
			return result;
		}else {	
			return null;
		}		
	}
}
