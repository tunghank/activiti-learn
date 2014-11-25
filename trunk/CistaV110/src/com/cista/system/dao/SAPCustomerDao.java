package com.cista.system.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.system.to.TSAPCustomerTo;
import com.cista.system.to.TSAPVendorTo;
import com.cista.system.util.BaseDao;

public class SAPCustomerDao extends BaseDao {
	
	public TSAPCustomerTo getCustByKey(String customerCode) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		
		ParameterizedBeanPropertyRowMapper<TSAPCustomerTo> rowMapper = 
			new ParameterizedBeanPropertyRowMapper<TSAPCustomerTo>();
		rowMapper.setMappedClass(TSAPCustomerTo.class);
		
		String sql =  " SELECT A.CUSTOMER_CODE, A.SHORT_NAME, A.CDT " +
					" FROM T_SAP_CUSTOMER A Where A.CUSTOMER_CODE = ? ";
		

    	logger.debug(sql);
		List<TSAPCustomerTo> result = sjt.query(sql, rowMapper, new Object[] {customerCode});

		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}
	
	public List getAllCustomer() throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		
		ParameterizedBeanPropertyRowMapper<TSAPCustomerTo> rowMapper = 
			new ParameterizedBeanPropertyRowMapper<TSAPCustomerTo>();
		rowMapper.setMappedClass(TSAPCustomerTo.class);
		
		String sql 	=  " SELECT A.CUSTOMER_CODE, A.SHORT_NAME, A.CDT " 
					+  " FROM T_SAP_CUSTOMER A " 
					+  " ORDER BY A.CUSTOMER_CODE ";		

    	logger.debug(sql);
    	
    	List<TSAPCustomerTo> result = sjt.query(sql,rowMapper, new Object[] {} );

		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}
	}
}
