package com.clt.quotation.config.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.config.to.QuoPaperGroupTo;
import com.clt.system.util.BaseDao;

public class QuoPaperGroupDao extends BaseDao {
	

	public int[] instPaperGroup(QuoPaperGroupTo paperGroupTo) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into QUO_PAPER_GROUP ( PAPER_GROUP_UID, PAPER_VER_UID, " +
				" PAPER_GROUP_NAME, PAPER_CREATE_BY, PAPER_GROUP_SEQ ) " +
		" Values ( ?, ?, ?, ?, ? )";
		
		List<Object[]> batch = new ArrayList<Object[]>();
		
		    Object[] values = new Object[] {
		    		paperGroupTo.getPaperGroupUid(),
		    		paperGroupTo.getPaperVerUid(),
		    		paperGroupTo.getPaperGroupName(),
		    		paperGroupTo.getPaperCreateBy(),
		    		paperGroupTo.getPaperGroupSeq()
					};
		    batch.add(values);
		
		
		logger.debug(sql+ " " +values.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	

	
	public List<QuoPaperGroupTo> getGroupByPaperVer(String paperVerUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.PAPER_GROUP_UID, A.PAPER_VER_UID, " +
				" A.PAPER_GROUP_NAME, A.PAPER_CREATE_BY, A.CDT, A.PAPER_GROUP_SEQ " +
				" FROM QUO_PAPER_GROUP A " +
				" WHERE A.PAPER_VER_UID = '" + paperVerUid + "'" +
				" Order by PAPER_GROUP_SEQ ";
		
		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<QuoPaperGroupTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoPaperGroupTo>();
    	rowMapper.setMappedClass(QuoPaperGroupTo.class);

    	List<QuoPaperGroupTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
	public QuoPaperGroupTo getPaperGroupByKey(String paperGroupUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.PAPER_GROUP_UID, A.PAPER_VER_UID, " +
				" A.PAPER_GROUP_NAME, A.PAPER_CREATE_BY, A.CDT, A.PAPER_GROUP_SEQ " +
				" FROM QUO_PAPER_GROUP A " +
				" WHERE A.PAPER_GROUP_UID = '" + paperGroupUid + "'" +
				" Order by PAPER_GROUP_SEQ ";
		
		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<QuoPaperGroupTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoPaperGroupTo>();
    	rowMapper.setMappedClass(QuoPaperGroupTo.class);

    	List<QuoPaperGroupTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	
	public int delPaperGroupByKey(String paperGroupUid) throws DataAccessException{
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
    	sql = " DELETE FROM QUO_PAPER_GROUP A "
			+ " WHERE A.PAPER_GROUP_UID = ? " ;    	
    	logger.debug(sql);	
		int result = sjt.update(sql	, new Object[]{paperGroupUid});		
		return result;		
	}



}
