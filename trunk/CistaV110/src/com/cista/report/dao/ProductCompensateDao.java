package com.cista.report.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.report.to.ProductCompensateTo;
import com.cista.system.util.BaseDao;


public class ProductCompensateDao extends BaseDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public ProductCompensateTo getCompensationByProduct(String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.PRODUCT, A.PROJECT, SUM(A.COMPENSATION) COMPENSATION " +
					" FROM RPT_PRODUCT_COMPENSATE A " +
					" WHERE 1=1 " +
					" AND A.PRODUCT = ?" +
					" GROUP BY A.PRODUCT, A.PROJECT" +
					" ORDER BY 1" ;
		
    	ParameterizedBeanPropertyRowMapper<ProductCompensateTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ProductCompensateTo>();
    	rowMapper.setMappedClass(ProductCompensateTo.class);

    	List<ProductCompensateTo> result = sjt.query(sql,rowMapper, new Object[] {product} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
}
