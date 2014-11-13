package com.cista.report.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.report.to.ProductYieldTo;
import com.cista.system.util.BaseDao;


public class ProductYieldDao extends BaseDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public ProductYieldTo getProductYield(String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.PRODUCT, A.PROJECT, A.WAFER_YIELD, A.CP_YIELD, A.CF_YIELD, " +
						" A.CSP_YIELD, A.FT_YIELD, A.CREATE_DATE " +
						" FROM RPT_PRODUCT_YIELD A" +
						" WHERE 1=1 " +
						" AND A.PRODUCT = ?" ;
		
    	ParameterizedBeanPropertyRowMapper<ProductYieldTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ProductYieldTo>();
    	rowMapper.setMappedClass(ProductYieldTo.class);

    	List<ProductYieldTo> result = sjt.query(sql,rowMapper, new Object[] {product} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
}
