package com.clt.quotation.config.dao;

import java.util.ArrayList;
import java.util.List;

import com.clt.quotation.config.to.QuoPaperTo;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;


import com.clt.system.util.BaseDao;

public class QuoPaperDao extends BaseDao {
	
	public QuoPaperTo getPapersByKey(String paperUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.PAPER_UID, A.PAPER, A.PAPER_REMARK, A.CDT " +
				" FROM QUO_PAPER A " +
				" WHERE A.PAPER_UID = '" + paperUid + "'";

		logger.debug(sql);
    	ParameterizedBeanPropertyRowMapper<QuoPaperTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoPaperTo>();
    	rowMapper.setMappedClass(QuoPaperTo.class);

    	List<QuoPaperTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	
	public List<QuoPaperTo> getPapersByCatalog(String catalogUid) throws DataAccessException{
			
			SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
			String sql  = " SELECT B.CATALOG , A.PAPER_UID, A.PAPER, A.PAPER_REMARK " +
					" FROM QUO_PAPER A , QUO_CATALOG B, QUO_PAPER_CATALOG C " +
					" WHERE A.PAPER_UID = C.PAPER_UID " +
					" AND B.CATALOG_UID = C.CATALOG_UID ";
			
			if( !catalogUid.equals("ALL") ){
				sql = sql + " AND B.CATALOG_UID = '" + catalogUid + "'";
			}
			logger.debug(sql);
	    	ParameterizedBeanPropertyRowMapper<QuoPaperTo> rowMapper = 
	    		new ParameterizedBeanPropertyRowMapper<QuoPaperTo>();
	    	rowMapper.setMappedClass(QuoPaperTo.class);

	    	List<QuoPaperTo> result = sjt.query(sql,rowMapper, new Object[] {} );
			
			if (result != null && result.size() > 0) {
				return result;
			} else {
				return null;
			}	
	}
	
	public int[] instPaperByKey(QuoPaperTo paperTo) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into QUO_PAPER ( PAPER_UID, " +
				" PAPER ) " +
		" Values ( ?, ? )";
		
		List<Object[]> batch = new ArrayList<Object[]>();
		
		    Object[] values = new Object[] {
		    		paperTo.getPaperUid(),
		    		paperTo.getPaper()
					};
		    batch.add(values);
		
		
		logger.debug(sql+ " " +values.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
}
