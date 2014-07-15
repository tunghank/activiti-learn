package com.clt.quotation.quote.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.quote.to.QuoteRecordTo;
import com.clt.system.util.BaseDao;

public class QuoteRecordDao extends BaseDao {
	
	public int[] batchInstQuoteRecord(List<QuoteRecordTo> quoteRecordList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into QUOTE_RECORD ( " +
				" QUOTE_RECORD_UID, QUOTE_HEADER_UID, PAPER_VER_UID, " +
				" PAPER_GROUP_UID, PAPER_FIELD_UID, RECORD_ROW_SEQ, FIELD_SEQ, " +
				" RECORD_VALUE ) " +
				" Values ( ?, ?, ?, ?, ?, ?, ?, ? )";
		
		
		
		List<Object[]> batch = new ArrayList<Object[]>();

		for (QuoteRecordTo recordTo : quoteRecordList) {

		    Object[] values = new Object[] {
		    		recordTo.getQuoteRecordUid(),
		    		recordTo.getQuoteHeaderUid(),
		    		recordTo.getPaperVerUid(),
		    		recordTo.getPaperGroupUid(),
		    		recordTo.getPaperFieldUid(),
		    		recordTo.getRecordRowSeq(),
		    		recordTo.getFieldSeq(),
		    		recordTo.getRecordValue()
		    	};
		    batch.add(values);
		}
	
		logger.debug(sql + batch.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	
	public List<QuoteRecordTo> getQuoteRecordCountByGroup(String quoteHeaderUid, 
			String paperVerUid, String paperGroupUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT DISTINCT A.QUOTE_HEADER_UID, A.PAPER_VER_UID, " +
				" A.PAPER_GROUP_UID, A.RECORD_ROW_SEQ " +
				" FROM QUOTE_RECORD A" +
				" WHERE 1=1 " +
				" AND A.QUOTE_HEADER_UID ='" + quoteHeaderUid + "'" +
				" AND A.PAPER_VER_UID ='" + paperVerUid + "'" +
				" AND A.PAPER_GROUP_UID ='" + paperGroupUid + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<QuoteRecordTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoteRecordTo>();
    	rowMapper.setMappedClass(QuoteRecordTo.class);

    	List<QuoteRecordTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
	public int deleteQuoteRecord(String quoteHeaderUid, 
			String paperVerUid/*, String paperGroupUid*/) throws DataAccessException{
		
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
    	sql = " DELETE FROM QUOTE_RECORD A " +
			" WHERE A.QUOTE_HEADER_UID ='" + quoteHeaderUid + "'" +
			" AND A.PAPER_VER_UID ='" + paperVerUid + "'" /*+
			" AND A.PAPER_GROUP_UID ='" + paperGroupUid + "'"*/; 	
    	logger.debug(sql);	
		int result = sjt.update(sql	, new Object[]{});		
		return result;		
		
	}
	
	public List<QuoteRecordTo> getQuoteRecordByGroup(String quoteHeaderUid, 
			String paperVerUid, String paperGroupUid, int recordRowSeq) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.QUOTE_RECORD_UID, A.QUOTE_HEADER_UID, " +
				" A.PAPER_VER_UID, A.PAPER_GROUP_UID, A.PAPER_FIELD_UID, " +
				" A.RECORD_ROW_SEQ, A.FIELD_SEQ, A.RECORD_VALUE, A.CDT " +
				" FROM QUOTE_RECORD A" +
				" WHERE 1=1 " +
				" AND A.QUOTE_HEADER_UID ='" + quoteHeaderUid + "'" +
				" AND A.PAPER_VER_UID ='" + paperVerUid + "'" +
				" AND A.PAPER_GROUP_UID ='" + paperGroupUid + "'" +
				" AND A.RECORD_ROW_SEQ ='" + recordRowSeq + "'" +
				" Order By A.FIELD_SEQ ";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<QuoteRecordTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoteRecordTo>();
    	rowMapper.setMappedClass(QuoteRecordTo.class);

    	List<QuoteRecordTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}

}
