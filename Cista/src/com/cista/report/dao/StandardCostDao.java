package com.cista.report.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.report.to.StandardCostTo;
import com.cista.system.util.BaseDao;


public class StandardCostDao extends BaseDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public List<StandardCostTo> getAllStandardCostByProject(String project) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.PRODUCT, A.PROJECT, A.GROSS_DIE, A.WAFER_COST, A.CP_COST, " +
				" A.CP_YIELD, A.CF_COST, A.CSP_COST, A.CSP_YIELD, A.CSP_DIE, " +
				" A.FT_UNIT_COST, A.FT_FEE, A.FT_YIELD, A.TOTAL_COST, A.GOOD_PART, " +
				" A.UNIT_COST, A.CREATE_DATE, A.CREATOR, A.UPDATE_BY, " +
				" A.UPDATE_DATE " +
				" FROM RPT_STANDARD_COST A" +
				" WHERE 1=1" +
				" AND A.PROJECT = ? " ;
		
    	ParameterizedBeanPropertyRowMapper<StandardCostTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<StandardCostTo>();
    	rowMapper.setMappedClass(StandardCostTo.class);

    	List<StandardCostTo> result = sjt.query(sql,rowMapper, new Object[] {project} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
}
