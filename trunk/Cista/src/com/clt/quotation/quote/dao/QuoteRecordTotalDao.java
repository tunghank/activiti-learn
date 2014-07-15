package com.clt.quotation.quote.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.quote.to.QuoteRecordTotalTo;
import com.clt.system.util.BaseDao;

public class QuoteRecordTotalDao extends BaseDao {
	
	public int[] batchInstQuoteRecordTotal(List<QuoteRecordTotalTo> quoteRecordTotalList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into QUOTE_RECORD_TOTAL ( " +
				" QUOTE_RECORD_TOTAL_UID, QUOTE_HEADER_UID, PAPER_VER_UID, " +
				" PAPER_GROUP_UID, RECORD_TOTAL ) " +
				" Values ( ?, ?, ?, ?, ? )";
				
		List<Object[]> batch = new ArrayList<Object[]>();

		for (QuoteRecordTotalTo recordTotalTo : quoteRecordTotalList) {

		    Object[] values = new Object[] {
		    		recordTotalTo.getQuoteRecordTotalUid(),
		    		recordTotalTo.getQuoteHeaderUid(),
		    		recordTotalTo.getPaperVerUid(),
		    		recordTotalTo.getPaperGroupUid(),
		    		recordTotalTo.getRecordTotal()

		    	};
		    batch.add(values);
		}
	
		logger.debug(sql + batch.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	
	public QuoteRecordTotalTo getQuoteRecordTotalByGroup(String quoteHeaderUid, 
			String paperVerUid, String paperGroupUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.QUOTE_RECORD_TOTAL_UID, A.QUOTE_HEADER_UID, " +
				" A.PAPER_VER_UID, A.PAPER_GROUP_UID, A.RECORD_TOTAL, A.CDT " +
				" FROM QUOTE_RECORD_TOTAL A" +
				" WHERE 1=1 " +
				" AND A.QUOTE_HEADER_UID ='" + quoteHeaderUid + "'" +
				" AND A.PAPER_VER_UID ='" + paperVerUid + "'" +
				" AND A.PAPER_GROUP_UID ='" + paperGroupUid + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<QuoteRecordTotalTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoteRecordTotalTo>();
    	rowMapper.setMappedClass(QuoteRecordTotalTo.class);

    	List<QuoteRecordTotalTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}

	public List<QuoteRecordTotalTo> getQuoteRecordTotalByQuoteHeaderUid(String quoteHeaderUid, 
			String paperVerUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.QUOTE_RECORD_TOTAL_UID, A.QUOTE_HEADER_UID, " +
				" A.PAPER_VER_UID, A.PAPER_GROUP_UID, A.RECORD_TOTAL, A.CDT " +
				" FROM QUOTE_RECORD_TOTAL A" +
				" WHERE 1=1 " +
				" AND A.QUOTE_HEADER_UID ='" + quoteHeaderUid + "'" +
				" AND A.PAPER_VER_UID ='" + paperVerUid + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<QuoteRecordTotalTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoteRecordTotalTo>();
    	rowMapper.setMappedClass(QuoteRecordTotalTo.class);

    	List<QuoteRecordTotalTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
	public int deleteQuoteRecordTotal(String quoteHeaderUid, 
			String paperVerUid/*, String paperGroupUid*/) throws DataAccessException{
		
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
    	sql = " DELETE FROM QUOTE_RECORD_TOTAL A " +
			" WHERE A.QUOTE_HEADER_UID ='" + quoteHeaderUid + "'" +
			" AND A.PAPER_VER_UID ='" + paperVerUid + "'" /*+
			" AND A.PAPER_GROUP_UID ='" + paperGroupUid + "'"*/; 	
    	logger.debug(sql);	
		int result = sjt.update(sql	, new Object[]{});		
		return result;		
		
	}
}
