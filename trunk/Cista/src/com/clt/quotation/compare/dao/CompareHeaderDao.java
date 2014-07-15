package com.clt.quotation.compare.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.compare.to.CompareHeaderTo;
import com.clt.system.util.BaseDao;

public class CompareHeaderDao extends BaseDao {
	
	public int[] instCompareHeader(CompareHeaderTo compareHeaderTo) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into COMPARE_HEADER ( " +
				" COMPARE_HEADER_UID, INQUIRY_HEADER_UID, COMPARE_NUM, COMPARE_STATUS, " +
				" CLT_COMPARE_USER ) " +
				" Values ( ?, ?, ?, ?,? )";
				
		List<Object[]> batch = new ArrayList<Object[]>();

		    Object[] values = new Object[] {
		    		compareHeaderTo.getCompareHeaderUid(),
		    		compareHeaderTo.getInquiryHeaderUid(),
		    		compareHeaderTo.getCompareNum(),
		    		compareHeaderTo.getCompareStatus(),
		    		compareHeaderTo.getCltCompareUser()
		    	};
		    
		    batch.add(values);
	
		logger.debug(sql + batch.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	

}
