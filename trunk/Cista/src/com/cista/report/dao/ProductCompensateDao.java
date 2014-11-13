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



	public List<ProductCompensateTo> getCompensationByProduct(String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.PRODUCT, A.PROJECT, A.MONTH, A.COMPENSATION, A.CREATE_DATE " +
					" FROM RPT_PRODUCT_COMPENSATE A " +
					" WHERE 1=1 " +
					" AND A.PRODUCT = ?" ;
		
    	ParameterizedBeanPropertyRowMapper<ProductCompensateTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ProductCompensateTo>();
    	rowMapper.setMappedClass(ProductCompensateTo.class);

    	List<ProductCompensateTo> result = sjt.query(sql,rowMapper, new Object[] {product} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
}
