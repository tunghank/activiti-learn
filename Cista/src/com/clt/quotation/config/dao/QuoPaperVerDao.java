package com.clt.quotation.config.dao;

import java.util.ArrayList;
import java.util.List;

import com.clt.quotation.config.to.QuoCatalogPaperVerTo;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;


import com.clt.system.util.BaseDao;


public class QuoPaperVerDao extends BaseDao {
	
	public List<QuoCatalogPaperVerTo> getVerByPaper(String paperUid) throws DataAccessException{
			
			SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
			String sql  = " SELECT C.CATALOG, C.CATALOG_UID, B.PAPER, " +
					" A.PAPER_VER_UID, A.PAPER_UID, A.PAPER_VER, " +
					" A.PAPER_VER_APPROVE, A.VER_CREATE_BY, A.CDT, " +
					" A.VER_UPDATE_BY, A.UDT, A.VER_APPROVE_DT, " +
					" A.VER_START_DT, A.VER_END_DT " +
					" FROM QUO_PAPER_VER A , QUO_PAPER B, QUO_CATALOG C, " +
					" QUO.QUO_PAPER_CATALOG D " +
					" WHERE A.PAPER_UID = B.PAPER_UID " +
					" AND C.CATALOG_UID = D.CATALOG_UID " +
					" AND B.PAPER_UID = D.PAPER_UID " +
					" AND A.PAPER_UID ='" + paperUid +"'" +
					" ORDER BY  B.PAPER, A.PAPER_VER ";
			
			logger.debug(sql);
			
	    	ParameterizedBeanPropertyRowMapper<QuoCatalogPaperVerTo> rowMapper = 
	    		new ParameterizedBeanPropertyRowMapper<QuoCatalogPaperVerTo>();
	    	rowMapper.setMappedClass(QuoCatalogPaperVerTo.class);

	    	List<QuoCatalogPaperVerTo> result = sjt.query(sql,rowMapper, new Object[] {} );
			
			if (result != null && result.size() > 0) {
				return result;
			} else {
				return null;
			}	
	}
	
	public QuoCatalogPaperVerTo getPaperByVer(String paperVerUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT C.CATALOG, C.CATALOG_UID, B.PAPER, " +
				" A.PAPER_VER_UID, A.PAPER_UID, A.PAPER_VER, " +
				" A.PAPER_VER_APPROVE, A.VER_CREATE_BY, A.CDT, " +
				" A.VER_UPDATE_BY, A.UDT, A.VER_APPROVE_DT, " +
				" A.VER_START_DT, A.VER_END_DT " +
				" FROM QUO_PAPER_VER A , QUO_PAPER B, QUO_CATALOG C, " +
				" QUO.QUO_PAPER_CATALOG D " +
				" WHERE A.PAPER_UID = B.PAPER_UID " +
				" AND C.CATALOG_UID = D.CATALOG_UID " +
				" AND B.PAPER_UID = D.PAPER_UID " +
				" AND A.PAPER_VER_UID ='" + paperVerUid +"'" +
				" ORDER BY  B.PAPER, A.PAPER_VER ";
		
		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<QuoCatalogPaperVerTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoCatalogPaperVerTo>();
    	rowMapper.setMappedClass(QuoCatalogPaperVerTo.class);

    	List<QuoCatalogPaperVerTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	
	public int[] updPaperVerByKey(QuoCatalogPaperVerTo paperVerTo) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " UPDATE QUO_PAPER_VER SET PAPER_VER_APPROVE = ? , " +
				" VER_APPROVE_DT = ? , VER_START_DT = ?, VER_END_DT = ?, " +
				" VER_UPDATE_BY = ?, UDT = ? " +
				" WHERE PAPER_VER_UID = ?";
				
		List<Object[]> batch = new ArrayList<Object[]>();
        
            Object[] values = new Object[] {
            		paperVerTo.getPaperVerApprove(),
            		paperVerTo.getVerApproveDt(),
            		paperVerTo.getVerStartDt(),
            		paperVerTo.getVerEndDt(),
            		paperVerTo.getVerUpdateBy(),
            		paperVerTo.getUdt(),
            		paperVerTo.getPaperVerUid()
            		
        			};
            batch.add(values);


    	logger.debug(sql);
    	
    	int result [] = sjt.batchUpdate(sql, batch);
    	return result;
	}
	
