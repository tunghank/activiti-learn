package com.clt.quotation.compare.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.compare.to.CompareSeqnumTo;

import com.clt.system.util.BaseDao;

public class CompareSeqnumDao extends BaseDao {
	
	public boolean isSeqNumExisted(String year, String month) throws DataAccessException{
			
			SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
			boolean ret = true;
			
			String sql  = " SELECT A.SEQ_YEAR, A.SEQ_MONTH, A.SEQ_NUM " +
						" FROM COMPARE_SEQNUM A" +
						" WHERE A.SEQ_YEAR='" + year + "' " +
						" AND A.SEQ_MONTH = '"+ month + "'";
			
	    	ParameterizedBeanPropertyRowMapper<CompareSeqnumTo> rowMapper = 
	    		new ParameterizedBeanPropertyRowMapper<CompareSeqnumTo>();
	    	rowMapper.setMappedClass(CompareSeqnumTo.class);

	    	List<CompareSeqnumTo> result = sjt.query(sql,rowMapper, new Object[] {} );
			
			if (result != null && result.size() > 0) {
				return ret;
			} else {
				return false;
			}	
	}
	
	public int insertSeqNum(String year, String month, int seqNum) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into COMPARE_SEQNUM ( SEQ_YEAR, SEQ_MONTH, " +
        			" SEQ_NUM )  values ( ?,?,? )";
				
		
    	logger.debug(sql);
    	int result = sjt.update(sql, 
    			new Object[] {year, month, seqNum} );
    	
    	return result;
	}
	
	public int updateSeqNum(String year, String month, int seqNum) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		seqNum = seqNum + 1;
		String sql =  " Update COMPARE_SEQNUM Set SEQ_NUM = ? " +
		" Where SEQ_YEAR = ? And SEQ_MONTH = ? ";
				
		
    	logger.debug(sql);
    	int result = sjt.update(sql, 
    			new Object[] {seqNum, year, month} );
    	
    	return result;
		
	}
	
	public int getSeqNum(String year, String month) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		int ret = 0;

		String sql = " SELECT A.SEQ_YEAR, A.SEQ_MONTH, A.SEQ_NUM " +
					" FROM COMPARE_SEQNUM A " +
					" Where A.SEQ_YEAR = ? And A.SEQ_MONTH = ? ";
		
    	ParameterizedBeanPropertyRowMapper<CompareSeqnumTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<CompareSeqnumTo>();
    	rowMapper.setMappedClass(CompareSeqnumTo.class);

    	logger.debug(sql);
    	List<CompareSeqnumTo> result = sjt.query(sql,rowMapper, 
    			new Object[] {year, month } );
		
		if (result != null && result.size() > 0) {
			ret =result.get(0).getSeqNum();
		}
		return ret;
	}
}
