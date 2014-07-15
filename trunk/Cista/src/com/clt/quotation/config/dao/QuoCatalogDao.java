package com.clt.quotation.config.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.config.to.QuoCatalogTo;
import com.clt.quotation.config.to.QuoPaperCatalogTo;
import com.clt.system.util.BaseDao;

public class QuoCatalogDao extends BaseDao {
	
	public List<QuoCatalogTo> getAllCatalog() throws DataAccessException{
			
			SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
			String sql  = " SELECT A.CATALOG_UID, A.CATALOG " +
						" FROM QUO_CATALOG A " +
						" Order by A.CATALOG ";
			
	    	ParameterizedBeanPropertyRowMapper<QuoCatalogTo> rowMapper = 
	    		new ParameterizedBeanPropertyRowMapper<QuoCatalogTo>();
	    	rowMapper.setMappedClass(QuoCatalogTo.class);

	    	List<QuoCatalogTo> result = sjt.query(sql,rowMapper, new Object[] {} );
			
			if (result != null && result.size() > 0) {
				return result;
			} else {
				return null;
			}	
	}
	
	public int[] instPaperCatalog(QuoPaperCatalogTo paperCatalogTo) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into QUO_PAPER_CATALOG ( PAPER_CATALOG_UID, CATALOG_UID, " +
				" PAPER_UID ) " +
		" Values ( ?, ?, ? )";
		
		List<Object[]> batch = new ArrayList<Object[]>();
		
		    Object[] values = new Object[] {
		    		paperCatalogTo.getPaperCatalogUid(),
		    		paperCatalogTo.getCatalogUid(),
		    		paperCatalogTo.getPaperUid()
					};
		    batch.add(values);
		
		
		logger.debug(sql+ " " +values.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
}
