package com.cista.report.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.report.to.UnitCostTo;
import com.cista.system.util.BaseDao;


public class UnitCostDao extends BaseDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public UnitCostTo getUnitCostByProduct(String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.PRODUCT, A.PROJECT, A.WAFER, A.CP, A.CF, A.CSP, A.FT, " +
					" A.CREATE_DATE " +
					" FROM RPT_UNIT_COST A " +
					" WHERE 1=1 " +
					" AND A.PRODUCT = ?" ;
		
    	ParameterizedBeanPropertyRowMapper<UnitCostTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<UnitCostTo>();
    	rowMapper.setMappedClass(UnitCostTo.class);

    	List<UnitCostTo> result = sjt.query(sql,rowMapper, new Object[] {product} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
}
