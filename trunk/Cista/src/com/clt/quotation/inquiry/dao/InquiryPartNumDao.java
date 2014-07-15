package com.clt.quotation.inquiry.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.inquiry.to.InquiryPartNumTo;
import com.clt.system.util.BaseDao;

public class InquiryPartNumDao extends BaseDao {
	
	public int[] batchInstInquiryPartNum(List<InquiryPartNumTo> inquiryPartNumList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into INQUIRY_PART_NUM ( " +
				" INQUIRY_PART_NUM_UID, INQUIRY_HEADER_UID, INQUIRY_MODEL, " +
				" INQUIRY_MODEL_DESC, INQUIRY_PART_NUM, INQUIRY_PART_NUM_DESC, " +
				" INQUIRY_PART_NUM_DIFFER, INQUIRY_QTY, INQUIRY_UNIT ) " +
				" Values ( ?, ?, ?, ?, ?, ?, ? ,?, ? )";
				
		List<Object[]> batch = new ArrayList<Object[]>();

		for (InquiryPartNumTo partNumTo : inquiryPartNumList) {

		    Object[] values = new Object[] {
		    		partNumTo.getInquiryPartNumUid(),
		    		partNumTo.getInquiryHeaderUid(),
		    		partNumTo.getInquiryModel(),
		    		partNumTo.getInquiryModelDesc(),
		    		partNumTo.getInquiryPartNum(),
		    		partNumTo.getInquiryPartNumDesc(),
		    		partNumTo.getInquiryPartNumDiffer(),
		    		partNumTo.getInquiryQty(),
		    		partNumTo.getInquiryUnit()
		    	};
		    batch.add(values);
		}
	
		logger.debug(sql + batch.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	
	public InquiryPartNumTo getInquiryPartNumByKey(String inquiryPartNumUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.INQUIRY_PART_NUM_UID, A.INQUIRY_HEADER_UID, " +
				" A.INQUIRY_MODEL, A.INQUIRY_MODEL_DESC, A.INQUIRY_PART_NUM, " +
				" A.INQUIRY_PART_NUM_DESC, A.INQUIRY_PART_NUM_DIFFER, " +
				" A.INQUIRY_QTY, A.INQUIRY_UNIT " +
				" FROM INQUIRY_PART_NUM A" +
				" WHERE 1=1 " +
				" AND A.INQUIRY_PART_NUM_UID ='" + inquiryPartNumUid + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<InquiryPartNumTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InquiryPartNumTo>();
    	rowMapper.setMappedClass(InquiryPartNumTo.class);

    	List<InquiryPartNumTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	
	public InquiryPartNumTo getInquiryPartNum(String inquiryHeaderUid, String inquiryPartNum) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.INQUIRY_PART_NUM_UID, A.INQUIRY_HEADER_UID, " +
				" A.INQUIRY_MODEL, A.INQUIRY_MODEL_DESC, A.INQUIRY_PART_NUM, " +
				" A.INQUIRY_PART_NUM_DESC, A.INQUIRY_PART_NUM_DIFFER, " +
				" A.INQUIRY_QTY, A.INQUIRY_UNIT " +
				" FROM INQUIRY_PART_NUM A" +
				" WHERE 1=1 " +
				" AND A.INQUIRY_HEADER_UID ='" + inquiryHeaderUid + "'" +
				" AND A.INQUIRY_PART_NUM ='" + inquiryPartNum + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<InquiryPartNumTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InquiryPartNumTo>();
    	rowMapper.setMappedClass(InquiryPartNumTo.class);

    	List<InquiryPartNumTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	
	public List<InquiryPartNumTo> getInquiryPartNumList(String inquiryHeaderUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.INQUIRY_PART_NUM_UID, A.INQUIRY_HEADER_UID, " +
				" A.INQUIRY_MODEL, A.INQUIRY_MODEL_DESC, A.INQUIRY_PART_NUM, " +
				" A.INQUIRY_PART_NUM_DESC, A.INQUIRY_PART_NUM_DIFFER, " +
				" A.INQUIRY_QTY, A.INQUIRY_UNIT " +
				" FROM INQUIRY_PART_NUM A" +
				" WHERE 1=1 " +
				" AND A.INQUIRY_HEADER_UID ='" + inquiryHeaderUid + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<InquiryPartNumTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InquiryPartNumTo>();
    	rowMapper.setMappedClass(InquiryPartNumTo.class);

    	List<InquiryPartNumTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
	public int delInquiryPartNum(String inquiryHeaderUid) throws DataAccessException{
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
    	sql = " DELETE FROM INQUIRY_PART_NUM A "
			+ " WHERE A.INQUIRY_HEADER_UID = ? " ;    	
    	logger.debug(sql);	
		int result = sjt.update(sql	, new Object[]{inquiryHeaderUid});		
		return result;		
	}
}
