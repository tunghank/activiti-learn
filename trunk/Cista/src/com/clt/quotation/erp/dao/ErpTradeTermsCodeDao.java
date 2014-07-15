package com.clt.quotation.erp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.erp.to.ErpTradeTermsCodeTo;
import com.clt.system.util.CLTUtil;
import com.clt.system.util.BaseErpDao;

public class ErpTradeTermsCodeDao extends BaseErpDao {
	
	private String orgId = CLTUtil.getMessage("System.ERP.Org");
	private String orgOuId = CLTUtil.getMessage("System.ERP.OU");
	


	public List<ErpTradeTermsCodeTo> getAllTradeTermsCode() throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT * FROM CLT_WF_TRADE_TERMS_V" ;

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpTradeTermsCodeTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpTradeTermsCodeTo>();
    	rowMapper.setMappedClass(ErpTradeTermsCodeTo.class);

    	List<ErpTradeTermsCodeTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
}