	public int cleanPaperVerActive(String paperUid) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " UPDATE QUO_PAPER_VER SET PAPER_VER_APPROVE = '0' " +
				" WHERE PAPER_UID = '" + paperUid + "'" ;
				
		/*List<Object[]> batch = new ArrayList<Object[]>();
        
            Object[] values = new Object[] {
            		paperUid
           		
        			};
            batch.add(values);*/


    	logger.debug(sql);
    	int result = sjt.update(sql, new Object[] {} );
    	//int result [] = sjt.batchUpdate(sql, batch);
    	return result;
	}
	public int[] instPaperVerByKey(QuoCatalogPaperVerTo paperVerTo) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into QUO_PAPER_VER ( PAPER_VER_UID, PAPER_UID, " +
				" PAPER_VER, PAPER_VER_APPROVE, VER_CREATE_BY, " +
				" VER_APPROVE_DT, VER_START_DT, VER_END_DT ) " +
		" Values ( ?, ?, ?, ?, ?, ?, ?, ? )";
		
		List<Object[]> batch = new ArrayList<Object[]>();
		
		    Object[] values = new Object[] {
		    		paperVerTo.getPaperVerUid(),
		    		paperVerTo.getPaperUid(),
		    		paperVerTo.getPaperVer(),
		    		paperVerTo.getPaperVerApprove(),
		    		paperVerTo.getVerCreateBy(),
		    		paperVerTo.getVerApproveDt(),
		    		paperVerTo.getVerStartDt(),
		    		paperVerTo.getVerEndDt()
					};
		    batch.add(values);
		
		
		logger.debug(sql+ " " +values.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	
	public double getMaxVerByPaper(String paperUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT MAX(TO_NUMBER( A.PAPER_VER, '999.9')) PAPER_VER " +
					" FROM QUO_PAPER_VER A " +
					" WHERE A.PAPER_UID ='" + paperUid +"'";
		
		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<QuoCatalogPaperVerTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoCatalogPaperVerTo>();
    	rowMapper.setMappedClass(QuoCatalogPaperVerTo.class);

    	List<QuoCatalogPaperVerTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return Double.parseDouble( result.get(0).getPaperVer() );
		} else {
			return 0.0;
		}	
	}
	
	public List<QuoCatalogPaperVerTo> getActivePaperList() throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT C.CATALOG, C.CATALOG_UID, B.PAPER, " +
				" A.PAPER_VER_UID, A.PAPER_UID, A.PAPER_VER, " +
				" A.PAPER_VER_APPROVE, A.VER_CREATE_BY, A.CDT, " +
				" A.VER_UPDATE_BY, A.UDT, A.VER_APPROVE_DT, " +
				" A.VER_START_DT, A.VER_END_DT " +
				" FROM QUO_PAPER_VER A , QUO_PAPER B, QUO_CATALOG C, " +
				" QUO.QUO_PAPER_CATALOG D " +
				" WHERE A.PAPER_UID = B.PAPER_UID " +
				" AND C.CATALOG_UID = D.CATALOG_UID " +
				" AND B.PAPER_UID = D.PAPER_UID " +
				" AND A.PAPER_VER_APPROVE = '1' " +
				" ORDER BY  C.CATALOG, B.PAPER, A.PAPER_VER ";
		
		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<QuoCatalogPaperVerTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<QuoCatalogPaperVerTo>();
    	rowMapper.setMappedClass(QuoCatalogPaperVerTo.class);

    	List<QuoCatalogPaperVerTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
}
