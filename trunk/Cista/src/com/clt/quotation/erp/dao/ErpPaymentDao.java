package com.clt.quotation.erp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.erp.to.ErpPaymentTo;
import com.clt.system.util.CLTUtil;
import com.clt.system.util.BaseErpDao;

public class ErpPaymentDao extends BaseErpDao {
	
	private String orgId = CLTUtil.getMessage("System.ERP.Org");
	private String orgOuId = CLTUtil.getMessage("System.ERP.OU");
	
	public List<ErpPaymentTo> getAllPayment() throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT DISTINCT TERM_ID, NAME FROM AP_TERMS_TL PAYMENT ";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpPaymentTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpPaymentTo>();
    	rowMapper.setMappedClass(ErpPaymentTo.class);

    	List<ErpPaymentTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
	public ErpPaymentTo getPayment(String termId) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT DISTINCT TERM_ID, NAME FROM AP_TERMS_TL PAYMENT " +
				" WHERE TERM_ID = '" + termId + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpPaymentTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpPaymentTo>();
    	rowMapper.setMappedClass(ErpPaymentTo.class);

    	List<ErpPaymentTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}

}
