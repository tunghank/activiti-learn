package com.clt.quotation.compare.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.compare.to.CompareMapQuoteTo;
import com.clt.system.util.BaseDao;

public class CompareMapQuoteDao extends BaseDao {
	
	public int[] batchInstCompareMapQuote(List<CompareMapQuoteTo> compareMapQuoteList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into COMPARE_MAP_QUOTE ( " +
				" COMPARE_MAP_QUOTE_UID, COMPARE_HEADER_UID, " +
				" QUOTE_HEADER_UID, COMPARE_RATIO ) " +
		" Values ( ?, ?, ?, ? )";
			
		List<Object[]> batch = new ArrayList<Object[]>();

		for (CompareMapQuoteTo compareMapQuoteTo : compareMapQuoteList) {
		    Object[] values = new Object[] {
		    		compareMapQuoteTo.getCompareMapQuoteUid(),
		    		compareMapQuoteTo.getCompareHeaderUid(),
		    		compareMapQuoteTo.getQuoteHeaderUid(),
		    		compareMapQuoteTo.getCompareRatio()
		    	};
		    batch.add(values);
		}
	
		logger.debug(sql + batch.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	

}
