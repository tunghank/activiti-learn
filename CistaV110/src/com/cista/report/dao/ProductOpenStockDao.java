package com.cista.report.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.report.to.ProductOpenStockTo;
import com.cista.system.util.BaseDao;


public class ProductOpenStockDao extends BaseDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public ProductOpenStockTo getProductOpenStockCost(String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.PRODUCT, A.PROJECT, SUM(A.IN_AMOUNT) IN_AMOUNT, SUM(A.OUT_AMOUNT) OUT_AMOUNT " +
					" FROM RPT_PRODUCT_OPEN_STOCK A " +
					" WHERE PRODUCT = ? " +
					" GROUP BY A.PRODUCT, A.PROJECT " ;
		
    	ParameterizedBeanPropertyRowMapper<ProductOpenStockTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ProductOpenStockTo>();
    	rowMapper.setMappedClass(ProductOpenStockTo.class);

    	List<ProductOpenStockTo> result = sjt.query(sql,rowMapper, new Object[] {product} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
}
