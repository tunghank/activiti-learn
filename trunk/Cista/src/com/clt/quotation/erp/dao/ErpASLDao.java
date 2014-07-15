package com.clt.quotation.erp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.erp.to.ErpASLTo;
import com.clt.system.util.CLTUtil;
import com.clt.system.util.BaseErpDao;

public class ErpASLDao extends BaseErpDao {
	
	private String orgSiteId = CLTUtil.getMessage("System.ERP.Org");
	private String orgOuId = CLTUtil.getMessage("System.ERP.OU");
	

	
	public List<ErpASLTo> getASLVendorList(String itemId) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT ASL_ID, VENDOR_SITE_ID, VENDOR_ID, ITEM_ID " +
				" FROM APPS.PO_APPROVED_SUPPLIER_LIST ASL " +
				" WHERE ASL.OWNING_ORGANIZATION_ID = '" + this.orgOuId + "'" +
				" AND NVL(ASL.DISABLE_FLAG , 'N') = 'N' " +
				" AND ASL.ITEM_ID = '"+ itemId + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpASLTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpASLTo>();
    	rowMapper.setMappedClass(ErpASLTo.class);

    	List<ErpASLTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
}
