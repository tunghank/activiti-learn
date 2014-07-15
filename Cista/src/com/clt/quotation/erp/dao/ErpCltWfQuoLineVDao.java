package com.clt.quotation.erp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.erp.to.ErpCltWfQuoLineVTo;
import com.clt.system.util.CLTUtil;
import com.clt.system.util.BaseErpDao;

public class ErpCltWfQuoLineVDao extends BaseErpDao {
	
	private String orgId = CLTUtil.getMessage("System.ERP.Org");
	private String orgOuId = CLTUtil.getMessage("System.ERP.OU");
	

	
	public ErpCltWfQuoLineVTo getCltWfQuoLine(String vendorId, String partNum) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.* FROM CLT_WF_QUO_LINE_V A " +
			" WHERE A.ORG_ID = '" + this.orgId + "' " + 
			" AND VENDOR_ID = '" + vendorId + "'" +
			" AND ITEM_NUMBER = '" + partNum + "'" +
			" ORDER BY Q_NO_LAST_UPDATE_DATE DESC, Q_NO DESC";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpCltWfQuoLineVTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpCltWfQuoLineVTo>();
    	rowMapper.setMappedClass(ErpCltWfQuoLineVTo.class);

    	List<ErpCltWfQuoLineVTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}

}
