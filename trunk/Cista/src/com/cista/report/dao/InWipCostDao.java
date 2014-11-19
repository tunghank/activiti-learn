package com.cista.report.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import com.cista.report.to.InWipCostTo;
import com.cista.system.util.CistaERPBaseDao;



public class InWipCostDao extends CistaERPBaseDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<InWipCostTo> getInWipCostByProduct(String month, String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT SUBSTRING(A.TA001,0,4) TA001 , SUBSTRING(MB003,0,6) PRODUCT, " +
					" ( ISNULL(SUM(A.TA026),0) + ISNULL(SUM(A.TA029),0) ) AS MATERIAL_COST " +
					" FROM CISTA_HK..CSTTA AS A " +
					" JOIN CISTA_HK..MOCTA AS B ON A.TA003=B.TA001 AND A.TA004=B.TA002  " +
					" LEFT JOIN CISTA_HK..INVMB AS C ON A.TA001=MB001 " +
					" WHERE 1=1  " +
					//" AND ((A.TA023<>0 OR A.TA024<>0 OR A.TA031<>0) AND (A.TA002='" + month + "')) " +
					" and  ( SUBSTRING(A.TA002,1,6) = N'" + month + "') " +
					" AND  MB003 LIKE '" + product + "%' " +
					" GROUP BY SUBSTRING(A.TA001,0,4) , SUBSTRING(MB003,0,6) " +
					" ORDER BY 1 ";
								
    	ParameterizedBeanPropertyRowMapper<InWipCostTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InWipCostTo>();
    	rowMapper.setMappedClass(InWipCostTo.class);

    	List<InWipCostTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}

	public List<InWipCostTo> getNgCostByProduct(String edate, String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT SUBSTRING(A.TA001,0,4) TA001 , SUBSTRING(MB003,0,6) PRODUCT, " +
					" ( 0- ( ISNULL(SUM(A.TA036),0) + ISNULL(SUM(A.TA039),0) ) ) AS NG_COST " +
					" FROM CISTA_HK..CSTTA AS A " +
					" JOIN CISTA_HK..MOCTA AS B ON A.TA003=B.TA001 AND A.TA004=B.TA002  " +
					" LEFT JOIN CISTA_HK..INVMB AS C ON A.TA001=MB001 " +
					" WHERE 1=1  " +
					//" AND ((A.TA023<>0 OR A.TA024<>0 OR A.TA031<>0) AND (A.TA002='" + month + "')) " +
					" and  A.TA002 <= '" + edate + "' " +
					" AND  MB003 LIKE '" + product + "%' " +
					" GROUP BY SUBSTRING(A.TA001,0,4) , SUBSTRING(MB003,0,6) " +
					" ORDER BY 1 ";
								
    	ParameterizedBeanPropertyRowMapper<InWipCostTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InWipCostTo>();
    	rowMapper.setMappedClass(InWipCostTo.class);

    	List<InWipCostTo> result = sjt.query(sql,rowMapper, new Object[] {} );

		logger.debug("SQL " + sql);
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
}
