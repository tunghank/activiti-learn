package com.clt.quotation.config.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.config.to.QuoPaperFieldTo;
import com.clt.system.util.BaseDao;


public class QuoPaperFieldDao extends BaseDao {
	

	public int[] batchInstPaperFields(List<QuoPaperFieldTo> fieldList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into QUO_PAPER_FIELD ( PAPER_FIELD_UID, PAPER_GROUP_UID, " +
				" FIELD, FIELD_SEQ, FIELD_ATTR, FIELD_NECESS, FIELD_VALUE, " +
				" NUMBER_START, NUMBER_END ) " +
		" Values ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		
		List<Object[]> batch = new ArrayList<Object[]>();
		for (QuoPaperFieldTo fieldTo : fieldList) {
		    Object[] values = new Object[] {
		    		fieldTo.getPaperFieldUid(),
		    		fieldTo.getPaperGroupUid(),
		    		fieldTo.getField(),
		    		fieldTo.getFieldSeq(),
		    		fieldTo.getFieldAttr(),
		    		fieldTo.getFieldNecess(),
		    		fieldTo.getFieldValue(),
		    		fieldTo.getNumberStart(),
		    		fieldTo.getNumberEnd()
					};
		    batch.add(values);
		}
	
		logger.debug(sql);
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	
	public List<QuoPaperFieldTo> getFildByGroup(String paperGroupUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.PAPER_FIELD_UID, A.PAPER_GROUP_UID, " +
				" A.FIELD, A.FIELD_SEQ, A.FIELD_ATTR, A.FIELD_NECESS, " +
				" A.FIELD_VALUE, A.NUMBER_START, A.NUMBER_END, A.CDT " +
				" FROM QUO_PAPER_FIELD A " +
				" WHERE A.PAPER_GROUP_UID = '" + paperGroupUid + "'" +
				" ORDER BY FIELD_SEQ ";
		
		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<QuoPaperFieldTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoPaperFieldTo>();
    	rowMapper.setMappedClass(QuoPaperFieldTo.class);

    	List<QuoPaperFieldTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	

	public int delFieldByGroupUid(String paperGroupUid) throws DataAccessException{
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
    	sql = " DELETE FROM QUO_PAPER_FIELD A "
			+ " WHERE A.PAPER_GROUP_UID = ? " ;    	
    	logger.debug(sql);	
		int result = sjt.update(sql	, new Object[]{paperGroupUid});		
		return result;		
	}
	

}
