package com.cista.report.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.report.to.FgReceiveBinTo;
import com.cista.system.util.CistaERPBaseDao;



public class FgReceiveBinDao extends CistaERPBaseDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FgReceiveBinTo getFgReceiveBinQty(String edate, String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT  MG001, MG006, ISNULL(SUM(MG008),0) FG_GOODDIE " +
				" FROM CISTA_HK..IDLMG " +
				" WHERE MG001 = '5906' " +
				"	AND MODI_DATE <= '" + edate + "' "+
				" AND MG004 IN ('GA' , 'GB' ) " +
				" AND MG006 LIKE '%" + product.substring(1) +"%' "+
				" GROUP BY MG001, MG006";
								
    	ParameterizedBeanPropertyRowMapper<FgReceiveBinTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<FgReceiveBinTo>();
    	rowMapper.setMappedClass(FgReceiveBinTo.class);

    	List<FgReceiveBinTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}

}
