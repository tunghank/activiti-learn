package com.cista.system.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.system.to.SysUserTo;
import com.cista.system.to.TSAPVendorTo;
import com.cista.system.util.BaseDao;

public class SAPVendorDao extends BaseDao {
	
	public TSAPVendorTo getVendorByKey(String vendorCode) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		
		ParameterizedBeanPropertyRowMapper<TSAPVendorTo> rowMapper = 
			new ParameterizedBeanPropertyRowMapper<TSAPVendorTo>();
		rowMapper.setMappedClass(TSAPVendorTo.class);
		
		String sql =  " SELECT A.VENDOR_CODE, A.SHORT_NAME, A.CDT " +
					" FROM T_SAP_VENDOR A Where A.VENDOR_CODE = ? ";
		

    	logger.debug(sql);
		List<TSAPVendorTo> result = sjt.query(sql, rowMapper, new Object[] {vendorCode});

		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}
	
	public List getAllVendor() throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		
		ParameterizedBeanPropertyRowMapper<TSAPVendorTo> rowMapper = 
			new ParameterizedBeanPropertyRowMapper<TSAPVendorTo>();
		rowMapper.setMappedClass(TSAPVendorTo.class);
		
		String sql 	=  " SELECT A.VENDOR_CODE, A.SHORT_NAME, A.CDT " 
					+  " FROM T_SAP_VENDOR A ";	
		
		sql = sql + "Order By A.VENDOR_CODE ";

    	logger.debug(sql);
    	
    	List<TSAPVendorTo> result = sjt.query(sql,rowMapper, new Object[] {} );

		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}
	}
	
	public List getAllMaterialVendor() throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		
		ParameterizedBeanPropertyRowMapper<TSAPVendorTo> rowMapper = 
			new ParameterizedBeanPropertyRowMapper<TSAPVendorTo>();
		rowMapper.setMappedClass(TSAPVendorTo.class);
		
		String sql 	=  " SELECT A.VENDOR_CODE, A.SHORT_NAME, A.CDT " +  
					" FROM T_SAP_VENDOR A " +
					 "WHERE A.VENDOR_CODE LIKE '2%' ";	
		
		sql = sql + "Order By A.VENDOR_CODE ";

    	logger.debug(sql);
    	
    	List<TSAPVendorTo> result = sjt.query(sql,rowMapper, new Object[] {} );

		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}
	}
}
