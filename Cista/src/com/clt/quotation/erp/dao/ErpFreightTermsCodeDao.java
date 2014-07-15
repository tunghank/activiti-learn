package com.clt.quotation.erp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.erp.to.ErpFreightTermsCodeTo;
import com.clt.system.util.CLTUtil;
import com.clt.system.util.BaseErpDao;

public class ErpFreightTermsCodeDao extends BaseErpDao {
	
	private String orgId = CLTUtil.getMessage("System.ERP.Org");
	private String orgOuId = CLTUtil.getMessage("System.ERP.OU");
	

	public ErpFreightTermsCodeTo getFreightTermsCode(String lookupCode) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT V.LOOKUP_CODE, V.MEANING " +
				" FROM FND_LOOKUP_TYPES_VL T, FND_LOOKUP_VALUES_VL V " +
       			" WHERE T.LOOKUP_TYPE = 'FREIGHT TERMS' " +
       			" AND ENABLED_FLAG = 'Y' " +
       			" AND T.LOOKUP_TYPE = V.LOOKUP_TYPE " +
       			" AND NVL(START_DATE_ACTIVE, SYSDATE - 1) < SYSDATE " +
       			" AND NVL(END_DATE_ACTIVE, SYSDATE + 1) > SYSDATE " +
       			" AND UPPER(V.LOOKUP_CODE) = '"+ lookupCode.toUpperCase() + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpFreightTermsCodeTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpFreightTermsCodeTo>();
    	rowMapper.setMappedClass(ErpFreightTermsCodeTo.class);

    	List<ErpFreightTermsCodeTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}

	public List<ErpFreightTermsCodeTo> getAllFreightTermsCode() throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT V.LOOKUP_CODE, V.MEANING " +
				" FROM FND_LOOKUP_TYPES_VL T, FND_LOOKUP_VALUES_VL V " +
       			" WHERE T.LOOKUP_TYPE = 'FREIGHT TERMS' " +
       			" AND ENABLED_FLAG = 'Y' " +
       			" AND T.LOOKUP_TYPE = V.LOOKUP_TYPE " +
       			" AND NVL(START_DATE_ACTIVE, SYSDATE - 1) < SYSDATE " +
       			" AND NVL(END_DATE_ACTIVE, SYSDATE + 1) > SYSDATE " +
       			" Order by V.LOOKUP_CODE " ;

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpFreightTermsCodeTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpFreightTermsCodeTo>();
    	rowMapper.setMappedClass(ErpFreightTermsCodeTo.class);

    	List<ErpFreightTermsCodeTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
}
