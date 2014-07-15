package com.clt.quotation.erp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.erp.to.ErpTaxTo;
import com.clt.system.util.CLTUtil;
import com.clt.system.util.BaseErpDao;

public class ErpTaxDao extends BaseErpDao {
	
	private String orgId = CLTUtil.getMessage("System.ERP.Org");
	private String orgOuId = CLTUtil.getMessage("System.ERP.OU");
	
	public List<ErpTaxTo> getAllTax() throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.NAME, A.TAX_ID, A.TAX_TYPE, A.DESCRIPTION, " +
				" A.TAX_RATE " +
				" FROM AP_TAX_CODES_ALL A, ORG_ORGANIZATION_DEFINITIONS  B " +
				" WHERE A.SET_OF_BOOKS_ID = B.SET_OF_BOOKS_ID " +
				" AND B.ORGANIZATION_ID ='" + orgOuId + "'" +
				" AND A.ORG_ID = '" + orgId + "'" +
				" AND ( A.INACTIVE_DATE IS NULL OR A.INACTIVE_DATE >= TRUNC(SYSDATE) )" +
				" AND TAX_TYPE ='VAT' ";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpTaxTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpTaxTo>();
    	rowMapper.setMappedClass(ErpTaxTo.class);

    	List<ErpTaxTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
	public List<ErpTaxTo> getAllTaxRate() throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT DISTINCT A.TAX_RATE " +
				" FROM AP_TAX_CODES_ALL A, ORG_ORGANIZATION_DEFINITIONS  B " +
				" WHERE A.SET_OF_BOOKS_ID = B.SET_OF_BOOKS_ID " +
				" AND B.ORGANIZATION_ID ='" + orgOuId + "'" +
				" AND A.ORG_ID = '" + orgId + "'" +
				" AND ( A.INACTIVE_DATE IS NULL OR A.INACTIVE_DATE >= TRUNC(SYSDATE) )" +
				" AND TAX_TYPE ='VAT' ";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpTaxTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpTaxTo>();
    	rowMapper.setMappedClass(ErpTaxTo.class);

    	List<ErpTaxTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
	public ErpTaxTo getTax(String taxName) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.NAME, A.TAX_ID, A.TAX_TYPE, A.DESCRIPTION, " +
				" A.TAX_RATE " +
				" FROM AP_TAX_CODES_ALL A, ORG_ORGANIZATION_DEFINITIONS  B " +
				" WHERE A.SET_OF_BOOKS_ID = B.SET_OF_BOOKS_ID " +
				" AND B.ORGANIZATION_ID ='" + orgOuId + "'" +
				" AND A.ORG_ID = '" + orgId + "'" +
				" AND ( A.INACTIVE_DATE IS NULL OR A.INACTIVE_DATE >= TRUNC(SYSDATE) )" +
				" AND A.NAME = '" + taxName + "'" +
				" AND TAX_TYPE ='VAT' ";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpTaxTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpTaxTo>();
    	rowMapper.setMappedClass(ErpTaxTo.class);

    	List<ErpTaxTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}

	public ErpTaxTo getTaxByTaxRate(String taxRate) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.NAME, A.TAX_ID, A.TAX_TYPE, A.DESCRIPTION, " +
				" A.TAX_RATE " +
				" FROM AP_TAX_CODES_ALL A, ORG_ORGANIZATION_DEFINITIONS  B " +
				" WHERE A.SET_OF_BOOKS_ID = B.SET_OF_BOOKS_ID " +
				" AND B.ORGANIZATION_ID ='" + orgOuId + "'" +
				" AND A.ORG_ID = '" + orgId + "'" +
				" AND ( A.INACTIVE_DATE IS NULL OR A.INACTIVE_DATE >= TRUNC(SYSDATE) )" +
				" AND A.TAX_RATE = '" + taxRate + "'" +
				" AND TAX_TYPE ='VAT' ";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpTaxTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpTaxTo>();
    	rowMapper.setMappedClass(ErpTaxTo.class);

    	List<ErpTaxTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
}
