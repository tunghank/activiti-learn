package com.clt.quotation.quote.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.quote.to.QuoteHeaderTo;
import com.clt.system.util.BaseDao;

public class QuoteHeaderDao extends BaseDao {
	
	public int[] batchInstQuoteHeader(List<QuoteHeaderTo> quoteHeaderList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into QUOTE_HEADER ( " +
				" QUOTE_HEADER_UID, QUOTE_NUM, INQUIRY_HEADER_UID, " +
				" INQUIRY_PART_NUM_UID, INQUIRY_SUPPLIER_UID, " +
				" QUOTE_SUPPLIER_CODE, CLT_CONTACT_USER, QUOTE_OFFER_PEOPLE, " +
				" QUOTE_PART_NUM, QUOTE_PART_NUM_DESC, QUOTE_TOTAL, " +
				" QUOTE_REAL_TOTAL, QUOTE_TFT_TOTAL, QUOTE_TAX, " +
				" QUOTE_NOTES ) " +
		" Values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		
		
		
		List<Object[]> batch = new ArrayList<Object[]>();

		for (QuoteHeaderTo headerTo : quoteHeaderList) {

		    Object[] values = new Object[] {
		    		headerTo.getQuoteHeaderUid(),
		    		headerTo.getQuoteNum(),
		    		headerTo.getInquiryHeaderUid(),
		    		headerTo.getInquiryPartNumUid(),
		    		headerTo.getInquirySupplierUid(),
		    		headerTo.getQuoteSupplierCode(),
		    		headerTo.getCltContactUser(),
		    		headerTo.getQuoteOfferPeople(),
		    		headerTo.getQuotePartNum(),
		    		headerTo.getQuotePartNumDesc(),
		    		headerTo.getQuoteTotal(),
		    		headerTo.getQuoteRealTotal(),
		    		headerTo.getQuoteTftTotal(),
		    		headerTo.getQuoteTax(),
		    		headerTo.getQuoteNotes()
		    	};
		    batch.add(values);
		}
	
		logger.debug(sql + batch.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	
	public QuoteHeaderTo getQuoteHeaderByQuote(String quoteNum) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.QUOTE_HEADER_UID, A.QUOTE_NUM, " +
			" A.INQUIRY_HEADER_UID, A.INQUIRY_PART_NUM_UID, A.INQUIRY_SUPPLIER_UID, " +
			" A.QUOTE_SUPPLIER_CODE, A.CLT_CONTACT_USER, " +
			" A.QUOTE_OFFER_PEOPLE, A.QUOTE_PART_NUM, A.QUOTE_PART_NUM_DESC, " +
			" A.QUOTE_TOTAL, A.QUOTE_REAL_TOTAL, A.QUOTE_TFT_TOTAL, " +
			" A.QUOTE_TAX, A.QUOTE_NOTES, A.CDT, " +
			" A.QUOTE_STATUS, A.FIRST_QUOTE_DATE, A.LAST_QUOTE_DATE, A.QUOTE_COUNT " +
			" FROM QUOTE_HEADER A " +
			" WHERE 1=1 " +
			" AND A.QUOTE_NUM ='" + quoteNum + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<QuoteHeaderTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoteHeaderTo>();
    	rowMapper.setMappedClass(QuoteHeaderTo.class);

    	List<QuoteHeaderTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}

	public QuoteHeaderTo getQuoteHeaderByKey(String quoteHeaderUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.QUOTE_HEADER_UID, A.QUOTE_NUM, " +
			" A.INQUIRY_HEADER_UID, A.INQUIRY_PART_NUM_UID, A.INQUIRY_SUPPLIER_UID, " +
			" A.QUOTE_SUPPLIER_CODE, A.CLT_CONTACT_USER, " +
			" A.QUOTE_OFFER_PEOPLE, A.QUOTE_PART_NUM, A.QUOTE_PART_NUM_DESC, " +
			" A.QUOTE_TOTAL, A.QUOTE_REAL_TOTAL, A.QUOTE_TFT_TOTAL, " +
			" A.QUOTE_TAX, A.QUOTE_NOTES, A.CDT, " +
			" A.QUOTE_STATUS, A.FIRST_QUOTE_DATE, A.LAST_QUOTE_DATE, A.QUOTE_COUNT " +
			" FROM QUOTE_HEADER A " +
			" WHERE 1=1 " +
			" AND A.QUOTE_HEADER_UID ='" + quoteHeaderUid + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<QuoteHeaderTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoteHeaderTo>();
    	rowMapper.setMappedClass(QuoteHeaderTo.class);

    	List<QuoteHeaderTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	
	public QuoteHeaderTo getQuoteHeaderByInquiry(String inquiryHeaderUid,
			String inquiryPartNumUid, String inquirySupplierUid ) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.QUOTE_HEADER_UID, A.QUOTE_NUM, " +
			" A.INQUIRY_HEADER_UID, A.INQUIRY_PART_NUM_UID, A.INQUIRY_SUPPLIER_UID, " +
			" A.QUOTE_SUPPLIER_CODE, A.CLT_CONTACT_USER, " +
			" A.QUOTE_OFFER_PEOPLE, A.QUOTE_PART_NUM, A.QUOTE_PART_NUM_DESC, " +
			" A.QUOTE_TOTAL, A.QUOTE_REAL_TOTAL, A.QUOTE_TFT_TOTAL, " +
			" A.QUOTE_TAX, A.QUOTE_NOTES, A.CDT, " +
			" A.QUOTE_STATUS, A.FIRST_QUOTE_DATE, A.LAST_QUOTE_DATE, A.QUOTE_COUNT " +
			" FROM QUOTE_HEADER A " +
			" WHERE 1=1 " +
			" AND A.INQUIRY_HEADER_UID ='" + inquiryHeaderUid + "'" +
			" AND A.INQUIRY_PART_NUM_UID ='" + inquiryPartNumUid + "'" +
			" AND A.INQUIRY_SUPPLIER_UID ='" + inquirySupplierUid + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<QuoteHeaderTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoteHeaderTo>();
    	rowMapper.setMappedClass(QuoteHeaderTo.class);

    	List<QuoteHeaderTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	
	public int updateQuoteHeader(QuoteHeaderTo quoteHeader) throws DataAccessException{
		
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		sql = " UPDATE QUOTE_HEADER " + 
			"	SET QUOTE_OFFER_PEOPLE 	= ? , " + 
			"		QUOTE_TOTAL = ? , " +
			"		QUOTE_REAL_TOTAL = ? , " +
			"		QUOTE_TFT_TOTAL = ? , " +
			"		QUOTE_TAX = ? , " +
			"		QUOTE_NOTES = ? ," +
			"		QUOTE_STATUS = ? ," +
			"		FIRST_QUOTE_DATE = ? ," +
			"		LAST_QUOTE_DATE = ? ," +
			"		QUOTE_COUNT = QUOTE_COUNT + 1 " +
			"	WHERE  QUOTE_HEADER_UID = ? ";
			
		logger.debug(sql);
		int result = sjt.update( sql, new Object[]{
				quoteHeader.getQuoteOfferPeople(),
				quoteHeader.getQuoteTotal(),
				quoteHeader.getQuoteRealTotal(),
				quoteHeader.getQuoteTftTotal(),
				quoteHeader.getQuoteTax(),
				quoteHeader.getQuoteNotes(),
				quoteHeader.getQuoteStatus(),
				quoteHeader.getFirstQuoteDate(),
				quoteHeader.getLastQuoteDate(),
				//COUNT
				quoteHeader.getQuoteHeaderUid()
		});		
		return result;		
	}
	
	public List<QuoteHeaderTo> getQuoteHeaderByInquiryUid(String inquiryHeaderUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.QUOTE_HEADER_UID, A.QUOTE_NUM, " +
			" A.INQUIRY_HEADER_UID, A.INQUIRY_PART_NUM_UID, A.INQUIRY_SUPPLIER_UID, " +
			" A.QUOTE_SUPPLIER_CODE, A.CLT_CONTACT_USER, " +
			" A.QUOTE_OFFER_PEOPLE, A.QUOTE_PART_NUM, A.QUOTE_PART_NUM_DESC, " +
			" A.QUOTE_TOTAL, A.QUOTE_REAL_TOTAL, A.QUOTE_TFT_TOTAL, " +
			" A.QUOTE_TAX, A.QUOTE_NOTES, A.CDT, " +
			" A.QUOTE_STATUS, A.FIRST_QUOTE_DATE, A.LAST_QUOTE_DATE, A.QUOTE_COUNT " +
			" FROM QUOTE_HEADER A " +
			" WHERE 1=1 " +
			" AND A.INQUIRY_HEADER_UID ='" + inquiryHeaderUid + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<QuoteHeaderTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoteHeaderTo>();
    	rowMapper.setMappedClass(QuoteHeaderTo.class);

    	List<QuoteHeaderTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
}
