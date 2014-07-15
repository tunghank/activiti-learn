package com.clt.quotation.erp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.erp.to.ErpCurrencyTo;

import com.clt.system.util.BaseErpDao;

public class ErpCurrencyDao extends BaseErpDao {

	
	public List<ErpCurrencyTo> getErpCurrency() throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  =  " SELECT A.CURRENCY_CODE, A.NAME, A.SYMBOL  " +
					" FROM FND_CURRENCIES_VL A " +
					" WHERE CURRENCY_FLAG = 'Y' " +
					" AND ENABLED_FLAG = 'Y' ";
					
		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpCurrencyTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpCurrencyTo>();
    	rowMapper.setMappedClass(ErpCurrencyTo.class);

    	List<ErpCurrencyTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	

}
